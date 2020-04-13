package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.MBaseLink;

/**
 * @author Liu,Jia
 * @version 2012-02-22
 */
public class ServiceInfoAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 轮播图
		setMbaseLinkList(request, 10, 4, "true");
		// 导航
		setMbaseLinkList(request, 20, 10, "true");
		// 楼层
		// setMbaseLinkList(request, 30, 9, "true");
		List<MBaseLink> mBaseLinkList = this.getMBaseLinkList(30, 9, "true");// 取后台编辑的楼层
		if ((null != mBaseLinkList) && (mBaseLinkList.size() > 0)) {
			for (MBaseLink temp : mBaseLinkList) {

				// temp.getMap().put("mBaseLinkList40",
				// this.getMBaseLinkListWithParIdAndParSonType(temp.getId(), 40, null, 1, "true"));

				// List<MBaseLink> baseLink50List = this.getMBaseLinkListWithParIdAndParSonType(temp.getId(), 50, null,
				// 10, "no_null_image_path", true);
				// temp.getMap().put("mBaseLinkList50", baseLink50List);
			}
		}
		request.setAttribute("mBaseLinkList30", mBaseLinkList);

		return new ActionForward("/admin/ServiceInfo/index.jsp");
	}

}
