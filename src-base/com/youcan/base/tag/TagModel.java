package com.youcan.base.tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class TagModel {
	/**
	 * 根据关键字 分页查询标签列表集合
	 * 
	 * @param qw
	 *            - 标签关键字
	 * @param start
	 *            - 页码
	 * @param limit
	 *            - 单页条数
	 * @return
	 */
	public List<TagDao> getTags(String qw, int start, int limit) {
		Db db = null;
		List<TagDao> tags = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from tag ");
			if (qw != null && !qw.equals(""))
				sql.append("where name like '" + qw + "%' ");
			sql.append("limit " + (start - 1) * limit + ", " + limit);
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				tags = new ArrayList<TagDao>(num);
				while (rs.next()) {
					TagDao tag = new TagDao();
					tag.setId(rs.getInt(1));
					tag.setName(rs.getString(2));
					tags.add(tag);
				}
			}
		} catch (SQLException e) {
			l.error("根据关键字 分页查询标签列表集合 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return tags;
	}

	/**
	 * 根据关键字 查询标签列表个数
	 * 
	 * @param qw
	 *            - 标签关键字
	 * @return
	 */
	public int getTagsCount(String qw) {
		Db db = null;
		int result = 0;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from tag ");
			if (qw != null && !qw.equals(""))
				sql.append("where name like '" + qw + "%' ");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					result = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			l.error("根据关键字 查询标签列表个数 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	/**
	 * 获取标签对象
	 * 
	 * @param id
	 *            - 标签ID
	 * @return
	 */
	public TagDao getTag(int id) {
		Db db = null;
		TagDao tag = null;
		if (id <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from tag where id " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					tag = new TagDao();
					tag.setId(rs.getInt(1));
					tag.setName(rs.getString(2));
				}
			}
		} catch (SQLException e) {
			l.error("获取标签对象 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return tag;
	}

	/**
	 * 根据标签名称 获取 标签对象
	 * 
	 * @param name
	 *            - 标签名称
	 * @return
	 */
	public TagDao getTag(String name) {
		Db db = null;
		TagDao tag = null;
		if (name == null || name.equals(""))
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from tag where name '" + name + "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					tag = new TagDao();
					tag.setId(rs.getInt(1));
					tag.setName(rs.getString(2));
				}
			}
		} catch (SQLException e) {
			l.error("获取标签对象 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return tag;
	}

	/**
	 * 根据视频ID获取标签个数
	 * 
	 * @param vid
	 *            - 视频ID
	 * @return
	 */
	public int getTagsCountByVid(int vid) {
		Db db = null;
		int result = 0;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from tagmap where ");
			sql.append("vid = " + vid);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					result = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			l.error("根据关键字 查询标签列表个数 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	/**
	 * 增加标签
	 * 
	 * @param name
	 *            - 标签名称
	 * @return 0 失败 -1 参数错误 500 系统错误 101 重复
	 */
	public int addTag(String name) {
		Db db = null;
		if (name == null || name.equals(""))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from tag where name = '" + name + "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("insert into tag(name) values('" + name
								+ "')");
						return db.executeUpdate(sql.toString());
					}
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("增加标签 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 增加标签关系
	 * 
	 * @param id
	 *            - 标签ID
	 * @param vid
	 *            - 新闻ID
	 * @return
	 */
	public int addTagMap(int id, int vid) {
		Db db = null;
		if (id <= 0 || vid <= 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from tagmap where name id = " + id
					+ " and vid = " + vid);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("insert into tagmap(id, vid) values(" + id
								+ ", " + vid + ")");
						return db.executeUpdate(sql.toString());
					}
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("增加标签关系 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 删除标签
	 * 
	 * @param id
	 *            - 标签ID
	 * @return
	 */
	public boolean deleteTag(int id) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("delete from tag where id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;
		} catch (SQLException e) {
			l.error("删除标签 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 删除标签关系
	 * 
	 * @param id
	 *            - 标签ID
	 * @param vid
	 *            - 新闻ID
	 * @return
	 */
	public boolean deleteTagMap(int id, int vid) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("delete from tagmap where id = " + id + " and vid = "
					+ vid);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;
		} catch (SQLException e) {
			l.error("删除标签关系 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	public int updateTag(int id, String name) {
		Db db = null;
		if (id <= 0 || name == null || name.equals(""))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from tag where name = '" + name
					+ "' and id != " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("update tag set name = '" + name
								+ "' where id = " + id);
						return db.executeUpdate(sql.toString());
					}
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("更新标签 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}
}
