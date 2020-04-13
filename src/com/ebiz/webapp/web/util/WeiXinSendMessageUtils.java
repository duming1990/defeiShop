package com.ebiz.webapp.web.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aiisen.weixin.httpclient.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.WEIXINSMS;
import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author 刘佳
 * @date: 2018年3月19日 下午4:24:26
 */
public class WeiXinSendMessageUtils extends BaseAction {

	protected static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

	/**
	 * @desc 订单支付成功发现微信消息
	 * @param entpInfo 关联商家
	 * @param userInfo 购买人用户
	 * @param orderInfo 订单信息
	 * @param biRecord 预计收益奖励
	 * @param userInfoPar 代言人
	 */
	public static void orderSuccessSendMessage(OrderInfo orderInfo, EntpInfo entpInfo, UserInfo userEntpInfo,
			UserInfo userInfo, UserBiRecord biRecord, UserInfo userInfoPar) {
		// 订单支付成功
		// 商家
		// 详细内容 {{first}}
		// 订单号：{ keyword1}
		// 订单金额：{keyword2元}
		// 买家：{keyword3}
		// 订单状态：{keyword5}
		// {{remark}}
		if (null != userEntpInfo && StringUtils.isNotBlank(userEntpInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyOrderDetail.do?order_id=" + orderInfo.getId();
			JSONObject data = new JSONObject();
			data.put(
					"first",
					SmsUtils.setMsgValue("[" + userEntpInfo.getOwn_entp_name() + "]您好，你的店铺内新增"
							+ orderInfo.getOrder_num() + "笔支付订单，请及时处理发货。"));
			data.put("keyword1", SmsUtils.setMsgValue(orderInfo.getTrade_index()));
			data.put("keyword2",
					SmsUtils.setMsgValue(orderInfo.getOrder_money().add(orderInfo.getMoney_bi()).toString()));
			data.put("keyword3", SmsUtils.setMsgValue(orderInfo.getAdd_user_name()));
			data.put("keyword4", SmsUtils.setMsgValue(Keys.OrderState.getName(orderInfo.getOrder_state())));
			data.put("remark", SmsUtils.setMsgValue("请尽快给买家处理订单"));
			sendWeixinTemplateMsg(userEntpInfo.getAppid_weixin(), WEIXINSMS.weixin_orderSucces_sms_entp, url_template,
					data);
		}

		// 客户
		// 详细内容：{{first}}
		// 支付金额：{orderMoneySum}
		// 商品信息：{orderProductName}
		// {{Remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyOrderDetail.do?order_id=" + orderInfo.getId();
			JSONObject data = new JSONObject();
			data.put("first", SmsUtils.setMsgValue("尊敬的会员[" + userInfo.getUser_name() + "]您好，您的订单已支付成功，我们将尽快为您发货。"));
			data.put("orderMoneySum",
					SmsUtils.setMsgValue(orderInfo.getOrder_money().add(orderInfo.getMoney_bi()).toString()));
			String orderName = "";
			for (int i = 0; i < orderInfo.getOrderInfoDetailsList().size(); i++) {
				orderName += orderInfo.getOrderInfoDetailsList().get(i).getComm_name() + ",";
			}
			data.put("orderProductName", SmsUtils.setMsgValue(orderName));
			data.put("Remark", SmsUtils.setMsgValue("如有问题，请尽快与我们联系，感谢您的购物"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_PaySucces_sms_userinfo, url_template,
					data);
		}

		// 代言费
		// 详细内容：{{first}}
		// 订单编号：{ keyword1 }
		// 订单金额：{keyword2}
		// 预计收益：{keyword3元}
		// {{remark}}
		// 详情进入会员中心查看。
		if (null != userInfoPar && StringUtils.isNotBlank(userInfoPar.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyQianBao.do";
			JSONObject data = new JSONObject();
			data.put(
					"first",
					SmsUtils.setMsgValue("尊敬的会员【" + orderInfo.getAdd_user_name()
							+ "】您好，恭喜您获得一笔新的订单代言收益，代言收益将会在订单完成后到账。"));
			data.put("keyword1", SmsUtils.setMsgValue(orderInfo.getTrade_index()));
			data.put("keyword2",
					SmsUtils.setMsgValue(orderInfo.getOrder_money().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString()));
			data.put("keyword3",
					SmsUtils.setMsgValue(biRecord.getBi_no().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString()));
			data.put("remark", SmsUtils.setMsgValue("详情进入我的钱包查看。"));
			sendWeixinTemplateMsg(userInfoPar.getAppid_weixin(), WEIXINSMS.weixin_orderSucces_sms_spokesman,
					url_template, data);
		}
	}

	/**
	 * @desc 订单发货发现微信消息
	 * @param orderInfo 订单信息
	 * @param userInfo 买家
	 * @param wlorderInfo 物流信息
	 */
	public static void ordersShipment(OrderInfo orderInfo, UserInfo userInfo, WlOrderInfo wlorderInfo) {

		// 详细内容：{{first}}
		// 订单编号：{ keyword1 }
		// 物流公司：{ keyword2 }
		// 物流单号：{ keyword3 }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyOrder.do?method=list&order_state="
					+ Keys.OrderState.ORDER_STATE_20.getIndex();
			JSONObject data = new JSONObject();
			data.put(
					"first",
					SmsUtils.setMsgValue("尊敬的会员【" + userInfo.getUser_name() + "】您在" + Keys.app_name
							+ "购买的宝贝已经发货，请注意及时查收。"));
			data.put("keyword1", SmsUtils.setMsgValue(orderInfo.getTrade_index()));
			data.put("keyword2", SmsUtils.setMsgValue(wlorderInfo.getWl_comp_name()));
			data.put("keyword3", SmsUtils.setMsgValue(wlorderInfo.getWaybill_no()));
			data.put("remark", SmsUtils.setMsgValue("详情进入会员中心查看。"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_orderSucces_sms_ordersShipment,
					url_template, data);
		}

	}

	/**
	 * @desc 代言人收益到账
	 * @param orderInfo 订单信息
	 * @param userInfo 买家
	 * @param wlorderInfo 代言奖励
	 */
	public static void spokesMan(OrderInfo orderInfo, UserInfo userInfo, UserBiRecord biRecord) {

		// 详细内容：{{first}}
		// 到账时间：{ keyword1 }
		// 到账金额：{ keyword2 }
		// 收益产品：{ keyword3 }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyQianBao.do?id=" + userInfo.getId();
			JSONObject data = new JSONObject();
			data.put("first", SmsUtils.setMsgValue("尊敬的会员[" + userInfo.getUser_name() + "]您好，您有一笔收益已经到账"));
			data.put("keyword1", SmsUtils.setMsgValue(sdFormat_ymdhms.format(new Date())));
			data.put("keyword2",
					SmsUtils.setMsgValue(biRecord.getBi_no().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString()));
			data.put("keyword3", SmsUtils.setMsgValue(""));
			data.put("remark", SmsUtils.setMsgValue("详情进入我的钱包查看。"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_endorsements, url_template, data);
		}

	}

	/**
	 * @desc 成为会员提醒
	 */
	public static void vipFirst(UserInfo userInfo) {

		// 详细内容：{{first}}
		// 会员号：{ cardNumber }
		// { type }地址：{ address }
		// 会员信息：{ vipName }
		// 会员手机号：{ vipPhone }
		// 有效期：{ expDate }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyCard.do?id=" + userInfo.getId();
			JSONObject data = new JSONObject();
			data.put("first",
					SmsUtils.setMsgValue("尊敬的[" + userInfo.getUser_name() + "]您好，恭喜你已成为" + Keys.app_name + "的会员"));
			data.put("cardNumber", SmsUtils.setMsgValue(userInfo.getUser_no()));
			data.put("type", SmsUtils.setMsgValue(Keys.app_name));
			data.put("address", SmsUtils.setMsgValue(Keys.app_name));
			data.put("vipName", SmsUtils.setMsgValue(userInfo.getReal_name()));
			data.put("vipPhone", SmsUtils.setMsgValue(userInfo.getMobile()));
			data.put("expDate", SmsUtils.setMsgValue(sdFormat_ymdhms_china.format(userInfo.getCard_end_date())));
			data.put("remark", SmsUtils.setMsgValue("详情进入我的会员卡查看"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_vipFrist, url_template, data);
		}
	}

	/**
	 * @desc 会员到期提醒
	 */
	public static void vipEnd(UserInfo userInfo) {

		// 详细内容：{{first}}
		// 你的{{name}}有效期至{{expDate}}
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyCard.do?id=" + userInfo.getId();
			JSONObject data = new JSONObject();
			data.put(
					"first",
					SmsUtils.setMsgValue("尊敬的会员[" + userInfo.getUser_name() + "]您好，您的" + Keys.app_name
							+ "的会员即将到期，请尽快续费"));
			data.put("name", SmsUtils.setMsgValue("你的" + Keys.app_name + "的会员有效期至"));
			data.put("expDate", SmsUtils.setMsgValue(sdFormat_ymdhms_china.format(userInfo.getCard_end_date())));
			data.put(
					"remark",
					SmsUtils.setMsgValue("你的" + Keys.app_name + "的会员有效期至"
							+ sdFormat_ymdhms_china.format(userInfo.getCard_end_date())));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_vipEnd, url_template, data);
		}
	}

