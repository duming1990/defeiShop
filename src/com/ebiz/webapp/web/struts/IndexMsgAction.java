package com.ebiz.webapp.web.struts;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wang,Zhixiong
 * @version 2012-05-07 网站底部相关信息
 */
public class IndexMsgAction extends BaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// if (isCancelled(request)) {
		// String msg = "Cancelled.token";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }
		// if (!isTokenValid(request)) {
		// String msg = "errors.token";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }
		DynaBean dynaBean = (DynaBean) form;
		HttpSession session = request.getSession();
		UserInfo ui = super.getUserInfoFromSession(request);
		// if (null == ui) {
		// String msg = "您还未登录，请先登录系统！";
		// super.renderJavaScript(response, "window.onload=function(){alert('" + msg
		// + "');location.href='../../login.shtml'}");
		// return null;
		// }

		String verificationCode = (String) dynaBean.get("verificationCode");
		JSONObject data = new JSONObject();
		String msg = "";
		String ret = "0";

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
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			ret = "0";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		// resetToken(request);

		Msg entity = new Msg();
		super.copyProperties(entity, form);
		entity.setMsg_type(Keys.MSG_TYPE.MSG_TYPE_20.getIndex());
		entity.setUser_id(ui.getId());
		entity.setSend_time(new Date());
		super.getFacade().getMsgService().createMsg(entity);
		saveMessage(request, "entity.inerted");

		msg = "提交成功，感谢您的参与。";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}
}