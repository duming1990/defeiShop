package com.ebiz.webapp.web.struts;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.AppInfo;
import com.ebiz.webapp.domain.AppTokens;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Factory.MessageFactory;
import com.sun.xml.internal.messaging.saaj.util.Base64;

public class MobaAppAuthAction extends BaseWebAction {

	public ActionForward sign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String app_key = (String) dynaBean.get("app_key");// 应用编码
		String app_secret = (String) dynaBean.get("app_secret");// 应用密钥
		if (StringUtils.isBlank(app_key) || StringUtils.isBlank(app_secret)) {
			StringBuffer result = new StringBuffer();
			result.append("{\"code\":\"101\",\"msg\":\"参数为空，认证失败\"}");
			super.renderJson(response, result.toString());
			return null;
		} else {
			AppInfo appInfo = new AppInfo();
			appInfo.setApp_key(app_key);
			appInfo.setApp_secret(app_secret);
			appInfo = super.getFacade().getAppInfoService().getAppInfo(appInfo);
			if (null == appInfo) {
				StringBuffer result = new StringBuffer();
				result.append("{\"code\":\"102\",\"msg\":\"应用编码或应用密钥错误，认证失败\"}");
				super.renderJson(response, result.toString());
				return null;
			}

			String timestamp = String.valueOf(new Date().getTime());
			String token = EncryptUtilsV2.MD5Encode(
					"app_key=" + app_key + "&app_secret=" + app_secret + "&timestamp=" + timestamp + "#").toLowerCase();

			// 生成数字签名证书，注意证书只能使用一次，使用后立即删除
			AppTokens appTokens = new AppTokens();
			appTokens.setApp_key(app_key);
			appTokens.setToken(token);
			appTokens.setAdd_date(new Date());
			super.getFacade().getAppTokensService().createAppTokens(appTokens);

			StringBuffer result = new StringBuffer();
			result.append("{\"code\":\"100\",\"msg\":\"认证成功\",\"token\":\"" + token + "\"}");
			super.renderJson(response, result.toString());
			return null;
		}
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String app_key = (String) dynaBean.get("app_key");// 应用编码
		String token = (String) dynaBean.get("token");// 签名token
		String mobile = (String) dynaBean.get("mobile");// 当前用户
		String par_mobile = (String) dynaBean.get("par_mobile");// 上级用户
		String redirect_url = (String) dynaBean.get("redirect_url");

		String ctx = super.getCtxPath(request);

		if (StringUtils.isBlank(app_key) || StringUtils.isBlank(token) || StringUtils.isBlank(mobile)
				|| StringUtils.isBlank(par_mobile)) {
			// StringBuffer result = new StringBuffer();
			// result.append("{\"code:\":\"103\",\"msg\":\"参数为空，登陆失败\"}");
			// super.renderJson(response, result.toString());
			// return null;
			String tip = "参数为空，登陆失败";
			response.sendRedirect(ctx + "/m/Tip/login_fail.html?tip=" + URLEncoder.encode(tip, "UTF-8"));
			return null;
		}

		AppTokens appTokens = new AppTokens();
		appTokens.setApp_key(app_key);
		appTokens.setToken(token);
		appTokens = super.getFacade().getAppTokensService().getAppTokens(appTokens);
		if (null == appTokens) {
			// StringBuffer result = new StringBuffer();
			// result.append("{\"code:\":\"104\",\"msg\":\"签名失效，请重新认证\"}");
			// super.renderJson(response, result.toString());
			// return null;
			// 签名失效，请重新认证
			String tip = "签名失效，请重新认证";
			response.sendRedirect(ctx + "/m/Tip/login_fail.html?tip=" + URLEncoder.encode(tip, "UTF-8"));
			return null;
		}

		// 数字签名证书只能使用一次，使用后立即删除
		AppTokens appTokensDel = new AppTokens();
		appTokensDel.setApp_key(app_key);
		appTokensDel.setToken(token);
		super.getFacade().getAppTokensService().removeAppTokens(appTokensDel);

		HttpSession session = request.getSession();

		String msg = null;

		mobile = mobile.trim();
		par_mobile = par_mobile.trim();

