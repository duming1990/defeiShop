package com.ebiz.webapp.web.struts.manager.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.ReturnsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.util.DESPlus;

/**
 * @author Wu,Yang
 * @version 2011-04-27
 */
public class CsAjaxAction extends BaseAdminAction {

	/**
	 * Ajax asynchronous request to get BaseProvince List
	 * 
	 * @return json: [[key, value],[key, value]..]
	 */
	public ActionForward getBaseProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("p_index");

		if (!GenericValidator.isLong(par_index)) {
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setPar_index(Long.valueOf(par_index));
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<String> dataList = new ArrayList<String>();

		for (BaseProvince entity : baseProvinceList) {
			dataList.add(StringUtils.join(new String[] { "[\"", entity.getP_name(), "\",\"",
					String.valueOf(entity.getP_index()), "\"]" }));
		}

		super.renderJson(response, StringUtils.join(new String[] { "[", StringUtils.join(dataList, ","), "]" }));
		return null;
	}

	/**
	 * @author Zhang,Xufeng
	 * @version 2011-05-11
	 * @desc 取得始发地的省市县信息
	 */
	public ActionForward getSrcBaseProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("src_p_index");

		if (!GenericValidator.isLong(par_index)) {
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setPar_index(Long.valueOf(par_index));
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<String> dataList = new ArrayList<String>();

		for (BaseProvince entity : baseProvinceList) {
			dataList.add(StringUtils.join(new String[] { "[\"", entity.getP_name(), "\",\"",
					String.valueOf(entity.getP_index()), "\"]" }));
		}

		super.renderJson(response, StringUtils.join(new String[] { "[", StringUtils.join(dataList, ","), "]" }));
		return null;
	}

	/**
	 * @author Zhang,Xufeng
	 * @version 2011-05-11
	 * @desc 取得目的地的省市县信息
	 */
	public ActionForward getDestBaseProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("dest_p_index");

		if (!GenericValidator.isLong(par_index)) {
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setPar_index(Long.valueOf(par_index));
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<String> dataList = new ArrayList<String>();

		for (BaseProvince entity : baseProvinceList) {
			dataList.add(StringUtils.join(new String[] { "[\"", entity.getP_name(), "\",\"",
					String.valueOf(entity.getP_index()), "\"]" }));
		}

		super.renderJson(response, StringUtils.join(new String[] { "[", StringUtils.join(dataList, ","), "]" }));
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

	public ActionForward payReturnsInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uid = request.getParameter("id");

		ReturnsInfo entity = new ReturnsInfo();
		entity.setId(new Integer(uid));
		entity.setAudit_status(4);
		JSONObject result = new JSONObject();
		int rows = super.getFacade().getReturnsInfoService().modifyReturnsInfo(entity);
		String msg = "恭喜！付款成功！";

		result.put("result", rows);
		result.put("msg", msg);

		super.render(response, result.toString(), "text/x-json;charset=UTF-8");
		return null;
	}
}
