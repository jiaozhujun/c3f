package com.youcan.core.util;

public interface IWorkerDeskListener {
	/**
	 * 返回一个工作者
	 * @param worker
	 */
	public void onWorkReturn(Worker worker);
	/**
	 * 工作台状态改变
	 * @param state
	 */
	public void onWorkDeskStateChanged(int state);
}
