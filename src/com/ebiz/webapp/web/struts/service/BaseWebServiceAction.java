package com.ebiz.webapp.web.struts.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.web.struts.BasePayAction;

public class BaseWebServiceAction extends BasePayAction {

	// ios这里传入的datas是一个json，不一样
	public String returnJsoupInfo(HttpServletResponse response, String ret, String msg, Object datas, String callback)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("code", ret);
		json.put("msg", msg);
		json.put("datas", datas);
		String jsonsring = json.toJSONString();
		logger.info("jsonsring:{}", jsonsring);
		super.renderJson(response, callback + "(" + jsonsring + ")");
		return null;
	}

	public String returnInfoForIos(HttpServletResponse response, String ret, String msg, Object datas) throws Exception {
		JSONObject json = new JSONObject();
		json.put("ret", ret);
		json.put("msg", msg);
		json.put("datas", datas);
		String jsonsring = json.toJSONString();
		logger.info("jsonsring:{}", jsonsring);
		super.renderJson(response, jsonsring);
		return null;
	}

	/**
	 * @param plainText 明文
	 * @return 32位密文
	 */
	public String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (byte element : b) {
				i = element;
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
}
