package com.youcan.base.area;

public class AreaDao {
	private int id; // 地区ID
	private String name; // 地区名称
	private int parentid; // 父类地区
	private int gradeid; // 级别
	private String childids; // 子类地区

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

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getGradeid() {
		return gradeid;
	}

	public void setGradeid(int gradeid) {
		this.gradeid = gradeid;
	}

	public String getChildids() {
		return childids;
	}

	public void setChildids(String childids) {
		this.childids = childids;
	}

}
