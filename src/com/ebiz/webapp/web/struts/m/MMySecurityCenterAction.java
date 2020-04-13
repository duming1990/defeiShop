package com.ebiz.webapp.web.struts.m;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserSecurity;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,Yang
 * @version 2014-05-28
 */
public class MMySecurityCenterAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "安全中心");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		int percent = 100;
		if (StringUtils.isBlank(user.getPassword())) {
			percent -= 10;
		}
		if (StringUtils.isBlank(user.getMobile())) {
			percent -= 15;
		}
		if (StringUtils.isBlank(user.getEmail())) {
			percent -= 15;
		}
		if (StringUtils.isBlank(user.getPassword_pay())) {
			percent -= 15;
		}
		if (user.getIs_set_security() == 0) {
			percent -= 15;
		}
		if (StringUtils.isBlank(user.getUser_no())) {
			percent -= 15;
		}
		if (StringUtils.isBlank(user.getBank_account())) {
			percent -= 15;
		}

		request.setAttribute("user", user);
		request.setAttribute("percent", percent);

		return mapping.findForward("list");
	}

	public ActionForward setPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "密码设置");
		// DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("user", user);

		return new ActionForward("/../m/MMySecurityCenter/form_setpassword.jsp");
	}

	public ActionForward setMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "换绑手机");

		// DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String isBind = (String) dynaBean.get("isBind");

		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("user", user);
		if (StringUtils.isNotBlank(isBind)) {
			request.setAttribute("header_title", "绑定手机");
			return new ActionForward("/../m/MMySecurityCenter/form_setmobile_bind.jsp");
		} else {
			return new ActionForward("/../m/MMySecurityCenter/form_setmobile.jsp");
		}
	}

	public ActionForward setBank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "设置银行账号");
		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		super.copyProperties(form, user);

		if (null != user.getBank_account()) {
			dynaBean.set("encryptBankAccount", super.encryptBankAccount(user.getBank_account()));
		}
		request.setAttribute("user", user);
		if (null != user.getBank_pindex()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, user.getBank_pindex());
		}

		return new ActionForward("/../m/MMySecurityCenter/form_setbank.jsp");
	}

	public ActionForward setEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("user", user);
		return new ActionForward("/customer/MMySecurityCenter/form_setemail.jsp");
	}

	public ActionForward setUserNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaBean dynaBean = (DynaBean) form;
		request.setAttribute("header_title", "设置会员卡");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("user", user);
		return new ActionForward("/../m/MMySecurityCenter/form_setuserno.jsp");
	}

	public ActionForward setPasswordPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "更换支付密码");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("user", user);
		return new ActionForward("/../m/MMySecurityCenter/form_setpasswordpay.jsp");
	}

	public ActionForward setSecurequestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "设置安全问题");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("user", user);

		request.setAttribute("base3200DataList", super.getBaseDataList(3200));

		List<UserSecurity> userSecurityList = new ArrayList<UserSecurity>();

		UserSecurity userSecurity = new UserSecurity();
		userSecurity.setUser_id(ui.getId());
		userSecurity.setIs_del(0);
		userSecurityList = super.getFacade().getUserSecurityService().getUserSecurityList(userSecurity);

		if (userSecurityList.size() == 0) {
			for (int i = 0; i < 3; i++) {
				UserSecurity userSecurityTemp = new UserSecurity();
				userSecurityList.add(userSecurityTemp);
			}
		}
		request.setAttribute("userSecurityList", userSecurityList);

		return new ActionForward("/../m/MMySecurityCenter/form_setsecurequestion.jsp");
	}

	public ActionForward modifyPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		// String old_password = (String) dynaBean.get("old_password");
		String new_password = (String) dynaBean.get("new_password");
		String repeat = (String) dynaBean.get("repeat");
		String verificationCode = (String) dynaBean.get("verificationCode");
		String ver_code = (String) dynaBean.get("ver_code");
		UserInfo ui = super.getUserInfoFromSession(request);
		DESPlus des = new DESPlus();

		JSONObject data = new JSONObject();

		// if (StringUtils.isBlank(old_password)) {
		// data.put("ret", "0");
		// data.put("msg", "原密码不能为空！");
		// super.renderJson(response, data.toString());
		// return null;
		// }
		if (StringUtils.isBlank(new_password)) {
			data.put("ret", "0");
			data.put("msg", "密码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.equals(ui.getPassword(), des.encrypt(new_password))) {
			data.put("ret", "0");
			data.put("msg", "新密码不能和原密码一样！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (!StringUtils.equals(repeat, new_password)) {
			data.put("ret", "0");
			data.put("msg", "2次密码不一致！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.isBlank(verificationCode)) {
			data.put("ret", "0");
			data.put("msg", "验证码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.isBlank(ver_code)) {
			data.put("ret", "0");
			data.put("msg", "手机动态码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (!(ver_code.equals(request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			data.put("ret", "0");
			data.put("msg", "手机动态码填写不正确！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		// // entity.setPassword(EncryptUtilsV2.MD5Encode(old_password));
		// DESPlus des = new DESPlus();
		// entity.setPassword(des.encrypt(old_password));
		// Integer count = super.getFacade().getUserInfoService().getUserInfoCount(entity);
		// if (count <= 0) {
		// data.put("ret", "0");
		// data.put("msg", "您输入的原密码不正确！");
		// super.renderJson(response, data.toString());
		// return null;
		// }
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		// entity.setPassword(EncryptUtilsV2.MD5Encode(new_password));

		entity.setPassword(des.encrypt(new_password));
		entity.setIs_has_update_pass(1);
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		data.put("ret", "1");
		data.put("msg", "密码更新成功！请重新登录");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward modifyMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String user_name = (String) dynaBean.get("user_name");
		String verificationCode = (String) dynaBean.get("verificationCode");
		String ver_code = (String) dynaBean.get("ver_code");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(mobile)) {
			data.put("ret", "0");
			data.put("msg", "手机号码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.isBlank(verificationCode)) {
			data.put("ret", "0");
			data.put("msg", "验证码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.isBlank(ver_code)) {
			data.put("ret", "0");
			data.put("msg", "手机动态码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (!(ver_code.equals(request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			data.put("ret", "0");
			data.put("msg", "手机动态码填写不正确！");
			super.renderJson(response, data.toString());
			return null;
		}

		if (StringUtils.isNotBlank(user_name)) {
			UserInfo ui1 = new UserInfo();
			ui1.getMap().put("not_in_id", ui.getId());
			ui1.setUser_name(user_name);
			ui1.setIs_del(0);
			int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
			if (count1 > 0) {
				String msg = "你的用户名太响亮了，已经被注册。";
				data.put("ret", "0");
				data.put("msg", msg);
				super.renderJson(response, data.toString());
				return null;
			}
			ui1 = new UserInfo();
			ui1.getMap().put("not_in_id", ui.getId());
			ui1.setUser_name(user_name);
			ui1.setMobile(mobile);
			ui1.setIs_del(0);
			int count2 = getFacade().getUserInfoService().getUserInfoCount(ui1);
			if (count2 > 0) {
				String msg = "用户名或者手机号，已经被注册。";
				data.put("ret", "0");
				data.put("msg", msg);
				super.renderJson(response, data.toString());
				return null;
			}
			ui1 = new UserInfo();
			ui1.getMap().put("not_in_id", ui.getId());
			ui1.setUser_name(mobile);// 防止这个人的手机号是另一个人的用户名
			ui1.setIs_del(0);
			int count4 = getFacade().getUserInfoService().getUserInfoCount(ui1);
			if (count4 > 0) {
				String msg = "用户名，已经被注册。";
				data.put("ret", "0");
				data.put("msg", msg);
				super.renderJson(response, data.toString());
				return null;
			}
		}

		UserInfo ui1 = new UserInfo();
		ui1.setMobile(mobile);
		ui1.setIs_del(0);
		int count3 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count3 > 0) {
			String msg = "手机号，已经被注册。";
			data.put("ret", "0");
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		super.setUserInfoToSession(request, userInfo);
		String msg = "修改手机号码成功！";
		if (StringUtils.isBlank(ui.getMobile())) {
			msg = "绑定手机号码成功！您可以通过手机号登陆!";
		}
		// 当用户名为RY_开头时，绑定完手机号码用户名自动更改为手机号码
		UserInfo entity1 = new UserInfo();
		entity1.getMap().put("user_name_like", "RY");
		entity1.setId(ui.getId());
		entity1 = super.getFacade().getUserInfoService().getUserInfo(entity1);
		if (entity1 != null) {
			entity1.setUser_name(mobile);
			entity1.getMap().put("update_user_name_update_ym_id", "true");
			super.getFacade().getUserInfoService().modifyUserInfo(entity1);
		}

		// 或者用户名和手机号是一样的情况 需要去更新user_name
		if (StringUtils.isNotBlank(ui.getMobile())&&ui.getMobile().equals(ui.getUser_name())) {
			UserInfo entityUpdate = new UserInfo();
			entityUpdate.setId(ui.getId());
			entityUpdate.setUser_name(mobile);
			entityUpdate.getMap().put("update_user_name_update_ym_id", "true");
			super.getFacade().getUserInfoService().modifyUserInfo(entityUpdate);
			data.put("ret", "1");
			data.put("msg", "修改手机号码成功！用户名已更改为手机号码");
			super.renderJson(response, data.toString());
			return null;
		}

		data.put("ret", "1");
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward modifyBank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		HttpSession session = request.getSession();
		UserInfo ui = super.getUserInfoFromSession(request);
		String verificationCode = (String) dynaBean.get("verificationCode");
		String ver_code = (String) dynaBean.get("ver_code");

		JSONObject data = new JSONObject();
		String msg = "";
		String ret = "0";

		String bank_pindex = (String) dynaBean.get("bank_pindex");
		if (StringUtils.isBlank(bank_pindex)) {
			msg = "请选择开户地";
			ret = "0";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setIs_del(0);
		baseProvince.setP_index(Long.valueOf(bank_pindex));
		baseProvince = super.getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
		if (null == baseProvince) {
			msg = "区域不存在";
			ret = "0";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		boolean is_edit = true;
		if (StringUtils.isBlank(ui.getBank_account())) {
			is_edit = false;
		}
		if (is_edit) {
			if (StringUtils.isBlank(verificationCode)) {
				msg = "请输入验证码";
				ret = "0";
				data.put("ret", ret);
				data.put("msg", msg);
				super.renderJson(response, data.toJSONString());
				return null;

			}

			if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
				msg = "验证码输入错误";
				ret = "0";
				data.put("ret", ret);
				data.put("msg", msg);
				super.renderJson(response, data.toJSONString());
				return null;
			}

		}

		UserInfo uiInfo = new UserInfo();
		super.copyProperties(uiInfo, dynaBean);
		uiInfo.setId(ui.getId());
		uiInfo.setBank_pname(StringUtils.replace(baseProvince.getFull_name(), ",", ""));
		super.getFacade().getUserInfoService().modifyUserInfo(uiInfo);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		super.setUserInfoToSession(request, userInfo);

		msg = "保存成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward valOldMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String verificationCode = (String) dynaBean.get("verificationCode");
		// String password_pay = (String) dynaBean.get("password_pay");
		// String ver_code_old = (String) dynaBean.get("ver_code_old");
		UserInfo ui = super.getUserInfoFromSession(request);
		logger.info("=======valOldMobile===========");
		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(verificationCode)) {
			data.put("ret", "0");
			data.put("msg", "验证码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}

		// if (StringUtils.isBlank(password_pay)) {
		// data.put("ret", "0");
		// data.put("msg", "参数有误，请联系管理员！");
		// super.renderJson(response, data.toString());
		// return null;
		// }

		HttpSession session = request.getSession();
		if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
			data.put("ret", "0");
			data.put("msg", "验证码不正确！");
			super.renderJson(response, data.toString());
			return null;
		}

		ui = super.getUserInfo(ui.getId());

		// if (!EncryptUtilsV2.MD5Encode(password_pay.trim()).equals(ui.getPassword_pay())) {
		// data.put("ret", "0");
		// data.put("msg", "支付密码输入有误！");
		// super.renderJson(response, data.toString());
		// }

		// if (StringUtils.isBlank(ver_code_old)) {
		// data.put("ret", "0");
		// data.put("msg", "手机动态码不能为空！");
		// super.renderJson(response, data.toString());
		// return null;
		// }
		// if (!(ver_code_old.equals(request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
		// data.put("ret", "0");
		// data.put("msg", "手机动态码填写不正确！");
		// super.renderJson(response, data.toString());
		// return null;
		// }

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		super.setUserInfoToSession(request, userInfo);

		data.put("ret", "1");
		data.put("msg", "验证成功！");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward modifyEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DynaBean dynaBean = (DynaBean) form;
		String email = (String) dynaBean.get("email");
		String ver_code = (String) dynaBean.get("ver_code");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(email)) {
			data.put("ret", "0");
			data.put("msg", "邮箱地址不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (!(ver_code.equals(request.getSession().getAttribute(Keys.EMAIL_VERI_CODE)))) {
			data.put("ret", "0");
			data.put("msg", "邮箱动态码填写不正确！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		super.setUserInfoToSession(request, userInfo);

		data.put("ret", "1");
		data.put("msg", "修改邮箱地址成功！");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward modifyPasswordPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DynaBean dynaBean = (DynaBean) form;
		// String old_password_pay = (String) dynaBean.get("old_password_pay");
		String new_password_pay = (String) dynaBean.get("new_password_pay");
		String repeat = (String) dynaBean.get("repeat");
		String verificationCode = (String) dynaBean.get("verificationCode");
		String ver_code = (String) dynaBean.get("ver_code");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();

		UserInfo entityQuery = new UserInfo();
		entityQuery.setId(ui.getId());
		entityQuery = super.getFacade().getUserInfoService().getUserInfo(entityQuery);
		if (null == entityQuery) {
			data.put("ret", "0");
			data.put("msg", "该用户有误！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.isNotBlank(entityQuery.getPassword_pay())) {
			// if (StringUtils.isBlank(old_password_pay)) {
			// data.put("ret", "0");
			// data.put("msg", "原支付密码不能为空！");
			// super.renderJson(response, data.toString());
			// return null;
			// }

			if (StringUtils.isBlank(verificationCode)) {
				data.put("ret", "0");
				data.put("msg", "验证码不能为空！");
				super.renderJson(response, data.toString());
				return null;
			}
			if (StringUtils.isBlank(ver_code)) {
				data.put("ret", "0");
				data.put("msg", "手机动态码不能为空！");
				super.renderJson(response, data.toString());
				return null;
			}
			if (!(ver_code.equals(request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
				data.put("ret", "0");
				data.put("msg", "手机动态码填写不正确！");
				super.renderJson(response, data.toString());
				return null;
			}
		}

		if (StringUtils.isBlank(new_password_pay)) {
			data.put("ret", "0");
			data.put("msg", "支付密码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (!StringUtils.equals(repeat, new_password_pay)) {
			data.put("ret", "0");
			data.put("msg", "2次密码不一致！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		// if (StringUtils.isNotBlank(entityQuery.getPassword_pay())) {
		// entity.setId(ui.getId());
		// entity.setPassword_pay(EncryptUtilsV2.MD5Encode(old_password_pay));
		// Integer count = super.getFacade().getUserInfoService().getUserInfoCount(entity);
		// if (count <= 0) {
		// data.put("ret", "0");
		// data.put("msg", "您输入的原密码不正确！");
		// super.renderJson(response, data.toString());
		// return null;
		// }
		// }
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		entity.setPassword_pay(EncryptUtilsV2.MD5Encode(new_password_pay));
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		super.setUserInfoToSession(request, userInfo);

		data.put("ret", "1");
		if (StringUtils.isNotBlank(entityQuery.getPassword_pay())) {
			data.put("msg", "支付密码更新成功！");
		} else {
			data.put("msg", "支付密码设置成功！");
		}
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward modifySecurequestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String[] question_ids = request.getParameterValues("question_id");
		String[] answer_names = request.getParameterValues("answer_name");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();

		UserInfo entityQuery = new UserInfo();
		entityQuery.setId(ui.getId());
		entityQuery = super.getFacade().getUserInfoService().getUserInfo(entityQuery);
		if (null == entityQuery) {
			data.put("ret", "0");
			data.put("msg", "该用户有误！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (ArrayUtils.isEmpty(question_ids)) {
			data.put("ret", "0");
			data.put("msg", "问题不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (ArrayUtils.isEmpty(answer_names)) {
			data.put("ret", "0");
			data.put("msg", "答案不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}

		List<UserSecurity> inserUserSecurityList = new ArrayList<UserSecurity>();
		for (int i = 0; i < question_ids.length; i++) {
			UserSecurity saveUserSecurity = new UserSecurity();
			saveUserSecurity.setAdd_date(new Date());
			saveUserSecurity.setAdd_uid(ui.getId());
			saveUserSecurity.setUser_id(ui.getId());
			saveUserSecurity.setAnswer_name(answer_names[i]);
			saveUserSecurity.setQuestion_id(Integer.valueOf(question_ids[i]));
			BaseData baseData = new BaseData();
			baseData.setId(Integer.valueOf(question_ids[i]));
			baseData.setIs_del(0);
			baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
			if (null != baseData) {
				saveUserSecurity.setQuestion_name(baseData.getType_name());
			}
			inserUserSecurityList.add(saveUserSecurity);
		}

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		entity.setIs_set_security(1);
		entity.getMap().put("inserUserSecurityList", inserUserSecurityList);
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		data.put("ret", "1");
		data.put("msg", "密保问题设置成功！");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward setRenzheng(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (null == user.getMobile()) {
			String msg = "用户未绑定手机号,请先绑定手机";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		super.copyProperties(form, user);
		BaseImgs imgs = new BaseImgs();
		imgs.setLink_id(ui.getId());
		imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
		List<BaseImgs> imgsList = getFacade().getBaseImgsService().getBaseImgsList(imgs);
		if (null != imgsList && imgsList.size() > 0) {
			// for (BaseImgs temp : imgsList) {
			request.setAttribute("img_id_card_zm", imgsList.get(0).getFile_path());
			if (imgsList.size() > 1) {
				request.setAttribute("img_id_card_fm", imgsList.get(1).getFile_path());
			}
			// }
		} else {// 没有图片表示没有添加
			dynaBean.set("real_name", "");// 默认设置成空
		}
		request.setAttribute("user", user);

		request.setAttribute("real_name", user.getReal_name());

		request.setAttribute("header_title", "实名认证");

		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
		baseAuditRecord.setLink_id(ui.getId());
		baseAuditRecord = super.getFacade().getBaseAuditRecordService().getBaseAuditRecord(baseAuditRecord);
		if (null != baseAuditRecord && StringUtils.isNotBlank(baseAuditRecord.getOpt_note())) {
			request.setAttribute("real_name", baseAuditRecord.getOpt_note());
		}
		request.setAttribute("baseAuditRecord", baseAuditRecord);

		return new ActionForward("/../m/MMySecurityCenter/form_setrenzheng.jsp");

	}

	public ActionForward modifyRenzheng(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======进入modifyRenzheng===");
		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);

		String img_id_card_zm = (String) dynaBean.get("img_id_card_zm");
		String img_id_card_fm = (String) dynaBean.get("img_id_card_fm");
		String real_name = (String) dynaBean.get("real_name");
		String id_card = (String) dynaBean.get("id_card");
		logger.info("====real_name:" + real_name);
		logger.info("====id_card:" + id_card);
		logger.info("====img_id_card_zm:" + img_id_card_zm);

		String[] basefiles = { img_id_card_zm, img_id_card_fm };

		JSONObject data = new JSONObject();
		String msg = "";
		String ret = "0";

		UserInfo uiInfo = new UserInfo();
		uiInfo.setId(ui.getId());
		uiInfo.setId_card(id_card);
		uiInfo.getMap().put("basefiles", basefiles);
		uiInfo.setIs_renzheng(0);

		BaseAuditRecord entity = new BaseAuditRecord();
		entity.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
		entity.setLink_id(ui.getId());
		entity.setLink_table("USER_INFO");
		// 修改用户信息
		entity.getMap().put("modUserInfo", uiInfo);
		List<BaseAuditRecord> entityList = getFacade().getBaseAuditRecordService().getBaseAuditRecordList(entity);
		if (null != entityList && entityList.size() > 0) {
			entity.setOpt_note(real_name);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_user_name(ui.getUser_name());
			entity.setAudit_state(0);
			entity.setId(entityList.get(0).getId());
			getFacade().getBaseAuditRecordService().modifyBaseAuditRecord(entity);
		} else {
			entity.setOpt_note(real_name);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_user_name(ui.getUser_name());
			entity.setAudit_state(0);
			super.getFacade().getBaseAuditRecordService().createBaseAuditRecord(entity);
		}

		// UserInfo userInfo = new UserInfo();
		// userInfo.setId(ui.getId());
		// userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		// super.setUserInfoToSession(request, userInfo);

		if (real_name == null && StringUtils.isBlank(real_name)) {
			msg = "请填写姓名";
			ret = "0";
			super.renderJson(response, data.toJSONString());
			return null;
		}
		if (id_card == null || StringUtils.isBlank(id_card) || id_card.length() != 18) {
			msg = "请填写正确的身份证号码";
			ret = "0";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		msg = "保存成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}
}
