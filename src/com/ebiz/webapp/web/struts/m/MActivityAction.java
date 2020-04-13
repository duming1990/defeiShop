package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.aiisen.weixin.pay.util.PayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.m.config.AlipayConfig;
import com.alipay.m.util.AlipaySubmit;
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.ActivityApply;
import com.ebiz.webapp.domain.ActivityApplyComm;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.allinpay.SybPayService;

/**
 * @author 刘佳
 * @date: 2018年1月23日 下午5:26:20
 */
public class MActivityAction extends MBasePayAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return toList(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String openid = (String) dynaBean.get("openid");
		request.setAttribute("isWeixin", super.isWeixin(request));
		String id = (String) dynaBean.get("id");
		String msg;

		if (!GenericValidator.isInt(id)) {
			msg = "请选择商家。";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		ActivityApply ActivityApply = new ActivityApply();
		ActivityApply.setId(Integer.valueOf(id));
		ActivityApply.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		ActivityApply = getFacade().getActivityApplyService().getActivityApply(ActivityApply);
		if (null == ActivityApply) {
			msg = "商家未申请活动或未审核通过。";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		Activity activity = new Activity();
		activity.setId(ActivityApply.getLink_id());
		activity.setIs_del(0);
		activity.getMap().put("now_date", "true");
		activity = getFacade().getActivityService().getActivity(activity);
		if (null == activity) {
			msg = "当前时间不在活动时间内或已关闭";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		logger.info("===activity.getPay_type().intValue():" + activity.getPay_type().intValue());

		if (ActivityApply.getPay_type().intValue() == Keys.ActivityPayType.ActivityPayType_1.getIndex()) {
			return this.toPay(mapping, form, request, response);
		}

		request.setAttribute("header_title", "活动商品");
		return new ActionForward("/MActivity/list.jsp");
	}

	public ActionForward toList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String openid = (String) dynaBean.get("openid");
		String id = (String) dynaBean.get("id");
		request.setAttribute("isWeixin", super.isWeixin(request));

		// 这里不能带端口号，否则在微信里面会报rediect_uri错误
		String ctx = super.getCtxPath(request, false);
		String return_url = ctx + "/m/MActivity.do?method=list&id=" + id;

		// 判断是否微信扫描
		if (super.isWeixin(request)) {
			// 如果是微信的话 直接跳转到自动登陆

			StringBuilder link = new StringBuilder();
			String scope = "snsapi_userinfo";
			String state = "";

			StringBuffer server = new StringBuffer();
			server.append(request.getHeader("host")).append(request.getContextPath());
			request.setAttribute("server_domain", server.toString());
			String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=afterLoginWeixinForActivity");
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
			link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
					.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
					.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");
			response.sendRedirect(link.toString());

		} else {

			return list(mapping, form, request, response);

			// StringBuilder link = new StringBuilder();
			// String scope = "auth_user,auth_base";
			//
			// StringBuffer server = new StringBuffer();
			// server.append(request.getHeader("host")).append(request.getContextPath());
			// String redirectUri =
			// "http://".concat(server.toString()).concat("/alipay/AlipayLogin.do?method=afterLogin");
			//
			// if (StringUtils.isNotBlank(return_url)) {
			// redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
			// }
			//
			// link.append(AliPayUrl.OPENAUTH + "?app_id=" + AlipayConfig.app_id).append("&scope=" + scope)
			// .append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8"));
			//
			// response.sendRedirect(link.toString());

		}

		return new ActionForward("/MActivity/list.jsp");
	}

	public ActionForward paySuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "支付成功");

		return new ActionForward("/MActivity/paySuccess.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (!GenericValidator.isInt(id)) {
			msg = "请选择商家。";
			return returnAjaxData(response, code, msg, data);
		}

		ActivityApply ActivityApply = new ActivityApply();
		ActivityApply.setId(Integer.valueOf(id));
		ActivityApply.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		ActivityApply = getFacade().getActivityApplyService().getActivityApply(ActivityApply);
		if (null == ActivityApply) {
			msg = "商家未申请活动或未审核通过。";
			return returnAjaxData(response, code, msg, data);
		}

		Activity activity = new Activity();
		activity.setId(ActivityApply.getLink_id());
		activity.setIs_del(0);
		activity.getMap().put("now_date", "true");
		activity = getFacade().getActivityService().getActivity(activity);
		if (null == activity) {
			msg = "当前时间不在活动时间内或已关闭";
			return returnAjaxData(response, code, msg, data);
		}

		List<ActivityApplyComm> commList = new ArrayList<ActivityApplyComm>();
		ActivityApplyComm comm = new ActivityApplyComm();
		comm.setActivity_apply_id(ActivityApply.getId());
		comm.setActivity_id(activity.getId());
		comm.setEntp_id(ActivityApply.getEntp_id());
		List<ActivityApplyComm> list = getFacade().getActivityApplyCommService().getActivityApplyCommList(comm);
		for (ActivityApplyComm cur : list) {
			CommTczhPrice e = new CommTczhPrice();
			e.setComm_id(cur.getComm_id() + "");
			List<CommTczhPrice> tczhList = getFacade().getCommTczhPriceService().getCommTczhPriceList(e);
			cur.getMap().put("tczhList", tczhList);
			if (null != tczhList && tczhList.size() > 0) {
				e = tczhList.get(0);
				cur.getMap().put("tczh_id", e.getId());
				cur.getMap().put("pd_count", 0);
				cur.getMap().put("comm_price", e.getComm_price());
				cur.getMap().put("tczh_name", cur.getComm_name());
				cur.getMap().put("pd_max_count", e.getInventory());
				cur.getMap().put("isChoose", false);
				commList.add(cur);
			}
		}

		data.put("list", commList);
		return returnAjaxData(response, "1", msg, data);
	}

	public ActionForward creartOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String activityApplyId = (String) dynaBean.get("activityApplyId");
		String applyCommIds = (String) dynaBean.get("cartIds");
		String pdCount = (String) dynaBean.get("pdCount");
		String tczh_id_s = (String) dynaBean.get("tczh_id_s");
		String order_money_ = (String) dynaBean.get("order_money");
		logger.info("==tczh_id_s:" + tczh_id_s);

		if (StringUtils.isBlank(applyCommIds)) {
			msg = "请选择商品!";
			return returnAjaxData(response, code, msg, data);
		}
		String ids[] = applyCommIds.split(",");
		if (ids.length == 0) {
			msg = "请选择商品!";
			return returnAjaxData(response, code, msg, data);
		}
		String pd_count[] = pdCount.split(",");
		String tczh_ids[] = tczh_id_s.split(",");

		ActivityApply apply = new ActivityApply();
		apply.setId(Integer.valueOf(activityApplyId));
		apply.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		apply = getFacade().getActivityApplyService().getActivityApply(apply);
		if (null == apply) {
			msg = "商家未申请活动";
			return returnAjaxData(response, code, msg, data);
		}

		Activity activity = new Activity();
		activity.setId(apply.getLink_id());
		activity.setIs_del(0);
		activity.getMap().put("now_date", "true");
		activity = getFacade().getActivityService().getActivity(activity);
		if (null == activity) {
			msg = "当前时间不在活动时间内或已关闭";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		EntpInfo entpInfo = super.getEntpInfo(apply.getEntp_id());
		if (null == entpInfo) {
			msg = "商家不存在或未审核通过";
			return returnAjaxData(response, code, msg, data);
		}

		BigDecimal order_money = new BigDecimal("0");
		BigDecimal sum_red_money = new BigDecimal("0");

		List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();

		for (int i = 0; i < ids.length; i++) {

			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();

			ActivityApplyComm a = new ActivityApplyComm();
			a.setId(Integer.valueOf(ids[i]));
			a = getFacade().getActivityApplyCommService().getActivityApplyComm(a);
			if (null == a) {
				continue;
			}

			CommInfo commInfo = super.getCommInfoOnlyById(a.getComm_id());

			CommTczhPrice commTczhPrice = new CommTczhPrice();
			commTczhPrice.setId(Integer.valueOf(tczh_ids[i]));
			// commTczhPrice.setComm_id(commInfo.getId() + "");
			commTczhPrice = getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
			if (null == commTczhPrice) {
				msg = commInfo.getComm_name() + "套餐不存在";
				return returnAjaxData(response, code, msg, data);

			}

			if (commTczhPrice.getInventory().intValue() <= 0) {
				msg = commInfo.getComm_name() + "库存不足";
				return returnAjaxData(response, code, msg, data);
			}

			orderInfoDetails.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
			orderInfoDetails.setCls_id(commInfo.getCls_id());
			orderInfoDetails.setCls_name(commInfo.getCls_name());
			orderInfoDetails.setPd_id(commInfo.getPd_id());
			orderInfoDetails.setPd_name(commInfo.getPd_name());
			logger.info("==订单明细数量:" + Integer.valueOf(pd_count[i]));
			orderInfoDetails.setGood_count(Integer.valueOf(pd_count[i]));
			orderInfoDetails.setComm_id(commInfo.getId());
			orderInfoDetails.setComm_name(commInfo.getComm_name());
			orderInfoDetails.setComm_tczh_id(commTczhPrice.getId());
			orderInfoDetails.setComm_tczh_name(commTczhPrice.getTczh_name());
			orderInfoDetails.setGood_price(commTczhPrice.getComm_price());
			orderInfoDetails.setGood_sum_price(commTczhPrice.getComm_price().multiply(new BigDecimal(pd_count[i])));
			orderInfoDetails.setActual_money(commTczhPrice.getComm_price().multiply(new BigDecimal(pd_count[i])));
			orderInfoDetails.setEntp_id(commInfo.getOwn_entp_id());
			orderInfoDetails.setMatflow_price(new BigDecimal(0));
			orderInfoDetailsList.add(orderInfoDetails);

			order_money = order_money.add(commTczhPrice.getComm_price().multiply(new BigDecimal(pd_count[i])));

		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setActivity_id(apply.getLink_id());

		orderInfo.setOrder_money(new BigDecimal(order_money_));
		orderInfo.setOrder_num(pd_count.length);
		orderInfo.setNo_dis_money(order_money);

		orderInfo.setTrade_index(this.creatTradeIndex());
		orderInfo.setOrder_date(new Date());
		orderInfo.setAdd_date(new Date());
		orderInfo.setEnd_date(DateUtils.addDays(new Date(), Keys.ORDER_END_DATE));// 订单默认7天后失效
		orderInfo.setAdd_user_id(-1);
		orderInfo.setAdd_user_name("线下扫码付款用户");

		if (orderInfo.getOrder_money().compareTo(order_money) != 0) {// 判断订单金额与实际支付金额是否想相等
			for (OrderInfoDetails odsList : orderInfoDetailsList) {// 如果不相等则进行金额的平均分配

				if (order_money.compareTo(new BigDecimal(0)) != 0) {
					BigDecimal update_after_money = odsList.getActual_money().multiply(orderInfo.getOrder_money())
							.divide(order_money, 2, BigDecimal.ROUND_HALF_UP);
					odsList.setActual_money(update_after_money);
				} else {
					odsList.setActual_money(new BigDecimal(0));
				}
			}
		}

		UserInfo ui = super.getUserInfo(request);
		if (null != ui) {
			orderInfo.setAdd_user_id(ui.getId());
			orderInfo.setAdd_user_name(ui.getUser_name());
		}

		orderInfo.setEntp_id(entpInfo.getId());
		orderInfo.setEntp_name(entpInfo.getEntp_name());
		orderInfo.setPay_platform(Keys.PayPlatform.WAP.getIndex());
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());
		orderInfo.setMatflow_price(new BigDecimal(0));
		Boolean isApp = super.isApp(request);
		if (isApp) {
			String jugdeAppPt = super.jugdeAppPt(request);
			if (jugdeAppPt.contains("android")) {
				orderInfo.setPay_platform(Keys.PayPlatform.APP_ANDROID.getIndex());
			}
			if (jugdeAppPt.contains("iphone")) {
				orderInfo.setPay_platform(Keys.PayPlatform.APP_IPHONE.getIndex());
			}
		}

		orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

		orderInfo.getMap().put("update_comm_info_saleCountAndInventory", "true");
		int insertFlag = getFacade().getOrderInfoService().createOrderInfo(orderInfo);
		logger.info("==insertFlag:" + insertFlag);
		if (insertFlag > 0) {
			data.put("trade_index", orderInfo.getTrade_index());
			return returnAjaxData(response, "1", msg, data);

			// super.renderJavaScript(response, "location.href='MMyCartInfo.do?method=selectPayType&trade_index="
			// + orderInfo.getTrade_index() + "'");
			// return null;
		} else if (insertFlag == -1) {
			msg = "商品库存不足，请联系商家！";
			return returnAjaxData(response, code, msg, data);
		} else if (insertFlag == -2) {
			msg = "同步京东订单有误，请联系管理员！";
			return returnAjaxData(response, code, msg, data);
		} else {
			msg = "保存订单有误，请联系管理员！";
			return returnAjaxData(response, code, msg, data);
		}
	}

	public ActionForward toPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "支付方式");
		request.setAttribute("isWeixin", super.isWeixin(request));
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		ActivityApply a = new ActivityApply();
		a.setId(Integer.valueOf(id));
		a.setAudit_state(1);
		a = getFacade().getActivityApplyService().getActivityApply(a);
		if (null != a) {
			EntpInfo b = super.getEntpInfo(a.getEntp_id());
			if (null != b) {
				request.setAttribute("entpInfo", b);
			}
		}

		if (super.isWeixin(request)) {
			request.setAttribute("pay_type", "4");
		} else {
			request.setAttribute("pay_type", "1");
		}
		UserInfo ui = super.getUserInfo(request);
		request.setAttribute("userInfo", ui);
		return new ActionForward("/MActivity/pay.jsp");
	}

	public ActionForward selectPayType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		request.setAttribute("header_title", "支付方式");

		String trade_index = (String) dynaBean.get("trade_index");
		String openid = (String) dynaBean.get("openid");
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(trade_index)) {
			String msg = "参数错误";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		if (StringUtils.isBlank(openid)) {
			String msg = "openid参数错误";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("trade_index", trade_index);
		request.setAttribute("isWeixin", super.isWeixin(request));
		// request.setAttribute("isWeixin", "true");

		OrderInfo orderInfo = new OrderInfo();
		// orderInfo.setAdd_user_id(-1);
		orderInfo.setTrade_index(trade_index);
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			String msg = "订单不存在！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (orderInfo.getOrder_state().intValue() != 0) {
			String msg = "订单" + trade_index + "状态已更新！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("orderInfo", orderInfo);

		BigDecimal order_money = new BigDecimal("0.0");

		request.setAttribute("order_money", order_money);

		UserInfo ui = super.getUserInfo(request);
		if (null != ui) {
			request.setAttribute("userInfo", ui);
		}

		request.setAttribute("openid", openid);

		saveToken(request);

		ActivityApply a = new ActivityApply();
		a.setId(Integer.valueOf(id));
		a.setAudit_state(1);
		a = getFacade().getActivityApplyService().getActivityApply(a);
		if (null != a) {
			EntpInfo b = super.getEntpInfo(a.getEntp_id());
			if (null != b) {
				request.setAttribute("entpInfo", b);
			}
		}

		// return new ActionForward("/MActivity/step2.jsp");
		return new ActionForward("/MActivity/pay.jsp");
	}

	public ActionForward step3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "支付成功");

		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String pay_type = (String) dynaBean.get("pay_type");
		String openid = (String) dynaBean.get("openid");

		if (StringUtils.isBlank(pay_type))
			if (super.isWeixin(request)) {
				pay_type = "4";
			}

		if (StringUtils.isBlank(out_trade_no) || StringUtils.isBlank(pay_type)) {
			String msg = "参数有误，请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		if (StringUtils.isBlank(openid)) {
			String msg = "openid参数有误，请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		// 更新订单状态

		if (!isTokenValid(request)) {
			return unspecified(mapping, form, request, response);
		}
		resetToken(request);

		super.renderJavaScript(response, "location.href='" + super.getCtxPath(request)
				+ "/m/MActivity.do?method=pay&out_trade_no=" + out_trade_no + "&pay_type=" + pay_type + "&openid="
				+ openid + "';");
		return null;

	}

	// 通联支付
	public ActionForward weixinPayTonglian(Integer order_type, String out_trade_no, BigDecimal order_money,
			HttpServletRequest request, HttpServletResponse response, String openid) throws Exception {
		String ctx = super.getCtxPath(request, false);

		request.setAttribute("order_fee", order_money);
		request.setAttribute("header_title", "通联支付");

		OrderInfo a = new OrderInfo();
		a.setTrade_index(out_trade_no);
		a = getFacade().getOrderInfoService().getOrderInfo(a);

		// if (null == a) {
		// return returnJsMsg(response, “订单不存在”)；
		// }

		String notify_url = ctx + "/allinpay/NotifyAllinpay.do";

		String payinfo = "";

		Map<String, String> map = SybPayService.pay(PayUtil.yuanToFee(a.getOrder_money().toString()), out_trade_no,
				"W02", openid, Keys.APP_ID, notify_url, order_type.toString());
		if (map != null) {
			for (String key : map.keySet()) {
				if (key.equals("payinfo")) {
					payinfo = map.get(key);
					break;
				}
			}
		}

		request.setAttribute("payJsRequest", JSON.parse(payinfo));
		request.setAttribute("order_type", Keys.OrderType.ORDER_TYPE_90.getIndex());

		super.setJsApiTicketRetrunParamToSession(request);
		return new ActionForward("/../index/Pay/weixinallinpay.jsp");

	}

	public ActionForward aliPayMobile(String out_trade_no, BigDecimal order_money, HttpServletRequest request,
			HttpServletResponse response, Integer order_type, String extra) throws Exception {
		logger.info("====aliPayMobile=====activity===");
		String ctx = super.getCtxPath(request);

		String pdUrl = "";
		pdUrl = ctx + "/m/MActivity.do?method=paySuccess";

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
		logger.info(sHtmlText.toString());
		response.getWriter().write(sHtmlText);
		return null;
	}

	public String aliPayMobileAjax(String out_trade_no, BigDecimal order_money, HttpServletRequest request,
			HttpServletResponse response, Integer order_type, String extra) throws Exception {
		logger.info("====aliPayMobile=====activity===");
		String ctx = super.getCtxPath(request);

		String pdUrl = "";
		pdUrl = ctx + "/m/MActivity.do?method=paySuccess";

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
		logger.info(sHtmlText.toString());
		return sHtmlText;
	}

	public void weixinPayTonglianAjax(String out_trade_no, BigDecimal order_money, HttpServletRequest request,
			HttpServletResponse response, String openid) throws Exception {
		String ctx = super.getCtxPath(request, false);

		request.setAttribute("order_fee", order_money);
		request.setAttribute("header_title", "通联支付");

		String notify_url = ctx + "/allinpay/NotifyAllinpay.do";

		String payinfo = "";

		logger.warn("===weixinpay=order_money:" + order_money);
		Map<String, String> map = SybPayService.pay(PayUtil.yuanToFee(order_money.toString()), out_trade_no, "W02",
				openid, Keys.APP_ID, notify_url, Keys.OrderType.ORDER_TYPE_90.getIndex() + "");
		if (map != null) {
			for (String key : map.keySet()) {
				if (key.equals("payinfo")) {
					payinfo = map.get(key);
					break;
				}
			}
		}

		request.setAttribute("payJsRequest", JSON.parse(payinfo));
		request.setAttribute("order_type", Keys.OrderType.ORDER_TYPE_90.getIndex());

		super.setJsApiTicketRetrunParamToSession(request);

	}

	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("==activity==pay=====");
		DynaBean dynaBean = (DynaBean) form;

		String trade_index = (String) dynaBean.get("trade_index");
		String pay_money = (String) dynaBean.get("pay_money");
		String pay_type = (String) dynaBean.get("pay_type");
		String openid = (String) dynaBean.get("openid");
		String id = (String) dynaBean.get("id");
		String is_use_bi_dianzi = (String) dynaBean.get("is_use_bi_dianzi");
		logger.info("===is_use_bi_dianzi:" + is_use_bi_dianzi);
		String msg = "";
		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(trade_index)) {
			msg = "订单编号参数不正确";
			return returnErr(response, msg, data);
		}
		if (StringUtils.isBlank(pay_money)) {
			msg = "请填写支付金额！";
			return returnErr(response, msg, data);
		}
		if (StringUtils.isBlank(pay_type)) {
			msg = "请选择支付方式！";
			return returnErr(response, msg, data);
		}
		if (Integer.valueOf(pay_type) == Keys.PayType.PAY_TYPE_4.getIndex()) {
			// 如果是微信支付，判断openid
			if (StringUtils.isBlank(openid)) {
				return returnErr(response, "微信id不正确，请重新打开。", data);
			}
		}

		OrderInfo a = new OrderInfo();
		a.setTrade_index(trade_index);
		a.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());
		a = getFacade().getOrderInfoService().getOrderInfo(a);
		if (null == a) {
			return returnErr(response, "订单不存在或状态已改变。", data);
		}

		BigDecimal pay_money_this = new BigDecimal(pay_money);
		BigDecimal order_money_this = new BigDecimal(pay_money);
		BigDecimal no_ids_money = new BigDecimal(pay_money);
		BigDecimal bi_dianzi = new BigDecimal(0);

		if (pay_type.equals("0")) {
			logger.info("===线下活动订单===线下支付===");

			UserInfo userInfo = super.getUserInfo(request);
			if (null == userInfo) {
				msg = "余额支付必须登录用户！";
				return returnErr(response, msg, data);
			}
			bi_dianzi = order_money_this;

			BigDecimal rmb_to_dianzibi = super.BiToBi2(bi_dianzi, Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());

			if (userInfo.getBi_dianzi().compareTo(rmb_to_dianzibi) < 0) {
				msg = "余额不足！";
				return returnErr(response, msg, data);
			}

			logger.warn("===使用余额抵扣 更改价格===");
		}

		// 使用电子币抵扣
		if (StringUtils.isNotBlank(is_use_bi_dianzi) && is_use_bi_dianzi.equals("true")) {

			UserInfo userInfo = super.getUserInfo(request);
			if (null == userInfo) {
				msg = "余额支付必须登录用户！";
				return returnErr(response, msg, data);

			}

			// 用户电子币 大于 当前支付金额 完全抵扣
			if (userInfo.getBi_dianzi().compareTo(pay_money_this) >= 0) {
				order_money_this = new BigDecimal(0);
				bi_dianzi = pay_money_this;
				pay_type = Keys.PayType.PAY_TYPE_0.getIndex() + "";// 完全抵扣 支付方式改成 余额支付
			} else {
				order_money_this = pay_money_this.subtract(userInfo.getBi_dianzi());
				bi_dianzi = userInfo.getBi_dianzi();

			}

			// 更改支付金额
			logger.warn("===使用余额抵扣 更改价格===");
		}

		Integer order_type = Keys.OrderType.ORDER_TYPE_90.getIndex();

		// 更新支付方式
		OrderInfo orderInfoUpdatepayType = new OrderInfo();
		orderInfoUpdatepayType.setId(a.getId());
		orderInfoUpdatepayType.setPay_type(Integer.valueOf(pay_type));
		orderInfoUpdatepayType.getMap().put("activity_update_pay_type", "true");
		orderInfoUpdatepayType.setOrder_money(order_money_this);
		orderInfoUpdatepayType.setMoney_bi(bi_dianzi);

		super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdatepayType);

		if (pay_type.equals("0")) {
			logger.info("===线下活动订单===线下支付===");

			UserInfo userInfo = super.getUserInfo(request);
			if (null == userInfo) {
				msg = "余额支付必须登录用户！";
				return returnErr(response, msg, data);
			}

			BigDecimal rmb_to_dianzibi = super.BiToBi2(bi_dianzi, Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());

			if (userInfo.getBi_dianzi().compareTo(rmb_to_dianzibi) < 0) {
				msg = "余额不足！";
				return returnErr(response, msg, data);
			}

			int i = super.modifyOrderInfoActivity(request, a.getTrade_index(), " ", Integer.valueOf(pay_type),
					String.valueOf(Double.valueOf(order_money_this.toString()) / (Double.valueOf(100))));
			if (i < 0) {
				msg = "支付失败！";
				return returnErr(response, msg, data);
			}
		}

		if (Keys.PayType.PAY_TYPE_1.getIndex() == Integer.valueOf(pay_type).intValue()) { // 支付宝
			// 如果是微信中选择支付宝支付 则跳转到中间页面
			if (super.isWeixin(request)) {
				return new ActionForward("/MIndexPayment/weixin_show_pay_tip.jsp");
			} else {
				String extra = order_type + "_";

				String ajaxFrom = this.aliPayMobileAjax(a.getTrade_index(), order_money_this, request, response,
						order_type, extra);

				data.put("ajaxFrom", ajaxFrom);

			}
		}

		if (Keys.PayType.PAY_TYPE_4.getIndex() == Integer.valueOf(pay_type).intValue()) { // 微信
			logger.warn("==openid:" + openid);
			data.put("out_trade_no", a.getTrade_index());
			data.put("openid", openid);
			data.put("order_type", order_type);
		}
		data.put("pay_type", pay_type);

		return returnSus(response, msg, data);
	}

	public ActionForward payAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("==payAjax=====");

		JSONObject data = new JSONObject();
		String msg = "";

		UserInfo ui = super.getUserInfo(request);

		DynaBean dynaBean = (DynaBean) form;
		String pay_money = (String) dynaBean.get("pay_money");
		String pay_type = (String) dynaBean.get("pay_type");
		String openid = (String) dynaBean.get("openid");
		String id = (String) dynaBean.get("id");
		String is_use_bi_dianzi = (String) dynaBean.get("is_use_bi_dianzi");
		logger.info("===is_use_bi_dianzi:" + is_use_bi_dianzi);

		if (StringUtils.isBlank(id)) {
			msg = "商家参数不正确！";
			return returnErr(response, msg, data);
		}
		if (StringUtils.isBlank(pay_money)) {
			msg = "请填写支付金额！";
			return returnErr(response, msg, data);
		}
		if (StringUtils.isBlank(pay_type)) {
			msg = "请选择支付方式！";
			return returnErr(response, msg, data);
		}
		if (Integer.valueOf(pay_type) == Keys.PayType.PAY_TYPE_4.getIndex()) {
			// 如果是微信支付，判断openid
			if (StringUtils.isBlank(openid)) {
				return returnErr(response, "微信id不正确，请重新打开。", data);
			}
		}

		BigDecimal pay_money_this = new BigDecimal(pay_money);
		BigDecimal order_money_this = new BigDecimal(pay_money);
		BigDecimal no_ids_money = new BigDecimal(pay_money);
		BigDecimal bi_dianzi = new BigDecimal(0);

		if (pay_type.equals("0")) {
			logger.info("===线下活动订单===线下支付===");

			UserInfo userInfo = super.getUserInfo(request);
			if (null == userInfo) {
				msg = "余额支付必须登录用户！";
				return returnErr(response, msg, data);
			}
			bi_dianzi = order_money_this;

			BigDecimal rmb_to_dianzibi = super.BiToBi2(bi_dianzi, Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());

			if (userInfo.getBi_dianzi().compareTo(bi_dianzi) < 0) {
				msg = "余额不足！";
				return returnErr(response, msg, data);
			}
		}

		// 使用电子币抵扣
		if (StringUtils.isNotBlank(is_use_bi_dianzi) && is_use_bi_dianzi.equals("true")) {

			UserInfo userInfo = super.getUserInfo(request);
			if (null == userInfo) {
				msg = "余额支付必须登录用户！";
				return returnErr(response, msg, data);
			}

			// 用户电子币 大于 当前支付金额 完全抵扣
			if (userInfo.getBi_dianzi().compareTo(pay_money_this) >= 0) {
				order_money_this = new BigDecimal(0);
				bi_dianzi = pay_money_this;
				pay_type = Keys.PayType.PAY_TYPE_0.getIndex() + "";// 完全抵扣 支付方式改成 余额支付
			} else {
				order_money_this = pay_money_this.subtract(userInfo.getBi_dianzi());
				bi_dianzi = userInfo.getBi_dianzi();
			}
		}

		ActivityApply apply = new ActivityApply();
		apply.setId(Integer.valueOf(id));
		apply.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		apply = getFacade().getActivityApplyService().getActivityApply(apply);
		if (null == apply) {
			msg = "商家未申请活动！";
			return returnErr(response, msg, data);
		}

		Activity activity = new Activity();
		activity.setId(apply.getLink_id());
		activity.setIs_del(0);
		activity.getMap().put("now_date", "true");
		activity = getFacade().getActivityService().getActivity(activity);
		if (null == activity) {
			msg = "当前时间不在活动时间内或已关闭";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		EntpInfo entpInfo = super.getEntpInfo(apply.getEntp_id());
		if (null == entpInfo) {
			msg = "商家不存在或未审核通过！";
			return returnErr(response, msg, data);
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setIs_activity_default(1);
		commInfo.setIs_del(0);
		commInfo.setOwn_entp_id(entpInfo.getId());
		List<CommInfo> commInfoList = getFacade().getCommInfoService().getCommInfoList(commInfo);
		if (null == commInfoList || commInfoList.size() == 0) {
			msg = "商品未维护，请联系管理员!";
			return returnErr(response, msg, data);
		}
		commInfo = commInfoList.get(0);

		BigDecimal order_money = new BigDecimal(pay_money);
		Integer order_type = Keys.OrderType.ORDER_TYPE_90.getIndex();

		List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();

		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setComm_id(commInfo.getId() + "");
		commTczhPrice = getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
		if (null == commTczhPrice) {
			msg = commInfo.getComm_name() + "套餐不存在";
			return returnErr(response, msg, data);

		}

		if (commTczhPrice.getInventory().intValue() <= 0) {
			msg = commInfo.getComm_name() + "库存不足";
			return returnErr(response, msg, data);
		}

		orderInfoDetails.setOrder_type(order_type);
		orderInfoDetails.setCls_id(commInfo.getCls_id());
		orderInfoDetails.setCls_name(commInfo.getCls_name());
		orderInfoDetails.setPd_id(commInfo.getPd_id());
		orderInfoDetails.setPd_name(commInfo.getPd_name());
		orderInfoDetails.setGood_count(1);
		orderInfoDetails.setComm_id(commInfo.getId());
		orderInfoDetails.setComm_name(commInfo.getComm_name());
		orderInfoDetails.setComm_tczh_id(commTczhPrice.getId());
		orderInfoDetails.setComm_tczh_name(commTczhPrice.getTczh_name());
		orderInfoDetails.setGood_price(commTczhPrice.getComm_price());
		orderInfoDetails.setGood_sum_price(order_money);
		orderInfoDetails.setActual_money(order_money);
		orderInfoDetails.setEntp_id(commInfo.getOwn_entp_id());
		orderInfoDetails.setMatflow_price(new BigDecimal(0));
		orderInfoDetailsList.add(orderInfoDetails);

		String trade_index = this.creatTradeIndex();

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setActivity_id(apply.getLink_id());
		orderInfo.setTrade_index(trade_index);

		orderInfo.setOrder_money(order_money_this);
		orderInfo.setNo_dis_money(no_ids_money);
		orderInfo.setMoney_bi(bi_dianzi);
		orderInfo.setOrder_num(1);

		orderInfo.setOrder_date(new Date());
		orderInfo.setAdd_date(new Date());

		// 订单默认7天后失效
		orderInfo.setEnd_date(DateUtils.addDays(new Date(), Keys.ORDER_END_DATE));
		orderInfo.setAdd_user_id(-1);
		orderInfo.setAdd_user_name("线下扫码付款用户");
		if (null != ui) {
			orderInfo.setAdd_user_id(ui.getId());
			orderInfo.setAdd_user_name(ui.getUser_name());
		}

		orderInfo.setEntp_id(entpInfo.getId());
		orderInfo.setEntp_name(entpInfo.getEntp_name());
		orderInfo.setPay_platform(Keys.PayPlatform.WAP.getIndex());
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());
		orderInfo.setMatflow_price(new BigDecimal(0));
		Boolean isApp = super.isApp(request);
		if (isApp) {
			String jugdeAppPt = super.jugdeAppPt(request);
			if (jugdeAppPt.contains("android")) {
				orderInfo.setPay_platform(Keys.PayPlatform.APP_ANDROID.getIndex());
			}
			if (jugdeAppPt.contains("iphone")) {
				orderInfo.setPay_platform(Keys.PayPlatform.APP_IPHONE.getIndex());
			}
		}

		orderInfo.setPay_type(Integer.valueOf(pay_type));
		// 余额支付就更新电子币
		if (orderInfo.getPay_type().intValue() == Keys.PayType.PAY_TYPE_0.getIndex()) {
			// orderInfo.setMoney_bi(orderInfo.getOrder_money());
			// orderInfo.setOrder_money(new BigDecimal(0));

		}

		orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

		orderInfo.getMap().put("update_comm_info_saleCountAndInventory", "true");
		int insertFlag = getFacade().getOrderInfoService().createOrderInfo(orderInfo);
		logger.info("==insertFlag:" + insertFlag);
		if (insertFlag == -1) {
			msg = "商品库存不足，请联系商家！";
			return returnErr(response, msg, data);
		}

		OrderInfo thisOrder = new OrderInfo();
		thisOrder.setId(insertFlag);
		thisOrder = getFacade().getOrderInfoService().getOrderInfo(thisOrder);

		trade_index = trade_index.trim();

		if (pay_type.equals("0")) {
			logger.info("===线下活动订单===线下支付===");

			UserInfo userInfo = super.getUserInfo(request);
			if (null == userInfo) {
				msg = "余额支付必须登录用户！";
				return returnErr(response, msg, data);
			}

			BigDecimal rmb_to_dianzibi = super.BiToBi2(bi_dianzi, Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());

			if (userInfo.getBi_dianzi().compareTo(rmb_to_dianzibi) < 0) {
				msg = "余额不足！";
				return returnErr(response, msg, data);
			}

			int i = super.modifyOrderInfoActivity(request, trade_index, "111", Integer.valueOf(pay_type),
					String.valueOf(Double.valueOf(orderInfo.getOrder_money().toString()) / (Double.valueOf(100))));
			if (i < 0) {
				msg = "支付失败！";
				return returnErr(response, msg, data);
			}
		}

		if (Keys.PayType.PAY_TYPE_1.getIndex() == Integer.valueOf(pay_type).intValue()) { // 支付宝
			// 如果是微信中选择支付宝支付 则跳转到中间页面
			if (super.isWeixin(request)) {
				return new ActionForward("/MIndexPayment/weixin_show_pay_tip.jsp");
			} else {
				String extra = order_type + "_";

				String ajaxFrom = this.aliPayMobileAjax(trade_index, thisOrder.getOrder_money(), request, response,
						order_type, extra);

				data.put("ajaxFrom", ajaxFrom);

				// return this.aliPayMobile(orderMergerInfo.getOut_trade_no(), thisOrder.getOrder_money(), request,
				// response, order_type, extra);

			}
		}

		if (Keys.PayType.PAY_TYPE_4.getIndex() == Integer.valueOf(pay_type).intValue()) { // 微信
			logger.warn("==openid:" + openid);
			// return this.weixinPayTonglian(order_type, out_trade_no, thisOrder.getOrder_money(), request, response,
			// openid);

			// weixinPayTonglianAjax(order_type, out_trade_no, order_money, request, response, openid);
			data.put("out_trade_no", trade_index);
			data.put("openid", openid);
			data.put("order_type", order_type);
		}
		data.put("pay_type", pay_type);

		return returnSus(response, msg, data);
	}

	public ActionForward weixinPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("==weixinPay=====");
		DynaBean dynaBean = (DynaBean) form;
		// String order_money = (String) dynaBean.get("order_money");
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String openid = (String) dynaBean.get("openid");
		// String order_type = (String) dynaBean.get("order_type");

		OrderInfo a = new OrderInfo();
		a.setTrade_index(out_trade_no);
		a = getFacade().getOrderInfoService().getOrderInfo(a);

		weixinPayTonglianAjax(out_trade_no, a.getOrder_money(), request, response, openid);

		return new ActionForward("/../index/Pay/weixinallinpay.jsp");

	}
}
