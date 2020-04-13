package com.ebiz.webapp.web.struts;

import java.math.BigDecimal;
import java.net.URLEncoder;
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

import com.aiisen.weixin.pay.api.PayMchAPI;
import com.aiisen.weixin.pay.bean.paymch.Unifiedorder;
import com.aiisen.weixin.pay.bean.paymch.UnifiedorderResult;
import com.aiisen.weixin.pay.util.PayUtil;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author TUDP
 * @version 2014-5-30
 */
public class IndexPaymentAction extends BasePayAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.pay(mapping, form, request, response);
	}

	public ActionForward PayForUpLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "你尚未登录！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("order_money", super.getSysSetting(Keys.upLevelNeedPayMoney));
		request.setAttribute("order_name", "升级付费会员");
		request.setAttribute("pay_method", "savePayForUpLevel");

		request.setAttribute("isWeixin", super.isWeixin(request));
		request.setAttribute("isApp", super.isApp(request));

		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("link_id", ui.getId());
		saveToken(request);
		return new ActionForward("/index/IndexPayment/selectPayType.jsp");
	}

	public ActionForward savePayForUpLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
			String extra_common_param = Keys.OrderType.ORDER_TYPE_20.getIndex() + "_" + link_id;
			super.alipayForPc(trade_index, order_money, request, response, Keys.OrderType.ORDER_TYPE_20.getIndex(),
					extra_common_param);
			return null;

		} else if (Integer.valueOf(pay_type).intValue() == Keys.PayType.PAY_TYPE_3.getIndex()) { // 微信

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("link_id", link_id);
			return super.weixinPayForNative(request, Keys.OrderType.ORDER_TYPE_20.getIndex(), "pc", link_id,
					order_money, trade_index, jsonObject);
		}
		return null;
	}

	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String pay_type = (String) dynaBean.get("pay_type");
		String is_test = (String) dynaBean.get("is_test");

		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (StringUtils.isBlank(out_trade_no) || !GenericValidator.isLong(pay_type)) {
			String msg = "Trade index lost.";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		if (null == userInfo) {
			return null;
		}

		BigDecimal order_money = new BigDecimal("0.0");
		Date order_date = new Date();
		Integer order_type = Keys.OrderType.ORDER_TYPE_10.getIndex();

		OrderMergerInfo orderMergerInfo = new OrderMergerInfo();
		orderMergerInfo.setOut_trade_no(out_trade_no);
		orderMergerInfo = super.getFacade().getOrderMergerInfoService().getOrderMergerInfo(orderMergerInfo);
		if (null != orderMergerInfo) {
			OrderMergerInfo orderMergerInfoSon = new OrderMergerInfo();
			orderMergerInfoSon.setPar_id(orderMergerInfo.getId());
			List<OrderMergerInfo> orderMergerInfoList = getFacade().getOrderMergerInfoService().getOrderMergerInfoList(
					orderMergerInfoSon);
			if ((orderMergerInfoList != null) && (orderMergerInfoList.size() > 0)) {
				for (OrderMergerInfo m : orderMergerInfoList) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setTrade_index(m.getTrade_index());
					orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null == orderInfo) {
						String msg = "订单不存在！";
						super.showMsgForManager(request, response, msg);
						return null;
					}
					if (orderInfo.getOrder_state().intValue() != 0) {
						String msg = m.getTrade_index() + "订单状态已变更！";
						super.showMsgForCustomer(request, response, msg);
						return null;
					}
					// 更新支付方式
					OrderInfo orderInfoUpdatepayType = new OrderInfo();
					orderInfoUpdatepayType.setId(orderInfo.getId());
					orderInfoUpdatepayType.setPay_type(Integer.valueOf(pay_type));
					super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdatepayType);

					order_money = order_money.add(orderInfo.getOrder_money());
					order_type = orderInfo.getOrder_type();
					order_date = orderInfo.getOrder_date();
				}
			}

			orderMergerInfo.getMap().put("order_money", order_money);

			orderMergerInfo.getMap().put("pd_name", out_trade_no);

			if (order_money.doubleValue() < 0.01) {
				String msg = "订单金额小于最低支付金额！";
				super.showMsgForManager(request, response, msg);
				return null;
			}
			if (StringUtils.isNotBlank(is_test)) {
				return super.weixinPayForNative(request, order_type, "pc", null, order_money, out_trade_no, null);
			}
			if (StringUtils.equals("1", pay_type)) { // 支付宝
				String extra_common_param = order_type + "_";
				this.alipayForPc(out_trade_no, order_money, request, response, order_type, extra_common_param);
			}
			if (StringUtils.equals("3", pay_type)) { // 微信
				return super.weixinPayForNative(request, order_type, "pc", null, order_money, out_trade_no, null);
			}
		}
		return null;
	}

	public ActionForward selectPayTypeForBiXiaoFei(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String type = (String) dynaBean.get("type");
		String link_id = (String) dynaBean.get("link_id");

		if (StringUtils.isBlank(type) || StringUtils.isBlank(link_id)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		return null;
	}

	/**
	 * @author Wu,Yang
	 * @desc 微信 扫描支付
	 */
	public ActionForward weixinPay(OrderMergerInfo orderMergerInfo, HttpServletRequest request,
			HttpServletResponse response, Integer order_type, String needFxFlag, String huizhuan_order_ids)
			throws Exception {

		BigDecimal order_money = (BigDecimal) orderMergerInfo.getMap().get("order_money");
		String out_trade_no = orderMergerInfo.getOut_trade_no();
		String ctx = super.getCtxPath(request);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (StringUtils.isBlank(ui.getAppid_weixin())) {
			// 微信支付，未绑定
			String code_url = ctx + "/weixin/WeixinLogin.do?method=bindWeixin&user_id=" + ui.getId();
			logger.warn("==weixinPay code_url:{}", code_url);
			code_url = URLEncoder.encode(code_url, "UTF-8");
			request.setAttribute("code_url", code_url);
			return new ActionForward("/index/IndexPayment/pay_weixin_not_bind.jsp");
		}
		// 设置微信支付 json 开始： 后期如果增加优惠劵，可以改用ajax调用加载
		String openid = ui.getAppid_weixin();
		String appid = Keys.APP_ID; // appid
		String mch_id = Keys.MCH_ID; // 微信支付商户号
		String key = Keys.API_KEY; // API密钥

		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(String.valueOf(new Date().getTime()));

		unifiedorder.setBody(Keys.PayTypeName.WEIXIN.getName() + order_type + "(" + out_trade_no + ")");
		unifiedorder.setOut_trade_no(out_trade_no);
		BigDecimal allmoney = order_money;

		unifiedorder.setTotal_fee(PayUtil.yuanToFee(allmoney.toString()));// 单位分
		unifiedorder.setSpbill_create_ip(request.getRemoteAddr());// IP
		String notify_url = ctx + "/weixin/NotifyWeixin.do";

		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type("NATIVE");// JSAPI，NATIVE，APP，WAP
		unifiedorder.setOpenid(openid);// 我的openid

		UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
		String code_url = "";
		if (null != unifiedorderResult) {
			code_url = unifiedorderResult.getCode_url();
		}
		logger.warn("code_url:{}", code_url);
		// 将json 传到jsp 页面
		request.setAttribute("code_url", code_url);
		request.setAttribute("order_money", order_money);
		request.setAttribute("out_trade_no", out_trade_no);
		request.setAttribute("order_type", order_type);
		return new ActionForward("/index/IndexPayment/pay_weixin.jsp");
	}
}
