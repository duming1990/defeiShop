package com.ebiz.webapp.web.struts.manager.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 * @version 2010-09-26
 */
public class MyQianBaoAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		DynaBean dynaBean = (DynaBean) form;
		if (null == userInfo) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		getsonSysModuleList(request, dynaBean);

		request.setAttribute("userInfo", userInfo);

		return mapping.findForward("list");
	}
}
