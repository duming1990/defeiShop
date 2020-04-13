package com.kameila.koala.sdk;

import java.util.Map;

/**
 * api请求参数
 */
public class KoalaApi {
	
	/**
	 * api名称
	 */
	private String api;
	
	/**
	 * api版本
	 */
	private String apiVersion = "1.0.0";
	
	/**
	 * token
	 */
	private String token;
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 用户角色
	 */
	private String userRole;
	
	/**
	 * useragent参数
	 */
	private String userAgent;
	
	/**
	 * body，value基本类型+文件
	 */
	private Map<String,Object> requestBody;
	
	private long timestamp;
	
	private Map<String,String> urlParams;
	
	public KoalaApi(){
		timestamp = System.currentTimeMillis();
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Map<String, Object> getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(Map<String, Object> requestBody) {
		this.requestBody = requestBody;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, String> getUrlParams() {
		return urlParams;
	}

	public void setUrlParams(Map<String, String> urlParams) {
		this.urlParams = urlParams;
	}
}
