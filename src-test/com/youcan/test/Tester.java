package com.youcan.test;

import com.youcan.core.util.Text;

public class Tester {

	/**
	 * @Title: main
	 * @Description: TODO
	 * @param @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		System.out.println("http://www.xingbook.com/Aa/abc.jpg".hashCode());
		System.out.println("http://www.xingbook.com/BB/abc.jpg".hashCode());
		System.out.println("Aa".equals("BB"));
		long start = System.currentTimeMillis();
		System.out.println(start);
//		for (int i=0;i<10000;i++) {
//			String s = new StringBuilder().append("abc").append("def").append(1234).append("ddd").toString();
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("time[1]:"+(end-start));
//		start = System.currentTimeMillis();
//		for (int i=0;i<10000;i++) {
//			String s = "abc"+"def"+1234+"ddd";
//		}
//		end = System.currentTimeMillis();
//		System.out.println("time[2]:"+(end-start));
	}

}
