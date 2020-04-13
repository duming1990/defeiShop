package com.ebiz.webapp.web.struts.manager.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.UserInfo;

//import com.ebiz.tjgis.web.util.DESPlus;

/**
 * @author Wu,yang
 * @version 2015-12-6
 */
public class MyAccountAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			saveError(request, "entity.missing");
			return mapping.findForward("input");
		}
		super.copyProperties(form, entity);
		if (null != entity.getId_card()) {
			dynaBean.set("encryptIdCard", super.encryptIdCard(entity.getId_card()));
		}

		if (null != entity.getP_index()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index());
		}

		return mapping.findForward("input");
	}

	public ActionForward saveUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		logger.info("==p_index:{}", p_index);
		if (!GenericValidator.isInt(p_index)) {
			String msg = "请选择所属区域";
			data.put("code", "0");
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		BaseProvince bp = super.getBaseProvince(Long.valueOf(p_index));
		if (null == bp) {
			String msg = "区域不存在";
			data.put("code", "0");
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		// if (bp.getP_level().intValue() != 3) {
		// String msg = "区域必须选择到区县";
		// data.put("code", "0");
		// data.put("msg", msg);
		// super.renderJson(response, data.toString());
		// return null;
		// }

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		super.copyProperties(entity, form);
		entity.setId(ui.getId());
		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		data.put("code", "1");
		data.put("msg", "修改成功！");
		super.renderJson(response, data.toString());
		return null;
	}

}
