package com.ebiz.webapp.web.struts.m;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author 刘佳
 * @date: 2018年2月2日 下午12:13:17
 */
public class MMyCardAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "我的会员卡");

		return new ActionForward("/../m/MMyCard/list.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (StringUtils.isBlank(user_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			msg = "用户不存在";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		int guanzhu_count = getGuanZhuCount(userInfo);
		data.put("guanzhu_count", guanzhu_count);

		data.put("userInfo", userInfo);
		data.put("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward userRight(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "会员卡权益");

		return new ActionForward("/../m/MMyCard/userRight.jsp");
	}

}
