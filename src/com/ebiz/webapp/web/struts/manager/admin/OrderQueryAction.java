package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.OrderType;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

public class OrderQueryAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.getFacade().getAutoRunService().autoSyncUpdatePtOrderThread(null);
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
		String st_pay_date = (String) dynaBean.get("st_pay_date");
		String en_pay_date = (String) dynaBean.get("en_pay_date");
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
		String fp_state = (String) dynaBean.get("fp_state");
		String today_order = (String) dynaBean.get("today_order");
		String is_price_modify = (String) dynaBean.get("is_price_modify");
		String entp_id = (String) dynaBean.get("entp_id");

		String activity_id = request.getParameter("activity_id");

		String payPlatform = request.getParameter("payPlatform");
		// String entp_id1 = (String) dynaBean.get("entp_id");
		String order_state = request.getParameter("order_state");

		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(fp_state)) {
			entity.setFp_state(Integer.valueOf(fp_state));
		}
		if (StringUtils.isNotBlank(order_state)) {
			entity.setOrder_state(Integer.valueOf(order_state));
		}
		if (StringUtils.isNotBlank(activity_id)) {
			entity.setActivity_id(Integer.valueOf(activity_id));
		}

		if (StringUtils.isNotBlank(order_has_pay)) {
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_40.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_50.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
		}

		if (StringUtils.isBlank(from_type)) {
			from_type = "0";
		}

		String order_has_pay_three = (String) dynaBean.get("order_has_pay_three");
		if (StringUtils.isNotBlank(order_has_pay_three)) {// 已支付，第三方支付，不包含余额
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_40.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_50.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
		}

		String st_qrsh_date = (String) dynaBean.get("st_qrsh_date");
		String en_qrsh_date = (String) dynaBean.get("en_qrsh_date");
		entity.getMap().put("st_qrsh_date", st_qrsh_date);
		entity.getMap().put("en_qrsh_date", en_qrsh_date);

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		entity.getMap().put("st_pay_date", st_pay_date);
		entity.getMap().put("en_pay_date", en_pay_date);

		if (StringUtils.isNotBlank(today_order)) {
			entity.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
			entity.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("st_add_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("en_add_date", sdFormat_ymd.format(new Date()));
		}
		if (StringUtils.isNotBlank(entp_id)) {
			entity.setEntp_id(Integer.valueOf(entp_id));
		}

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

		if (StringUtils.isBlank(order_type)) {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		}

		entity.getMap().put("left_join_user_info", true);

		if (StringUtils.isNotBlank(payPlatform) && StringUtils.isNumeric(payPlatform)) {
			entity.setPay_platform(Integer.parseInt(payPlatform));
		}
		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		if (null != entity.getOrder_state()
				&& entity.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_90.getIndex()) {
			entity.getMap().put("order_state_ne", null);
		}
		// 线下订单：默认查询所有订单状态
		if (null != entity.getOrder_type()
				&& entity.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_60.getIndex()) {// 线下订单：默认查询所有订单状态
			entity.getMap().put("order_state_ne", null);
		}
		// 是否修改过订单金额，is_price_modify默认为null
		if (StringUtils.isNotBlank(is_price_modify) && "0".equals(is_price_modify)) {
			entity.setIs_price_modify(null);
			entity.getMap().put("is_price_modify_is_null", true);
		}

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		if (entity.getOrder_type() != null && entity.getOrder_type() == Keys.OrderType.ORDER_TYPE_100.getIndex()) {// 拼团商品订单
			entity.setIs_leader(1);
			List<OrderInfo> LeaderorderInfoList = getFacade().getOrderInfoService().getOrderInfoList(entity);
			for (OrderInfo leaderOrder : LeaderorderInfoList) {
				// 查主订单明细
				OrderInfoDetails ods1 = new OrderInfoDetails();
				ods1.setOrder_id(leaderOrder.getId());
				List<OrderInfoDetails> ods1List = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(ods1);
				leaderOrder.setOrderInfoDetailsList(ods1List);

				// 查子订单，和明细
				OrderInfo orderInfoQuery = new OrderInfo();
				orderInfoQuery.setLeader_order_id(leaderOrder.getId());
				orderInfoQuery.setIs_leader(0);
				orderInfoQuery.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());

				List<OrderInfo> childOrderInfoList = getFacade().getOrderInfoService().getOrderInfoList(orderInfoQuery);
				for (OrderInfo orderInfo : childOrderInfoList) {
					OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
					orderInfoDetails.setOrder_id(orderInfo.getId());
					List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
							.getOrderInfoDetailsList(orderInfoDetails);
					orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);
				}
				leaderOrder.getMap().put("childOrderInfoList", childOrderInfoList);
			}
			request.setAttribute("entityList", LeaderorderInfoList);
		} else {
			List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(
					entity);
			if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
				for (OrderInfo item : orderInfoList) {
					UserInfo userInfo = super.getUserInfo(item.getAdd_user_id());
					if (null != userInfo) {
						item.getMap().put("user_name", userInfo.getUser_name());
					}

					OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
					orderInfoDetails.setOrder_id(item.getId());
					List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
							.getOrderInfoDetailsList(orderInfoDetails);
					item.setOrderInfoDetailsList(orderInfoDetailsList);

					if (item.getOrder_money().compareTo(new BigDecimal(0)) == 0
							&& item.getNo_dis_money().compareTo(new BigDecimal(0)) == 0) {
						item.getMap().put("huanhuo", "true");
					}
					// 如果是退款的，把退款金额传回
					if (item.getOrder_state() != Keys.OrderState.ORDER_STATE_0.getIndex()) {
						BigDecimal reMoney = refund(item.getId());
						if (reMoney.compareTo(new BigDecimal(0)) == 1) {
							item.getMap().put("reMoney", reMoney);
						}
					}
					// 获取线下活动title
					Activity activity = new Activity();
					if (null != item.getActivity_id()) {
						logger.info("===============jinru");
						activity.setId(item.getActivity_id());
						activity = super.getFacade().getActivityService().getActivity(activity);
						if (null != activity) {
							item.getMap().put("title", activity.getTitle());
						}
					}
				}

			}
			request.setAttribute("entityList", orderInfoList);
		}

		dynaBean.set("entp_id", entp_id);
		dynaBean.set("activity_id", activity_id);

		List<OrderType> orderTypeList = new ArrayList<Keys.OrderType>();
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_10.getSonType().toString()));
		request.setAttribute("orderTypeList", orderTypeList);
		request.setAttribute("payTypeList", Keys.PayType.values());
		request.setAttribute("payPlatformList", Keys.PayPlatform.values());

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

		// 收货人信息
		super.showShippingAddressForOrderInfo(orderInfo);
		super.copyProperties(form, orderInfo);

		// 产品详细
		OrderInfoDetails orderInfoDetail = new OrderInfoDetails();
		orderInfoDetail.setOrder_id(Integer.valueOf(order_id));
		List<OrderInfoDetails> orderInfoDetailList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsList(orderInfoDetail);
		request.setAttribute("orderInfoDetailList", orderInfoDetailList);

		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("payTypeList", Keys.PayType.values());

		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(orderInfo.getId());
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		request.setAttribute("wlOrderInfo", wlOrderInfo);
		dynaBean.set("queryString", super.serialize(request, "id", "method"));

		return mapping.findForward("view");
	}

	public ActionForward removeOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";
		DynaBean dynaBean = (DynaBean) form;
		setNaviStringToRequestScope(request);
		String order_id = (String) dynaBean.get("order_id");
		if (!GenericValidator.isLong(order_id) || StringUtils.isBlank(order_id)) {
			msg = "参数有误，请联系管理员！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		// 订单信息
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null != orderInfo && orderInfo.getOrder_state() == Keys.OrderState.ORDER_STATE_50.getIndex()
				&& orderInfo.getOrder_type() == Keys.OrderType.ORDER_TYPE_60.getIndex()) {
			OrderInfo orderInfoUpdate = new OrderInfo();
			orderInfoUpdate.setId(orderInfo.getId());
			orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
			orderInfoUpdate.getMap().put("delect_aid_money", true);
			int count = super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdate);
			if (count > 0) {
				msg = "操作成功";
				ret = "1";
			}
		} else {
			msg = "订单状态或订单类型不对！";
		}

		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
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
			super.showMsgForManager(request, response, msg);
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

			return new ActionForward("/../manager/admin/OrderQuery/updateStateFh.jsp");
		} else {
			return null;
		}
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String st_pay_date = (String) dynaBean.get("st_pay_date");
		String en_pay_date = (String) dynaBean.get("en_pay_date");
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
		String fp_state = (String) dynaBean.get("fp_state");
		String today_order = (String) dynaBean.get("today_order");
		String code = (String) dynaBean.get("code");
		String is_price_modify = (String) dynaBean.get("is_price_modify");

		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(fp_state)) {
			entity.setFp_state(Integer.valueOf(fp_state));
		}
		if (StringUtils.isNotBlank(order_has_pay)) {
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_40.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_50.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
		}

		if (StringUtils.isBlank(from_type)) {
			from_type = "0";
		}

		String order_has_pay_three = (String) dynaBean.get("order_has_pay_three");
		if (StringUtils.isNotBlank(order_has_pay_three)) {// 已支付，第三方支付，不包含余额
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_40.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_50.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
		}

		String st_qrsh_date = (String) dynaBean.get("st_qrsh_date");
		String en_qrsh_date = (String) dynaBean.get("en_qrsh_date");
		entity.getMap().put("st_qrsh_date", st_qrsh_date);
		entity.getMap().put("en_qrsh_date", en_qrsh_date);

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		entity.getMap().put("st_pay_date", st_pay_date);
		entity.getMap().put("en_pay_date", en_pay_date);

		if (StringUtils.isNotBlank(today_order)) {
			entity.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
			entity.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("st_add_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("en_add_date", sdFormat_ymd.format(new Date()));
		}

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

		if (StringUtils.isBlank(order_type)) {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		}

		entity.getMap().put("left_join_user_info", true);

		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		if (null != entity.getOrder_state()
				&& entity.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_90.getIndex()) {
			entity.getMap().put("order_state_ne", null);
		}
		// 是否修改过订单金额，is_price_modify默认为null
		if (StringUtils.isNotBlank(is_price_modify) && "0".equals(is_price_modify)) {
			entity.setIs_price_modify(null);
			entity.getMap().put("is_price_modify_is_null", true);
		}
		List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNameList(entity);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo oi : orderInfoList) {// 按照商品查询后，只显示匹配商品名称的订单数和订单金额

				super.showShippingAddressForOrderInfo(oi);

				OrderInfoDetails oid = new OrderInfoDetails();
				oid.setOrder_id(oi.getId());
				oid.getMap().put("comm_name_like", comm_name_like);
				List<OrderInfoDetails> oidList = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(oid);
				oi.setOrderInfoDetailsList(oidList);

				// 获取线下活动title
				if (null != oi.getActivity_id()) {
					Activity activity = new Activity();
					logger.info("===============jinru");
					activity.setId(oi.getActivity_id());
					activity = super.getFacade().getActivityService().getActivity(activity);
					if (null != activity) {
						oi.getMap().put("title", activity.getTitle());
					}
				}

			}
		}

		model.put("entityList", orderInfoList);
		model.put("title", "订单导出_日期" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("Order/orderList.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("订单导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward updateOrderAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		super.copyProperties(form, orderInfo);
		return new ActionForward("/../manager/admin/OrderQuery/updateOrderAddress.jsp");
	}

	public ActionForward saveOrderAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");

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

		OrderInfo orderInfoUpdate = new OrderInfo();
		orderInfoUpdate.setId(Integer.valueOf(order_id));
		super.copyProperties(orderInfoUpdate, form);
		super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdate);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

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

	public ActionForward ziti(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("id");

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
		EntpInfo entpInfo = super.getEntpInfo(orderInfo.getEntp_id());
		if (null != entpInfo) {
			if (null != orderInfo.getOrder_password()) {
				StringBuffer message = new StringBuffer("{\"code\":\"" + orderInfo.getOrder_password() + "\",");
				message.append("\"shop\":\""
						+ (entpInfo.getEntp_name().length() > 20 ? entpInfo.getEntp_name().substring(0, 20) : entpInfo
								.getEntp_name()) + "\",");
				message.append("\"order\":\"" + orderInfo.getTrade_index() + "\",");
				message.append("\"address\":\""
						+ (entpInfo.getEntp_addr().length() > 20 ? entpInfo.getEntp_addr().substring(0, 20) : entpInfo
								.getEntp_addr()) + "\"");
				message.append("}");
				DySmsUtils.sendMessage(message.toString(), orderInfo.getRel_phone(), SmsTemplate.sms_04_02);
				logger.info("====orderInfo.getOrder_password()=======" + orderInfo.getOrder_password() + ","
						+ orderInfo.getRel_phone());
			}
		}
		msg = "发送成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	private BigDecimal refund(Integer id) {
		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setOrder_id(id);
		entity.setIs_del(0);
		entity.setAudit_state(1);
		entity = super.getFacade().getOrderReturnInfoService().getRefund(entity);
		BigDecimal money = new BigDecimal(0);
		if (null != entity) {
			money = (BigDecimal) entity.getMap().get("sum_price");
		}
		return money;
	}

	public ActionForward orderAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("id");
		String audit_state = (String) dynaBean.get("audit_state");
		String audit_desc = (String) dynaBean.get("audit_desc");
		String order_type = (String) dynaBean.get("order_type");
		String queryString = (String) dynaBean.get("queryString");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "请先登陆";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		if (!GenericValidator.isInt(order_id) || !GenericValidator.isInt(audit_state)) {
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

		orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo.setAudit_state(Integer.valueOf(audit_state));
		orderInfo.setAudit_user_id(ui.getId());
		orderInfo.setAudit_date(new Date());
		orderInfo.setAudit_desc(audit_desc);
		if ("1".equals(audit_state)) {// 审核通过
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		} else {
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
		}
		orderInfo.getMap().put("opt_order_type", Keys.OrderType.ORDER_TYPE_60.getIndex());
		super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);

		msg = "审核成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		data.put("order_type", order_type);
		data.put("queryString", super.encodeSerializedQueryString(queryString));
		super.renderJson(response, data.toJSONString());
		return null;

	}
}
