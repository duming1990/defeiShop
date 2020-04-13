package com.ebiz.webapp.web.struts.m;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.HelpInfo;
import com.ebiz.webapp.domain.HelpModule;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MHelpDocumentAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		if (null != h_mod_id) {
			HelpModule helpModule = new HelpModule();
			helpModule.setPar_id(Integer.valueOf(h_mod_id));
			List<HelpModule> helpModulelist = getFacade().getHelpModuleService().getHelpModuleList(helpModule);
			if (null != helpModulelist && helpModulelist.size() > 0) {
				request.setAttribute("helpModulelist", helpModulelist);
			}
			HelpModule helpModule1 = new HelpModule();
			helpModule1.setH_mod_id(Integer.valueOf(h_mod_id));
			helpModule1 = getFacade().getHelpModuleService().getHelpModule(helpModule1);
			if (null != helpModule1) {
				super.setTitle(request, helpModule1.getMod_name());
			}
		}
		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		if (!GenericValidator.isLong(h_mod_id)) {
			saveError(request, "errors.Integer", new String[] { h_mod_id });
			return mapping.findForward("list");
		}
		if (GenericValidator.isLong(h_mod_id)) {
			HelpModule helpModule = new HelpModule();
			helpModule.setH_mod_id(new Integer(h_mod_id));
			helpModule = getFacade().getHelpModuleService().getHelpModule(helpModule);
			if (null != helpModule) {
				HelpModule helpModulePar = new HelpModule();
				helpModulePar.setH_mod_id(helpModule.getPar_id());
				helpModulePar = getFacade().getHelpModuleService().getHelpModule(helpModulePar);
				if (null != helpModulePar) {
					request.setAttribute("par_mod_name", helpModulePar.getMod_name());
				}
				request.setAttribute("mod_name", helpModule.getMod_name());
			}
		}

		// 只有一条信息
		HelpInfo helpInfo = new HelpInfo();
		super.copyProperties(helpInfo, form);
		helpInfo.setIs_del(0);
		helpInfo.setH_mod_id(new Integer(h_mod_id));
		List<HelpInfo> helpInfoList = getFacade().getHelpInfoService().getHelpInfoList(helpInfo);

		HelpInfo entity = new HelpInfo();
		if (null != helpInfoList && helpInfoList.size() > 0) {
			super.copyProperties(entity, form);
			entity.setIs_del(0);
			entity.setId(helpInfoList.get(0).getId());
			entity = getFacade().getHelpInfoService().getHelpInfo(entity);
		}
		if (null == entity) {
			saveError(request, "errors.Integer", new String[] { h_mod_id });
			return mapping.findForward("list");
		}

		entity.setQueryString(super.serialize(request, "h_mod_id"));
		super.copyProperties(form, entity);

		super.setTitle(request, entity.getTitle());

		return mapping.findForward("view");
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HelpModule helpModule = new HelpModule();
		helpModule.setPar_id(1);
		List<HelpModule> helpModuleList = getFacade().getHelpModuleService().getHelpModuleList(helpModule);
		request.setAttribute("helpModuleList", helpModuleList);

		super.setTitle(request, "帮助中心");

		return new ActionForward("/../m/MHelpDocument/index.jsp");
	}
}
