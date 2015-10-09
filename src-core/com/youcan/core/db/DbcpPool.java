package com.youcan.core.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.dbcp.BasicDataSource;

import com.youcan.core.l;
import com.youcan.core.db.PoolInterface;

public class DbcpPool implements PoolInterface {
	private BasicDataSource basicDataSource;
	private String configFile;

	public DbcpPool() {
		l.info("init DbcpPool");
	}
	
	@Override
	public void setConfig(String configFile) {
		this.configFile = configFile;
	}

	@Override
	public void init() {
		XMLConfiguration config;
		try {
			config = new XMLConfiguration(configFile);
			basicDataSource = new BasicDataSource();
			basicDataSource.setDriverClassName(config.getString("jdbc.driverClassName"));
			basicDataSource.setUrl(config.getString("jdbc.url"));
			basicDataSource.setUsername(config.getString("jdbc.username"));
			basicDataSource.setPassword(config.getString("jdbc.password"));

			basicDataSource.setTestWhileIdle(false);
			basicDataSource.setTestOnReturn(false);
			basicDataSource.setTestOnBorrow(config.getBoolean("dataSource.testOnBorrow"));
			basicDataSource.setValidationQuery(config.getString("dataSource.validationQuery"));
//			basicDataSource.setValidationQueryTimeout(config.getInt("dataSource.validationInterval"));

			basicDataSource.setMaxActive(config.getInt("dataSource.maxActive"));
			basicDataSource.setInitialSize(config.getInt("dataSource.initialSize"));
			basicDataSource.setMaxWait(config.getInt("dataSource.maxWait"));
			basicDataSource.setMaxIdle(config.getInt("dataSource.maxIdle"));
			basicDataSource.setRemoveAbandonedTimeout(config.getInt("dataSource.removeAbandonedTimeOut"));
			basicDataSource.setMinEvictableIdleTimeMillis(config.getInt("dataSource.minEvictableIdleTimeMillis"));
			basicDataSource.setMinIdle(config.getInt("dataSource.minIdle"));

			basicDataSource.setLogAbandoned(config.getBoolean("dataSource.logAbandoned"));
			basicDataSource.setRemoveAbandoned(config.getBoolean("dataSource.removeAbandoned"));
		} catch (ConfigurationException e) {
			l.error("ConfigurationException when DbcpPool init");
			basicDataSource = null;
		} finally {
			config = null;
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		if (basicDataSource == null) {
			init();
		}
		if (basicDataSource != null) {
			return basicDataSource.getConnection();
		}
		l.error("dataSource is null when getConnection");
		return null;
	}

	@Override
	public void close() throws SQLException {
		if (basicDataSource != null) {
			basicDataSource.close();
			basicDataSource = null;
		}
	}

	@Override
	public void debug() {
		l.debug("TODO: debug");
	}
}
