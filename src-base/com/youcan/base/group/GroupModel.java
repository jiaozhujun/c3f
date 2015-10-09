package com.youcan.base.group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class GroupModel {
	/**
	 * 获取所有用户组（XML格式）
	 * 
	 * @return
	 */
	/*
	 * public Element getGroups() { Db db = null; Element root = null; try { db
	 * = new Db(); StringBuffer sql = new StringBuffer();
	 * sql.append("select id, name, grantids from user_group "); ResultSet rs =
	 * db.executeQuery(sql.toString()); if (db.getNumRows() != 0) { root =
	 * DocumentHelper.createElement("groups"); while (rs.next()) { Element group
	 * = DocumentHelper.createElement("group");
	 * 
	 * group.addElement("id") .addText(String.valueOf(rs.getInt(1)));
	 * group.addElement("name").addText(rs.getString(2));
	 * 
	 * group.addAttribute("id", String.valueOf(rs.getInt(1)));
	 * group.addAttribute("name", rs.getString(2)); String grantids =
	 * rs.getString(3); sql.delete(0, sql.length()); // Element child =
	 * getGrants(grantids); // if (child != null) // group.add(child);
	 * root.add(group); } } } catch (SQLException e) { e.printStackTrace(); //
	 * l.error("获取所有用户组 SQL Exception ", e); } finally { Db.close(db);
	 * } return root; }
	 */

	/**
	 * 获取所有用户组
	 * 
	 * @return
	 */
	public List<GroupDao> getGroups() {
		Db db = null;
		List<GroupDao> groups = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, grantids from user_group ");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				groups = new ArrayList<GroupDao>();
				while (rs.next()) {
					GroupDao group = new GroupDao();
					group.setId(rs.getInt(1));
					group.setName(rs.getString(2));
					group.setGrantids(rs.getString(3));
					groups.add(group);
				}
			}
		} catch (SQLException e) {
			l.error("获取所有用户组 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return groups;
	}

	/**
	 * 增加用户组
	 * 
	 * @param name
	 *            - 用户组名称
	 * @param grantids
	 *            - 权限IDs
	 * @return 0 失败 -1 参数错误 101 重复 500 系统异常
	 */
	public int addGroup(String name, String grantids) {
		Db db = null;
		if (name == null || name.equals("") || grantids == null
				|| grantids.equals(""))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from user_group where name = '" + name
					+ "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append(
								"insert into user_group(name, grantids) values(")
								.append("'" + name + "',")
								.append("'" + grantids + "')");
						return db.executeUpdate(sql.toString());
					}
					//JIAO: 请使用常量定义结果代码
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("增加用户组 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 更新用户组
	 * 
	 * @param id
	 *            - 用户组ID
	 * @param name
	 *            - 用户组名称
	 * @param grantids
	 *            - 权限IDs
	 * @return 0 失败 -1 参数错误 101 重复 500 系统异常
	 */
	public int updateGroup(int id, String name, String grantids) {
		Db db = null;
		if (id <= 0 || name == null || name.equals("") || grantids == null
				|| grantids.equals(""))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from user_group where id != " + id
					+ " and name = '" + name + "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) != 1) {
						sql.delete(0, sql.length());
						sql.append(
								"update user_group set name = '" + name + "', ")
								.append("grantids = '" + grantids + "' ");
						sql.append("where id = " + id);
						return db.executeUpdate(sql.toString());
					}
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("更新用户组 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 删除用户组
	 * 
	 * @param id
	 *            - 用户组ID
	 * @return
	 */
	public boolean delGroup(int id) {
		Db db = null;
		if (id <= 0)
			return false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("delete from user_group where id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;
		} catch (SQLException e) {
			l.error("删除用户组 SQL Exception ", e);
			return false;
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 获取用户组
	 * 
	 * @param id
	 *            - 用户组ID
	 * @return
	 */
	public GroupDao getGroup(int id) {
		Db db = null;
		GroupDao group = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, grantids from user_group where id = "
					+ id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					group = new GroupDao();
					group.setId(rs.getInt(1));
					group.setName(rs.getString(2));
					group.setGrantids(rs.getString(3));
				}
			}
		} catch (SQLException e) {
			l.error("获取用户组 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return group;
	}

}
