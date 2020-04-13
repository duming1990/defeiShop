package com.ebiz.webapp.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * @author Wu,Yang
 * @version 2011-11-23
 */
public class IndexLoginAfterLoginQQAction extends IndexLoginAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaBean dynaBean = (DynaBean) form;
		// request.setAttribute("isEnabledCode", super.getSysSetting("isEnabledCode"));
		//
		// String htype = (String) dynaBean.get("htype");// 隐藏搜索类型
		// if (StringUtils.isBlank(htype)) {
		// htype = "0";
		// }

		response.setContentType("text/html; charset=utf-8");
		DynaBean dynaBean = (DynaBean) form;

		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				String msg = "没有获取到响应参数";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;

			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				request.getSession().setAttribute("demo_access_token", accessToken);
				request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();

				com.qq.connect.api.qzone.UserInfo qzoneUserInfo = new com.qq.connect.api.qzone.UserInfo(accessToken,
						openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				if (userInfoBean.getRet() == 0) {
					UserInfo userInfo = new UserInfo();
					userInfo.setAppid_qq(openID);
					userInfo.setIs_del(0);
					long count = getFacade().getUserInfoService().getUserInfoCount(userInfo);
					if (count > 0) {
						userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
						super.setUserInfoToSession(request, userInfo);
						boolean isMoblie = super.JudgeIsMoblie(request);
						if (isMoblie) {// 手机版
							return this.toCustomerForM(mapping, form, request, response);
						} else {
							return this.toCustomer(mapping, form, request, response);
						}
					}

					String user_name = userInfoBean.getNickname();

					// dynaBean.set("user_name", user_name);
					dynaBean.set("real_name", user_name);
					dynaBean.set("appid_qq", openID);
					// 调转到手机版用
					request.setAttribute("header_title", "用户注册 - 绑定QQ");

					return super.toRegister(mapping, form, request, response);
					// out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL100() + "/><br/>");
				} else {
					String msg = userInfoBean.getMsg();
					super.renderJavaScript(response, "alert('" + msg + "');history.back();");
					return null;
				}

			}
		} catch (QQConnectException e) {
			String msg = "QQConnectException";

			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

	}

}
