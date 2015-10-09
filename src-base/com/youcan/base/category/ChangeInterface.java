package com.youcan.base.category;

import java.util.ArrayList;

import com.youcan.base.content.ContentGroup;

public interface ChangeInterface {
	public ContentGroup tellContentGroup();
	public boolean doChange(ArrayList<CategoryDao> categoryList);
}
