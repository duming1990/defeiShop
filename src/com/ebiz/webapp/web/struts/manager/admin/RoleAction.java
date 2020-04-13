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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.Role;
import com.ebiz.webapp.domain.RoleUser;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class RoleAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String role_name_like = (String) dynaBean.get("role_name_like");

		Role entity = new Role();
		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		super.copyProperties(entity, dynaBean);
		entity.getMap().put("role_name_like", role_name_like);

		Integer recordCount = getFacade().getRoleService().getRoleCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<Role> list = getFacade().getRoleService().getRolePaginatedList(entity);
		request.setAttribute("roleList", list);
		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");
		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			Role entity = new Role();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			getFacade().getRoleService().modifyRole(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			Role entity = new Role();
			entity.setIs_del(1);
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			entity.getMap().put("pks", pks);
			getFacade().getRoleService().modifyRole(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		Role role = new Role();
		role.setId(new Integer(id));

		Role entity = getFacade().getRoleService().getRole(role);
		super.copyProperties(form, entity);

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		Role entity = new Role();
		super.copyProperties(entity, dynaBean);
		entity.setIs_lock(0);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == entity.getId()) {
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(ui.getId());
			int id = getFacade().getRoleService().createRole(entity);

			BaseData bd = new BaseData();
			bd.setType(10);
			bd.setId(id);
			bd.setType_name(entity.getRole_name());
			bd.setAdd_date(new Date());
			bd.setAdd_user_id(ui.getId());
			bd.setIs_lock(1);
			// bd.setRemark("系统自动添加");
			getFacade().getBaseDataService().createBaseData(bd);

			saveMessage(request, "entity.inerted");
		} else {
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());
			if (null != entity.getIs_del() && entity.getIs_del().intValue() == 0) {
				entity.setDel_date(null);
			}
			getFacade().getRoleService().modifyRole(entity);

			BaseData bd = new BaseData();
			bd.setType(10);
			bd.setId(entity.getId());
			bd.setType_name(entity.getRole_name());
			getFacade().getBaseDataService().modifyBaseData(bd);

			saveMessage(request, "entity.updated");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		Role role = new Role();
		role.setId(new Integer(id));
		Role entity = getFacade().getRoleService().getRole(role);

		super.copyProperties(form, entity);

		// 获取该角色的相应用户列表
		RoleUser role_user = new RoleUser();
		// role_user.getMap().put("user_id_not_in", Keys.USER_WY);
		role_user.setRole_id(entity.getId());
		int hasShouquanCount = getFacade().getRoleUserService().getRoleUserCount(role_user);
		request.setAttribute("hasShouquanCount", hasShouquanCount);
		return mapping.findForward("view");
	}

	public ActionForward checkRoleName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String role_name = (String) dynaBean.get("role_name");
		String id = (String) dynaBean.get("id");
		Role entity = new Role();
		entity.getMap().put("not_in_id", id);
		entity.setRole_name(role_name);
		entity.setIs_del(0);
		Integer recordCount = super.getFacade().getRoleService().getRoleCount(entity);
		String flag = "1";
		if (recordCount.intValue() == 0) {
			flag = "0";
		}
		super.renderJson(response, flag);
		return null;
	}

	public ActionForward getHasShouquanUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String role_id = (String) dynaBean.get("role_id");
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String mobile_like = (String) dynaBean.get("mobile_like");

		if (!GenericValidator.isInt(role_id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		// 获取该角色的相应用户列表

		RoleUser entity = new RoleUser();
		entity.setRole_id(Integer.valueOf(role_id));
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("mobile_like", mobile_like);
		// entity.getMap().put("user_id_not_in", Keys.USER_WY);

		long recordCount = getFacade().getRoleUserService().getRoleUserCount(entity);
		pager.init(recordCount, 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<RoleUser> entityList = super.getFacade().getRoleUserService().getRoleUserPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/admin/Role/hasShouquanUserList.jsp");

	}

	public ActionForward canalRoleUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		JSONObject data = new JSONObject();

		String ret = "0";
		String msg = "参数有误！";
		if (!GenericValidator.isInt(id)) {
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		// 获取该角色的相应用户列表
		RoleUser entity = new RoleUser();
		entity.setId(Integer.valueOf(id));
		int count = super.getFacade().getRoleUserService().removeRoleUser(entity);
		if (count > 0) {
			ret = "1";
			msg = "操作成功！";
		} else {
			ret = "0";
			msg = "操作失败！";
		}

		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward saveRoleUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String role_id = (String) dynaBean.get("id");
		String[] user_ids = request.getParameterValues("user_ids");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "参数有误！";
		if (!GenericValidator.isInt(role_id) || ArrayUtils.isEmpty(user_ids)) {
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		for (String user_id : user_ids) {
			RoleUser queryHasExist = new RoleUser();
			queryHasExist.setRole_id(Integer.valueOf(role_id));
			queryHasExist.setUser_id(Integer.valueOf(user_id));
			queryHasExist = super.getFacade().getRoleUserService().getRoleUser(queryHasExist);
			if (null == queryHasExist) {
				RoleUser entity = new RoleUser();
				entity.setRole_id(Integer.valueOf(role_id));
				entity.setUser_id(Integer.valueOf(user_id));
				getFacade().getRoleUserService().createRoleUser(entity);
			}
		}

		ret = "1";
		msg = "操作成功！";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}
}