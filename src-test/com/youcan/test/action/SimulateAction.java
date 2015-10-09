package com.youcan.test.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.youcan.core.l;

public class SimulateAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -510415591211173650L;

	private String simid;

	public SimulateAction() {
		//
	}

	@Override
	public String execute() throws Exception {
		ActionContext ctx = ActionContext.getContext();
//		@SuppressWarnings("rawtypes")
//		Map session = ActionContext.getContext().getSession();
		HttpServletRequest request = (HttpServletRequest)ctx.get(StrutsStatics.HTTP_REQUEST);
		String queryString = request.getQueryString();
		l.debug("---------------------------------------------");
		l.debug("---------------------------------------------");
		l.debug("---- Method信息 ----");
		l.debug(request.getMethod());

		l.debug("---- Header信息 ----");
		Enumeration<String> headNames = request.getHeaderNames();
		String headName = null;
		while (headNames.hasMoreElements()) {
			headName = headNames.nextElement();
			l.debug("[Header]" + headName + " : " + request.getHeader(headName));
		}

		l.debug("---- QueryString信息 ----");
		l.debug(request.getQueryString());
		
		l.debug("---- Attribute信息 ----");
		Enumeration<String> attributeNames = request.getAttributeNames();
		String attributeName = null;
		while (attributeNames.hasMoreElements()) {
			attributeName = attributeNames.nextElement();
			l.debug("[Attribute]" + attributeName + " : " + request.getAttribute(attributeName));
		}

		l.debug("---- Parameter信息 ----");
		Enumeration<String> parameterNames = request.getParameterNames();
		String parameterName = null;
		while (parameterNames.hasMoreElements()) {
			parameterName = parameterNames.nextElement();
			l.debug("[Parameter]" + parameterName + " : " + request.getParameter(parameterName));
		}

		l.debug("---- Action信息 ----");
		l.debug(ctx.getName());
		l.debug("---- CharacterEncoding信息 ----");
		l.debug(request.getCharacterEncoding());
		l.debug("---- ContentType信息 ----");
		l.debug(request.getContentType());
		l.debug("---- AuthType信息 ----");
		l.debug(request.getAuthType());
		l.debug("---- RemoteAddr信息 ----");
		l.debug(request.getRemoteAddr());
		l.debug("---- RemoteHost信息 ----");
		l.debug(request.getRemoteHost());
		l.debug("---- SessionId信息 ----");
		l.debug(request.getSession().getId());

		if (simid == null || simid.equals("")) {
			if (queryString == null || "".equals(queryString)) {
				simid = ctx.getName();
			} else {
				simid = ctx.getName() + '+' + queryString;
			}
		}
		l.debug("id:" + simid);
		return SUCCESS;
	}

	public String getSimid() {
		return simid;
	}

	public void setSimid(String simid) {
		this.simid = simid;
	}
}