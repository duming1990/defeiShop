package com.ebiz.webapp.web.struts.manager.admin;

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
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,Yang
 * @version 2011-04-25
 */
public class ProfileAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			saveError(request, "entity.missing");
			return mapping.findForward("input");
		}
		super.copyProperties(form, entity);

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String old_password = (String) dynaBean.get("old_password");
		String new_password = (String) dynaBean.get("new_password");

		JSONObject jsonObject = new JSONObject();
		String ret = "0", msg = "";

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			msg = "用户未登录！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		if (StringUtils.isBlank(old_password) || StringUtils.isBlank(new_password)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(old_password));
		// entity.setPassword(EncryptUtilsV2.MD5Encode(old_password));
		entity = super.getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			msg = "原密码不正确！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		entity.setPassword(des.encrypt(new_password));
		// entity.setPassword(EncryptUtilsV2.MD5Encode(new_password));
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		ret = "1";
		msg = "修改成功，请重新登录！";
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;
	}
}
