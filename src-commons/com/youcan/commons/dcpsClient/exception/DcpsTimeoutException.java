package com.youcan.commons.dcpsClient.exception;

public class DcpsTimeoutException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3172037935856644294L;

	public DcpsTimeoutException() {
		super();
	}
	
	public DcpsTimeoutException(String message) {
		super(message);
	}
	
	public DcpsTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
}
