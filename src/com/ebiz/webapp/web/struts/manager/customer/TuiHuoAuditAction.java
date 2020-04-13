package com.ebiz.webapp.web.struts.manager.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Liyuan
 * @version 2013-04-02
 */
public class TuiHuoAuditAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

		super.setTuihuoAudit(form, request, ui, dynaBean);

		return mapping.findForward("list");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = "id为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		if (GenericValidator.isLong(id)) {
			saveToken(request);

			int i = viewOrderReturnInfo(mapping, form, request, response, id);
			if (i == -1) {
				String msg = "订单不存在。";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back(-1);}");
				return null;
			}

			return new ActionForward("/../manager/customer/TuiHuoAudit/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) { return super
				.checkPopedomInvalid(request, response); }
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			int i = viewOrderReturnInfo(mapping, form, request, response, id);
			if (i == -1) {
				String msg = "订单不存在。";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back(-1);}");
				return null;
			}
		}
		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) { return list(mapping, form, request, response); }
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}

		resetToken(request);

		return saveTuiHuoAudit(mapping, form, request, response);
	}

}
