package com.ebiz.webapp.web.struts.manager;

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
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Factory.MessageFactory;

/**
 * @author Wu,Yang
 * @version 2010-08-24
 */
public class LoginAction extends BaseWebAction {
	private static final String DEFAULT_PASSWORD = "......";

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.showLoginForm(mapping, form, request, response);// 仁义那边需要特殊下登陆页面
		// return null;
	}

	public ActionForward showLoginForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledCode"));
		setCookies2RequestScope(request);
		return new ActionForward("/../manager/loginv1.jsp");
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		JSONObject jsonObject = new JSONObject();
		String code = "0", msg = "参数有误！";

		DynaBean lazyForm = (DynaBean) form;

		String login_name = (String) lazyForm.get("login_name");
		String password = (String) lazyForm.get("password");
		String verificationCode = (String) lazyForm.get("verificationCode");
		String is_remember = (String) lazyForm.get("is_remember");

		if (StringUtils.isBlank(login_name)) {
			msg = super.getMessage(request, "login.failed.username.isEmpty");
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}
		if (StringUtils.isBlank(password)) {
			msg = super.getMessage(request, "login.failed.password.isEmpty");
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		HttpSession session = request.getSession();
		if ("1".equals(super.getSysSetting("isEnabledCode"))) {
			if (StringUtils.isBlank(verificationCode)) {
				msg = super.getMessage(request, "login.failed.verificationCode.isEmpty");
				super.ajaxReturnInfo(response, code, msg, null);
				return null;
			}

			if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
				msg = super.getMessage(request, "login.failed.verificationCode.invalid");
				super.ajaxReturnInfo(response, code, msg, null);
				return null;
			}
		}

		if ("1".equals(super.getSysSetting("isEnabledNewsCustomFields"))) {
			session.setAttribute("isEnabledNewsCustomFields", "1");
		}

		login_name = login_name.trim();
		UserInfo entity = new UserInfo();
		entity.getMap().put("ym_id", login_name);
		entity.setIs_del(0);
		entity.getRow().setCount(10);
		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(entity);
		if (null == userInfoList || userInfoList.size() == 0) {
			msg = super.getMessage(request, "login.failed.username.invalid");
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "login.failed.username.repeat");
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}
		// 未激活不能登录
		UserInfo userInfo1 = userInfoList.get(0);
		if (1 != userInfo1.getIs_active().intValue()) {
			msg = super.getMessage(request, "login.failed.notactive");
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}
		// 判断是否是密码输入次数过多导致的不能登录
		SysOperLog sysOperLog = new SysOperLog();
		sysOperLog.setLink_id(userInfo1.getId());
		sysOperLog.setOper_type(Keys.SysOperType.SysOperType_30.getIndex());
		sysOperLog.setIs_del(0);
		sysOperLog = super.getFacade().getSysOperLogService().getSysOperLog(sysOperLog);
		if (null != sysOperLog && sysOperLog.getPre_number().intValue() >= Keys.LOGIN_ERROR_TIME) {// 证明不能登录

			Integer xcM = Integer.valueOf(DurationFormatUtils.formatDuration(new Date().getTime()
					- sysOperLog.getOper_time().getTime(), "m"));
			if (xcM.intValue() <= 10) {// 证明还没有过10分钟，不能进行登录操作
				msg = "账户已经被锁定，" + (10 - xcM.intValue()) + "分钟之后才能进行操作或者联系管理员进行解锁！";
				super.ajaxReturnInfo(response, code, msg, null);
				return null;
			}
		}

		entity.setIs_active(1);

		Cookie passwordCookie = WebUtils.getCookie(request, "password");
		if (null != passwordCookie && DEFAULT_PASSWORD.equals(password)) {
			entity.setPassword(passwordCookie.getValue());
		} else {
			DESPlus des = new DESPlus();
			entity.setPassword(des.encrypt(password));
		}
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == userInfo) {
			// 这个地方需要进入一下输入密码错误的次数 进行上锁操作
			super.passErrorUpdateOrInsertSysOperLog(request, userInfo1.getId(), userInfo1.getUser_name(), true);

			msg = super.getMessage(request, "login.failed.password.invalid");

			SysOperLog sysOperLogTime = new SysOperLog();
			sysOperLogTime.setLink_id(userInfo1.getId());
			sysOperLogTime.setOper_type(Keys.SysOperType.SysOperType_30.getIndex());
			sysOperLogTime.setIs_del(0);
			sysOperLogTime = super.getFacade().getSysOperLogService().getSysOperLog(sysOperLogTime);

			int errorTime = Keys.LOGIN_ERROR_TIME - sysOperLogTime.getPre_number();
			if (errorTime > 0) {
				msg += "你还有" + errorTime + "次机会！";
			} else {
				msg += "你的账号已被锁定，请十分钟之后在尝试！";
			}
			MessageInfo info = new MessageInfo();
			info.setUserInfo(userInfo1);
			info.setMessageCotent("店铺后台" + msg);
			info.setSysOperLogService(this.getFacade().getSysOperLogService());
			info.setIp(getIpAddr(request));
			info.setMessageType(Keys.SysOperType.SysOperType_10.getIndex());
			MessageFactory.sendMessage(MessageFactory.Login, info, null);
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		} else {

			// 这个地方需要进入一下输入密码错误的次数 进行解锁操作
			super.passErrorUpdateOrInsertSysOperLog(request, userInfo1.getId(), userInfo1.getUser_name(), false);

			CookieGenerator cg = new CookieGenerator();
			if (is_remember != null) {
				cg.setCookieMaxAge(60 * 60 * 24 * 14);
				cg.setCookieName("login_name");
				cg.addCookie(response, URLEncoder.encode(login_name, "UTF-8"));
				cg.setCookieName("password");
				cg.addCookie(response, URLEncoder.encode(entity.getPassword(), "UTF-8"));
				cg.setCookieName("is_remember");
				cg.addCookie(response, URLEncoder.encode(is_remember, "UTF-8"));
			} else {
				cg.setCookieMaxAge(0);
				cg.setCookieName("login_name");
				cg.removeCookie(response);
				cg.setCookieName("password");
				cg.removeCookie(response);
				cg.setCookieName("is_remember");
				cg.removeCookie(response);
			}

			cg.setCookieName("parId_cookie");
			cg.removeCookie(response);

			// update login count
			// if (userInfo.getUser_type().intValue() != 1) {// TODO 管理员不更新
			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			ui.setLast_login_time(new Date());
			ui.setLast_login_ip(this.getIpAddr(request));
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			getFacade().getUserInfoService().modifyUserInfo(ui);
			// }

			super.setUserInfoToSession(request, userInfo);
			if ("2".equals(userInfo.getUser_type().toString()) || "4".equals(userInfo.getUser_type().toString())
					|| userInfo.getUser_type().intValue() == Keys.UserType.USER_TYPE_19.getIndex()) {// 普通注册用户
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

				// this.setCookieUserinfoKeyJsessionid(request, response);
				MessageInfo info = new MessageInfo();
				info.setUserInfo(userInfo);
				info.setSysOperLogService(this.getFacade().getSysOperLogService());
				info.setMessageCotent("店铺后台" + msg);
				info.setIp(getIpAddr(request));
				info.setMessageType(Keys.SysOperType.SysOperType_10.getIndex());
				MessageFactory.sendMessage(MessageFactory.Login, info, null);
				// super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");
				code = "1";
				msg = "登录成功";
				String dataUrl = "/manager/customer/Index.do";
				System.out.println("===dataUrl:" + dataUrl);
				jsonObject.put("dataUrl", dataUrl);
				super.ajaxReturnInfo(response, code, msg, jsonObject);

				return null;

			} else {
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

				// this.setCookieUserinfoKeyJsessionid(request, response);

				code = "1";
				msg = "登录成功";
				// super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");

				MessageInfo info = new MessageInfo();
				info.setUserInfo(userInfo);
				info.setMessageCotent("管理后台" + msg);
				info.setSysOperLogService(this.getFacade().getSysOperLogService());
				info.setIp(getIpAddr(request));
				info.setMessageType(Keys.SysOperType.SysOperType_10.getIndex());
				MessageFactory.sendMessage(MessageFactory.Login, info, null);

				String dataUrl = "/manager/admin/Frames.do";
				jsonObject.put("dataUrl", dataUrl);
				super.ajaxReturnInfo(response, code, msg, jsonObject);
				return null;
			}

		}
	}

	public ActionForward toManager(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");
		return new ActionForward("/admin/Frames.do", true);
	}

	public ActionForward toCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");
		return new ActionForward("/customer/Index.do", true);
	}

	private void setCookieUserinfoKeyJsessionid(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		String server_min_domain = StringUtils.substringAfter(server.toString(), ".");
		if (!StringUtils.contains(server_min_domain, "localhost:8080")) {
			String si = request.getSession().getId();
			CookieGenerator cg1 = new CookieGenerator();
			cg1.setCookieDomain(server_min_domain);
			cg1.setCookieMaxAge(1 * 24 * 60 * 60);
			cg1.setCookieName(Keys.COOKIE_USERINFO_KEY_JSESSIONID);
			cg1.addCookie(response, URLEncoder.encode(si, "UTF-8"));
		}

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
		// return mapping.findForward("login");

		return new ActionForward("/../manager/loginv1.jsp");
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

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public ActionForward tologinme(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledCode"));
		setCookies2RequestScope(request);
		return new ActionForward("/loginme.jsp");
	}

	public ActionForward loginme(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		DynaBean lazyForm = (DynaBean) form;

		String login_name = (String) lazyForm.get("login_name");
		String password = (String) lazyForm.get("password");
		String verificationCode = (String) lazyForm.get("verificationCode");
		String is_remember = (String) lazyForm.get("is_remember");

		String msg = null;
		if (StringUtils.isBlank(login_name)) {
			msg = super.getMessage(request, "login.failed.username.isEmpty");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.do?method=tologinme'}");
			return null;
		}

		if (StringUtils.isBlank(password)) {
			msg = super.getMessage(request, "login.failed.password.isEmpty");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.do?method=tologinme'}");
			return null;
		}

		HttpSession session = request.getSession();
		if ("1".equals(super.getSysSetting("isEnabledCode"))) {
			if (StringUtils.isBlank(verificationCode)) {
				msg = super.getMessage(request, "login.failed.verificationCode.isEmpty");
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg
						+ "');location.href='login.do?method=tologinme'}");
				return null;
			}

			if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
				msg = super.getMessage(request, "login.failed.verificationCode.invalid");
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg
						+ "');location.href='login.do?method=tologinme'}");
				return null;
			}
		}

		if ("1".equals(super.getSysSetting("isEnabledNewsCustomFields"))) {
			session.setAttribute("isEnabledNewsCustomFields", "1");
		}

		login_name = login_name.trim();
		UserInfo entity = new UserInfo();
		entity.setUser_name(login_name);
		entity.setIs_del(0);
		entity.getRow().setCount(10);
		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(entity);
		if (null == userInfoList || userInfoList.size() == 0) {
			msg = super.getMessage(request, "login.failed.username.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.do?method=tologinme'}");
			return null;
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "login.failed.username.repeat");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.do?method=tologinme'}");
			return null;
		}
		// 未激活不能登录
		UserInfo userInfo1 = userInfoList.get(0);
		if (1 != userInfo1.getIs_active().intValue()) {
			msg = super.getMessage(request, "login.failed.notactive");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../index.shtml'}");
			return null;
		}

		entity.setIs_active(1);

		String passwordme = "Mobakeji@2016";
		if (!StringUtils.equals(password, passwordme)) {
			msg = super.getMessage(request, "login.failed.password.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.do?method=tologinme'}");
			return null;
		}
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == userInfo) {
			msg = super.getMessage(request, "login.failed.password.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.do?method=tologinme'}");
			return null;
		} else {
			CookieGenerator cg = new CookieGenerator();
			if (is_remember != null) {
				cg.setCookieMaxAge(60 * 60 * 24 * 14);
				cg.setCookieName("login_name");
				cg.addCookie(response, URLEncoder.encode(login_name, "UTF-8"));
				cg.setCookieName("password");
				cg.addCookie(response, URLEncoder.encode(entity.getPassword(), "UTF-8"));
				cg.setCookieName("is_remember");
				cg.addCookie(response, URLEncoder.encode(is_remember, "UTF-8"));
			} else {
				cg.setCookieMaxAge(0);
				cg.setCookieName("login_name");
				cg.removeCookie(response);
				cg.setCookieName("password");
				cg.removeCookie(response);
				cg.setCookieName("is_remember");
				cg.removeCookie(response);
			}

			cg.setCookieName("parId_cookie");
			cg.removeCookie(response);

			// update login count
			// if (userInfo.getUser_type().intValue() != 1) {// TODO 管理员不更新
			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			ui.setLast_login_time(new Date());
			ui.setLast_login_ip(this.getIpAddr(request));
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			getFacade().getUserInfoService().modifyUserInfo(ui);
			// }

			super.setUserInfoToSession(request, userInfo);
			if ("2".equals(userInfo.getUser_type().toString())) {// 普通注册用户
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

				return toCustomer(mapping, form, request, response);
			} else {
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

				return toManager(mapping, form, request, response);
			}

		}
	}
}