package com.youcan.core.util;

public interface IWorkerDeskManager {
	public static final int WORKERDESK_STATE_EMPTY = 0;
	public static final int WORKERDESK_STATE_ENABLED = 1;
	public static final int WORKERDESK_STATE_FULL = 2;

	/**
	 * 返回一个工作者
	 * @param worker
	 * @throws WorkerDeskFullException
	 * @throws WorkerInvalidException
	 */
	public void back(Worker worker) throws WorkerDeskFullException, WorkerInvalidException;
	/**
	 * 呼叫一个工作者
	 * @return
	 * @throws WorkerDeskEmptyException
	 */
	public Worker call() throws WorkerDeskEmptyException;
	/**
	 * 根据ID查找一位工作者
	 * @param id
	 * @return
	 */
	public Worker get(int id);
	/**
	 * 查询正在等待的工作者数量
	 * @return
	 */
	public int waiting();
	/**
	 * 查询工作队列是否为空
	 * @return
	 */
	public boolean empty();
	/**
	 * 查询工作者队列的总长度
	 * @return
	 */
	public int size();
}
