package com.youcan.base.category;

public class CategoryDao {
	public static final int CATEGORY_ALL = 0;
	/*
	 * 分类ID
	 */
	private int id;
	
	/*
	 * 分类名称
	 */
	private String title;
	
	/*
	 * 父分类的ID
	 */
	private int parent;
	
	/*
	 * 子分类的数量
	 */
	private int child;
	
	/*
	 * 内容的数量
	 */
	private int content;

	/*
	 * 排序，正序排列
	 */
	private int ord;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}
}
