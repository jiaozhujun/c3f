package com.youcan.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.youcan.core.db.PoolProvider;

public class Db {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	/**
	 * 获取数据库操作环境
	 * @throws SQLException
	 */
	public Db() throws SQLException {
		this.conn = PoolProvider.getConnection();
		this.conn.setAutoCommit(true);
		this.stmt = conn.createStatement();
	}

	public Connection getConnection() {
		return conn;
	}

	/**
	 * 执行SQL操作
	 * @param sql 要执行的SQL语句
	 * @return 执行成功还是失败
	 * @throws SQLException
	 */
	public boolean execute(String sql) throws SQLException {
		if (stmt == null)
			return false;
		return stmt.execute(sql);
	}

	/**
	 * 执行SELECT查询
	 * @param sql 要执行的SQL查询语句
	 * @return 返回ResultSet结果集
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		rs = stmt.executeQuery(sql);
		return rs;
	}

	/**
	 * 执行INSERT、UPDATE等操作，不需取出AUTO_INCREMENT字段的值
	 * @param sql 要执行的SQL语句
	 * @return 受到影响的行数
	 * @throws SQLException
	 */
	public int executeUpdate(String sql) throws SQLException {
		if (stmt == null) {
			return -1;
		}
		return stmt.executeUpdate(sql);
	}

	/**
	 * 执行INSERT、UPDATE等操作
	 * @param sql 要执行的SQL语句
	 * @param returnKey 是否需要取出AUTO_INCREMENT字段的值
	 * @return 受到影响的行数
	 * @throws SQLException
	 */
	public int executeUpdate(String sql, boolean returnKey) throws SQLException {
		if (stmt == null) {
			return -1;
		}
		if (returnKey) {
			return stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		}
		return stmt.executeUpdate(sql);
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
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭数据库连接，包括rs、stmt和conn
	 * @param db
	 */
	public static void close(Db db) {
		if (db != null) {
			try {
				if (db.rs != null) {
					db.rs.close();
					db.rs = null;
				}
				if (db.stmt != null) {
					db.stmt.close();
					db.stmt = null;
				}
				if (db.conn != null) {
					db.conn.close();
					db.conn = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取结果集的行数
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
	 * 取得单个的整数值,
	 * 假定SQL查询语句返回一个单个的整数值，比如COUNT(*)
	 * @return 单个的值，如果没有查找到结果则返回-1
	 * @throws SQLException
	 */
	public int getSingleInt() throws SQLException {
		if (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

	/**
	 * 取得自动生成的key值，针对AUTO_INCREMENT字段
	 * 需要使用executeUpdate(String sql, boolean returnKey)函数，并且设置returnKey=true
	 * @return AUTO_INCREMENT字段的值
	 * @throws SQLException
	 */
	public int getGeneratedKey() throws SQLException {
		rs = stmt.getGeneratedKeys();
		int autoIncKeyFromApi = 0;
		if (rs.next()) {
			autoIncKeyFromApi = rs.getInt(1);
		}
		return autoIncKeyFromApi;
	}
	public long getGeneratedKeyLong() throws SQLException {
		rs = stmt.getGeneratedKeys();
		long autoIncKeyFromApi = 0;
		if (rs.next()) {
			autoIncKeyFromApi = rs.getLong(1);
		}
		return autoIncKeyFromApi;
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}
	
	public boolean isAutoCommit() {
		try {
			return conn.getAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void commit() throws SQLException {
		conn.commit();
	}

	public void addBatch(String sql) throws SQLException {
		stmt.addBatch(sql);
	}

	public int[] executeBatch() throws SQLException {
		if (stmt == null)
			return null;
		return stmt.executeBatch();
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

	public void setSavepoint(String name) throws SQLException {
		conn.setSavepoint(name);
	}

	public void setSavepoint() throws SQLException {
		conn.setSavepoint();
	}

	public static void debug() {
		PoolProvider.debug();
	}
}
