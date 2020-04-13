package com.ebiz.webapp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wu,Yang
 * @desc 获取快递单号物流信息
 * @API http://www.kuaidi100.com/openapi/api_2_02.shtml
 */
public class DeliveryUtils {
	protected static final Logger logger = LoggerFactory.getLogger(DeliveryUtils.class);

	private static final String ENCODING_UTF8 = "UTF-8";

	private static final String ENCODING_GBK = "GBK";

	private static final String KUAIDI100_KEY = "ca79104c23465575";

	private static final String ICKD_ID = "105619";

	private static final String ICKD_SECRET = "d2ed76801ef974e9827732f83e2d8785";

	/**
	 * @param kuaidi_comp 快递公司代码
	 * @param kuaidi_no 快递单号
	 * @param show 返回类型： 0：返回json字符串， 1：返回xml对象， 2：返回html对象， 3：返回text文本。 如果不填，默认返回json字符串。
	 * @param order 排序desc|asc，默认逆序desc
	 */
	public static String getKuaiDi100Info1(String kuaidi_comp, String kuaidi_no) throws Exception {
		InputStream is = null;
		try {
			String show = "3";
			String order = "desc";
			String url = "http://api.kuaidi100.com/api?id=" + KUAIDI100_KEY + "&com=" + kuaidi_comp + "&nu=" + kuaidi_no
					+ "&show=" + show + "&muti=1&order=" + order + "";
			logger.info("==url:{}", url);
			// HttpClient client = new DefaultHttpClient();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, ENCODING_UTF8));
			StringBuffer result = new StringBuffer();
			String string = null;
			if (null != (string = reader.readLine())) {
				result.append(string);
			}
			String result_string = result.toString();
			if ("2".equals(show)) {
				result_string = StringUtils.replace(result_string, "#64AADB", "#A8A9AA");
				result_string = StringUtils.replace(result_string, "#75C2EF", "#DDDDDD");
			}
			logger.info("result: " + result.toString());
			return result_string;
		} finally {
			// close stream here
			is.close();
		}
	}

	public static String getKuaiDi100Info(String kuaidi_comp, String kuaidi_no, String show, String order)
			throws Exception {
		InputStream is = null;
		try {
			String url = "http://api.kuaidi100.com/api?id=" + KUAIDI100_KEY + "&com=" + kuaidi_comp + "&nu=" + kuaidi_no
					+ "&show=" + show + "&muti=1&order=" + order + "";
			logger.info("==url:{}", url);
			// HttpClient client = new DefaultHttpClient();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, ENCODING_UTF8));
			StringBuffer result = new StringBuffer();
			String string = null;
			if (null != (string = reader.readLine())) {
				result.append(string);
			}
			String result_string = result.toString();
			if ("2".equals(show)) {
				result_string = StringUtils.replace(result_string, "#64AADB", "#A8A9AA");
				result_string = StringUtils.replace(result_string, "#75C2EF", "#DDDDDD");
			}
			logger.info("result: " + result.toString());
			return result_string;
		} finally {
			// close stream here
			is.close();
		}
	}

	public static String getKuaiDi100Info(String kuaidi_comp, String kuaidi_no) throws Exception {
		return getKuaiDi100Info(kuaidi_comp, kuaidi_no, "2", "asc");
	}

	public static String getKuaiDi100InfoForUrl(String kuaidi_comp, String kuaidi_no) throws Exception {

		InputStream is = null;

		kuaidi_comp = StringUtils.trim(kuaidi_comp);
		kuaidi_no = StringUtils.trim(kuaidi_no);
		// String result_string = "https://m.kuaidi100.com/index_all.html?type=" + kuaidi_comp + "&postid=" + kuaidi_no;
		String result_string = "https://www.kuaidi100.com/chaxun?com=" + kuaidi_comp + "&nu=" + kuaidi_no;
		return result_string;
//		try {
//			String url = "http://api.kuaidi100.com/applyurl?key=" + KUAIDI100_KEY + "&com=" + kuaidi_comp + "&nu="
//					+ kuaidi_no;
//			logger.warn("==getKuaiDi100InfoForUrl:{}", url);
//			// HttpClient client = new DefaultHttpClient();
//			CloseableHttpClient client = HttpClients.createDefault();
//			HttpGet httpGet = new HttpGet(url);
//			HttpResponse response = client.execute(httpGet);
//			is = response.getEntity().getContent();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, ENCODING_UTF8));
//			StringBuffer result = new StringBuffer();
//			String string = null;
//			if (null != (string = reader.readLine())) {
//				result.append(string);
//			}
//			String result_string = result.toString();
//			logger.info("==getKuaiDi100InfoForUrl result_string:{}", result_string);
//			return result_string;
//		} finally {
//			if (null != is) {
//				is.close();
//			}
//		}
	}

	/**
	 * @param kuaidi_comp 快递公司代码
	 * @param kuaidi_no 快递单号
	 * @param type 返回类型： 值分别为 html | json（默认） | text | xml
	 * @param order 排序desc|asc，默认逆序desc
	 */
	public static String getIckdInfo(String kuaidi_comp, String kuaidi_no, String type, String order) throws Exception {
		InputStream is = null;
		try {
			String url = "http://api.ickd.cn/?id=" + ICKD_ID + "&secret=" + ICKD_SECRET + "&com=" + kuaidi_comp + "&nu="
					+ kuaidi_no + "&type=" + type + "&encode=gbk&ord=" + order + "";
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, ENCODING_GBK));
			StringBuffer result = new StringBuffer();
			String string = null;
			if (null != (string = reader.readLine())) {
				System.out.println("string: " + string);
				result.append(string);
			}
			String result_string = result.toString();
			if ("html".equals(type)) {
				result_string = StringUtils.replace(result_string, "<th>时间</th>", "<th width=\"28%\">时间</th>");
			}
			// System.out.println("result: " + result.toString());
			return result_string;
		} finally {
			// close stream here
			is.close();
		}
	}

	public static String getIckdInfo(String kuaidi_comp, String kuaidi_no) throws Exception {
		return getIckdInfo(kuaidi_comp, kuaidi_no, "html", "asc");
	}

	public static void main(String[] args) throws Exception {
		String kuaidi_comp = "yunda";
		String kuaidi_no = "1600395960022";
		String result = DeliveryUtils.getIckdInfo(kuaidi_comp, kuaidi_no);
		System.out.println("result: " + result);
	}

}
