package com.youcan.base.module;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class ModuleModel {
	/**
	 * 获取模块列表
	 * 
	 * @return
	 */
	public static List<ModuleDao> refreshAll() {
		Db db = null;
		List<ModuleDao> modules = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer("select id, name from module");
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				modules = new ArrayList<ModuleDao>(num);
				while (rs.next()) {
					ModuleDao module = new ModuleDao();
					module.setId(rs.getInt(1));
					module.setName(rs.getString(2));
					modules.add(module);
				}
			}
		} catch (SQLException e) {
			l.error("模块列表 SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return modules;
	}

	/**
	 * 获取模块ID列表
	 * 
	 * @param id
	 *            - 权限ID
	 * @return
	 */
	public static List<Integer> getIds(int id) {
		Db db = null;
		List<Integer> modules = null;
		if (id <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select mid from grant_module where id = " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				modules = new ArrayList<Integer>(num);
				while (rs.next()) {
					modules.add(new Integer(rs.getInt(1)));
				}
			}
		} catch (SQLException e) {
			l.error("模块ID列表 SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return modules;
	}

	/**
	 * 
	 * @return
	 */
	public static StringBuffer getJson() {
		Db db = null;
		StringBuffer json = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer("select id, name from module");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				json = new StringBuffer("{\"modules\":[");
				int i = 0;
				while (rs.next()) {
					if (i++ > 0)
						json.append(",");
					json.append("{\"id\": \"" + rs.getInt(1)
							+ "\", \"name\":\"" + rs.getString(2) + "\"}");
				}
				json.append("]}");
			}
		} catch (SQLException e) {
			l.error("模块列表(JSON) SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return json;
	}

	/**
	 * 
	 * @return
	 */
	public static Document getXml() {
		Db db = null;
		Document doc = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer("select id, name from module");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("modules");
				while (rs.next()) {
					Element child = DocumentHelper.createElement("module");
					child.addElement("id")
							.addText(String.valueOf(rs.getInt(1)));
					child.addElement("name").addText(rs.getString(2));
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("模块列表(XMl) SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}
}
