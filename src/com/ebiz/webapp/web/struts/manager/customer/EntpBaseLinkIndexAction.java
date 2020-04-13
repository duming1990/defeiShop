package com.ebiz.webapp.web.struts.manager.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.EntpBaseLink;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;

public class EntpBaseLinkIndexAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setAdd_user_id(ui.getId());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		request.setAttribute("entpInfo", entpInfo);
		List<EntpBaseLink> baseLink20List = this.getentpBaseLinkList(20, null, entpInfo.getId(), 10,
				"no_null_image_path");
		request.setAttribute("baseLink20List", baseLink20List);
		EntpBaseLink baseLinkBg = this.getEntpBaseLinkBg(30, entpInfo.getId(), 10, "no_null_image_path");// 背景
		request.setAttribute("baseLinkBg", baseLinkBg);
		return mapping.findForward("list");
	}

	public ActionForward entpStyle(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String is_entpstyle = (String) dynaBean.get("is_entpstyle");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		if (StringUtils.isBlank(entp_id)) {
			msg = "参数不能为空";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(Integer.valueOf(entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null != entpInfo) {
			entpInfo.setIs_entpstyle(Integer.valueOf(is_entpstyle));
			super.getFacade().getEntpInfoService().modifyEntpInfo(entpInfo);
		} else {
			msg = "没有该商家";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}
}
