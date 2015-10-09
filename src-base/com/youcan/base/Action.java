package com.youcan.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.youcan.core.l;

public class Action {
	public static void outputXmlResponse(StringBuffer sb) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml; charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(sb.toString());
		} catch (IOException e) {
			l.error("outputXmlResponse error : " + sb);
		}
	}

	public static String getSessionId() {
		return ServletActionContext.getRequest().getSession().getId();
	}
	
	public static String getIp() {
		return ServletActionContext.getRequest().getRemoteAddr();
	}
}
