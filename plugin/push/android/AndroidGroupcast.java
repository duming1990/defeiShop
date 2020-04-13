package push.android;

import push.AndroidNotification;

import com.alibaba.fastjson.JSONObject;

public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "groupcast");
	}

	public void setFilter(JSONObject filter) throws Exception {
		setPredefinedKeyValue("filter", filter);
	}
}
