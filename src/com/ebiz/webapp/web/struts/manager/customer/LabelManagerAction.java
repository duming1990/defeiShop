package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpCommClass;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author 戴诗学
 * @version 2018-05-17
 */

public class LabelManagerAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String class_name = (String) dynaBean.get("class_name");

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfo.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "商家不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		EntpCommClass entity = new EntpCommClass();
		entity.setEntp_id(entpInfo.getId());
		super.copyProperties(entity, form);
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		Integer recordCount = getFacade().getEntpCommClassService().getEntpCommClassCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpCommClass> entityList = super.getFacade().getEntpCommClassService()
				.getEntpCommClassPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			request.setAttribute("entityList", entityList);
		}
		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		UserInfo userInfoTemp = super.getUserInfo(userInfo.getId());

		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		getsonSysModuleList(request, dynaBean);
		super.getSessionId(request);

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfoTemp.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "商家不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		EntpCommClass entity = new EntpCommClass();
		entity.setEntp_id(entpInfo.getId());
		entity.setIs_del(0);
		Integer count = super.getFacade().getEntpCommClassService().getEntpCommClassCount(entity);
		if (count >= Integer.valueOf(super.getSysSetting(Keys.labelManager))) {
			String msg = "维护标签过多，请修改或删除不需要的标签！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		dynaBean.set("own_entp_id", entpInfo.getId());
		dynaBean.set("yhq_start_date", new Date());
		dynaBean.set("yhq_end_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		UserInfo userInfoTemp = super.getUserInfo(ui.getId());
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfoTemp.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		EntpCommClass entity = new EntpCommClass();
		super.copyProperties(entity, form);
		if (StringUtils.isEmpty(id)) {
			entity.setEntp_id(entpInfo.getId());
			entity.setIs_del(0);
			super.getFacade().getEntpCommClassService().createEntpCommClass(entity);
			saveMessage(request, "entity.inerted");
		} else {
			entity.setEntp_id(entpInfo.getId());
			super.getFacade().getEntpCommClassService().modifyEntpCommClass(entity);
			saveMessage(request, "entity.updated");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		EntpCommClass entity = new EntpCommClass();
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setIs_del(1);
			super.getFacade().getEntpCommClassService().modifyEntpCommClass(entity);
			saveMessage(request, "entity.deleted");
		} else if (ArrayUtils.isNotEmpty(pks)) {
			for (String entity_id : pks) {
				entity.setId(Integer.valueOf(entity_id));
				entity.setIs_del(1);
				super.getFacade().getEntpCommClassService().modifyEntpCommClass(entity);
			}
			saveMessage(request, "entity.deleted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;

		saveToken(request);
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}

		super.getSessionId(request);
		EntpCommClass entity = new EntpCommClass();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getEntpCommClassService().getEntpCommClass(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		super.copyProperties(form, entity);
		return mapping.findForward("input");
	}

}