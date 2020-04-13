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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ReturnsInfo;
import com.ebiz.webapp.domain.ShippingAddress;

/**
 * 退换货审核功能
 * 
 * @author Li,Yuan
 * @version 2013-07-04
 */
public class ReturnBackAuditAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;

		String pd_name_like = (String) dynaBean.get("pd_name_like");
		// String audit_status = (String) dynaBean.get("audit_status");
		ReturnsInfo entity = new ReturnsInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(pd_name_like)) {
			entity.getMap().put("pd_name_like", pd_name_like);
		}

		Pager pager = (Pager) dynaBean.get("pager");

		Integer recordCount = getFacade().getReturnsInfoService().getReturnsInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ReturnsInfo> entityList = getFacade().getReturnsInfoService().getReturnsInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		ReturnsInfo entity = new ReturnsInfo();
		super.copyProperties(entity, form);

		super.getFacade().getReturnsInfoService().modifyReturnsInfo(entity);

		saveMessage(request, "entity.audit");
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			ReturnsInfo returnsInfo = new ReturnsInfo();
			returnsInfo.setId(Integer.valueOf(id));
			returnsInfo = super.getFacade().getReturnsInfoService().getReturnsInfo(returnsInfo);

			if (null == returnsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			returnsInfo.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, returnsInfo);

			BaseFiles baseFiles = new BaseFiles();
			baseFiles.setLink_id(Integer.valueOf(id));
			baseFiles.setType(10);
			baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
			if (null != baseFiles) {
				dynaBean.set("save_path", baseFiles.getSave_path());
			}

			BaseFiles baseFiles1 = new BaseFiles();
			baseFiles1.setLink_id(returnsInfo.getId());
			baseFiles1.setType(20);
			baseFiles1 = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles1);
			if (null != baseFiles1) {
				dynaBean.set("save_path1", baseFiles1.getSave_path());
			}

			// 订单产品详细
			OrderInfoDetails oid = new OrderInfoDetails();
			oid.setId(returnsInfo.getOrder_info_details_id());
			oid = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetails(oid);

			// 订单信息
			if (null != oid) {
				OrderInfo oi = new OrderInfo();
				oi.setId(oid.getOrder_id());
				oi = super.getFacade().getOrderInfoService().getOrderInfo(oi);
				request.setAttribute("oi", oi);

				// 联系人地址
				ShippingAddress spa = new ShippingAddress();
				spa.setId(oi.getShipping_address_id());
				spa = super.getFacade().getShippingAddressService().getShippingAddress(spa);
				request.setAttribute("spa", spa);

			}
			return mapping.findForward("input");

		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}
}
