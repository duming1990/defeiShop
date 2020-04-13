package com.kameila.koala.sdk;

import java.util.Map;

/**
 * 使用http发送请求需要的数据
 */
public class KoalaRequest {
	
	private String url;
	
	private Map<String, Object> urlParams;
	
	private Map<String, String> headers;
	
	private Map<String, String> body;
	
	private Map<String,byte[]> fileBody;

	private String sign;
	
	private String signBefore;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getUrlParams() {
		return urlParams;
	}

	public void setUrlParams(Map<String, Object> urlParams) {
		this.urlParams = urlParams;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getBody() {
		return body;
	}

	public void setBody(Map<String, String> body) {
		this.body = body;
	}

	public Map<String, byte[]> getFileBody() {
		return fileBody;
	}

	public void setFileBody(Map<String, byte[]> fileBody) {
		this.fileBody = fileBody;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignBefore() {
		return signBefore;
	}

	public void setSignBefore(String signBefore) {
		this.signBefore = signBefore;
	}
}
