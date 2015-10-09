package com.youcan.core.strutsExt;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;

public class ObjectResultType extends StrutsResultSupport {
	private static final long serialVersionUID = 7245281594489917666L;

	private String objectName;
	private String contentType;
	public ObjectResultType() {
	}

	public ObjectResultType(String location) {
		super(location);
	}

	public ObjectResultType(String location, boolean parse, boolean encode) {
		super(location, parse, encode);
	}

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		 HttpServletResponse response =  (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);  
//	        String contentType = conditionalParse(contentType, invocation);  
	        if(contentType == null){  
	            contentType = "text/plain; charset=UTF-8";  
	        }  
	        response.setContentType(contentType);  
	        PrintWriter out = response.getWriter();  
	        Object result = invocation.getStack().findValue(objectName);  
	        out.println(result);  
	        out.flush();  
	        out.close();  
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
