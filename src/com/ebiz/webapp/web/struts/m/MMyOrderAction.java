package com.ebiz.webapp.web.struts.m;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.OrderState;
import com.ebiz.webapp.web.util.DateTools;

public class MMyOrderAction extends MBaseWebAction {
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
		request.setAttribute("header_title", "我的订单");
		DynaBean dynaBean = (DynaBean) form;
		String par_id = (String) dynaBean.get("par_id");
		if (StringUtils.isNotBlank(par_id)) {
			return new ActionForward("/../m/MMyOrder/index.jsp");
		} else {
			return list(mapping, form, request, response);
		}

	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "我的订单");
		DynaBean dynaBean = (DynaBean) form;

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		String is_comment = (String) dynaBean.get("is_comment");
		Pager pager = (Pager) dynaBean.get("pager");
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		if (entity.getOrder_state() != null && entity.getOrder_state() == Keys.OrderState.ORDER_STATE_50.getIndex()) {// 已完成
			entity.setOrder_state(null);
			entity.getMap().put("order_state_in",
					Keys.OrderState.ORDER_STATE_40.getIndex() + "," + Keys.OrderState.ORDER_STATE_50.getIndex());
			entity.setIs_comment(1);
		}

		entity.setAdd_user_id(ui.getId());
		entity.getMap().put("st_add_date", st_date);
		entity.getMap().put("en_add_date", en_date);
		entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(trade_index)) {
			entity.setTrade_index(trade_index.trim());
		}
		if (StringUtils.isNotBlank(is_comment)) {
			entity.setIs_comment(Integer.valueOf(is_comment));
		}
		entity.getMap().put(
				"order_type_in",
				Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_7.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_30.getIndex() + "," + Keys.OrderType.ORDER_TYPE_70.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_80.getIndex() + "," + Keys.OrderType.ORDER_TYPE_100.getIndex());
		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		Integer pageSize = 10;
		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		logger.info("=============");
		List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(entity);
		if (orderInfoList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);
		if ((orderInfoList.size() > 0)) {
			for (OrderInfo oi : orderInfoList) {
				WlOrderInfo wlOrderInfo = new WlOrderInfo();
				wlOrderInfo.setOrder_id(oi.getId());
				wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
				if (wlOrderInfo != null) {
					oi.getMap().put("wlOrderInfo", wlOrderInfo);
				}

				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(oi.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				oi.setOrderInfoDetailsList(orderInfoDetailsList);

				if (oi.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()
						|| oi.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()) {
					if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
						for (OrderInfoDetails temp : orderInfoDetailsList) {
							if (null != temp.getComm_id()) {
								temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
							}
						}
					}
				}
			}
		}

		request.setAttribute("entityList", orderInfoList);

		// 订单类型数量
		request.setAttribute(
				"dai_fukuan_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_0.getIndex(), ui.getId(),
						StringUtils.isBlank(order_type) ? null : Integer.valueOf(order_type), null));

		request.setAttribute(
				"dai_fukuan_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_0.getIndex(), ui.getId(),
						StringUtils.isBlank(order_type) ? null : Integer.valueOf(order_type), null));
		request.setAttribute(
				"dai_fahuo_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_10.getIndex(), ui.getId(),
						StringUtils.isBlank(order_type) ? null : Integer.valueOf(order_type), null));
		request.setAttribute(
				"dai_shouhuo_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_20.getIndex(), ui.getId(),
						StringUtils.isBlank(order_type) ? null : Integer.valueOf(order_type), null));
		request.setAttribute(
				"dai_pingjia_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_40.getIndex(), ui.getId(),
						StringUtils.isBlank(order_type) ? null : Integer.valueOf(order_type), null, 0));
		request.setAttribute(
				"wancheng_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_50.getIndex(), ui.getId(),
						StringUtils.isBlank(order_type) ? null : Integer.valueOf(order_type), null));

		request.setAttribute("orderTypeList", Keys.OrderType.values());
		request.setAttribute("payTypeList", Keys.PayType.values());
		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("order_tuikuan_rate", Keys.ORDER_TUIKUAN_RATE);
		request.setAttribute("not_show", true);
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
			return showTipNotLogin(mapping, form, request, response, msg);
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
			entity.getMap().put("update_yhq", true);
			if (orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()) {
				entity.getMap().put("cancel_jd_order_update_info", true);
			}
			if (orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_100.getIndex()
					&& orderQuery.getIs_leader().intValue() == 0) {
				entity.getMap().put("updata_group_number", true);
			}
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
		if ("90".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_X10.getIndex()) {
			msg = "删除订单成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_X10.getIndex());// 取消订单，前一个状态0
		}
		if ("15".equals(state)
				&& (orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex() || orderQuery
						.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_20.getIndex())) {
			msg = "退款/换货申请中！";// 如果待发货或者已经发货了，但是没有确认之前，可以申请退款
			ret = "1";
			entity.getMap().put("opt_order_state", orderQuery.getOrder_state().intValue());// 申请退款，前一个状态：10
		}
		if ("-20".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_15.getIndex()) {
			msg = "退款成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());// 退款，前一个状态：10
		}
		if ("40".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_20.getIndex()) {
			entity.getMap().put("xiaofei_success_update_link_table", "true");
			msg = "确认收货成功！";
			ret = "1";
			entity.setQrsh_date(new Date());
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
			if (row == -2) {
				msg = "取消京东订单失败，请联系管理员";
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
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String order_type = (String) dynaBean.get("order_type");
		String order_state = (String) dynaBean.get("order_state");
		String mod_id = (String) dynaBean.get("mod_id");
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
		pathBuffer.append("&order_type=" + order_type);
		pathBuffer.append("&order_state=" + order_state);
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward getOrderListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		// getsonSysModuleList(request, dynaBean);

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

		entity.setAdd_user_id(ui.getId());
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
				map.put("money_bi", dfFormat.format(oi.getMoney_bi()));
				map.put("order_type", oi.getOrder_type());
				map.put("xiadan_user_sum", oi.getXiadan_user_sum());

				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(oi.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				if (oi.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()
						|| oi.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()) {
					if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
						for (OrderInfoDetails temp : orderInfoDetailsList) {
							if (null != temp.getComm_id()) {
								temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
							}
						}
					}
				}

				map.put("detailSize", orderInfoDetailsList.size());
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

	public ActionForward getOrderReturnInfoCount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		String msg = "";
		String ret = "0";

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");

		JSONObject data = new JSONObject();

		if (!GenericValidator.isLong(order_id)) {
			data.put("ret", ret);
			data.put("msg", "参数有误！");
			super.renderJson(response, data.toString());
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (orderInfo == null) {
			data.put("ret", ret);
			data.put("msg", "订单不存在！");
			super.renderJson(response, data.toString());
			return null;
		}
		OrderReturnInfo returnInfo = new OrderReturnInfo();
		returnInfo.setOrder_id(orderInfo.getId());
		returnInfo.setIs_del(0);
		int returnInfoCount = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(returnInfo);
		ret = "1";
		data.put("returnInfoCount", returnInfoCount);
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
			msg = "延迟收货成功！";
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

	public ActionForward DefaultComment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(id)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		if (null == ui) {
			msg = "用户不存在！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(id));
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			msg = "订单不存在！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(orderInfo.getId());
		List<OrderInfoDetails> list = getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsList(orderInfoDetails);
		if (null != list && list.size() > 0) {
			List<CommentInfo> commentInfoList = new ArrayList<CommentInfo>();
			CommentInfo entity = null;
			for (OrderInfoDetails cur : list) {
				entity = new CommentInfo();
				if (orderInfo.getOrder_type().equals(Keys.OrderType.ORDER_TYPE_10.getIndex())) {
					entity.setComm_type(Keys.CommentType.COMMENT_TYPE_10.getIndex());
				}
				entity.setLink_id(cur.getComm_id());
				entity.setOrder_id(orderInfo.getId());
				entity.setComm_tczh_id(cur.getComm_tczh_id());
				entity.setComm_ip(this.getIpAddr(request));
				entity.setComm_time(new Date());
				entity.setComm_state(1);// 发布
				entity.setLink_f_id(orderInfo.getEntp_id());
				entity.setComm_type(orderInfo.getOrder_type());
				entity.setHas_pic(0);
				entity.setOrder_value(0);
				entity.setComm_uid(ui.getId());
				entity.setComm_uname(ui.getUser_name());
				entity.setOrder_details_id(cur.getId());
				entity.setEntp_id(orderInfo.getEntp_id());
				entity.setComm_experience(Keys.DEFAULT_COMMENT);
				entity.setComm_level(1);
				entity.setComm_score(5);
				commentInfoList.add(entity);
			}
			CommentInfo insert = new CommentInfo();
			insert.setCommentInfoList(commentInfoList);
			int i = getFacade().getCommentInfoService().createCommentInfo(insert);
			if (i > 0) {
				ret = "1";
				msg = "系统默认好评！";
			}
		}
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

}