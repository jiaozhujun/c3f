package com.youcan.base.file;

import com.youcan.base.content.ContentGroup;

public enum FileGroup {
	JOURNALS(1, "journals", "报纸"), VIDEO(2, "video", "网络视频");
	private final int id;
	private final String name;
	private final String label;

	private FileGroup(int id, String name, String label) {
		this.id = id;
		this.name = name;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
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
