package com.ebiz.webapp.web.struts.m;

import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.util.RegexUtils;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class MRegisterAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "用户注册");
		DynaBean dynaBean = (DynaBean) form;
		String from = (String) dynaBean.get("from");
		String suid = (String) dynaBean.get("suid");
		if (StringUtils.isNotBlank(from)) {

			// 从分享二维码页面调转过来
			request.setAttribute("header_title", "邀请 - 用户注册");
			if (StringUtils.equals(from, "share") && StringUtils.isNotBlank(suid)) {
				if (!GenericValidator.isLong(suid)) {
					String msg = "参数不正确";
					super.showMsgForManager(request, response, msg);
					return null;
				}
				UserInfo ui = new UserInfo();
				ui.setId(Integer.valueOf(suid));
				ui.setIs_del(0);
				ui = getFacade().getUserInfoService().getUserInfo(ui);
				if (null == ui) {
					String msg = "邀请人不存在";
					super.showMsgForManager(request, response, msg);
					return null;
				}
				String ctx = super.getCtxPath(request, false);
				// 判断是否微信扫描
				if (super.isWeixin(request)) {
					// 如果是微信的话 直接跳转到微信注册
					StringBuilder link = new StringBuilder();
					String scope = "snsapi_userinfo";
					String state = "";

					StringBuffer server = new StringBuffer();
					server.append(request.getHeader("host")).append(request.getContextPath());
					request.setAttribute("server_domain", server.toString());
					String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=afterLoginWeixin");

					link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
							.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8"))
							.append("&response_type=code").append("&scope=" + scope).append("&state=" + state)
							.append("#wechat_redirect");
					response.sendRedirect(link.toString());
					return null;

				}
				dynaBean.set("readolny_ymid", suid);
				dynaBean.set("ymid", ui.getUser_name());
			}
		}

		saveToken(request);
		return new ActionForward("/MRegister/register_no_username.jsp");
	}

	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		DynaBean dynaBean = (DynaBean) form;

		String real_name = (String) dynaBean.get("real_name");
		String password = (String) dynaBean.get("password");
		String mobile = (String) dynaBean.get("mobile");
		String ymid = (String) dynaBean.get("ymid");
		String verifycode = (String) dynaBean.get("verifycode");

		boolean _mobile = RegexUtils.checkMobile(mobile);
		if (_mobile == false) {
			String msg = mobile + "手机号码格式不正确";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		if (StringUtils.isBlank(password)) {
			String msg = "密码不能为空";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		if (StringUtils.isBlank(verifycode)) {
			String msg = mobile + "手机动态码不能为空";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		if (!(verifycode.equalsIgnoreCase((String) request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			String msg = mobile + "手机动态码填写有误";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		if (StringUtils.isBlank(ymid)) {
			UserInfo userInfo = super.getUserInfo(Keys.SYS_ADMIN_ID);
			ymid = userInfo.getUser_name();
		}

		UserInfo entityQueryHasExist = new UserInfo();
		entityQueryHasExist.getMap().put("ym_id", ymid);
		entityQueryHasExist.setIs_del(0);
		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entityQueryHasExist);
		if (recordCount.intValue() <= 0) {
			String msg = "未查询到该邀请人";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		if (recordCount > 0) {
			entityQueryHasExist = super.getFacade().getUserInfoService().getUserInfo(entityQueryHasExist);
			if (entityQueryHasExist.getUser_type().intValue() != Keys.UserType.USER_TYPE_1.getIndex()) {
				if (entityQueryHasExist.getIs_entp().intValue() == 1
						|| entityQueryHasExist.getIs_fuwu().intValue() == 1) {// 这几个类型不能作为推荐人
					String msg = "该用户不能作为推荐人";
					return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
				}
			}
		}

		if (recordCount.intValue() > 1) {
			String msg = "上级用户重复，请联系管理员";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		UserInfo ui1 = new UserInfo();
		ui1.setUser_name(mobile);
		ui1.setIs_del(0);
		int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count1 > 0) {
			String msg = "你的用户名太响亮了，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		ui1 = new UserInfo();
		ui1.setUser_name(mobile);
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count2 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count2 > 0) {
			String msg = "用户名或者手机号，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		ui1 = new UserInfo();
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count3 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count3 > 0) {
			String msg = "手机号，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		ui1 = new UserInfo();
		ui1.setUser_name(mobile);// 防止这个人的手机号是另一个人的用户名
		ui1.setIs_del(0);
		int count4 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count4 > 0) {
			String msg = "用户名，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		resetToken(request);

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(real_name)) {
			entity.setReal_name(mobile);
		}
		entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(new Integer(1));
		entity.setIs_daqu(0);
		entity.setIs_entp(0);
		entity.setIs_fuwu(0);
		entity.setIs_lianmeng(0);
		entity.setIs_active(1);
		entity.setIs_has_update_pass(1);

		if (StringUtils.isNotBlank(ymid)) {
			UserInfo user_ymid = new UserInfo();
			user_ymid.getMap().put("ym_id", ymid);
			user_ymid.setIs_del(0);
			user_ymid = getFacade().getUserInfoService().getUserInfo(user_ymid);
			entity.setYmid(user_ymid.getUser_name());// 全部保存用户名

			entity.getMap().put("insert_user_realtion", "true");
		}

		entity.setLogin_count(1);
		entity.setLast_login_time(new Date());
		entity.setLast_login_ip(this.getIpAddr(request));

		int id = super.getFacade().getUserInfoService().createUserInfo(entity);

		UserInfo ui = new UserInfo();
		ui.setId(id);
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(ui);

		super.setUserInfoToSession(request, userInfo);

		session.setAttribute(Keys.MOBILE_VERI_CODE, UUID.randomUUID().toString());// 清空下短信验证码，防止刷单

		// 重复登录相关信息存储
		ServletContext sc = this.getServlet().getServletContext();
		Object obj = sc.getAttribute(userInfo.getId().toString());
		if (null != obj) {
			sc.removeAttribute(userInfo.getId().toString());
		}
		session.setAttribute("loginDate", new Date());
		session.setAttribute("loginIp", getIpAddr(request));
		sc.setAttribute("repeatLogin_" + userInfo.getId().toString(), session);

		String ctx = super.getCtxPath(request);

		// this.setCookieUserinfoKeyJsessionid(request, response);
		return new ActionForward(ctx + "/m/MMyCard.do", true);

		// String msg = "注册成功，请您登陆！";
		// return super.showTipNotLogin(mapping, form, request, response, msg);

	}

	public ActionForward registerNoUserName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		DynaBean dynaBean = (DynaBean) form;

		// String user_name = (String) dynaBean.get("user_name");
		String real_name = (String) dynaBean.get("real_name");
		String password = (String) dynaBean.get("password_hide");
		String mobile = (String) dynaBean.get("mobile");
		String ymid = (String) dynaBean.get("ymid");
		String verifycode = (String) dynaBean.get("verifycode");

		boolean _mobile = RegexUtils.checkMobile(mobile);
		if (_mobile == false) {
			String msg = mobile + "手机号码格式不正确";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		if (StringUtils.isBlank(password)) {
			String msg = "密码不能为空";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		if (StringUtils.isBlank(verifycode)) {
			String msg = mobile + "手机动态码不能为空";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		if (!(verifycode.equalsIgnoreCase((String) request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			String msg = mobile + "手机动态码填写有误";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		if (StringUtils.isBlank(ymid)) {
			UserInfo userInfo = super.getUserInfo(Keys.SYS_ADMIN_ID);
			ymid = userInfo.getUser_name();
		}

		UserInfo entityQueryHasExist = new UserInfo();
		entityQueryHasExist.getMap().put("ym_id", ymid);
		entityQueryHasExist.setIs_del(0);
		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entityQueryHasExist);
		if (recordCount.intValue() <= 0) {
			String msg = "未查询到该邀请人";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		if (recordCount > 0) {

			entityQueryHasExist = super.getFacade().getUserInfoService().getUserInfo(entityQueryHasExist);

			if (entityQueryHasExist.getUser_type().intValue() != Keys.UserType.USER_TYPE_1.getIndex()) {
				if (entityQueryHasExist.getIs_entp().intValue() == 1
						|| entityQueryHasExist.getIs_fuwu().intValue() == 1) {// 这几个类型不能作为推荐人
					String msg = "该用户不能作为推荐人";
					return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
				}
			}
		}

		if (recordCount.intValue() > 1) {
			String msg = "上级用户重复，请联系管理员";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		UserInfo ui1 = new UserInfo();
		ui1.setUser_name(mobile);
		ui1.setIs_del(0);
		int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count1 > 0) {
			String msg = "你的用户名太响亮了，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		ui1 = new UserInfo();
		ui1.setUser_name(mobile);
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count2 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count2 > 0) {
			String msg = "用户名或者手机号，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		ui1 = new UserInfo();
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count3 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count3 > 0) {
			String msg = "手机号，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}
		ui1 = new UserInfo();
		ui1.setUser_name(mobile);// 防止这个人的手机号是另一个人的用户名
		ui1.setIs_del(0);
		int count4 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count4 > 0) {
			String msg = "用户名，已经被注册。";
			return super.showTipMsg(mapping, form, request, response, msg, "/m/MRegister.do");
		}

		resetToken(request);

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(real_name)) {
			entity.setReal_name(mobile);
		}
		entity.setUser_name(mobile);
		entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(new Integer(1));
		entity.setIs_daqu(0);
		entity.setIs_entp(0);
		entity.setIs_fuwu(0);
		entity.setIs_lianmeng(0);
		entity.setIs_active(1);
		entity.setIs_has_update_pass(1);

		if (StringUtils.isNotBlank(ymid)) {
			UserInfo user_ymid = new UserInfo();
			user_ymid.getMap().put("ym_id", ymid);
			user_ymid.setIs_del(0);
			user_ymid = getFacade().getUserInfoService().getUserInfo(user_ymid);
			entity.setYmid(user_ymid.getUser_name());// 全部保存用户名

			entity.getMap().put("insert_user_realtion", "true");
		}

		entity.setLogin_count(1);
		entity.setLast_login_time(new Date());
		entity.setLast_login_ip(this.getIpAddr(request));

		int id = super.getFacade().getUserInfoService().createUserInfo(entity);

		UserInfo ui = new UserInfo();
		ui.setId(id);
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(ui);

		super.setUserInfoToSession(request, userInfo);

		session.setAttribute(Keys.MOBILE_VERI_CODE, UUID.randomUUID().toString());// 清空下短信验证码，防止刷单

		// 重复登录相关信息存储
		ServletContext sc = this.getServlet().getServletContext();
		Object obj = sc.getAttribute(userInfo.getId().toString());
		if (null != obj) {
			sc.removeAttribute(userInfo.getId().toString());
		}
		session.setAttribute("loginDate", new Date());
		session.setAttribute("loginIp", getIpAddr(request));
		sc.setAttribute("repeatLogin_" + userInfo.getId().toString(), session);

		String ctx = super.getCtxPath(request);

		// this.setCookieUserinfoKeyJsessionid(request, response);
		return new ActionForward(ctx + "/m/MMyCard.do", true);

		// String msg = "注册成功，请您登陆！";
		// return super.showTipNotLogin(mapping, form, request, response, msg);

	}
}
