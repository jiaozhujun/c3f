package com.youcan.core;

public abstract class BaseSession {
	/**
	 * SESSION ID
	 */
	private String sessionId;
	/**
	 * 本SESSION创建时间，用来决定SESSION失效
	 */
	private long ctime;
	/**
	 * 本SESSION更新时间，用来决定SESSION失效
	 */
	private long mtime;

	public BaseSession() {
		mtime = ctime = System.currentTimeMillis();
		this.sessionId = makeSessionId();
	}

	public BaseSession(String sessionId) {
		mtime = ctime = System.currentTimeMillis();
		this.sessionId = sessionId;
	}
	/**
	 * 获取SESSION ID
	 * @return
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * 获取SESSION创建时间
	 * @return
	 */
	public long getCtime() {
		return ctime;
	}
	/**
	 * 获取SESSION更新时间
	 * @return
	 */
	public long getMtime() {
		return mtime;
	}
	/**
	 * 更新SESSION时间
	 */
	public void access() {
		this.mtime = System.currentTimeMillis();
	}

	/**
	 * 生成SESSION ID
	 * @return
	 */
	public abstract String makeSessionId();
}
