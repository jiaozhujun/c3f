package com.youcan.commons.dcpsClient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import com.youcan.commons.dcpsClient.exception.DcpsProtocalException;
import com.youcan.commons.dcpsClient.exception.DcpsTimeoutException;

abstract public class DcpsService {
	public RequestHeader header;

	public DcpsService() {
		header = new RequestHeader();
		setDefaultHeader();
	}
	
	abstract protected void setDefaultHeader();
	abstract protected StringBuffer makeReq();
	
	public int request() {
		StringBuffer requestXml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><UcapService transactionId=\"");
		requestXml.append(header.getTransactionId())
			.append("\" template=\"").append(header.getTemplate())
			.append("\" debug=\"").append(header.isDebug())
			.append("\" priority=\"").append(header.getPriority())
			.append("\" serviceId=\"").append(header.getServiceId())
			.append("\" clientId=\"").append(header.getClientId())
			.append("\" timeStamp=\"").append(System.currentTimeMillis())
			.append("\" callBack=\"").append(header.getCallBack())
			.append("\">");
		requestXml.append(makeReq()).append("</UcapService>");
		try {
			String response = DcpsClient.getInstance().requestDcps(requestXml.toString());
			System.out.println("DcpsService response: " + response);
			return DcpsClient.DCPS_OK;
		} catch (ConnectException e) {
			return DcpsClient.DCPS_ERROR_CONNECT;
		} catch (UnknownHostException e) {
			return DcpsClient.DCPS_ERROR_UNKNOWN_HOST;
		} catch (IOException e) {
			return DcpsClient.DCPS_ERROR_IO;
		} catch (DcpsTimeoutException e) {
			return DcpsClient.DCPS_ERROR_TIMEOUT;
		} catch (DcpsProtocalException e) {
			return DcpsClient.DCPS_ERROR_PROTOCAL;
		}
	}
}
