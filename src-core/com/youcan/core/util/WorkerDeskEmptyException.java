package com.youcan.core.util;

public class WorkerDeskEmptyException extends WorkerDeskException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5910302238061702881L;

	public WorkerDeskEmptyException() {
		super("Worker desk is empty.");
	}
}
