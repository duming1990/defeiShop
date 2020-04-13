package com.ebiz.webapp.web.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeUtility;

import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebiz.webapp.web.Keys;

public class EmailUtils {

	protected static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	public static final String api_user = "API_USER_YOOHOO";

	public static final String api_key = "UzkoH1V64UKqgwHF";

	public static final String from = "<92yoohoo@92yoohoo.cn>";

	public static void sendEmail(String title, String content, String to) {

		String url = "https://sendcloud.sohu.com/webapi/mail.send.xml";
		// HttpClient httpclient = new DefaultHttpClient();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(url);
		List nvps = new ArrayList();
		nvps.add(new BasicNameValuePair("api_user", api_user)); // # 使用api_user和api_key进行验证
		nvps.add(new BasicNameValuePair("api_key", api_key));
		String tfrom = from ;
		try {//设置发件人中文别名
			tfrom  =  MimeUtility.encodeText(Keys.app_name, "UTF-8", "B") + from;
		} catch (UnsupportedEncodingException e1) {
			tfrom = from;
		}// #
		nvps.add(new BasicNameValuePair("from",tfrom ));
		nvps.add(new BasicNameValuePair("to", to)); // # 收件人地址，用正确邮件地址替代，多个地址用';'分隔
		nvps.add(new BasicNameValuePair("subject", title));
		nvps.add(new BasicNameValuePair("html", content));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
				logger.info("EamilUtils:" + EntityUtils.toString(response.getEntity()));
			} else {
				logger.info("EamilUtils:error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		EmailUtils.sendEmail("众汇消费增值服务网邮箱验证", "尊敬的用户，邮箱验证码为223355(客服绝不会索取此验证码，切勿告知他人)，请在页面中输入以完成验证。", "68851854@qq.com");
	}
}
