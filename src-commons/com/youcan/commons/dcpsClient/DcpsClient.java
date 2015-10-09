package com.youcan.commons.dcpsClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.youcan.commons.dcpsClient.DcpsClientConfig;
import com.youcan.commons.dcpsClient.exception.DcpsProtocalException;
import com.youcan.commons.dcpsClient.exception.DcpsTimeoutException;
import com.youcan.core.l;

public class DcpsClient {
	public static DcpsClient instance = null;
	public static final int DCPS_OK = 0;
	public static final int DCPS_ERROR_CONNECT = 1;
	public static final int DCPS_ERROR_UNKNOWN_HOST = 2;
	public static final int DCPS_ERROR_IO = 3;
	public static final int DCPS_ERROR_TIMEOUT = 4;
	public static final int DCPS_ERROR_PROTOCAL = 5;

	/*
	 * 初始化DcpsClient
	 */
	public DcpsClient() {
		//do nothing
	}

	public static DcpsClient getInstance() {
		if (instance == null) {
			instance = new DcpsClient();
		}
		return instance;
	}

	/*
	 * 请求DCPS服务器，发送任务信息
	 */
	@SuppressWarnings("static-method")
	public String requestDcps(String requestXml) throws UnknownHostException, IOException, ConnectException, DcpsTimeoutException, DcpsProtocalException {
		l.debug(requestXml);
		Socket socket;
		socket = new Socket(DcpsClientConfig.dcpsAddress, DcpsClientConfig.dcpsPort);
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
			} else {
				socket.close();
				throw new DcpsProtocalException("Dcps protocal error, the returned word PREPARED is expected.");
			}
		} else {
			socket.close();
			throw new DcpsProtocalException("Dcps protocal error, the returned word UCAPSERVICE is expected.");
		}
	}
}