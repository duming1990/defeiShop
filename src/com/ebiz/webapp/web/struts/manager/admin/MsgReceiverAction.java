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
public class MsgReceiverAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String send_user_id = (String) dynaBean.get("send_user_id");
		String revert_id = (String) dynaBean.get("revert_id");
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
		dynaBean.set("revert_id", revert_id);

		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			MsgReceiver entity = new MsgReceiver();
			entity.setId(new Integer(id));
			getFacade().getMsgReceiverService().removeMsgReceiver(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			MsgReceiver entity = new MsgReceiver();
			entity.getMap().put("pks", pks);
			getFacade().getMsgReceiverService().removeMsgReceiver(entity);
			saveMessage(request, "entity.deleted");
		}
		ActionForward forward = new ActionForward(mapping.findForward("success").getPath()
				+ response.encodeURL("&mod_id=" + mod_id), true);
		return forward;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		MsgReceiver entity = new MsgReceiver();
		entity.setReceiver_user_id(ui.getId());

		DynaBean dynaBean = (DynaBean) form;
		String st_pub_date = (String) dynaBean.get("st_pub_date");
		String en_pub_date = (String) dynaBean.get("en_pub_date");
		Pager pager = (Pager) dynaBean.get("pager");

		entity.getMap().put("st_pub_date", st_pub_date);
		entity.getMap().put("en_pub_date", en_pub_date);
		super.copyProperties(entity, form);

		long recordCount = getFacade().getMsgReceiverService().getMsgReceiverCount(entity);

		pager.init(recordCount, pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<MsgReceiver> msgRecList = getFacade().getMsgReceiverService().getMsgReceiverPaginatedList(entity);
		request.setAttribute("entityList", msgRecList);
		return mapping.findForward("list");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		msgRecever.setIs_del(0);
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

		dynaBean = (DynaBean) form;
		String revert_id = (String) dynaBean.get("revert_id");
		if (StringUtils.isNotBlank(revert_id)) {
			MsgReceiver msgReceiver = new MsgReceiver();
			msgReceiver.setId(new Integer(revert_id));
			msgReceiver.setIs_reply(1);
			getFacade().getMsgReceiverService().modifyMsgReceiver(msgReceiver);
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

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String msg_id = (String) dynaBean.get("msg_id");
		String msg_rec_id = (String) dynaBean.get("msg_rec_id");

		if (!GenericValidator.isLong(msg_id)) {
			String msg = "msg_id 为空";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		UserInfo ui = super.getUserInfoFromSession(request);

		Msg msg = new Msg();
		msg.setId(new Integer(msg_id));
		Msg entity = getFacade().getMsgService().getMsg(msg);

		if (null == entity) {
			String msg1 = "信息不存在";
			super.showMsgForManager(request, response, msg1);
			return null;
		}

		request.setAttribute("send_user_name", entity.getUser_name());
		if (GenericValidator.isLong(msg_rec_id)) {
			MsgReceiver msgReceiver = new MsgReceiver();
			msgReceiver.setId(Integer.valueOf(msg_rec_id));
			msgReceiver.setIs_read(1);
			getFacade().getMsgReceiverService().modifyMsgReceiver(msgReceiver);
		} else {
			MsgReceiver mr = new MsgReceiver();
			mr.setMsg_id(entity.getId());
			mr.setUser_id(entity.getUser_id());
			mr.setReceiver_user_id(ui.getId());
			mr.setReceiver_user_mobile(ui.getUser_name() + "(" + ui.getMobile() + ")");
			mr.setIs_del(0);
			mr.setIs_read(1);
			mr.setIs_reply(0);
			super.getFacade().getMsgReceiverService().createMsgReceiver(mr);
		}

		super.copyProperties(form, entity);
		dynaBean.set("msg_id", msg_id);

		return mapping.findForward("view");
	}

}