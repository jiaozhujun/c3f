package com.youcan.commons.dcpsClient.exception;


public class DcpsRequestInvalidValueException extends DcpsRequestException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -863712113713653268L;

	public DcpsRequestInvalidValueException() {
		super();
	}
	
	public DcpsRequestInvalidValueException(String message) {
		super(message);
	}
	
	public DcpsRequestInvalidValueException(String message, Throwable cause) {
		super(message, cause);
	}
}
