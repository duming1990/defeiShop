package push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import push.android.AndroidUnicast;
import push.ios.IOSUnicast;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.web.Keys;

public class AppPush {
	protected static final Logger logger = LoggerFactory.getLogger(AppPush.class);

	public static final String push_activity = "com.moba.renyi.activity.MainActivity";

	// TODO ios 换密钥
	private static final String appkeyIos = "578ddc1f67e58e252d002587";

	private static final String appMasterSecretIos = "wsipkqudlvljd68mzgc1qaifiktw325h";

	// TODO android 换密钥
	private static final String appkeyAndroid = "578ddec867e58e01a6003ed0";

	private static final String appMasterSecretAndroid = "khaoiaipsp3umvytglay2jkahirods3b";

	private static PushClient client = new PushClient();

	/**
	 * @方法名称：1、 安装推送新闻信息
	 * @param msg_data={"webview_title":"测试标题","webvew_url":
	 *            "http://www.xiuqingchun.net/m/MNewsInfo.do?method=view&uuid=xxx"}
	 * @author 吴洋
	 */
	public static void sendAndroidUnicastForNews(String deviceToken, String title, String content, String msg_url,
			boolean isProduct) throws Exception {

		logger.warn("=send[IOS]UnicastForNews=" + title + " deviceToken:" + deviceToken);
		AndroidUnicast unicast = new AndroidUnicast(appkeyAndroid, appMasterSecretAndroid);
		unicast.setDeviceToken(deviceToken);
		unicast.setTicker(title);
		unicast.setTitle(title);
		unicast.setText(content);
		unicast.goActivityAfterOpen(push_activity);
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// For how to register a test device, please see the developer doc.
		if (isProduct) {
			unicast.setProductionMode();
		} else {
			unicast.setTestMode();
		}
		unicast.setExtraField("msg_type", Keys.push_action_show_news);
		JSONObject json = new JSONObject();
		json.put("webview_title", title);
		json.put("webvew_url", msg_url);
		// logger.info("=msg_data:" + json.toJSONString());
		unicast.setExtraField("msg_data", json.toJSONString());
		client.send(unicast);
	}

	/**
	 * @方法名称：1、 苹果推送新闻信息
	 * @param msg_data={"webview_title":"测试标题","webvew_url":
	 *            "http://www.xiuqingchun.net/m/MNewsInfo.do?method=view&uuid=xxx"}
	 * @author 吴洋
	 */
	public static void sendIOSUnicastForNews(String deviceToken, String title, String msg_url, boolean isProduct)
			throws Exception {
		logger.warn("=send[IOS]UnicastForNews=" + title + " deviceToken:" + deviceToken);
		IOSUnicast unicast = new IOSUnicast(appkeyIos, appMasterSecretIos);
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(title);
		unicast.setBadge(1);
		unicast.setSound("default");
		if (isProduct) {
			unicast.setProductionMode();
		} else {
			unicast.setTestMode();
		}
		// Set customized fields
		unicast.setCustomizedField("msg_type", Keys.push_action_show_news);
		JSONObject json = new JSONObject();
		json.put("webview_title", title);
		json.put("webvew_url", msg_url);
		// logger.info("=msg_data:" + json.toJSONString());
		unicast.setCustomizedField("msg_data", json.toJSONString());
		client.send(unicast);
	}
}
