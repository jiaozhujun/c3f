package com.youcan.core.ha;

public class CcsServer extends SingleServer {
	/**
	 * 权重
	 */
	private int weight = 100;
	/**
	 * 总的工作线程数
	 */
	private int totalWorkers = 0;
	/**
	 * 处于等待状态的工作线程数
	 */
	private int waiting = 0;
	/**
	 * 已完成的工作数
	 */
	private long completed;
	/**
	 * 启动时间
	 */
	private long startTime;
	/**
	 * 当前时间，用于做时间同步
	 */
	private long lastTime;
	private StringBuilder statusMessage, infoMessage;
	private int statusMessageLength;

	public CcsServer(String id, String addr, int port) {
		super(id, addr, port);
		startTime = System.currentTimeMillis();
		lastTime = startTime;
		statusMessage = new StringBuilder();
		initStatusMessage();
		infoMessage = new StringBuilder();
		initServerInfoMessage();
	}

	private void initStatusMessage() {
		//固定部分 > ID:总的工作线程数:权重:开始时间:
		//可变部分 > 当前时间:已完成的总任务数(不包括进行中的):空闲的工作线程数
		statusMessage.delete(0, statusMessage.length()).append("STATUS:")
			.append(getId()).append(':')
			.append(totalWorkers).append(':')
			.append(weight).append(':')
			.append(startTime).append(':');
		statusMessageLength = statusMessage.length();
	}

	private void initServerInfoMessage() {
		//ID:IP地址:端口号:总的工作线程数:权重:开始时间
		infoMessage.delete(0, infoMessage.length()).append("INFO:")
			.append(getId()).append(':')
			.append(getAddr()).append(':')
			.append(getPort()).append(':')
			.append(totalWorkers).append(':')
			.append(weight).append(':')
			.append(startTime);
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
		initStatusMessage();
	}
	public int getTotalWorkers() {
		return totalWorkers;
	}
	public void setTotalWorkers(int totalWorkers) {
		waiting = this.totalWorkers = totalWorkers;
		initStatusMessage();
	}
	public int getWaiting() {
		return waiting;
	}
	public synchronized void setWaiting(int waiting) {
		this.waiting = waiting;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public float getRank() {
		return (totalWorkers == 0) ? 0f : weight * waiting / totalWorkers;
	}

	public long getCompleted() {
		return completed;
	}
	public synchronized void doneWork() {
		completed++;
		waiting++;
	}

	public synchronized void startWork() {
		waiting--;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public synchronized void setCompleted(long completed) {
		this.completed = completed;
	}
	
	public CharSequence getStatus() {
		//固定部分 > ID:总的工作线程数:权重:开始时间:
		//可变部分 > 当前时间:已完成的总任务数(不包括进行中的):空闲的工作线程数
		statusMessage.delete(statusMessageLength, statusMessage.length())
			.append(System.currentTimeMillis()).append(':')
			.append(completed).append(':')
			.append(waiting);
		return statusMessage;
	}
	
	public CharSequence getInfo() {
		//ID:IP地址:端口号:总的工作线程数:权重:开始时间
		return infoMessage;
	}
}
