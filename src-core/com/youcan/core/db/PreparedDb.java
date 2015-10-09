package com.youcan.core.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

public class PreparedDb {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public static void debug() {
		PoolProvider.debug();
	}

	public PreparedDb() throws SQLException {
		this.conn = PoolProvider.getConnection();
	}

	/**
	 * 关闭数据库连接
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedDb db) {
		try {
			if (db.rs != null) {
				db.rs.close();
				db.rs = null;
			}
			if (db.ps != null) {
				db.ps.close();
				db.ps = null;
			}
			if (db.conn != null) {
				db.conn.close();
				db.conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commit() throws SQLException {
		conn.commit();
	}

	public void clearParams() throws SQLException {
		ps.clearParameters();
	}

	public void checkPs() throws SQLException {
		if (this.ps == null) {
			throw new SQLException("PreparedStatement not init");
		}
	}

	/**
	 * @param sql
	 * @param params
	 *            第一个参数为是否返回自动生成的key 其他参数为sql参数
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery() throws SQLException {
		checkPs();
		rs = ps.executeQuery();
		return rs;
	}

	public int executeUpdate() throws SQLException {
		checkPs();
		int result = ps.executeUpdate();
		return result;
	}

	/**
	 * 取得自动生成的key值，针对AUTO_INCREMENT字段 需要使用executeUpdate(String sql, boolean
	 * returnKey)函数，并且设置returnKey=true
	 * 
	 * @return AUTO_INCREMENT字段的值
	 * @throws SQLException
	 */
	public int getGeneratedKey() throws SQLException {
		rs = ps.getGeneratedKeys();
		int autoIncKeyFromApi = 0;
		if (rs.next()) {
			autoIncKeyFromApi = rs.getInt(1);
		}
		return autoIncKeyFromApi;
	}

	/**
	 * 获取结果集的行数
	 * 
	 * @return 结果集的行数
	 * @throws SQLException
	 */
	public int getNumRows() throws SQLException {
		int numRows = 0;
		if (rs.last()) {
			numRows = rs.getRow();
			rs.beforeFirst();// 光标回滚
		}
		return numRows;
	}

	/**
	 * 取得单个的整数值, 假定SQL查询语句返回一个单个的整数值，比如COUNT(*)
	 * 
	 * @return 单个的值，如果没有查找到结果则返回-1
	 * @throws SQLException
	 */
	public int getSingleInt() throws SQLException {
		if (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

	public long getSingleLong() throws SQLException {
		if (rs.next()) {
			return rs.getLong(1);
		}
		return -1;
	}

	public void prepareSql(String sql) throws SQLException {
		this.prepareSql(sql, false);
	}

	public void prepareSql(String sql, boolean autoGeneratedKeys) throws SQLException {
		if (autoGeneratedKeys) {
			this.ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} else {
			this.ps = conn.prepareStatement(sql);
		}
	}

	public boolean isAutoCommit() {
		try {
			return conn.getAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void rollback() {
		try {
			if (conn != null) {
				conn.rollback();
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}

	public void setParams(Object... params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			int parameterIndex = i + 1;
			if (param != null) {
				ps.setObject(parameterIndex, param);
			} else {
				throw new SQLException("params[" + i + "]is null");
			}
		}
	}

	public void setString(int index, String value) throws SQLException {
		ps.setString(index, value);
	}

	public void setInt(int index, int value) throws SQLException {
		ps.setInt(index, value);
	}

	public void setBoolean(int index, boolean value) throws SQLException {
		ps.setBoolean(index, value);
	}

	public void setDate(int index, Date value) throws SQLException {
		ps.setDate(index, value);
	}

	public void setDate(int index, java.util.Date value) throws SQLException {
		java.sql.Date date = new java.sql.Date(value.getTime());
		ps.setDate(index, date);
	}

	public void setTime(int index, Time value) throws SQLException {
		ps.setTime(index, value);
	}

	public void setTimestamp(int index, Timestamp value) throws SQLException {
		ps.setTimestamp(index, value);
	}

	public void setLong(int index, long value) throws SQLException {
		ps.setLong(index, value);
	}

	public void setFloat(int index, float value) throws SQLException {
		ps.setFloat(index, value);
	}

	public void setObject(int index, Object obj) throws SQLException {
		ps.setObject(index, obj);
	}

	public void setSavepoint() throws SQLException {
		conn.setSavepoint();
	}

	public void setSavepoint(String name) throws SQLException {
		conn.setSavepoint(name);
	}

}