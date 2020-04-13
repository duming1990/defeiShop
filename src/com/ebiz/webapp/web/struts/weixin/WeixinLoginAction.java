package com.ebiz.webapp.web.struts.weixin;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

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
import com.aiisen.weixin.oauth2.Oauth2AccessToken;
import com.aiisen.weixin.oauth2.Oauth2Api;
import com.aiisen.weixin.oauth2.Oauth2UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.util.RegexUtils;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BasePayAction;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,Yang
 * @version 2011-11-23
 */
public class WeixinLoginAction extends BasePayAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return null;
	}

	public ActionForward toCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean lazyForm = (DynaBean) form;
		String return_url = (String) lazyForm.get("return_url");
		// HttpSession session = request.getSession();
		// this.setWeixinMenuToSession(session);

		if (StringUtils.isNotBlank(return_url)) {// 自动跳转至returnUrl
			response.sendRedirect(return_url);
			return null;
		}

		logger.info("==toCustomer==");
		String ctx = super.getCtxPath(request);
		return new ActionForward(ctx + "/m/MMyCard.do", true);
	}

	public ActionForward validateName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String success = "true";
		String user_name = (String) dynaBean.get("user_name");
		if (StringUtils.isBlank(user_name)) {
			super.renderJson(response, success);
			return null;
		}
		logger.info("===user_name:{}", user_name);

		UserInfo entity = new UserInfo();
		entity.setUser_name(user_name);
		int count = getFacade().getUserInfoService().getUserInfoCount(entity);
		if (count == 0) {
			success = "true";
		} else {
			success = "false";
		}
		logger.info("===success:{}", success);
		super.renderJson(response, success);
		return null;
	}

	public ActionForward weixin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		UserInfo ui = super.getUserInfoFromSession(request);
		String return_url = request.getParameter("return_url");// 返回URL
		logger.info("==weixin return_url:{}", return_url);
		// if (null != ui && StringUtils.isNotBlank(return_url)) {// 如果session存在，并且return_url不为空，直接跳转到return_url
		response.sendRedirect(return_url);
		return null;
		// }

		// StringBuilder link = new StringBuilder();
		// String scope = "snsapi_userinfo";
		// String state = "";
		//
		// StringBuffer server = new StringBuffer();
		// server.append(request.getHeader("host")).append(request.getContextPath());
		// request.setAttribute("server_domain", server.toString());
		// String redirectUri = "http://".concat(server.toString()).concat(
		// "/weixin/WeixinLogin.do?method=afterLoginWeixin");
		//
		// if (StringUtils.isNotBlank(return_url)) {
		// redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
		// }
		// // String refererUrl = request.getHeader("Referer");
		// // logger.info("==refererUrl:{}", refererUrl);
		// link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
		// .append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
		// .append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");
		//
		// response.sendRedirect(link.toString());
		//
		// logger.warn("==weixin sendRedirect :{}", link.toString());
		//
		// return null;
	}

	public ActionForward afterLoginWeixin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String return_url = request.getParameter("return_url");// 返回URL

		logger.info("code:" + code + "--state:" + state);
		if (StringUtils.isBlank(code)) {
			String msg = "没有获取到响应参数code";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		Oauth2AccessToken token = Oauth2Api.getOauth2AccessToken(code);

		// 这个方法能获取到微信头像和昵称，但是获取不到subscribe
		Oauth2UserInfo oauth2UserInfoWithNicknameAndHeadimgurl = Oauth2Api.oauth2UserInfo(token.getAccess_token(),
				token.getOpenid(), "zh_CN");

		// 注意：如果要获取是否关注微信公众号和UnionID信息，需要用这个方法获取
		Oauth2AccessToken accessToken = Oauth2Api.getAccessToken();// 获取普通的accessToken
		// 这个方法不能获取到微信头像和昵称，能获取到subscribe
		Oauth2UserInfo oauth2UserInfo = Oauth2Api.oauth2UserInfoUnionID(accessToken.getAccess_token(),
				token.getOpenid(), "zh_CN");

		if ((null == oauth2UserInfoWithNicknameAndHeadimgurl)
				|| (null == oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid())) {
			String msg = "user为空或者Openid为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		// TODO 在这里做绑定帐号操作
		String appid_weixin = oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid();
		if (StringUtils.isBlank(appid_weixin)) {
			String msg = "appid_weixin为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		String appid_weixin_unionid = oauth2UserInfoWithNicknameAndHeadimgurl.getUnionid();
		// String subscribe = oauth2UserInfoWithNicknameAndHeadimgurl.getSubscribe();// 是否关注 0 未关注 1 已关注
		String subscribe = oauth2UserInfo.getSubscribe();// 是否关注 0 未关注 1 已关注
		// 参数说明：http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
		String nickname = oauth2UserInfoWithNicknameAndHeadimgurl.getNickname();
		String headimgurl = oauth2UserInfoWithNicknameAndHeadimgurl.getHeadimgurl();
		logger.warn("=afterLoginWeixin=appid_weixin:{} -- appid_weixin_unionid:{}", appid_weixin, appid_weixin_unionid);
		logger.warn("=afterLoginWeixin=oauth2UserInfo.subscribe:" + subscribe);

		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(0);
		if(appid_weixin_unionid == null || "".equals(appid_weixin_unionid)){
			userInfo.setAppid_weixin_unionid(appid_weixin_unionid);//通过微信union_id登录
		}else{
			userInfo.setAppid_weixin(appid_weixin);//更新
		}
		
		long count = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		if (count > 0) {
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			super.setUserInfoToSession(request, userInfo);

			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			ui.setLast_login_ip(this.getIpAddr(request));
			ui.setLast_login_time(new Date());
			ui.setAppid_weixin(appid_weixin);//更新
			getFacade().getUserInfoService().modifyUserInfo(ui);

			super.createSysOperLog(request, Keys.SysOperType.SysOperType_11.getIndex(), "用户登录", "用户微信登录");

			if (GenericValidator.isInt(subscribe)) {
				int appid_weixin_is_follow = Integer.valueOf(subscribe);

				// 如果微信获取的状态和数据库状态不一样，更新下关注状态
				if (appid_weixin_is_follow != userInfo.getAppid_weixin_is_follow().intValue()) {
					UserInfo userInfoUpdate = new UserInfo();
					userInfoUpdate.setId(userInfo.getId());
					userInfoUpdate.setAppid_weixin_is_follow(appid_weixin_is_follow);
					super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
				}

				// 未关注公众号，通过微信状态判断，防止关注后有取消了关注
				if (appid_weixin_is_follow == 0 && StringUtils.isBlank(return_url)) {
					return this.subscribe(mapping, form, request, response);
				}
			}

			return this.toCustomer(mapping, form, request, response);
		}

		DynaBean db = (DynaBean) form;
		String suid = request.getParameter("suid");
		// 有suid说明是：通过微信扫描个人推广二维码过来的，需要自动注册
		if (GenericValidator.isInt(suid)) {

			UserInfo ui = new UserInfo();
			ui.setId(Integer.valueOf(suid));
			ui.setIs_del(0);
			ui = getFacade().getUserInfoService().getUserInfo(ui);
			if (null == ui) {
				String msg = "邀请人不存在";
				super.showTipMsg(mapping, form, request, response, msg);
				return null;
			}
			db.set("ymid", ui.getUser_name());
			// 这个地方自动注册
			// userInfo.setUser_name(UUID.randomUUID().toString());
			// userInfo.setReal_name(nickname);
			// DESPlus des = new DESPlus();
			// userInfo.setPassword(des.encrypt(Keys.INIT_PWD));
			// userInfo.setUser_logo(headimgurl);
			// userInfo.setAdd_date(new Date());
			// userInfo.setAppid_weixin_unionid(appid_weixin_unionid);// 保存unionid,测试是否能获取到
			// userInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			// userInfo.getMap().put("update_user_name", "true");
			// userInfo.setLogin_count(1);
			// userInfo.setIs_active(1);
			// userInfo.setLast_login_time(new Date());
			// userInfo.setLast_login_ip(this.getIpAddr(request));
			// userInfo.setYmid(ui.getUser_name());
			// userInfo.getMap().put("insert_user_realtion", "true");
			//
			// int id = super.getFacade().getUserInfoService().createUserInfo(userInfo);
			//
			// super.setUserInfoToSession(request, super.getUserInfo(id));
			// super.createSysOperLog(request, Keys.SysOperType.SysOperType_11.getIndex(), "用户登录", "用户微信登录");
			//
			// response.sendRedirect(return_url);
			// return null;
		}
		db.set("nickname", nickname);
		db.set("appid_weixin", appid_weixin);
		db.set("appid_weixin_unionid", appid_weixin_unionid);
		db.set("headimgurl", headimgurl);
		db.set("subscribe", subscribe);
		db.set("return_url", return_url);

		request.setAttribute("header_title", "用户注册 - 绑定微信");
		return new ActionForward("/WeixinLogin/formReg.jsp");
	}

	public ActionForward test(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "用户注册 - 绑定微信");
		return new ActionForward("/WeixinLogin/bindMobile.jsp");
	}

	public ActionForward saveWeixin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		DynaBean dynaBean = (DynaBean) form;

		String appid_weixin = (String) dynaBean.get("appid_weixin");
		String appid_weixin_unionid = (String) dynaBean.get("appid_weixin_unionid");
		String subscribe = (String) dynaBean.get("subscribe");
		String headimgurl = (String) dynaBean.get("user_logo");
		String return_url = (String) dynaBean.get("return_url");

		if (StringUtils.isBlank(appid_weixin)) {
			String msg = "appid_weixin为空！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (StringUtils.isBlank(appid_weixin_unionid)) {
			String msg = "appid_weixin_unionid为空！";
			//super.showMsgForManager(request, response, msg);
			//return null;
		}
		
		// String user_name = (String) dynaBean.get("user_name");
		String real_name = (String) dynaBean.get("real_name");
		String password = (String) dynaBean.get("password");
		String mobile = (String) dynaBean.get("mobile");
		String ymid = (String) dynaBean.get("ymid");
		String verifycode = (String) dynaBean.get("verifycode");

		boolean _mobile = RegexUtils.checkMobile(mobile);
		if (_mobile == false) {
			String msg = mobile + "手机号码格式不正确";
			return super.showMsgForManager(request, response, msg);
		}
		// if (StringUtils.isBlank(password)) {
		// String msg = "密码不能为空";
		// return super.showMsgForManager(request, response, msg);
		// }
		if (StringUtils.isBlank(verifycode)) {
			String msg = mobile + "手机动态码不能为空";
			return super.showMsgForManager(request, response, msg);
		}
		if (!(verifycode.equalsIgnoreCase((String) request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			String msg = mobile + "手机动态码填写有误";
			return super.showMsgForManager(request, response, msg);
		}

		UserInfo uiweixin = new UserInfo();
		uiweixin.setAppid_weixin(appid_weixin);
		uiweixin.setIs_del(0);
		int cweixn = getFacade().getUserInfoService().getUserInfoCount(uiweixin);
		if (cweixn > 0) {
			String msg = "您的微信已经被绑定了！";
			return super.showMsgForManager(request, response, msg);
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
			return super.showMsgForManager(request, response, msg);
		}
		if (recordCount > 0) {
			entityQueryHasExist = super.getFacade().getUserInfoService().getUserInfo(entityQueryHasExist);
			if (entityQueryHasExist.getUser_type().intValue() != Keys.UserType.USER_TYPE_1.getIndex()) {
				if (entityQueryHasExist.getIs_entp().intValue() == 1
						|| entityQueryHasExist.getIs_fuwu().intValue() == 1) {// 这几个类型不能作为推荐人
					String msg = "该用户不能作为推荐人";
					return super.showMsgForManager(request, response, msg);
				}
			}
		}

		if (recordCount.intValue() > 1) {
			String msg = "上级用户重复，请联系管理员";
			return super.showMsgForManager(request, response, msg);
		}

		// UserInfo ui1 = new UserInfo();
		// ui1.setUser_name(mobile);
		// ui1.setIs_del(0);
		// int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		// if (count1 > 0) {
		// String msg = "你的用户名太响亮了，已经被注册。";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }
		// ui1 = new UserInfo();
		// ui1.setUser_name(mobile);
		// ui1.setMobile(mobile);
		// ui1.setIs_del(0);
		// int count2 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		// if (count2 > 0) {
		// String msg = "用户名或者手机号，已经被注册。";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }
		// ui1 = new UserInfo();
		// ui1.setMobile(mobile);
		// ui1.setIs_del(0);
		// int count3 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		// if (count3 > 0) {
		// String msg = "手机号，已经被注册。";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }
		// ui1 = new UserInfo();
		// ui1.setUser_name(mobile);// 防止这个人的手机号是另一个人的用户名
		// ui1.setIs_del(0);
		// int count4 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		// if (count4 > 0) {
		// String msg = "用户名，已经被注册。";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }
		UserInfo ui1 = new UserInfo();
		ui1.getMap().put("ym_id", mobile);
		ui1.setIs_del(0);
		ui1 = getFacade().getUserInfoService().getUserInfo(ui1);
		if (null != ui1) {
			if (null != ui1.getAppid_weixin()) {
				String msg = "用户名或者手机号，已经被注册。";
				super.showMsgForManager(request, response, msg);
				return null;
			} else {
				DESPlus des = new DESPlus();
				ui1.setAppid_weixin(appid_weixin);
				ui1.setAppid_weixin_is_follow(Integer.valueOf(subscribe));
				ui1.setAppid_weixin_unionid(appid_weixin_unionid);
				ui1.setUpdate_date(new Date());
				getFacade().getUserInfoService().modifyUserInfo(ui1);

				super.setUserInfoToSession(request, ui1);

				// 重复登录相关信息存储
				ServletContext sc = this.getServlet().getServletContext();
				Object obj = sc.getAttribute(ui1.getId().toString());
				if (null != obj) {
					sc.removeAttribute(ui1.getId().toString());
				}
				session.setAttribute("loginDate", new Date());
				session.setAttribute("loginIp", getIpAddr(request));
				sc.setAttribute("repeatLogin_" + ui1.getId().toString(), session);

				if (StringUtils.isNotBlank(return_url)) {// 自动跳转至returnUrl
					response.sendRedirect(return_url);
					return null;
				}
				return this.toCustomer(mapping, form, request, response);
			}
		}

		resetToken(request);

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);

		entity.setAppid_weixin(appid_weixin);
		entity.setAppid_weixin_unionid(appid_weixin_unionid);// 保存unionid,测试是否能获取到
		if (GenericValidator.isInt(subscribe)) {
			entity.setAppid_weixin_is_follow(Integer.valueOf(subscribe));
		}
		entity.setUser_logo(headimgurl);

		if (StringUtils.isBlank(real_name)) {
			entity.setReal_name(mobile);
		}
		entity.setUser_name(mobile);
		entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());

		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(Keys.DEFAULT_PASSWORD));
		if (StringUtils.isNotBlank(password)) {
			entity.setPassword(des.encrypt(password));
		}
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(new Integer(-1));
		entity.setIs_daqu(0);
		entity.setIs_entp(0);
		entity.setIs_fuwu(0);
		entity.setIs_lianmeng(0);
		entity.setIs_active(1);
		entity.setIs_has_update_pass(1);

		if (StringUtils.isNotBlank(ymid)) {
			entity.getMap().put("insert_user_realtion", "true");
			entity.setYmid(ymid);
		}

		entity.setLogin_count(1);
		entity.setLast_login_time(new Date());
		entity.setLast_login_ip(this.getIpAddr(request));

		int id = super.getFacade().getUserInfoService().createUserInfo(entity);

		UserInfo ui = new UserInfo();
		ui.setId(id);
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(ui);

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

		if (StringUtils.isNotBlank(return_url)) {// 自动跳转至returnUrl
			response.sendRedirect(return_url);
			return null;
		}
		return this.toCustomer(mapping, form, request, response);
	}

	public ActionForward subscribe(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "关注公众号");
		return new ActionForward("/WeixinLogin/subscribe.jsp");
	}

	public ActionForward toReg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "用户注册 - 绑定微信");
		return new ActionForward("/WeixinLogin/formReg.jsp");
	}

	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String out_trade_no = request.getParameter("out_trade_no");
		String order_type = request.getParameter("order_type");
		String needFxFlag = request.getParameter("needFxFlag");
		String huizhuan_order_ids = request.getParameter("huizhuan_order_ids");
		if (StringUtils.isBlank(out_trade_no)) {
			String msg = "out_trade_no参数不正确";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (!GenericValidator.isInt(order_type)) {
			String msg = "order_type参数不正确";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		StringBuilder link = new StringBuilder();
		String scope = "snsapi_userinfo";
		String state = "";

		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		request.setAttribute("server_domain", server.toString());
		String redirectUri = "http://".concat(server.toString()).concat(
				"/weixin/Pay.do?out_trade_no=" + out_trade_no + "&order_type=" + order_type + "&needFxFlag="
						+ needFxFlag + "&huizhuan_order_ids=" + huizhuan_order_ids);

		link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
				.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
				.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");

		response.sendRedirect(link.toString());

		return null;
	}

	public ActionForward pay1Fen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		StringBuilder link = new StringBuilder();
		String scope = "snsapi_userinfo";
		String state = "";

		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		request.setAttribute("server_domain", server.toString());
		String redirectUri = "http://".concat(server.toString()).concat("/weixin/Pay1Fen.do");

		String return_url = request.getParameter("return_url");// 返回URL
		if (StringUtils.isNotBlank(return_url)) {
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
		}
		// String refererUrl = request.getHeader("Referer");
		// logger.info("==refererUrl:{}", refererUrl);
		link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
				.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
				.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");

		response.sendRedirect(link.toString());

		// logger.warn("==weixin sendRedirect :{}", link.toString());

		return null;
	}

	public ActionForward pay1FenNative(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		StringBuilder link = new StringBuilder();
		String scope = "snsapi_userinfo";
		String state = "";

		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		request.setAttribute("server_domain", server.toString());
		String redirectUri = "http://".concat(server.toString()).concat("/weixin/Pay1Fen.do?method=payNative");

		String return_url = request.getParameter("return_url");// 返回URL
		if (StringUtils.isNotBlank(return_url)) {
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
		}
		// String refererUrl = request.getHeader("Referer");
		// logger.info("==refererUrl:{}", refererUrl);
		link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
				.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
				.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");

		response.sendRedirect(link.toString());

		// logger.warn("==weixin sendRedirect :{}", link.toString());

		return null;
	}

	public ActionForward bindWeixin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String user_id = request.getParameter("user_id");
		if (!GenericValidator.isLong(user_id)) {
			String msg = "user_id参数不正确";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		StringBuilder link = new StringBuilder();
		String scope = "snsapi_userinfo";
		String state = "";

		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		request.setAttribute("server_domain", server.toString());
		String redirectUri = "http://".concat(server.toString()).concat(
				"/weixin/WeixinLogin.do?method=bindWeixinAfter&user_id=" + user_id);

		// String return_url = request.getParameter("return_url");// 返回URL
		// if (StringUtils.isNotBlank(return_url)) {
		// redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
		// }
		// String refererUrl = request.getHeader("Referer");
		// logger.info("==refererUrl:{}", refererUrl);
		link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
				.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
				.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");

		response.sendRedirect(link.toString());

		// logger.warn("==weixin sendRedirect :{}", link.toString());

		return null;
	}

	public ActionForward bindWeixinAfter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// DynaBean dynaBean = (DynaBean) form;

		String user_id = request.getParameter("user_id");// 返回URL

		if (!GenericValidator.isLong(user_id)) {
			String msg = "绑定时出现user_id参数不正确";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		response.setContentType("text/html; charset=utf-8");

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		// String return_url = request.getParameter("return_url");// 返回URL
		logger.info("bindWeixinAfter user_id:" + user_id + "code:" + code + "--state:" + state);
		logger.info("code:" + code + "--state:" + state);
		if (StringUtils.isBlank(code)) {
			String msg = "没有获取到响应参数code";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		Oauth2AccessToken token = Oauth2Api.getOauth2AccessToken(code);

		// 这个方法能获取到微信头像和昵称，但是获取不到subscribe
		Oauth2UserInfo oauth2UserInfoWithNicknameAndHeadimgurl = Oauth2Api.oauth2UserInfo(token.getAccess_token(),
				token.getOpenid(), "zh_CN");

		// logger.info("oauth2AccessToken:" + token);
		// logger.warn("==token.getAccess_token():" + token.getAccess_token());
		// logger.warn("==token.getOpenid():" + token.getOpenid());

		// 注意：如果要获取是否关注微信公众号和UnionID信息，需要用这个方法获取
		Oauth2AccessToken accessToken = Oauth2Api.getAccessToken();// 获取普通的accessToken
		// 这个方法不能获取到微信头像和昵称，能获取到subscribe
		Oauth2UserInfo oauth2UserInfo = Oauth2Api.oauth2UserInfoUnionID(accessToken.getAccess_token(),
				token.getOpenid(), "zh_CN");

		if ((null == oauth2UserInfoWithNicknameAndHeadimgurl)
				|| (null == oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid())) {
			String msg = "user为空或者Openid为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		// Oauth2UserInfo oauth2UserInfo = new Oauth2UserInfo();
		// oauth2UserInfo.setOpenid("openid_111111");
		// oauth2UserInfo.setNickname("aiisen");
		// oauth2UserInfo.setHeadimgurl("http://aasdfs.com/a.jpg");
		// logger.info("oauth2UserInfo:" + oauth2UserInfo);

		// TODO 在这里做绑定帐号操作
		String appid_weixin = oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid();
		if (StringUtils.isBlank(appid_weixin)) {
			String msg = "appid_weixin为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		String appid_weixin_unionid = oauth2UserInfoWithNicknameAndHeadimgurl.getUnionid();
		// String subscribe = oauth2UserInfoWithNicknameAndHeadimgurl.getSubscribe();// 是否关注 0 未关注 1 已关注
		String subscribe = oauth2UserInfo.getSubscribe();// 是否关注 0 未关注 1 已关注
		// 参数说明：http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html

		String nickname = oauth2UserInfoWithNicknameAndHeadimgurl.getNickname();
		String headimgurl = oauth2UserInfoWithNicknameAndHeadimgurl.getHeadimgurl();
		logger.warn("=bindWeixinAfter=appid_weixin:{} -- appid_weixin_unionid:{}", appid_weixin, appid_weixin_unionid);
		logger.warn("=bindWeixinAfter=oauth2UserInfo.subscribe:" + subscribe);

		UserInfo ub = new UserInfo();
		ub.setId(Integer.valueOf(user_id));
		ub.setIs_del(0);
		long cb = getFacade().getUserInfoService().getUserInfoCount(ub);
		if (cb <= 0) {
			String msg = "绑定用户不存在";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		UserInfo uijudge = new UserInfo();
		uijudge.setAppid_weixin(appid_weixin);
		uijudge.setIs_del(0);
		int count = getFacade().getUserInfoService().getUserInfoCount(uijudge);
		if (count > 0) {
			String msg = "对不起，此微信号已经被其他用户绑定了，请确认后重新绑定！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setAppid_weixin(appid_weixin);
		// userInfo.setUser_logo(headimgurl);
		userInfo.setId(Integer.valueOf(user_id));
		userInfo.setAppid_weixin_unionid(appid_weixin_unionid);
		super.getFacade().getUserInfoService().modifyUserInfo(userInfo);

		UserInfo ui = new UserInfo();
		ui.setId(Integer.valueOf(user_id));
		ui = getFacade().getUserInfoService().getUserInfo(ui);
		super.setUserInfoToSession(request, ui);

		String msg = "恭喜，绑定微信成功";
		String ctx = super.getCtxPath(request);
		// String toGoUrl = "http://" + Keys.app_domain_m;
		String toGoUrl = ctx + "/m/MMyHome.do";

		return super.showTipMsg(mapping, form, request, response, msg, toGoUrl);

	}

	// public ActionForward bf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// // DynaBean dynaBean = (DynaBean) form;
	//
	// String msg = "恭喜，绑定微信成功";
	// String ctx = super.getCtxPath(request);
	// // String toGoUrl = "http://" + Keys.app_domain_m;
	// String toGoUrl = ctx + "/m/MMyHome.do";
	//
	// return super.showTipMsg(mapping, form, request, response, msg, toGoUrl);
	//
	// }

	public ActionForward bindExistUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String hasUserName = (String) dynaBean.get("hasUserName");
		String hasUserPassword = (String) dynaBean.get("hasUserPassword");
		String appid_weixin = (String) dynaBean.get("appid_weixin");
		String appid_weixin_unionid = (String) dynaBean.get("appid_weixin_unionid");
		String real_name = (String) dynaBean.get("real_name");
		String user_logo = (String) dynaBean.get("user_logo");
		String subscribe = (String) dynaBean.get("subscribe");
		String appid_qq = (String) dynaBean.get("appid_qq");
		String appid_weibo = (String) dynaBean.get("appid_weibo");

		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(hasUserPassword)) {
			data.put("ret", "0");
			data.put("msg", "密码不能为空,CODE:8001");
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.isBlank(hasUserName)) {
			data.put("ret", "0");
			data.put("msg", "用户名或手机号参数不正确,CODE:8002");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.getMap().put("ym_id", hasUserName);
		// entity.setUser_name(hasUserName);
		entity.setIs_del(0);
		List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(entity);

		if (null == userInfoList || userInfoList.size() == 0) {
			data.put("ret", "0");
			data.put("msg", "未查询到用户！");
			super.renderJson(response, data.toString());
			return null;
		}
		UserInfo userInfo = super.getUserInfo(userInfoList.get(0).getId());

		if (!EncryptUtilsV2.MD5Encode(hasUserPassword).equalsIgnoreCase(userInfo.getPassword())) {
			data.put("ret", "0");
			data.put("msg", "密码不正确！");
			super.renderJson(response, data.toString());
			return null;
		}

		if (StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			data.put("ret", "0");
			data.put("msg", "该用户已经绑定了微信，如需再次绑定，请联系管理员！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo userInfoQuery = new UserInfo();
		userInfoQuery.setAppid_weixin(appid_weixin);
		int count2 = super.getFacade().getUserInfoService().getUserInfoCount(userInfoQuery);
		if (count2 > 0) {
			data.put("ret", "0");
			data.put("msg", "该微信账号已经绑定过其他用户，如需再次绑定，请联系管理员！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entityUpdate = new UserInfo();
		entityUpdate.setId(userInfo.getId());
		entityUpdate.setAppid_weixin(appid_weixin);
		entityUpdate.setAppid_weixin_unionid(appid_weixin_unionid);
		entityUpdate.setAppid_qq(appid_qq);
		entityUpdate.setAppid_weibo(appid_weibo);
		entityUpdate.setReal_name(real_name);
		entityUpdate.setUser_logo(user_logo);
		if (GenericValidator.isInt(subscribe)) {
			entityUpdate.setAppid_weixin_is_follow(Integer.valueOf(subscribe));
		}

		int count = super.getFacade().getUserInfoService().modifyUserInfo(entityUpdate);
		if (count > 0) {

			HttpSession session = request.getSession();

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

			data.put("ret", "1");
			data.put("msg", "绑定成功！");
			super.renderJson(response, data.toString());
			return null;

		} else {
			data.put("ret", "0");
			data.put("msg", "更新用户信息失败！");
			super.renderJson(response, data.toString());
			return null;
		}

	}

	public ActionForward bindMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String return_url = (String) dynaBean.get("return_url");
		String mobile = (String) dynaBean.get("mobile");

		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(user_id) || StringUtils.isBlank(return_url) || StringUtils.isBlank(mobile)) {
			data.put("ret", "0");
			data.put("msg", "参数错误");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.setId(Integer.valueOf(user_id));
		entity.setIs_del(0);
		entity = super.getFacade().getUserInfoService().getUserInfo(entity);

		if (null == entity) {
			data.put("ret", "0");
			data.put("msg", "未查询到用户！");
			super.renderJson(response, data.toString());
			return null;
		}
		UserInfo userInfoUpdate = new UserInfo();
		userInfoUpdate.setUser_name(mobile);
		userInfoUpdate.setMobile(mobile);
		userInfoUpdate.setId(Integer.valueOf(user_id));
		int count = super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);

		if (count > 0) {
			userInfoUpdate = getFacade().getUserInfoService().getUserInfo(userInfoUpdate);
			super.setUserInfoToSession(request, userInfoUpdate);
			data.put("ret", "1");
			data.put("msg", "绑定成功！");
			super.renderJson(response, data.toString());
			return null;
		} else {
			data.put("ret", "0");
			data.put("msg", "更新用户信息失败！");
			super.renderJson(response, data.toString());
			return null;
		}

	}

	public ActionForward onlyCreateUserAndRedirect(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String ymid = request.getParameter("ymid");
		String return_url = request.getParameter("return_url");// 返回URL

		logger.warn("code:" + code + "--state:" + state + "====ymid======" + ymid);
		if (StringUtils.isBlank(code)) {
			String msg = "没有获取到响应参数code";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		Oauth2AccessToken token = Oauth2Api.getOauth2AccessToken(code);
		logger.warn("token:" + token);
		// 这个方法能获取到微信头像和昵称，但是获取不到subscribe
		Oauth2UserInfo oauth2UserInfoWithNicknameAndHeadimgurl = Oauth2Api.oauth2UserInfo(token.getAccess_token(),
				token.getOpenid(), "zh_CN");
		// 注意：如果要获取是否关注微信公众号和UnionID信息，需要用这个方法获取
		Oauth2AccessToken accessToken = Oauth2Api.getAccessToken();// 获取普通的accessToken
		Oauth2UserInfo oauth2UserInfo = Oauth2Api.oauth2UserInfoUnionID(accessToken.getAccess_token(),
				token.getOpenid(), "zh_CN");
		logger.warn("Oauth2UserInfo:" + oauth2UserInfoWithNicknameAndHeadimgurl.toString());
		logger.warn("openId:" + oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid());
		logger.warn("Oauth2UserInfo:" + oauth2UserInfo.toString());
		if ((null == oauth2UserInfoWithNicknameAndHeadimgurl)
				|| (null == oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid())) {
			String msg = "user为空或者Openid为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		String appid_weixin = oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid();
		if (StringUtils.isBlank(appid_weixin)) {
			String msg = "appid_weixin为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		// String appid_weixin_unionid = oauth2UserInfoWithNicknameAndHeadimgurl.getUnionid();
		String appid_weixin_unionid = oauth2UserInfo.getUnionid();
		String subscribe = oauth2UserInfo.getSubscribe();// 是否关注 0 未关注 1 已关注
		String nickname = oauth2UserInfoWithNicknameAndHeadimgurl.getNickname();
		String headimgurl = oauth2UserInfoWithNicknameAndHeadimgurl.getHeadimgurl();

		// 根据UnionID判断是否存在用户
		UserInfo userInfo = new UserInfo();
		if(appid_weixin_unionid == null || "".equals(appid_weixin_unionid)){
			//userInfo.setAppid_weixin_unionid(appid_weixin_unionid);
		}else{
			
		}
		userInfo.setAppid_weixin(appid_weixin);
		userInfo.setIs_del(0);
		long count = getFacade().getUserInfoService().getUserInfoCount(userInfo);

		if (count > 0) {
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			super.setUserInfoToSession(request, userInfo);

			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			ui.setLast_login_ip(this.getIpAddr(request));
			ui.setLast_login_time(new Date());
			getFacade().getUserInfoService().modifyUserInfo(ui);

			super.createSysOperLog(request, Keys.SysOperType.SysOperType_11.getIndex(), "用户登录", "用户微信登录");
			response.sendRedirect(return_url);
			return null;
		} else {
			// 根据appid_weixin判断是否存在用户
			UserInfo userInfo2 = new UserInfo();
			userInfo2.setAppid_weixin(appid_weixin);
			userInfo2.setIs_del(0);
			long i = getFacade().getUserInfoService().getUserInfoCount(userInfo2);
			if (i > 0) {
				userInfo2 = getFacade().getUserInfoService().getUserInfo(userInfo2);
				super.setUserInfoToSession(request, userInfo2);

				UserInfo ui = new UserInfo();
				ui.setId(userInfo2.getId());
				ui.setLogin_count(userInfo2.getLogin_count() + 1);
				ui.setLast_login_ip(this.getIpAddr(request));
				ui.setLast_login_time(new Date());
				ui.setAppid_weixin_unionid(appid_weixin_unionid);// 将UnionID保存到用户信息
				getFacade().getUserInfoService().modifyUserInfo(ui);

				super.createSysOperLog(request, Keys.SysOperType.SysOperType_11.getIndex(), "用户登录", "用户微信登录");
				response.sendRedirect(return_url);
				return null;
			} else {// 创建用户
				DynaBean db = (DynaBean) form;
				if (StringUtils.isNotBlank(ymid)) {
					db.set("ymid", ymid);
				}
				db.set("nickname", nickname);
				db.set("appid_weixin", appid_weixin);
				db.set("appid_weixin_unionid", appid_weixin_unionid);
				db.set("headimgurl", headimgurl);
				db.set("subscribe", subscribe);
				db.set("return_url", return_url);

				request.setAttribute("header_title", "用户注册 - 绑定微信");
				return new ActionForward("/WeixinLogin/formReg.jsp");
			}
		}
	}

	public ActionForward afterLoginWeixinForActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");

		HttpSession session = request.getSession();

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String return_url = request.getParameter("return_url");// 返回URL

		logger.info("code:" + code + "--state:" + state);
		if (StringUtils.isBlank(code)) {
			String msg = "没有获取到响应参数code";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		Oauth2AccessToken token = Oauth2Api.getOauth2AccessToken(code);

		// 这个方法能获取到微信头像和昵称，但是获取不到subscribe
		Oauth2UserInfo oauth2UserInfoWithNicknameAndHeadimgurl = Oauth2Api.oauth2UserInfo(token.getAccess_token(),
				token.getOpenid(), "zh_CN");

		// 注意：如果要获取是否关注微信公众号和UnionID信息，需要用这个方法获取
		Oauth2AccessToken accessToken = Oauth2Api.getAccessToken();// 获取普通的accessToken
		Oauth2UserInfo oauth2UserInfo = Oauth2Api.oauth2UserInfoUnionID(accessToken.getAccess_token(),
				token.getOpenid(), "zh_CN");

		if ((null == oauth2UserInfoWithNicknameAndHeadimgurl)
				|| (null == oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid())) {
			String msg = "user为空或者Openid为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		String appid_weixin = oauth2UserInfoWithNicknameAndHeadimgurl.getOpenid();
		if (StringUtils.isBlank(appid_weixin)) {
			String msg = "appid_weixin为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		return_url += "&openid=" + appid_weixin;

		String appid_weixin_unionid = oauth2UserInfoWithNicknameAndHeadimgurl.getUnionid();
		String subscribe = oauth2UserInfo.getSubscribe();// 是否关注 0 未关注 1 已关注
		String nickname = oauth2UserInfoWithNicknameAndHeadimgurl.getNickname();
		String headimgurl = oauth2UserInfoWithNicknameAndHeadimgurl.getHeadimgurl();

		UserInfo userInfo = new UserInfo();
		userInfo.setAppid_weixin(appid_weixin);
		userInfo.setIs_del(0);
		long count = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		if (count > 0) {
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			super.setUserInfoToSession(request, userInfo);

			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			ui.setLogin_count(userInfo.getLogin_count() + 1);
			ui.setLast_login_ip(this.getIpAddr(request));
			ui.setLast_login_time(new Date());
			getFacade().getUserInfoService().modifyUserInfo(ui);

			super.createSysOperLog(request, Keys.SysOperType.SysOperType_11.getIndex(), "用户登录", "用户微信登录");
			response.sendRedirect(return_url);
			return null;
		} else {// 创建用户

			UserInfo entity = new UserInfo();
			super.copyProperties(entity, form);

			entity.setAppid_weixin(appid_weixin);
			entity.setAppid_weixin_unionid(appid_weixin_unionid);// 保存unionid,测试是否能获取到
			if (GenericValidator.isInt(subscribe)) {
				entity.setAppid_weixin_is_follow(Integer.valueOf(subscribe));
			}
			entity.setUser_logo(headimgurl);
			entity.setUser_name("USER" + new Date().getTime());

			if (StringUtils.isBlank(nickname)) {
				entity.setReal_name(nickname);
			}

			entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());

			DESPlus des = new DESPlus();
			entity.setPassword(des.encrypt(Keys.DEFAULT_PASSWORD));
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(new Integer(-1));
			entity.setIs_daqu(0);
			entity.setIs_entp(0);
			entity.setIs_fuwu(0);
			entity.setIs_lianmeng(0);
			entity.setIs_active(1);
			entity.setIs_has_update_pass(1);
			String ymid = "admin";

			if (StringUtils.isNotBlank(ymid)) {
				entity.getMap().put("insert_user_realtion", "true");
				entity.setYmid(ymid);
			}

			entity.setLogin_count(1);
			entity.setLast_login_time(new Date());
			entity.setLast_login_ip(this.getIpAddr(request));

			entity.getMap().put("update_user_name", "true");
			int id = super.getFacade().getUserInfoService().createUserInfo(entity);

			UserInfo ui = new UserInfo();
			ui.setId(id);
			userInfo = getFacade().getUserInfoService().getUserInfo(ui);

			super.createSysOperLog(request, Keys.SysOperType.SysOperType_11.getIndex(), "用户登录", "用户微信登录");

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

		}

		response.sendRedirect(return_url);
		return null;
	}
}
