package com.ebiz.webapp.web.struts.manager.customer;

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
public class MySecurityCenterAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
		baseAuditRecord.setLink_id(ui.getId());
		baseAuditRecord = super.getFacade().getBaseAuditRecordService().getBaseAuditRecord(baseAuditRecord);
		request.setAttribute("baseAuditRecord", baseAuditRecord);

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

		// DynaBean dynaBean = (DynaBean) form;

		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("user", user);
		return new ActionForward("/customer/MySecurityCenter/form_setpassword.jsp");
	}

	public ActionForward setMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("user", user);
		return new ActionForward("/customer/MySecurityCenter/form_setmobile.jsp");
	}

	public ActionForward setBank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, user);

		if (null != user.getBank_account()) {
			dynaBean.set("encryptBankAccount", super.encryptBankAccount(user.getBank_account()));
		}
		if (null != user.getBank_pindex()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, user.getBank_pindex());
		}

		request.setAttribute("user", user);

		return new ActionForward("/customer/MySecurityCenter/form_setbank.jsp");
	}

	public ActionForward setEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("user", user);
		return new ActionForward("/customer/MySecurityCenter/form_setemail.jsp");
	}

	public ActionForward setUserNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("user", user);
		return new ActionForward("/customer/MySecurityCenter/form_setuserno.jsp");
	}

	public ActionForward setPasswordPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("user", user);
		return new ActionForward("/customer/MySecurityCenter/form_setpasswordpay.jsp");
	}

	public ActionForward setSecurequestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
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

		return new ActionForward("/customer/MySecurityCenter/form_setsecurequestion.jsp");
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
		if (!(verificationCode.equals(request.getSession().getAttribute("verificationCode")))) {
			data.put("ret", "0");
			data.put("msg", "验证码输入错误！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		// DESPlus des = new DESPlus();
		// entity.setPassword(des.encrypt(old_password));
		// // entity.setPassword(EncryptUtilsV2.MD5Encode(old_password));
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
		if (!(verificationCode.equals(request.getSession().getAttribute("verificationCode")))) {
			data.put("ret", "0");
			data.put("msg", "验证码输入错误！");
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
		
		UserInfo ui1 = new UserInfo();
		ui1.getMap().put("not_in_id", ui.getId());
		ui1.setUser_name(mobile);
		ui1.setIs_del(0);
		int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count1 > 0) {
			String msg = "手机号，已经被注册。";
			data.put("ret", "0");
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		ui1 = new UserInfo();
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

		// 当用户名为RY_开头时，绑定完手机号码用户名自动更改为手机号码
		UserInfo entity1 = new UserInfo();
		entity1.getMap().put("user_name_like", "RY");
		entity1.setId(ui.getId());
		entity1 = super.getFacade().getUserInfoService().getUserInfo(entity1);
		if (entity1 != null) {
			entity1.setUser_name(mobile);
			entity1.getMap().put("update_user_name_update_ym_id", "true");
			super.getFacade().getUserInfoService().modifyUserInfo(entity1);
			data.put("ret", "1");
			data.put("msg", "修改手机号码成功！用户名已更改为手机号码");
			super.renderJson(response, data.toString());
			return null;
		}

		// 或者用户名和手机号是一样的情况 需要去更新user_name
		if (ui.getMobile().toString().equals(ui.getUser_name().toString())) {
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
		data.put("msg", "修改手机号码成功！");
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
		super.copyProperties(uiInfo, form);
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
		logger.info("=========valOldMobile=============");
		DynaBean dynaBean = (DynaBean) form;
		String verificationCode = (String) dynaBean.get("verificationCode");
		String password_pay = (String) dynaBean.get("password_pay");
		// String ver_code_old = (String) dynaBean.get("ver_code_old");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(verificationCode)) {
			data.put("ret", "0");
			data.put("msg", "验证码不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}

		if (StringUtils.isBlank(password_pay)) {
			data.put("ret", "0");
			data.put("msg", "参数有误，请联系管理员！");
			super.renderJson(response, data.toString());
			return null;
		}

		HttpSession session = request.getSession();
		if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
			data.put("ret", "0");
			data.put("msg", "验证码不正确！");
			super.renderJson(response, data.toString());
			return null;
		}

		ui = super.getUserInfo(ui.getId());

		if (null == ui.getPassword_pay()) {
			data.put("ret", "0");
			data.put("msg", "未设置支付密码！");
			super.renderJson(response, data.toString());
			return null;
		}

		if (!EncryptUtilsV2.MD5Encode(password_pay.trim()).equals(ui.getPassword_pay())) {
			data.put("ret", "0");
			data.put("msg", "支付密码输入有误！");
			super.renderJson(response, data.toString());
			return null;
		}

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
		String old_password_pay = (String) dynaBean.get("old_password_pay");
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
			if (StringUtils.isBlank(old_password_pay)) {
				data.put("ret", "0");
				data.put("msg", "原支付密码不能为空！");
				super.renderJson(response, data.toString());
				return null;
			}

			if (StringUtils.isBlank(verificationCode)) {
				data.put("ret", "0");
				data.put("msg", "验证码不能为空！");
				super.renderJson(response, data.toString());
				return null;
			}
			if (!(verificationCode.equals(request.getSession().getAttribute("verificationCode")))) {
				data.put("ret", "0");
				data.put("msg", "验证码输入错误！");
				super.renderJson(response, data.toString());
				return null;
			}
		}

		if (StringUtils.isBlank(new_password_pay)) {
			data.put("ret", "0");
			data.put("msg", "原支付密码不能为空！");
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
		if (StringUtils.isNotBlank(entityQuery.getPassword_pay())) {
			entity.setId(ui.getId());
			entity.setPassword_pay(EncryptUtilsV2.MD5Encode(old_password_pay));
			Integer count = super.getFacade().getUserInfoService().getUserInfoCount(entity);
			if (count <= 0) {
				data.put("ret", "0");
				data.put("msg", "您输入的原密码不正确！");
				super.renderJson(response, data.toString());
				return null;
			}
		}
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
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
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

		dynaBean.set("real_name", user.getReal_name());

		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
		baseAuditRecord.setLink_id(ui.getId());
		baseAuditRecord = super.getFacade().getBaseAuditRecordService().getBaseAuditRecord(baseAuditRecord);

		if (null != baseAuditRecord && StringUtils.isNotBlank(baseAuditRecord.getOpt_note())) {
			dynaBean.set("real_name", baseAuditRecord.getOpt_note());
		}
		request.setAttribute("baseAuditRecord", baseAuditRecord);

		return new ActionForward("/customer/MySecurityCenter/form_setrenzheng.jsp");
	}

	public ActionForward modifyRenzheng(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		UserInfo ui = super.getUserInfoFromSession(request);
		logger.info("====ui===" + ui.getId());

		String img_id_card_zm = (String) dynaBean.get("img_id_card_zm");
		String img_id_card_fm = (String) dynaBean.get("img_id_card_fm");
		String real_name = (String) dynaBean.get("real_name");
		String id_card = (String) dynaBean.get("id_card");

		logger.info("====img_id_card_zm" + img_id_card_zm);
		logger.info("====img_id_card_fm" + img_id_card_fm);
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

		msg = "保存成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}
}
