package com.ebiz.webapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wu,Yang
 */
public class GetHtmlTools {
	private static final Logger logger = LoggerFactory.getLogger(GetHtmlTools.class);

	/**
	 * 网页抓取方法
	 * 
	 * @param urlString 要抓取的url地址,普通方法
	 * @param charset 网页编码方式
	 * @param timeout 超时时间
	 * @return 抓取的网页内容
	 * @throws IOException 抓取异常
	 */
	public static String GetWebContent(String urlString, final String charset, int timeout) throws IOException {
		if ((urlString == null) || (urlString.length() == 0)) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString
				: ("http://" + urlString).intern();
		URL url = new URL(urlString);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");// 增加报头，模拟浏览器，防止屏蔽
		conn.setRequestProperty("Accept", "text/html");// 只接受text/html类型，当然也可以接受图片,pdf,*/*任意，就是tomcat/conf/web里面定义那些

		conn.setConnectTimeout(timeout);
		try {
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		reader.close();
		conn.disconnect();
		return sb.toString();

	}

	/**
	 * @desc 从网页抓取新蛋网品牌
	 * @param url 抓取网页的url地址
	 * @return 插入数据库的语句
	 */
	public static void GetNeweggBrandInfo(String url) {
		Document doc;
		if (StringUtils.isBlank(url)) {
			url = "http://www.newegg.com.cn/BrandList.htm";
		}
		try {
			doc = Jsoup.connect(url).get();
			// Elements ems = doc.getElementsByTag("em");
			Elements ems = doc.select("em");
			StringBuffer sb = new StringBuffer();
			for (Element em : ems) {
				// String linkHref = link.attr("href");
				String emText = StringUtils.trimToEmpty(em.text());
				emText = StringUtils.replace(emText, "'", "''");
				emText = StringUtils.replace(emText, "&", "''&''");
				emText = StringUtils.replace(emText, "  ", "");
				String sql = "insert into base_brand_info (BRAND_ID, BRAND_NAME) values (seq_brand.nextval, trim('"
						.concat(emText).concat("'));\n");
				if (!StringUtils.contains(emText, "�")) {
					sb.append(sql);
				} else {
					System.out.println(sql);
				}

			}
			FileUtils.writeStringToFile(new File("E://sql.sql"), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// String xx = "ASUS华硕  ";
		// System.out.println(StringUtils.trim(xx));
		// System.out.println(StringUtils.trimToEmpty(xx));
		// System.out.println(StringUtils.replace(xx, "  ", ""));
	}

	// public static void run(Facade facade) {
	// GetHtmlTools ght = new GetHtmlTools();
	// ght.get360BuyAttrInfo(facade, "http://www.360buy.com/products/737-794-870-0-0-0-0-0-0-0-1-1-1-1-72-33.html",
	// null);
	// }
	public static void main(String args[]) throws IOException {
		String url = "http://www.nmgzfcg.gov.cn";
		Document doc;
		if (StringUtils.isBlank(url)) {
			url = "http://www.newegg.com.cn/BrandList.htm";
		}
		try {
			doc = Jsoup.connect(url).get();
			System.out.print(doc.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
