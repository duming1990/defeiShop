package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author Wu,Yang
 * @version 2011-04-22
 */
public class HomeHdAction extends BaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<BaseLink> base20LinkList = this.getBaseLinkList(20, 9, null);
		request.setAttribute("base20LinkList", base20LinkList);

		List<BaseLink> base70LinkList = this.getBaseLinkList(70, 9, null);
		request.setAttribute("base70LinkList", base70LinkList);

		// 取最上面的导航条
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, 6, null);
		request.setAttribute("base3000LinkList", base3000LinkList);

		SysSetting sysSetting = new SysSetting();
		sysSetting.setTitle("isEnabledIndexStatic");
		sysSetting = super.getFacade().getSysSettingService().getSysSetting(sysSetting);
		request.setAttribute("sysSetting", sysSetting);

		request.setAttribute("staticIndex", "true");

		return new ActionForward("/admin/HomeHd/index.jsp");
	}
}