	/**
	 * @desc 商品审核成功
	 */
	public static void goodsAuditSuccess(CommInfo commInfo, UserInfo userInfo) {

		// 详细内容：{{first}}
		// 商品名称：{ keyword1 }
		// 审核时间：{ keyword2 }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = null;
			JSONObject data = new JSONObject();
			data.put("first", SmsUtils.setMsgValue("您的商品通过审核，请及时进店查看"));
			data.put("keyword1", SmsUtils.setMsgValue(commInfo.getComm_name()));
			data.put("keyword2", SmsUtils.setMsgValue(sdFormat_ymdhms.format(commInfo.getAdd_date())));
			data.put("remark", SmsUtils.setMsgValue("详情请前往你的店铺查看"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_goodsAuditSuccess, url_template,
					data);
		}
	}

	/**
	 * @desc 商品审核失败
	 */
	public static void goodsAuditFailure(CommInfo commInfo, UserInfo userInfo) {

		// 详细内容：{{first}}
		// 商品名称：{ keyword1 }
		// 失败原因：{ keyword2 }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = null;
			JSONObject data = new JSONObject();
			data.put("first", SmsUtils.setMsgValue("您的商品未通过审核，请及时查看"));
			data.put("keyword1", SmsUtils.setMsgValue(commInfo.getComm_name()));
			data.put("keyword2", SmsUtils.setMsgValue(commInfo.getAudit_desc()));
			data.put("remark", SmsUtils.setMsgValue("详情请前往你的店铺查看"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_goodsAuditFailure, url_template,
					data);
		}
	}

