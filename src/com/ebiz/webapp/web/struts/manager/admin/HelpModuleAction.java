package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.HelpModule;
import com.ebiz.webapp.web.util.StringHelper;

/**
 * @author Wu, Yang
 */
public class HelpModuleAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("list");
	}

	public ActionForward showHelpModuleTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<HelpModule> deptInfoList = getFacade().getHelpModuleService().getHelpModuleList(new HelpModule());

		String treeNodes = StringHelper.getTreeNodesFromHelpModuleList(deptInfoList);

		request.setAttribute("treeNodes", treeNodes);

		return new ActionForward("/admin/HelpModule/helpModuleTree.jsp");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		if (StringUtils.isNotBlank(h_mod_id)) {
			HelpModule helpModule = new HelpModule();
			if (StringUtils.isNotBlank(h_mod_id)) {
				helpModule.setH_mod_id(Integer.valueOf(h_mod_id));
				String level_4 = StringUtils.substring(h_mod_id, 6, 8);
				if (StringUtils.isNotBlank(level_4) && Integer.valueOf(level_4) > 0) {
					dynaBean.set("level_4", "true");
				}
			}
			HelpModule entity = getFacade().getHelpModuleService().getHelpModule(helpModule);
			super.copyProperties(form, entity);
		} else {
			super.saveMessage(request, "entity.missing");
		}

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		HelpModule entity = new HelpModule();
		super.copyProperties(entity, dynaBean);

		String save_level_1 = "00";
		String save_level_2 = "00";
		String save_level_3 = "00";
		String save_level_4 = "00";

		String save_level_mod_id = "";
		String msg = "success";
		Integer level_number_99 = 0;
		if (StringUtils.isNotBlank(h_mod_id) && null == entity.getId()) {

			save_level_mod_id = setHmodID(h_mod_id, save_level_1, save_level_2, save_level_3, save_level_4)[0];
			level_number_99 = Integer.valueOf(setHmodID(h_mod_id, save_level_1, save_level_2, save_level_3,
					save_level_4)[1]);

			if (level_number_99 > 99) {
				String msg_99 = super.getMessage(request, "help.module.treeNodes.gt.99");
				super.renderJavaScript(response, "alert('" + msg_99 + "');history.back();");
				return null;
			}

			entity.setH_mod_id(Integer.valueOf(save_level_mod_id));

			entity.setPar_id(new Integer(h_mod_id));
			getFacade().getHelpModuleService().createHelpModule(entity);
			msg = getMessage(request, "entity.inerted");
		} else {
			getFacade().getHelpModuleService().modifyHelpModule(entity);
			msg = getMessage(request, "entity.updated");
		}
		super.renderJavaScript(response, "alert('" + msg + "');window.parent.frames[0].location.reload(true);");
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String h_mod_id = (String) dynaBean.get("h_mod_id");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			HelpModule entity = new HelpModule();
			entity.setId(new Integer(id));
			entity.setH_mod_id(Integer.valueOf(h_mod_id));
			getFacade().getHelpModuleService().removeHelpModule(entity);
		}

		super.renderJavaScript(response, "alert(\"" + super.getMessage(request, "entity.deleted")
				+ "\");window.parent.frames[0].location.reload(true);");
		return null;
	}

	private String[] setHmodID(String h_mod_id, String save_level_1, String save_level_2, String save_level_3,
			String save_level_4) {
		String[] returnDate = new String[2];
		Integer level_number_99 = 10;
		if ("1".equals(h_mod_id)) {
			HelpModule helpModule_level = new HelpModule();
			helpModule_level.setPar_id(Integer.valueOf(1));
			helpModule_level.getMap().put("start_index", "1");
			helpModule_level.getMap().put("setp", "2");
			Integer level_number = getFacade().getHelpModuleService().getHelpModuleWithLevelNumber(helpModule_level);
			if (null != level_number) {
				level_number_99 = level_number;
				save_level_1 = String.valueOf(level_number);
				save_level_1 = StringUtils.rightPad(save_level_1, 2, "0");
			}
		} else {
			String level_1 = StringUtils.substring(h_mod_id, 0, 2);
			String level_2 = StringUtils.substring(h_mod_id, 2, 4);
			String level_3 = StringUtils.substring(h_mod_id, 4, 6);
			String level_4 = StringUtils.substring(h_mod_id, 6, 8);
			save_level_1 = level_1;
			save_level_2 = level_2;
			save_level_3 = level_3;
			save_level_4 = level_4;

			if (Integer.valueOf(level_1) > 0) {
				save_level_1 = level_1;
			}
			if (Integer.valueOf(level_2) == 0) {
				HelpModule helpModule_level = new HelpModule();
				helpModule_level.setPar_id(Integer.valueOf(h_mod_id));
				helpModule_level.getMap().put("start_index", "3");
				helpModule_level.getMap().put("setp", "2");
				Integer level_number = getFacade().getHelpModuleService()
						.getHelpModuleWithLevelNumber(helpModule_level);
				if (null != level_number) {
					level_number_99 = level_number;
					save_level_2 = String.valueOf(level_number);
					save_level_2 = StringUtils.leftPad(save_level_2, 2, "0");

				}
			} else if (Integer.valueOf(level_3) == 0) {
				HelpModule helpModule_level = new HelpModule();
				helpModule_level.setPar_id(Integer.valueOf(h_mod_id));
				helpModule_level.getMap().put("start_index", "5");
				helpModule_level.getMap().put("setp", "2");
				Integer level_number = getFacade().getHelpModuleService()
						.getHelpModuleWithLevelNumber(helpModule_level);
				if (null != level_number) {
					level_number_99 = level_number;
					save_level_3 = String.valueOf(level_number);
					save_level_3 = StringUtils.leftPad(save_level_3, 2, "0");
				}
			} else if (Integer.valueOf(level_4) == 0) {
				HelpModule helpModule_level = new HelpModule();
				helpModule_level.setPar_id(Integer.valueOf(h_mod_id));
				helpModule_level.getMap().put("start_index", "7");
				helpModule_level.getMap().put("setp", "2");
				Integer level_number = getFacade().getHelpModuleService()
						.getHelpModuleWithLevelNumber(helpModule_level);
				if (null != level_number) {
					level_number_99 = level_number;
					save_level_4 = String.valueOf(level_number);
					save_level_4 = StringUtils.leftPad(save_level_4, 2, "0");
				}
			}
		}
		returnDate[0] = save_level_1 + save_level_2 + save_level_3 + save_level_4;
		returnDate[1] = String.valueOf(level_number_99);
		return returnDate;
	}
}
