package com.youcan.test;

import com.youcan.core.CallBack;

public class CallbackTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final CallBack cb = new CallBack(5000);
		System.out.println("before thread");
		Thread t1 = new Thread() {
			Object lock = new Object();
			@Override
			public void run() {
				try {
					System.out.println("thread start");
					synchronized (lock) {
						lock.wait(2000);
						cb.finished("runnable finish");
					}
					System.out.println("thread finish");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t1.start();
		System.out.println("before waitBack");
		cb.waitBack();
		System.out.println(cb.getContent());

	}

}
