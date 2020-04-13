package com.ebiz.webapp.web.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ebiz.ssi.util.EncryptUtils;

public class ServiceUtils {

	private static final String DIVIDEKEY = "SMART";

	private static final String ENCODEKEY = "smArt";

	private static String xmlString = "";

	private static HttpClient httpClient = null;

	private static HttpClient getHttpClient() {
		if (httpClient == null) {
			HttpParams params = new BasicHttpParams();
			// Increase max total connection to 200
			ConnManagerParams.setMaxTotalConnections(params, 200);
			// Increase default max connection per route to 20
			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
			// Increase max connections for localhost:80 to 50
			HttpHost localhost = new HttpHost("locahost", 80);
			connPerRoute.setMaxForRoute(new HttpRoute(localhost), 50);
			ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);

			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

			ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
			httpClient = new DefaultHttpClient(cm, params);
		}
		return httpClient;
	}

	public static final byte[] myEncode(byte[] s, int key) throws UnsupportedEncodingException {
		String str = Integer.toHexString(key);

		for (int i = 0; i < (8 - str.length()); i++) {
			str = '0' + str;
		}

		byte[] bkey = HexString2Bytes(str);
		byte[] ikey = new byte[4];
		for (int i = 0; i < 4; i++) {
			ikey[i] = (byte) (bkey[3 - i] & 0xFF);
		}

		for (int i = 0; i < s.length; i++) {
			s[i] = (byte) ((byte) (s[i] & 0xFF) ^ ikey[(i + 1) % 4]);
		}

		return s;

	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0 byte
	 * @param src1 byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
	 * 
	 * @param src String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[4];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 4; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static int GetXORKey(String key) {
		int result = (int) (0x35894715);
		for (int i = 0; i < key.length(); i++) {
			result = result + (key.charAt(i) << (i + 1));
		}
		return result;
	}

	public static byte[] SplitToBytes(String str) {
		String[] strs = str.split(DIVIDEKEY);
		byte[] bbs = new byte[strs.length];
		for (int i = 0; i < strs.length; i++) {// 输出结果
			bbs[i] = Byte.valueOf(strs[i]).byteValue();
		}
		return bbs;
	}

	private static HttpResponse executePostMethod(String url, List<NameValuePair> formparams, String charset) {
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charset);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			HttpResponse response = getHttpClient().execute(httppost);
			return response;
		} catch (UnsupportedEncodingException e) {
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}

	private static HttpResponse executeGetMethod(String url, HashMap<String, String> qparams, String charset) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : qparams.keySet()) {
			params.add(new BasicNameValuePair(key, qparams.get(key)));
		}
		try {
			URI qurl = URI.create(url);
			URI uri = URIUtils.createURI(qurl.getScheme(), qurl.getHost(), qurl.getPort(), qurl.getPath(),
					URLEncodedUtils.format(params, charset), null);
			HttpGet httpget = new HttpGet(uri);
			HttpResponse response = getHttpClient().execute(httpget);
			return response;
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (URISyntaxException e) {
		}
		return null;
	}

	public static String getResultJson(HashMap<String, Object> params) {
		String postUrl = params.get("postUrl") == null ? "" : params.get("postUrl").toString();
		Object paramp = params.get("p");
		String pdacode = params.get("pdacode") == null ? "" : params.get("pdacode").toString();
		String[] p = null;
		if (paramp instanceof String[]) {
			p = (String[]) paramp;
		}

		// Post提交数据
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();

		String ecrptinfo = StringUtils.join(p, "|");
		String key = ENCODEKEY + pdacode;
		try {
			byte[] encode1 = myEncode(ecrptinfo.getBytes("UTF-8"), GetXORKey(key));
			ecrptinfo = "";
			for (int i = 0; i < encode1.length; i++) {// 输出结果
				ecrptinfo += encode1[i];
				if (i != encode1.length - 1) {
					ecrptinfo += DIVIDEKEY;
				}
			}
		} catch (Exception e) {
			ecrptinfo = "";
			e.printStackTrace();
		}

		qparams.add(new BasicNameValuePair("info", ecrptinfo));
		HttpResponse response = executePostMethod(postUrl, qparams, "UTF-8");
		if (response != null && response.getEntity() != null) {
			try {
				String jsonEncrpt = EntityUtils.toString(response.getEntity());
				// 解密
				byte[] decode = myEncode(SplitToBytes(jsonEncrpt), GetXORKey(key));
				String jsonDecrpt = new String(decode, "UTF-8");
				String json = jsonDecrpt.replaceAll(":null", ":\"\"").replaceAll("null", "");
				return json;
			} catch (NumberFormatException e) {
				String json = "{\"return_flag\":\"false\",\"message\":\"090:exception:【"
						+ (e.getMessage() != null ? e.getMessage().substring(0, 200) : "") + "】！\"}";
				return json;
			} catch (IOException e) {
				String json = "{\"return_flag\":\"false\",\"message\":\"091:exception:【"
						+ (e.getMessage() != null ? e.getMessage().substring(0, 200) : "") + "】！\"}";
				return json;
			}
		} else {
			String json = "{\"return_flag\":\"0\",\"message\":\"093:接口无法访问！\"}";
			return json;
		}
	}

	private static String ToJsonEncrypt(Object object, HttpServletResponse response, HttpServletRequest request) {
		// String[] header = { "encoding:UTF-8" };
		String md = request.getParameter("md") == null ? "" : request.getParameter("md").toString();
		String key = ENCODEKEY + md;
		xmlString = JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(xmlString);

		// 加密字符串
		try {
			byte[] encode1 = myEncode(xmlString.getBytes("UTF-8"), GetXORKey(key));
			xmlString = "";
			for (int i = 0; i < encode1.length; i++) {// 输出结果
				xmlString += encode1[i];
				if (i != encode1.length - 1) {
					xmlString += DIVIDEKEY;
				}
			}
		} catch (Exception e) {
			xmlString = "001: 传输数据解析错误!";
			e.printStackTrace();
		}
		renderText(response, xmlString.toString());
		return null;
	}

	public static String ToJson(Object object, HttpServletResponse response) {
		xmlString = JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(xmlString);
		renderText(response, xmlString.toString());
		return null;
	}

	/**
	 * @param[map] json原始的map
	 * @param[result_code] 0 失败 1 成功
	 * @param[message] 返回提示的文本信息,如"000:成功!","001:没有相关信息!"
	 * @param[returnType] 返回值类型,如"json","jsonEncrypt"
	 */
	public static void ReturnInfo(Object map, String returnType, HttpServletResponse response,
			HttpServletRequest request) {
		if ("json".equals(returnType)) {
			ToJson(map, response);
		} else if ("jsonEncrypt".equals(returnType)) {
			ToJsonEncrypt(map, response, request);
		}
	}

	public static String getRequestpara(String paraName, HttpServletRequest request) {
		String transString = request.getParameter(paraName);
		// 解密
		try {
			String md = request.getParameter("md") == null ? "" : request.getParameter("md").toString();
			String key = ENCODEKEY + md;
			byte[] decode = myEncode(SplitToBytes(transString), GetXORKey(key));
			transString = new String(decode, "UTF-8");
		} catch (Exception e) {
			transString = "";
			e.printStackTrace();
		}
		return transString;
	}

	protected static void render(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
		}
	}

	protected static void renderJson(HttpServletResponse response, String text) {
		render(response, text, "application/json;charset=".concat("UTF-8"));
	}

	protected static void renderText(HttpServletResponse response, String text) {
		render(response, text, "text/plain;charset=".concat("UTF-8"));
	}

	protected static void renderXml(HttpServletResponse response, String text) {
		render(response, text, "text/xml;charset=".concat("UTF-8"));
	}

	protected static void renderJavaScript(HttpServletResponse response, String text) {
		String prefixJavaScript = "<script type=\"text/javascript\">";
		String suffixJavaScript = "</script>";
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">").append(text).append("</script>");
		render(response, sb.toString(), "text/html;charset=".concat("UTF-8"));
	}

	protected static void renderExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String hiddenHtml = StringUtils.lowerCase(request.getParameter("hiddenHtml"));

		hiddenHtml = StringUtils.replace(hiddenHtml, "border=0", "border=1");
		hiddenHtml = StringUtils.replace(hiddenHtml, "border=\"0\"", "border=\"1\"");

		String fname = EncryptUtils.encodingFileName(request.getParameter("hiddenName"));

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(hiddenHtml);

		out.flush();
		out.close();
	}

}
