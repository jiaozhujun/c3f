package com.youcan.core.util;

public class Page {
	private int total = 0;
	private int start = 0;
	private int limit = 20;
	private int setp = 4;

	//总页数
	private int tp = 0;
	//当前页数
	private int cp = 0;
	//上一页
	private int pp = 0;
	//下一页
	private int np = 0;

	private String url = "";
	private String imgPre = "";
	private String imgNext = "";
	private String pageHtml = "";
	
	public void makePageHtml() {
		//do nothing
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getTp() {
		return tp;
	}
	public void setTp(int tp) {
		this.tp = tp;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgPre() {
		return imgPre;
	}
	public void setImgPre(String imgPre) {
		this.imgPre = imgPre;
	}
	public String getImgNext() {
		return imgNext;
	}
	public void setImgNext(String imgNext) {
		this.imgNext = imgNext;
	}
	public String getPageHtml() {
		return pageHtml;
	}
	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}
	public int getPp() {
		return pp;
	}
	public void setPp(int pp) {
		this.pp = pp;
	}
	public int getNp() {
		return np;
	}
	public void setNp(int np) {
		this.np = np;
	}
	public int getSetp() {
		return setp;
	}
	public void setSetp(int setp) {
		this.setp = setp;
	}

}
