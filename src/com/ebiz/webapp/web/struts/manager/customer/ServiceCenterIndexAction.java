package com.ebiz.webapp.web.struts.manager.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.ServiceBaseLink;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;

public class ServiceCenterIndexAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.getMap().put("is_virtual_no_def", true);
		entity.setAdd_user_id(ui.getId());
		entity = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
		request.setAttribute("entity", entity);
		List<ServiceBaseLink> aseLink10List = this.getServiceBaseLinkCityWithList(10, null, entity.getId(), 10,
				"no_null_image_path");// 取后台编辑的轮播图
		List<ServiceBaseLink> baseLink20List = this.getServiceBaseLinkCityWithList(20, null, entity.getId(), 10,
				"no_null_image_path");// 楼层
		request.setAttribute("baseLink20List", baseLink20List);
		request.setAttribute("baseLink10List", aseLink10List);
		for (ServiceBaseLink temp : baseLink20List) {

			if (temp.getPre_number() == 1) {
				List<ServiceBaseLink> baseLink101List = this.getServiceBaseLinkListWithParIdAndParSonType(
						entity.getId(), temp.getId(), 1, 3, "no_null_image_path");
				temp.getMap().put("baseLink101List", baseLink101List);
			}
			if (temp.getPre_number() == 2) {
				List<ServiceBaseLink> baseLink201List = this.getServiceBaseLinkListWithParIdAndParSonType(
						entity.getId(), temp.getId(), 1, 1, "no_null_image_path");
				temp.getMap().put("baseLink201List", baseLink201List);

				List<ServiceBaseLink> baseLink202List = this.getServiceBaseLinkListWithParIdAndParSonType(
						entity.getId(), temp.getId(), 2, 2, "no_null_image_path");
				temp.getMap().put("baseLink202List", baseLink202List);

				List<ServiceBaseLink> baseLink203List = this.getServiceBaseLinkListWithParIdAndParSonType(
						entity.getId(), temp.getId(), 3, 1, "no_null_image_path");
				temp.getMap().put("baseLink203List", baseLink203List);
			}
			if (temp.getPre_number() == 3) {
				List<ServiceBaseLink> baseLink301List = this.getServiceBaseLinkListWithParIdAndParSonType(
						entity.getId(), temp.getId(), 1, 3, "no_null_image_path");
				temp.getMap().put("baseLink301List", baseLink301List);
			}
			if (temp.getPre_number() == 4) {
				List<ServiceBaseLink> baseLink401List = this.getServiceBaseLinkListWithParIdAndParSonType(
						entity.getId(), temp.getId(), 1, 4, "no_null_image_path");
				temp.getMap().put("baseLink401List", baseLink401List);

			}
			if (temp.getPre_number() == 5) {
				List<ServiceBaseLink> baseLink501List = this.getServiceBaseLinkListWithParIdAndParSonType(
						entity.getId(), temp.getId(), 1, 12, "no_null_image_path");
				temp.getMap().put("baseLink501List", baseLink501List);
			}
		}
		ServiceBaseLink baseLinkBg = this.getServiceBaseLinkBg(30, entity.getId(), 10, "no_null_image_path");// 背景图
		request.setAttribute("baseLinkBg", baseLinkBg);
		return mapping.findForward("list");
	}
}
