package com.youcan.core.util;

public class WorkerInvalidException extends WorkerDeskException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5910302238061702881L;

	public WorkerInvalidException() {
		super("The worker coming back is invalid.");
	}
}
