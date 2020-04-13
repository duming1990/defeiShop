package com.ebiz.webapp.web.struts.manager.admin;

import java.util.ArrayList;
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

import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.web.Keys;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class SysModuleAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");
		dynaBean.set("is_lock", "0");
		request.setAttribute("modGroups", Keys.ModGroup.values());

		String par_id = (String) dynaBean.get("par_id");
		if (GenericValidator.isLong(par_id)) {
			SysModule sysmodule = new SysModule();
			sysmodule.setMod_id(Long.valueOf(par_id));
			sysmodule = getFacade().getSysModuleService().getSysModule(sysmodule);
			if (null != sysmodule) {
				int mod_level = sysmodule.getMod_level().intValue() + 1;
				dynaBean.set("mod_level", mod_level);
				dynaBean.set("mod_group", sysmodule.getMod_group());
				String ppdm_code = "0";
				if (mod_level == 3) {
					ppdm_code = "7";
				}
				dynaBean.set("ppdm_code", ppdm_code);
			}
		}

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String mod_group = (String) dynaBean.get("mod_group");
		String mod_name_like = (String) dynaBean.get("mod_name_like");
		String mod_desc_like = (String) dynaBean.get("mod_desc_like");
		String mod_url_like = (String) dynaBean.get("mod_url_like");

		SysModule entity = new SysModule();
		super.copyProperties(entity, dynaBean);
		entity.setMod_id(null);
		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (null == mod_group) {
			entity.setMod_group(Keys.ModGroup.MOD_GROUP_1.getIndex());
			dynaBean.set("mod_group", Keys.ModGroup.MOD_GROUP_1.getIndex());
		}
		entity.getMap().put("mod_id_not_in", "1000000000");
		entity.getMap().put("mod_name_like", mod_name_like);
		entity.getMap().put("mod_desc_like", mod_desc_like);
		entity.getMap().put("mod_url_like", mod_url_like);
		// entity.getRow().setCount(8);
		List<SysModule> sysModuleList = getFacade().getSysModuleService().getSysModuleList(entity);
		// request.setAttribute("sysModuleList", sysModuleList);

		// 这里默认只支持最多三级
		List<SysModule> sysModule1List = new ArrayList<SysModule>();
		List<SysModule> sysModule2List = new ArrayList<SysModule>();
		List<SysModule> sysModule3List = new ArrayList<SysModule>();
		for (SysModule sys : sysModuleList) {
			if (sys.getMod_level().intValue() == 1) {
				sysModule1List.add(sys);
			}
			if (sys.getMod_level().intValue() == 2) {
				sysModule2List.add(sys);
			}
			if (sys.getMod_level().intValue() == 3) {
				sysModule3List.add(sys);
			}
		}
		for (SysModule sys1 : sysModule1List) {
			List<SysModule> sysModule1SonList = new ArrayList<SysModule>();
			for (SysModule sys2 : sysModule2List) {
				if (sys1.getMod_id().longValue() == sys2.getPar_id().longValue()) {
					sysModule1SonList.add(sys2);
				}
			}
			sys1.setSysModuleList(sysModule1SonList);
		}
		for (SysModule sys1 : sysModule1List) {
			for (SysModule sys2 : sys1.getSysModuleList()) {
				List<SysModule> sysModule2SonList = new ArrayList<SysModule>();
				for (SysModule sys3 : sysModule3List) {
					// logger.info("sys2:" + sys2.getMod_id() + "mod_name:" + sys2.getMod_name() + " sys3:"
					// + sys3.getMod_id() + "mod_name:" + sys3.getMod_name());
					if (sys2.getMod_id().longValue() == sys3.getPar_id().longValue()) {
						sysModule2SonList.add(sys3);
					}
				}
				// logger.info("sysModule2SonList:{}", sysModule2SonList.size());
				sys2.setSysModuleList(sysModule2SonList);
			}
		}

		request.setAttribute("sysModule1List", sysModule1List);

		request.setAttribute("modGroups", Keys.ModGroup.values());

		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
		SysModule entity = new SysModule();
		entity.setMod_id(Long.valueOf(id));
		entity = getFacade().getSysModuleService().getSysModule(entity);

		super.copyProperties(form, entity);
		dynaBean.set("mod_id", mod_id);// 赋值mod_id防止保存报没有权限操作问题

		request.setAttribute("modGroups", Keys.ModGroup.values());
		return mapping.findForward("input");
	}

	public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String this_mod_id = (String) dynaBean.get("this_mod_id");
		String mod_id = (String) dynaBean.get("mod_id");
		logger.info("this_mod_id:" + this_mod_id);
		if (!GenericValidator.isLong(this_mod_id)) {
			saveError(request, "errors.Integer", this_mod_id);
			return mapping.findForward("list");
		}
		SysModule entity = new SysModule();
		entity.setMod_id(Long.valueOf(this_mod_id));
		entity = getFacade().getSysModuleService().getSysModule(entity);
		// entity.setMod_id(null);
		request.setAttribute("this_mod_id", entity.getMod_id());
		super.copyProperties(form, entity);
		dynaBean.set("id", entity.getMod_id());
		dynaBean.set("mod_id", mod_id);// 赋值mod_id防止保存报没有权限操作问题

		request.setAttribute("modGroups", Keys.ModGroup.values());
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String mod_group = (String) dynaBean.get("mod_group");
		String mod_id_old = (String) dynaBean.get("mod_id_old");
		String is_copy = (String) dynaBean.get("is_copy");
		String id = (String) dynaBean.get("id");

		SysModule entity = new SysModule();
		super.copyProperties(entity, dynaBean);

		entity.setMod_id(Long.valueOf(id));

		if (GenericValidator.isLong(mod_id_old) && StringUtils.isBlank(is_copy)) {// update
			entity.getMap().put("mod_id_old", mod_id_old);
			getFacade().getSysModuleService().modifySysModule(entity);
			saveMessage(request, "entity.updated");
		} else {// insert
			entity.setIs_del(0);
			getFacade().getSysModuleService().createSysModule(entity);
			saveMessage(request, "entity.inerted");
		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&mod_group=").append(mod_group);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			SysModule entity = new SysModule();
			entity.getMap().put("mod_id_old", id);
			entity.setIs_del(1);
			getFacade().getSysModuleService().modifySysModule(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			SysModule entity = new SysModule();
			entity.setIs_del(1);
			entity.getMap().put("pks", pks);
			getFacade().getSysModuleService().modifySysModule(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			SysModule entity = new SysModule();
			entity.setMod_id(new Long(id));
			entity = getFacade().getSysModuleService().getSysModule(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, entity);
			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward checkModid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		SysModule entity = new SysModule();
		entity.getMap().put("mod_id_not_in", id);
		entity.setMod_id(Long.valueOf(mod_id));
		Integer recordCount = super.getFacade().getSysModuleService().getSysModuleCount(entity);
		String flag = "1";
		if (recordCount.intValue() == 0) {
			flag = "0";
		}
		super.renderJson(response, flag);
		return null;
	}

}
