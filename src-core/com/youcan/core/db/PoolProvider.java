package com.youcan.core.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import com.youcan.core.l;
import com.youcan.core.db.PoolInterface;
import com.youcan.core.db.PoolProvider;

public class PoolProvider {
	private static PoolInterface pool;

	public static void setPool(PoolInterface pool) {
		PoolProvider.pool = pool;
	}

	public static Connection getConnection() throws SQLException {
		return pool.getConnection();
	}

	public static void close() {
		if (pool != null) {
			try {
				pool.close();
			} catch (SQLException e1) {
				l.error("SQLException when PoolProvider close.");
			} finally {
				Enumeration<Driver> drivers = DriverManager.getDrivers();
		        while (drivers.hasMoreElements()) {
		            Driver driver = drivers.nextElement();
		            try {
		                DriverManager.deregisterDriver(driver);
		            } catch (SQLException e) {
		            	l.error("SQLException when deregisterDriver.");
		            }
		        }
				pool = null;
			}
		}
	}
	
	public static void debug() {
		if (pool != null) {
			pool.debug();
		}
	}
}
