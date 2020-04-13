package com.ebiz.webapp.web.struts.service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.m.config.AlipayConfig;
import com.alipay.m.sign.RSA;
import com.ebiz.webapp.domain.AppTokens;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class WebServiceAction extends BaseWebServiceAction {

	public ActionForward loginForWeb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "1", msg = "";

		JSONObject dataList = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;

		String login_name = (String) dynaBean.get("user_name");
		String password = (String) dynaBean.get("password");
		String forBigShow = (String) dynaBean.get("forBigShow");

		if (StringUtils.isBlank(login_name)) {
			msg = "login_name参数不正确 CODE:1002";
			code = "0";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}
		if (StringUtils.isBlank(password)) {
			msg = "password参数不正确 CODE:1002";
			code = "0";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}

		HttpSession session = request.getSession();
		login_name = login_name.trim();
		UserInfo entity = new UserInfo();
		entity.setUser_name(login_name);
		entity.setIs_del(0);
		entity.getRow().setCount(10);

		Integer u_count = getFacade().getUserInfoService().getUserInfoCount(entity);
		if (u_count.intValue() == 0) {
			entity.setUser_name(null);
			entity.setMobile(login_name);
			Integer m_count = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (m_count.intValue() == 0) {
				msg = "账号不存在，请核对账号！";
				code = "0";
				dataList.put("code", code);
				dataList.put("msg", msg);
				super.returnInfo(response, code, msg, null);
				return null;
			}
		} else if (u_count.intValue() > 1) {
			code = "0";
			msg = "用户名重复！";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}

		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		UserInfo userInfo = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == userInfo) {
			code = "0";
			msg = "密码错误！";
			dataList.put("code", code);
			dataList.put("msg", msg);
			super.returnInfo(response, code, msg, null);
			return null;
		}
		// update login count
		UserInfo ui = new UserInfo();
		ui.setId(userInfo.getId());
		ui.setLogin_count(userInfo.getLogin_count() + 1);
		ui.setLast_login_time(new Date());
		ui.setLast_login_ip(this.getIpAddr(request));
		ui.setLogin_count(userInfo.getLogin_count() + 1);
		ui.setLast_login_time(ui.getLast_login_time());
		ui.setLast_login_ip(ui.getLast_login_ip());
		getFacade().getUserInfoService().modifyUserInfo(ui);
		dataList.put("userInfo", userInfo);

		if (StringUtils.isNotBlank(forBigShow)) {

			if (userInfo.getIs_fuwu().intValue() == 1) {
				ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
				serviceCenterInfo.setAdd_user_id(userInfo.getId());
				serviceCenterInfo = super.getFacade().getServiceCenterInfoService()
						.getServiceCenterInfo(serviceCenterInfo);

				if (null != serviceCenterInfo) {
					BaseFiles baseFiles = new BaseFiles();
					baseFiles.setType(Keys.BaseFilesType.BASE_FILES_TYPE_DAPINSHOW100.getIndex());
					baseFiles.setLink_id(serviceCenterInfo.getP_index());
					baseFiles.setIs_del(0);
					baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
					if (null == baseFiles) {
						code = "0";
						msg = "该用户暂未维护大屏内容！";
						dataList.put("code", code);
						dataList.put("msg", msg);
						super.returnInfo(response, code, msg, null);
						return null;
					}

					dataList.put("big_show_id", baseFiles.getId());
				}
			} else {
				code = "0";
				msg = "该用户不是服务中心账号！";
				dataList.put("code", code);
				dataList.put("msg", msg);
				super.returnInfo(response, code, msg, null);
				return null;
			}
		}

		session.setAttribute(Keys.SESSION_USERINFO_KEY, userInfo);

		dataList.put("code", code);
		dataList.put("msg", msg);

		super.renderJson(response, dataList.toString());
		return null;
	}

	/**
	 * @author 刘佳
	 * @param own_entp_id 企业id
	 */

	public ActionForward getCommInfoListByOwnEntpId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String code = "0", msg = "";

		if (StringUtils.isBlank(own_entp_id)) {
			msg = "参数不正确 CODE:1002";
			code = "0";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		commInfo.setIs_del(0);
		commInfo.setOwn_entp_id(Integer.valueOf(own_entp_id));
		List<CommInfo> commInfoList = getFacade().getCommInfoService().getCommInfoList(commInfo);
		if (null != commInfoList && commInfoList.size() > 0) {
			for (CommInfo temp : commInfoList) {
				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setComm_id(String.valueOf(temp.getId()));
				commTczhPrice = getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
				temp.getMap().put("comm_tczh_barcode", commTczhPrice.getComm_barcode());
			}
		}

		code = "1";
		super.returnInfo(response, code, msg, commInfoList);
		return null;
	}

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
		Integer order_type = Keys.OrderType.ORDER_TYPE_10.getIndex();

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
			order_type = orderInfoTemp.getOrder_type();
		}

		if (!code.equals("0")) {
			String appid = Keys.APP_ID_APP; // appid
			String mch_id = Keys.MCH_ID_APP; // 微信支付商户号
			String api_key_app = Keys.API_KEY_APP; // API密钥

			Unifiedorder unifiedorder = new Unifiedorder();
			unifiedorder.setAppid(appid);
			unifiedorder.setDevice_info("APP-001");
			unifiedorder.setMch_id(mch_id);
			String noncestr = order_type.toString();
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
			noncestr = order_type.toString();

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

		logger.warn("======service weixinpay====" + datas.toString());

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

			// 支付类型
			String payment_type = "1";
			// 必填，不能修改
			// 服务器异步通知页面路径
			String notify_url = ctx + "/alipay/AlipayNotifyForMobile.do";

			// 商户网站订单系统中唯一订单号，必填

			// 订单名称
			String subject = pdName;
			// 必填

			// 付款金额
			String total_fee = orderAmount;
			// 必填

			// 商品展示地址
			String show_url = Keys.app_domain;
			// 必填，需以http://开头的完整路径
			// 超时时间
			String it_b_pay = "1d";
			// 选填

			String body = order_type + "_";

			// ////////////////////////////////////////////////////////////////////////////////
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

	public ActionForward toKenValidation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		logger.info("================================toKenValidation==========================================");
		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));
		String app_key = (String) jsonObject.get("app_key");//
		String token = (String) jsonObject.get("token");
		if (StringUtils.isBlank(app_key) || StringUtils.isBlank(token)) {
			msg = "参数为空，登陆失败";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		AppTokens appTokens = new AppTokens();
		appTokens.setApp_key(app_key);
		appTokens.setToken(token);
		appTokens = super.getFacade().getAppTokensService().getAppTokens(appTokens);
		if (null == appTokens) {
			msg = "签名失效，请重新认证";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		// 数字签名证书只能使用一次，使用后立即删除
		AppTokens appTokensDel = new AppTokens();
		appTokensDel.setApp_key(app_key);
		appTokensDel.setToken(token);
		super.getFacade().getAppTokensService().removeAppTokens(appTokensDel);
		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

}