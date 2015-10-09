package com.youcan.core.util;

public abstract class Worker implements Runnable {
	private IWorkerDeskManager manager;
	/**
	 * 工作者是否正在工作
	 */
	private boolean busy = false;
	/**
	 * 工作者的ID
	 */
	private int id;
	/**
	 * 工作者坐席位置ID
	 */
	private int desk;
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public boolean isBusy() {
		return busy;
	}
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	public int getDesk() {
		return desk;
	}
	public void setDesk(int desk) {
		this.desk = desk;
	}
	public void setManager(IWorkerDeskManager manager) {
		this.manager = manager;
	}
	public void accomplish() {
		reset();
		try {
			if (manager != null) manager.back(this);
		} catch (WorkerDeskFullException | WorkerInvalidException e) {
			e.printStackTrace();
		}
	}
	public abstract void reset();
}
