package com.ebiz.webapp.web.struts.m;

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
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.InvoiceInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2012-03-30
 */
public class MMyShippingAddressAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);
		request.setAttribute("titleSideName", Keys.TopBtns.ADD.getName());
		super.getModNameForMobile(request);
		Pager pager = (Pager) dynaBean.get("pager");
		String id = (String) dynaBean.get("id");

		ShippingAddress entity = new ShippingAddress();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		if ((null != id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
		}
		entity.setAdd_user_id(ui.getId());
		Integer recordCount = getFacade().getShippingAddressService().getShippingAddressCount(entity);

		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ShippingAddress> saList = getFacade().getShippingAddressService().getShippingAddressPaginatedList(entity);
		if ((null != saList) && (saList.size() > 0)) {
			for (ShippingAddress sa : saList) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(Long.valueOf(sa.getRel_region()));
				baseProvince.setIs_del(0);
				baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				if (null != baseProvince) {
					sa.getMap().put("full_name", baseProvince.getFull_name());
				}
			}
		}

		request.setAttribute("shippingAddressList", saList);
		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "添加地址");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("rel_name", ui.getReal_name());
		dynaBean.set("rel_phone", ui.getMobile());

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		ShippingAddress entity = new ShippingAddress();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		if (!StringUtils.isNotBlank(id)) {// 插入信息
			entity.setIs_default(0);
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_date(new Date());
			getFacade().getShippingAddressService().createShippingAddress(entity);

			data.put("code", "1");
			data.put("msg", "添加收货地址成功！");
			super.renderJson(response, data.toString());
			return null;

		} else {// 修改信息
			entity.setUpdate_user_id(ui.getId());
			entity.setUpdate_date(new Date());
			getFacade().getShippingAddressService().modifyShippingAddress(entity);

			InvoiceInfo invoiceInfojudge = new InvoiceInfo();
			invoiceInfojudge.setShipping_id(entity.getId());
			getFacade().getInvoiceInfoService().getInvoiceInfoCount(invoiceInfojudge);

			data.put("code", "1");
			data.put("msg", "修改收货地址成功！");
			super.renderJson(response, data.toString());
			return null;

		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "修改地址");
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ShippingAddress shippingAddress = new ShippingAddress();
			shippingAddress.setId(Integer.valueOf(id));
			shippingAddress = getFacade().getShippingAddressService().getShippingAddress(shippingAddress);

			if (null == shippingAddress) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(Long.valueOf(shippingAddress.getRel_region()));
			baseProvince.setIs_del(0);
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {
				dynaBean.set("full_name", baseProvince.getFull_name());
			}

			super.copyProperties(form, shippingAddress);
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		ShippingAddress entity = new ShippingAddress();
		entity.setIs_del(1);
		entity.setDel_date(new Date());
		entity.setDel_user_id(ui.getId());

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(new Integer(id));
			getFacade().getShippingAddressService().modifyShippingAddress(entity);
		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getShippingAddressService().modifyShippingAddress(entity);
		}

		saveMessage(request, "entity.deleted");
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward updateDefault(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (!GenericValidator.isLong(id)) {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
		ShippingAddress entity = new ShippingAddress();
		entity.setAdd_user_id(ui.getId());
		entity.setIs_default(0);
		getFacade().getShippingAddressService().modifyShippingAddress(entity);

		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setId(Integer.valueOf(id));
		shippingAddress.setIs_default(1);
		getFacade().getShippingAddressService().modifyShippingAddress(shippingAddress);

		saveMessage(request, "entity.updated");

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;

	}
}
