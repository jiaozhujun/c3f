package com.youcan.base.grant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class GrantModel {
	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	public static List<GrantDao> refreshAll() {
		Db db = null;
		List<GrantDao> grants = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, name from user_grant");
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				grants = new ArrayList<GrantDao>(num);
				while (rs.next()) {
					GrantDao grant = new GrantDao();
					grant.setId(rs.getInt(1));
					grant.setName(rs.getString(2));
					grants.add(grant);
				}
			}
		} catch (SQLException e) {
			l.error("权限列表 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return grants;
	}

	/**
	 * 获取权限对象
	 * 
	 * @param id
	 *            - 权限ID
	 * @return
	 */
	public GrantDao getGrant(int id) {
		Db db = null;
		GrantDao grant = null;
		if (id <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, name from user_grant where id = " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					grant = new GrantDao();
					grant.setId(rs.getInt(1));
					grant.setName(rs.getString(2));
				}
			}
		} catch (SQLException e) {
			l.error("权限对象 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return grant;
	}

	/**
	 * 权限列表(JSON)
	 * 
	 * @return
	 */
	public static StringBuffer getJson() {
		Db db = null;
		StringBuffer json = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, name from user_grant");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				json = new StringBuffer("{\"grants\":[");
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
			l.error("权限列表(JSON) SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return json;
	}

	/**
	 * 权限列表(XML)
	 * 
	 * @return
	 */
	public static Document getXml() {
		Db db = null;
		Document doc = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, name from user_grant");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("grants");
				while (rs.next()) {
					Element child = DocumentHelper.createElement("grant");
					child.addElement("id")
							.addText(String.valueOf(rs.getInt(1)));
					child.addElement("name").addText(rs.getString(2));
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("权限列表(XML) SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}

	/**
	 * 
	 * @param grantIds
	 * @return
	 */
	public List<GrantDao> getGrants(String grantIds) {
		Db db = null;
		List<GrantDao> grants = null;
		if (grantIds == null || grantIds.equals(""))
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer(
					"select id, name from user_grant where id in (" + grantIds
							+ ")");
			ResultSet rs = db.executeQuery(sql.toString());
			int num;
			if ((num = db.getNumRows()) != 0) {
				grants = new ArrayList<GrantDao>(num);
				while (rs.next()) {
					GrantDao grant = new GrantDao();
					grant.setId(rs.getInt(1));
					grant.setName(rs.getString(2));
					grants.add(grant);
				}
			}
		} catch (SQLException e) {
			l.error("权限列表 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return grants;
	}
}
