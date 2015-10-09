package com.youcan.base.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class InterfaceModel {
	/**
	 * 获取接口列表
	 * 
	 * @return
	 */
	public static List<InterfaceDao> refreshAll() {
		Db db = null;
		List<InterfaceDao> interfaces = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, namespace, name, mid, type from interface");
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				interfaces = new ArrayList<InterfaceDao>(num);
				while (rs.next()) {
					InterfaceDao interfacedao = new InterfaceDao();
					interfacedao.setId(rs.getInt(1));
					interfacedao.setNamespace(rs.getString(2));
					interfacedao.setName(rs.getString(3));
					interfacedao.setMid(rs.getInt(1));
					interfacedao.setType(rs.getShort(5));
					interfaces.add(interfacedao);
				}
			}
		} catch (SQLException e) {
			l.error("接口获取异常 SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return interfaces;
	}

	/**
	 * 接口 权限认证
	 * 
	 * @param namespace
	 * @param name
	 * @param grantids
	 * @return
	 */
	public boolean checkInterface(String namespace, String name, String grantids) {
		Db db = null;
		boolean result = false;
		if (namespace == null || namespace.equals("") || name == null
				|| name.equals("") || grantids == null || grantids.equals(""))
			return false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select count(*) from module as m, interface as i ");
			sql.append("where ");
			sql.append("i.namespace = '"
					+ namespace
					+ "' and i.name = '"
					+ name
					+ "' and m.mid in (select gm.mid from grant_module as gm where id in ("
					+ grantids + "))");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					int res = rs.getInt(1);
					if (res == 1)
						result = true;
				}
			}
		} catch (SQLException e) {
			l.error("权限验证 SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	public static List<InterfaceDao> getInterfaces(int mid) {
		Db db = null;
		List<InterfaceDao> interfaces = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, namespace, name, mid, type from interface where mid = "
							+ mid);
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				interfaces = new ArrayList<InterfaceDao>(num);
				while (rs.next()) {
					InterfaceDao interfacedao = new InterfaceDao();
					interfacedao.setId(rs.getInt(1));
					interfacedao.setNamespace(rs.getString(2));
					interfacedao.setName(rs.getString(3));
					interfacedao.setMid(rs.getInt(1));
					interfacedao.setType(rs.getShort(5));
					interfaces.add(interfacedao);
				}
			}
		} catch (SQLException e) {
			l.error("接口获取异常 SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return interfaces;
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
			StringBuffer sql = new StringBuffer(
					"select id, namespace, name, mid, type from interface");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				json = new StringBuffer("{\"interfaces\":[");
				int i = 0;
				while (rs.next()) {
					if (i++ > 0)
						json.append(",");
					json.append("{\"id\":\"" + rs.getInt(1)
							+ "\", \"namespace\":\"" + rs.getString(2)
							+ "\", \"name\":\"" + rs.getString(3)
							+ "\", \"mid\":\"" + rs.getInt(4)
							+ "\",\"type\":\"" + rs.getShort(5) + "\"}");
				}
				json.append("]}");
			}
		} catch (SQLException e) {
			l.error("接口获取(JSON) SQL Exception", e);
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
			StringBuffer sql = new StringBuffer(
					"select id, namespace, name, mid, type from interface");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("interfaces");
				while (rs.next()) {
					Element child = DocumentHelper.createElement("interface");
					child.addElement("id")
							.addText(String.valueOf(rs.getInt(1)));
					child.addElement("namespace").addText(rs.getString(2));
					child.addElement("name").addText(rs.getString(3));
					child.addElement("mid").addText(
							String.valueOf(rs.getInt(4)));
					child.addElement("type").addText(
							String.valueOf(rs.getShort(5)));
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("接口获取(JSON) SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}

	public static StringBuffer getJson(int mid) {
		Db db = null;
		StringBuffer json = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, namespace, name, mid, type from interface where mid = "
							+ mid);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				json = new StringBuffer("{interfaces:[");
				int i = 0;
				while (rs.next()) {
					if (i++ > 0)
						json.append(",");
					json.append("{\"id\":\"" + rs.getInt(1)
							+ "\", \"namespace\":\"" + rs.getString(2)
							+ "\", \"name\":\"" + rs.getString(3)
							+ "\", \"mid\":\"" + rs.getInt(4)
							+ "\",\"type\":\"" + rs.getShort(5) + "\"}");
				}
				json.append("]}");
			}
		} catch (SQLException e) {
			l.error("接口获取(JSON) SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return json;
	}

	public static Document getXml(int mid) {
		Db db = null;
		Document doc = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, namespace, name, mid, type from interface where mid = "
							+ mid);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("interfaces");
				while (rs.next()) {
					Element child = DocumentHelper.createElement("interface");
					child.addElement("id")
							.addText(String.valueOf(rs.getInt(1)));
					child.addElement("namespace").addText(rs.getString(2));
					child.addElement("name").addText(rs.getString(3));
					child.addElement("mid").addText(
							String.valueOf(rs.getInt(4)));
					child.addElement("type").addText(
							String.valueOf(rs.getShort(5)));
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("接口获取(JSON) SQL Exception", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}
}
