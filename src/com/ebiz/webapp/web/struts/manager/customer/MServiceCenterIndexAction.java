package com.ebiz.webapp.web.struts.manager.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.MServiceBaseLink;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;

public class MServiceCenterIndexAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setAdd_user_id(ui.getId());
		entity = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
		request.setAttribute("entity", entity);
		List<MServiceBaseLink> baseLink20List = this.getMServiceBaseLinkCityWithList(20, null, entity.getId(), 10,
				"no_null_image_path");
		request.setAttribute("baseLink20List", baseLink20List);
		// ServiceBaseLink baseLinkBg = this.getServiceBaseLinkBg(30, entity.getId(), 10, "no_null_image_path");// 背景图
		// request.setAttribute("baseLinkBg", baseLinkBg);
		return mapping.findForward("list");
	}
}
