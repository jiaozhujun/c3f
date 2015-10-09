package com.youcan.base.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.youcan.base.content.ContentGroup;
import com.youcan.core.l;
import com.youcan.core.db.Db;

public class CategoryModel {
	private ContentGroup contentGroup;
	private static ArrayList<CategoryDao>[] categoryFullList;
	private static ChangeInterface[] cis;

	public CategoryModel(ContentGroup contentGroup) {
		this.contentGroup = contentGroup;
		if (cis == null) {
			ContentGroup[] cg = ContentGroup.values();
			cis = new ChangeInterface[cg.length];
			for (int i = 0; i < cg.length; i++) {
				cis[i] = null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void refreshAll() {
		categoryFullList = null;
		ContentGroup[] cg = ContentGroup.values();
		categoryFullList = new ArrayList[cg.length];
		for (int i = 0; i < cg.length; i++) {
			categoryFullList[i] = refreshList(cg[i]);
		}
	}

	public static ArrayList<CategoryDao> getCategoryList(
			ContentGroup contentGroup) {
		return categoryFullList[contentGroup.ordinal()];
	}

	public static CategoryDao getCategory(ContentGroup contentGroup, int id) {
		ArrayList<CategoryDao> cl = categoryFullList[contentGroup.ordinal()];
		int length = cl.size();
		for (int i = 0; i < length; i++) {
			if (cl.get(i).getId() == id) {
				return cl.get(i);
			}
		}
		return null;
	}

	public static int getCategoryOrdinary(ContentGroup contentGroup, int id) {
		ArrayList<CategoryDao> cl = categoryFullList[contentGroup.ordinal()];
		int length = cl.size();
		for (int i = 0; i < length; i++) {
			if (cl.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}

	public boolean add(CategoryDao cat) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("INSERT INTO category (title,contentGroup,parent,ord) VALUES (\"")
					.append(cat.getTitle()).append("\",\"")
					.append(contentGroup.getId()).append("\",\"")
					.append(cat.getParent()).append("\",\"")
					.append(cat.getOrd()).append("\")");
			if (db.executeUpdate(sql.toString()) == 1) {
				if (cis[contentGroup.ordinal()] != null) {
					cis[contentGroup.ordinal()].doChange(list());
				}
				return true;
			}
			return false;
		} catch (SQLException e) {
			l.error("分类增加 SQL Exception ", e);
			return false;
		} finally {
			Db.close(db);
		}
	}

	public int getNextOrd() {
		int maxOrd = 0;
		Db db = null;
		ResultSet rs = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer().append(
					"SELECT MAX(ord) FROM category WHERE contentGroup=")
					.append(contentGroup.getId());
			rs = db.executeQuery(sql.toString());
			while (rs.next()) {
				maxOrd = rs.getInt(1);
			}
		} catch (SQLException e) {
			maxOrd = 0;
			l.error("获取分类NextOrd SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return maxOrd + 1;
	}

	public boolean delete(int id) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer().append(
					"DELETE FROM category WHERE id=").append(id);
			if (db.executeUpdate(sql.toString()) == 1) {
				if (cis[contentGroup.ordinal()] != null) {
					cis[contentGroup.ordinal()].doChange(list());
				}
				return true;
			}
			return false;
		} catch (SQLException e) {
			l.error("删除分类 SQL Exception ", e);
			return false;
		} finally {
			Db.close(db);
		}
	}

	public ArrayList<CategoryDao> list() {
		Db db = null;
		ArrayList<CategoryDao> categoryDaoList = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("SELECT id,title,parent,childNum,contentNum,ord FROM category WHERE contentGroup=")
					.append(contentGroup.getId()).append(" ORDER BY ord ASC");
			ResultSet rs = db.executeQuery(sql.toString());
			CategoryDao categoryDao = null;
			categoryDaoList = new ArrayList<CategoryDao>(db.getNumRows());
			while (rs.next()) {
				categoryDao = new CategoryDao();
				categoryDao.setId(rs.getInt(1));
				categoryDao.setTitle(rs.getString(2));
				categoryDao.setParent(rs.getInt(3));
				categoryDao.setChild(rs.getInt(4));
				categoryDao.setContent(rs.getInt(5));
				categoryDao.setOrd(rs.getInt(6));
				categoryDaoList.add(categoryDao);
			}
		} catch (SQLException e) {
			categoryDaoList = null;
			l.error("获取分类列表 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return categoryDaoList;
	}

	public static ArrayList<CategoryDao> refreshList(ContentGroup contentGroup) {
		Db db = null;
		ArrayList<CategoryDao> categoryDaoList = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("SELECT id,title,parent,childNum,contentNum,ord FROM category WHERE contentGroup=")
					.append(contentGroup.getId()).append(" ORDER BY ord ASC");
			ResultSet rs = db.executeQuery(sql.toString());
			CategoryDao categoryDao = null;
			categoryDaoList = new ArrayList<CategoryDao>(db.getNumRows());
			while (rs.next()) {
				categoryDao = new CategoryDao();
				categoryDao.setId(rs.getInt(1));
				categoryDao.setTitle(rs.getString(2));
				categoryDao.setParent(rs.getInt(3));
				categoryDao.setChild(rs.getInt(4));
				categoryDao.setContent(rs.getInt(5));
				categoryDao.setOrd(rs.getInt(6));
				categoryDaoList.add(categoryDao);
			}
		} catch (SQLException e) {
			categoryDaoList = null;
			l.error("获取分类列表 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return categoryDaoList;
	}

	public static StringBuffer getJson(ContentGroup contentGroup) {
		StringBuffer json = new StringBuffer("{\"cats\":[");
		Db db = null;
		ResultSet rs = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("SELECT id,title,parent FROM category WHERE contentGroup=")
					.append(contentGroup.getId()).append(" ORDER BY ord ASC");
			rs = db.executeQuery(sql.toString());
			int i = 0;
			while (rs.next()) {
				if (i++ > 0) {
					json.append(',');
				}
				json.append("{\"id\":").append(rs.getInt(1))
						.append(",\"title\":\"").append(rs.getString(2))
						.append("\",\"parent\":").append(rs.getInt(3))
						.append("}");
			}
			json.append("]}");
		} catch (SQLException e) {
			l.error("获取分类JSON SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return json;
	}

	/**
	 * 获取子分类列表（JSON）
	 * 
	 * @param contentGroup
	 *            - 内容组
	 * @param parentId
	 *            - 父分类
	 * @return
	 */
	public static StringBuffer getJson(ContentGroup contentGroup, int parentId) {
		StringBuffer json = new StringBuffer("{\"cats\":[");
		Db db = null;
		ResultSet rs = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("SELECT id,title,parent FROM category WHERE contentGroup=")
					.append(contentGroup.getId())
					.append(" and parent = " + parentId)
					.append(" ORDER BY ord ASC");
			rs = db.executeQuery(sql.toString());
			int i = 0;
			while (rs.next()) {
				if (i++ > 0) {
					json.append(',');
				}
				json.append("{\"id\":").append(rs.getInt(1))
						.append(",\"title\":\"").append(rs.getString(2))
						.append("\",\"parent\":").append(rs.getInt(3))
						.append("}");
			}
			json.append("]}");
		} catch (SQLException e) {
			l.error("获取分类JSON SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return json;
	}

	/**
	 * 根据内容组获取分类(XML)
	 * 
	 * @param contentGroup
	 *            - 内容组
	 * @return XML
	 */
	public static Document getXml(ContentGroup contentGroup) {
		Db db = null;
		Document doc = null;
		ResultSet rs = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("SELECT id,title,parent FROM category WHERE contentGroup=")
					.append(contentGroup.getId())
					.append(" and parent = 0 ORDER BY ord ASC");
			rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("categorys");
				sql.delete(0, sql.length());
				while (rs.next()) {
					Element child = DocumentHelper.createElement("category");
					child.addAttribute("id", String.valueOf(rs.getInt(1)));
					child.addAttribute("name", rs.getString(2));
					child.addAttribute("parent", String.valueOf(rs.getInt(3)));
					sql.append(
							"SELECT id,title,parent FROM category WHERE contentGroup=")
							.append(contentGroup.getId())
							.append(" and parent = " + rs.getInt(1))
							.append(" ORDER BY ord ASC");
					rs = db.executeQuery(sql.toString());
					if (db.getNumRows() != 0) {
						sql.delete(0, sql.length());
						while (rs.next()) {
							Element node = DocumentHelper
									.createElement("category");
							node.addAttribute("id",
									String.valueOf(rs.getInt(1)));
							node.addAttribute("name", rs.getString(2));
							node.addAttribute("parent",
									String.valueOf(rs.getInt(3)));
							child.add(node);
						}
					}
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("获取分类XML SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}

	/**
	 * 获取子分类列表（XML）)
	 * 
	 * @param contentGroup
	 *            - 内容组
	 * @param parentId
	 *            - 父分类
	 * @return
	 */
	public static Document getXml(ContentGroup contentGroup, int parentId) {
		Db db = null;
		Document doc = null;
		ResultSet rs = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("SELECT id,title,parent FROM category WHERE contentGroup=")
					.append(contentGroup.getId())
					.append(" and parent = " + parentId)
					.append(" ORDER BY ord ASC");
			rs = db.executeQuery(sql.toString());
			if (db.getNumRows() != 0) {
				doc = DocumentHelper.createDocument();
				Element root = DocumentHelper.createElement("categorys");
				while (rs.next()) {
					Element child = DocumentHelper.createElement("category");
					child.addElement("id")
							.addText(String.valueOf(rs.getInt(1)));
					child.addElement("name").addText(rs.getString(2));
					root.add(child);
				}
				doc.add(root);
			}
		} catch (SQLException e) {
			l.error("获取分类XML SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return doc;
	}

	/**
	 * 增加分类
	 * 
	 * @param title
	 *            - 标题
	 * @param content
	 *            - 内容
	 * @param parent
	 *            - 父分类
	 * @param ord
	 *            - 排序
	 * @return 0 失败 -1 参数错误 101 重复 500 系统异常
	 */
	public int addCategory(String title, int parent, ContentGroup cg,
			int ord) {
		Db db = null;
		if (title == null || title.equals("") || parent < 0 || ord <= 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from category where title = '" + title
					+ "'");
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("insert into category(title, contentGroup, parent, ord) values(");
						sql.append("'" + title + "', ")
								.append(cg.getId() + ",")
								.append(parent + ",").append(ord + ")");
						return db.executeUpdate(sql.toString());
					}
					return 101;
				}
			} else
				return 500;

		} catch (SQLException e) {
			l.error("增加分类 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 更新分类
	 * 
	 * @param id
	 *            - 分类ID
	 * @param title
	 *            - 标题
	 * @param content
	 *            - 内容
	 * @param parent
	 *            - 父分类
	 * @return
	 */
	public int updateCategory(int id, String title, int parent) {
		Db db = null;
		if (title == null || title.equals("") || parent < 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from category where title = '" + title
					+ "' and id != " + id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					if (rs.getInt(1) == 0) {
						sql.delete(0, sql.length());
						sql.append("update category set ");
						sql.append("title = '" + title + "', ").append(
								"parent = " + parent + " ");
						sql.append("where id = " + id);
						return db.executeUpdate(sql.toString());
					}
					//JIAO: 请使用常量表示结果代码
					return 101;
				}
			} else
				return 500;

		} catch (SQLException e) {
			l.error("更新分类 SQL Exception ", e);
			return 500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 更新分类子分类个数
	 * 
	 * @param id
	 *            - 分类ID
	 * @param childNum
	 *            - 内容个数
	 * @return
	 */
	public boolean setChildNum(int id, int childNum) {
		Db db = null;
		if (id <= 0)
			return false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update category set childNum = " + childNum
					+ " where id = " + id);
			if (db.executeUpdate(sql.toString()) == 1)
				return true;
		} catch (SQLException e) {
			l.error("更新分类子分类个数 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 更新分类内容个数
	 * 
	 * @param id
	 *            - 分类ID
	 * @param contentNum
	 *            - 内容个数
	 * @return
	 */
	public boolean setContentNum(int id, int contentNum) {
		Db db = null;
		if (id <= 0)
			return false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("update category set contentNum = " + contentNum
					+ " where id = " + id);
			if (db.executeUpdate(sql.toString()) == 1)
				return true;
		} catch (SQLException e) {
			l.error("更新分类内容个数 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return false;
	}

	/**
	 * 获取子分类个数
	 * 
	 * @param parent
	 *            - 父分类
	 * @return 0 没有子分类 -1 参数错误 -500 系统异常
	 */
	public int getChildNum(int parent, ContentGroup cg) {
		Db db = null;
		if (parent <= 0)
			return -1;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from category where parent = " + parent
					+ " and contentGroup = " + cg.getId());
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					return rs.getInt(1);
				}
			} else
				//JIAO:请为else加上{}
				return -500;
		} catch (SQLException e) {
			l.error("更新分类 SQL Exception ", e);
			return -500;
		} finally {
			Db.close(db);
		}
		return 0;
	}

	/**
	 * 获取分类
	 * 
	 * @param id
	 *            - 分类ID
	 * @return
	 */
	public CategoryDao getCategory(int id) {
		Db db = null;
		CategoryDao category = null;
		if (id <= 0)
			return null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			sql.append("select id, title, parent, childNum, contentNum, ord from category where id = "
					+ id);
			ResultSet rs = db.executeQuery(sql.toString());
			if (db.getNumRows() == 1) {
				while (rs.next()) {
					category = new CategoryDao();
					category.setId(rs.getInt(1));
					category.setTitle(rs.getString(2));
					category.setChild(rs.getInt(3));
					category.setContent(rs.getInt(4));
					category.setOrd(rs.getInt(5));
				}
			}
		} catch (SQLException e) {
			l.error("获取分类 SQL Exception ", e);
			category = null;
		} finally {
			Db.close(db);
		}
		return category;
	}

	public boolean del(int id) {
		Db db = null;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer()
					.append("DELETE FROM category WHERE id=").append(id)
					.append(" and childNum = 0 ");
			if (db.executeUpdate(sql.toString()) == 1) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			l.error("删除分类 SQL Exception ", e);
			return false;
		} finally {
			Db.close(db);
		}
	}
}