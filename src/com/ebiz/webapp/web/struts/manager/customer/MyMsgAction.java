package com.ebiz.webapp.web.struts.manager.customer;

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
 * @author Wuyang
 * @version 2016-1-6
 */
public class MyMsgAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		MsgReceiver entity = new MsgReceiver();

		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String read_state = (String) dynaBean.get("read_state");
		Pager pager = (Pager) dynaBean.get("pager");

		super.copyProperties(entity, form);

		entity.setReceiver_user_id(ui.getId());
		entity.getMap().put("st_date", st_date);
		entity.getMap().put("en_date", en_date);
		if (GenericValidator.isLong(read_state)) {
			if (read_state.equals("0")) {// 未读
				entity.getMap().put("is_not_read", "true");
			}
			if (read_state.equals("1")) {// 已读
				entity.setIs_read(1);
			}
		}

		long recordCount = getFacade().getMsgReceiverService().getMsgReceiverCount(entity);

		pager.init(recordCount, pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<MsgReceiver> msgRecList = getFacade().getMsgReceiverService().getMsgReceiverPaginatedList(entity);
		request.setAttribute("entityList", msgRecList);

		return mapping.findForward("list");

	}

	public ActionForward setRead(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		MsgReceiver entity = new MsgReceiver();
		entity.getMap().put("receiver_user_id", ui.getId());
		entity.setIs_read(1);
		super.getFacade().getMsgReceiverService().modifyMsgReceiver(entity);

		ActionForward forward = new ActionForward(mapping.findForward("success").getPath()
				+ response.encodeURL("&mod_id=" + mod_id + "&par_id=" + par_id), true);
		return forward;

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String msg_id = (String) dynaBean.get("msg_id");
		String msg_rec_id = (String) dynaBean.get("msg_rec_id");

		getsonSysModuleList(request, dynaBean);

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
		int minus_msg_count = 0;
		if (GenericValidator.isLong(msg_rec_id)) {
			MsgReceiver msgReceiver = new MsgReceiver();
			msgReceiver.setId(Integer.valueOf(msg_rec_id));
			msgReceiver.getMap().put("select_only_msg_re", "true");
			msgReceiver.setIs_read(0);
			int count = getFacade().getMsgReceiverService().getMsgReceiverCount(msgReceiver);
			logger.info("==count:" + count);
			if (count > 0) {
				msgReceiver.setIs_read(1);
				minus_msg_count = getFacade().getMsgReceiverService().modifyMsgReceiver(msgReceiver);
			}
		} else {
			// MsgReceiver mr = new MsgReceiver();
			// mr.setMsg_id(entity.getId());
			// mr.setUser_id(entity.getUser_id());
			// mr.setReceiver_user_id(ui.getId());
			// mr.setReceiver_user_mobile(ui.getUser_name() + "(" + ui.getMobile() + ")");
			// mr.setIs_del(0);
			// mr.setIs_read(0);
			// mr.setIs_reply(0);
			// int count = getFacade().getMsgReceiverService().getMsgReceiverCount(mr);
			// if (count > 0) {
			// mr.setIs_read(1);
			// minus_msg_count = getFacade().getMsgReceiverService().modifyMsgReceiver(mr);
			// }
		}

		dynaBean.set("minus_msg_count", minus_msg_count);

		super.copyProperties(form, entity);
		dynaBean.set("msg_id", msg_id);

		return mapping.findForward("view");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

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
				+ response.encodeURL("&mod_id=" + mod_id + "&par_id=" + par_id), true);
		return forward;
	}
}
