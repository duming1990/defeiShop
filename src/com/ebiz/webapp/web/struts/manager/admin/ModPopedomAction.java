package com.ebiz.webapp.web.struts.manager.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BasePopedom;
import com.ebiz.webapp.domain.ModPopedom;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.ArrayTools;

/**
 * @author Qin,Yue
 */
public class ModPopedomAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request, " -&gt; 授权");
		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request, " -&gt; 授权");
		DynaBean dynaBean = (DynaBean) form;
		String role_id = (String) dynaBean.get("role_id");
		String user_id = (String) dynaBean.get("user_id");
		if (StringUtils.isBlank(role_id)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		List<SysModule> sysModuleAllList = new ArrayList<SysModule>();
		SysModule entity = new SysModule();
		entity.setIs_del(0);
		entity.setIs_public(0);
		entity.setMod_level(Keys.ModLevel.MOD_LEVEL_1.getIndex());
		if (Integer.valueOf(role_id) == Keys.RoleType.ROLE_TYPE_2.getIndex())
			entity.getMap().put("mod_group_in", Keys.ModGroup.MOD_GROUP_2.getIndex());
		else if (Integer.valueOf(role_id) == Keys.RoleType.ROLE_TYPE_3.getIndex())
			entity.getMap().put("mod_group_in", Keys.ModGroup.MOD_GROUP_3.getIndex());
		else if (Integer.valueOf(role_id) == Keys.RoleType.ROLE_TYPE_4.getIndex())
			entity.getMap().put("mod_group_in", Keys.ModGroup.MOD_GROUP_4.getIndex());
		else if (Integer.valueOf(role_id) == Keys.RoleType.ROLE_TYPE_5.getIndex())
			entity.getMap().put("mod_group_in", Keys.ModGroup.MOD_GROUP_5.getIndex());
		else if (Integer.valueOf(role_id) == Keys.RoleType.ROLE_TYPE_19.getIndex())
			entity.getMap().put("mod_group_in", Keys.ModGroup.MOD_GROUP_6.getIndex());
		else
			entity.setMod_group(Keys.RoleType.ROLE_TYPE_1.getIndex());

		List<SysModule> sysModuleList = getFacade().getSysModuleService().getSysModuleList(entity);
		for (SysModule sm : sysModuleList) {
			List<SysModule> sysModuleSonList = getFacade().getSysModuleService().proGetSysModuleSonList(sm);
			if (null != sysModuleSonList && sysModuleSonList.size() > 0) {
				sysModuleAllList.addAll(sysModuleSonList);
			}
		}

		BasePopedom basePopedom = new BasePopedom();
		basePopedom.setIs_base(1);
		List<BasePopedom> basePopedomList = super.getFacade().getBasePopedomService().getBasePopedomList(basePopedom);

		for (SysModule webSysModule : sysModuleAllList) {
			// if (null != webSysModule.getMod_url()) {
			List<BasePopedom> basePopedomList1 = new ArrayList<BasePopedom>();
			String[] webModPeopedoms = null;
			BasePopedom bp = new BasePopedom();
			if (null != webSysModule.getPpdm_code()) {
				bp.setPpdm_code(new Integer(webSysModule.getPpdm_code()));
			}
			bp = super.getFacade().getBasePopedomService().getBasePopedom(bp);
			String ppdm_detail = bp.getPpdm_detail();
			webModPeopedoms = StringUtils.split(ppdm_detail, "+");
			for (BasePopedom basePopedom1 : basePopedomList) {
				if (ArrayUtils.contains(webModPeopedoms, basePopedom1.getPpdm_code().toString())) {
					basePopedomList1.add(basePopedom1);
				}
			}
			webSysModule.setBasePopedomList(basePopedomList1);

			ModPopedom modPopedom = new ModPopedom();
			modPopedom.setMod_id(webSysModule.getMod_id().intValue());
			if (!StringUtils.isBlank(role_id)) {
				modPopedom.setRole_id(new Integer(role_id));
			} else if (!StringUtils.isBlank(user_id)) {
				modPopedom.setUser_id(new Integer(user_id));
			}
			String[] selectedModPeopedoms = null;
			modPopedom = super.getFacade().getModPopedomService().getModPopedom(modPopedom);
			if (null != modPopedom) {
				BasePopedom bp2 = new BasePopedom();
				bp2.setPpdm_code(new Integer(modPopedom.getPpdm_code()));
				bp2 = super.getFacade().getBasePopedomService().getBasePopedom(bp2);
				if (null != bp2) {
					String ppdm_detail2 = bp2.getPpdm_detail();
					selectedModPeopedoms = StringUtils.split("+" + ppdm_detail2, "+");
				}
			}
			if (null != selectedModPeopedoms) {
				request.setAttribute("mod_popedom_" + webSysModule.getMod_id(), selectedModPeopedoms);
			}
		}
		request.setAttribute("sysModuleList", ArrayTools.removeDuplication(sysModuleAllList, "mod_id"));
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String mod_id = (String) dynaBean.get("mod_id");
		String role_id = (String) dynaBean.get("role_id");
		String user_id = (String) dynaBean.get("user_id");
		String isShouQuanUserTpye23 = (String) dynaBean.get("isShouQuanUserTpye23");
		String url = (String) dynaBean.get("url");

		List<SysModule> sysModuleAllList = new ArrayList<SysModule>();
		SysModule entity = new SysModule();
		entity.setIs_del(0);
		entity.setIs_public(0);
		entity.setMod_level(1);
		List<SysModule> sysModuleList = getFacade().getSysModuleService().getSysModuleList(entity);
		for (SysModule sm : sysModuleList) {
			List<SysModule> sysModuleSonList = getFacade().getSysModuleService().proGetSysModuleSonList(sm);
			if (null != sysModuleSonList && sysModuleSonList.size() > 0) {
				sysModuleAllList.addAll(sysModuleSonList);
			}
		}

		ModPopedom mod_popedom = new ModPopedom();
		if (StringUtils.isNotBlank(role_id)) {
			mod_popedom.setRole_id(new Integer(role_id));
		} else if (StringUtils.isNotBlank(user_id)) {
			mod_popedom.setUser_id(new Integer(user_id));
		}

		Set<String> modIdSet = new HashSet<String>();
		List<ModPopedom> modPopedomList = new ArrayList<ModPopedom>();
		int listIndex = -1;

		for (SysModule sysModule : sysModuleAllList) {
			String _mod_id = sysModule.getMod_id().toString();
			String parameterName = "checkbox_" + _mod_id;
			String[] selectedModPeopedoms = request.getParameterValues(parameterName);

			if (null != selectedModPeopedoms) {
				if (modIdSet.add(_mod_id)) {
					ModPopedom modPopedom = new ModPopedom();
					Integer popedomSum = 0;
					for (int i = 0; i < selectedModPeopedoms.length; i++) {
						Integer popedom = Integer.valueOf(selectedModPeopedoms[i]);
						popedomSum += popedom;
					}
					modPopedom.setPpdm_code(popedomSum.toString());
					modPopedom.setMod_id(new Integer(_mod_id));

					modPopedomList.add(modPopedom);
					listIndex++;
				} else {
					Integer popedomSum = 0;
					for (int i = 0; i < selectedModPeopedoms.length; i++) {
						Integer popedom = Integer.valueOf(selectedModPeopedoms[i]);
						popedomSum += popedom;
					}

					ModPopedom modPopedom = modPopedomList.get(listIndex);
					if (null != modPopedom) {
						if (popedomSum > Integer.valueOf(modPopedom.getPpdm_code())) {
							modPopedom.setPpdm_code(popedomSum.toString());
						}
					}
				}
			}
		}

		mod_popedom.setModPopedomList(modPopedomList);
		super.getFacade().getModPopedomService().createModPopedom(mod_popedom);

		String msg = super.getMessage(request, "entity.updated");
		if (StringUtils.isNotBlank(isShouQuanUserTpye23))
			super.renderJavaScript(response, "alert('" + msg + "');window.parent.location.href='" + url + ".do?mod_id="
					+ mod_id + "';");
		else
			super.renderJavaScript(response, "alert('" + msg + "');location.href='" + url + ".do?mod_id=" + mod_id
					+ "';");
		return null;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request, " -&gt; 授权");
		DynaBean dynaBean = (DynaBean) form;
		String role_id = (String) dynaBean.get("role_id");
		String user_id = (String) dynaBean.get("user_id");

		List<SysModule> sysModuleAllList = new ArrayList<SysModule>();
		SysModule entity = new SysModule();
		entity.setIs_del(0);
		entity.setIs_public(0);
		entity.setMod_level(1);
		List<SysModule> sysModuleList = getFacade().getSysModuleService().getSysModuleList(entity);
		for (SysModule sm : sysModuleList) {
			List<SysModule> sysModuleSonList = getFacade().getSysModuleService().proGetSysModuleSonList(sm);
			if (null != sysModuleSonList && sysModuleSonList.size() > 0) {
				sysModuleAllList.addAll(sysModuleSonList);
			}
		}

		BasePopedom basePopedom = new BasePopedom();
		basePopedom.setIs_base(1);
		List<BasePopedom> basePopedomList = super.getFacade().getBasePopedomService().getBasePopedomList(basePopedom);

		for (SysModule webSysModule : sysModuleAllList) {
			if (null != webSysModule.getMod_url()) {
				List<BasePopedom> basePopedomList1 = new ArrayList<BasePopedom>();
				String[] webModPeopedoms = null;
				String ppdm_detail = (String) webSysModule.getMap().get("ppdm_detail");
				webModPeopedoms = StringUtils.split(ppdm_detail, "+");
				for (BasePopedom basePopedom1 : basePopedomList) {
					if (ArrayUtils.contains(webModPeopedoms, basePopedom1.getPpdm_code().toString())) {
						basePopedomList1.add(basePopedom1);
					}
				}
				webSysModule.setBasePopedomList(basePopedomList1);

				ModPopedom modPopedom = new ModPopedom();
				modPopedom.setMod_id(webSysModule.getMod_id().intValue());
				if (!StringUtils.isBlank(role_id)) {
					modPopedom.setRole_id(new Integer(role_id));
				} else if (!StringUtils.isBlank(user_id)) {
					modPopedom.setUser_id(new Integer(user_id));
				}
				String[] selectedModPeopedoms = null;
				modPopedom = super.getFacade().getModPopedomService().getModPopedom(modPopedom);
				if (null != modPopedom) {
					selectedModPeopedoms = StringUtils
							.split("+" + (String) modPopedom.getMap().get("ppdm_detail"), "+");
				}
				if (null != selectedModPeopedoms) {
					request.setAttribute("mod_popedom_" + webSysModule.getMod_id(), selectedModPeopedoms);
				}
			}
		}
		request.setAttribute("sysModuleList", sysModuleList);
		return mapping.findForward("view");
	}
}
