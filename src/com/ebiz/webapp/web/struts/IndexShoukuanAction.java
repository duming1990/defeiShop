package com.ebiz.webapp.web.struts;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.ebiz.webapp.domain.EntpInfo;

/**
 * @author Wu,Yang
 * @version 2014-09-29
 */
public class IndexShoukuanAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.shoukuan(mapping, form, request, response);
	}

	public ActionForward showTipMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String msg) throws Exception {
		String refererUrl = request.getHeader("Referer");
		logger.info("==refererUrl:{}", refererUrl);

		request.setAttribute("header_title", "系统提示");
		String tip_msg = msg;
		request.setAttribute("tip_msg", tip_msg);
		request.setAttribute("tip_url", refererUrl);

		return new ActionForward("/index/Tip/index.jsp");
	}

	public ActionForward shoukuan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String rule = (String) dynaBean.get("rule");// 规则

		if (!GenericValidator.isLong(entp_id)) {
			String msg = "entp_id参数不正确";
			return this.showTipMsg(mapping, form, request, response, msg);
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(Integer.valueOf(entp_id));
		entpInfo.setIs_del(0);
		entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "店铺不存在";
			return this.showTipMsg(mapping, form, request, response, msg);
		}
		// 这里不能带端口号，否则在微信里面会报rediect_uri错误
		String ctx = super.getCtxPath(request, false);
		String return_url = ctx + "/m/MPayScan.do?entp_id=" + entp_id;
		if (StringUtils.isNotEmpty(rule)) {
			return_url = return_url + "&rule=" + rule;// 增加rule规则
		}

		// 判断是否微信扫描
		if (super.isWeixin(request)) {
			// 如果是微信的话 直接跳转到自动登陆

			StringBuilder link = new StringBuilder();
			String scope = "snsapi_userinfo";
			String state = "";

			StringBuffer server = new StringBuffer();
			server.append(request.getHeader("host")).append(request.getContextPath());
			request.setAttribute("server_domain", server.toString());
			String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=afterLoginWeixin");
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");

			// String refererUrl = request.getHeader("Referer");
			// logger.info("==refererUrl:{}", refererUrl);
			link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
					.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
					.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");

			response.sendRedirect(link.toString());

			logger.warn("==weixin shoukuan sendRedirect :{}", link.toString());

		} else {
			// 如果不是微信的话普通浏览器扫描 跳到转M端登录
			return new ActionForward(return_url, true);
		}

		return null;
	}

}
