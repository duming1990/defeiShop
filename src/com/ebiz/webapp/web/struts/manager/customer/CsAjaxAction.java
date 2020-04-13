package com.ebiz.webapp.web.struts.manager.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.struts.BaseCsAjaxAction;
import com.ebiz.webapp.web.util.DESPlus;

/**
 * @author Wu,Yang
 * @version 2011-04-27
 */
public class CsAjaxAction extends BaseCsAjaxAction {

	public ActionForward updateCommNote(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		if (!GenericValidator.isInt(comm_id) || StringUtils.isBlank(comm_id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(comm_id));
		entity = super.getFacade().getCommInfoService().getCommInfo(entity);
		if (null == entity) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, entity);

		return new ActionForward("/customer/CsAjax/updateCommNote.jsp");
	}

	public ActionForward saveCommNote(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		JSONObject jsonObject = new JSONObject();

		String ret = "0";
		String msg = "";
		if (!GenericValidator.isInt(comm_id) || StringUtils.isBlank(comm_id)) {
			msg = "参数有误，请联系管理员！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(comm_id));
		entity = super.getFacade().getCommInfoService().getCommInfo(entity);
		if (null == entity) {
			msg = "未查询到该商品，无法修改！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		CommInfo entityUpdate = new CommInfo();
		super.copyProperties(entityUpdate, form);
		entityUpdate.setId(Integer.valueOf(comm_id));
		int updateCount = super.getFacade().getCommInfoService().modifyCommInfo(entityUpdate);
		if (updateCount <= 0) {
			msg = "修改商品失败！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		} else {
			ret = "1";
			msg = "修改商品成功！";
		}
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	public ActionForward initPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uid = request.getParameter("uid");
		String password = request.getParameter("password");

		UserInfo entity = new UserInfo();
		entity.setId(new Integer(uid));
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		entity.setIs_has_update_pass(1);
		// entity.setPassword(EncryptUtils.MD5Encode(password));
		JSONObject result = new JSONObject();
		int rows = super.getFacade().getUserInfoService().modifyUserInfo(entity);
		String msg = getMessage(request, "password.updated.success");

		result.put("result", rows);
		result.put("msg", msg);

		super.render(response, result.toString(), "text/x-json;charset=UTF-8");
		return null;
	}
}
