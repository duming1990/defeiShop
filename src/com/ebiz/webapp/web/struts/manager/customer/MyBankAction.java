package com.ebiz.webapp.web.struts.manager.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class MyBankAction extends BaseCustomerAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		getsonSysModuleList(request, dynaBean);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		super.copyProperties(form, userInfo);
		// dynaBean.set("mobile", "");
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		HttpSession session = request.getSession();
		UserInfo ui = super.getUserInfoFromSession(request);
		String verificationCode = (String) dynaBean.get("verificationCode");
		String ver_code = (String) dynaBean.get("ver_code");
		JSONObject data = new JSONObject();
		String msg = "";
		String ret = "0";

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

			if (!(ver_code.equals(request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
				data.put("ret", "0");
				data.put("msg", "手机动态码填写不正确！");
				super.renderJson(response, data.toString());
				return null;
			}
		}

		UserInfo uiInfo = new UserInfo();
		super.copyProperties(uiInfo, dynaBean);
		uiInfo.setId(ui.getId());
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
}