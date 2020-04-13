package com.ebiz.webapp.web.struts;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 * @version 2014-09-29
 */
public class IndexShareAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.share(mapping, form, request, response);
	}

	public ActionForward share(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String suid = (String) dynaBean.get("suid");
		if (!GenericValidator.isLong(suid)) {
			String msg = "参数不正确";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserInfo ui = new UserInfo();
		ui.setId(Integer.valueOf(suid));
		ui.setIs_del(0);
		ui = getFacade().getUserInfoService().getUserInfo(ui);
		if (null == ui) {
			String msg = "邀请人不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		// 判断是否微信扫描
		if (super.isWeixin(request)) {
			// 如果是微信的话 直接跳转到自动登陆

			String ctx = super.getCtxPath(request, false);
			String return_url = ctx + "/m/MMyCard.do";

			StringBuilder link = new StringBuilder();
			String scope = "snsapi_userinfo";
			String state = "";

			StringBuffer server = new StringBuffer();
			server.append(request.getHeader("host")).append(request.getContextPath());
			request.setAttribute("server_domain", server.toString());
			String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=afterLoginWeixin");
			redirectUri += "&suid=" + suid;// 通过suid 自动注册然后登陆
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");

			// String refererUrl = request.getHeader("Referer");
			// logger.info("==refererUrl:{}", refererUrl);
			link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
					.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
					.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");

			response.sendRedirect(link.toString());

			logger.warn("==weixin shoukuan sendRedirect :{}", link.toString());
		}

		saveToken(request);

		dynaBean.set("readolny_ymid", suid);
		dynaBean.set("ymid", ui.getUser_name());
		boolean isMoblie = super.JudgeIsMoblie(request);
		if (isMoblie) {// 手机版
			request.setAttribute("is_mobile", "true");
			request.setAttribute("header_title", "邀请 - 用户注册");
			return new ActionForward("/m/MRegister/register_no_username.jsp");
		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		return new ActionForward("/register/register_no_username.jsp");
	}

}
