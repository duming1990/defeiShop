package com.kameila.koala.sdk;

/**
 * http请求异常
 */
public class HttpException extends RuntimeException{

	private static final long serialVersionUID = -7986057373963720777L;

	public HttpException() {
		super();
	}

	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpException(String message) {
		super(message);
	}

	public HttpException(Throwable cause) {
		super(cause);
	}
	
}