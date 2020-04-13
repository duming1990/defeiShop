package com.ebiz.webapp.web.struts.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class AppServiceAction extends BaseWebServiceAction {

	public ActionForward getUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ret = "0", msg = "";

		JSONObject datas = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;

		String user_id = (String) dynaBean.get("user_id");

		if (StringUtils.isBlank(user_id)) {
			msg = "参数错误！";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.returnInfo(response, ret, msg, null);
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(user_id));
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);

		if (userInfo != null) {
			super.setUserInfoToSession(request, userInfo);
			datas.put("user_info", userInfo);
			ret = "1";
			datas.put("ret", ret);
			datas.put("user_id", userInfo.getId());
			super.renderJson(response, datas.toString());
			return null;
		}
		msg = "该用户不存在";
		datas.put("ret", ret);
		datas.put("msg", msg);
		super.returnInfo(response, ret, msg, null);
		return null;
	}

	public ActionForward getPindexByPName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ret = "0", msg = "";

		JSONObject datas = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;

		String p_name = (String) dynaBean.get("p_name");

		if (StringUtils.isBlank(p_name)) {
			msg = "参数错误！";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.returnInfo(response, ret, msg, null);
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setP_name(p_name);
		baseProvince = super.getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
		if (null != baseProvince) {
			datas.put("p_index", baseProvince.getP_index());
			ret = "1";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.returnInfo(response, ret, msg, datas);
			return null;
		}

		datas.put("ret", ret);
		datas.put("msg", msg);
		super.returnInfo(response, ret, msg, null);
		return null;

	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "1", msg = "";

		JSONObject dataList = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;

		String login_name = (String) dynaBean.get("user_name");
		String password = (String) dynaBean.get("password");

		if (StringUtils.isBlank(login_name)) {
			msg = "login_name参数不正确 CODE:1002";
			code = "0";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}
		if (StringUtils.isBlank(password)) {
			msg = "password参数不正确 CODE:1002";
			code = "0";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}

		HttpSession session = request.getSession();
		login_name = login_name.trim();
		UserInfo entity = new UserInfo();
		entity.setUser_name(login_name);
		entity.setIs_del(0);
		entity.getRow().setCount(10);

		Integer u_count = getFacade().getUserInfoService().getUserInfoCount(entity);
		if (u_count.intValue() == 0) {
			entity.setUser_name(null);
			entity.setMobile(login_name);
			Integer m_count = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (m_count.intValue() == 0) {
				msg = "账号不存在，请核对账号！";
				code = "0";
				dataList.put("code", code);
				dataList.put("msg", msg);
				super.returnInfo(response, code, msg, null);
				return null;
			}
		} else if (u_count.intValue() > 1) {
			code = "0";
			msg = "用户名重复！";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}

		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == userInfo) {
			code = "0";
			msg = "密码错误！";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}
		// update login count
		UserInfo ui = new UserInfo();
		ui.setId(userInfo.getId());
		ui.setLogin_count(userInfo.getLogin_count() + 1);
		ui.setLast_login_time(new Date());
		ui.setLast_login_ip(this.getIpAddr(request));
		ui.setLogin_count(userInfo.getLogin_count() + 1);
		ui.setLast_login_time(ui.getLast_login_time());
		ui.setLast_login_ip(ui.getLast_login_ip());
		getFacade().getUserInfoService().modifyUserInfo(ui);
		dataList.put("userInfo", userInfo);

		session.setAttribute(Keys.SESSION_USERINFO_KEY, userInfo);
		dataList.put("code", code);
		dataList.put("msg", msg);
		super.returnInfo(response, code, msg, dataList);
		return null;
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		if (null != session) {
			session.removeAttribute(Keys.SESSION_USERINFO_KEY);
			session.invalidate();
		}

		JSONObject datas = new JSONObject();
		datas.put("ret", 1);

		super.renderJson(response, datas.toString());
		return null;
	}

	public ActionForward getSingleAd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ret = "0", msg = "";

		JSONObject datas = new JSONObject();

		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setMod_id(Keys.ads_mod_id);
		newsInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);

		if (newsInfoList != null && newsInfoList.size() > 0) {
			ret = "1";
			datas.put("newsInfo", newsInfoList.get(0));
			datas.put("ret", ret);
			super.renderJson(response, datas.toString());
			return null;
		}
		msg = "单个广告图未上传！";
		datas.put("ret", ret);
		datas.put("msg", msg);
		super.returnInfo(response, ret, msg, null);
		return null;
	}

	public ActionForward getLunBo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ret = "0", msg = "";

		JSONObject datas = new JSONObject();

		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setMod_id(Keys.lun_bo_ads_mod_id);
		newsInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);

		if (newsInfoList != null && newsInfoList.size() > 0) {
			ret = "1";
			datas.put("AppLunbo", newsInfoList);
			datas.put("ret", ret);
			super.renderJson(response, datas.toString());
			return null;
		}
		msg = "App轮播图未上传！";
		datas.put("ret", ret);
		datas.put("msg", msg);
		super.returnInfo(response, ret, msg, null);
		return null;
	}

	public ActionForward setUserSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";

		JSONObject dataList = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;

		String user_id = (String) dynaBean.get("user_id");

		if (StringUtils.isBlank(user_id)) {
			msg = "user_id参数不正确 CODE:1002";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		HttpSession session = request.getSession();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(user_id));
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null != userInfo) {
			session.setAttribute(Keys.SESSION_USERINFO_KEY, userInfo);
			code = "1";
			dataList.put("userInfo", userInfo);
			super.returnInfo(response, code, msg, dataList);
			return null;
		}
		// 证明没找到该用户
		code = "-1";
		super.returnInfo(response, code, msg, dataList);
		return null;
	}
}