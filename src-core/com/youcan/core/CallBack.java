package com.youcan.core;

public class CallBack {
	private String content = null;
	private long timeout;
	private boolean isTimeout = true;
	public static final int CALLBACK_NONE = -1;
	public static final int CALLBACK_TIMEOUT_ENDLESS = 0;

	public CallBack(long timeout) {
		this.timeout = timeout;
	}

	public String getContent() {
		return content;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	public synchronized String waitBack() {
		try {
			if (timeout >= 0) {
				wait(timeout);
				if (isTimeout) {
					content = "TIMEOUT:" + timeout;
				}
			}
		} catch (InterruptedException e) {
			content = "InterruptedException:" + e.getMessage();
		} finally {
			isTimeout = true;
		}
		return content;
	}

	public synchronized void finished(String content) {
		this.content = content;
		isTimeout = false;
		notify();
	}
}
