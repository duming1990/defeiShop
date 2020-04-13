package com.kameila.koala.sdk;

/**
 * 
 * @author lindezhi
 * 2016年5月28日 上午11:13:44
 */
public class KoalaException extends RuntimeException{

	private static final long serialVersionUID = -2102101205627865874L;

	public KoalaException() {
		super();
	}

	public KoalaException(String message, Throwable cause) {
		super(message, cause);
	}

	public KoalaException(String message) {
		super(message);
	}

	public KoalaException(Throwable cause) {
		super(cause);
	}
}