		if (mobile.equals(par_mobile)) {
			String tip = "登陆用户与上级用户不能为同一手机号";
			response.sendRedirect(ctx + "/m/Tip/login_fail.html?tip=" + URLEncoder.encode(tip, "UTF-8"));
			return null;
		}
		UserInfo entity = new UserInfo();
		entity.setMobile(mobile);
		entity.setIs_del(0);
		Integer m_count = getFacade().getUserInfoService().getUserInfoCount(entity);
		if (m_count.intValue() == 0) {// 用户名或者手机不存在自动创建新会员用户
			UserInfo entityQueryHasExist = new UserInfo();
			entityQueryHasExist.getMap().put("ym_id", par_mobile);
			entityQueryHasExist.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entityQueryHasExist);
			if (recordCount.intValue() <= 0) {
				String tip = "未查询到该邀请人";
				response.sendRedirect(ctx + "/m/Tip/login_fail.html?tip=" + URLEncoder.encode(tip, "UTF-8"));
				return null;

			}
			if (recordCount.intValue() > 1) {
				String tip = "邀请人用户重复，请联系管理员";
				response.sendRedirect(ctx + "/m/Tip/login_fail.html?tip=" + URLEncoder.encode(tip, "UTF-8"));
				return null;
			}

			entityQueryHasExist = super.getFacade().getUserInfoService().getUserInfo(entityQueryHasExist);
			UserInfo createUserInfo = new UserInfo();
			createUserInfo.setUser_name("UC" + super.createUserNo());
			createUserInfo.setMobile(mobile);
			createUserInfo.setYmid(entityQueryHasExist.getUser_name());// 邀请人
			createUserInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			createUserInfo.setUser_level(Keys.USER_LEVEL_FX);
			createUserInfo.setApp_key(app_key);
			DESPlus des = new DESPlus();
			createUserInfo.setPassword(des.encrypt(Keys.INIT_PWD));
			createUserInfo.setLogin_count(new Integer("0"));
			createUserInfo.setAdd_user_id(1);
			createUserInfo.setAdd_date(new Date());
			createUserInfo.setIs_active(1);// 已激活
			createUserInfo.getMap().put("insert_user_realtion", "true");
			super.getFacade().getUserInfoService().createUserInfo(createUserInfo);
		} else if (m_count.intValue() > 1) {
			String tip = "登录失败，手机号重复";
			response.sendRedirect(ctx + "/m/Tip/login_fail.html?tip=" + URLEncoder.encode(tip, "UTF-8"));
			return null;
		}

		entity.setIs_active(1);// 已激活
		entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		// entity.setApp_key(app_key);//只能自动登陆当前APP_KEY下的注册会员
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == userInfo) {
			msg = super.getMessage(request, "login.failed.password.invalid");
			/**
			 * 添加登陆日志
			 */
			MessageInfo info = new MessageInfo();
			info.setUserInfo(userInfo);
			info.setMessageCotent("移动端" + mobile + "登陆失败");
			info.setIp(getIpAddr(request));
			info.setSysOperLogService(this.getFacade().getSysOperLogService());

			info.setMessageType(Keys.SysOperType.SysOperType_11.getIndex());
			MessageFactory.sendMessage(MessageFactory.Login, info, null);

			String tip = "无效用户，登陆失败";
			response.sendRedirect(ctx + "/m/Tip/login_fail.html?fail_tip=" + URLEncoder.encode(tip, "UTF-8"));
			return null;
			// return super.showTipNotLogin(mapping, form, request, response, msg);
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

			// this.setCookieUserinfoKeyJsessionid(request, response);
			if (StringUtils.isNotBlank(redirect_url)) {// 自动跳转至returnUrl
				redirect_url = Base64.base64Decode(redirect_url);
				response.sendRedirect(redirect_url);
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

	public ActionForward products(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		DynaBean dynaBean = (DynaBean) form;
		String msg = "", code = "100";
		String keyword = (String) dynaBean.get("keyword");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String p_index = (String) dynaBean.get("p_index");
		String hdtype = (String) dynaBean.get("hdtype");
		String isIndex = (String) dynaBean.get("isIndex");
		String is_zingying = (String) dynaBean.get("is_zingying");
		String is_aid = (String) dynaBean.get("is_aid");

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		logger.warn("=startPage={}", startPage);
		logger.warn("=pageSize={}", pageSize);

		// ------------------安全认证 start-----------------------
		String app_key = (String) dynaBean.get("app_key");// 应用编码
		String token = (String) dynaBean.get("token");// 签名token
		if (StringUtils.isBlank(app_key) || StringUtils.isBlank(token)) {
			StringBuffer result = new StringBuffer();
			result.append("{\"code\":\"103\",\"msg\":\"参数为空，调用失败\"}");
			super.renderJson(response, result.toString());
			return null;
		}
		AppTokens appTokens = new AppTokens();
		appTokens.setApp_key(app_key);
		appTokens.setToken(token);
		appTokens = super.getFacade().getAppTokensService().getAppTokens(appTokens);
		if (null == appTokens) {
			StringBuffer result = new StringBuffer();
			result.append("{\"code\":\"104\",\"msg\":\"签名失效，请重新认证\"}");
			super.renderJson(response, result.toString());
			return null;
		}
		// 数字签名证书只能使用一次，使用后立即删除
		AppTokens appTokensDel = new AppTokens();
		appTokensDel.setApp_key(app_key);
		appTokensDel.setToken(token);
		super.getFacade().getAppTokensService().removeAppTokens(appTokensDel);
		// ------------------安全认证 end-----------------------

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "6";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		List<CommInfo> entityList = new ArrayList<CommInfo>();
		if (Keys.app_cls_level.equals("3")) {

			if (StringUtils.isNotBlank(hdtype) && hdtype.equals("0")) { // 获取活动
				entityList = super.getCommInfoListForJson(null, false, null, false, false, orderByParam, true, pager,
						Integer.valueOf(pageSize), keyword, p_index, root_cls_id, par_cls_id, null,
						Integer.valueOf(startPage), true, is_zingying, is_aid, null, null);
			} else {
				entityList = super.getCommInfoListForJson(null, false, null, false, false, orderByParam, true, pager,
						Integer.valueOf(pageSize), keyword, p_index, root_cls_id, par_cls_id, null,
						Integer.valueOf(startPage), false, is_zingying, is_aid, null, null);
			}

		} else if (Keys.app_cls_level.equals("2")) {
			entityList = super.getCommInfoListForJson(null, false, null, false, false, orderByParam, true, pager,
					Integer.valueOf(pageSize), keyword, p_index, null, root_cls_id, par_cls_id,
					Integer.valueOf(startPage), false, is_zingying, is_aid, null, null);
		}

		String ctx = super.getCtxPath(request);
		if ((null != entityList) && (entityList.size() > 0)) {
			code = "100";
			if (StringUtils.isBlank(isIndex)) {
				for (CommInfo b : entityList) {
					JSONObject map = new JSONObject();
					map.put("id", b.getId());
					map.put("redirect_url", ctx.concat("/").concat("m/MEntpInfo.do?id=").concat(b.getId().toString()));
					String main_pic = b.getMain_pic();
					map.put("comm_name", b.getComm_name());
					map.put("commZyName", b.getMap().get("commZyName"));
					if (StringUtils.isBlank(main_pic)) {
						map.put("main_pic", "/styles/imagesPublic/no_image.jpg");
						map.put("main_pic_400", "/styles/imagesPublic/no_image.jpg");
					} else {
						String min_img = StringUtils.substringBeforeLast(main_pic, ".") + "_400."
								+ FilenameUtils.getExtension(main_pic);
						map.put("main_pic", ctx.concat("/").concat(main_pic));
						map.put("main_pic_400", ctx.concat("/").concat(min_img));
					}
					map.put("sale_price", dfFormat.format(b.getSale_price()));
					long sale_count = b.getSale_count_update();
					if (sale_count > 10000) {
						Double sc = Double.valueOf(sale_count) / 10000;
						map.put("sale_count", dfFormat.format(sc) + "万");
					} else {
						map.put("sale_count", sale_count);
					}
					dataLoadList.add(map);
				}
			} else {
				dataLoadList.addAll(entityList);
			}

		}
		datas.put("dataList", dataLoadList);
		msg = "加载完成";

		if (dataLoadList.size() < 2) {
			code = "200";
			msg = "没有更多数据";
		}
		log.warn("getCommInfoList");
		super.returnInfo(response, code, msg, datas);
		return null;

	}
}