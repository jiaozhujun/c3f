package com.youcan.base.user;

import com.youcan.base.content.ContentGroup;

public enum UserGroup {
	PC(1, "pc", "PC 客户端"), PHONE(2, "phone", "手机客户端"), IPAD(3, "IPad",
			"IPad客户端");
	private int id;
	private String name;
	private String label;

	private UserGroup(int id, String name, String label) {
		this.id = id;
		this.name = name;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}
	
	public static StringBuffer getJson() {
		StringBuffer json = new StringBuffer("{\"fgs\":[");
		ContentGroup[] cgs = ContentGroup.values();
		for (int i = 0; i < cgs.length; i++) {
			json.append("{\"id\":").append(cgs[i].getId())
					.append(",\"name\":\"").append(cgs[i].getName())
					.append("\",\"label\":\"").append(cgs[i].getLabel())
					.append("\"}");
			if (i < cgs.length - 1) {
				json.append(",");
			}
		}
		json.append("]}");
		return json;
	}

}
