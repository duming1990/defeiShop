package com.ebiz.webapp.web.struts.manager.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.OrderType;

/**
 * @author Liyuan
 * @version 2013-04-02
 */
public class MyOrderAction extends BaseCustomerAction {

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
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String trade_index = (String) dynaBean.get("trade_index");
		Pager pager = (Pager) dynaBean.get("pager");
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.setEntp_id(ui.getOwn_entp_id());
		// entity.setAdd_user_id(ui.getId());
		entity.getMap().put("st_add_date", st_date);
		entity.getMap().put("en_add_date", en_date);
		entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(trade_index)) {
			entity.setTrade_index(trade_index.trim());
		}
		entity.getMap().put(
				"order_type_in",
				Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_7.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_90.getIndex() + "," + Keys.OrderType.ORDER_TYPE_100.getIndex());

		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(entity);
		if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
			for (OrderInfo oi : orderInfoList) {
				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(oi.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
					OrderReturnInfo orderReturnInfo = null;
					for (OrderInfoDetails temp : orderInfoDetailsList) {
						orderReturnInfo = new OrderReturnInfo();
						orderReturnInfo.setOrder_detail_id(temp.getId());
						orderReturnInfo = getFacade().getOrderReturnInfoService().getOrderReturnInfo(orderReturnInfo);
						if (orderReturnInfo != null) {
							temp.getMap().put("order_return_id", orderReturnInfo.getId());
						}
					}
				}

				// 获取线下活动title
				Activity activity = new Activity();
				if (null != oi.getActivity_id()) {
					activity.setId(oi.getActivity_id());
					activity = super.getFacade().getActivityService().getActivity(activity);
					if (null != activity) {
						oi.getMap().put("title", activity.getTitle());
					}
				}

				oi.setOrderInfoDetailsList(orderInfoDetailsList);
				int is_shixiao = 0;// 未失效
				if (null != oi.getEnd_date()) {// 判断订单是否失效
					if (oi.getEnd_date().before(new Date())) {
						is_shixiao = 1;// 已失效
					}
					JSONObject json = this.getShiXiaoTxt(oi.getEnd_date());
					oi.getMap().put("day_tip", json.getString("day_tip"));
					oi.getMap().put("day_tip_min", json.getString("day_tip_min"));
				}
				oi.setIs_shixiao(is_shixiao);

				if (oi.getOrder_state().intValue() >= Keys.OrderState.ORDER_STATE_40.getIndex()
						&& null != oi.getQrsh_date()) {// 判断这个是否已经分红过
					JSONObject json = this.getOrderGiveMoneyTxt(oi.getQrsh_date());
					oi.getMap().put("day_tip_give_money", json.getString("day_tip"));
				}
			}
		}

		request.setAttribute("entityList", orderInfoList);

		List<OrderType> orderTypeList = new ArrayList<Keys.OrderType>();
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_10.getSonType().toString()));
		request.setAttribute("orderTypeList", orderTypeList);

		request.setAttribute("orderTypeShowList", Keys.OrderType.values());

		request.setAttribute("payTypeList", Keys.PayType.values());
		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("order_tuikuan_rate", Keys.ORDER_TUIKUAN_RATE);

		return mapping.findForward("list");

	}

	public JSONObject getShiXiaoTxt(Date date) {
		JSONObject json = new JSONObject();
		String day_tip_min = "";
		String day_tip = "";
		String day = DurationFormatUtils.formatDuration(date.getTime() - new Date().getTime(), "d");
		String hour = DurationFormatUtils.formatDuration(date.getTime() - new Date().getTime(), "H");
		String minutes = DurationFormatUtils.formatDuration(date.getTime() - new Date().getTime(), "m");
		if (GenericValidator.isInt(hour)) {
			int h = Integer.valueOf(hour);
			if (h <= 0) {
				day_tip_min = minutes + "分失效";
				day_tip = minutes + "分钟后失效";
			}
			if (h > 0 && h < 24) {
				day_tip_min = h + "小时失效";
				day_tip = h + "小时后失效";
			}
			if (h > 24) {
				day_tip_min = day + "天失效";
				day_tip = day + "天后失效";
			}
		}
		json.put("day_tip", day_tip);
		json.put("day_tip_min", day_tip_min);
		return json;
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

		if (StringUtils.equals("-10", state)) {
			entity.getMap().put("update_comm_info_saleCountAndInventory", true);
			if (orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()) {
				entity.getMap().put("cancel_jd_order_update_info", true);
			}
			entity.getMap().put("update_yhq", true);
		}
		if (StringUtils.equals("-20", state)) {
			entity.getMap().put("tuikuan_update_link_table", true);
		}
		if (StringUtils.equals("15", state)) {
			entity.getMap().put("insert_order_return_info", true);
		}

		String msg = "操作失败或您已操作过";
		String ret = "0";
		if ("-10".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_0.getIndex()) {
			msg = "取消订单成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_0.getIndex());// 取消订单，前一个状态0
		}
		if ("15".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
			msg = "退款/换货申请中！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());// 申请退款，前一个状态：10
		}
		if ("-20".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_15.getIndex()) {
			msg = "退款成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_15.getIndex());// 确认退款，前一个状态：15
		}
		if ("40".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_20.getIndex()) {
			if ((orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex())) {
				entity.getMap().put("xiaofei_success_update_link_table", "true");
			}
			msg = "确认收货成功！";
			ret = "1";
			entity.setQrsh_date(new Date());
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_20.getIndex());// 确认收货，前一个状态：10
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
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward delayShouhuo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

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

		OrderInfo entity = new OrderInfo();
		entity.setId(Integer.valueOf(id));

		String msg = "操作失败或您已操作过";
		String ret = "0";
		if (orderQuery.getDelay_shouhuo() == null || orderQuery.getDelay_shouhuo() == 0) {
			msg = "延迟退货成功！";
			ret = "1";
			entity.setDelay_shouhuo(new Integer(1));
			entity.getMap().put("delay_shouhuo", true);// 取消订单，前一个状态0
		}

		if (ret.equals("1")) {
			int row = super.getFacade().getOrderInfoService().modifyOrderInfo(entity);
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
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(Integer.valueOf(id));
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
			getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}
}
