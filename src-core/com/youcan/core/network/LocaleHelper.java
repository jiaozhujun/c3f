package com.youcan.core.network;

import javax.servlet.http.HttpServletRequest;

import com.youcan.core.l;

public class LocaleHelper {

	/**
	 * 根据IP地址获取城市ID
	 * @param ipAddr IP地址
	 * @return 城市ID
	 */
	public static int getCityFromIp(String ipAddr) {
		//TODO 根据ipAddr信息获取城市ID，需要考虑内网IP、外网IP、路由IP等情况
		return 0;
	}
	
	/**
	 * 根据请求获取客户端IP地址
	 * @param request HTTP请求
	 * @return IP地址字符串
	 */
	public static String getIpAddr(HttpServletRequest request) {
		/*
		 * 如果通过了多级反向代理，X-Forwarded-For的值并不止一个，而是一串ip值
		 * 究竟哪个才是真正的用户端的真实IP呢？
		 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
		 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
		 * 用户真实IP为： 192.168.1.110
		 */
		if (request == null) {
			l.error("request is null");
			return null;
		}
		
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
//			ip = request.getHeader("Proxy-Client-IP");
//			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//				ip = request.getHeader("WL-Proxy-Client-IP");
//				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//					ip = request.getRemoteAddr();
//				}
//			}
		} else {
			for (String ipOne : ip.split(",")) {
				if (!"unknown".equalsIgnoreCase(ipOne = ipOne.trim())) {
					ip = ipOne;
					break;
				}
			}
		}
		return ip;
	}
}
