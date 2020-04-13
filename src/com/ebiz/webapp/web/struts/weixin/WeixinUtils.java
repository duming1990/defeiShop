/**
 * 
 */
package com.ebiz.webapp.web.struts.weixin;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aiisen.weixin.pay.api.PayMchAPI;
import com.aiisen.weixin.pay.bean.paymch.SecapiPayRefund;
import com.aiisen.weixin.pay.bean.paymch.SecapiPayRefundResult;
import com.aiisen.weixin.pay.client.LocalHttpClient;
import com.aiisen.weixin.pay.util.PayUtil;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author 刘佳
 * @date: 2018年7月17日 下午5:20:11
 */
public class WeixinUtils extends BaseAction {

	protected static final Logger logger = LoggerFactory.getLogger(WeixinUtils.class);

	public static final String appid = Keys.APP_ID; // appid

	public static final String mch_id = Keys.MCH_ID; // 微信支付商户号

	public static final String key = Keys.API_KEY; // API密钥

	public static final String mp_appid = Keys.MP_APP_ID; // 赋能小程序appid

	public static final String mp_mch_id = Keys.MP_MCH_ID; // 赋能小程序关联微信支付商户号

	public static final String mp_key = Keys.MP_API_KEY; // 赋能小程序关联微信支付商户号API密钥

	/**
	 * @param out_refund_no 我们的退款订单号
	 * @param transaction_id 微信的订单号
	 * @param total_fee 订单总金额 单位 元
	 * @param refund_fee 退款的金额 单位 元
	 * @param ctxdir BaseAction中getCtxPathForServer方法
	 * @return 1 正常 0异常
	 */
	public static int TuiKuan(String out_refund_no, String transaction_id, BigDecimal total_fee, BigDecimal refund_fee,
			String ctxdir, SysOperLogDao sysOperLogDao) {
		int i = 0;

		if (null == total_fee || null == refund_fee) {
			return i;
		}

		try {

			SecapiPayRefund secapiPayRefund = new SecapiPayRefund();
			secapiPayRefund.setAppid(appid);
			secapiPayRefund.setMch_id(mch_id);
			String noncestr = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
			secapiPayRefund.setNonce_str(noncestr);
			secapiPayRefund.setOut_refund_no(out_refund_no);
			secapiPayRefund.setTransaction_id(transaction_id);
			secapiPayRefund.setTotal_fee(Integer.valueOf(PayUtil.yuanToFee(total_fee.toString())));
			secapiPayRefund.setRefund_fee(Integer.valueOf(PayUtil.yuanToFee(refund_fee.toString())));

			String keyStoreFilePath = ctxdir + "cert" + File.separator + "weixin" + File.separator
					+ "apiclient_cert.p12";
			LocalHttpClient.initMchKeyStore("PKCS12", keyStoreFilePath, mch_id, 100, 10);// 读取密钥

			SecapiPayRefundResult secapiPayRefundResult = PayMchAPI.secapiPayRefund(secapiPayRefund, key);

			if (secapiPayRefundResult.getReturn_code().equals("SUCCESS")) {// 提交成功
				// String ret = JSON.toJSONString(secapiPayRefundResult);
				if (secapiPayRefundResult.getResult_code().equals("SUCCESS")) {// 真的请求成功
					i = 1;
				}
			}
			SysOperLog log = new SysOperLog();
			log.setIs_del(0);
			log.setLink_id(0);
			log.setOper_time(new Date());
			log.setOper_type(Keys.SysOperType.SysOperType_90.getIndex());
			log.setOper_name(Keys.SysOperType.SysOperType_90.getName() + "-微信");
			log.setOper_uid(1);
			log.setOper_name("系统管理员");
			log.setOper_memo(transaction_id + "," + out_refund_no + "," + total_fee + "," + refund_fee + ","
					+ secapiPayRefundResult.getReturn_code() + "," + secapiPayRefundResult.getReturn_msg() + ","
					+ secapiPayRefundResult.getResult_code() + "," + secapiPayRefundResult.getErr_code() + ","
					+ secapiPayRefundResult.getErr_code_des());
			sysOperLogDao.insertEntity(log);
		} catch (Exception e) {
			// TODO: handle exception
			return i;
		}
		return i;
	}

	/**
	 * 从微信小程序支付的退款接口
	 * 
	 * @param out_refund_no 我们的退款订单号
	 * @param transaction_id 微信的订单号
	 * @param total_fee 订单总金额 单位 元
	 * @param refund_fee 退款的金额 单位 元
	 * @param ctxdir BaseAction中getCtxPathForServer方法
	 * @return 1 正常 0异常
	 */
	public static int TuiKuanForWeiXinMp(String out_refund_no, String transaction_id, BigDecimal total_fee,
			BigDecimal refund_fee, String ctxdir, SysOperLogDao sysOperLogDao) {
		int i = 0;

		if (null == total_fee || null == refund_fee) {
			return i;
		}

		try {

			SecapiPayRefund secapiPayRefund = new SecapiPayRefund();
			secapiPayRefund.setAppid(mp_appid);
			secapiPayRefund.setMch_id(mp_mch_id);
			String noncestr = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
			secapiPayRefund.setNonce_str(noncestr);
			secapiPayRefund.setOut_refund_no(out_refund_no);
			secapiPayRefund.setTransaction_id(transaction_id);
			secapiPayRefund.setTotal_fee(Integer.valueOf(PayUtil.yuanToFee(total_fee.toString())));
			secapiPayRefund.setRefund_fee(Integer.valueOf(PayUtil.yuanToFee(refund_fee.toString())));

			String keyStoreFilePath = ctxdir + "cert" + File.separator + "weixin/mp" + File.separator
					+ "apiclient_cert.p12";
			LocalHttpClient.initMchKeyStore("PKCS12", keyStoreFilePath, mp_mch_id, 100, 10);// 读取密钥

			SecapiPayRefundResult secapiPayRefundResult = PayMchAPI.secapiPayRefund(secapiPayRefund, mp_key);

			if (secapiPayRefundResult.getReturn_code().equals("SUCCESS")) {// 提交成功
				// String ret = JSON.toJSONString(secapiPayRefundResult);
				if (secapiPayRefundResult.getResult_code().equals("SUCCESS")) {// 真的请求成功
					i = 1;
				}
			}
			SysOperLog log = new SysOperLog();
			log.setIs_del(0);
			log.setLink_id(0);
			log.setOper_time(new Date());
			log.setOper_type(Keys.SysOperType.SysOperType_90.getIndex());
			log.setOper_name(Keys.SysOperType.SysOperType_90.getName() + "-微信");
			log.setOper_uid(1);
			log.setOper_name("系统管理员");
			log.setOper_memo(transaction_id + "," + out_refund_no + "," + total_fee + "," + refund_fee + ","
					+ secapiPayRefundResult.getReturn_code() + "," + secapiPayRefundResult.getReturn_msg() + ","
					+ secapiPayRefundResult.getResult_code() + "," + secapiPayRefundResult.getErr_code() + ","
					+ secapiPayRefundResult.getErr_code_des());
			sysOperLogDao.insertEntity(log);
		} catch (Exception e) {
			// TODO: handle exception
			return i;
		}
		return i;
	}
}
