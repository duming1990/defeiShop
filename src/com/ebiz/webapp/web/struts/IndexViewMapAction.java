package com.ebiz.webapp.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.EntpInfo;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class IndexViewMapAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/IndexViewMap/viewMap.jsp");
	}

	public ActionForward indexForViewAndCanClick(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/IndexViewMap/indexForViewAndCanClick.jsp");
	}

	public ActionForward viewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean db = (DynaBean) form;
		String entp_id = (String) db.get("entp_id");
		if (StringUtils.isBlank(entp_id)) {
			String msg = "参数有误，禁止调用！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		EntpInfo entpInfo = super.getEntpInfo(Integer.valueOf(entp_id));

		if (null == entpInfo) {
			String msg = "未查询到该企业！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, entpInfo);

		return new ActionForward("/index/IndexViewMap/viewDetails.jsp");
	}

}
