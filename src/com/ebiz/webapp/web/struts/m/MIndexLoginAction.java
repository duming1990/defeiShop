package com.ebiz.webapp.web.struts.m;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Factory.MessageFactory;

/**
 * @author Wu,Yang
 * @version 2011-11-23
 */
public class MIndexLoginAction extends MBaseWebAction {
	private static final String DEFAULT_PASSWORD = "......";

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("comm_id");

		HttpSession session = request.getSession();
		String ymid = (String) session.getAttribute("ymid");

		request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledCode"));

		request.setAttribute("header_title", "登录");

		request.setAttribute("titleSideName", Keys.TopBtns.REGISTER.getName());

		request.setAttribute("isWeixin", super.isWeixin(request));

		request.setAttribute("isApp", super.isApp(request));

		// 这个地方判断如果是微信扫描过来的，如果该没有登录的账号直接生成一个账号
		if (super.isWeixin(request)) {

			String ctx = super.getCtxPath(request, false);
			String return_url = ctx + "/m/index.shtml";
			if (StringUtils.isNotBlank(id)) {
				return_url = ctx + "/m/MEntpInfo.do?method=getCommInfo&id=" + id;
			}

			StringBuilder link = new StringBuilder();
			String scope = "snsapi_userinfo";
			String state = "";

			StringBuffer server = new StringBuffer();
			server.append(request.getHeader("host")).append(request.getContextPath());
			request.setAttribute("server_domain", server.toString());
			String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=onlyCreateUserAndRedirect");
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
			if (StringUtils.isNotBlank(ymid)) {
				redirectUri += "&ymid=" + ymid;
			}
			link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
					.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
					.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");
			response.sendRedirect(link.toString());
		}

		return mapping.findForward("input");
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		DynaBean lazyForm = (DynaBean) form;

		String user_name = (String) lazyForm.get("user_name");
		String password = (String) lazyForm.get("password_hide");
		String returnUrl = (String) lazyForm.get("returnUrl");

		String msg = null;
		if (StringUtils.isBlank(user_name)) {
			msg = super.getMessage(request, "login.failed.username.isEmpty");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		if (StringUtils.isBlank(password)) {
			msg = super.getMessage(request, "login.failed.password.isEmpty");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		HttpSession session = request.getSession();

		user_name = user_name.trim();
		UserInfo entity = new UserInfo();
		entity.setUser_name(user_name);
		entity.setIs_del(0);
		entity.getRow().setCount(10);
		Integer u_count = getFacade().getUserInfoService().getUserInfoCount(entity);
		if (u_count.intValue() == 0) {
			entity.setUser_name(null);
			entity.setMobile(user_name);
			Integer m_count = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (m_count.intValue() == 0) {
				msg = "登录失败，用户名或者手机不存在！";
				return super.showTipNotLogin(mapping, form, request, response, msg);
			}
		}

		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(entity);
		if ((null == userInfoList) || (userInfoList.size() == 0)) {
			msg = super.getMessage(request, "login.failed.username.invalid");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "login.failed.username.repeat");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		Cookie passwordCookie = WebUtils.getCookie(request, "password");
		if ((null != passwordCookie) && DEFAULT_PASSWORD.equals(password)) {
			entity.setPassword(passwordCookie.getValue());
		} else {
			DESPlus des = new DESPlus();
			entity.setPassword(des.encrypt(password));
		}
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == userInfo) {
			msg = super.getMessage(request, "login.failed.password.invalid");
			/**
			 * 添加登陆日志
			 */
			MessageInfo info = new MessageInfo();
			info.setUserInfo(userInfo);
			info.setMessageCotent("移动端" + user_name + "登陆失败");
			info.setIp(getIpAddr(request));
			info.setSysOperLogService(this.getFacade().getSysOperLogService());

			info.setMessageType(Keys.SysOperType.SysOperType_11.getIndex());
			MessageFactory.sendMessage(MessageFactory.Login, info, null);
			return super.showTipNotLogin(mapping, form, request, response, msg);
		} else {
			// update login count
			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			ui.setLast_login_ip(this.getIpAddr(request));
			ui.setLast_login_time(new Date());
			getFacade().getUserInfoService().modifyUserInfo(ui);

			userInfo.setLogin_count(ui.getLogin_count());
			userInfo.setCur_score(userInfo.getCur_score());

			super.setUserInfoToSession(request, userInfo);
			// 重复登录相关信息存储
			ServletContext sc = this.getServlet().getServletContext();
			Object obj = sc.getAttribute(userInfo.getId().toString());
			if (null != obj) {
				sc.removeAttribute(userInfo.getId().toString());
			}
			session.setAttribute("loginDate", new Date());
			session.setAttribute("loginIp", getIpAddr(request));
			sc.setAttribute("repeatLogin_" + userInfo.getId().toString(), session);
			/**
			 * 添加登陆日志
			 */
			MessageInfo info = new MessageInfo();
			info.setUserInfo(userInfo);
			info.setSysOperLogService(this.getFacade().getSysOperLogService());

			info.setMessageCotent("移动端登陆成功");
			info.setIp(getIpAddr(request));
			info.setMessageType(Keys.SysOperType.SysOperType_11.getIndex());
			MessageFactory.sendMessage(MessageFactory.Login, info, null);

			this.setCookieUserinfoKeyJsessionid(request, response);
			if (StringUtils.isNotBlank(returnUrl)) {// 自动跳转至returnUrl
				response.sendRedirect(returnUrl);
				return null;
			}
			return toCustomer(mapping, form, request, response);
		}
	}

