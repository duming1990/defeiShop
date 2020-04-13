package com.ebiz.webapp.web.struts.alipay;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author 刘志祥
 * @date 2018-8-8
 */
public class AlipayUtils extends BaseAction {
	protected static final Logger logger = LoggerFactory.getLogger(AlipayUtils.class);

	private static final String ALIPAY_GATEWAY_NEW = "https://openapi.alipay.com/gateway.do";

	/**
	 * @param out_trade_no 我们的平台订单号
	 * @param trade_no 支付宝的订单号
	 * @param out_request_no 我们的退款订单号
	 * @param total_fee 订单总金额 单位 元
	 * @param refund_amount 退款的金额 单位 元
	 * @param ctxdir BaseAction中getCtxPathForServer方法
	 * @return 1 正常 0异常
	 */
	public static int TuiKuan(String out_trade_no, String trade_no, String out_request_no, BigDecimal refund_amount,
			String ctxdir, SysOperLogDao sysOperLogDao) {
		int i = 0;

		if (null == refund_amount) {
			return i;
		}

		try {
			AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY_NEW, Keys.alipay_appid_app,
					Keys.alipay_private_key_app, "json", "utf-8", Keys.alipay_pubilc_key_app, "RSA2");

			AlipayTradeRefundModel model = new AlipayTradeRefundModel();
			model.setOutTradeNo(trade_no);
			model.setTradeNo(trade_no);
			model.setRefundAmount(refund_amount.toString());
			model.setOutRequestNo(out_request_no);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizModel(model);
			AlipayTradeRefundResponse response = alipayClient.execute(request);

			if (response.isSuccess()) {
				if (response.getCode().equals("10000")) {// 真的请求成功
					i = 1;
				}
			}
			SysOperLog log = new SysOperLog();
			log.setIs_del(0);
			log.setLink_id(0);
			log.setOper_time(new Date());
			log.setOper_type(Keys.SysOperType.SysOperType_90.getIndex());
			log.setOper_name(Keys.SysOperType.SysOperType_90.getName() + "-支付宝");
			log.setOper_uid(1);
			log.setOper_name("系统管理员");
			log.setOper_memo(out_trade_no + "," + trade_no + "," + out_request_no + "," + refund_amount + "," + "JSON:"
					+ response.getBody());
			sysOperLogDao.insertEntity(log);
		} catch (Exception e) {
			// TODO: handle exception

			logger.warn("==========AlipayUtilsError===========");

			e.printStackTrace();
			return i;
		}

		return i;
	}
}