package com.ebiz.webapp.web.struts.m;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.util.DESPlus;

/**
 * @author Wu,Yang
 * @version 2011-12-14
 */
public class MMyAccountCenterAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("flag", "4");
		super.getModNameForMobile(request);
		return mapping.findForward("list");
	}

	public ActionForward modifypassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("flag", "4");
		request.setAttribute("header_title", "修改密码");
		return new ActionForward("/../m/MMyAccountCenter/pw_form.jsp");
	}

	public ActionForward changeUserInfoPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String old_password = (String) dynaBean.get("password");
		String new_password = (String) dynaBean.get("new_password");
		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());

		JSONObject data = new JSONObject();
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(old_password));
		Integer count = super.getFacade().getUserInfoService().getUserInfoCount(entity);
		if (count <= 0) {
			data.put("code", "0");
			data.put("msg", "您输入的原密码不正确！");
			super.renderJson(response, data.toString());
			return null;
		}
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		entity.setPassword(des.encrypt(new_password));
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		data.put("code", "1");
		data.put("msg", "密码更新成功！请重新登录");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward bindOtherApp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("userInfoTemp", super.getUserInfo(ui.getId()));
		request.setAttribute("header_title", "绑定第三方账号");

		return new ActionForward("/../m/MMyAccountCenter/bindOtherApp.jsp");
	}
}
