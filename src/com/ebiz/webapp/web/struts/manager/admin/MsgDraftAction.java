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
public class MsgDraftAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
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
			saveMessage(request, "entity.deleted");
		}
		ActionForward forward = new ActionForward(mapping.findForward("success").getPath()
				+ response.encodeURL("&mod_id=" + mod_id), true);
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		// List<DeptInfo> deptInfoList = getFacade().getDeptInfoService().getDeptInfoListWithUserList(new DeptInfo());
		// String treeNodes = super.getTreeNodesFromDeptInfoListWithUserList(deptInfoList, msgReceverList);
		// request.setAttribute("treeNodes", treeNodes);

		if (GenericValidator.isLong(id)) {
			Msg msg = new Msg();
			msg.setId(new Integer(id));
			Msg entity = getFacade().getMsgService().getMsg(msg);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, entity);

			MsgReceiver msgRecever = new MsgReceiver();
			msgRecever.setMsg_id(Integer.valueOf(id));
			List<MsgReceiver> msgReceverList = getFacade().getMsgReceiverService().getMsgReceiverList(msgRecever);
			if ((null != msgReceverList) && (msgReceverList.size() > 0)) {
				String[] user_ids = new String[msgReceverList.size()];
				int i = 0;
				for (MsgReceiver mr : msgReceverList) {
					user_ids[i++] = mr.getReceiver_user_id().toString();
				}
				dynaBean.set("user_ids", user_ids);
			}
			UserInfo ui = new UserInfo();
			ui.setUser_type(30);
			ui.setIs_del(0);
			List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(ui);
			request.setAttribute("userInfoList", userInfoList);
			return mapping.findForward("input");
		} else {
			this.saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		Msg entity = new Msg();
		entity.setUser_id(ui.getId());

		DynaBean dynaBean = (DynaBean) form;
		String st_pub_date = (String) dynaBean.get("st_pub_date");
		String en_pub_date = (String) dynaBean.get("en_pub_date");
		String msg_title_like = (String) dynaBean.get("msg_title_like");
		Pager pager = (Pager) dynaBean.get("pager");

		entity.getMap().put("msg_title_like", msg_title_like);
		entity.getMap().put("st_pub_date", st_pub_date);
		entity.getMap().put("en_pub_date", en_pub_date);
		super.copyProperties(entity, form);
		entity.setInfo_state(0);

		long recordCount = getFacade().getMsgService().getMsgCount(entity);

		pager.init(recordCount, pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<Msg> list = getFacade().getMsgService().getMsgPaginatedList(entity);
		request.setAttribute("entityList", list);
		return mapping.findForward("list");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		Msg entity = new Msg();
		super.copyProperties(entity, form);

		DynaBean dynaBean = (DynaBean) form;
		String[] user_ids = request.getParameterValues("user_ids");
		String mod_id = (String) dynaBean.get("mod_id");

		List<MsgReceiver> msgReceiverList = new ArrayList<MsgReceiver>();
		for (String user_id : user_ids) {
			MsgReceiver msgRecever = new MsgReceiver();
			msgRecever.setMsg_id(entity.getId());
			msgRecever.setReceiver_user_id(new Integer(user_id));
			msgRecever.setIs_read(0);
			msgRecever.setIs_reply(0);
			msgRecever.setIs_del(0);
			msgReceiverList.add(msgRecever);
		}
		entity.setMsgReceiverList(msgReceiverList);

		if (null == entity.getId()) {
			entity.setSend_time(new Date());
			entity.setUser_id(ui.getId());
			entity.setIs_replay_msg(0);
			getFacade().getMsgService().createMsg(entity);
			saveMessage(request, "entity.inerted");
		} else {
			getFacade().getMsgService().modifyMsg(entity);
			saveMessage(request, "entity.updated");
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