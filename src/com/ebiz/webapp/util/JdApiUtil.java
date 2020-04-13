package com.ebiz.webapp.util;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.service.impl.BaseImpl;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class JdApiUtil {
	protected static final Logger logger = LoggerFactory.getLogger(BaseImpl.class);

	private static HttpClient httpclient = new HttpClient(new MultiThreadedHttpConnectionManager());

	// 获取token
	private static String getToken() {
		PostMethod post = new PostMethod("http://jd.weite.xcfngc.cn/api/account/sign/in");
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		post.setRequestHeader("Content-type", "application/json; charset=utf-8");
		post.setRequestHeader("appkey", Keys.jd_yuan_appkey);

		StringBuffer accountJson = new StringBuffer("");
		String appkey = Keys.jd_yuan_appkey;
		String appsecret = Keys.jd_yuan_secret;
		String timestamp = String.valueOf(new Date().getTime());
		String signature = EncryptUtilsV2.MD5Encode(
				"appkey=" + appkey + "&appsecret=" + appsecret + "&timestamp=" + timestamp + "#").toLowerCase();
		accountJson.append("{");
		accountJson.append("\"appkey\":\"" + appkey + "\"");
		accountJson.append(",\"timestamp\":" + timestamp + "");
		accountJson.append(",\"signature\":\"" + signature + "\"");
		accountJson.append("}");
		post.setRequestBody(accountJson.toString());
		try {
			httpclient.executeMethod(post);
			String info = new String(post.getResponseBody(), "utf-8");
			logger.info("accountJson：" + accountJson.toString());
			logger.info("登陆信息：" + info);
			if (StringUtils.isNotBlank(info)) {
				JSONObject obj = JSONObject.parseObject(info);
				if (null != obj && null != obj.get("status_code") && null != obj.get("ok")) {
					if (Keys.JD_API_RESULT_STATUS_CODE.equals(obj.get("status_code").toString())
							&& Keys.JD_API_RESULT_OK.equals(obj.get("ok").toString()) && null != obj.get("result")) {// 登陆成功
						String result_str = JSONObject.toJSONString(obj.get("result"));
						JSONObject result = JSONObject.parseObject(result_str);
						String token = "";
						if (null != result) {
							token = result.getString("token");
							return token;
						}
					}
				}
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("syncCancelJdOrder异常信息：" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("syncCancelJdOrder异常信息：" + e.getMessage());
		}
		return null;
	}

	/*
	 * 查询商品库存
	 */
	public static String getJdProductStocks(String json) {
		String info = "";
		try {
			PostMethod post = new PostMethod("http://jd.weite.xcfngc.cn/api/products/getstock");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.setRequestHeader("Content-type", "application/json; charset=utf-8");

			String token = JdApiUtil.getToken();// 获取token
			post.setRequestHeader("token", token);
			post.setRequestHeader("appkey", Keys.jd_yuan_appkey);
			post.setRequestBody(json);

			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
			logger.error("====getJdProductStocks==== responseBody :" + info);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/*
	 * 查询运费
	 */
	public static String getJdProductFreight(String json) {
		String info = "";
		try {
			PostMethod post = new PostMethod("http://jd.weite.xcfngc.cn/api/products/getfreight");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.setRequestHeader("Content-type", "application/json; charset=utf-8");

			String token = JdApiUtil.getToken();// 获取token
			post.setRequestHeader("token", token);
			post.setRequestHeader("appkey", Keys.jd_yuan_appkey);
			post.setRequestBody(json);

			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
			logger.error("====getJdProductFreight==== responseBody ：" + info);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 根据sku获取产品基本信息（不包含产品详情和产品规格和包装清单）
	 * 
	 * @param sku 京东商品sku
	 * @return
	 */
	public static String getJdProductInfo(Integer sku) {
		String info = "";
		try {
			GetMethod get = new GetMethod("http://jd.weite.xcfngc.cn/api/products/getproduct?sku=" + sku);
			get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

			String token = JdApiUtil.getToken();// 获取token
			get.setRequestHeader("token", token);
			get.setRequestHeader("appkey", Keys.jd_yuan_appkey);

			httpclient.executeMethod(get);
			info = new String(get.getResponseBody(), "utf-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 京东商品列表
	 * 
	 * @param skipCount 忽略前多少条记录
	 * @param limitCount 每次查询的总条数
	 * @return
	 */
	public static String getJdProductList(Integer skipCount, Integer limitCount) {
		String info = "";
		try {
			GetMethod get = new GetMethod("http://jd.weite.xcfngc.cn/api/products/list?skip=" + skipCount + "&limit="
					+ limitCount);
			get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

			String token = JdApiUtil.getToken();// 获取token
			get.setRequestHeader("token", token);
			get.setRequestHeader("appkey", Keys.jd_yuan_appkey);

			httpclient.executeMethod(get);
			info = new String(get.getResponseBody(), "utf-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 根据id获取分类信息
	 * 
	 * @param categoryId 分类id
	 * @return
	 */
	public static String getJdCategoryInfo(String categoryId) {
		String info = "";
		try {
			GetMethod get = new GetMethod("http://jd.weite.xcfngc.cn/api/category/get?id=" + categoryId);
			get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

			String token = JdApiUtil.getToken();// 获取token
			get.setRequestHeader("token", token);
			get.setRequestHeader("appkey", Keys.jd_yuan_appkey);

			httpclient.executeMethod(get);
			info = new String(get.getResponseBody(), "utf-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 创建京东订单
	 * 
	 * @param json
	 * @return
	 */
	public static String createJdOrder(String json) {
		String info = "";
		try {
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod("http://jd.weite.xcfngc.cn/api/trades");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.setRequestHeader("Content-type", "application/json; charset=utf-8");

			String token = JdApiUtil.getToken();// 获取token
			post.setRequestHeader("token", token);
			post.setRequestHeader("appkey", Keys.jd_yuan_appkey);
			post.setRequestBody(json);

			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
			logger.error("===createJdOrder=== responseBody : " + info);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 确认京东订单
	 * 
	 * @param json
	 * @return
	 */
	public static String confirmJdOrder(String json) {
		String info = "";
		try {
			PostMethod post = new PostMethod("http://jd.weite.xcfngc.cn/api/trades/confirm");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.setRequestHeader("Content-type", "application/json; charset=utf-8");

			String token = JdApiUtil.getToken();// 获取token
			post.setRequestHeader("token", token);
			post.setRequestHeader("appkey", Keys.jd_yuan_appkey);
			post.setRequestBody(json);

			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
			logger.error("===confirmJdOrder=== responseBody : " + info);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 获取京东订单
	 * 
	 * @param jd_order_no 京东订单id
	 * @return
	 */
	public static String getJdOrderInfo(String jd_order_no) {
		String info = "";
		try {
			GetMethod get = new GetMethod("http://jd.weite.xcfngc.cn/api/trades/" + jd_order_no);
			get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			get.setRequestHeader("Content-type", "application/json; charset=utf-8");

			String token = JdApiUtil.getToken();// 获取token
			get.setRequestHeader("token", token);
			get.setRequestHeader("appkey", Keys.jd_yuan_appkey);

			httpclient.executeMethod(get);
			info = new String(get.getResponseBody(), "utf-8");
			logger.error("===getJdOrderInfo=== responseBody : " + info);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 取消京东订单
	 * 
	 * @param jd_order_no 京东订单id
	 * @return
	 */
	public static String cancelJdOrder(String jd_order_no) {
		String info = "";
		try {
			PostMethod post = new PostMethod("http://jd.weite.xcfngc.cn/api/trades/cancel?id=" + jd_order_no);
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.setRequestHeader("Content-type", "application/json; charset=utf-8");

			String token = JdApiUtil.getToken();// 获取token
			post.setRequestHeader("token", token);
			post.setRequestHeader("appkey", Keys.jd_yuan_appkey);

			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
			logger.error("===cancelJdOrder=== responseBody : " + info);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 获取下级地区信息
	 * 
	 * @param pid
	 * @return
	 */
	public static String getJdAreaListByPid(String pid) {
		String info = "";
		try {
			GetMethod get = new GetMethod("http://jd.weite.xcfngc.cn/api/area/listbypid?pid=" + pid);
			get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			get.setRequestHeader("Content-type", "application/json; charset=utf-8");

			String token = JdApiUtil.getToken();// 获取token
			get.setRequestHeader("token", token);
			get.setRequestHeader("appkey", Keys.jd_yuan_appkey);

			httpclient.executeMethod(get);
			info = new String(get.getResponseBody(), "utf-8");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	public static void main(String[] args) {
		String info = getJdProductList(10000, 1);
		System.out.println("=============" + info);
	}
}
