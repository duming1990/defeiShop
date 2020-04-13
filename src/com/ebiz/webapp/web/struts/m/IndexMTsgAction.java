package com.ebiz.webapp.web.struts.m;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.MServiceBaseLink;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author 戴诗学
 * @version 2018-4-21
 */
public class IndexMTsgAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		DynaBean dynaBean = (DynaBean) form;
		String link_id = (String) dynaBean.get("link_id");
		boolean flag = false;
		boolean flag1 = false;
		if (null != link_id) {// 判断有没有传link_id
			UserInfo userInfo = new UserInfo();
			userInfo.setId(Integer.valueOf(link_id));
			userInfo.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
			userInfo.setIs_del(0);
			userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
			if (null != userInfo) {// 判断是不是市级县域馆
				flag1 = true;
				MServiceBaseLink baseLink = new MServiceBaseLink();
				baseLink.setLink_id(userInfo.getId());
				baseLink.setLink_type(10);
				baseLink.setMain_type(20);
				baseLink.setIs_del(0);
				int count = super.getFacade().getMServiceBaseLinkService().getMServiceBaseLinkCount(baseLink);
				if (0 == count) {
					String msg = "市级特色馆维护中！";
					super.showMsgForManager(request, response, msg);
					return null;
				}
				if (count > 0) {
					flag = true;
				}
			} else if (!flag1) {// 如果是市级的link_id，不再找是不是县级特色馆
				MServiceBaseLink baseLink = new MServiceBaseLink();
				baseLink.setLink_id(Integer.valueOf(link_id));
				baseLink.setLink_type(10);
				baseLink.setMain_type(10);
				baseLink.setIs_del(0);
				int count = super.getFacade().getMServiceBaseLinkService().getMServiceBaseLinkCount(baseLink);
				if (0 == count) {// 判断县级特色馆有没有维护
					String msg = "县级特色馆维护中！";
					super.showMsgForManager(request, response, msg);
					return null;
				}
			}

		} else {
			String msg = "特色馆维护中！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		List<MServiceBaseLink> baseLink20List = null;
		List<MServiceBaseLink> baseLink10List = null;
		if (flag) {
			List<MServiceBaseLink> baseLink100List = this.getMServiceBaseLinkCityList(0, Integer.valueOf(link_id),
					"no_null_image_path");// 取市级下面县域馆的头像
			request.setAttribute("baseLink100List", baseLink100List);
			baseLink20List = this.getMServiceBaseLinkCityWithList(20, null, Integer.valueOf(link_id), 20,
					"no_null_image_path");// 取后台编辑的楼层
			baseLink10List = this.getMServiceBaseLinkCityWithList(10, null, Integer.valueOf(link_id), 20,
					"no_null_image_path");// 取后台编辑的轮播图
			MServiceBaseLink baseLinkBg = this.getServiceMBaseLinkBg(30, Integer.valueOf(link_id), 20,
					"no_null_image_path");// 背景图
			request.setAttribute("baseLinkBg", baseLinkBg);
		} else {
			baseLink20List = this.getMServiceBaseLinkCityWithList(20, null, Integer.valueOf(link_id), 10,
					"no_null_image_path");// 取后台编辑的楼层
			baseLink10List = this.getMServiceBaseLinkCityWithList(10, null, Integer.valueOf(link_id), 10,
					"no_null_image_path");// 取后台编辑的轮播图
			MServiceBaseLink baseLinkBg = this.getServiceMBaseLinkBg(30, Integer.valueOf(link_id), 10,
					"no_null_image_path");// 背景图
			request.setAttribute("baseLinkBg", baseLinkBg);
		}

		if ((null != baseLink20List) && (baseLink20List.size() > 0)) {
			for (MServiceBaseLink temp : baseLink20List) {

				if (temp.getPre_number() == 1) {
					List<MServiceBaseLink> baseLink101List = this.getMServiceBaseLinkListWithParIdAndParSonType(
							Integer.valueOf(link_id), temp.getId(), 1, 3, "no_null_image_path");
					temp.getMap().put("baseLink101List", baseLink101List);
				}
				if (temp.getPre_number() == 2) {
					List<MServiceBaseLink> baseLink201List = this.getMServiceBaseLinkListWithParIdAndParSonType(
							Integer.valueOf(link_id), temp.getId(), 1, 1, "no_null_image_path");
					temp.getMap().put("baseLink201List", baseLink201List);

					List<MServiceBaseLink> baseLink202List = this.getMServiceBaseLinkListWithParIdAndParSonType(
							Integer.valueOf(link_id), temp.getId(), 2, 2, "no_null_image_path");
					temp.getMap().put("baseLink202List", baseLink202List);

					List<MServiceBaseLink> baseLink203List = this.getMServiceBaseLinkListWithParIdAndParSonType(
							Integer.valueOf(link_id), temp.getId(), 3, 1, "no_null_image_path");
					temp.getMap().put("baseLink203List", baseLink203List);
				}
				if (temp.getPre_number() == 3) {
					List<MServiceBaseLink> baseLink301List = this.getMServiceBaseLinkListWithParIdAndParSonType(
							Integer.valueOf(link_id), temp.getId(), 1, 3, "no_null_image_path");
					temp.getMap().put("baseLink301List", baseLink301List);
				}
				if (temp.getPre_number() == 4) {
					List<MServiceBaseLink> baseLink401List = this.getMServiceBaseLinkListWithParIdAndParSonType(
							Integer.valueOf(link_id), temp.getId(), 1, 4, "no_null_image_path");
					temp.getMap().put("baseLink401List", baseLink401List);

				}
				if (temp.getPre_number() == 5) {
					List<MServiceBaseLink> baseLink501List = this.getMServiceBaseLinkListWithParIdAndParSonType(
							Integer.valueOf(link_id), temp.getId(), 1, 12, "no_null_image_path");
					temp.getMap().put("baseLink501List", baseLink501List);
				}

			}
		}
		request.setAttribute("baseLink20List", baseLink20List);
		request.setAttribute("baseLink10List", baseLink10List);
		return new ActionForward("/IndexMTsg/list.jsp");
	}
}
