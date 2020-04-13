package com.ebiz.webapp.web.struts.manager.customer;

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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.InvoiceInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;

/**
 * 注册用户后台---企业投诉
 * 
 * @author Wu,Yang
 * @version 2012-03-30
 */
public class MyShippingAddressAction extends BaseCustomerAction {

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
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

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
				InvoiceInfo invoiceInfo = new InvoiceInfo();
				invoiceInfo.setShipping_id(sa.getId());
				invoiceInfo.setIs_del(0);
				invoiceInfo = getFacade().getInvoiceInfoService().getInvoiceInfo(invoiceInfo);
				sa.setInvoiceInfo(invoiceInfo);
			}
		}

		request.setAttribute("shippingAddressList", saList);
		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		ShippingAddress entity = new ShippingAddress();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		if (!StringUtils.isNotBlank(id)) {// 插入信息
			entity.setIs_default(0);
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_date(new Date());
			entity.getMap().put("update_attr", "true");
			Integer sp_id = getFacade().getShippingAddressService().createShippingAddress(entity);

			InvoiceInfo invoiceInfo = new InvoiceInfo();
			invoiceInfo.setShipping_id(Integer.valueOf(sp_id));
			super.copyProperties(invoiceInfo, form);
			invoiceInfo.setAdd_user_id(ui.getId());
			invoiceInfo.setAdd_date(new Date());
			getFacade().getInvoiceInfoService().createInvoiceInfo(invoiceInfo);

			saveMessage(request, "entity.inerted");

		} else {// 修改信息
			entity.setUpdate_user_id(ui.getId());
			entity.setUpdate_date(new Date());
			getFacade().getShippingAddressService().modifyShippingAddress(entity);

			InvoiceInfo invoiceInfojudge = new InvoiceInfo();
			invoiceInfojudge.setShipping_id(entity.getId());
			Integer count = getFacade().getInvoiceInfoService().getInvoiceInfoCount(invoiceInfojudge);
			if (count > 0) {// 更新
				InvoiceInfo invoiceInfo = new InvoiceInfo();
				invoiceInfo.setShipping_id(entity.getId());
				super.copyProperties(invoiceInfo, form);
				invoiceInfo.setUpdate_date(new Date());
				invoiceInfo.setUpdate_user_id(ui.getId());
				invoiceInfo.setId(null);
				getFacade().getInvoiceInfoService().modifyInvoiceInfo(invoiceInfo);
			} else {// 新增
				InvoiceInfo invoiceInfo = new InvoiceInfo();
				super.copyProperties(invoiceInfo, form);
				invoiceInfo.setId(null);
				invoiceInfo.setShipping_id(entity.getId());
				invoiceInfo.setAdd_user_id(ui.getId());
				invoiceInfo.setAdd_date(new Date());
				getFacade().getInvoiceInfoService().createInvoiceInfo(invoiceInfo);
			}

			saveMessage(request, "entity.updated");

		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		// pathBuffer.append("&id=" + id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end

		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

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
			baseProvince.setP_index(shippingAddress.getRel_region().longValue());
			baseProvince.setIs_del(0);
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (null != baseProvince) {
				dynaBean.set("full_name", baseProvince.getFull_name());
			}

			// the line below is added for pagination
			shippingAddress.setQueryString(super.serialize(request, "id", "method"));
			// end

			InvoiceInfo invoiceInfo = new InvoiceInfo();
			invoiceInfo.setShipping_id(shippingAddress.getId());
			invoiceInfo.setIs_del(0);
			invoiceInfo = getFacade().getInvoiceInfoService().getInvoiceInfo(invoiceInfo);
			if (null != invoiceInfo) {
				invoiceInfo.setId(null);
				super.copyProperties(form, invoiceInfo);
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
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
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
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
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
