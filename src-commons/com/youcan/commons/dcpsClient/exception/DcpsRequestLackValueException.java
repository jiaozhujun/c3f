package com.youcan.commons.dcpsClient.exception;


public class DcpsRequestLackValueException extends DcpsRequestException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4710558912267420527L;

	public DcpsRequestLackValueException() {
		super();
	}
	
	public DcpsRequestLackValueException(String message) {
		super(message);
	}
	
	public DcpsRequestLackValueException(String message, Throwable cause) {
		super(message, cause);
	}
}
