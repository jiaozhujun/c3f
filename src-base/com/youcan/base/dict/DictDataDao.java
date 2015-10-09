package com.youcan.base.dict;

public class DictDataDao {
	private int ddId = 0;
	private int dmId = 0;
	private String dataLabel = "";
	private String dataValue = "";
	private int grade = 0;
	private String timeModify = "";
	public int getDdId() {
		return ddId;
	}
	public void setDdId(int ddId) {
		this.ddId = ddId;
	}
	public int getDmId() {
		return dmId;
	}
	public void setDmId(int dmId) {
		this.dmId = dmId;
	}
	public String getDataLabel() {
		return dataLabel;
	}
	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getTimeModify() {
		return timeModify;
	}
	public void setTimeModify(String timeModify) {
		this.timeModify = timeModify;
	}
}
