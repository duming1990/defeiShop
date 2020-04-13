package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.web.struts.BaseWebAction;

public class HomeMAction extends BaseWebAction {
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
		List<MBaseLink> mBaseLinkList = this.getMBaseLinkList(30, 20, "true");// 取后台编辑的楼层
		request.setAttribute("mBaseLinkList30", mBaseLinkList);

		return new ActionForward("/admin/HomeM/index.jsp");
	}

}
