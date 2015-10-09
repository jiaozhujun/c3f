package com.youcan.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener extends Context implements ServletContextListener {

	// 释放连接池的资源
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.descroyed();
	}

	// 初始化连接池
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (event != null) {
			ServletContext cxt = event.getServletContext();
			init(cxt.getRealPath("/"), cxt.getRealPath("/WEB-INF/classes/"));
		} else {
			init("", "conf");
		}
	}
}
