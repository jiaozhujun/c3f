package com.youcan.core.util;

public class WorkerDesk implements IWorkerDeskManager {
	private Worker[] workers;
	private int pointer = -1;
	private IWorkerDeskListener listener = null;

	public WorkerDesk(Worker[] workers) throws WorkerDeskException {
		if (workers == null) {
			throw new WorkerDeskException("Workers is null.");
		}
		for (Worker waiter : workers) {
			pointer++;
			waiter.setId(pointer);
			waiter.setDesk(pointer);
			waiter.setManager(this);
		}
		this.workers = workers;
	}
	
	@Override
	public synchronized void back(Worker worker) throws WorkerDeskFullException, WorkerInvalidException {
		if (++pointer == workers.length) {
			pointer--;
			throw new WorkerDeskFullException();
		}
		if (workers[pointer] != worker) {
			int oldDesk = worker.getDesk();
			if (workers[oldDesk] == worker) {
				workers[oldDesk] = workers[pointer];
				workers[oldDesk].setDesk(oldDesk);
				worker.setDesk(pointer);
				worker.setBusy(false);
				workers[pointer] = worker;
				if (listener != null) {
					listener.onWorkReturn(worker);
					if (pointer == 0) {
						listener.onWorkDeskStateChanged(WORKERDESK_STATE_ENABLED);
					} else if (pointer == workers.length - 1) {
						listener.onWorkDeskStateChanged(WORKERDESK_STATE_FULL);
					}
				}
			} else {
				pointer--;
				throw new WorkerInvalidException();
			}
		}
	}
	
	@Override
	public Worker get(int index) {
		if (index >= 0 && index < workers.length) {
			return workers[index];
		} else {
			return null;
		}
	}
	
	@Override
	public synchronized Worker call() throws WorkerDeskEmptyException {
		if (pointer == -1) {
			throw new WorkerDeskEmptyException();
		}
		Worker worker = workers[pointer--];
		worker.setBusy(true);
		if (listener != null && pointer == -1) {
			listener.onWorkDeskStateChanged(WORKERDESK_STATE_EMPTY);
		}
		return worker;
	}
	
	@Override
	public synchronized int waiting() {
		return pointer + 1;
	}
	
	@Override
	public synchronized boolean empty() {
		return pointer == -1;
	}
	
	@Override
	public int size() {
		return workers.length;
	}

	public void setListener(IWorkerDeskListener listener) {
		this.listener = listener;
	}
}
