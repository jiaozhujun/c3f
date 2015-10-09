package com.youcan.commons.dcpsClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.youcan.commons.dcpsClient.exception.DcpsProtocalException;
import com.youcan.commons.dcpsClient.exception.DcpsRequestException;
import com.youcan.commons.dcpsClient.exception.DcpsRequestInvalidValueException;
import com.youcan.commons.dcpsClient.exception.DcpsRequestLackValueException;
import com.youcan.commons.dcpsClient.exception.DcpsTimeoutException;


public class DcpsRequest {
	protected NodeList tempNl = null;
	protected int tempInt;
	protected String tempStr;
	protected Element topElement = null;
	protected Element tmpElement = null;
	private Document document;
	
	public DcpsRequest() {
		tempStr = null;
		tempInt = 0;
	}
	
	public String getStringValue(Element element, String name) throws DcpsRequestLackValueException,DcpsRequestInvalidValueException {
		tempNl = element.getElementsByTagName(name);
		if (tempNl == null || tempNl.getLength() != 1) {
			throw new DcpsRequestLackValueException();
		}
		tempStr = tempNl.item(0).getTextContent();
		if ("".equals(tempStr)) {
			throw new DcpsRequestInvalidValueException();
		}
		return tempStr;
	}
	
	public String getStringValueTrim(Element element, String name) throws DcpsRequestLackValueException,DcpsRequestInvalidValueException {
		tempNl = element.getElementsByTagName(name);
		if (tempNl == null || tempNl.getLength() != 1) {
			throw new DcpsRequestLackValueException();
		}
		tempStr = tempNl.item(0).getTextContent().trim();
		if ("".equals(tempStr)) {
			throw new DcpsRequestInvalidValueException();
		}
		return tempStr;
	}

	public String getStringValue(Element element, String name, String defaultValue) {
		try {
			return getStringValue(element, name);
		} catch (DcpsRequestException e) {
			return defaultValue;
		}
	}
	
	public String getStringValueTrim(Element element, String name, String defaultValue) {
		try {
			return getStringValueTrim(element, name);
		} catch (DcpsRequestException e) {
			return defaultValue;
		}
	}

	public int getIntegerValue(Element element, String name)  throws DcpsRequestLackValueException,DcpsRequestInvalidValueException {
		tempNl = element.getElementsByTagName(name);
		if (tempNl == null || tempNl.getLength() != 1) {
			throw new DcpsRequestLackValueException();
		}
		try {
			tempInt = Integer.parseInt(tempNl.item(0)
					.getTextContent());
			return tempInt;
		} catch (NumberFormatException ne) {
			throw new DcpsRequestInvalidValueException();
		}
	}
	
	public int getIntegerValue(Element element, String name, int defaultValue) {
		try {
			return getIntegerValue(element, name);
		} catch (DcpsRequestException e) {
			return defaultValue;
		}
	}

	public Element getTopElement() {
		return topElement;
	}

	public void setTopElement(Element topElement) {
		this.topElement = topElement;
	}
	
	public static String requestDcps(String requestXml, String dcpsAddress, int dcpsPort) throws UnknownHostException, IOException, ConnectException, DcpsTimeoutException, DcpsProtocalException {
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

	public static String commandDcps(String command, String dcpsAddress, int dcpsPort) throws UnknownHostException, IOException, ConnectException, DcpsProtocalException {
		Socket socket;
		socket = new Socket(dcpsAddress, dcpsPort);
		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		String line = null;
		line = br.readLine();
		if (line.equals("UCAPSERVICE")) {
			pw.println(command);
			pw.flush();
			line = br.readLine();
			return line;
		}
		throw new DcpsProtocalException("Dcps protocal error, the returned word UCAPSERVICE is expected.");
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
