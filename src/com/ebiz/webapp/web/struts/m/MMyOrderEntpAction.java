package com.ebiz.webapp.web.struts.m;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.OrderState;
import com.ebiz.webapp.web.Keys.OrderType;
import com.ebiz.webapp.web.util.DateTools;

public class MMyOrderEntpAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "店铺订单");
		return new ActionForward("/../m/MMyOrderEntp/index.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("===进入list====");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		logger.info("==order_type:" + order_type);

		Pager pager = (Pager) dynaBean.get("pager");
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.setEntp_id(ui.getOwn_entp_id());

		entity.getMap().put("st_add_date", st_date);
		entity.getMap().put("en_add_date", en_date);
		entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(trade_index)) {
			entity.setTrade_index(trade_index.trim());
		}

		if (StringUtils.isNotBlank(order_type)) {
			entity.setOrder_type(Integer.valueOf(order_type));
			String order_type_name = "";
			for (OrderType temp : Keys.OrderType.values()) {
				if (Integer.valueOf(order_type) == temp.getIndex()) {
					order_type_name = temp.getName();
				}
			}
			logger.info("==order_type_name:" + order_type_name);
			request.setAttribute("header_title", order_type_name);
		} else {
			request.setAttribute("header_title", "店铺订单");
		}

		// if (StringUtils.isBlank(order_type)
		// || order_type.equals(String.valueOf(Keys.OrderType.ORDER_TYPE_10.getIndex()))) {
		// entity.getMap().put("order_type_in",
		// Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_11.getIndex());
		// entity.setOrder_type(null);
		// }

		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());

		String rel_name_like = (String) dynaBean.get("rel_name_like");
		String rel_phone_like = (String) dynaBean.get("rel_phone_like");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String add_user_mobile_like = (String) dynaBean.get("add_user_mobile_like");
		entity.getMap().put("rel_name_like", rel_name_like);
		entity.getMap().put("rel_phone_like", rel_phone_like);
		entity.getMap().put("add_user_name_like", add_user_name_like);
		entity.getMap().put("add_user_mobile_like", add_user_mobile_like);

		entity.getMap().put("left_join_user_info", true);
		entity.getMap().put("left_join_order_info_query_entp_blance", true);

		Integer pageSize = 10;

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
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

				if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
					for (OrderInfoDetails temp : orderInfoDetailsList) {
						if (null != temp.getComm_id()) {
							temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
						}
					}
				}
			}
		}

		if (orderInfoList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);

		request.setAttribute("entityList", orderInfoList);

		List<OrderType> orderTypeList = new ArrayList<Keys.OrderType>();
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_10.getSonType().toString()));

		request.setAttribute("orderTypeShowList", Keys.OrderType.values());

		request.setAttribute("orderTypeList", orderTypeList);
		request.setAttribute("payTypeList", Keys.PayType.values());
		request.setAttribute("orderStateList", Keys.OrderState.values());

		return mapping.findForward("list");

	}

	public ActionForward updateState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String state = (String) dynaBean.get("state");
		String fahuo_remark = (String) dynaBean.get("fahuo_remark");
		String wl_order_id = (String) dynaBean.get("wl_order_id");

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

		if (orderQuery.getIs_opt() == 1) {
			data.put("ret", "0");
			data.put("msg", "订单正在操作中，请稍后......！");
			super.renderJson(response, data.toString());
			return null;
		}
		updateOrderInfoOpt(1, orderQuery.getId());
		OrderInfo entity = new OrderInfo();
		entity.setId(Integer.valueOf(id));
		entity.setOrder_state(Integer.valueOf(state));

		if (StringUtils.equals("40", state)) {
			entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());// 商家点击确认订单，现在应该将订单状态改成50，直接给推广奖励
			entity.setQrsh_date(new Date());
		}

		if (orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()) {
			entity.getMap().put("xiaofei_success_update_link_table", "true");
		}

		String msg = "操作失败或您已操作过";
		String ret = "0";

		if ("40".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
			msg = "确认订单成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());// 确认消费，前一个状态：10
		}
		if ("20".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
			msg = "发货成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());// 确认消费，前一个状态：10

			if (StringUtils.isNotBlank(fahuo_remark)) {
				entity.setFahuo_remark(fahuo_remark);
			}
			entity.setFahuo_date(new Date());

			if (orderQuery.getOrder_type() != Keys.OrderType.ORDER_TYPE_10.getIndex()) {
				// 发货操作
				WlOrderInfo wlOrderInfo = new WlOrderInfo();
				super.copyProperties(wlOrderInfo, form);
				wlOrderInfo.setId(null);

				WlCompInfo wlCompInfo = new WlCompInfo();
				wlCompInfo.setIs_del(0);
				wlCompInfo.setId(wlOrderInfo.getWl_comp_id());
				wlCompInfo = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
				if (null == wlCompInfo) {
					data.put("ret", "0");
					data.put("msg", "物流方式不存在");
					super.renderJson(response, data.toString());
					return null;
				}
				wlOrderInfo.setWl_comp_name(wlCompInfo.getWl_comp_name());
				wlOrderInfo.setWl_code(wlCompInfo.getWl_code());
				wlOrderInfo.setOrder_id(Integer.valueOf(id));
				wlOrderInfo.setAdd_date(new Date());
				wlOrderInfo.setAdd_user_id(ui.getId());

				if (GenericValidator.isLong(wl_order_id)) {
					wlOrderInfo.setId(Integer.valueOf(wl_order_id));
					entity.getMap().put("update_wlOrderInfo_info", wlOrderInfo);
				} else {
					entity.getMap().put("add_wlOrderInfo_info", wlOrderInfo);
				}
			}

		}

		if (ret.equals("1")) {
			int row = super.getFacade().getOrderInfoService().modifyOrderInfo(entity);

			updateOrderInfoOpt(0, orderQuery.getId());

			if (row == 0) {
				msg = "操作失败或您已操作过";
				ret = "0";
			}
			if (row == -1) {
				msg = "系统繁忙，请稍后重试";
				ret = "0";
			}
		}
		data.put("ret", ret);
		data.put("msg", msg);
		log.info("===data:" + data.toString());
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getOrderListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.setEntp_id(ui.getOwn_entp_id());

		entity.getMap().put("st_add_date", st_date);
		entity.getMap().put("en_add_date", en_date);
		entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(trade_index)) {
			entity.setTrade_index(trade_index.trim());
		}
		if (StringUtils.isBlank(order_type)
				|| order_type.equals(String.valueOf(Keys.OrderType.ORDER_TYPE_10.getIndex()))) {
			entity.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex());
			entity.setOrder_type(null);
		}
		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		// String ctx = super.getCtxPath(request);
		if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
			for (OrderInfo oi : orderInfoList) {
				JSONObject map = new JSONObject();
				map.put("id", oi.getId());
				map.put("trade_index", oi.getTrade_index());
				map.put("order_state", oi.getOrder_state());
				map.put("pay_type", oi.getPay_type());
				for (OrderState os : Keys.OrderState.values()) {
					if (os.getIndex() == oi.getOrder_state()) {
						map.put("order_state_name", os.getName());
						break;
					}
				}
				map.put("order_date", DateTools.getStringDate(oi.getOrder_date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("order_money", dfFormat.format(oi.getOrder_money()));
				map.put("order_type", oi.getOrder_type());
				map.put("entp_user_balance", oi.getEntp_user_balance());

				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(oi.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
					for (OrderInfoDetails temp : orderInfoDetailsList) {
						if (null != temp.getComm_id()) {
							temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
						}
					}
					map.put("detailSize", orderInfoDetailsList.size());
				}

				map.put("orderInfoDetailsList", orderInfoDetailsList);

				int is_shixiao = 0;// 未失效
				if (null != oi.getEnd_date()) {// 判断订单是否失效
					if (oi.getEnd_date().before(new Date())) {
						is_shixiao = 1;// 已失效
					}
				}
				map.put("is_shixiao", is_shixiao);
				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (orderInfoList.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		super.renderJson(response, datas.toString());

		return null;

	}

	public ActionForward orderFh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		if (orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_10.getIndex()) {
			String msg = "订单状态也改变！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(Integer.valueOf(order_id));
		wlOrderInfo.setIs_del(0);
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		if (null != wlOrderInfo) {
			super.copyProperties(form, wlOrderInfo);
			dynaBean.set("wl_order_id", wlOrderInfo.getId());
			dynaBean.set("fahuo_remark", orderInfo.getFahuo_remark());
		}

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
		request.setAttribute("wlCompInfoList", wlCompInfoList);

		request.setAttribute("orderInfo", orderInfo);
		return new ActionForward("/../m/MMyOrderEntp/orderFh.jsp");
	}

	public ActionForward orderConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		if (orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_10.getIndex()) {
			String msg = "订单状态也改变！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		request.setAttribute("orderInfo", orderInfo);
		return new ActionForward("/../m/MMyOrderEntp/orderConfirm.jsp");
	}

}