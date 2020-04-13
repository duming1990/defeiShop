package com.ebiz.webapp.web.struts.entp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author Wu,Yang
 * @version 2011-04-20
 */
public abstract class BaseEntpAction extends BaseWebAction {
	// 实例化公共信息 ，取得企业信息
	public EntpInfo initPublic(ActionMapping mapping, ActionForm form, HttpServletRequest request, String entp_id,
			HttpServletResponse response) {
		if (StringUtils.isBlank(entp_id)) { 
			String msg = "对不起，您输入的链接地址不存在";
			super.renderJavaScript(response, "alert('" + msg + "');window.close();");
			return null;
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setAudit_state(2);
		entpInfo.setId(Integer.valueOf(entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (entpInfo == null) {
			String msg = "企业不存在";
			super.renderJavaScript(response, "alert('" + msg + "');window.close();");
			return null;
		}
		request.setAttribute("entpInfo", entpInfo);

		return entpInfo;
	}
	
}