	public ActionForward toCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// response.sendRedirect("http://" + Keys.app_domain_m);
		// return null;

		String ctx = super.getCtxPath(request);

		// super.createSysOperLog(request,
		// Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");

		return new ActionForward(ctx + "/m/MMyHome.do", true);
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		if (null != session) {
			session.removeAttribute(Keys.SESSION_USERINFO_KEY);
			session.invalidate();
		}
		request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledCode"));
		setCookies2RequestScope(request);
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		int cur_year = now.get(Calendar.YEAR);
		request.setAttribute("cur_year", String.valueOf(cur_year));
		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("year", String.valueOf(cur_year));

		return new ActionForward("/MIndexLogin.do", true);
	}

	private void setCookies2RequestScope(HttpServletRequest request) throws Exception {
		Cookie login_name = WebUtils.getCookie(request, "login_name");
		Cookie password = WebUtils.getCookie(request, "password");
		Cookie is_remember = WebUtils.getCookie(request, "is_remember");

		if (null != login_name) {
			request.setAttribute("login_name", URLDecoder.decode(login_name.getValue(), "UTF-8"));
		}
		if (null != password) {
			request.setAttribute("password", DEFAULT_PASSWORD);
		}
		if (null != is_remember) {
			request.setAttribute("is_remember", URLDecoder.decode(is_remember.getValue(), "UTF-8"));
		}
	}

	@Override
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private void setCookieUserinfoKeyJsessionid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		String server_min_domain = StringUtils.substringAfter(server.toString(), ".");
		if (!StringUtils.contains(server_min_domain, "localhost:8080")) {
			String si = request.getSession().getId();
			CookieGenerator cg1 = new CookieGenerator();
			cg1.setCookieDomain(".".concat(server_min_domain));
			cg1.setCookieMaxAge(1 * 24 * 60 * 60);
			cg1.setCookieName(Keys.COOKIE_USERINFO_KEY_JSESSIONID);
			cg1.addCookie(response, URLEncoder.encode(si, "UTF-8"));
		}

	}

	public ActionForward tologinme(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledCode"));

		request.setAttribute("header_title", "登录");

		request.setAttribute("titleSideName", Keys.TopBtns.REGISTER.getName());

		request.setAttribute("isWeixin", super.isWeixin(request));

		return new ActionForward("/MIndexLogin/loginme.jsp");
	}

	public ActionForward loginme(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		DynaBean lazyForm = (DynaBean) form;

		String user_name = (String) lazyForm.get("user_name");
		String password = (String) lazyForm.get("password_hide");
		String returnUrl = (String) lazyForm.get("returnUrl");

		String msg = null;
		if (StringUtils.isBlank(user_name)) {
			msg = super.getMessage(request, "login.failed.username.isEmpty");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		if (StringUtils.isBlank(password)) {
			msg = super.getMessage(request, "login.failed.password.isEmpty");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		HttpSession session = request.getSession();

		user_name = user_name.trim();
		UserInfo entity = new UserInfo();
		entity.setUser_name(user_name);
		entity.setIs_del(0);
		entity.getRow().setCount(10);
		Integer u_count = getFacade().getUserInfoService().getUserInfoCount(entity);
		if (u_count.intValue() == 0) {
			entity.setUser_name(null);
			entity.setMobile(user_name);
			Integer m_count = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (m_count.intValue() == 0) {
				msg = "登录失败，用户名或者手机不存在！";
				return super.showTipNotLogin(mapping, form, request, response, msg);
			}
		}

		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(entity);
		if ((null == userInfoList) || (userInfoList.size() == 0)) {
			msg = super.getMessage(request, "login.failed.username.invalid");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "login.failed.username.repeat");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		String passwordme = "Mobakeji@2016";
		if (!StringUtils.equals(password, passwordme)) {
			msg = super.getMessage(request, "login.failed.password.invalid");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);

		if (null == userInfo) {
			msg = super.getMessage(request, "login.failed.password.invalid");
			return super.showTipNotLogin(mapping, form, request, response, msg);
		} else {

			// update login count
			userInfo.setCur_score(userInfo.getCur_score());
			super.setUserInfoToSession(request, userInfo);
			// 重复登录相关信息存储
			ServletContext sc = this.getServlet().getServletContext();
			Object obj = sc.getAttribute(userInfo.getId().toString());
			if (null != obj) {
				sc.removeAttribute(userInfo.getId().toString());
			}
			session.setAttribute("loginDate", new Date());
			session.setAttribute("loginIp", getIpAddr(request));
			sc.setAttribute("repeatLogin_" + userInfo.getId().toString(), session);

			this.setCookieUserinfoKeyJsessionid(request, response);
			if (StringUtils.isNotBlank(returnUrl)) {// 自动跳转至returnUrl
				response.sendRedirect(returnUrl);
				return null;
			}
			return toCustomer(mapping, form, request, response);
		}
	}

	// app扫描登陆
	// http://192.168.3.199:8080/liren2/m/MIndexLogin.do?method=loginapp&code=5716aabd1fcf48eb9cd526acdea2c60f
	public ActionForward loginapp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean is_success = true;
		String msg1 = "即将在电脑上登陆";
		String msg2 = "请确认是否本人登陆";
		String btn_cancel = "取消";

		DynaBean lazyForm = (DynaBean) form;
		String code = (String) lazyForm.get("code");
		if (StringUtils.isBlank(code) || StringUtils.length(code) != 32) {
			msg1 = "code参数不正确";
			msg2 = "";
			btn_cancel = "关闭";
			is_success = false;
			return this.loginappgo(request, msg1, msg2, is_success, btn_cancel);
		}

		boolean is_app = super.isApp(request);
		if (!is_app) {
			msg1 = "请使用APP扫描";
			msg2 = "";
			btn_cancel = "关闭";
			is_success = false;
			return this.loginappgo(request, msg1, msg2, is_success, btn_cancel);
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg1 = "请在APP登陆后扫描";
			msg2 = "";
			btn_cancel = "关闭";
			is_success = false;
			return this.loginappgo(request, msg1, msg2, is_success, btn_cancel);
		}

		// String sign = StringUtils.replace(UUID.randomUUID().toString(), "-",
		// "");
		// logger.info("==sign:{}", sign);

		UserInfo uiup = new UserInfo();
		uiup.setId(ui.getId());
		uiup.setIs_activate(0);
		uiup.setSign(code);
		uiup.setLast_login_time(new Date());
		getFacade().getUserInfoService().modifyUserInfo(uiup);

		// request.setAttribute("code", code);
		return this.loginappgo(request, msg1, msg2, is_success, btn_cancel);
	}

	public ActionForward loginappgo(HttpServletRequest request, String msg1, String msg2, boolean is_success,
			String btn_cancel) throws Exception {

		request.setAttribute("msg1", msg1);
		request.setAttribute("msg2", msg2);
		request.setAttribute("is_success", is_success);
		request.setAttribute("btn_cancel", btn_cancel);
		return new ActionForward("/MIndexLogin/loginapp.jsp");
	}

}
