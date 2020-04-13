package com.ebiz.webapp.web.struts.m;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class MChangeEntpAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return changeSchool(mapping, form, request, response);
	}

	public ActionForward changeSchool(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "选择超市");

		request.setAttribute("titleSideName", "地区");

		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		} else {
			// return super.toAppDownload(mapping, form, request, response);
		}

		return new ActionForward("/MChangeEntp/list.jsp");
	}
}
