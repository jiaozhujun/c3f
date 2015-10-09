package com.youcan.core.ha;

public class SingleServer {
	private String id;
	private String addr;
	private int port;

	public SingleServer(String id, String addr, int port) {
		this.id = id;
		this.addr = addr;
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public String getAddr() {
		return addr;
	}

	public int getPort() {
		return port;
	}
}
