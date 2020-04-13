package com.ebiz.webapp.web.struts.manager.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 */
public class MsgAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);// 导航

		DynaBean dynaBean = (DynaBean) form;

		Pager pager = (Pager) dynaBean.get("pager");
		String msg_title = (String) dynaBean.get("msg_title");
		String msg_title_like = (String) dynaBean.get("msg_title_like");
		String msg_type = (String) dynaBean.get("msg_type");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String info_state = (String) dynaBean.get("info_state");

		logger.info("==msg_type:{}", msg_type);
		if (!GenericValidator.isInt(msg_type)) {
			msg_type = String.valueOf(Keys.MSG_TYPE.MSG_TYPE_0.getIndex());
		}
		if (!GenericValidator.isInt(info_state)) {
			info_state = "1";
			dynaBean.set("info_state", info_state);
		}

		logger.info("==msg_type:{}", msg_type);
		Msg entity = new Msg();
		super.copyProperties(entity, form);

		entity.setMsg_type(Integer.valueOf(msg_type));
		if (StringUtils.isNotBlank(msg_title)) {
			entity.getMap().put("msg_title", msg_title);
		}
		entity.getMap().put("st_date", st_date);
		entity.getMap().put("en_date", en_date);
		entity.getMap().put("send_time_like", msg_title_like);
		entity.setInfo_state(Integer.valueOf(info_state));// 已发送
		Integer recordCount = getFacade().getMsgService().getMsgCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<Msg> entityList = getFacade().getMsgService().getMsgPaginatedList(entity);
		if (entityList != null && entityList.size() > 0) {
			for (Msg msg : entityList) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(msg.getUser_id());
				userInfo.setIs_del(0);
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {
					msg.getMap().put("mobile", userInfo.getMobile());
				}
			}
		}

		request.setAttribute("entityList", entityList);
		if (msg_type.equals(String.valueOf(Keys.MSG_TYPE.MSG_TYPE_10.getIndex()))
				|| msg_type.equals(String.valueOf(Keys.MSG_TYPE.MSG_TYPE_20.getIndex()))) {// 商家申请留言
			return new ActionForward("/../manager/admin/Msg/list_entp_msg.jsp");
		}

		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		// DynaBean dynaBean = (DynaBean) form;

		// List<DeptInfo> deptInfoList = getFacade().getDeptInfoService().getDeptInfoListWithUserList(new DeptInfo());
		// String treeNodes = super.getTreeNodesFromDeptInfoListWithUserList(deptInfoList, null);
		// request.setAttribute("treeNodes", treeNodes);
		// dynaBean.set("isShowTree", "1");

		// UserInfo ui = new UserInfo();
		// ui.setUser_type(20);
		// ui.setIs_del(0);
		// List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(ui);
		// request.setAttribute("userInfoList", userInfoList);

		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			Msg entity = new Msg();
			entity.setId(new Integer(id));
			getFacade().getMsgService().removeMsg(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			Msg entity = new Msg();
			entity.getMap().put("pks", pks);
			getFacade().getMsgService().removeMsg(entity);
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);// 导航

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isLong(id)) {
			Msg entity = new Msg();
			entity.setId(new Integer(id));
			entity.setMsg_type((Keys.MSG_TYPE.MSG_TYPE_10.getIndex()));
			entity = getFacade().getMsgService().getMsg(entity);
			super.copyProperties(form, entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}

			UserInfo userInfo = new UserInfo();
			userInfo.setId(entity.getUser_id());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			request.setAttribute("real_name", userInfo.getReal_name());

		}
		return new ActionForward("/../manager/admin/Msg/reply.jsp");
	};

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		Msg entity = new Msg();
		super.copyProperties(entity, form);
		if (entity.getReply_content() != null) {// 回复
			if (GenericValidator.isLong(id)) {
				entity.setId(new Integer(id));
				entity.setInfo_state(Keys.INFO_STATE.INFO_STATE_1.getIndex());
				entity.setReply_time(new Date());
				entity.setReply_user_id(ui.getId());
				entity.setReply_user_name(ui.getUser_name());
				getFacade().getMsgService().modifyMsg(entity);
				saveMessage(request, "entity.updated");
			}
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		String user_ids = (String) dynaBean.get("user_ids");
		String is_send_all = (String) dynaBean.get("is_send_all");
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		Msg entity = new Msg();
		super.copyProperties(entity, form);
		logger.info("===user_ids:{}", user_ids);
		logger.info("===is_send_all:{}", is_send_all);
		// 发送站内信
		if (StringUtils.isNotBlank(is_send_all) && is_send_all.equals("1")) {// 发送全部
			entity.setIs_send_all(1);
		} else if (StringUtils.isNotBlank(user_ids)) {
			List<MsgReceiver> mrList = new ArrayList<MsgReceiver>();
			String[] uids = StringUtils.split(user_ids, ",");
			for (String user_id : uids) {
				MsgReceiver mr = new MsgReceiver();
				UserInfo uii = super.getUserInfo(Integer.valueOf(user_id));
				mr.setUser_id(ui.getId());
				mr.setReceiver_user_id(uii.getId());
				mr.setReceiver_user_mobile(uii.getUser_name() + "(" + uii.getMobile() + ")");
				mr.setIs_del(0);
				mr.setIs_read(0);
				mr.setIs_reply(0);
				mrList.add(mr);
			}
			entity.setMsgReceiverList(mrList);
		}

		entity.setUser_name(ui.getUser_name());
		entity.setMsg_type(0);
		entity.setUser_id(ui.getId());
		entity.setSend_time(new Date());
		getFacade().getMsgService().createMsg(entity);

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			Msg msg = new Msg();
			msg.setId(new Integer(id));
			msg = getFacade().getMsgService().getMsg(msg);
			if (null == msg) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, msg);

			if (msg.getIs_send_all().intValue() == 0) {
				MsgReceiver mr = new MsgReceiver();
				mr.setMsg_id(msg.getId());
				List<MsgReceiver> mrList = super.getFacade().getMsgReceiverService().getMsgReceiverList(mr);
				request.setAttribute("mrList", mrList);
			}

			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			Msg msg = new Msg();
			msg.setId(new Integer(id));
			Msg entity = getFacade().getMsgService().getMsg(msg);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			MsgReceiver msgRecever = new MsgReceiver();
			msgRecever.setMsg_id(entity.getId());
			List<MsgReceiver> msgReceiverList = getFacade().getMsgReceiverService().getMsgReceiverList(msgRecever);
			String _names = "";
			for (MsgReceiver mr : msgReceiverList) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(mr.getReceiver_user_id());
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {
					_names += "," + userInfo.getUser_name();
				}
			}
			_names = StringUtils.substring(_names, 1);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			dynaBean.set("isShowTree", "0");
			dynaBean.set("userNames", _names);
			return mapping.findForward("input");
		} else {
			this.saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward apply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String send_user_id = (String) dynaBean.get("send_user_id");
		String msg_id = (String) dynaBean.get("msg_id");

		Msg msg = new Msg();
		msg.setId(new Integer(msg_id));
		msg = getFacade().getMsgService().getMsg(msg);
		if (null != msg) {
			dynaBean.set("send_msg_title", "Re:" + msg.getMsg_title());
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(new Integer(send_user_id));
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null != userInfo) {
			request.setAttribute("send_user_name", userInfo.getUser_name());
		}

		Msg entity = new Msg();
		entity.setUser_id(new Integer(ui.getId()));
		super.copyProperties(form, entity);

		dynaBean.set("send_user_id", send_user_id);
		dynaBean.set("msg_id", msg_id);

		return new ActionForward("/admin/Msg/apply.jsp");
	}

	public ActionForward saveApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Msg entity = new Msg();
		super.copyProperties(entity, form);

		DynaBean dynaBean = (DynaBean) form;
		String send_user_id = (String) dynaBean.get("send_user_id");
		String msg_id = (String) dynaBean.get("msg_id");
		String mod_id = (String) dynaBean.get("mod_id");

		List<MsgReceiver> msgReceiverList = new ArrayList<MsgReceiver>();
		MsgReceiver msgRecever = new MsgReceiver();
		msgRecever.setMsg_id(entity.getId());
		msgRecever.setReceiver_user_id(new Integer(send_user_id));
		msgRecever.setIs_read(0);
		msgRecever.setIs_reply(0);
		msgRecever.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		msgReceiverList.add(msgRecever);
		entity.setMsgReceiverList(msgReceiverList);

		entity.setSend_time(new Date());

		if (null == entity.getId()) {
			entity.setReplay_id(Integer.valueOf(msg_id));
			entity.setIs_replay_msg(1);
			getFacade().getMsgService().createMsg(entity);
			saveMessage(request, "entity.inerted");
		} else {
			saveMessage(request, "entity.missing");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}
}