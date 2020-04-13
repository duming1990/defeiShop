package com.ebiz.webapp.web.struts.manager.admin;

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
import com.ebiz.webapp.domain.Role;
import com.ebiz.webapp.domain.RoleUser;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 */
public class RoleUserAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		RoleUser entity = new RoleUser();
		super.copyProperties(entity, dynaBean);

		Integer recordCount = getFacade().getRoleUserService().getRoleUserCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<RoleUser> list = getFacade().getRoleUserService().getRoleUserPaginatedList(entity);

		UserInfo ui = new UserInfo();
		ui.setIs_del(0);

		request.setAttribute("entityList", list);
		request.setAttribute("userInfoList", getFacade().getUserInfoService().getUserInfoList(ui));
		request.setAttribute("roleList", getFacade().getRoleService().getRoleList(new Role()));
		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		RoleUser roleUser = new RoleUser();
		roleUser.setId(new Integer(id));

		RoleUser entity = getFacade().getRoleUserService().getRoleUser(roleUser);
		super.copyProperties(form, entity);

		UserInfo ui = new UserInfo();
		ui.setIs_del(0);
		request.setAttribute("userInfoList", getFacade().getUserInfoService().getUserInfoList(ui));
		request.setAttribute("roleList", getFacade().getRoleService().getRoleList(new Role()));

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		RoleUser entity = new RoleUser();
		super.copyProperties(entity, dynaBean);

		if (null == entity.getId()) {
			getFacade().getRoleUserService().createRoleUser(entity);
			saveMessage(request, "entity.inerted");
		} else {
			getFacade().getRoleUserService().modifyRoleUser(entity);
			saveMessage(request, "entity.updated");
		}
		ActionForward forward = new ActionForward(mapping.findForward("success").getPath(), true);
		return forward;
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		request.setAttribute("userInfoList", getFacade().getUserInfoService().getUserInfoList(new UserInfo()));
		request.setAttribute("roleList", getFacade().getRoleService().getRoleList(new Role()));
		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			RoleUser entity = new RoleUser();
			entity.setId(new Integer(id));
			getFacade().getRoleUserService().removeRoleUser(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			RoleUser entity = new RoleUser();
			entity.getMap().put("pks", pks);
			getFacade().getRoleUserService().removeRoleUser(entity);
			saveMessage(request, "entity.deleted");
		}
		ActionForward forward = new ActionForward(mapping.findForward("success").getPath(), true);
		return forward;
	}

}