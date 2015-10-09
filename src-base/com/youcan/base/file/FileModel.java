package com.youcan.base.file;

import java.sql.SQLException;

import com.youcan.core.l;
import com.youcan.core.db.Db;

public class FileModel {
	private FileGroup filegroup;

	public FileModel(FileGroup filegroup) {
		this.filegroup = filegroup;
	}

	/**
	 * 逻辑删除文件
	 * 
	 * @param id
	 *            - 文件ID
	 * @return
	 */
	public boolean deleteFile(int id) {
		return deleteFile(id, false);
	}

	/**
	 * 删除文件到file_(group)_del
	 * 
	 * @param id
	 *            - 文件ID
	 * @param force
	 *            - 是否强制删除 否 ：到回收站 是 ： 强制删除
	 * @return
	 */
	public boolean deleteFile(int id, boolean force) {
		Db db = null;
		boolean result = false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer();
			if (!force) {
				sql.append(
						"insert into file_" + filegroup.getName()
								+ "_del select * from file_"
								+ filegroup.getName()).append(
						" where id = " + id);
				if (db.executeUpdate(sql.toString()) == 1) {
					sql.delete(0, sql.length());
					sql.append("delete from file_" + filegroup.getName()
							+ " where id = " + id);
					if (db.executeUpdate(sql.toString()) == 1)
						result = true;
				}
			} else {
				sql.append("delete from file_" + filegroup.getName()
						+ " where id = " + id);
				if (db.executeUpdate(sql.toString()) == 1)
					result = true;
			}
		} catch (SQLException e) {
			l.error(filegroup.getLabel() + "文件回收站 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}

	public boolean restoreFile(int id) {
		Db db = null;
		boolean result = false;
		try {
			db = new Db();
			StringBuffer sql = new StringBuffer("insert into file_"
					+ filegroup.getName() + " select * from file_"
					+ filegroup.getName() + "_del where id = " + id);
			if (db.executeUpdate(sql.toString()) == 1)
				result = true;
		} catch (Exception e) {
			l.error(filegroup.getLabel() + "文件回收站恢复 SQL Exception ", e);
		} finally {
			Db.close(db);
		}
		return result;
	}
}
