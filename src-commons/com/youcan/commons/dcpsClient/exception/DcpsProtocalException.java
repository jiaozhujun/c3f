package com.youcan.commons.dcpsClient.exception;

public class DcpsProtocalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3172037935856644294L;

	public DcpsProtocalException() {
		super();
	}
	
	public DcpsProtocalException(String message) {
		super(message);
	}
	
	public DcpsProtocalException(String message, Throwable cause) {
		super(message, cause);
	}
}
