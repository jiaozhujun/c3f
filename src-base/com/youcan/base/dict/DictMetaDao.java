package com.youcan.base.dict;

public class DictMetaDao {
	private int dmId = 0;
	private String dmName = "";
	private String dmLabel = "";
	private int numData = 0;
	//JIAO:这里为什么要使用String类型？应该使用long类型，这样在使用的时候可以根据需要进行灵活转换
	private String timeModify = "";
	public int getDmId() {
		return dmId;
	}
	public void setDmId(int dmId) {
		this.dmId = dmId;
	}
	public String getDmName() {
		return dmName;
	}
	public void setDmName(String dmName) {
		this.dmName = dmName;
	}
	public String getDmLabel() {
		return dmLabel;
	}
	public void setDmLabel(String dmLabel) {
		this.dmLabel = dmLabel;
	}
	public int getNumData() {
		return numData;
	}
	public void setNumData(int numData) {
		this.numData = numData;
	}
	public String getTimeModify() {
		return timeModify;
	}
	public void setTimeModify(String timeModify) {
		this.timeModify = timeModify;
	}
}
