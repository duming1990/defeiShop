package com.ebiz.webapp.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class GetPwBackAction extends BaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String changeType = (String) dynaBean.get("changeType");

		if (StringUtils.isNotBlank(changeType) && changeType.equals("2")) {
			UserInfo ui = super.getUserInfoFromSession(request);
			if (null == ui) {
				String msg = "您还未登录，请先登录系统！";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg
						+ "');location.href='login.shtml'}");
				return null;
			}
			return new ActionForward("/getpwback.shtml?method=stepOne&user_name=" + ui.getUser_name());
		}

		return new ActionForward("/index/GetPwBack/pw_back_one.jsp");
	}

	public ActionForward stepOne(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_name = (String) dynaBean.get("user_name");
		String verificationCode = (String) dynaBean.get("verificationCode");
		String changeType = (String) dynaBean.get("changeType");
		String msg;

		if (StringUtils.isBlank(user_name)) {
			msg = "账号不能为空！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (StringUtils.isNotBlank(changeType) && !changeType.equals("2")) {
			HttpSession session = request.getSession();
			if (StringUtils.isBlank(verificationCode)) {
				msg = "验证码不能为空！";
				super.showMsgForManager(request, response, msg);
				return null;
			}

			if (!verificationCode.equalsIgnoreCase((String) session.getAttribute("verificationCode"))) {
				msg = "验证码有误！";
				super.showMsgForManager(request, response, msg);
				return null;
			}
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(0);
		userInfo.setIs_active(1);
		userInfo.setMobile(user_name);
		Integer m_count = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		if (m_count.intValue() == 0) {
			userInfo.setMobile(null);
			userInfo.setEmail(user_name);
			Integer e_count = getFacade().getUserInfoService().getUserInfoCount(userInfo);
			if (e_count.intValue() == 0) {
				msg = super.getMessage(request, "pw.back.failed.username.invalid");
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
		}
		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(userInfo);
		if ((null == userInfoList) || (userInfoList.size() == 0)) {
			msg = super.getMessage(request, "pw.back.failed.username.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "pw.back.failed.username.repeat");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		UserInfo authUser = userInfoList.get(0);
		super.copyProperties(form, authUser);

		if (null != authUser.getMobile()) {
			String phone = super.encryptPhone(authUser.getMobile());
			dynaBean.set("phone", phone);
			request.setAttribute("hasPhone", "true");
		}
		if (StringUtils.isNotBlank(authUser.getEmail())) {
			request.setAttribute("hasEmail", "true");
		}
		request.setAttribute("changeType", changeType);
		return new ActionForward("/index/GetPwBack/pw_back_two.jsp");
	}

	public ActionForward stepTwo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String type = (String) dynaBean.get("type");
		String changeType = (String) dynaBean.get("changeType");
		if (StringUtils.isBlank(user_id)) {
			String msg = super.getMessage(request, "login.failed.username.isEmpty");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(0);
		userInfo.setIs_active(1);
		userInfo.setId(Integer.valueOf(user_id));
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		super.copyProperties(form, userInfo);

		dynaBean.set("mobile", super.encryptPhone(userInfo.getMobile()));
		dynaBean.set("email", super.encryptEmail(userInfo.getEmail()));

		request.setAttribute("no_en_mobile", userInfo.getMobile());
		request.setAttribute("no_en_email", userInfo.getEmail());

		request.setAttribute("type", type);
		request.setAttribute("changeType", changeType);

		return new ActionForward("/index/GetPwBack/pw_back_three.jsp");
	}

	public ActionForward stepThree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		HttpSession session = request.getSession(false);
		String id = (String) dynaBean.get("id");
		String type = (String) dynaBean.get("type");
		String changeType = (String) dynaBean.get("changeType");
		String mobile_veri_code = (String) dynaBean.get("verifycode"); // 页面输入的手机验证码
		String mobileCode = (String) session.getAttribute(Keys.MOBILE_CODE); // 接受验证码的手机号码
		String mobileVeriCode = (String) session.getAttribute(Keys.MOBILE_VERI_CODE); // 手机验证码
		String emailVeriCode = (String) session.getAttribute(Keys.EMAIL_VERI_CODE); // 邮箱验证码
		String msg;

		if (StringUtils.isBlank(type) || !GenericValidator.isInt(id)) {
			msg = super.getMessage(request, "参数有误,请联系管理员！");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(id));
		userInfo.setIs_del(0);
		userInfo.setIs_active(1);
		List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(userInfo);
		if ((null == userInfoList) || (userInfoList.size() == 0)) {
			msg = super.getMessage(request, "pw.back.failed.username.invalid");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		} else if (userInfoList.size() > 1) {
			msg = super.getMessage(request, "pw.back.failed.username.repeat");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		UserInfo authUser = userInfoList.get(0);
		String mobile = authUser.getMobile();

		if (StringUtils.isBlank(mobile_veri_code)) {
			msg = getMessage(request, "mobile.veri.code.isEmpty");
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		if (type.equals("1")) {
			if (!StringUtils.equals(mobile, mobileCode)) {
				msg = getMessage(request, "mobile.veri.code.change");
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
			if (!StringUtils.equals(mobile_veri_code, mobileVeriCode)) {
				msg = getMessage(request, "mobile.veri.code.isError");
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
		} else if (type.equals("2")) {
			if (!StringUtils.equals(mobile_veri_code, emailVeriCode)) {
				msg = getMessage(request, "mobile.veri.code.isError");
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
		}
		dynaBean.set("id", id);
		dynaBean.set("changeType", changeType);
		return new ActionForward("/index/GetPwBack/pw_back_four.jsp");
	}

	public ActionForward stepFour(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String password = (String) dynaBean.get("password");
		String id = (String) dynaBean.get("id");
		String changeType = (String) dynaBean.get("changeType");
		if (StringUtils.isBlank(id)) {
			String msg = getMessage(request, "errors.parm.missing");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='getpwback.shtml'}");
			return null;
		}

		if (StringUtils.isBlank(changeType)) {
			changeType = "1";
		}

		UserInfo entity = new UserInfo();
		entity.setId(Integer.valueOf(id));
		if (changeType.equals("1")) {
			DESPlus des = new DESPlus();
			entity.setPassword(des.encrypt(password));
			entity.setIs_has_update_pass(1);
			// entity.setPassword(EncryptUtilsV2.MD5Encode(password));
		} else if (changeType.equals("2")) {
			// DESPlus des = new DESPlus();
			// entity.setPassword(des.encrypt(password));
			entity.setPassword_pay(EncryptUtilsV2.MD5Encode(password));
		}
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		request.setAttribute("changeType", changeType);
		return new ActionForward("/index/GetPwBack/pw_success.jsp");
	}

	public ActionForward chooseAgain(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		if (StringUtils.isBlank(user_id)) {
			String msg = super.getMessage(request, "login.failed.username.isEmpty");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(0);
		userInfo.setIs_active(1);
		userInfo.setId(Integer.valueOf(user_id));
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		super.copyProperties(form, userInfo);
		if (null != userInfo.getMobile()) {
			String phone = super.encryptPhone(userInfo.getMobile());
			dynaBean.set("phone", phone);
			request.setAttribute("hasPhone", "true");
		}
		if (StringUtils.isNotBlank(userInfo.getEmail())) {
			request.setAttribute("hasEmail", "true");
		}
		return new ActionForward("/index/GetPwBack/pw_back_two.jsp");
	}

}
