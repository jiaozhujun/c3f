package com.youcan.base.interfaces;

public class InterfaceDao {

	/*
	 * 例如：http://127.0.0.1:8080/video/userlogin namespace = /video ; name =
	 * userlogin
	 */
	public static int INTERFACE_WEBFORM = 0;
	public static int INTERFACE_ADMINFORM = 1;

	private int id; // 接口ID
	private String namespace; // 接口命名前缀
	private String name; // 接口名称
	private int mid; // 模块ID
	private short type; // 0 : 前台接口 1： 后台接口

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

}
