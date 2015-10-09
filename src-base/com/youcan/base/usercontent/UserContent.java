package com.youcan.base.usercontent;

public class UserContent {
	/*
	 * 【该类记录一个用户在某一时间内操作一类内容中的一个内容】 例如：用户A收藏视频类的一个视频【建国大业】 【uid：A的用户编号 contentid
	 * ：视频【建国大业】的视频编号 contentGroup ： 视频类的组编号 act ： 操作类型 actTime ： 操作时间
	 */
	private int uid; // 用户ID
	private int contentid; // 内容ID
	// private int contentGroup; // 内容组
	// private int act; // 操作类型
	private long actTime; // 操作时间

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getContentid() {
		return contentid;
	}

	public void setContentid(int contentid) {
		this.contentid = contentid;
	}

	/*
	 * public int getContentGroup() { return contentGroup; }
	 * 
	 * public void setContentGroup(int contentGroup) { this.contentGroup =
	 * contentGroup; }
	 * 
	 * public int getAct() { return act; }
	 * 
	 * public void setAct(int act) { this.act = act; }
	 */
	public long getActTime() {
		return actTime;
	}

	public void setActTime(long actTime) {
		this.actTime = actTime;
	}

}
