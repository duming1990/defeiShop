package com.ebiz.webapp.web.struts;

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
import org.springframework.web.util.CookieGenerator;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.util.RegexUtils;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EmailUtils;
import com.ebiz.webapp.web.util.SmsUtils;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

public class RegisterAction extends BaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		saveToken(request);

		// request.setAttribute("isEnabledMobileVeriCode", super.getSysSetting("isEnabledMobileVeriCode"));
		// return new ActionForward("/register/register.jsp");
		return new ActionForward("/register/register_no_username.jsp");
	}

	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			return unspecified(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return unspecified(mapping, form, request, response);
		}

		HttpSession session = request.getSession();
		DynaBean dynaBean = (DynaBean) form;

		String user_name = (String) dynaBean.get("user_name");
		String real_name = (String) dynaBean.get("real_name");

		String password = (String) dynaBean.get("_password");
		String mobile = (String) dynaBean.get("mobile");
		String ymid = (String) dynaBean.get("ymid");
		String verifycode = (String) dynaBean.get("verifycode");

		boolean _mobile = RegexUtils.checkMobile(mobile);
		if (_mobile == false) {
			String msg = mobile + "手机号码格式不正确";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}
		if (StringUtils.isBlank(verifycode)) {
			String msg = mobile + "手机动态码不能为空";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		if (!(verifycode.equalsIgnoreCase((String) request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			String msg = mobile + "手机动态码填写有误";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		if (StringUtils.isBlank(ymid)) {
			String msg = "邀请人不能为空";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		UserInfo entityQueryHasExist = new UserInfo();
		entityQueryHasExist.getMap().put("ym_id", ymid);
		entityQueryHasExist.setIs_del(0);
		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entityQueryHasExist);
		if (recordCount.intValue() <= 0) {
			String msg = "未查询到该邀请人";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;

		}
		if (recordCount > 0) {
			entityQueryHasExist = super.getFacade().getUserInfoService().getUserInfo(entityQueryHasExist);
			if (entityQueryHasExist.getIs_entp().intValue() == 1 || entityQueryHasExist.getIs_fuwu().intValue() == 1
					|| entityQueryHasExist.getUser_type().intValue() == Keys.UserType.USER_TYPE_1.getIndex()) {// 这几个类型不能作为推荐人
				String msg = "该用户不能作为推荐人";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg
						+ "');location.href='Register.do'}");
				return null;
			}
		}

		if (recordCount.intValue() > 1) {
			String msg = "上级用户重复，请联系管理员";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		UserInfo ui1 = new UserInfo();
		ui1.setUser_name(user_name);
		ui1.setIs_del(0);
		int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count1 > 0) {
			String msg = "你的用户名太响亮了，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ui1 = new UserInfo();
		ui1.setUser_name(user_name);
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count2 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count2 > 0) {
			String msg = "用户名或者手机号，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ui1 = new UserInfo();
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count3 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count3 > 0) {
			String msg = "手机号，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ui1 = new UserInfo();
		ui1.setUser_name(mobile);// 防止这个人的手机号是另一个人的用户名
		ui1.setIs_del(0);
		int count4 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count4 > 0) {
			String msg = "用户名，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		resetToken(request);

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(real_name)) {
			entity.setReal_name(user_name);
		}
		entity.setUser_type(2);
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		// entity.setPassword(EncryptUtilsV2.MD5Encode(password));
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(new Integer(1));
		entity.setIs_daqu(0);
		entity.setIs_entp(0);
		entity.setIs_fuwu(0);
		entity.setIs_lianmeng(0);
		entity.setIs_active(1);
		entity.setIs_has_update_pass(1);

		// TAG 20160107
		// BaseData baseData = super.getBaseData(Keys.BiGetType.BI_GET_TYPE_802.getIndex());
		// entity.setBi_xiaofei(new BigDecimal(baseData.getPre_number()));
		// entity.getMap().put("xiaofeibi", baseData.getPre_number());

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

		super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");

		boolean isMoblie = super.JudgeIsMoblie(request);
		if (isMoblie) {// 手机版
			String ctx = super.getCtxPath(request);
			return new ActionForward(ctx + "/m/MMyHome.do", true);
		} else {
			return new ActionForward("/manager/customer/Index.do", true);
		}

		// String msg = "注册成功，请您登陆！";
		// super.renderJavaScript(response, "window.onload=function(){alert('" + msg +
		// "');location.href='login.shtml'}");
		// return null;
	}

	public ActionForward registerNoUserName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			return unspecified(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return unspecified(mapping, form, request, response);
		}

		HttpSession session = request.getSession();
		DynaBean dynaBean = (DynaBean) form;

		// String user_name = (String) dynaBean.get("user_name");
		String real_name = (String) dynaBean.get("real_name");

		String password = (String) dynaBean.get("password");
		String mobile = (String) dynaBean.get("mobile");
		String ymid = (String) dynaBean.get("ymid");
		String verifycode = (String) dynaBean.get("verifycode");

		boolean _mobile = RegexUtils.checkMobile(mobile);
		if (_mobile == false) {
			String msg = mobile + "手机号码格式不正确";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}
		if (StringUtils.isBlank(verifycode)) {
			String msg = mobile + "手机动态码不能为空";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		if (!(verifycode.equalsIgnoreCase((String) request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			String msg = mobile + "手机动态码填写有误";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		if (StringUtils.isBlank(ymid)) {
			UserInfo admin = new UserInfo();
			admin.setUser_type(Keys.UserType.USER_TYPE_1.getIndex());
			admin.setIs_del(0);
			admin = getFacade().getUserInfoService().getUserInfo(admin);
			ymid = admin.getUser_name();
		}

		UserInfo entityQueryHasExist = new UserInfo();
		entityQueryHasExist.getMap().put("ym_id", ymid);
		entityQueryHasExist.setIs_del(0);
		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entityQueryHasExist);
		if (recordCount.intValue() <= 0) {
			String msg = "未查询到该邀请人";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		if (recordCount.intValue() > 1) {
			String msg = "上级用户重复，请联系管理员";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Register.do'}");
			return null;
		}

		UserInfo ui1 = new UserInfo();
		ui1.setUser_name(mobile);
		ui1.setIs_del(0);
		int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count1 > 0) {
			String msg = "你的用户名太响亮了，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ui1 = new UserInfo();
		ui1.setUser_name(mobile);
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count2 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count2 > 0) {
			String msg = "用户名或者手机号，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ui1 = new UserInfo();
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count3 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count3 > 0) {
			String msg = "手机号，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ui1 = new UserInfo();
		ui1.setUser_name(mobile);// 防止这个人的手机号是另一个人的用户名
		ui1.setIs_del(0);
		int count4 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count4 > 0) {
			String msg = "用户名，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		resetToken(request);

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(real_name)) {
			entity.setReal_name(mobile);
		}
		entity.setUser_name(mobile);
		entity.setUser_type(2);
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		// entity.setPassword(EncryptUtilsV2.MD5Encode(password));
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(new Integer(1));
		entity.setIs_daqu(0);
		entity.setIs_entp(0);
		entity.setIs_fuwu(0);
		entity.setIs_lianmeng(0);
		entity.setIs_active(1);
		entity.setIs_has_update_pass(1);

		// TAG 20160107
		// BaseData baseData = super.getBaseData(Keys.BiGetType.BI_GET_TYPE_802.getIndex());
		// entity.setBi_xiaofei(new BigDecimal(baseData.getPre_number()));
		// entity.getMap().put("xiaofeibi", baseData.getPre_number());

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

		super.createSysOperLog(request, Keys.SysOperType.SysOperType_10.getIndex(), "用户登录", "用户登录");

		// boolean isMoblie = super.JudgeIsMoblie(request);
		// if (isMoblie) {// 手机版
		// String ctx = super.getCtxPath(request);
		// return new ActionForward(ctx + "/m/MMyHome.do", true);
		// } else {
		return new ActionForward("/manager/customer/Index.do", true);
		// }

		// String msg = "注册成功，请您登陆！";
		// super.renderJavaScript(response, "window.onload=function(){alert('" + msg +
		// "');location.href='login.shtml'}");
		// return null;
	}

	public ActionForward validateMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String state = "0";

		if (StringUtils.isNotBlank(mobile)) {
			UserInfo entity = new UserInfo();
			// entity.setMobile(mobile);
			entity.getMap().put("ym_id", mobile);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() <= 0) { // 手机号可用
				state = "1";
			} else if (recordCount.intValue() > 0) { // 手机号重复
				state = "2";
			}
		}
		super.renderJson(response, state);
		return null;
	}

	public ActionForward validateMobileForWeiXin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String ret = "0";

		JSONObject data = new JSONObject();

		if (StringUtils.isNotBlank(mobile)) {
			UserInfo entity = new UserInfo();
			entity.getMap().put("ym_id", mobile);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() <= 0) { // 手机号可用
				ret = "1";
			} else if (recordCount.intValue() > 0) { // 手机号重复
				ret = "2";
				List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(entity);
				data.put("user_id", userInfoList.get(0).getId());
			}
		}

		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward validateYmId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String ymid = (String) dynaBean.get("ymid");

		String state = "0";
		if (StringUtils.isNotBlank(ymid)) {
			UserInfo entity = new UserInfo();
			entity.getMap().put("ym_id", ymid);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() <= 0) {
				state = "1";
			}
			if (recordCount > 0) {
				entity = super.getFacade().getUserInfoService().getUserInfo(entity);
				if (entity.getIs_entp().intValue() == 1 || entity.getIs_fuwu().intValue() == 1
						|| entity.getUser_type().intValue() == Keys.UserType.USER_TYPE_1.getIndex()) {// 这几个类型不能作为推荐人
					state = "2";
				}
			}
		}
		super.renderJson(response, state);
		return null;
	}

	public ActionForward validateName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		StringBuffer oper = new StringBuffer("{\"result\":");
		String user_name = (String) dynaBean.get("user_name");
		UserInfo entity = new UserInfo();
		// entity.setUser_name(user_name);
		entity.getMap().put("ym_id", user_name);
		entity.setIs_del(0);
		int recordCount = super.getFacade().getUserInfoService().getUserInfoCount(entity);
		if (recordCount <= 0) { // 手机号可用
			oper.append(false);
		} else if (recordCount > 0) { // 手机号重复
			oper.append(true);
		}
		oper.append("}");
		super.renderJson(response, oper.toString());
		return null;
	}

	public ActionForward validateNameForWeiXin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String user_name = (String) dynaBean.get("user_name");
		UserInfo entity = new UserInfo();
		entity.getMap().put("ym_id", user_name);
		entity.setIs_del(0);
		JSONObject data = new JSONObject();
		String ret = "0";
		int recordCount = super.getFacade().getUserInfoService().getUserInfoCount(entity);
		if (recordCount > 0) { // 手机号可用
			ret = "1";
			List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(entity);
			data.put("user_id", userInfoList.get(0).getId());
		}
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward listPdClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cls_name_like = (String) dynaBean.get("cls_name_like");
		String hy_cls_id = (String) dynaBean.get("hy_cls_id");
		String main_pd_class_ids = (String) dynaBean.get("main_pd_class_ids");
		String main_pd_class_names = (String) dynaBean.get("main_pd_class_names");
		String is_xianxia = (String) dynaBean.get("is_xianxia");
		logger.info("===is_xianxia:" + is_xianxia);

		BaseClass baseClass = new BaseClass();

		baseClass.setIs_del(0);
		if (StringUtils.isNotBlank(cls_name_like)) {
			baseClass.getMap().put("cls_name_like", cls_name_like);
		}
		if (StringUtils.isNotBlank(hy_cls_id)) {
			logger.info("==hy_cls_id:" + hy_cls_id);
			baseClass.setPar_id(Integer.valueOf(hy_cls_id));
		}

		baseClass.setCls_scope(1);

		if (StringUtils.isNotBlank(is_xianxia)) {
			baseClass.setCls_scope(2);
		}
		baseClass.setCls_level(2);

		List<BaseClass> baseClassList = getFacade().getBaseClassService().getBaseClassList(baseClass);
		request.setAttribute("baseClassList", baseClassList);

		if (StringUtils.isNotBlank(main_pd_class_ids)) {
			BaseClass baseClass2 = new BaseClass();
			baseClass2.setIs_del(0);
			baseClass2.setCls_scope(1);
			if (StringUtils.isNotBlank(is_xianxia)) {
				baseClass.setCls_scope(2);
			}
			baseClass2.setCls_level(2);
			baseClass2.getMap().put("cls_ids", main_pd_class_ids);
			List<BaseClass> baseClass2List = getFacade().getBaseClassService().getBaseClassList(baseClass2);
			request.setAttribute("baseClass2List", baseClass2List);
		}
		if (StringUtils.isNotBlank(is_xianxia)) {
			dynaBean.set("cls_scope", 2);
		}
		dynaBean.set("main_pd_class_ids", main_pd_class_ids);
		dynaBean.set("main_pd_class_names", main_pd_class_names);
		return new ActionForward("/register/listPdClass.jsp");
	}

	public ActionForward validateEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String email = (String) dynaBean.get("email");
		String state = "0";

		if (StringUtils.isNotBlank(email)) {
			UserInfo entity = new UserInfo();
			entity.setEmail(email);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() <= 0) { // 邮箱可用
				state = "1";
			} else if (recordCount.intValue() > 0) { // 邮箱重复
				state = "2";
			}
		}
		super.renderJson(response, state);
		return null;
	}

	public ActionForward sendEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String email = (String) dynaBean.get("email");
		String state = "0";
		JSONObject obj = new JSONObject();

		if (StringUtils.isNotBlank(email)) {
			HttpSession session = request.getSession();

			// 随机生成6位整数
			String ranNm = SmsUtils.generateCheckPass();

			String msg = getMessage(request, "send.email", new String[] { ranNm });

			EmailUtils.sendEmail(Keys.app_name + "邮箱验证", msg, email);

			session.setAttribute(Keys.EMAIL_VERI_CODE, ranNm);
			obj.put("veriCode", ranNm);
			state = "1";
		}
		obj.put("state", state);
		super.renderJson(response, obj.toString());
		return null;
	}

	public ActionForward getUserXyInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取用户协议的信息
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setMod_id("1019015000");
		newsInfo = super.getFacade().getNewsInfoService().getNewsInfo(newsInfo);

		request.setAttribute("newsInfo", newsInfo);
		return new ActionForward("/register/getUserXyInfo.jsp");
	}

	public ActionForward getUserXyInfoAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取用户协议的信息
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setMod_id("1019015000");
		newsInfo = super.getFacade().getNewsInfoService().getNewsInfo(newsInfo);
		JSONObject data = new JSONObject();
		data.put("newsInfo", newsInfo);
		return returnAjaxData(response, "", "", data);
	}

	public ActionForward sendMobileMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String veri_code = (String) dynaBean.get("veri_code");
		String type = (String) dynaBean.get("type");
		String bdType = (String) dynaBean.get("bdType"); // 绑定type
		String isBdOrSet = (String) dynaBean.get("isBdOrSet"); // 绑定type
		String isValMobile = (String) dynaBean.get("isValMobile"); // 是否验证手机号
		String ret = "0";
		String msg = "";
		JSONObject datas = new JSONObject();

		String veri_code_session = (String) request.getSession().getAttribute(Keys.VERIFICATION_CODE);
		if (!GenericValidator.isLong(veri_code)) {
			msg = "请输入验证码";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}
		if (StringUtils.isBlank(veri_code_session)) {
			msg = "veri_code_session为空";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}
		if (!veri_code.equals(veri_code_session)) {
			msg = "验证码输入不正确";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}
		if (StringUtils.isBlank(mobile)) {
			msg = "请输入手机号码";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}

		if (StringUtils.isNotBlank(isValMobile)) {
			UserInfo entity = new UserInfo();
			entity.getMap().put("ym_id", mobile);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() > 0) { // 手机号重复
				msg = "该手机号码已被占用";
				datas.put("ret", ret);
				datas.put("msg", msg);
				super.renderJson(response, datas.toString());
				return null;
			}
		}

		HttpSession session = request.getSession();

		if (StringUtils.isBlank(type)) {
			type = "1";
		}

		// 随机生成6位整数
		String ranNm = SmsUtils.generateCheckPass();
		StringBuffer message = new StringBuffer("");
		String template_code = "";
		if (type.equals("1")) {
			// message = StringUtils.replace(SMS.sms_01, "{0}", ranNm);
			message.append("{\"code\":\"" + ranNm + "\"}");
			template_code = SmsTemplate.sms_01;
		} else if (type.equals("2")) {
			// message = StringUtils.replace(SMS.sms_02, "{0}", ranNm);
			message.append("{\"code\":\"" + ranNm + "\"}");
			template_code = SmsTemplate.sms_02;
		} else if (type.equals("3")) {
			// 换绑
			// message = StringUtils.replace(SMS.sms_03, "{0}", ranNm);
			message.append("{\"code\":\"" + ranNm + "\"");
			template_code = SmsTemplate.sms_03;
			// 设置
			if (StringUtils.isNotBlank(isBdOrSet) && isBdOrSet.equals("0")) {
				// message = StringUtils.replace(SMS.sms_03_2, "{0}", ranNm);
				template_code = SmsTemplate.sms_03_2;
			}

			if (StringUtils.isBlank(bdType)) {
				bdType = "1";
			}
			if (bdType.equals("1")) {
				// message = StringUtils.replace(message, "{1}", SMS.hb_01);
				// message = StringUtils.replace(message, "{2}", SMS.hb_01);
				message.append(",\"name\":\"" + SMS.hb_01 + "\"");
			} else if (bdType.equals("2")) {
				// message = StringUtils.replace(message, "{1}", SMS.hb_02);
				// message = StringUtils.replace(message, "{2}", SMS.hb_02);
				message.append(",\"name\":\"" + SMS.hb_02 + "\"");
			} else if (bdType.equals("3")) {
				// message = StringUtils.replace(message, "{1}", SMS.hb_03);
				// message = StringUtils.replace(message, "{2}", SMS.hb_03);
				message.append(",\"name\":\"" + SMS.hb_03 + "\"");
			}
			message.append("}");
		} else if (type.equals("zhzx")) {
			// message = StringUtils.replace(SMS.sms_02, "{0}", ranNm);
			message.append("{\"code\":\"" + ranNm + "\"}");
			template_code = SmsTemplate.sms_zhzx;
		}

		boolean is_local = false;
		is_local = super.isLocal(request);
		if (StringUtils.isNotBlank(message.toString())) {
			String result = "1";
			if (!is_local) {// 不是本地，发送短信
				// result = SmsUtils.sendMessage(message, mobile);
				SendSmsResponse sendSmsResponse = DySmsUtils.sendMessage(message.toString(), mobile, template_code);
				if ("OK".equals(sendSmsResponse.getCode())) {
					result = "0";
				}
			} else {// 如果是本地，则短信验证码为111111
				result = "0";
				ranNm = "111111";
			}

			logger.warn("==send result:{}", result);
			logger.warn("==send ranNm:{}", ranNm);
			if ("0".equals(result)) {
				session.setAttribute(Keys.MOBILE_VERI_CODE, ranNm);
				session.setAttribute(Keys.MOBILE_CODE, mobile);
				datas.put("veriCode", ranNm); // 测试
				ret = "1";
				msg = "发送成功";
			} else {
				ret = "0";
				msg = "发送失败，请稍后重试";
			}
		} else {
			ret = "0";
			msg = "发送失败，请稍后重试";
		}

		datas.put("ret", ret);
		datas.put("msg", msg);
		super.renderJson(response, datas.toString());
		return null;
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
}
