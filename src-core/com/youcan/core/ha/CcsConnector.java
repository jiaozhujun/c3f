package com.youcan.core.ha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CcsConnector {
	private String host;
	private int port;
	private boolean isLong;
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;
	String line = null;
	
	public CcsConnector(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public boolean open(String clientInfo) {
		try {
			socket = new Socket(host, port);
			pw = new PrintWriter(socket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			pw.println(clientInfo);
			//pw.flush();
			line = br.readLine();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void close() {
		pw.close();
		try {
			br.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw = null;
		br = null;
		socket = null;
	}

	public void sendStatus(String status) {
		//if (socket == null || socket.isClosed() || !socket.isConnected()) {
			//open("RE");
		//}
		pw.println(status);
		//pw.flush();
//		line = br.readLine();
	}
}
