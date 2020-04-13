package com.ebiz.webapp.web.struts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts.action.ActionForward;

import com.aiisen.weixin.pay.api.PayMchAPI;
import com.aiisen.weixin.pay.bean.paymch.Unifiedorder;
import com.aiisen.weixin.pay.bean.paymch.UnifiedorderResult;
import com.aiisen.weixin.pay.util.PayUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.WeiXinSendMessageUtils;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2011-9-5
 */
public class BasePayAction extends BaseWebAction {

	public ActionForward alipayForPc(String out_trade_no, BigDecimal order_money, HttpServletRequest request,
			HttpServletResponse response, Integer order_type, String extra_common_param) throws Exception {

		String ctx = super.getCtxPath(request);

		String pdUrl = "";
		pdUrl = ctx + "/manager/customer/index.shtml";

		String pdName = Keys.PayTypeName.ALIPAY.getName() + order_type + "(" + out_trade_no + ")";
		String orderAmount = String.valueOf(order_money.doubleValue());// 订单金额，单位：元
		String payment_type = "1";
		String notify_url = ctx + "/alipay/AlipayNotify.do";
		String return_url = ctx + "/alipay/AlipayReturn.do";
		String subject = pdName;
		String total_fee = orderAmount;
		String body = pdName;
		if (StringUtils.isNotBlank(extra_common_param)) {
			body = extra_common_param;
		}

		String show_url = pdUrl;
		String anti_phishing_key = "";
		String exter_invoke_ip = "";

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		sParaTemp.put("extra_common_param", extra_common_param);
		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		response.getWriter().write(sHtmlText);
		return null;
	}

	public ActionForward weixinPayForNative(HttpServletRequest request, Integer order_type, String pay_platform,
			String attach, BigDecimal order_money, String out_trade_no, JSONObject jsonObject) throws Exception {
		request.setAttribute("header_title", "微信支付");

		logger.info("2019年5月17日15:55:41");
		String ctx = super.getCtxPath(request);
		String appid = Keys.APP_ID; // appid
		String mch_id = Keys.MCH_ID; // 微信支付商户号
		String key = Keys.API_KEY; // API密钥

		Unifiedorder unifiedorder = new Unifiedorder();

		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(order_type.toString());

		// 统一改变订单名称
		String body = Keys.PayTypeName.WEIXIN.getName() + order_type + "(" + out_trade_no + ")";
		unifiedorder.setBody(body);

		unifiedorder.setOut_trade_no(out_trade_no);
		BigDecimal allmoney = order_money;

		unifiedorder.setTotal_fee(PayUtil.yuanToFee(allmoney.toString()));// 单位分
		unifiedorder.setSpbill_create_ip(request.getRemoteAddr());// IP
		String notify_url = ctx + "/weixin/NotifyWeixin.do";

		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type("NATIVE");// JSAPI，NATIVE，APP，WAP

		if (StringUtils.isNotBlank(attach)) {
			unifiedorder.setAttach(attach);
		}
		UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, key);
		String code_url = "";
		if (null != unifiedorderResult) {
			code_url = unifiedorderResult.getCode_url();
		}
		logger.warn("code_url:{}", code_url);
		request.setAttribute("code_url", code_url);
		request.setAttribute("order_fee", order_money);
		request.setAttribute("order_money", order_money);
		request.setAttribute("out_trade_no", out_trade_no);
		request.setAttribute("order_type", order_type);

