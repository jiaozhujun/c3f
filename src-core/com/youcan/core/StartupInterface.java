package com.youcan.core;

public interface StartupInterface {
	/**
	 * 注销时调用
	 */
	public void destroy();
	/**
	 * 初始化时调用
	 */
	public void init();
}
