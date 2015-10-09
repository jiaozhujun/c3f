package com.youcan.core.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.sql.DataSource;

import com.youcan.core.db.PoolProvider;

public class Pdb {
	private Connection conn = null;
	private PreparedStatement prepStmt = null;

	public Pdb(String sql) throws SQLException {
		this.conn = PoolProvider.getConnection();
		prepStmt = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	public Pdb(Connection conn, String sql) throws SQLException {
		this.conn = conn;
		prepStmt = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	public Pdb(DataSource ds, String sql) throws SQLException {
		conn = ds.getConnection();
		prepStmt = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	public Connection getConnection() {
		return conn;
	}

	public PreparedStatement getPreparedStatement() {
		return prepStmt;
	}

	public boolean execute() throws SQLException {
		if (prepStmt == null)
			return false;
		return prepStmt.execute();
	}

	public ResultSet executeQuery() throws SQLException {
		return prepStmt.executeQuery();
	}

	public int executeUpdate() throws SQLException {
		if (prepStmt == null) {
			return -1;
		}
		return prepStmt.executeUpdate();
	}

	public void close() {
		try {
			if (prepStmt != null) {
				prepStmt.close();
				prepStmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setString(int index, String value) throws SQLException {
		prepStmt.setString(index, value);
	}

	public void setInt(int index, int value) throws SQLException {
		prepStmt.setInt(index, value);
	}

	public void setBoolean(int index, boolean value) throws SQLException {
		prepStmt.setBoolean(index, value);
	}

	public void setDate(int index, Date value) throws SQLException {
		prepStmt.setDate(index, value);
	}

	public void setDate(int index, java.util.Date value) throws SQLException {
		java.sql.Date date = new java.sql.Date(value.getTime());
		prepStmt.setDate(index, date);
	}

	public void setTime(int index, Time value) throws SQLException {
		prepStmt.setTime(index, value);
	}

	public void setTimestamp(int index, Timestamp value) throws SQLException {
		prepStmt.setTimestamp(index, value);
	}

	public void setLong(int index, long value) throws SQLException {
		prepStmt.setLong(index, value);
	}

	public void setFloat(int index, float value) throws SQLException {
		prepStmt.setFloat(index, value);
	}

	public void setObject(int index, Object obj) throws SQLException {
		prepStmt.setObject(index, obj);
	}

	/**
	 * File file = new File("test/data.txt"); int fileLength = file.length();
	 * InputStream fin = new java.io.FileInputStream(file);
	 * mysql.setBinaryStream(5,fin,fileLength);
	 */
	public void setBinaryStream(int index, InputStream in, int length)
			throws SQLException {
		prepStmt.setBinaryStream(index, in, length);
	}

	public void commit() {
		try {
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getNumRows(ResultSet resultSet) throws SQLException {
        //通过该方法获取结果集的行数
        int numRows = 0;
        if (resultSet.last()) {
            numRows = resultSet.getRow();
            resultSet.beforeFirst();//光标回滚
        }
        return numRows;
    }
}
