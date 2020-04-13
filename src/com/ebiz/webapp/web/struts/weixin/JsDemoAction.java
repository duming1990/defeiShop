package com.ebiz.webapp.web.struts.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.web.struts.m.MBaseWebAction;

/**
 * @author Wu,Yang
 * @version 2011-11-23
 */
public class JsDemoAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setJsApiTicketRetrunParamToSession(request);
		return mapping.findForward("input");
	}

}
