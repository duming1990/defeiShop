package com.ebiz.webapp.web.struts.m;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class MGetPwBackAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledCode"));
		request.setAttribute("header_title", "找回密码");
		return new ActionForward("/MGetPwBack/pw_back_one.jsp");
	}

	public ActionForward stepOne(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "找回密码");
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String verificationCode = (String) dynaBean.get("veri_code");
		String msg;

		if (StringUtils.isBlank(mobile)) {
			msg = "操作失败，手机号不能为空！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		HttpSession session = request.getSession();
		if (StringUtils.isBlank(verificationCode)) {
			msg = "操作失败，验证码不能为空！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
			msg = "操作失败，验证码不正确！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setMobile(mobile);
		Integer m_count = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		if (m_count.intValue() == 0) {
			msg = super.getMessage(request, "pw.back.failed.username.invalid");
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(userInfo);
		if ((null == userInfoList) || (userInfoList.size() == 0)) {
			msg = super.getMessage(request, "pw.back.failed.username.invalid");
			return super.showTipMsg(mapping, form, request, response, msg);
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "pw.back.failed.username.repeat");
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		UserInfo authUser = userInfoList.get(0);
		super.copyProperties(form, authUser);

		return new ActionForward("/MGetPwBack/pw_back_two.jsp");
	}

	public ActionForward stepTwo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "找回密码");
		DynaBean dynaBean = (DynaBean) form;
		HttpSession session = request.getSession(false);
		String id = (String) dynaBean.get("id");
		String password = (String) dynaBean.get("password");
		String mobile_veri_code = (String) dynaBean.get("mobile_veri_code"); // 页面输入的手机验证码
		String mobileCode = (String) session.getAttribute(Keys.MOBILE_CODE); // 接受验证码的手机号码
		String mobileVeriCode = (String) session.getAttribute(Keys.MOBILE_VERI_CODE); // 手机验证码
		String msg;

		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(id));
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			msg = super.getMessage(request, "pw.back.failed.username.invalid");
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		String mobile = userInfo.getMobile();

		if (StringUtils.isBlank(mobile_veri_code)) {
			msg = getMessage(request, "mobile.veri.code.isEmpty");
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (!StringUtils.equals(mobile, mobileCode)) {
			msg = getMessage(request, "mobile.veri.code.change");
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (!StringUtils.equals(mobile_veri_code, mobileVeriCode)) {
			msg = getMessage(request, "mobile.veri.code.isError");
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		UserInfo entity = new UserInfo();
		entity.setId(Integer.valueOf(id));
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		entity.setIs_has_update_pass(1);
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		return new ActionForward("/MGetPwBack/pw_success.jsp");
	}
}
