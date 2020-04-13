package com.ebiz.webapp.web.struts.allinpay;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aiisen.weixin.pay.util.PayUtil;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.web.Keys;

public class SybPayService {

	private static Logger logger = LoggerFactory.getLogger(SybPayService.class);

	public static Map<String, String> pay(String trxamt, String reqsn, String paytype, String acct, String sub_appid,
			String notify_url, String remark) throws Exception {

		HttpConnectionUtil http = new HttpConnectionUtil(Keys.allinpay_url + "/pay");
		http.init();

		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("cusid", Keys.allinpay_cusid);
		params.put("appid", Keys.allinpay_appid);
		if (StringUtils.isNotBlank(acct)) {
			params.put("acct", acct);
		}
		params.put("trxamt", trxamt);
		params.put("reqsn", reqsn);
		params.put("paytype", paytype);
		params.put("randomstr", StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
		params.put("remark", remark);
		params.put("notify_url", notify_url);
		params.put("sub_appid", sub_appid);
		params.put("sign", SybUtil.sign(params, Keys.allinpay_key));

		byte[] bys = http.postParams(params, true);
		String result = new String(bys, "UTF-8");

		return handleResult(result);
	}

	/**
	 * @param trxamt 退款金额
	 * @param reqsn 商户退款订单号
	 * @param oldtrxid 原交易流水 通联的
	 * @param oldreqsn 原交易订单号 我们的
	 * @param sysOperLogDao
	 * @return
	 * @throws Exception
	 */
	public static int cancel(String trxamt, String reqsn, String oldtrxid, String oldreqsn, SysOperLogDao sysOperLogDao) {

		int i = 0;

		if (StringUtils.isBlank(trxamt)) {
			return i;
		}

		HttpConnectionUtil http = new HttpConnectionUtil(Keys.allinpay_url + "/cancel");
		try {
			http.init();

			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("cusid", Keys.allinpay_cusid);
			params.put("appid", Keys.allinpay_appid);
			params.put("version", "11");
			params.put("trxamt", PayUtil.yuanToFee(trxamt));
			params.put("reqsn", reqsn);
			params.put("oldtrxid", oldtrxid);
			params.put("oldreqsn", oldreqsn);
			params.put("randomstr", StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
			params.put("sign", SybUtil.sign(params, Keys.allinpay_key));
			byte[] bys = http.postParams(params, true);
			String result = new String(bys, "UTF-8");
			Map<String, String> map = handleResult(result);

			Map resultMap = SybUtil.json2Obj(result, Map.class);
			if ("SUCCESS".equals(resultMap.get("retcode"))) {
				if ("0000".equals(resultMap.get("trxstatus"))) {
					i = 1;
					SysOperLog log = new SysOperLog();
					log.setIs_del(0);
					log.setLink_id(0);
					log.setOper_time(new Date());
					log.setOper_type(Keys.SysOperType.SysOperType_90.getIndex());
					log.setOper_name(Keys.SysOperType.SysOperType_90.getName() + "-通联");
					log.setOper_uid(1);
					log.setOper_name("系统管理员");
					log.setOper_memo(oldtrxid + "," + oldreqsn + "," + trxamt + "," + resultMap.get("trxstatus") + ","
							+ resultMap.get("errmsg"));
					sysOperLogDao.insertEntity(log);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			i = 0;
		}
		logger.info("===================" + i);
		return i;
	}

	/**
	 * @param trxamt 退款金额
	 * @param reqsn 商户退款订单号
	 * @param oldtrxid 原交易流水 通联的
	 * @param oldreqsn 原交易订单号 我们的
	 * @param sysOperLogDao
	 * @return
	 * @throws Exception
	 */
	public static int refund(String trxamt, String reqsn, String oldtrxid, String oldreqsn, SysOperLogDao sysOperLogDao) {

		int i = 0;

		if (StringUtils.isBlank(trxamt)) {
			return i;
		}

		HttpConnectionUtil http = new HttpConnectionUtil(Keys.allinpay_url + "/refund");
		try {
			http.init();

			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("cusid", Keys.allinpay_cusid);
			params.put("appid", Keys.allinpay_appid);
			params.put("version", "11");
			params.put("trxamt", PayUtil.yuanToFee(trxamt));
			params.put("reqsn", reqsn);
			params.put("oldreqsn", oldreqsn);
			params.put("oldtrxid", oldtrxid);
			params.put("randomstr", StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
			params.put("sign", SybUtil.sign(params, Keys.allinpay_key));
			byte[] bys = http.postParams(params, true);
			String result = new String(bys, "UTF-8");

			Map<String, String> map = handleResult(result);
			Map resultMap = SybUtil.json2Obj(result, Map.class);

			if ("SUCCESS".equals(resultMap.get("retcode"))) {
				if ("0000".equals(resultMap.get("trxstatus"))) {
					i = 1;
					SysOperLog log = new SysOperLog();
					log.setIs_del(0);
					log.setLink_id(0);
					log.setOper_time(new Date());
					log.setOper_type(Keys.SysOperType.SysOperType_90.getIndex());
					log.setOper_name(Keys.SysOperType.SysOperType_90.getName() + "-通联");
					log.setOper_uid(1);
					log.setOper_name("系统管理员");
					log.setOper_memo(oldtrxid + "," + oldreqsn + "," + trxamt + "," + resultMap.get("trxstatus") + ","
							+ resultMap.get("errmsg"));
					sysOperLogDao.insertEntity(log);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			i = 0;
		}

		return i;
	}

	public static Map<String, String> handleResult(String result) throws Exception {
		Map map = SybUtil.json2Obj(result, Map.class);
		if (map == null) {
			throw new Exception("返回数据错误");
		}
		if ("SUCCESS".equals(map.get("retcode"))) {
			TreeMap tmap = new TreeMap();
			tmap.putAll(map);
			String sign = tmap.remove("sign").toString();
			String sign1 = SybUtil.sign(tmap, Keys.allinpay_key);
			if (sign1.toLowerCase().equals(sign.toLowerCase())) {
				return map;
			} else {
				throw new Exception("验证签名失败");
			}
		} else {
			throw new Exception(map.get("retmsg").toString());
		}
	}

	public static String postParams(Map<String, String> params) throws Exception {
		StringBuilder outBuf = new StringBuilder();
		boolean isNotFirst = false;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (isNotFirst)
				outBuf.append('&');
			isNotFirst = true;
			outBuf.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}

		return outBuf.toString();
	}

}
