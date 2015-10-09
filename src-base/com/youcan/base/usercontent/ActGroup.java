package com.youcan.base.usercontent;

public enum ActGroup {

	Report(1, "report", "报题"), Cross(2, "cross", "划题"), Sub(3, "sub", "订阅"), download(
			4, "download", "下载");

	private int id;
	private String name;
	private String content;

	ActGroup(int id, String name, String content) {
		this.id = id;
		this.name = name;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
