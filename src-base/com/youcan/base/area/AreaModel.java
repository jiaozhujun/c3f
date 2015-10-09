package com.youcan.base.area;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class AreaModel {
	/**
	 * 获取所有地区列表
	 * 
	 * @return
	 */
	public static List<AreaDao> refreshAll() {
		Db db = null;
		List<AreaDao> areas = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, parentid, gradeid, childids from area ");
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				areas = new ArrayList<AreaDao>(num);
				while (rs.next()) {
					AreaDao area = new AreaDao();
					area.setId(rs.getInt(1));
					area.setName(rs.getString(2));
					area.setParentid(rs.getInt(3));
					area.setGradeid(rs.getInt(4));
					area.setChildids(rs.getString(5));
					areas.add(area);
				}
			}
		} catch (SQLException e) {
			l.error("获取所有地区列表 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return areas;
	}

	/**
	 * 获取所有地区（JSON）
	 * 
	 * @return JSON
	 */
	public static StringBuffer getJson() {
		Db db = null;
		StringBuffer json = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, parentid, gradeid, childids from area");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				json = new StringBuffer("{\"areas\":[");
				int i = 0;
				while (rs.next()) {
					if (i++ > 0)
						json.append(",");
					json.append("{");
					json.append("\"id\":\"" + rs.getInt(1) + "\",");
					json.append("\"name\":\"" + rs.getString(2) + "\",");
					json.append("\"parentid\":\"" + rs.getInt(3) + "\",");
					json.append("\"gradeid\":\"" + rs.getInt(4) + "\",");
					json.append("\"childs\":\"" + rs.getString(5) + "\"");
					json.append("}");
				}
				json.append("]}");
			}
		} catch (SQLException e) {
			l.error("获取所有地区（JSON） SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return json;
	}

	/**
	 * 获取子类地区（JSON）
	 * 
	 * @param parentId
	 *            - 父类地区ID
	 * @return JSON
	 */
	public static StringBuffer getJson(int parentId) {
		Db db = null;
		StringBuffer json = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, parentid, gradeid, childids from area where parentid = "
					+ parentId);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				json = new StringBuffer("{\"areas\":[");
				int i = 0;
				while (rs.next()) {
					if (i++ > 0)
						json.append(",");
					json.append("{");
					json.append("\"id\":\"" + rs.getInt(1) + "\",");
					json.append("\"name\":\"" + rs.getString(2) + "\",");
					json.append("\"parentid\":\"" + rs.getInt(3) + "\",");
					json.append("\"gradeid\":\"" + rs.getInt(4) + "\",");
					json.append("\"childs\":\"" + rs.getString(5) + "\"");
					json.append("}");
				}
				json.append("]}");
			}
		} catch (SQLException e) {
			l.error("获取子类地区（JSON） SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return json;
	}

	/**
	 * 获取所有地区（XML）
	 * 
	 * @return XML
	 */
	public static Document getXml() {
		Db db = null;
		Document doc = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, parentid, gradeid, childids from area");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				while (rs.next()) {
					doc = DocumentHelper.createDocument();
					Element root = DocumentHelper.createElement("areas");
					while (rs.next()) {
						Element child = DocumentHelper.createElement("area");
						child.addElement("id").addText(
								String.valueOf(rs.getInt(1)));
						child.addElement("name").addText(rs.getString(2));
						child.addElement("parentid").addText(
								String.valueOf(rs.getInt(3)));
						child.addElement("gradeid").addText(
								String.valueOf(rs.getInt(4)));
						child.addElement("childs").addText(rs.getString(5));
						root.add(child);
					}
					doc.add(root);
				}
			}
		} catch (SQLException e) {
			l.error("获取所有地区（XML） SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}

	/**
	 * 获取子类地区（XML）
	 * 
	 * @param parentId
	 *            - 父类地区ID
	 * @return XML
	 */
	public static Document getXml(int parentId) {
		Db db = null;
		Document doc = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, parentid, gradeid, childids from area where parentid = "
					+ parentId);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("areas");
				while (rs.next()) {
					Element child = DocumentHelper.createElement("area");
					child.addElement("id")
							.addText(String.valueOf(rs.getInt(1)));
					child.addElement("name").addText(rs.getString(2));
					child.addElement("parentid").addText(
							String.valueOf(rs.getInt(3)));
					child.addElement("gradeid").addText(
							String.valueOf(rs.getInt(4)));
					child.addElement("childs").addText(rs.getString(5));
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("获取子类地区（XML） SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}

	/**
	 * 获取子类地区IDs
	 * 
	 * @param parentId
	 *            - 父类地区ID
	 * @return XML
	 */
	public String getChilds(int parentId) {
		Db db = null;
		StringBuffer childs = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id from area where parentid = " + parentId);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				childs = new StringBuffer();
				while (rs.next()) {
					childs.append(rs.getInt(1) + ",");
				}
				childs.substring(0, childs.lastIndexOf(","));
			}
		} catch (SQLException e) {
			l.error("获取子类地区IDs SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return childs != null ? childs.toString() : null;
	}

	/**
	 * 增加地区
	 * 
	 * @param name
	 *            - 地区名称
	 * @param parentId
	 *            - 父类地区ID
	 * @param gradeId
	 *            - 级别ID
	 * @return 0 失败 -1 参数错误 101 重复 500 系统异常
	 */
	public int addArea(String name, int parentId, int gradeId) {
		Db db = null;
		if (name == null || name.equals("") || gradeId <= 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from area where name = '" + name + "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append(
								"insert into area(name, parentid, gradeid) values(")
								.append("'" + name + "',")
								.append(parentId + ", ").append(gradeId + ")");
						return db.executeUpdate(sql.toString());
					}
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("增加地区 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 更新地区
	 * 
	 * @param name
	 *            - 地区名称
	 * @param parentId
	 *            - 父类地区ID
	 * @param gradeId
	 *            - 级别ID
	 * @return
	 */
	public int updateArea(int id, String name, int parentId, int gradeId) {
		Db db = null;
		if (name == null || name.equals("") || gradeId <= 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from area where name = '" + name
					+ "' and id != " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("update area set ")
								.append("name = '" + name + "',")
								.append("parentid = " + parentId + ", ")
								.append("gradeid = " + gradeId + " ");
						sql.append("where id = " + id);
						return db.executeUpdate(sql.toString());
					}
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("更新地区 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 删除地区
	 * 
	 * @param id
	 *            - 地区ID
	 * @param force
	 *            - 是否级联删除 将子类地区也删除
	 * @return
	 */
	public boolean delArea(int id, boolean force) {
		Db db = null;
		if (id <= 0)
			return false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			if (!force) {
				sql.append("select count(*) from area where parentid = " + id);
				ResultSet rs = db.executeQuery(sql.toString());
				if (db.getNumRows() == 1) {
					while (rs.next()) {
						if (rs.getInt(1) == 0) {
							sql.delete(0, sql.length());
							sql.append("delete from area where id = " + id);
							if (db.executeUpdate(sql.toString()) == 1)
								return true;
						}
					}
				}
			} else {
				sql.append("delete from area where id = " + id
						+ " or parentid = " + id);
				if (db.executeUpdate(sql.toString()) != 0)
					return true;
			}
		} catch (SQLException e) {
			l.error("删除地区 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 更新子类地区
	 * 
	 * @param id
	 *            - 地区ID
	 * @param childs
	 *            - 子类地区
	 * @return
	 */
	public boolean setChilds(int id, String childs) {
		Db db = null;
		if (id <= 0 || childs == null || childs.equals(""))
			return false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update area set childids = '" + childs + "' ");
			sql.append("where id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;
		} catch (SQLException e) {
			l.error("更新子类地区 SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 获取地区
	 * 
	 * @param id
	 *            - 地区ID
	 * @return
	 */
	public AreaDao getArea(int id) {
		Db db = null;
		AreaDao area = null;
		if (id <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, parentid, gradeid from area where id = "
					+ id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				area = new AreaDao();
				while (rs.next()) {
					area.setId(rs.getInt(1));
					area.setName(rs.getString(2));
					area.setParentid(rs.getInt(3));
					area.setGradeid(rs.getInt(4));
				}
			}
		} catch (SQLException e) {
			l.error("获取地区 SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return area;
	}
}
