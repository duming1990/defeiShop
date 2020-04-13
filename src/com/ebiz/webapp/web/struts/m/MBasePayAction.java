package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForward;

import com.aiisen.weixin.pay.api.PayMchAPI;
import com.aiisen.weixin.pay.bean.PayJsRequest;
import com.aiisen.weixin.pay.bean.paymch.Unifiedorder;
import com.aiisen.weixin.pay.bean.paymch.UnifiedorderResult;
import com.aiisen.weixin.pay.util.PayUtil;
import com.aiisen.weixin.pay.util.SignatureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.m.config.AlipayConfig;
import com.alipay.m.util.AlipaySubmit;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.allinpay.SybPayService;

/**
 * @author Wu,Yang
 * @version 2011-9-5
 */
public class MBasePayAction extends MBaseWebAction {

	public ActionForward aliPayMobile(String out_trade_no, BigDecimal order_money, HttpServletRequest request,
			HttpServletResponse response, Integer order_type, String extra) throws Exception {

		String ctx = super.getCtxPath(request);

		String pdUrl = "";
		pdUrl = ctx + "/m/MMyHome.do";

		String pdName = "订单(" + out_trade_no + ")";
		String orderAmount = String.valueOf(order_money.doubleValue());// 订单金额，单位：元

		String payment_type = "1";

		String notify_url = ctx + "/alipay/AlipayNotifyForMobile.do";
		String return_url = ctx + "/alipay/AlipayReturnForMobile.do";

		String subject = pdName;
		String total_fee = orderAmount;
		String show_url = pdUrl;
		String body = "";
		if (StringUtils.isNotBlank(extra)) {
			body = extra;
		} else {
			body = Keys.app_name + "订单";
		}
		String it_b_pay = "";
		String extern_token = "";

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("body", body);
		sParaTemp.put("it_b_pay", it_b_pay);
		sParaTemp.put("extern_token", extern_token);

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		response.getWriter().write(sHtmlText);
		return null;
	}

	public ActionForward weixinPay(Integer order_type, BigDecimal order_money, String out_trade_no,
			HttpServletRequest request, String attach) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);

		request.setAttribute("order_fee", order_money);
		request.setAttribute("header_title", "微信支付");

		String openid = ui.getAppid_weixin();
		String appid = Keys.APP_ID; // appid
		String mch_id = Keys.MCH_ID; // 微信支付商户号
		String key = Keys.API_KEY; // API密钥

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		// 辅助字段 塞入order_type
		unifiedorder.setNonce_str(order_type.toString());
		unifiedorder.setAttach(attach);

		String body = Keys.PayTypeName.WEIXIN.getName();
		unifiedorder.setBody(body);
		unifiedorder.setOut_trade_no(out_trade_no);

		unifiedorder.setTotal_fee(PayUtil.yuanToFee(order_money.toString()));// 单位分
		unifiedorder.setSpbill_create_ip(request.getRemoteAddr());// IP
		String ctx = super.getCtxPath(request);
		String notify_url = ctx + "/weixin/NotifyWeixin.do";

		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type("JSAPI");
		unifiedorder.setOpenid(openid);

		UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);

		PayJsRequest payJsRequest = JSON.parseObject(
				PayUtil.generateMchPayJsRequestJson(unifiedorderResult.getPrepay_id(), appid, key), PayJsRequest.class);

		request.setAttribute("payJsRequest", payJsRequest);

		super.setJsApiTicketRetrunParamToSession(request);

		return new ActionForward("/../index/Pay/weixinpay.jsp");
	}

	public ActionForward weixinPayApp(Integer order_type, BigDecimal order_money, String out_trade_no,
			HttpServletRequest request, String attach) throws Exception {
		String appid = Keys.APP_ID_APP; // appid
		String mch_id = Keys.MCH_ID_APP; // 微信支付商户号
		String api_key_app = Keys.API_KEY_APP; // API密钥

		String ctx = super.getCtxPath(request);
		String notify_url = ctx + "/weixin/NotifyWeixin.do";

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

		JSONObject datas = new JSONObject();
		datas.put("appid", appid);
		datas.put("partnerid", mch_id);
		datas.put("prepayid", prepay_id);
		datas.put("noncestr", noncestr);
		datas.put("timestamp", timestamp);
		datas.put("package", "Sign=WXPay");
		datas.put("sign", sign2);

		request.setAttribute("datas", datas);

		super.setJsApiTicketRetrunParamToSession(request);

		return new ActionForward("/../index/Pay/weixinpayApp.jsp");
	}

	// 通联支付
	public ActionForward allinPayMobile(Integer order_type, String out_trade_no, BigDecimal order_money,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		String ctx = super.getCtxPath(request, false);

		request.setAttribute("order_fee", order_money);
		request.setAttribute("header_title", "通联支付");

		String notify_url = ctx + "/allinpay/NotifyAllinpay.do";

		String payinfo = "";

		Map<String, String> map = SybPayService.pay(PayUtil.yuanToFee(order_money.toString()), out_trade_no, "W02",
				ui.getAppid_weixin(), Keys.APP_ID, notify_url, order_type.toString());
		if (map != null) {
			for (String key : map.keySet()) {
				if (key.equals("payinfo")) {
					payinfo = map.get(key);
					break;
				}
			}
		}

		request.setAttribute("payJsRequest", JSON.parse(payinfo));

		super.setJsApiTicketRetrunParamToSession(request);

		return new ActionForward("/../index/Pay/weixinallinpay.jsp");
	}
}
