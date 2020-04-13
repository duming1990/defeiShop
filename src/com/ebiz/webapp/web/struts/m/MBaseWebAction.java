package com.ebiz.webapp.web.struts.m;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;

import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BasePayAction;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class MBaseWebAction extends BasePayAction {

	public void setTitle(HttpServletRequest request, String title) {
		request.setAttribute("header_title", title);
	}

	public ActionForward showTipNotLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String msg) throws Exception {
		String refererUrl = request.getHeader("Referer");
		logger.info("==refererUrl:{}", refererUrl);

		request.setAttribute("header_title", "系统提示");
		String tip_msg = msg;
		request.setAttribute("tip_msg", tip_msg);
		request.setAttribute("tip_url", "MIndexLogin.do");

		return new ActionForward("/../m/Tip/index.jsp");
	}

	public ActionForward showTipMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String msg) throws Exception {
		String refererUrl = request.getHeader("Referer");
		logger.info("==refererUrl:{}", refererUrl);

		request.setAttribute("header_title", "系统提示");
		String tip_msg = msg;
		request.setAttribute("tip_msg", tip_msg);
		request.setAttribute("tip_url", refererUrl);

		return new ActionForward("/../m/Tip/index.jsp");
	}

	public ActionForward showTipMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String msg, String toGoUrl) throws Exception {

		request.setAttribute("header_title", "系统提示");
		String tip_msg = msg;
		request.setAttribute("tip_msg", tip_msg);
		request.setAttribute("tip_url", toGoUrl);

		return new ActionForward("/../m/Tip/index.jsp");
	}

	public ActionForward goHome(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String url = super.getCtxPath(request) + "/m/MMyHome.do";
		return new ActionForward(url, true);
	}

	public void setIsAppToCookie(HttpServletRequest request, HttpServletResponse response) {
		CookieGenerator isApp = new CookieGenerator();
		isApp.setCookieMaxAge(7 * 24 * 60 * 60);
		isApp.setCookieName(Keys.IS_APP);
		try {
			isApp.addCookie(response, URLEncoder.encode("true", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("==setisApp:" + e.getMessage());
		}
	}

	// 实例化公共信息 ，取得企业信息
	public EntpInfo initPublic(ActionMapping mapping, ActionForm form, HttpServletRequest request, String entp_id,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(entp_id)) {
			String msg = "对不起，您输入的链接地址不存在";
			super.renderJavaScript(response, "alert('" + msg + "');window.close();");
			return null;
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setId(Integer.valueOf(entp_id));
		entpInfo.getMap().put("audit_state_in", "1,2");
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (entpInfo == null) {
			String msg = "企业不存在";
			super.renderJavaScript(response, "alert('" + msg + "');window.close();");
			return null;
		}
		request.setAttribute("entpInfo", entpInfo);

		return entpInfo;
	}
}
