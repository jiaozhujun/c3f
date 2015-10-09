package com.youcan.base.dict;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;

import com.youcan.core.l;
import com.youcan.core.db.Db;
import com.youcan.core.util.DateTime;
import com.youcan.core.util.FileUtil;

public class DictModel {
	public DictModel() {
		//do nothing
	}

	// dictMeta的数量
	public int getDictMetaNum() {
		int num = 0;
		Db db = null;
		try {
			db = new Db();
			ResultSet rs = db.executeQuery("SELECT COUNT(*) FROM dict_meta");
			int numRows = db.getNumRows();
			if (numRows == 1) {
				if (rs.next()) {
					num = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			num = -1;
			l.error("字典表查询个数 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return num;
	}

	// 获取dictMeta列表
	public ArrayList<DictMetaDao> getDictMetaList() {
		ArrayList<DictMetaDao> dictMetaList = null;
		Db db = null;
		try {
			db = new Db();
			ResultSet rs = db
					.executeQuery("SELECT dmId, dmName, dmLabel, numData, timeModify FROM dict_meta ORDER BY dmName ASC");
			int numRows = db.getNumRows();
			if (numRows > 0) {
				dictMetaList = new ArrayList<DictMetaDao>(numRows);
				while (rs.next()) {
					DictMetaDao dictMetaDao = new DictMetaDao();
					dictMetaDao.setDmId(rs.getInt(1));
					dictMetaDao.setDmName(rs.getString(2));
					dictMetaDao.setDmLabel(rs.getString(3));
					dictMetaDao.setNumData(rs.getInt(4));
					dictMetaDao.setTimeModify(DateTime.dtFormatFull.format(new Long(rs.getLong(5))));
					dictMetaList.add(dictMetaDao);
				}
			}
		} catch (SQLException e) {
			dictMetaList = null;
			l.error("字典表查询列表 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return dictMetaList;
	}

	// 创建dictMeta下拉列表控件HTML
	public boolean createDictMetaSelect(ArrayList<DictMetaDao> dictMetaList) {
		boolean result = false;
		StringBuilder htmlSb = new StringBuilder(
				"<select name=\"dict_meta\" id=\"dict_meta\"><option value=\"0\">-- 请选择配置项 --</option>");
		if (dictMetaList == null) {
			result = false;
		} else {
			for (DictMetaDao dictMetaDao : dictMetaList) {
				htmlSb.append("<option value=\"" + dictMetaDao.getDmId()
						+ "\">" + dictMetaDao.getDmName() + "-"
						+ dictMetaDao.getDmLabel() + "</option>");
			}
			htmlSb.append("</select>");
			result = FileUtil.writeFile(
					ServletActionContext.getServletContext().getRealPath(
							"/template/block/dict_meta.html"), htmlSb, "utf-8");
		}
		return result;
	}

	// 添加一个dictMeta项
	public boolean addDictMeta(String dmName, String dmLabel) {
		boolean result = false;
		int num = 0;
		Db db = null;
		try {
			db = new Db();
			num = db.executeUpdate("INSERT INTO dict_meta VALUES (null,'"
					+ dmName + "','" + dmLabel + "',0,"
					+ System.currentTimeMillis() + ")");
			if (num == 1) {
				result = true;
			}
		} catch (SQLException e) {
			result = false;
			l.error("字典表增加 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	// 修改一个dictMeta项
	public boolean editDictMeta(int dmId, String dmName, String dmLabel) {
		boolean result = false;
		int num = 0;
		Db db = null;
		try {
			db = new Db();
			num = db.executeUpdate("UPDATE dict_meta SET dmName='" + dmName
					+ "',dmLabel='" + dmLabel + "',timeModify='"
					+ System.currentTimeMillis() + "' WHERE dmId='" + dmId
					+ "'");
			if (num == 1) {
				result = true;
			}
		} catch (SQLException e) {
			result = false;
			l.error("字典表修改 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	// 修改一个dictMeta项中的data的数量
	public boolean updateDictMetaNumData(int dmId, String act) {
		boolean result = false;
		int num = 0;
		Db db = null;
		try {
			db = new Db();
			num = db.executeUpdate("UPDATE dict_meta SET numData=numData" + act
					+ ",timeModify='" + System.currentTimeMillis()
					+ "' WHERE dmId='" + dmId + "'");
			if (num == 1) {
				result = true;
			}
		} catch (SQLException e) {
			result = false;
			l.error("字典表数据个数修改 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	// 删除一个dictMeta项
	public boolean deleteDictMeta(int dmId) {
		boolean result = false;
		int num = 0;
		Db db = null;
		try {
			db = new Db();
			num = db.executeUpdate("DELETE FROM dict_meta WHERE dmId='" + dmId
					+ "'");
			if (num == 1) {
				result = true;
			}
		} catch (SQLException e) {
			result = false;
			l.error("字典表删除 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	public String getDmName(int dmId) {
		String dmName = "";
		Db db = null;
		try {
			db = new Db();
			ResultSet rs = db
					.executeQuery("SELECT dmName FROM dict_meta WHERE dmId='"
							+ dmId + "'");
			int numRows = db.getNumRows();
			if (numRows == 1) {
				if (rs.next()) {
					dmName = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			dmName = "";
			l.error("字典表查询字典名称 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return dmName;
	}

	public DictMetaDao getDictMeta(int dmId) {
		DictMetaDao dictMetaDao = new DictMetaDao();
		Db db = null;
		try {
			db = new Db();
			ResultSet rs = db
					.executeQuery("SELECT dmId, dmName, dmLabel, numData, timeModify FROM dict_meta WHERE dmId='"
							+ dmId + "'");
			int numRows = db.getNumRows();
			if (numRows == 1) {
				if (rs.next()) {
					dictMetaDao.setDmId(rs.getInt(1));
					dictMetaDao.setDmName(rs.getString(2));
					dictMetaDao.setDmLabel(rs.getString(3));
					dictMetaDao.setNumData(rs.getInt(4));
					//JIAO:这里将long转换为Long类型
					dictMetaDao.setTimeModify(DateTime.dtFormatFull.format(new Long(rs.getLong(5))));
				}
			}
		} catch (SQLException e) {
			dictMetaDao = null;
			l.error("字典表查询字典 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return dictMetaDao;
	}

	// dictData的数量
	public int getDictDataNum(int dmId) {
		int num = 0;
		Db db = null;
		try {
			db = new Db();
			ResultSet rs = db
					.executeQuery("SELECT COUNT(*) FROM dict_data WHERE dmId='"
							+ dmId + "'");
			int numRows = db.getNumRows();
			if (numRows == 1) {
				if (rs.next()) {
					num = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			num = -1;
			l.error("字典数据表某字典的数据个数 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return num;
	}

	// 获取dictData列表
	public ArrayList<DictDataDao> getDictDataList(int dmId) {
		Db db = null;
		ArrayList<DictDataDao> dictDataList = null;
		try {
			db = new Db();
			ResultSet rs = db
					.executeQuery("SELECT ddId, dmId, dataLabel, dataValue, grade, timeModify FROM dict_data WHERE dmId='"
							+ dmId + "' ORDER BY grade DESC");
			int numRows = db.getNumRows();
			if (numRows > 0) {
				dictDataList = new ArrayList<DictDataDao>(numRows);
				while (rs.next()) {
					DictDataDao dictDataDao = new DictDataDao();
					dictDataDao.setDdId(rs.getInt(1));
					dictDataDao.setDmId(rs.getInt(2));
					dictDataDao.setDataLabel(rs.getString(3));
					dictDataDao.setDataValue(rs.getString(4));
					dictDataDao.setGrade(rs.getInt(5));
					dictDataDao.setTimeModify(DateTime.dtFormatFull.format(new Long(rs.getLong(6))));
					dictDataList.add(dictDataDao);
				}
			}
		} catch (SQLException e) {
			dictDataList = null;
			l.error("字典数据表列表查询 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return dictDataList;
	}

	// 创建dictData下拉列表控件HTML
	public boolean createDictDataSelect(DictMetaDao dictMetaDao,
			ArrayList<DictDataDao> dictDataList) {
		boolean result = false;
		StringBuilder htmlSb = new StringBuilder("<select name=\""
				+ dictMetaDao.getDmName() + "\" id=\""
				+ dictMetaDao.getDmName() + "\"><option value=\"0\">-- "
				+ dictMetaDao.getDmLabel() + " --</option>");
		if (dictDataList == null) {
			result = false;
		} else {
			for (DictDataDao dictDataDao : dictDataList) {
				htmlSb.append("<option value=\"" + dictDataDao.getDataValue()
						+ "\">" + dictDataDao.getDataLabel() + "</option>");
			}
			htmlSb.append("</select>");
			result = FileUtil.writeFile(
					ServletActionContext.getServletContext().getRealPath(
							"/template/block/DD_" + dictMetaDao.getDmName()
									+ ".html"), htmlSb, "utf-8");
		}
		return result;
	}

	// 添加一个dictData项
	public boolean addDictData(int dmId, String dataLabel, String dataValue,
			int grade) {
		boolean result = false;
		Db db = null;
		try {
			db = new Db();
			int num = db.executeUpdate("INSERT INTO dict_data VALUES (null,'"
					+ dmId + "','" + dataLabel + "','" + dataValue + "','"
					+ grade + "','" + System.currentTimeMillis() + "')");
			if (num == 1) {
				result = true;
				this.updateDictMetaNumData(dmId, "+1");
			}
		} catch (SQLException e) {
			result = false;
			l.error("字典数据表增加 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	// 修改一个dictData项
	public boolean editDictData(int ddId, String dataLabel, String dataValue,
			int grade) {
		boolean result = false;
		Db db = null;
		try {
			db = new Db();
			int num = db.executeUpdate("UPDATE dict_data SET dataLabel='"
					+ dataLabel + "',dataValue='" + dataValue + "',grade='"
					+ grade + "',timeModify='" + System.currentTimeMillis()
					+ "' WHERE ddId='" + ddId + "'");
			if (num == 1) {
				result = true;
			}
		} catch (SQLException e) {
			result = false;
			l.error("字典数据表修改 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	// 删除一个dictData项
	public boolean deleteDictData(int dmId, int ddId) {
		boolean result = false;
		Db db = null;
		try {
			db = new Db();
			int num = db.executeUpdate("DELETE FROM dict_data WHERE dmId='"
					+ dmId + "' AND ddId='" + ddId + "'");
			if (num == 1) {
				result = true;
				this.updateDictMetaNumData(dmId, "-1");
			}
		} catch (SQLException e) {
			result = false;
			l.error("字典数据表删除 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}
}