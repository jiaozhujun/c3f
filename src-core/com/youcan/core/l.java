package com.youcan.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.youcan.core.l;

public class l {
	protected l() {
		//do nothing
		System.out.println("l started.");
	}

	private static Logger log;

	public static final boolean init(String logName) {
		if ((log = LogManager.exists(logName)) != null) {
			return true;
		}
		return false;
	}
	
	public static final void print(Object message) {
		System.out.print(message);
	}
	
	public static final void println(Object message) {
		System.out.println(message);
	}

	public static final void warn(Object message) {
		log.warn(message);
	}

	public static final void info(Object message) {
		log.info(message);
	}

	public static final void debug(Object message) {
		log.debug(message);
	}

	public static final void error(Object message) {
		log.error(message);
	}

	public static final void error(Object message, Throwable t) {
		log.error(message, t);
	}

	public static final void fatal(Object message) {
		log.fatal(message);
	}

	public static final void fatal(Object message, Throwable t) {
		log.fatal(message, t);
	}

	public static final Logger getLog() {
		return log;
	}

	public static final void setLog(Logger log) {
		l.log = log;
	}
}
