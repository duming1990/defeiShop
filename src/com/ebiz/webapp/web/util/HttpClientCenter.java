package com.ebiz.webapp.web.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient 使用 1.GET/POST（HEAD,PUT） 2.Proxy 3.Https 4.Cookie 5.字符编码 6.支持自动转向
 * 
 * @author Administrator
 */
public class HttpClientCenter {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientCenter.class);

	static HttpClient httpClient = null;

	// 构造HttpClient的实例
	private static HttpClientCenter instance;

	private HttpClientCenter() {
		httpClient = new HttpClient();
	}

	public static HttpClientCenter getInstance() {
		if (instance == null) {
			return new HttpClientCenter();
		}
		return instance;
	}

	// Https

	/**
	 * 设置代理 代理服务器是位于客户端和Web服务器之间一个中间服务器。我们通过使用浏览器中配置的相应代理， 将请求发送到代理服务器，代理服务器代理我们客户端去请求Web服务器，将请求到的响应资源再次
	 * 响应我们客户端，同时缓存到代理服务器上，以备下次客户端请求代理服务器的时候，直接从代理服 务器上取得要请求的资源，而无需再次请求网站的Web服务器，提高了响应速度。
	 */

	/**
	 * GET请求 使用 HttpClient 需要以下 6 个步骤： 1. 创建 HttpClient 的实例 2. 创建某种连接方法的实例，在这里是 GetMethod。在 GetMethod 的构造函数中传入待连接的地址 3.
	 * 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例 4. 读 response 5. 释放连接。无论执行方法是否成功，都必须释放连接 6. 对得到后的内容进行处理
	 * 
	 * @param url
	 */
	public void getMethod(String url) {
		GetMethod getMethod = null;
		try {
			URI uri = new URI(url, true);
			getMethod = new GetMethod(url);// 创建GET方法的实例,可同时对请求的头,等等，做相应的设置
			/** 设置代理 */
			HostConfiguration hostConfiguration = new HostConfiguration();
			hostConfiguration.setHost(uri);
			hostConfiguration.setProxy("127.0.0.1", 8080);// 配置代理IP和端口
			httpClient.setHostConfiguration(hostConfiguration);

			// 使用系统提供的默认的恢复策略
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
			managerParams.setConnectionTimeout(30000); // 设置连接超时时间(单位毫秒)
			managerParams.setSoTimeout(120000); // 设置读数据超时时间(单位毫秒)
			int statusCode = httpClient.executeMethod(getMethod);// 执行getMethod,返回响应码
			if (statusCode != HttpStatus.SC_OK) {
				logger.warn("Method failed: " + getMethod.getStatusLine()); // Method
																			// failed:
																			// HTTP/1.1
																			// 200
																			// OK
			}
			String charSet = getMethod.getResponseCharSet(); // 响应的字符编码
			byte[] responseBody = getMethod.getResponseBody(); // 读取内容

			logger.info("responseCode:\r\n" + statusCode);
			logger.info("charSet:\r\n" + charSet);
			logger.info("response:\r\n" + new String(responseBody, charSet));

			Header headers[] = getMethod.getResponseHeaders();
			for (int i = 0; i < headers.length; i++) {
				logger.info("headers:" + headers[i]);
			}
			/*
			 * Date: Mon, 21 Dec 2009 16:34:06 GMT Content-Type: text/vnd.wap.wml; charset=utf-8 Transfer-Encoding:
			 * chunked Connection: keep-alive Server: Resin/3.1.0 Cache-Control: no-cache Expires: Thu, 01 Dec 1994
			 * 16:00:00 GMT Set-Cookie: JSESSIONID=abc8cwOo6fWhcf2uQfYws; path=/ P3P:
			 * policyref="http://www.500wan.com/w3c/P3P.xml" CP="NOI ADM DEV PSAi COM NAV OUR OTR STP IND DEM"
			 */

		} catch (HttpException e) {
			e.printStackTrace();// 发生致命的异常，可能是协议不对或者返回的内容有问题
		} catch (IOException e) {
			e.printStackTrace();// 发生网络异常
		} finally {
			getMethod.releaseConnection();// 释放连接
		}
	}

	public String postMethod(String url, Map<Object, Object> datas) {
		PostMethod postMethod = new PostMethod(url);
		/** 设置代理---同上GET方法 */
		String responseBody = "";
		int params = datas.size();
		NameValuePair[] data = new NameValuePair[params];
		int i = 0;

		try {
			for (Object obj : datas.keySet()) {
				data[i++] = new NameValuePair((String) obj, (String) datas.get(obj));
			}
			// NameValuePair[] data = {new NameValuePair("id", "youUserName"),
			// new NameValuePair("passwd", "yourPwd")
			// };//填入各个表单域的值
			// postMethod.setRequestEntity(new
			// StringRequestEntity("createRequestXML()", "text/xml", "GBK"));
			// 可使用XML格式传输post参数
			postMethod.setRequestBody(data);// 将表单的值放入postMethod中

			HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
			managerParams.setConnectionTimeout(30000); // 设置连接超时时间(单位毫秒)
			managerParams.setSoTimeout(120000); // 设置读数据超时时间(单位毫秒)
			int statusCode = httpClient.executeMethod(postMethod);// 执行postMethod
			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {// 301或者302
				Header locationHeader = postMethod.getResponseHeader("location");// 从头中取出转向的地址
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					logger.info("The page was redirected to:" + location);
				} else {
					logger.warn("Location field value is null.");
				}
				return responseBody;
			} else if (statusCode == HttpStatus.SC_OK) {
				responseBody = postMethod.getResponseBodyAsString();
			}
		} catch (IOException e) {
			logger.warn("postMethod error:" + e.getMessage());
		}
		return responseBody;
	}

	public void cookieSet() {
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);// RFC_2109是支持较普遍的一个，还有其他cookie协议
		HttpState initialState = new HttpState();
		Cookie cookie = new Cookie();
		cookie.setDomain("www.balblabla.com");
		cookie.setPath("/");
		cookie.setName("多情环");
		cookie.setValue("多情即无情");
		initialState.addCookie(cookie);
		httpClient.setState(initialState);
	}

	public void cookieGet() {
		Cookie[] cookies = httpClient.getState().getCookies();
		logger.info("Present cookies: ");
		for (int i = 0; i < cookies.length; i++) {
			logger.info(" - " + cookies[i].toExternalForm());
			logger.info(" - domain=" + cookies[i].getDomain());
			logger.info(" - path=" + cookies[i].getPath());
		}
	}

	// public static void main(String args[]) {
	// HttpClientCenter hcc = HttpClientCenter.getInstance();
	// String url = "https://shishou.fupin.chinammt.com/m/WebService.do";
	// // hcc.getMethod(url);
	// Map<Object, Object> datas = new HashMap<Object, Object>();
	// datas.put("method", "login");
	// datas.put("login_name", "admin");
	// datas.put("password", "123456");
	//
	// String responseBody = hcc.postMethod(url, datas);
	// logger.info("==responseBody:" + responseBody);
	// }
}