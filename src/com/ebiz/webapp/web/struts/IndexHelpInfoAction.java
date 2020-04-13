package com.ebiz.webapp.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.HelpInfo;
import com.ebiz.webapp.domain.HelpModule;

/**
 * @author Zhang,Xufeng
 * @version 2012-06-11 网站底部相关信息
 */
public class IndexHelpInfoAction extends BaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");

		String h_mod_id = (String) dynaBean.get("h_mod_id");
		if (StringUtils.isNotBlank(h_mod_id)) {
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

		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}

		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);
		super.setPublicInfoWithHelpList(request);

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

		// entity.setQueryString(super.serialize(request, "h_mod_id"));
		super.copyProperties(form, entity);
		dynaBean.set("h_mod_id", h_mod_id);

		return mapping.findForward("view");
	}
}