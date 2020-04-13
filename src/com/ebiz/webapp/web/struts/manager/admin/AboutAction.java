package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;
/**
 * 
 * @author ding,ning
 *
 */
public class AboutAction  extends BaseWebAction {

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
		
		request.setAttribute("baseLink10150List", baseLink10150List);
		request.setAttribute("baseLink10160List", baseLink10160List);
		request.setAttribute("baseLink10170List", baseLink10170List);
		request.setAttribute("baseLink10180List", baseLink10180List);
		request.setAttribute("baseLink10190List", baseLink10190List);
		
		
		return new ActionForward("/admin/IndexEntp/about.jsp");
	}
	
	public ActionForward baseLinkList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	HttpServletResponse response) throws Exception {
		
		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String title_like = (String) dynaBean.get("title_like");
		String link_type = (String) dynaBean.get("link_type");
		Pager pager = (Pager) dynaBean.get("pager");
		BaseLink entity = new BaseLink();
		entity.setLink_type(Integer.valueOf(link_type));
		entity.getMap().put("title_like", title_like);
		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}
		super.copyProperties(entity, form);
		entity.setId(null);
		Integer recordCount = getFacade().getBaseLinkService().getBaseLinkCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkPaginatedList(entity);
		request.setAttribute("entityList", baseLinkList);
		return new ActionForward("/admin/IndexEntp/list.jsp");

		
		
		
	}
	
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", 0);
		return new ActionForward("/admin/IndexEntp/from.jsp");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		
		BaseLink entity = new BaseLink();
		super.copyProperties(entity,form);

			if (StringUtils.isBlank(id)) {
				getFacade().getBaseLinkService().createBaseLink(entity);

			} else {
				getFacade().getBaseLinkService().modifyBaseLink(entity);
				
			}
			return new ActionForward("/admin/About.do?method=baseLinkList");
		
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		
		BaseLink entity = new BaseLink();
		entity.setUpdate_time(new Date());
		entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			getFacade().getBaseLinkService().modifyBaseLink(entity);

			saveMessage(request, "entity.deleted");

		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getBaseLinkService().modifyBaseLink(entity);
			saveMessage(request, "entity.deleted");

		}
		
		return new ActionForward("/admin/About.do?method=baseLinkList"); 
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isLong(id)) {
			BaseLink baseLink = new BaseLink();
			baseLink.setId(Integer.valueOf(id));
			baseLink = getFacade().getBaseLinkService().getBaseLink(baseLink);

			if (null == baseLink) {
				saveMessage(request, "entity.missing");
				return new ActionForward("/admin/About.do?method=baseLinkList");
			}
			baseLink.setUpdate_time(new Date());
			super.copyProperties(form,baseLink);
			
			return new ActionForward("/admin/IndexEntp/edit.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return new ActionForward("/admin/About.do?method=baseLinkList");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			BaseLink baseLink = new BaseLink();
			baseLink.setId(Integer.valueOf(id));
			baseLink = getFacade().getBaseLinkService().getBaseLink(baseLink);

			if (null == baseLink) {
				saveMessage(request, "entity.missing");
				return new ActionForward("/admin/About.do?method=baseLinkList");
			}
			super.copyProperties(form,baseLink);
		
			return new ActionForward("/admin/IndexEntp/view.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return new ActionForward("/admin/About.do?method=baseLinkList");
		}
	}

	
}
