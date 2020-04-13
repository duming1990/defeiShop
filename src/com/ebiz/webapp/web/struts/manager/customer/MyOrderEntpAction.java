package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.OrderType;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.WeiXinSendMessageUtils;

/**
 * @author Liyuan
 * @version 2013-04-02
 */
public class MyOrderEntpAction extends BaseCustomerAction {

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

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entpId", ui.getOwn_entp_id());
		super.getFacade().getAutoRunService().autoSyncUpdatePtOrderThread(map);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		String queryToday = (String) dynaBean.get("queryToday");
		String order_has_pay = (String) dynaBean.get("order_has_pay");
		String fp_state = (String) dynaBean.get("fp_state");
		Pager pager = (Pager) dynaBean.get("pager");
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.setEntp_id(ui.getOwn_entp_id());

		entity.getMap().put("st_add_date", st_date);
		entity.getMap().put("en_add_date", en_date);
		entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(queryToday)) {
			entity.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
			entity.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("st_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("en_date", sdFormat_ymd.format(new Date()));
		}

		if (StringUtils.isNotBlank(order_has_pay)) {
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_40.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
		}

		if (StringUtils.isNotBlank(trade_index)) {
			entity.setTrade_index(trade_index.trim());
		}
		if (StringUtils.isNotBlank(fp_state)) {
			entity.setFp_state(Integer.valueOf(fp_state));
		}
		if (ui.getOwn_entp_id() != null && ui.getOwn_entp_id() == 0) {// 京东自营订单
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());
		} else if (StringUtils.isBlank(order_type)
				|| order_type.equals(String.valueOf(Keys.OrderType.ORDER_TYPE_10.getIndex()))) {
			entity.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex() + "");
			entity.setOrder_type(null);
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

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		if (entity.getOrder_type() != null && entity.getOrder_type() == Keys.OrderType.ORDER_TYPE_100.getIndex()) {// 拼团商品订单
			entity.setIs_leader(1);
			// entity.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
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
				orderInfoQuery.getMap().put("order_state_not_in", "0,-10");
				// orderInfoQuery.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());

				List<OrderInfo> childOrderInfoList = getFacade().getOrderInfoService().getOrderInfoList(orderInfoQuery);
				for (OrderInfo orderInfo : childOrderInfoList) {
					OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
					orderInfoDetails.setOrder_id(orderInfo.getId());
					List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
							.getOrderInfoDetailsList(orderInfoDetails);
					orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);
					// 获取线下活动title
					Activity activity = new Activity();
					if (null != orderInfo.getActivity_id()) {
						activity.setId(orderInfo.getActivity_id());
						activity = super.getFacade().getActivityService().getActivity(activity);
						if (null != activity) {
							orderInfo.getMap().put("title", activity.getTitle());
						}
					}
				}
				leaderOrder.getMap().put("childOrderInfoList", childOrderInfoList);
				leaderOrder.getMap().put("orderCount", childOrderInfoList.size() + 1);
				// 获取线下活动title
				Activity activity = new Activity();
				if (null != leaderOrder.getActivity_id()) {
					activity.setId(leaderOrder.getActivity_id());
					activity = super.getFacade().getActivityService().getActivity(activity);
					if (null != activity) {
						leaderOrder.getMap().put("title", activity.getTitle());
					}
				}
			}

			request.setAttribute("entityList", LeaderorderInfoList);
		} else {
			List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(
					entity);
			if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
				for (OrderInfo orderInfo : orderInfoList) {

					OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
					orderInfoDetails.setOrder_id(orderInfo.getId());
					List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
							.getOrderInfoDetailsList(orderInfoDetails);
					orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

					if (orderInfo.getOrder_money().compareTo(new BigDecimal(0)) == 0
							&& orderInfo.getNo_dis_money().compareTo(new BigDecimal(0)) == 0) {
						orderInfo.getMap().put("huanhuo", "true");
					}
				}
			}

			request.setAttribute("entityList", orderInfoList);
		}

		List<OrderType> orderTypeList = new ArrayList<Keys.OrderType>();
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_10.getSonType().toString()));
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_60.getSonType().toString()));
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_70.getSonType().toString()));
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_80.getSonType().toString()));
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_90.getSonType().toString()));
		orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_100.getSonType().toString()));
		if (ui.getIs_village().intValue() == 1) {
			orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_7.getSonType().toString()));
		}
		request.setAttribute("orderTypeList", orderTypeList);

		request.setAttribute("payTypeList", Keys.PayType.values());
		request.setAttribute("orderStateList", Keys.OrderState.values());

		return mapping.findForward("list");

	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();
		String code = (String) dynaBean.get("code");

		getsonSysModuleList(request, dynaBean);

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		String queryToday = (String) dynaBean.get("queryToday");
		String order_has_pay = (String) dynaBean.get("order_has_pay");
		String fp_state = (String) dynaBean.get("fp_state");
		Pager pager = (Pager) dynaBean.get("pager");
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.setEntp_id(ui.getOwn_entp_id());

		entity.getMap().put("st_add_date", st_date);
		entity.getMap().put("en_add_date", en_date);
		entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(queryToday)) {
			entity.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
			entity.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("st_date", sdFormat_ymd.format(new Date()));
			dynaBean.set("en_date", sdFormat_ymd.format(new Date()));
		}

		if (StringUtils.isNotBlank(order_has_pay)) {
			String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
					+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_40.getIndex();
			entity.getMap().put("order_state_in", order_state_in);
		}

		if (StringUtils.isNotBlank(trade_index)) {
			entity.setTrade_index(trade_index.trim());
		}
		if (StringUtils.isNotBlank(fp_state)) {
			entity.setFp_state(Integer.valueOf(fp_state));
		}
		if (ui.getOwn_entp_id() != null && ui.getOwn_entp_id() == 0) {// 京东自营订单
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());
		} else if (StringUtils.isBlank(order_type)
				|| order_type.equals(String.valueOf(Keys.OrderType.ORDER_TYPE_10.getIndex()))) {
			entity.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex() + "");
			entity.setOrder_type(null);
		}

		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		if (null != entity.getOrder_state()
				&& entity.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_90.getIndex()) {
			entity.getMap().put("order_state_ne", null);
		}
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
		List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(entity);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo oi : orderInfoList) {// 按照商品查询后，只显示匹配商品名称的订单数和订单金额
				super.showShippingAddressForOrderInfo(oi);
				OrderInfoDetails oid = new OrderInfoDetails();
				oid.setOrder_id(oi.getId());
				oid.getMap().put("comm_name_like", comm_name_like);
				List<OrderInfoDetails> oidList = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(oid);
				oi.setOrderInfoDetailsList(oidList);

				// 获取线下活动title
				Activity activity = new Activity();
				if (null != oi.getActivity_id()) {
					activity.setId(oi.getActivity_id());
					activity = super.getFacade().getActivityService().getActivity(activity);
					if (null != activity) {
						oi.getMap().put("title", activity.getTitle());
					} else {
						oi.getMap().put("title", "未参加活动！");
					}
				} else {
					oi.getMap().put("title", "未参加活动！");
				}
			}
		}

		model.put("entityList", orderInfoList);
		model.put("title", "商家订单导出_日期" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("Order/orderList.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("商家订单导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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
		String fahuo_remark = (String) dynaBean.get("fahuo_remark");
		String wl_order_id = (String) dynaBean.get("wl_order_id");
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

		if ((orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()) && state.equals("40")) {
			entity.getMap().put("xiaofei_success_update_link_table", "true");
		}

		String msg = "操作失败或您已操作过";
		String ret = "0";

		if ("40".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {

			entity.setQrsh_date(new Date());

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

			if (orderQuery.getOrder_type() == Keys.OrderType.ORDER_TYPE_10.getIndex()
					|| orderQuery.getOrder_type() == Keys.OrderType.ORDER_TYPE_7.getIndex()
					|| orderQuery.getOrder_type() == Keys.OrderType.ORDER_TYPE_100.getIndex()) {
				// 发货操作
				WlOrderInfo wlOrderInfo = new WlOrderInfo();
				super.copyProperties(wlOrderInfo, form);
				wlOrderInfo.setId(null);
				wlOrderInfo.setEntp_id(orderQuery.getEntp_id());

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
			int flag = super.getFacade().getOrderInfoService().modifyOrderInfo(entity);
			updateOrderInfoOpt(0, orderQuery.getId());

			if (flag == -1) {
				msg = "系统繁忙，请稍后重试";
				ret = "0";
				super.renderJson(response, data.toString());
				return null;
			}

			if ("20".equals(state)
					&& orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {// 发送微信消息

				WlOrderInfo wlOrderInfo = new WlOrderInfo();
				wlOrderInfo.setOrder_id(orderQuery.getId());
				wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
				if (null != wlOrderInfo) {
					UserInfo userInfo = super.getUserInfo(orderQuery.getAdd_user_id());
					WeiXinSendMessageUtils.ordersShipment(orderQuery, userInfo, wlOrderInfo);
				}
			}

			// 订单完成站内信
			if ("40".equals(state)
					&& orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
				// 给用户发站内信
				UserInfo uireal = super.getUserInfo(orderQuery.getAdd_user_id());
				String msg_content = StringUtils.replace(SMS.sms_06, "{0}", uireal.getUser_name());
				msg_content = StringUtils.replace(msg_content, "{1}", orderQuery.getTrade_index());
				super.sendMsg(1, uireal.getId(), "认证会员订单完成", msg_content);
			}
		}
		data.put("ret", ret);
		data.put("msg", msg);
		log.info("==data:" + data.toString());
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward updateFp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");

		JSONObject data = new JSONObject();

		if (!GenericValidator.isLong(order_id)) {
			data.put("ret", "0");
			data.put("msg", "参数有误！");
			super.renderJson(response, data.toString());
			return null;
		}

		OrderInfo orderQuery = new OrderInfo();
		orderQuery.setId(Integer.valueOf(order_id));
		orderQuery = super.getFacade().getOrderInfoService().getOrderInfo(orderQuery);
		if (orderQuery == null) {
			data.put("ret", "0");
			data.put("msg", "订单不存在！");
			super.renderJson(response, data.toString());
			return null;
		}
		orderQuery.setFp_state(Keys.Fp_State.Fp_State_2.getIndex());
		BaseImgs entity = new BaseImgs();
		entity.setLink_id(orderQuery.getId());
		entity.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_60.getIndex());
		BaseImgs entityOld = getFacade().getBaseImgsService().getBaseImgs(entity);
		if (entityOld != null) {
			getFacade().getBaseImgsService().removeBaseImgs(entityOld);
		}
		super.copyProperties(entity, form);
		entity.getMap().put("update_order_detail_fp_state", orderQuery);

		int flag = getFacade().getBaseImgsService().createBaseImgs(entity);
		if (Integer.valueOf(flag) > 0) {
			data.put("ret", "1");
			data.put("msg", "凭证已上传！");
			log.info("==data:" + data.toString());
			super.renderJson(response, data.toString());
			return null;
		} else {
			data.put("ret", "0");
			data.put("msg", "凭证上传败！");
			log.info("==data:" + data.toString());
			super.renderJson(response, data.toString());
			return null;
		}
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
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_10.getIndex()) {
			String msg = "订单状态也改变！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		request.setAttribute("orderInfo", orderInfo);
		return new ActionForward("/customer/MyOrderEntp/orderConfirm.jsp");
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

	public ActionForward orderFh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			String msg = "订单不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_10.getIndex()) {
			String msg = "订单状态也改变！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(Integer.valueOf(order_id));
		wlOrderInfo.setIs_del(0);
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		if (null != wlOrderInfo) {
			// String msg = "该订单已经发货，不能再进行发货操作！";
			// super.showMsgForManager(request, response, msg);
			// return null;
			super.copyProperties(form, wlOrderInfo);
			dynaBean.set("wl_order_id", wlOrderInfo.getId());
			dynaBean.set("fahuo_remark", orderInfo.getFahuo_remark());
		}

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
		request.setAttribute("wlCompInfoList", wlCompInfoList);

		request.setAttribute("orderInfo", orderInfo);
		return new ActionForward("/customer/MyOrderEntp/orderFh.jsp");
	}

	public ActionForward orderFP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			String msg = "订单不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (orderInfo.getFp_state() == Keys.Fp_State.Fp_State_2.getIndex()) {// 如果需要发票
			BaseImgs entity = new BaseImgs();
			entity.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_60.getIndex());
			entity.setLink_id(orderInfo.getId());
			entity = getFacade().getBaseImgsService().getBaseImgs(entity);
			if (entity != null) {
				super.copyProperties(form, entity);
			}
		}
		request.setAttribute("orderInfo", orderInfo);
		return new ActionForward("/customer/MyOrderEntp/orderFP.jsp");
	}

	public ActionForward delayShouhuo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			String msg = "订单不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_40.getIndex()) {
			String msg = "订单状态也改变！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, orderInfo);

		request.setAttribute("today", new Date());
		return new ActionForward("/customer/MyOrderEntp/delayShouhuo.jsp");
	}

	public ActionForward saveDelay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(entity.getId());
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);

		if (orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_40.getIndex()) {
			String msg = "订单状态也改变！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		orderInfo = new OrderInfo();
		orderInfo.setId(entity.getId());
		orderInfo.setFinish_date(entity.getFinish_date());
		orderInfo.setDelay_shouhuo(new Integer(1));
		super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);

		super.renderJavaScript(response, "window.onload=function(){window.parent.location.reload();}");
		return null;
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

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String id = (String) dynaBean.get("order_id");

		if (!GenericValidator.isLong(id)) {
			String msg = "参数有误，请联系管理员！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../login.shtml'}");
			return null;
		}

		// 订单信息
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);

		if (null == orderInfo) {
			String msg = "订单信息不存在，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (null != orderInfo.getEntp_id()) {
			request.setAttribute("entpInfo", super.getEntpInfo(orderInfo.getEntp_id()));
		}

		super.showShippingAddressForOrderInfo(orderInfo);
		request.setAttribute("orderInfo", orderInfo);

		// 产品详细
		OrderInfoDetails orderInfoDetail = new OrderInfoDetails();
		orderInfoDetail.setOrder_id(orderInfo.getId());
		List<OrderInfoDetails> orderInfoDetailList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsList(orderInfoDetail);
		if (orderInfoDetailList != null && orderInfoDetailList.size() > 0) {
			request.setAttribute("orderInfoDetail", orderInfoDetailList.get(0));
			request.setAttribute("basedata", super.getBaseData(orderInfoDetailList.get(0).getHuizhuan_rule()));
		}
		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("payTypeList", Keys.PayType.values());

		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(orderInfo.getId());
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		request.setAttribute("wlOrderInfo", wlOrderInfo);

		request.setAttribute("orderTypeShowList", Keys.OrderType.values());
		return mapping.findForward("input");
	}

	/**
	 * 手动结束拼团订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward completePtOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String id = (String) dynaBean.get("order_id");// 主订单id

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setIs_group_success(1);
		orderInfo.getMap().put("leaderOrderId", id);
		orderInfo.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());

		super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);

		dynaBean.set("order_type", "100");

		return list(mapping, form, request, response);
	}
}
