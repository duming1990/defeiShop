package com.ebiz.webapp.web.struts.service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.pay.api.PayMchAPI;
import com.aiisen.weixin.pay.bean.paymch.Unifiedorder;
import com.aiisen.weixin.pay.bean.paymch.UnifiedorderResult;
import com.aiisen.weixin.pay.util.PayUtil;
import com.aiisen.weixin.pay.util.SignatureUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.m.config.AlipayConfig;
import com.alipay.m.sign.RSA;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.web.Keys;

public class WebPayServiceAction extends BaseWebServiceAction {

	public ActionForward weixinPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "调用成功";
		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");

		if (StringUtils.isBlank(out_trade_no)) {
			msg = "out_trade_no参数不正确 CODE:2003";
			code = "0";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		JSONObject datas = new JSONObject();

		BigDecimal order_money = new BigDecimal("0.0");

		String ctx = super.getCtxPath(request);
		String notify_url = ctx + "/weixin/NotifyWeixin.do";

		OrderMergerInfo orderMergerInfoTemp = new OrderMergerInfo();
		orderMergerInfoTemp.setOut_trade_no(out_trade_no);
		orderMergerInfoTemp = super.getFacade().getOrderMergerInfoService().getOrderMergerInfo(orderMergerInfoTemp);
		if (null == orderMergerInfoTemp) {
			msg = "合并订单不存在 CODE:2005";
			code = "0";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		OrderMergerInfo orderMergerInfoSon = new OrderMergerInfo();
		orderMergerInfoSon.setPar_id(orderMergerInfoTemp.getId());
		List<OrderMergerInfo> orderMergerInfoList = getFacade().getOrderMergerInfoService().getOrderMergerInfoList(
				orderMergerInfoSon);
		if ((orderMergerInfoList == null) || (orderMergerInfoList.size() <= 0)) {
			msg = "合并子订单不存在 CODE:2006";
			code = "0";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		for (OrderMergerInfo m : orderMergerInfoList) {
			OrderInfo orderInfoTemp = new OrderInfo();
			orderInfoTemp.setTrade_index(m.getTrade_index());
			orderInfoTemp = getFacade().getOrderInfoService().getOrderInfo(orderInfoTemp);
			if (null == orderInfoTemp) {
				msg = "订单不存在！";
				code = "0";
				break;
			} else if (orderInfoTemp.getOrder_state().intValue() != 0) {
				msg = m.getTrade_index() + "订单状态已变更！";
				code = "0";
				break;
			}
			order_money = order_money.add(orderInfoTemp.getOrder_money());
		}

		if (!code.equals("0")) {
			String appid = Keys.APP_ID_APP; // appid
			String mch_id = Keys.MCH_ID_APP; // 微信支付商户号
			String api_key_app = Keys.API_KEY_APP; // API密钥

			Unifiedorder unifiedorder = new Unifiedorder();
			unifiedorder.setAppid(appid);
			unifiedorder.setDevice_info("APP-001");
			unifiedorder.setMch_id(mch_id);
			String noncestr = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
			unifiedorder.setNonce_str(noncestr);

			unifiedorder.setBody(Keys.PayTypeName.WEIXINAPP.getName() + "(" + out_trade_no + ")");
			unifiedorder.setOut_trade_no(out_trade_no);
			BigDecimal allmoney = order_money;

			unifiedorder.setTotal_fee(PayUtil.yuanToFee(allmoney.toString()));// 单位分
			unifiedorder.setSpbill_create_ip(request.getRemoteAddr());// IP

			unifiedorder.setNotify_url(notify_url);
			unifiedorder.setTrade_type("APP");// JSAPI，NATIVE，APP，WAP

			UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, api_key_app);

			String prepay_id = unifiedorderResult.getPrepay_id();

			if (StringUtils.isBlank(prepay_id)) {
				code = "0";
				msg = "发起微信支付失败";
				super.returnInfo(response, code, msg, null);
				return null;
			}
			String timestamp = String.valueOf(new Date().getTime() / 1000);
			noncestr = StringUtils.replace(UUID.randomUUID().toString(), "-", "");

			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("appid", appid);
			map.put("partnerid", mch_id);
			map.put("prepayid", prepay_id);
			map.put("noncestr", noncestr);
			map.put("timestamp", timestamp);
			map.put("package", "Sign=WXPay");
			String sign2 = SignatureUtil.generateSign(map, api_key_app);

			datas.put("appid", appid);
			datas.put("partnerid", mch_id);
			datas.put("prepayid", prepay_id);
			datas.put("noncestr", noncestr);
			datas.put("timestamp", timestamp);
			datas.put("package", "Sign=WXPay");
			datas.put("sign", sign2);

		}
		super.returnInfo(response, code, msg, datas);
		return null;

	}

	public ActionForward alipyPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "调用成功";
		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");// 商户订单号

		if (StringUtils.isBlank(out_trade_no)) {
			msg = "out_trade_no参数不正确 CODE:2003";
			code = "0";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		JSONObject datas = new JSONObject();

		BigDecimal order_money = new BigDecimal("0.0");
		Integer order_type = Keys.OrderType.ORDER_TYPE_10.getIndex();
		String ctx = super.getCtxPath(request);

		OrderMergerInfo orderMergerInfoTemp = new OrderMergerInfo();
		orderMergerInfoTemp.setOut_trade_no(out_trade_no);
		orderMergerInfoTemp = super.getFacade().getOrderMergerInfoService().getOrderMergerInfo(orderMergerInfoTemp);
		if (null == orderMergerInfoTemp) {
			msg = "合并订单不存在 CODE:2005";
			code = "0";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		OrderMergerInfo orderMergerInfoSon = new OrderMergerInfo();
		orderMergerInfoSon.setPar_id(orderMergerInfoTemp.getId());
		List<OrderMergerInfo> orderMergerInfoList = getFacade().getOrderMergerInfoService().getOrderMergerInfoList(
				orderMergerInfoSon);
		if ((orderMergerInfoList == null) || (orderMergerInfoList.size() <= 0)) {
			msg = "合并子订单不存在 CODE:2006";
			code = "0";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		for (OrderMergerInfo m : orderMergerInfoList) {
			OrderInfo orderInfoTemp = new OrderInfo();
			orderInfoTemp.setTrade_index(m.getTrade_index());
			orderInfoTemp = getFacade().getOrderInfoService().getOrderInfo(orderInfoTemp);
			if (null == orderInfoTemp) {
				msg = "订单不存在！";
				code = "0";
				break;
			} else if (orderInfoTemp.getOrder_state().intValue() != 0) {
				msg = m.getTrade_index() + "订单状态已变更！";
				code = "0";
				break;
			}
			order_money = order_money.add(orderInfoTemp.getOrder_money());
			order_type = orderInfoTemp.getOrder_type();
		}

		Map<String, String> sParaTemp = new HashMap<String, String>();

		if (!code.equals("0")) {

			String pdName = Keys.PayTypeName.ALIPAY.getName() + order_type + "("
					+ orderMergerInfoTemp.getOut_trade_no() + ")";
			String orderAmount = String.valueOf(order_money.doubleValue());// 订单金额，单位：元

			String payment_type = "1";
			String notify_url = ctx + "/alipay/AlipayNotifyForMobile.do";
			String subject = pdName;
			String total_fee = orderAmount;
			String show_url = "http://www.9tiaofu.com";
			String body = order_type + "_";
			String it_b_pay = "1d";

			// 把请求参数打包成数组
			sParaTemp.put("service", "mobile.securitypay.pay");
			sParaTemp.put("partner", Keys.alipay_partner_app);
			sParaTemp.put("seller_id", Keys.alipay_partner_app);
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", payment_type);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			sParaTemp.put("total_fee", total_fee);
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("body", body);
			sParaTemp.put("it_b_pay", it_b_pay);

			logger.info("=====================" + body);
		}

		String sHtmlText = "";
		for (Iterator<String> iter = sParaTemp.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String value = sParaTemp.get(name);
			sHtmlText += name + "=\"" + value + "\"&";
		}
		// 建立请求
		sHtmlText = sHtmlText.substring(0, sHtmlText.length() - 1);

		String sign = RSA.sign(sHtmlText, Keys.alipay_private_key_app_rsa1, AlipayConfig.input_charset);
		String outText = sHtmlText + "&sign=\"" + URLEncoder.encode(sign, "UTF-8") + "\"&sign_type=\""
				+ AlipayConfig.sign_type + "\"";

		datas.put("order_string", outText);
		super.returnInfo(response, code, msg, datas);
		return null;

	}
}