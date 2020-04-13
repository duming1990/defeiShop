package com.ebiz.webapp.web.struts;

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

import weibo4j.Account;
import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.IdGen;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

/**
 * @author Wu,Yang
 * @version 2011-11-23
 */
public class IndexLoginAction extends BaseWebAction {
	private static final String DEFAULT_PASSWORD = "......";

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean lazyForm = (DynaBean) form;

		// String version = (String) lazyForm.get("version");

		String isEnabledAppScanLogin = super.getSysSetting("isEnabledAppScanLogin");
		// request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledAppScanLogin"));

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		this.setCookies2RequestScope(request);

		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setType(Keys.BaseFilesType.Base_Files_TYPE_30.getIndex());
		baseFiles.setIs_del(0);
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		request.setAttribute("baseFiles", baseFiles);

		if (StringUtils.equals(isEnabledAppScanLogin, "1")) {
			String ctx = super.getCtxPath(request);

			String uuid = IdGen.uuid();

			String code_url = ctx + Keys.app_scan_login_url;
			code_url = StringUtils.replace(code_url, "{0}", uuid);

			request.setAttribute("code_url", URLEncoder.encode(code_url, "utf-8"));
			request.setAttribute("code", uuid);

			List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
			if (null != base0LinkList && base0LinkList.size() > 0) {
				request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
			}
			return new ActionForward("/index/IndexLogin/formv2.jsp");
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
		String password = (String) lazyForm.get("password");
		String verificationCode = (String) lazyForm.get("verificationCode");
		String is_remember = (String) lazyForm.get("is_remember");
		String returnUrl = (String) lazyForm.get("returnUrl");

		String msg = null;
		if (StringUtils.isBlank(user_name)) {
			msg = super.getMessage(request, "login.failed.username.isEmpty");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (StringUtils.isBlank(password)) {
			msg = super.getMessage(request, "login.failed.password.isEmpty");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession();
		// if ("1".equals(super.getSysSetting("isEnabledCode"))) {
		if (StringUtils.isBlank(verificationCode)) {
			msg = super.getMessage(request, "login.failed.verificationCode.isEmpty");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
			msg = super.getMessage(request, "login.failed.verificationCode.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		// }

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
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
		}
		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(entity);
		if ((null == userInfoList) || (userInfoList.size() == 0)) {
			msg = super.getMessage(request, "login.failed.username.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "login.failed.username.repeat");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		Cookie passwordCookie = WebUtils.getCookie(request, "password");
		if ((null != passwordCookie) && DEFAULT_PASSWORD.equals(password)) {
			entity.setPassword(passwordCookie.getValue());
		} else {
			DESPlus des = new DESPlus();
			entity.setPassword(des.encrypt(password));
			// entity.setPassword(EncryptUtilsV2.MD5Encode(password));
		}
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == userInfo) {
			msg = super.getMessage(request, "login.failed.password.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		} else {
			CookieGenerator cg = new CookieGenerator();
			if (is_remember != null) {
				cg.setCookieMaxAge(60 * 60 * 24 * 7);
				cg.setCookieName("login_name");
				cg.addCookie(response, URLEncoder.encode(user_name, "UTF-8"));
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

			// 未激活或者企业审核未通过不能登录
			// if (1 != userInfo.getIs_active().intValue()) {
			// if (null == userInfo.getOwn_entp_id()) {
			// msg = super.getMessage(request, "login.failed.notactive");
			// super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			// return null;
			// } else {
			// msg = "企业信息未审核或者审核未通过！";
			// super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			// return null;
			// }
			// }

			// update login count
			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			ui.setLast_login_ip(this.getIpAddr(request));
			ui.setLast_login_time(new Date());
			getFacade().getUserInfoService().modifyUserInfo(ui);

			userInfo.setLogin_count(ui.getLogin_count());
			userInfo.setCur_score(userInfo.getCur_score());
			if (userInfo.getUser_type().intValue() == 1 || userInfo.getUser_type().intValue() == 6
					|| userInfo.getUser_type().intValue() == 7 || userInfo.getUser_type().intValue() == 8
					|| userInfo.getUser_type().intValue() == 9 || userInfo.getUser_type().intValue() == 10) {
				// super.setUserInfoToSession(request, userInfo);
				// ServletContext sc = this.getServlet().getServletContext();
				// Object obj = sc.getAttribute(userInfo.getId().toString());
				// if (null != obj) {
				// sc.removeAttribute(userInfo.getId().toString());
				// }
				// session.setAttribute("loginDate", new Date());
				// session.setAttribute("loginIp", getIpAddr(request));
				// sc.setAttribute("repeatLogin_" + userInfo.getId().toString(), session);
				//
				// this.setCookieUserinfoKeyJsessionid(request, response);
				// // if (StringUtils.isNotBlank(returnUrl)) {// 自动跳转至returnUrl
				// // response.sendRedirect(returnUrl);
				// // return null;
				// // }
				// return toManager(mapping, form, request, response);

				// msg = "管理员不能再该端口登录！";
				msg = "此用户不存在！";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
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

				this.setCookieUserinfoKeyJsessionid(request, response);

				if (StringUtils.isNotBlank(returnUrl)) {// 自动跳转至returnUrl
					response.sendRedirect(returnUrl);
					return null;
				} else if (userInfo.getUser_type() == Keys.UserType.USER_TYPE_2.getIndex()) {
					return new ActionForward("index.shtml", true);
				}
				return toCustomer(mapping, form, request, response);
			}
		}
	}

	public ActionForward toManager(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");
		return new ActionForward("/manager/admin/Frames.do", true);
	}

	public ActionForward toCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");
		return new ActionForward("/manager/customer/Index.do", true);
	}

	public ActionForward toCustomerForM(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");
		String ctx = super.getCtxPath(request);
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

		return new ActionForward("/IndexLogin.do", true);
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
			cg1.setCookieDomain(server_min_domain);
			cg1.setCookieMaxAge(1 * 24 * 60 * 60);
			cg1.setCookieName(Keys.COOKIE_USERINFO_KEY_JSESSIONID);
			cg1.addCookie(response, URLEncoder.encode(si, "UTF-8"));
		}
	}

	public ActionForward qq(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		try {
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward weibo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		weibo4j.Oauth oauth = new weibo4j.Oauth();
		String state = String.valueOf(new Date().getTime());
		String scope = "all";
		response.sendRedirect(oauth.authorize("code", state, scope));
		return null;
	}

	public ActionForward afterLoginWeibo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// response.setContentType("text/html; charset=utf-8");
		DynaBean dynaBean = (DynaBean) form;
		String code = (String) dynaBean.get("code");
		try {
			weibo4j.Oauth oauth = new weibo4j.Oauth();
			weibo4j.http.AccessToken accessTokenObj = oauth.getAccessTokenByCode(code);

			String access_token = null, uid = null;
			access_token = accessTokenObj.getAccessToken();
			if (access_token.equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				String msg = "没有获取到响应参数";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;

			} else {
				// Timeline tm = new Timeline();
				// tm.client.setToken(access_token);

				Account am = new Account();
				am.client.setToken(access_token);
				JSONObject uidJson = am.getUid();
				uid = uidJson.getString("uid");

				Users um = new Users();
				um.client.setToken(access_token);
				User user = um.showUserById(uid);

				if (null == user) {
					String msg = "用户为空";
					super.showMsgForManager(request, response, msg);
					return null;
				}
				UserInfo userInfo = new UserInfo();
				userInfo.setAppid_weibo(uid);
				userInfo.setIs_del(0);
				long count = getFacade().getUserInfoService().getUserInfoCount(userInfo);
				if (count > 0) {
					userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
					super.setUserInfoToSession(request, userInfo);
					boolean isMoblie = super.JudgeIsMoblie(request);
					if (isMoblie) {// 手机版
						return this.toCustomerForM(mapping, form, request, response);
					} else {
						return this.toCustomer(mapping, form, request, response);
					}
				}

				String real_name = user.getScreenName();
				// String user_name = user.getName();

				// dynaBean.set("user_name", "WEIBO" + uid);
				dynaBean.set("real_name", real_name);
				dynaBean.set("user_logo", user.getAvatarLarge());
				dynaBean.set("appid_weibo", uid);

				// 调转到手机版用
				request.setAttribute("header_title", "用户注册 - 绑定微博");

				return this.toRegister(mapping, form, request, response);

				// out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL100() + "/><br/>");
			}
		} catch (WeiboException e) {
			String msg = e.getMessage();
			super.showMsgForManager(request, response, msg);
		}
		return null;
	}

	public ActionForward toRegister(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		saveToken(request);

		// request.setAttribute("isEnabledMobileVeriCode", super.getSysSetting("isEnabledMobileVeriCode"));
		boolean isMoblie = super.JudgeIsMoblie(request);
		if (isMoblie) {// 手机版
			// String ctx = super.getCtxPath(request);
			request.setAttribute("is_mobile", "true");
			return new ActionForward("/m/MRegister/register.jsp");
		}
		return new ActionForward("/register/register.jsp");
	}

}
