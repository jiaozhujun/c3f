package com.youcan.base.content;

public enum ContentGroup {
	WCL(1, "wcl", "网络摄像头直播内容"), VIDEO(2, "video", "视频内容"), JOURNALS(3,
			"journals", "报纸期刊内容");

	private final int id;
	private final String name;
	private final String label;

	private ContentGroup(int id, String name, String label) {
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
		StringBuffer json = new StringBuffer("{\"cgs\":[");
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
