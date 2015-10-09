package com.youcan.commons.dcpsClient;

public class BasicResponse {
	private int resultCode = 0;
	private String resultMsg = null;
	private String resultXml = null;

	public BasicResponse() {
		refresh();
	}

	public void refresh() {
		resultCode = 0;
		resultMsg = null;
		resultXml = null;
	}
	
	public void setResult(int resultCode, String resultMsg) {
		setResultCode(resultCode);
		setResultMsg(resultMsg);
	}

	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getResultXml() {
		return resultXml;
	}
	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
	}

}
