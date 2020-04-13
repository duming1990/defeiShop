package com.ebiz.webapp.web.struts.manager.customer;

import java.math.BigDecimal;
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
import com.ebiz.webapp.domain.InvoiceInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ReturnsInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserScoreRecord;

/**
 * @author Liu,Jia
 * @version 2014-05-28
 */
public class UserScoreRecordAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoListWithEntpAndCustomer(request);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String trade_index = (String) dynaBean.get("trade_index");
		UserScoreRecord entity = new UserScoreRecord();
		super.copyProperties(entity, form);
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(0);
		entity.getMap().put("trade_index", trade_index);

		Integer recordCount = getFacade().getUserScoreRecordService().getUserScoreRecordCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserScoreRecord> entityList = super.getFacade().getUserScoreRecordService()
				.getUserScoreRecordPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo.setIs_del(0);
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null != userInfo) {
			request.setAttribute("cur_score", userInfo.getCur_score());
		}

		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		String sal = (String) dynaBean.get("sal");
		if (StringUtils.isNotBlank(sal)) {
			request.setAttribute("sal", true);
		}
		if (!GenericValidator.isLong(order_id)) {
			String msg = "参数有误，请联系管理员！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		// 订单信息
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null != orderInfo) {

			request.setAttribute("orderInfo", orderInfo);

			// 收获地址表
			ShippingAddress spa = new ShippingAddress();
			spa.setId(orderInfo.getShipping_address_id());
			spa = super.getFacade().getShippingAddressService().getShippingAddress(spa);
			request.setAttribute("shippingAddress", spa);
			if (null != spa) {
				// 发票信息
				InvoiceInfo invoiceInfo = new InvoiceInfo();
				invoiceInfo.setShipping_id(spa.getId());
				invoiceInfo = super.getFacade().getInvoiceInfoService().getInvoiceInfo(invoiceInfo);
				request.setAttribute("invoiceInfo", invoiceInfo);
			}
			// 产品详细
			OrderInfoDetails orderInfoDetail = new OrderInfoDetails();
			orderInfoDetail.setOrder_id(Integer.valueOf(order_id));
			List<OrderInfoDetails> orderInfoDetailList = super.getFacade().getOrderInfoDetailsService()
					.getOrderInfoDetailsList(orderInfoDetail);
			BigDecimal good_sum_price = new BigDecimal("0");
			if (null != orderInfoDetailList && orderInfoDetailList.size() > 0) {
				for (OrderInfoDetails oid : orderInfoDetailList) {

					if (oid.getOrder_type() == 10) {
						ReturnsInfo entity = new ReturnsInfo();
						entity.setIs_del(0);
						entity.setOrder_info_details_id(Integer.valueOf(oid.getId()));
						entity = super.getFacade().getReturnsInfoService().getReturnsInfo(entity);
						if (null != entity) {
							request.setAttribute("state", true);
							oid.getMap().put("entity", entity);
						}
					}
					good_sum_price = good_sum_price.add(oid.getGood_sum_price());
				}
			}
			request.setAttribute("good_sum_price", good_sum_price);
			request.setAttribute("orderInfoDetailList", orderInfoDetailList);
		}
		return mapping.findForward("view");
	}
}
