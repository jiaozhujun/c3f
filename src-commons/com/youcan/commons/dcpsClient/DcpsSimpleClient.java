package com.youcan.commons.dcpsClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.youcan.commons.dcpsClient.exception.DcpsProtocalException;
import com.youcan.commons.dcpsClient.exception.DcpsTimeoutException;
import com.youcan.core.Param;

public class DcpsSimpleClient {
	private String dcpsAddress;
	private int dcpsPort;
	private char[] pieceHeader1, pieceHeader2, pieceHeader3;

	public DcpsSimpleClient(String dcpsAddress, int dcpsPort) {
		this.dcpsAddress = dcpsAddress;
		this.dcpsPort = dcpsPort;
	}
	
	public void initRequestXmlHeader(boolean taskDebug, int defaultPriority, String serviceId, String clientId, int callBack) {
		pieceHeader1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><UcapService transactionId=\"".toCharArray();
		pieceHeader2 = ("\" debug=\"" + taskDebug + "\" priority=\"" + defaultPriority + "\" serviceId=\"" + serviceId + "\" clientId=\"" + clientId + "\" timeStamp=\"").toCharArray();
		pieceHeader3 = ("\" callBack=\"" + callBack + "\">").toCharArray();
	}

	public StringBuffer makeHeader(String sid, String template) {
		return new StringBuffer().append(pieceHeader1)
				.append(sid)
				.append("\" template=\"" + template)
				.append(pieceHeader2)
				.append(System.currentTimeMillis()).append(pieceHeader3);
	}
	
	public String makeRequestXml(String sid, String template, ArrayList<Param> params) {
		StringBuffer xml = makeHeader(sid, template);
		for (Param param : params) {
			xml.append('<').append(param.getKey()).append('>').append(param.getValue()).append("</").append(param.getKey()).append('>');
		}
		xml.append("</UcapService>");
		return xml.toString();
	}

	public String requestDcps(String requestXml) throws UnknownHostException, IOException, ConnectException, DcpsTimeoutException, DcpsProtocalException {
		Socket socket;
		socket = new Socket(dcpsAddress, dcpsPort);
		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		String line = null;
		line = br.readLine();
		if (line.equals("UCAPSERVICE")) {
			pw.println("START");
			pw.flush();
			line = br.readLine();
			if (line.equals("PREPARED")) {
				pw.println(requestXml + "\r\nOVER");
				pw.flush();
				// 返回结果
				line = br.readLine();
				pw.println("CLOSE");
				pw.close();
				br.close();
				socket.close();
				if (line.startsWith("TIMEOUT")) {
					throw new DcpsTimeoutException("Dcps transaction timeout");
				}
				return line;
			}
			throw new DcpsProtocalException("Dcps protocal error, the returned word PREPARED is expected.");
		}
		throw new DcpsProtocalException("Dcps protocal error, the returned word UCAPSERVICE is expected.");
	}
}