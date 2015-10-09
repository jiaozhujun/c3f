package com.youcan.test.action;

import com.opensymphony.xwork2.ActionSupport;
import com.youcan.core.system.TomcatMan;

public class AdminAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5725075082818733738L;
	TomcatMan tm = null;
	private String obj = null;
	private String act = null;
	
	public AdminAction() {
		tm = new TomcatMan();
	}

	public String doAdmin() {
		if (obj == null) {
			return "start";
		}
		tm.shutdown();
		return SUCCESS;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

}
