package com.ebiz.webapp.web.struts.manager.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.EntpInfo;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class EntpCountAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		EntpInfo entpInfo;
		entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		Integer swEntp = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		request.setAttribute("swEntp", swEntp);
		// 农畜企业
		// entpInfo = new EntpInfo();
		// entpInfo.setIs_del(0);
		// entpInfo.setIs_nx_entp(1);
		// Integer ncEntp = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		// request.setAttribute("ncEntp", ncEntp);
		// 明星企业
		// entpInfo = new EntpInfo();
		// entpInfo.setIs_del(0);
		// entpInfo.setIs_mx_entp(1);
		// Integer mxEntp = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		// request.setAttribute("mxEntp", mxEntp);
		// 诚信企业
		entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		Integer cxEntp = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		request.setAttribute("cxEntp", cxEntp);
		// 失信企业
		entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		Integer sxEntp = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		request.setAttribute("sxEntp", sxEntp);
		return mapping.findForward("list");
	}
}
