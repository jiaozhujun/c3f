package com.youcan.core.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface PoolInterface {
	public void setConfig(String configFile);
	public void init();
	public Connection getConnection() throws SQLException;
	public void close() throws SQLException;
	public void debug();
}