	/**
	 * @desc 实名认证审核
	 */
	public static void realNameAudit(UserInfo userInfo, BaseAuditRecord record) {
		// 详细内容：{{first}}
		// 审核状态：{ keyword1 }
		// 操作时间：{ keyword2 }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMySecurityCenter.do?id=" + userInfo.getId();
			JSONObject data = new JSONObject();
			data.put("first", SmsUtils.setMsgValue("尊敬的【" + userInfo.getReal_name() + "】你好，你的实名认证已通过，请及时查看"));
			if (null != record) {
				String AuditState = null;
				if (record.getAudit_state() == 0) {
					AuditState = "待审核";
				} else if (record.getAudit_state() == 1) {
					AuditState = "审核通过";
				} else if (record.getAudit_state() == -1) {
					AuditState = "审核不通过";
				}
				data.put("keyword1", SmsUtils.setMsgValue(AuditState));
				data.put("keyword2", SmsUtils.setMsgValue(sdFormat_ymdhms_china.format(record.getAudit_date())));
				data.put("remark", SmsUtils.setMsgValue("详情可进入我的实名认证中查看"));
				sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_realNameAudit, url_template,
						data);
			}

		}
	}

	/**
	 * @desc 退款申情驳回
	 */
	public static void refundMoneyFailure(OrderInfo orderInfo, UserInfo userInfo, BigDecimal money) {
		// {{first}}
		// 退款金额：{ orderProductPrice }
		// 商品详情：{ orderProductName }
		// 订单编号：{ orderName }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMySecurityCente.do?id=" + userInfo.getId();
			JSONObject data = new JSONObject();
			data.put("first", SmsUtils.setMsgValue("尊敬的【" + userInfo.getUser_name() + "】你好,你的退款申请被商家驳回，请与商家协商沟通解决"));
			data.put("orderProductPrice", SmsUtils.setMsgValue(money.toString()));
			data.put("orderProductName", SmsUtils.setMsgValue(""));
			data.put("orderName", SmsUtils.setMsgValue(orderInfo.getTrade_index()));
			data.put("remark", SmsUtils.setMsgValue("详情请与店家沟通"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_refundMoneyFailure, url_template,
					data);
		}
	}

	/**
	 * @desc 退款到账
	 * @param orderInfo 订单信息
	 * @param userInfo 买家
	 */
	public static void refund(OrderInfo orderInfo, UserInfo userInfo, BigDecimal money) {

		// 详细内容：{{first}}
		// 退款金额：{ orderProductPrice }
		// 商品信息：{ orderProductName }
		// 订单编号：{ orderName }
		// {{remark}}
		if (null != userInfo && StringUtils.isNotBlank(userInfo.getAppid_weixin())) {
			String url_template = "http://" + Keys.app_domain + "/m/MMyQianBao.do?id=" + userInfo.getId();
			JSONObject data = new JSONObject();
			data.put("first", SmsUtils.setMsgValue("尊敬的会员[" + userInfo.getUser_name() + "]您好，您有一笔退款已经到账"));
			data.put("orderProductPrice", SmsUtils.setMsgValue(money.toString()));
			data.put("orderProductName", SmsUtils.setMsgValue(""));
			data.put("orderName", SmsUtils.setMsgValue(orderInfo.getTrade_index()));
			data.put("remark", SmsUtils.setMsgValue("详情进入我的钱包查看"));
			sendWeixinTemplateMsg(userInfo.getAppid_weixin(), WEIXINSMS.weixin_sms_refund, url_template, data);
		}
	}

	public static JSONObject sendWeixinTemplateMsg(String toUser, String template_id, String url_template,
			JSONObject data) {
		try {
			JSONObject rep = thisSendTemplateMsgGroup(toUser, template_id, url_template, data);
			logger.warn("sendWeixinTemplateMsg INFO: " + rep.toString());
			return rep;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.warn("sendWeixinTemplateMsg HTTP ERROR: " + e.getMessage());
			return null;
		}
	}

	public static JSONObject thisSendTemplateMsgGroup(String toUser, String template_id, String url_template,
			JSONObject data) {
		JSONObject req = new JSONObject();
		req.put("touser", toUser);
		req.put("topcolor", "#FF0000");
		req.put("template_id", template_id);
		req.put("url", url_template);
		req.put("data", data);

		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + getWeixinToKenBase();
		JSONObject resp = HttpClientUtils.postJsonDataForJsonResult(url, req.toString());
		return resp;
	}
}
