package com.ebiz.webapp.web.struts;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wu,Yang
 * @version 2011-11-23
 */
public class IndexTimeoutAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String return_url = (String) dynaBean.get("return_url");
		logger.info("==IndexTimeoutAction1 return_url:{}", return_url);
		if (StringUtils.isNotBlank(return_url)) {
			return_url = URLEncoder.encode(return_url, "UTF-8");
		} else {
			return_url = "";
		}
		String ctx = super.getCtxPath(request);
		String real_url = ctx + "/login.shtml?return_url=" + return_url;

		if (super.isWeixin(request)) {
			real_url = ctx + "/weixin/loginweixin.html?return_url=" + return_url;
			// String ip = this.getIpAddr(request);
			// logger.info("==ip:{}", ip);
			// if (StringUtils.startsWith("localhost", ip) || StringUtils.startsWith(ip, "192.168.3.")
			// || StringUtils.startsWith(ip, "192.168.1.")) {
			// real_url = "/weixin/login.html?return_url=" + return_url;
			// }
		}

		// return new ActionForward(real_url, true);top.location.href = "${url}";
		super.renderJavaScript(response, "window.onload=function(){top.location.href='" + real_url + "';}");
		return null;
	}
}
