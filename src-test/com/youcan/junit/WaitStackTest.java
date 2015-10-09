package com.youcan.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.youcan.core.util.WorkerDesk;
import com.youcan.core.util.WorkerDeskEmptyException;
import com.youcan.core.util.WorkerDeskException;

public class WaitStackTest {
//	private static WorkerDesk<Integer> waitStack;
//
//	@BeforeClass
//	public static void firstRun() throws WorkerDeskException {
//		Integer[] ws = new Integer[100];
//		for (int i = 0; i < 100; i++) {
//			ws[i] = i;
//		}
//		waitStack = new WorkerDesk<>(ws);
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@Test
//	public void testBack() throws WorkerDeskException {
//		System.out.println(1);
//		Integer waiter1 = waitStack.call();//99->99
//		Integer waiter2 = waitStack.call();//98->98
//		Integer waiter3 = waitStack.call();//97->97
//		waitStack.back(waiter2);//97->98, 98->97
//		waitStack.call();
//		assertEquals(97, waitStack.get(98).intValue());
//		System.out.println(2);
//	}
//	
//	@Test
//	public void testCall() throws WorkerDeskEmptyException {
//		System.out.println(3);
//		assertEquals(96, waitStack.call().intValue());
//		System.out.println(4);
//	}
//
//	@Test
//	public void testWaiting() {
//		assertEquals(96, waitStack.waiting());
//	}
//
//	@Ignore
//	public void testEmpty() {
//		assertEquals(false, waitStack.empty());
//	}
//
//	@Ignore//(expected = WaitStackException.class)
//	public void testBackException() throws WorkerDeskException {
//		Integer newWaiter = new Integer(101);
//		waitStack.back(newWaiter);
//	}

}
