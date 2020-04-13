package com.ebiz.webapp.web.struts.indexEntp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author libaiqiang
 * @version 2019-3-18
 */
public class CaseAction extends BaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// // 楼层
		List<BaseLink> baseLink10050List = this.getBaseLinkList(Keys.LinkType.LINK_TYPE_10050.getIndex(), 9, null);
		for (BaseLink cur : baseLink10050List) {
			BaseLink a = new BaseLink();
			a.setIs_del(0);

			a.setPar_id(cur.getId());
			List<BaseLink> LINK_TYPE_10110 = getFacade().getBaseLinkService().getBaseLinkList(a);
			if (null != LINK_TYPE_10110 && LINK_TYPE_10110.size() > 0) {
				cur.getMap().put("LINK_TYPE_CASE", LINK_TYPE_10110.get(0));
			}


		}
		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));
		List<BaseLink> list1 = super.common(10071).get("baseLinkList");
		if (null != list1 && list1.size() > 0) {
			request.setAttribute("baseLink10071", super.common(10071).get("baseLinkList").get(0));
		}
		List<BaseLink> list2 = super.common(10091).get("baseLinkList");
		if (null != list2 && list2.size() > 0) {
			request.setAttribute("baseLink10091", super.common(10091).get("baseLinkList").get(0));
		}

		request.setAttribute("baseLink10050List", baseLink10050List);
		// return new ActionForward("/admin/EntpCase/case.jsp");

		// BaseLink baseLink10109 =
		// baseLink10109.setLink_type(Keys.LinkType.LINK_TYPE_10109.getIndex());
		List<BaseLink> LINK_TYPE_10109 = super.getBaseLinkList(Keys.LinkType.LINK_TYPE_10109.getIndex(), 1, "true");
		if (null != LINK_TYPE_10109 && LINK_TYPE_10109.size() > 0) {
			request.setAttribute("LINK_TYPE_10109", LINK_TYPE_10109.get(0));
		} else {
			request.setAttribute("LINK_TYPE_10109", null);
		}

		return new ActionForward("/IndexEntp/case/case.jsp");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		NewsInfo entity = new NewsInfo();
		super.copyProperties(entity, dynaBean);
		entity = super.getFacade().getNewsInfoService().getNewsInfo(entity);
		request.setAttribute("entity", entity);
		System.out.println(entity.getMap().get("content"));
		return mapping.findForward("view");

	}

}