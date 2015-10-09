package com.youcan.base.file;

public class FileDao {

	public final static short FILE_PHYSICAL_DEL = 0;
	public final static short FILE_LOGIC_DEL = 2;
	public final static short FILE_NO_DEL = 1;

	private int id; // 文件编号
	private String name; // 文件名
	private String suffix; // 后缀
	private int length; // 大小
	private String type; // 类型
	private String location; // 位置
	private long ctime; // 创建时间
	private long mtime; // 更新时间
	private short state; // 删除状态

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

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public long getMtime() {
		return mtime;
	}

	public void setMtime(long mtime) {
		this.mtime = mtime;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

}
