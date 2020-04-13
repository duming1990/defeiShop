package com.ebiz.webapp.web.struts.m;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.OrderType;

public class MMyOrderModifyAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		super.getModNameForMobile(request);

		DynaBean dynaBean = (DynaBean) form;

		Pager pager = (Pager) dynaBean.get("pager");
		String st_modify_date = (String) dynaBean.get("st_date");
		String en_modify_date = (String) dynaBean.get("en_date");
		String order_type = (String) dynaBean.get("order_type");
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.setEntp_id(ui.getOwn_entp_id());

		if (StringUtils.isBlank(order_type)) {
			entity.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex());
		}
		entity.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());

		entity.getMap().put("st_modify_date", st_modify_date);
		entity.getMap().put("en_modify_date", en_modify_date);
		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfo> entityList = getFacade().getOrderInfoService().getOrderInfoPaginatedList(entity);
		if ((null != entityList) && (entityList.size() > 0)) {
			for (OrderInfo orderInfo : entityList) {

				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(orderInfo.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

				if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
					for (OrderInfoDetails temp : orderInfoDetailsList) {
						if (null != temp.getComm_id()) {
							temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
						}
					}
				}
			}
		}
		request.setAttribute("entityList", entityList);

		List<OrderType> orderTypeList = new ArrayList<Keys.OrderType>();
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_10.getSonType().toString()));

		request.setAttribute("orderTypeList", orderTypeList);

		return mapping.findForward("list");

	}

	public ActionForward orderModifyPrice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			String msg = "订单不存在！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_0.getIndex()) {
			String msg = "订单状态也改变！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		request.setAttribute("orderInfo", orderInfo);
		return new ActionForward("/../m/MMyOrderModify/orderModifyPrice.jsp");
	}
}