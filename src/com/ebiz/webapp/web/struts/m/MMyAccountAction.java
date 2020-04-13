package com.ebiz.webapp.web.struts.m;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class MMyAccountAction extends MBaseWebAction {

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

		request.setAttribute("header_title", "个人中心");
		return new ActionForward("/../m/MMyAccount/index.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);
		super.getModNameForMobile(request);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			saveError(request, "entity.missing");
			return mapping.findForward("input");
		}
		super.copyProperties(form, entity);
		if (null != entity.getId_card()) {
			dynaBean.set("encryptIdCard", super.encryptIdCard(entity.getId_card()));
		}

		if (null != entity.getP_index()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index());
		}
		request.setAttribute("d_year", new Date().getYear() + 1900);

		return mapping.findForward("input");
	}

	public ActionForward saveUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String id_card = (String) dynaBean.get("id_card");
		String sex = (String) dynaBean.get("sex");
		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		JSONObject data = new JSONObject();
		data.put("code", "1");
		data.put("msg", "修改成功！");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward cancelUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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

		request.setAttribute("header_title", "账号注销");
		return new ActionForward("/../m/MMyAccount/form_canceluser.jsp");
	}

	public ActionForward saveCancelUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String verificationCode = (String) dynaBean.get("verificationCode");
		String ver_code = (String) dynaBean.get("ver_code");
		String mobile = (String) dynaBean.get("mobile");

		JSONObject data = new JSONObject();
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
		if (StringUtils.isBlank(mobile)) {
			data.put("ret", "0");
			data.put("msg", "手机号不能为空！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (!(ver_code.equals(request.getSession().getAttribute(Keys.MOBILE_VERI_CODE)))) {
			data.put("ret", "0");
			data.put("msg", "手机动态码填写不正确！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		if (null == user) {
			data.put("ret", "0");
			data.put("msg", "用户不存在！");
			super.renderJson(response, data.toString());
			return null;
		}
		if (!mobile.equals(user.getMobile())) {
			data.put("ret", "0");
			data.put("msg", "当前用户的手机号与您输入的手机号不一致！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());
		entity.setDel_date(new Date());
		entity.setDel_user_id(ui.getId());
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		data.put("ret", "1");
		data.put("msg", "注销成功！");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getUserXyInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取用户协议的信息
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setMod_id("1019015000");
		newsInfo = super.getFacade().getNewsInfoService().getNewsInfo(newsInfo);
		request.setAttribute("header_title", "会员注册协议");
		request.setAttribute("newsInfo", newsInfo);
		return new ActionForward("/../m/MMyAccount/userXyInfo.jsp");
	}
}
