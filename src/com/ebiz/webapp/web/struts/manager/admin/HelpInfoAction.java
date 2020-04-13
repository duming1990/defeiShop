package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.HelpInfo;
import com.ebiz.webapp.domain.HelpModule;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.StringHelper;

/**
 * @author Wu, Yang
 */
public class HelpInfoAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.helpIndex(mapping, form, request, response);
	}

	public ActionForward helpIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ActionForward("/admin/HelpInfo/helpIndexFrame.jsp");
	}

	public ActionForward helpLeft(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<HelpModule> helpModuleList = getFacade().getHelpModuleService().getHelpModuleList(new HelpModule());
		String treeNodes = StringHelper.getTreeNodesForHelpModule(helpModuleList);
		request.setAttribute("treeNodes", treeNodes);
		return new ActionForward("/admin/HelpInfo/helpLeftFrame.jsp");
	}

	public ActionForward helpMain(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ActionForward("/admin/HelpInfo/helpMainFrame.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToScopeForHelpModule(form, request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		// String info_state = (String) dynaBean.get("info_state");

		HelpInfo entity = new HelpInfo();
		super.copyProperties(entity, form);

		if (null == entity.getIs_del()) {
			entity.setIs_del(Integer.valueOf(0));
		}

		Integer recordCount = getFacade().getHelpInfoService().getHelpInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<HelpInfo> entityList = super.getFacade().getHelpInfoService().getHelpInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		super.copyProperties(form, entity);

		return mapping.findForward("list");
	}

	public ActionForward single(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		setNaviStringToScopeForHelpModule(form, request);

		DynaBean dynaBean = (DynaBean) form;
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		dynaBean.set("isSingle", "true");
		HelpInfo helpInfo = new HelpInfo();
		helpInfo.setH_mod_id(Integer.valueOf(h_mod_id));
		helpInfo.setIs_del(0);
		Integer count = getFacade().getHelpInfoService().getHelpInfoCount(helpInfo);
		if (count.intValue() > 0) {
			helpInfo.setId(getFacade().getHelpInfoService().getHelpInfoList(helpInfo).get(0).getId());
		}
		helpInfo = getFacade().getHelpInfoService().getHelpInfo(helpInfo);
		if (null != helpInfo) {
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append("/admin/HelpInfo.do?method=edit");
			pathBuffer.append("&h_mod_id=" + h_mod_id);
			pathBuffer.append("&id=" + helpInfo.getId());
			pathBuffer.append("&isSingle=true");
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			return forward;
		}

		HelpInfo entity = new HelpInfo();
		entity.setH_mod_id(Integer.valueOf(h_mod_id));
		entity.setOrder_value(0);
		entity.setIs_del(0);
		entity.setPub_date(new Date());

		super.copyProperties(form, entity);
		return mapping.findForward("input");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToScopeForHelpModule(form, request);
		DynaBean dynaBean = (DynaBean) form;

		dynaBean.set("order_value", 0);
		dynaBean.set("is_del", 0);

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		dynaBean.set("pub_user_name", userInfo.getUser_name());
		dynaBean.set("pub_user_id", userInfo.getId());
		dynaBean.set("is_common_q", 0);

		return mapping.findForward("input");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToScopeForHelpModule(form, request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String h_mod_id = (String) dynaBean.get("h_mod_id");
		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}

		HelpInfo entity = super.getFacade().getHelpInfoService().getHelpInfo(new HelpInfo(Integer.valueOf(id)));
		if (null == entity) {
			saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}

		entity.setQueryString(super.serialize(request, "id", "h_mod_id"));
		super.copyProperties(form, entity);
		dynaBean.set("h_mod_id", h_mod_id);

		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToScopeForHelpModule(form, request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}

		HelpInfo entity = super.getFacade().getHelpInfoService().getHelpInfo(new HelpInfo(Integer.valueOf(id)));
		if (null == entity) {
			saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}

		entity.setQueryString(super.serialize(request, "id", "h_mod_id"));
		super.copyProperties(form, entity);

		dynaBean.set("h_mod_id", h_mod_id);

		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToScopeForHelpModule(form, request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String h_mod_id = (String) dynaBean.get("h_mod_id");
		String isSingle = (String) dynaBean.get("isSingle");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		HelpInfo entity = new HelpInfo();
		super.copyProperties(entity, form);
		entity.setPub_user_id(userInfo.getId());
		if (StringUtils.isNotBlank(h_mod_id)) {
			entity.setH_mod_id(Integer.valueOf(h_mod_id));

			// set Father h_mod_id
			HelpModule helpModule = new HelpModule();
			helpModule.setH_mod_id(Integer.valueOf(h_mod_id));
			helpModule = getFacade().getHelpModuleService().getHelpModule(helpModule);
			if (null != helpModule) {
				HelpModule helpModule1 = new HelpModule();
				helpModule1.setH_mod_id(helpModule.getPar_id());
				helpModule1 = getFacade().getHelpModuleService().getHelpModule(helpModule);
				if (null != helpModule1) {// 以后有时间在改成取par_id 为1的节点
					entity.setFa_h_mod_id(helpModule1.getPar_id());
				}
			}

		}

		if (StringUtils.isBlank(id)) {
			entity.setPub_date(new Date());
			super.getFacade().getHelpInfoService().createHelpInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {
			entity.setModify_date(new Date());
			super.getFacade().getHelpInfoService().modifyHelpInfo(entity);
			saveMessage(request, "entity.updated");
		}

		StringBuffer pathBuffer = new StringBuffer();
		if (StringUtils.isNotBlank(isSingle)) {
			pathBuffer.append("/admin/HelpInfo.do?method=edit");
			pathBuffer.append("&h_mod_id=" + h_mod_id);
			pathBuffer.append("&id=" + entity.getId());
			pathBuffer.append("&isSingle=true");
		} else {
			// the line below is added for pagination
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&h_mod_id=" + h_mod_id);
			pathBuffer.append("&").append(super.encodeSerializedQueryString(entity.getQueryString()));
			// end
		}
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToScopeForHelpModule(form, request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String h_mod_id = (String) dynaBean.get("h_mod_id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			HelpInfo entity = new HelpInfo();
			entity.setId(new Integer(id));
			getFacade().getHelpInfoService().removeHelpInfo(entity);
		} else if (!ArrayUtils.isEmpty(pks)) {
			HelpInfo entity = new HelpInfo();
			entity.getMap().put("pks", pks);
			super.getFacade().getHelpInfoService().removeHelpInfo(entity);
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&h_mod_id=" + h_mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	/**
	 * @author Wu,Yang
	 */
	protected void setNaviStringToScopeForHelpModule(ActionForm form, HttpServletRequest request) {
		DynaBean dynaBean = (DynaBean) form;
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		String naviString = "";
		if (StringUtils.isNotBlank(h_mod_id)) {
			HelpModule helpModule = new HelpModule();
			helpModule.setH_mod_id(new Integer(h_mod_id));
			List<HelpModule> helpModuleList = getFacade().getHelpModuleService().getHelpModuleParentList(helpModule);
			naviString = StringHelper.getNaviStringForHelpModule(helpModuleList);
		}
		request.setAttribute("naviString", naviString);
	}
}
