package com.youcan.base.content;

public abstract class ContentDao {
	public final static short STATE_EDITING = 0;
	public final static short STATE_EDITED = 1;
	public final static short STATE_AUDITED = 2;
	public final static short STATE_AUDITFAILED = 3;
	public final static short STATE_PUBLISHED = 10;

	private long id;
	private String title;
	private long uid;
	private int catid;
	private long ctime;
	private long mtime;
	private short state;

	public ContentDao() {
		this.state = STATE_EDITED;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
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
