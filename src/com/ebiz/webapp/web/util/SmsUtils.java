package com.ebiz.webapp.web.util;

import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aiisen.weixin.message.send.CustomMsgSender;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.web.Keys;

public class SmsUtils {

	protected static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

	public static final String uri = Keys.sms_url;

	public static final String account = Keys.sms_account;

	public static final String pswd = Keys.sms_password;

	// http://code.cl2009.com/

	public static String sendMessage(String message, String mobile) {

		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			logger.warn("==SMS msg: " + message);
			URI base = new URI(uri, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(true)), new NameValuePair("msg", (message)) });
			// URLEncoder.encode(message, "UTF-8")
			// logger.info("==SMS getQueryString: " + method.getQueryString());
			int result = client.executeMethod(method);
			if (result == 200) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				String str = URLDecoder.decode(baos.toString(), "UTF-8");
				logger.warn("==SMS str: " + str);
				String strs[] = str.split(",");
				for (String str2 : strs) {
					char strChrs[] = str2.toCharArray();
					for (char strChr : strChrs) {
						if (strChrs[0] == '0') {
							str = String.valueOf(strChrs[0]);
							break;
						}
					}
				}
				return str;
			}
			// logger.warn("==HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			return "1";
		} catch (Exception e) {
			// e.printStackTrace();
			logger.warn("SmsUtils HTTP ERROR: " + e.getMessage());
			return "1";
		} finally {
			method.releaseConnection();
		}
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.contains(ip, ",")) {
			String[] ips = StringUtils.split(ip, ",");
			if (ArrayUtils.isNotEmpty(ips)) {
				ip = ips[0];
			}
		}
		return ip;
	}

	public static String generateCheckPass() {
		Integer[] rands = new Integer[6];
		for (int i = 0; i < 6; i++) {
			int rand = (int) (Math.random() * 10);
			rands[i] = rand;
		}
		return StringUtils.join(rands, "");
	}

	public static JSONObject sendWeixinTemplateMsg(String toUser, String template_id, String url_template,
			JSONObject data) {
		try {
			JSONObject rep = CustomMsgSender.sendTemplateMsgGroup(toUser, template_id, url_template, data);
			logger.warn("sendWeixinTemplateMsg INFO: " + rep.toString());
			return rep;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.warn("sendWeixinTemplateMsg HTTP ERROR: " + e.getMessage());
			return null;
		}
	}

	public static JSONObject setMsgValue(String value) {
		JSONObject first = new JSONObject();
		first.put("value", value);
		first.put("color", "#173177");
		// first.put("color", "#2f9833");
		return first;
	}

}
