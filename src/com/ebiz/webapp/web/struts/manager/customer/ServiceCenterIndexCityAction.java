package com.ebiz.webapp.web.struts.manager.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.ServiceBaseLink;
import com.ebiz.webapp.domain.UserInfo;

public class ServiceCenterIndexCityAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}
		UserInfo entity = new UserInfo();
		entity = super.getUserInfo(ui.getId());
		request.setAttribute("entity", entity);
		List<ServiceBaseLink> baseLink30List = this.getServiceBaseLinkCityList(0, String.valueOf(ui.getP_index()),
				"no_null_image_path");// 取市级下面县域馆的头像
		request.setAttribute("baseLink30List", baseLink30List);
		List<ServiceBaseLink> baseLink20List = this.getServiceBaseLinkCityWithList(20, null, entity.getId(), 20,
				"no_null_image_path");// 楼层
		request.setAttribute("baseLink20List", baseLink20List);
		ServiceBaseLink baseLinkBg = this.getServiceBaseLinkBg(30, entity.getId(), 20, "no_null_image_path");// 背景图
		request.setAttribute("baseLinkBg", baseLinkBg);
		return mapping.findForward("list");
	}
}
