package com.youcan.core;

public class Message {
	private int code;
	private String text;
	private String data;

	public Message(int code, String text) {
		this.code = code;
		this.text = text;
		data = null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