		if (StringUtils.equals(pay_platform, "pc")) {
			return new ActionForward("/index/IndexPayment/pay_weixin.jsp");
		} else {// 手机版，所有人都可以支付
			return new ActionForward("/../index/Pay/weixinpay_qrcode.jsp");
		}
	}

	/**
	 * @author Wu,Yang
	 * @date 2016-07-01
	 * @desc 判断是否是异常订单，支付宝返回订单价格,然后比对下，如果不一致， 然后将订单标记为异常订单,停止回调下面的方法，后台记账明细列表整行标红，发短信给管理员
	 */
	public boolean OrderIsUnusualWithMerger(String total_fee, List<OrderMergerInfo> orderMergerInfoList, int pay_type) {
		boolean OrderIsUnusual = false;
		if (StringUtils.isBlank(total_fee)) {
			return OrderIsUnusual;
		}
		Double order_money_return = new Double(total_fee);
		Double order_money_sum = new Double(0);
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (OrderMergerInfo m : orderMergerInfoList) {
			OrderInfo orderInfojudge = new OrderInfo();
			orderInfojudge.setTrade_index(m.getTrade_index());
			orderInfojudge = getFacade().getOrderInfoService().getOrderInfo(orderInfojudge);

			if (orderInfojudge != null) {
				orderInfoList.add(orderInfojudge);
				order_money_sum += orderInfojudge.getOrder_money().doubleValue();
			}

		}
		logger.info("==order_money_sum:" + order_money_sum.doubleValue());
		logger.info("==order_money_return.doubleValue():" + order_money_return.doubleValue());
		if (order_money_sum.doubleValue() != order_money_return.doubleValue()) {
			if ((orderInfoList != null) && (orderInfoList.size() > 0)) {
				List<String> trade_indexs = new ArrayList<String>();
				String msg_sum = "";
				if (orderInfoList.size() > 0) {
					msg_sum = "合并支付";
				}
				for (OrderInfo orderInfo : orderInfoList) {
					trade_indexs.add(orderInfo.getTrade_index());
					OrderInfo orderInfoUpdate = new OrderInfo();
					orderInfoUpdate.setId(orderInfo.getId());
					orderInfoUpdate.setPay_type(pay_type);
					orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_X90.getIndex());
					String remark = "【异常订单】订单" + msg_sum + "总价格：" + order_money_sum.toString() + ",订单实际支付价格："
							+ order_money_return.toString();
					if (StringUtils.isNotBlank(orderInfo.getRemark())) {
						remark += "，原订单备注：" + orderInfo.getRemark();
					}
					orderInfoUpdate.setRemark(remark);
					getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdate);
				}
				UserInfo useradmin = super.getUserInfo(Keys.SYS_ADMIN_ID);
				// String msg = StringUtils.replace(SMS.sms_20, "{0}", StringUtils.join(trade_indexs, ","));
				// SmsUtils.sendMessage(msg, useradmin.getMobile());

				try {

					// 2、阿里云短信
					StringBuffer message = new StringBuffer("{\"order\":\"" + trade_indexs + "\"");
					message.append("}");
					DySmsUtils.sendMessage(message.toString(), useradmin.getMobile(), SmsTemplate.sms_20);
				} catch (Exception e) {
					// TODO: handle exception
					logger.warn("====短信发送失败===");
				}

				OrderIsUnusual = true;
				logger.warn("==异常订单：" + StringUtils.join(trade_indexs, ","));
			}
		}

		return OrderIsUnusual;
	}

	public boolean OrderIsUnusualWithSigle(String total_fee, OrderInfo orderInfo, int pay_type) {
		boolean OrderIsUnusual = false;
		logger.warn("===total_fee:{}", total_fee);
		if (StringUtils.isBlank(total_fee)) {
			return OrderIsUnusual;
		}
		Double order_money_return = new Double(total_fee);
		Double order_money_sum = orderInfo.getOrder_money().doubleValue();
		logger.warn("===order_money_sum:{}", order_money_sum);

		if (order_money_sum.doubleValue() != order_money_return.doubleValue()) {
			String trade_index = orderInfo.getTrade_index();

			OrderInfo orderInfoUpdate = new OrderInfo();
			orderInfoUpdate.setId(orderInfo.getId());
			orderInfoUpdate.setPay_type(pay_type);
			orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_X90.getIndex());
			String remark = "【异常订单】订单总价格：" + order_money_sum.toString() + ",订单实际支付价格：" + order_money_return.toString();
			if (StringUtils.isNotBlank(orderInfo.getRemark())) {
				remark += "，原订单备注：" + orderInfo.getRemark();
			}
			orderInfoUpdate.setRemark(remark);
			getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdate);

			UserInfo useradmin = super.getUserInfo(Keys.SYS_ADMIN_ID);
			// String msg = StringUtils.replace(SMS.sms_20, "{0}", trade_index);
			// super.sendMsg(1, Keys.SYS_ADMIN_ID, "异常订单提醒", msg);
			// 发短信给管理员
			// SmsUtils.sendMessage(msg, useradmin.getMobile());

			// 2、阿里云短信
			StringBuffer message = new StringBuffer("{\"order\":\"" + trade_index + "\"");
			message.append("}");
			DySmsUtils.sendMessage(message.toString(), useradmin.getMobile(), SmsTemplate.sms_20);

			OrderIsUnusual = true;
			logger.warn("==异常订单：" + trade_index);
		}

		return OrderIsUnusual;
	}

	/**
	 * @author Wu,Yang
	 * @desc 修改订单状态为已支付
	 * @version 2011-09-06
	 * @throws Exception
	 */

	public int modifyOrderInfo(HttpServletRequest request, String out_trade_no, String trade_no, Integer pay_type,
			String total_fee) throws Exception {

		int i = 0;

		OrderMergerInfo orderMergerInfo = new OrderMergerInfo();
		orderMergerInfo.setOut_trade_no(out_trade_no);
		orderMergerInfo = super.getFacade().getOrderMergerInfoService().getOrderMergerInfo(orderMergerInfo);
		if (null != orderMergerInfo) {
			OrderMergerInfo orderMergerInfoSon = new OrderMergerInfo();
			orderMergerInfoSon.setPar_id(orderMergerInfo.getId());
			List<OrderMergerInfo> orderMergerInfoList = getFacade().getOrderMergerInfoService().getOrderMergerInfoList(
					orderMergerInfoSon);

			if ((orderMergerInfoList != null) && (orderMergerInfoList.size() > 0)) {

				boolean orderIsUnusual = this.OrderIsUnusualWithMerger(total_fee, orderMergerInfoList, pay_type);
				if (orderIsUnusual) {// 异常订单
					return -90;
				}

				for (OrderMergerInfo m : orderMergerInfoList) {

					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setTrade_index(m.getTrade_index());
					orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);

					if (orderInfo != null) {

						request.setAttribute("order_type", orderInfo.getOrder_type());

						if (orderInfo.getOrder_state().intValue() != 0) {// 状态已经发生变更了，不能再进行修改
							i = -2;
							return i;
						}

						// 如果订单金额和电子币支付金额都是0，则提示支付失败 开始
						BigDecimal tmpOrderMoney = new BigDecimal("0");
						BigDecimal tmpMoneyBI = new BigDecimal("0");
						if (orderInfo.getOrder_money() != null) {
							tmpOrderMoney = orderInfo.getOrder_money();
						}
						if (orderInfo.getMoney_bi() != null) {
							tmpMoneyBI = orderInfo.getMoney_bi();
						}

						if (tmpOrderMoney.compareTo(new BigDecimal("0")) == 0
								&& tmpMoneyBI.compareTo(new BigDecimal("0")) == 0) {
							i = -2;
							return i;
						}
						// 判断金额错误 结束
						OrderMergerInfo mergerInfo = new OrderMergerInfo();
						mergerInfo.setId(m.getId());
						mergerInfo.setPay_state(new Integer(1));
						mergerInfo.setTrade_no(trade_no);
						if (m.getPay_state().intValue() != 1) {
							getFacade().getOrderMergerInfoService().modifyOrderMergerInfo(mergerInfo);
						}

						UserInfo userInfo = new UserInfo();
						userInfo.setId(orderInfo.getAdd_user_id());
						userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
						if (null != userInfo) {

							OrderInfo oi = new OrderInfo();
							oi.setId(orderInfo.getId());
							oi.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());

							oi.setPay_type(pay_type);
							oi.setPay_date(new Date());
							oi.setTrade_no(trade_no);
							oi.setTrade_merger_index(out_trade_no);

							if (orderInfo.getOrder_state().intValue() != Keys.OrderState.ORDER_STATE_10.getIndex()) {

								oi.getMap().put("pay_success_update_link_table", "true");

								String order_password = null;
								if (orderInfo.getIs_ziti().intValue() == 1) { // 如果自提生成自提密码
									order_password = super.getRandomNumber(8);// 随机8位密码
									oi.setOrder_password(order_password);
								}
								/************** 拼团子订单支付 **************/
								if (orderInfo.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_100.getIndex()
										&& orderInfo.getIs_leader().intValue() == 0) {
									oi.getMap().put("check_group_state_of_leader", true);
									oi.getMap().put("check_leader_order_id", orderInfo.getLeader_order_id());
									CommInfo commInfoByOrderId = getFacade().getCommInfoService().getCommInfoByOrderId(
											orderInfo.getId());
									oi.getMap().put("group_count", commInfoByOrderId.getGroup_count());
								}
								int row = getFacade().getOrderInfoService().modifyOrderInfo(oi);
								if (row == 0) {
									i = -4;
									return i;
								}
								/************** 拼团子订单支付 **************/
								if (row > 0) {
									// 给商家发送站内信
									if (null != orderInfo.getEntp_id()) {
										UserInfo uidest = super.getUserInfoWithEntpId(orderInfo.getEntp_id());
										if (null != uidest) {
											String msg = StringUtils.replace(SMS.sms_09, "{0}",
													orderInfo.getEntp_name());
											super.sendMsg(1, uidest.getId(), "新订单提醒", msg);

											OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
											orderInfoDetails.setOrder_id(orderInfo.getId());
											List<OrderInfoDetails> orderInfoDetailsList = super.getFacade()
													.getOrderInfoDetailsService()
													.getOrderInfoDetailsList(orderInfoDetails);
											orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);
											// 这个地方需要发送微信模板消息
											EntpInfo entpInfo = new EntpInfo();
											entpInfo.setId(orderInfo.getEntp_id());
											entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
											if (null != entpInfo) {

												// 如果是自提单则判断是否需要发送提货短信
												if (orderInfo.getIs_ziti().intValue() == 1) {
													// 1、创蓝短信
													// String message = StringUtils.replace(SMS.sms_04_02, "{0}",
													// order_password);
													// message = StringUtils.replace(message, "{1}",
													// entpInfo.getEntp_name());
													// message = StringUtils.replace(message, "{2}",
													// orderInfo.getTrade_index());
													// message = StringUtils.replace(message, "{3}",
													// entpInfo.getEntp_addr());
													// SmsUtils.sendMessage(message, orderInfo.getRel_phone());

													// 2、阿里云短信
													StringBuffer message = new StringBuffer("{\"code\":\""
															+ order_password + "\",");
													message.append("\"shop\":\""
															+ (entpInfo.getEntp_name().length() > 20 ? entpInfo
																	.getEntp_name().substring(0, 20) : entpInfo
																	.getEntp_name()) + "\",");
													message.append("\"order\":\"" + orderInfo.getTrade_index() + "\",");
													message.append("\"address\":\""
															+ (entpInfo.getEntp_addr().length() > 20 ? entpInfo
																	.getEntp_addr().substring(0, 20) : entpInfo
																	.getEntp_addr()) + "\"");
													message.append("}");
													DySmsUtils.sendMessage(message.toString(),
															orderInfo.getRel_phone(), SmsTemplate.sms_04_02);
												}

												if (StringUtils.isNotBlank(entpInfo.getEntp_tel())) {
													// 2、阿里云短信给商家
													StringBuffer message = new StringBuffer("{\"entp\":\""
															+ entpInfo.getEntp_name() + "\",");
													message.append("\"order\":\"" + orderInfo.getTrade_index() + "\",");
													message.append("}");
													DySmsUtils.sendMessage(message.toString(), entpInfo.getEntp_tel(),
															SmsTemplate.sms_24);
												}

											}
										}
									}
								}
							}
						}
						i++;
					}
				}
			}

		}
		return i;
	}

	/**
	 * 线下扫码活动订单支付成功回调
	 */
	public int modifyOrderInfoActivity(HttpServletRequest request, String trade_index, String trade_no,
			Integer pay_type, String total_fee) throws Exception {
		logger.info("===開始調用modifyOrderPublic=====");
		int i = 0;

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setTrade_index(trade_index);
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);

		if (orderInfo != null) {

			request.setAttribute("order_type", orderInfo.getOrder_type());

			if (orderInfo.getOrder_state().intValue() != 0) {
				i = -2;
				return i;
			}

			OrderInfo oi = new OrderInfo();
			oi.setId(orderInfo.getId());
			oi.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
			oi.setFinish_date(new Date());// 订单结束时间 用于结算订单
			oi.setPay_type(pay_type);
			oi.setPay_date(new Date());
			oi.setTrade_no(trade_no);
			oi.setTrade_merger_index(trade_index);

			if (orderInfo.getOrder_state().intValue() != Keys.OrderState.ORDER_STATE_10.getIndex()) {

				oi.getMap().put("pay_success_update_link_table", "true");

				String order_password = null;
				if (orderInfo.getIs_ziti().intValue() == 1) {
					order_password = super.getRandomNumber(8);
					oi.setOrder_password(order_password);
				}

				oi.getMap().put("update_entp_huokuan_bi", "true");
				oi.getMap().put("activity_pay_sus_seng_entp_msg", "true");
				int row = getFacade().getOrderInfoService().modifyOrderInfo(oi);
				if (row > 0) {
					logger.info("===訂單修改成功===");

					// 给商家发送站内信
					if (null != orderInfo.getEntp_id()) {
						UserInfo uidest = super.getUserInfoWithEntpId(orderInfo.getEntp_id());
						if (null != uidest) {
							String msg = StringUtils.replace(SMS.sms_09, "{0}", orderInfo.getEntp_name());
							super.sendMsg(1, uidest.getId(), "新订单提醒", msg);

							EntpInfo e = super.getEntpInfo(orderInfo.getEntp_id());
							if (null != e && null != e.getEntp_tel()) {
								if (StringUtils.isNotBlank(e.getEntp_tel())) {
									// 2、阿里云短信给商家
									StringBuffer message = new StringBuffer("{\"entp\":\"" + e.getEntp_name() + "\",");
									message.append("\"order\":\"" + orderInfo.getTrade_index() + " ，支付金额:"
											+ orderInfo.getOrder_money().toString() + ",抵扣金额:"
											+ orderInfo.getMoney_bi().toString() + "\",");
									message.append("}");
									// DySmsUtils.sendMessage(message.toString(), e.getEntp_tel(), SmsTemplate.sms_24);
								}

								UserInfo entoTelUser = new UserInfo();
								entoTelUser.getMap().put("ym_id", e.getEntp_tel());
								List<UserInfo> ulist = getFacade().getUserInfoService().getUserInfoList(entoTelUser);
								if (null != ulist && ulist.size() > 0) {
									logger.info("===发送微信模板消息===");
									// 这个地方需要发送微信模板消息
									if (null != ulist.get(0) && null != ulist.get(0).getAppid_weixin()) {
										WeiXinSendMessageUtils.orderSuccessSendMessage(orderInfo, null, ulist.get(0),
												null, null, null);
									}
								} else {
									logger.warn("=====发送微信模板消息===手机号码条数大于1===");
								}
							}
						}
					}
				}
			}
			i++;
		}

		return i;
	}

	public void modifyForPayUpLevel(String link_id, String trade_no, Integer pay_type, String out_trade_no,
			String total_fee) throws Exception {
		logger.warn("====modifyForPayUpLevel userId=" + link_id);
		UserInfo userInfo = super.getUserInfo(Integer.valueOf(link_id));
		if (null == userInfo) {
			return;
		}
		logger.warn("====userInfo userId=" + link_id);
		UserInfo userInfoUpdate = new UserInfo();
		userInfoUpdate.setId(Integer.valueOf(link_id));
		userInfoUpdate.setUser_level(Keys.USER_LEVEL_ONE);
		if (null != userInfo.getCard_end_date()) {
			if (DateTools.compareDate(userInfo.getCard_end_date(), new Date()) == 1) {// 大于今天，证明还没有过期
				userInfoUpdate.setCard_end_date(DateUtils.addYears(userInfo.getCard_end_date(), 1));
			} else {
				userInfoUpdate.setCard_end_date(DateUtils.addYears(new Date(), 1));
			}
		} else {
			userInfoUpdate.setCard_end_date(DateUtils.addYears(new Date(), 1));
		}
		userInfoUpdate.setUser_no(super.createUserInfoNo(Integer.valueOf(link_id)));

		userInfoUpdate.getMap().put("out_trade_no", out_trade_no);
		userInfoUpdate.getMap().put("pay_type", pay_type);
		userInfoUpdate.getMap().put("trade_no", trade_no);
		userInfoUpdate.getMap().put("total_fee", total_fee);
		userInfoUpdate.getMap().put("pay_success_create_orderInfo", "true");
		logger.warn("====modifyForPayUpLevel modifyUserInfo");
		int row = super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
		logger.warn("====modifyForPayUpLevel row=" + row);
		if (row > 0) {// 这个地方需要发送微信
			WeiXinSendMessageUtils.vipFirst(super.getUserInfo(Integer.valueOf(link_id)));
		}
	}

	/**
	 * @author LiuJia
	 * @desc 回调的公共方法
	 * @version 2017-11-24
	 * @throws Exception
	 */

	public int modifyOrderPublic(HttpServletRequest request, String out_trade_no, String trade_no, Integer pay_type,
			Integer order_type, String total_fee, String attach) throws Exception {

		// 商品下单
		// if (order_type.intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()
		// || order_type.intValue() == Keys.OrderType.ORDER_TYPE_7.getIndex()
		// || order_type.intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()
		// || order_type.intValue() == Keys.OrderType.ORDER_TYPE_70.getIndex()
		// || order_type.intValue() == Keys.OrderType.ORDER_TYPE_80.getIndex()
		// || order_type.intValue() == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
		//
		// }
		// 付费升级订单
		if (order_type.intValue() == Keys.OrderType.ORDER_TYPE_20.getIndex()) {
			this.modifyForPayUpLevel(attach, trade_no, pay_type, out_trade_no, total_fee);
		} else if (order_type.intValue() == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			// 线下活动订单
			return this.modifyOrderInfoActivity(request, out_trade_no, trade_no, pay_type, total_fee);
		} else {
			return this.modifyOrderInfo(request, out_trade_no, trade_no, pay_type, total_fee);
		}

		return 0;
	}
}
