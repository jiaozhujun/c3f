package com.youcan.commons.dcpsClient.exception;

public class DcpsRequestException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3172037935856644294L;

	public DcpsRequestException() {
		super();
	}
	
	public DcpsRequestException(String message) {
		super(message);
	}
	
	public DcpsRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
