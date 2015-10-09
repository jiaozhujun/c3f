package com.youcan.test;

import com.youcan.core.StartupInterface;
import com.youcan.core.l;

public class StartupTest implements StartupInterface {
	public StartupTest() {
		l.debug("StartupTest()");
	}

	@Override
	public void init() {
		l.debug("StartupTest.init()");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
