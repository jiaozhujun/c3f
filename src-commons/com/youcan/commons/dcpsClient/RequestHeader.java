package com.youcan.commons.dcpsClient;

public class RequestHeader {
	private String transactionId;
	private String serviceId;
	private String clientId;
	private int callBack;
	private boolean debug;
	private int priority;
	private long timeStamp;
	private String template;
	
	public RequestHeader() {
		transactionId = "";
		serviceId = "unknown";
		clientId = "unknown";
		callBack = 0;
		debug = false;
		priority = 0;
		timeStamp = 0;
		template = "";
	}

	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public int getCallBack() {
		return callBack;
	}
	public void setCallBack(int callBack) {
		this.callBack = callBack;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
