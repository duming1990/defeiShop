package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class EntpMsgAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		dynaBean.set("order_value", "0");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui.getP_index()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, ui.getP_index());
		}

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String msg_title_like = (String) dynaBean.get("msg_title_like");

		Msg msg = new Msg();
		super.copyProperties(msg, form);

		msg.getMap().put("msg_title_like", msg_title_like);

		msg.setMsg_type((Keys.MSG_TYPE.MSG_TYPE_10.getIndex()));
		msg.setUser_id(ui.getId());

		Integer recordCount = getFacade().getMsgService().getMsgCount(msg);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		msg.getRow().setFirst(pager.getFirstRow());
		msg.getRow().setCount(pager.getRowCount());

		List<Msg> msgList = getFacade().getMsgService().getMsgPaginatedList(msg);
		if ((null != msgList) && (msgList.size() > 0)) {
			for (Msg temp : msgList) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(temp.getUser_id());
				userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				temp.getMap().put("real_name", userInfo.getReal_name());
				temp.getMap().put("mobile", userInfo.getMobile());
				temp.getMap().put("p_index", userInfo.getP_index());
			}
		}
		request.setAttribute("msgList", msgList);
		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		String p_index = (String) dynaBean.get("p_index");

		if (!GenericValidator.isLong(p_index)) {
			String msg = "区域为空";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		Msg entity = new Msg();
		super.copyProperties(entity, form);

		entity.setMsg_type(Keys.MSG_TYPE.MSG_TYPE_10.getIndex());
		entity.setUser_id(userInfo.getId());
		entity.setSend_time(new Date());

		BaseProvince bp = super.getBaseProvince(Long.valueOf(p_index));
		if (null == bp) {
			String msg = "区域不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		entity.setP_index(bp.getP_index().intValue());
		entity.setP_name(bp.getFull_name());
		entity.setUser_name(userInfo.getUser_name());

		UserInfo ui = new UserInfo();// 申请商家留言将用户区域更下，可能不太合理，后边在改。
		ui.setId(userInfo.getId());
		ui.setP_index(Integer.valueOf(p_index));
		super.getFacade().getUserInfoService().modifyUserInfo(ui);

		super.getFacade().getMsgService().createMsg(entity);

		saveMessage(request, "entity.inerted");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));

		}
		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			Msg entity = new Msg();
			entity.setId(new Integer(id));
			entity.setMsg_type((Keys.MSG_TYPE.MSG_TYPE_10.getIndex()));
			entity = getFacade().getMsgService().getMsg(entity);
			super.copyProperties(form, entity);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(entity.getUser_id());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			request.setAttribute("real_name", userInfo.getReal_name());

		}
		return mapping.findForward("view");
	}
}
