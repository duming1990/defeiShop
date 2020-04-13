package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
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
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;

public class VillageOrderManagerAction extends BaseCustomerAction {
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
		setNaviStringToRequestScope(request);

		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String rel_name_like = (String) dynaBean.get("rel_name_like");
		String rel_phone_like = (String) dynaBean.get("rel_phone_like");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String add_user_mobile_like = (String) dynaBean.get("add_user_mobile_like");
		String order_type = (String) dynaBean.get("order_type");
		String trade_index_like = (String) dynaBean.get("trade_index_like");
		String trade_no_like = (String) dynaBean.get("trade_no_like");
		String trade_merger_index_like = (String) dynaBean.get("trade_merger_index_like");
		String order_has_pay = (String) dynaBean.get("order_has_pay");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String from_type = (String) dynaBean.get("from_type");

		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(order_has_pay)) {
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_50.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
		}

		if (StringUtils.isBlank(from_type)) {
			from_type = "0";
		}

		String order_has_pay_three = (String) dynaBean.get("order_has_pay_three");
		if (StringUtils.isNotBlank(order_has_pay_three)) {// 已支付，第三方支付，不包含余额
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_50.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
			entity.getMap().put("pay_type_in", "1,2,3");
		}

		String st_qrsh_date = (String) dynaBean.get("st_qrsh_date");
		String en_qrsh_date = (String) dynaBean.get("en_qrsh_date");
		entity.getMap().put("st_qrsh_date", st_qrsh_date);
		entity.getMap().put("en_qrsh_date", en_qrsh_date);

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("entp_name_like", entp_name_like);
		entity.getMap().put("comm_name_like", comm_name_like);
		entity.getMap().put("rel_name_like", rel_name_like);
		entity.getMap().put("rel_phone_like", rel_phone_like);
		entity.getMap().put("add_user_name_like", add_user_name_like);
		entity.getMap().put("add_user_mobile_like", add_user_mobile_like);
		if (StringUtils.isNotBlank(trade_index_like)) {
			entity.getMap().put("trade_index_like", trade_index_like.trim());
		}
		if (StringUtils.isNotBlank(trade_no_like)) {
			entity.getMap().put("trade_no_like", trade_no_like.trim());
		}
		if (StringUtils.isNotBlank(trade_merger_index_like)) {
			entity.getMap().put("trade_merger_index_like", trade_merger_index_like.trim());
		}
		if (ui.getIs_village() != null && ui.getIs_village() == 1) {
			entity.setVillage_id(ui.getOwn_village_id());
		}
		entity.setOrder_type(Keys.OrderType.ORDER_TYPE_7.getIndex());

		entity.getMap().put("left_join_user_info", true);

		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		if (null != entity.getOrder_state()
				&& entity.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_90.getIndex()) {
			entity.getMap().put("order_state_ne", null);
		}

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(entity);
		if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
			for (OrderInfo orderInfo : orderInfoList) {

				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(orderInfo.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);
				if (orderInfoDetailsList != null && orderInfoDetailsList.size() > 0) {
					CommInfo comm = super.getCommInfo(orderInfoDetailsList.get(0).getComm_id());
					if (comm != null) {
						UserInfo user = super.getUserInfo(comm.getAdd_user_id());
						if (user != null) {
							orderInfo.getMap().put("maijia_user", user);
						}
					}
				}
			}
		}
		request.setAttribute("entityList", orderInfoList);
		request.setAttribute("orderTypeList", Keys.OrderType.values());
		request.setAttribute("payTypeList", Keys.PayType.values());
		request.setAttribute("orderStateList", Keys.OrderState.values());

		return mapping.findForward("list");

	}

	public ActionForward updateState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String state = (String) dynaBean.get("state");
		String order_password = (String) dynaBean.get("order_password");

		JSONObject data = new JSONObject();
		if (!GenericValidator.isLong(id)) {
			data.put("ret", "0");
			data.put("msg", "参数有误！");
			super.renderJson(response, data.toString());
			return null;
		}

		OrderInfo orderQuery = new OrderInfo();
		orderQuery.setId(Integer.valueOf(id));
		orderQuery = super.getFacade().getOrderInfoService().getOrderInfo(orderQuery);
		if (orderQuery == null) {
			data.put("ret", "0");
			data.put("msg", "订单不存在！");
			super.renderJson(response, data.toString());
			return null;
		}

		if (orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex() && state.equals("20")) {
			if (StringUtils.isBlank(order_password)) {
				data.put("ret", "0");
				data.put("msg", "订单密码为空！");
				super.renderJson(response, data.toString());
				return null;
			}
			if (!(order_password.trim()).equals(orderQuery.getOrder_password())) {
				data.put("ret", "0");
				data.put("msg", "订单密码不正确！");
				super.renderJson(response, data.toString());
				return null;
			}
		}

		OrderInfo entity = new OrderInfo();
		entity.setId(Integer.valueOf(id));
		entity.setOrder_state(Integer.valueOf(state));
		if (StringUtils.equals("40", state)) {
			entity.setQrsh_date(new Date());
		}
		super.getFacade().getOrderInfoService().modifyOrderInfo(entity);
		data.put("ret", "1");
		data.put("msg", "修改订单成功！");
		super.renderJson(response, data.toString());
		return null;
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

		DynaBean dynaBean = (DynaBean) form;
		setNaviStringToRequestScope(request);
		String order_id = (String) dynaBean.get("order_id");

		if (!GenericValidator.isLong(order_id) || StringUtils.isBlank(order_id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		// 订单信息
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);

		// 村信息
		VillageInfo villageInfo = new VillageInfo();
		villageInfo.getMap().put("is_virtual_no_def", true);
		villageInfo.setId(orderInfo.getVillage_id());
		villageInfo = getFacade().getVillageInfoService().getVillageInfo(villageInfo);
		if (villageInfo != null) {
			request.setAttribute("villageInfo", villageInfo);
		}
		// 收货人信息
		super.showShippingAddressForOrderInfo(orderInfo);
		super.copyProperties(form, orderInfo);

		// 产品详细
		OrderInfoDetails orderInfoDetail = new OrderInfoDetails();
		orderInfoDetail.setOrder_id(Integer.valueOf(order_id));
		List<OrderInfoDetails> orderInfoDetailList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsList(orderInfoDetail);
		request.setAttribute("orderInfoDetailList", orderInfoDetailList);

		if (orderInfoDetailList != null && orderInfoDetailList.size() > 0) {
			CommInfo comm = super.getCommInfo(orderInfoDetailList.get(0).getComm_id());
			if (comm != null) {
				UserInfo user = super.getUserInfo(comm.getAdd_user_id());
				if (user != null) {
					request.setAttribute("maijia_user", user);
				}
			}
		}

		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("payTypeList", Keys.PayType.values());

		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(orderInfo.getId());
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		request.setAttribute("wlOrderInfo", wlOrderInfo);

		return mapping.findForward("view");
	}

	public ActionForward saveFh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		String order_id = (String) dynaBean.get("order_id");
		String wl_comp_id = (String) dynaBean.get("wl_comp_id");
		String fahuo_remark = (String) dynaBean.get("fahuo_remark");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(order_id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			msg = "订单不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		if (orderInfo.getOrder_type() != Keys.OrderType.ORDER_TYPE_10.getIndex()) {
			WlOrderInfo wlOrderInfo = new WlOrderInfo();
			super.copyProperties(wlOrderInfo, form);
			wlOrderInfo.setWl_comp_id(Integer.valueOf(wl_comp_id));

			WlCompInfo wlCompInfo = new WlCompInfo();
			wlCompInfo.setIs_del(0);
			wlCompInfo.setId(Integer.valueOf(wl_comp_id));
			wlCompInfo = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
			if (null != wlCompInfo) {
				wlOrderInfo.setWl_comp_name(wlCompInfo.getWl_comp_name());
				wlOrderInfo.setWl_code(wlCompInfo.getWl_code());
			}

			wlOrderInfo.setAdd_date(new Date());
			wlOrderInfo.setAdd_user_id(ui.getId());
			super.getFacade().getWlOrderInfoService().createWlOrderInfo(wlOrderInfo);
		}

		OrderInfo entity = new OrderInfo();
		entity.setId(Integer.valueOf(order_id));
		entity.setOrder_state(Keys.OrderState.ORDER_STATE_20.getIndex());
		entity.setFahuo_remark(fahuo_remark);
		entity.setFahuo_date(new Date());
		super.getFacade().getOrderInfoService().modifyOrderInfo(entity);

		msg = "发货成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward updateStateFh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		if (StringUtils.isBlank(order_id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
		request.setAttribute("wlCompInfoList", wlCompInfoList);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null != orderInfo) {

			WlOrderInfo wlOrderInfo = new WlOrderInfo();
			wlOrderInfo.setOrder_id(Integer.valueOf(order_id));
			wlOrderInfo.setIs_del(0);
			wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
			if (null != wlOrderInfo) {
				String msg = "该订单已经发货，不能再进行发货操作！";
				super.showMsgForManager(request, response, msg);
				return null;
			}
			request.setAttribute("orderInfo", orderInfo);
			// the line below is added for pagination
			dynaBean.set("queryString", super.serialize(request, "id", "method"));
			// end

			return new ActionForward("/../manager/customer/VillageOrderManager/updateStateFh.jsp");
		} else {
			return null;
		}
	}

	public ActionForward updateWlOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		if (StringUtils.isBlank(order_id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			String msg = "订单不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(orderInfo.getId());
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		super.copyProperties(form, wlOrderInfo);

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
		request.setAttribute("wlCompInfoList", wlCompInfoList);

		dynaBean.set("fahuo_remark", orderInfo.getFahuo_remark());

		return new ActionForward("/../manager/admin/OrderQuery/updateWlOrderInfo.jsp");
	}

	public ActionForward saveWlOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		String fahuo_remark = (String) dynaBean.get("fahuo_remark");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(order_id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			msg = "订单不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		WlOrderInfo wlOrderInfoUpdate = new WlOrderInfo();
		super.copyProperties(wlOrderInfoUpdate, form);

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		wlCompInfo.setId(wlOrderInfoUpdate.getWl_comp_id());
		wlCompInfo = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
		if (null == wlCompInfo) {
			msg = "物流方式不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		wlOrderInfoUpdate.setWl_comp_name(wlCompInfo.getWl_comp_name());
		wlOrderInfoUpdate.setWl_code(wlCompInfo.getWl_code());

		orderInfo.setIs_ziti(0);
		if (wlCompInfo.getId().intValue() == 1) {// 1:如果是自提，订单类型为自提订单
			orderInfo.setIs_ziti(1);

		}

		if (StringUtils.isNotBlank(fahuo_remark)) {
			orderInfo.setFahuo_remark(fahuo_remark);
		}

		wlOrderInfoUpdate.getMap().put("update_order_info", orderInfo);
		super.getFacade().getWlOrderInfoService().modifyWlOrderInfo(wlOrderInfoUpdate);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

}
