package com.youcan.test.action;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.youcan.core.l;
import com.youcan.test.bean.TestBean;
import com.youcan.test.model.TestModel;

public class TestAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5725075082818733738L;
	
	TestModel testModel;
	ArrayList<TestBean> testList;
	public TestAction() {
		testModel = new TestModel();
	}

	@Override
	public String execute() throws Exception {
		testList = testModel.getTestList();
		return SUCCESS;
    }
	
	public String checkHeader() {
		ActionContext ctx = ActionContext.getContext();
		//Map session = ctx.getSession();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);

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
			l.debug("[Attribute]" + attributeName + " : "+ request.getAttribute(attributeName));
		}

		l.debug("---- Parameter信息 ----");
		Enumeration<String> parameterNames = request.getParameterNames();
		String parameterName = null;
		while (parameterNames.hasMoreElements()) {
			parameterName = parameterNames.nextElement();
			l.debug("[Parameter]" + parameterName + " : "+ request.getParameter(parameterName));
		}

		l.debug("---- CharacterEncoding信息 ----");
		l.debug(request.getCharacterEncoding());
		l.debug("---- ContentType信息 ----");
		l.debug(request.getContentType());
		l.debug("---- AuthType信息 ----");
		l.debug(request.getAuthType());
		l.debug("---- ContentLength信息 ----");
		l.debug(request.getContentLength());
		l.debug("---- RemoteAddr信息 ----");
		l.debug(request.getRemoteAddr());
		l.debug("---- RemoteHost信息 ----");
		l.debug(request.getRemoteHost());
		l.debug("---- SessionId信息 ----");
		l.debug(request.getSession().getId());

		// HttpServletResponse response =
		// (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		return SUCCESS;
	}

	public String xmlList() {
		testList = testModel.getTestList();
		return SUCCESS;
	}

	public String dotest() {
		return "TEST";
	}

	public ArrayList<TestBean> getTestList() {
		return testList;
	}

	public void setTestList(ArrayList<TestBean> testList) {
		this.testList = testList;
	}
	
}
