package com.youcan.core.ha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/*
 * id/ip/port/weight/threads/wait/total
 */
/*
 * 刚刚被选取的时候，可以把权限设低；
 * 任务开始和任务结束的时候，分别发送一个状态；
 */
public class CcsCenter extends Thread {
	HashMap<String, CcsServer> dcpsInfoArray;
	ServerSocket watchSocket = null, adminSocket = null;
	
	CcsCenter() {
		dcpsInfoArray = new HashMap<String, CcsServer>();
	}
	
	public void addDcpsInfo(CcsServer dcpsInfo) {
		if (dcpsInfo == null || dcpsInfoArray.containsKey(dcpsInfo.getId())) {
			return;
		} else {
			dcpsInfoArray.put(dcpsInfo.getId(), dcpsInfo);
		}
	}
	
	public CcsServer selectDcps() {
		//wait * weight / threads
		return null;
	}
	
	public boolean startListening(String ip, int port) {
		try {
			watchSocket = new ServerSocket();
			watchSocket.bind(new InetSocketAddress(ip, port));
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void run() {
		PrintWriter response;
		BufferedReader request;
		String line;
		String[] status;
		while(true) {
			try {
				Socket socket = watchSocket.accept();
				response = new PrintWriter(
						socket.getOutputStream(), true);
				request = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				while((line = request.readLine()) != null) {
					if (line.startsWith("DCPS-STATUS:")) {
						//DCPS-STATUS:ID:当前时间:已完成的总任务数(不包括进行中的):空闲线程数
						status = line.split(":");
						if (status.length == 5) {
							CcsServer di = dcpsInfoArray.get(status[1]);
							di.setLastTime(Long.parseLong(status[2]));
							di.setCompleted(Long.parseLong(status[3]));
							di.setWaiting(Integer.parseInt(status[4]));
						}
					} else if (line.startsWith("DCPS-START:")){
						//DCPS-START:ID:IP地址:端口号:开始时间:线程数:权重
						status = line.split(":");
						if (status.length == 7) {
							CcsServer di = new CcsServer(status[1], status[2], Integer.parseInt(status[3]));
							di.setStartTime(Long.parseLong(status[4]));
							di.setTotalWorkers(Integer.parseInt(status[5]));
							di.setWeight(Integer.parseInt(status[6]));
							this.addDcpsInfo(di);
						}
					} else {
						response.println(line);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
