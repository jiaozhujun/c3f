package com.youcan.base.content;

import java.sql.SQLException;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public abstract class ContentModel {
	private ContentGroup contentGroup;

	public ContentModel(ContentGroup contentGroup) {
		this.contentGroup = contentGroup;
	}

	public boolean setState(long id, int state) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer().append("UPDATE content_")
					.append(contentGroup.getName()).append(" SET state=")
					.append(state).append(" WHERE id=").append(id);
			if (db.executeUpdate(sql.toString()) == 1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			l.error(contentGroup.getLabel() + "内容更改状态 SQL Exception ", e);
			return false;
		} finally {
			Db.close(db);
		}
	}

	/*
	 * 删除指定ID的内容 默认为先放入回收站content_(group)_del
	 */
	public boolean deleteContent(long id) {
		return deleteContent(id, false);
	}

	public boolean deleteContent(long id, boolean force) {
		Db db = null;
		boolean result = false;
		StringBuffer sql = new StringBuffer();
		try {
			db = new Db();
			if (!force) {
				// 非强制删除
				sql.append("INSERT INTO content_")
						.append(contentGroup.getName())
						.append("_del SELECT * FROM content_")
						.append(contentGroup.getName()).append(" WHERE id=")
						.append(id);
				if (db.executeUpdate(sql.toString()) == 1) {
					// 放入回收站后再删除
					sql.delete(0, sql.length());
					sql.append("DELETE FROM content_")
							.append(contentGroup.getName())
							.append(" WHERE id=").append(id);
					if (db.executeUpdate(sql.toString()) == 1) {
						result = true;
					}
				}
			} else {
				// 强制删除
				sql.append("DELETE FROM content_")
						.append(contentGroup.getName()).append(" WHERE id=")
						.append(id);
				if (db.executeUpdate(sql.toString()) == 1) {
					result = true;
				}
			}
		} catch (SQLException e) {
			result = false;
			l.error(contentGroup.getLabel() + "内容回收站 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	public boolean restoreContent(long id) {
		Db db = null;
		boolean result = false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("INSERT INTO content_")
					.append(contentGroup.getName())
					.append(" SELECT * FROM content_")
					.append(contentGroup.getName()).append("_del WHERE id=")
					.append(id);
			if (db.executeUpdate(sql.toString()) == 1) {
				sql.delete(0, sql.length());
				sql.append("DELETE FROM content_")
						.append(contentGroup.getName())
						.append("_del WHERE id=").append(id);
				if (db.executeUpdate(sql.toString()) == 1) {
					result = true;
				}
			}
		} catch (SQLException e) {
			result = false;
			l.error(contentGroup.getLabel() + "内容回收站 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}
}
