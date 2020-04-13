package com.ebiz.webapp.web.struts.alipay;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alipay.AliPayUrl;
import com.alipay.m.config.AlipayConfig;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.struts.BasePayAction;

public class AlipayLoginAction extends BasePayAction {

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		UserInfo ui = super.getUserInfoFromSession(request);
		String return_url = request.getParameter("return_url");// 返回URL
		logger.warn("==weixin return_url:{}", return_url);
		if (null != ui && StringUtils.isNotBlank(return_url)) {// 如果session存在，并且return_url不为空，直接跳转到return_url
			response.sendRedirect(return_url);
			return null;
		}

		StringBuilder link = new StringBuilder();
		String scope = "auth_user";
		String state = "";

		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		request.setAttribute("server_domain", server.toString());
		String redirectUri = "http://".concat(server.toString()).concat("/alipay/AlipayLogin.do?method=afterLogin");

		if (StringUtils.isNotBlank(return_url)) {
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
		}
		link.append(AliPayUrl.OPENAUTH + "?app_id=" + AlipayConfig.app_id)
				.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&scope=" + scope)
				.append("&state=" + state);

		response.sendRedirect(link.toString());

		logger.warn("==alipay sendRedirect :{}", link.toString());

		return null;
	}

}
