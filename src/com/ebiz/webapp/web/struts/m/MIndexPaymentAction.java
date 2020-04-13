package com.ebiz.webapp.web.struts.m;

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

import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class MIndexPaymentAction extends MBasePayAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.pay(mapping, form, request, response);
	}

	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String pay_type = (String) dynaBean.get("pay_type");

		if (StringUtils.isBlank(out_trade_no) || !GenericValidator.isLong(pay_type)) {
			String msg = "Trade index lost.";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		BigDecimal order_money = new BigDecimal("0.0");
		Integer order_type = Keys.OrderType.ORDER_TYPE_10.getIndex();

		OrderMergerInfo orderMergerInfo = new OrderMergerInfo();
		orderMergerInfo.setOut_trade_no(out_trade_no);
		orderMergerInfo = super.getFacade().getOrderMergerInfoService().getOrderMergerInfo(orderMergerInfo);
		if (null == orderMergerInfo) {
			String msg = "合并订单号不存在,CODE:3001";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		OrderMergerInfo orderMergerInfoSon = new OrderMergerInfo();
		orderMergerInfoSon.setPar_id(orderMergerInfo.getId());
		List<OrderMergerInfo> orderMergerInfoList = getFacade().getOrderMergerInfoService()
				.getOrderMergerInfoList(orderMergerInfoSon);
		if ((orderMergerInfoList != null) && (orderMergerInfoList.size() > 0)) {
			for (OrderMergerInfo m : orderMergerInfoList) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setTrade_index(m.getTrade_index());
				orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
				if (null == orderInfo) {
					String msg = "订单不存在！";
					return super.showTipMsg(mapping, form, request, response, msg);
				}
				if (orderInfo.getOrder_state().intValue() != 0) {
					String msg = m.getTrade_index() + "订单状态已变更！";
					return super.showTipMsg(mapping, form, request, response, msg);
				}
				// 更新支付方式
				OrderInfo orderInfoUpdatepayType = new OrderInfo();
				orderInfoUpdatepayType.setId(orderInfo.getId());
				orderInfoUpdatepayType.setPay_type(Integer.valueOf(pay_type));
				super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdatepayType);

				order_money = order_money.add(orderInfo.getOrder_money());

				order_type = orderInfo.getOrder_type();
			}
		}

		if (Keys.PayType.PAY_TYPE_1.getIndex() == Integer.valueOf(pay_type).intValue()) { // 支付宝
			// 如果是微信中选择支付宝支付 则跳转到中间页面
			if (super.isWeixin(request)) {
				return new ActionForward("/MIndexPayment/weixin_show_pay_tip.jsp");
			} else {
				String extra = order_type + "_";
				super.aliPayMobile(orderMergerInfo.getOut_trade_no(), order_money, request, response, order_type,
						extra);
			}
		}

		if (Keys.PayType.PAY_TYPE_3.getIndex() == Integer.valueOf(pay_type).intValue()) { // 微信
			if (super.isWeixin(request)) {
				UserInfo ui = super.getUserInfoFromSession(request);
				if (null == ui) {
					String msg = "您还未登录，请先登录系统！";
					return super.showTipNotLogin(mapping, form, request, response, msg);
				}

				String ctx = super.getCtxPath(request);
				if (StringUtils.isBlank(ui.getAppid_weixin())) {
					String bind_url = ctx + "/weixin/WeixinLogin.do?method=bindWeixin&user_id=" + ui.getId();
					response.sendRedirect(bind_url);
					return null;
				}

				return super.weixinPay(order_type, order_money, orderMergerInfo.getOut_trade_no(), request, null);

			} else if (super.isApp(request)) {
				super.renderJavaScript(response,
						"window.onload=function(){location.href='appweixinpay://" + out_trade_no + "'}");
				return null;
			}
		}

		if (Keys.PayType.PAY_TYPE_4.getIndex() == Integer.valueOf(pay_type).intValue()) { // 通联支付
			UserInfo ui = super.getUserInfoFromSession(request);
			if (null == ui) {
				String msg = "您还未登录，请先登录系统！";
				return super.showTipNotLogin(mapping, form, request, response, msg);
			}

			String ctx = super.getCtxPath(request);
			if (StringUtils.isBlank(ui.getAppid_weixin())) {
				String bind_url = ctx + "/weixin/WeixinLogin.do?method=bindWeixin&user_id=" + ui.getId();
				response.sendRedirect(bind_url);
				return null;
			}
			return super.allinPayMobile(order_type, orderMergerInfo.getOut_trade_no(), order_money, request, response);
		}

		return null;
	}

	public ActionForward PayForUpLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "你尚未登录！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}
		logger.info("======getMobile==========" + ui.getMobile());
		if (StringUtils.isBlank(ui.getMobile())) {
			String msg = "你尚未绑定手机号,请先绑定手机号！";
			request.setAttribute("msg", msg);
			return super.showTipMsg(mapping, form, request, response, msg,
					super.getCtxPath(request, true) + "/m/MMySecurityCenter.do?method=setMobile");

		}

		request.setAttribute("order_money", super.getSysSetting(Keys.upLevelNeedPayMoney));
		request.setAttribute("pay_method", "savePayForUpLevel");

		request.setAttribute("header_title", "升级付费会员");

		request.setAttribute("isWeixin", super.isWeixin(request));
		boolean isApp = super.isApp(request);
		request.setAttribute("isApp", isApp);

		// TODO 如果APP会员升级，则先创建订单在支付
		if (isApp) {
			OrderInfo orderInfo = new OrderInfo();
			String order_money = super.getSysSetting("upLevelNeedPayMoney");
			if (StringUtils.isNotBlank(order_money)) {
				orderInfo.setOrder_money(new BigDecimal(order_money));
			} else {
				orderInfo.setOrder_money(new BigDecimal(9.9));
			}

			String creatTradeIndex = "APP" + this.creatTradeIndex();
			orderInfo.setTrade_index(creatTradeIndex);
			orderInfo.setAdd_user_id(ui.getId());

			int order_id = super.getFacade().getOrderInfoService().createUserUpLevelOrderInfo(orderInfo);
			if (order_id > 0) {// 会员升级订单创建成功，将订单号传给支付接口页面
				request.setAttribute("out_trade_no", creatTradeIndex);
			}
		}

		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("link_id", ui.getId());
		saveToken(request);
		return new ActionForward("/MIndexPayment/selectPayUpLevel.jsp");
	}

	public ActionForward savePayForUpLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!isTokenValid(request)) {
			return super.goHome(mapping, form, request, response);
		}

		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String link_id = (String) dynaBean.get("link_id");
		String pay_type = (String) dynaBean.get("pay_type");
		if (StringUtils.isBlank(link_id)) {
			String msg = "参数有误！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		BigDecimal order_money = new BigDecimal(super.getSysSetting(Keys.upLevelNeedPayMoney));
		String trade_index = super.creatTradeIndex();

		if (Integer.valueOf(pay_type).intValue() == Keys.PayType.PAY_TYPE_1.getIndex()) {// 支付宝
			// 如果是微信中选择支付宝支付 则跳转到中间页面
			if (super.isWeixin(request)) {
				return new ActionForward("/MIndexPayment/weixin_show_pay_tip.jsp");
			} else {
				String extra = Keys.OrderType.ORDER_TYPE_20.getIndex() + "_" + link_id;
				return super.aliPayMobile(trade_index, order_money, request, response,
						Keys.OrderType.ORDER_TYPE_20.getIndex(), extra);
			}
		} else if (Integer.valueOf(pay_type).intValue() == Keys.PayType.PAY_TYPE_3.getIndex()) { // 微信
			if (super.isWeixin(request)) {
				return super.weixinPay(Keys.OrderType.ORDER_TYPE_20.getIndex(), order_money, trade_index, request,
						link_id);
			} else if (super.isApp(request)) {
				return super.weixinPayApp(Keys.OrderType.ORDER_TYPE_20.getIndex(), order_money, trade_index, request,
						link_id);
			}
		}
		return null;
	}
}
