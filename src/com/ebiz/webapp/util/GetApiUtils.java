package com.ebiz.webapp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetApiUtils {

	private static final String ENCODING = "GBK";

	public static String getApiWithUrl(String url) throws Exception {
		InputStream is = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, ENCODING));
			StringBuffer result = new StringBuffer();

			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}

			// System.out.println("result: " + result.toString());
			return result.toString();
		} finally {
			// close stream here
			if (null != is) {
				is.close();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// String url = "http://weather.tao123.com/static/weather/weather_json.php";
		// String result = GetApiUtils.getWeatherApi(url);
		// result = StringUtils.substringAfter(result, "(");
		// result = StringUtils.substringBeforeLast(result, ")");
		// System.out.println("result: " + result);
		// String translation = (String) new JSONObject(result.toString()).get("data");
		// System.out.println("translation: " + translation);
		// String content = URLEncoder.encode("尊敬的用户，欢迎您成功注册神买商城会员，验证码为222222(神买商城客服绝不会索取此验证码，切勿告知他人)，请在页面中输入以完成验证。",
		// "GBK");
		// String url = "http://www.106551.com/ws/Send.aspx?CorpID=YSSM000230&Pwd=mmt@2015&Mobile=13515658881&Content="
		// + content;
		// String result = GetApiUtils.getApiWithUrl(url);
		BigDecimal b1 = new BigDecimal("0.020");
		BigDecimal b2 = new BigDecimal("0.12");
		BigDecimal b3 = new BigDecimal("0.1400");
		BigDecimal b1_2 = b1.add(b2);

		System.out.println("b1_2: " + b1_2);
		System.out.println("result: " + b3.equals(b1_2));
		System.out.println("result: " + (b3 == b1_2));
		System.out.println("result: " + (b3.doubleValue() == b3.doubleValue()));
		// String data = new JSONObject(result.toString()).get("data").toString();
		// System.out.println(" " + data);
		// JSONObject json_data = new JSONObject(data);
		// System.out.println("city: " + json_data.get("city"));
		// System.out.println("country: " + URLDecoder.decode("\u5408\u80a5\u5e02", "UTF-8"));

	}
}
