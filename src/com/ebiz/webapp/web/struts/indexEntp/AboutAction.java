package com.ebiz.webapp.web.struts.indexEntp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;

public class AboutAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseLink baseLink1 = new BaseLink();
		baseLink1.setLink_type(Keys.LinkType.LINK_TYPE_10150.getIndex());
		baseLink1.setIs_del(0);
		List<BaseLink> baseLink10150List = getFacade().getBaseLinkService().getBaseLinkList(baseLink1);

		BaseLink baseLink2 = new BaseLink();
		baseLink2.setLink_type(Keys.LinkType.LINK_TYPE_10160.getIndex());
		baseLink2.setIs_del(0);
		List<BaseLink> baseLink10160List = getFacade().getBaseLinkService().getBaseLinkList(baseLink2);

		BaseLink baseLink3 = new BaseLink();
		baseLink3.setLink_type(Keys.LinkType.LINK_TYPE_10170.getIndex());
		baseLink3.setIs_del(0);
		List<BaseLink> baseLink10170List = getFacade().getBaseLinkService().getBaseLinkList(baseLink3);

		BaseLink baseLink4 = new BaseLink();
		baseLink4.setLink_type(Keys.LinkType.LINK_TYPE_10180.getIndex());
		baseLink4.setIs_del(0);
		List<BaseLink> baseLink10180List = getFacade().getBaseLinkService().getBaseLinkList(baseLink4);

		BaseLink baseLink5 = new BaseLink();
		baseLink5.setLink_type(Keys.LinkType.LINK_TYPE_10190.getIndex());
		baseLink5.setIs_del(0);
		List<BaseLink> baseLink10190List = getFacade().getBaseLinkService().getBaseLinkList(baseLink5);

		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));
		request.setAttribute("baseLink10071", super.common(10071).get("baseLinkList").get(0));
		request.setAttribute("baseLink10091", super.common(10091).get("baseLinkList").get(0));

		request.setAttribute("baseLink10150List", baseLink10150List);
		request.setAttribute("baseLink10160List", baseLink10160List);
		request.setAttribute("baseLink10170List", baseLink10170List);
		request.setAttribute("baseLink10180List", baseLink10180List);
		request.setAttribute("baseLink10190List", baseLink10190List);

		return new ActionForward("/IndexEntp/about/about.jsp");
	}
}