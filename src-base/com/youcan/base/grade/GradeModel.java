package com.youcan.base.grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class GradeModel {
	/**
	 * 获取所有级别
	 * 
	 * @return
	 */
	public static List<GradeDao> refreshAll() {
		Db db = null;
		List<GradeDao> grades = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from grade ");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				grades = new ArrayList<GradeDao>();
				while (rs.next()) {
					GradeDao grade = new GradeDao();
					grade.setId(rs.getInt(1));
					grade.setName(rs.getString(2));
					grades.add(grade);
				}
			}
		} catch (SQLException e) {
			l.error("获取所有级别 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return grades;
	}

	/**
	 * 获取所有级别（JSON）
	 * 
	 * @return
	 */
	public static StringBuffer getJson() {
		Db db = null;
		StringBuffer json = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from grade ");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				json = new StringBuffer("{\"grades\":[");
				while (rs.next()) {
					json.append("{");
					json.append("\"id\":\"" + rs.getInt(1) + "\",");
					json.append("\"name\":\"" + rs.getString(2) + "\",");
					json.append("}");
				}
				json.append("]}");
			}
		} catch (SQLException e) {
			l.error("获取所有级别(JSON) SQL Exception ", e);
		} finally {
			Db.close(db);
		}

		return json;
	}

	/**
	 * 获取所有级别（XML）
	 * 
	 * @return
	 */
	public static Document getXml() {
		Db db = null;
		Document doc = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from grade ");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("grades");
				while (rs.next()) {
					Element child = DocumentHelper.createElement("grade");
					child.addElement("id")
							.addText(String.valueOf(rs.getInt(1)));
					child.addElement("name").addText(rs.getString(2));
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("获取所有级别(XML) SQL Exception ", e);
		} finally {
			Db.close(db);
		}

		return doc;
	}

	/**
	 * 获取所有级别（数组）
	 * 
	 * @return
	 */
	public static int[] getGrades() {
		Db db = null;
		int[] grades = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id from grade ");
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				grades = new int[num];
				int i = 0;
				while (rs.next()) {
					grades[i++] = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			l.error("获取所有级别 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return grades;
	}

	/**
	 * 获取级别
	 * 
	 * @param id
	 *            - 级别ID
	 * @return
	 */
	public GradeDao getGrade(int id) {
		Db db = null;
		GradeDao grade = null;
		if (id <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from grade where id = " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				grade = new GradeDao();
				while (rs.next()) {
					grade.setId(rs.getInt(1));
					grade.setName(rs.getString(2));
				}
			}
		} catch (SQLException e) {
			l.error("获取级别 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return grade;
	}

	/**
	 * 增加级别
	 * 
	 * @param name
	 *            - 级别名称
	 * @return
	 */
	public int addGrade(String name) {
		Db db = null;
		if (name == null || name.equals(""))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from grade where name = '" + name + "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("insert into grade(name) values('" + name
								+ "')");
						return db.executeUpdate(sql.toString());
					}
					//JIAO:请使用常量来定义结果代码
					return 101;
				}
			} else {
				return 500;
			}
		} catch (SQLException e) {
			l.error("增加级别 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 更新级别
	 * 
	 * @param id
	 *            - 级别ID
	 * @param name
	 *            - 级别名称
	 * @return
	 */
	public int updateGrade(int id, String name) {
		Db db = null;
		if (id <= 0 || name == null || name.equals(""))
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from grade where name = '" + name
					+ "' and id != " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("update grade set name = '" + name
								+ "' where id = " + id);
						return db.executeUpdate(sql.toString());
					}
					//JIAO:请使用常量来定义结果代码
					return 101;
				}
			} else
				return 500;
		} catch (SQLException e) {
			l.error("更新级别 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 删除级别
	 * 
	 * @param id
	 *            - 级别ID
	 * @return
	 */
	public boolean delGrade(int id) {
		Db db = null;
		if (id <= 0)
			return false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("delete from grade where id = " + id);
			int res = db.executeUpdate(sql.toString());
			if (res == 1)
				return true;
		} catch (SQLException e) {
			l.error("删除级别 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}
}
