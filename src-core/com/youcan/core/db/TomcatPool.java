package com.youcan.core.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.tomcat.jdbc.pool.DataSource;

import com.youcan.core.l;
import com.youcan.core.db.PoolInterface;

public class TomcatPool implements PoolInterface {
	private DataSource dataSource;
	private String configFile;

	public TomcatPool() {
		l.info("init TomcatPool");
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
			dataSource = new DataSource();
			dataSource.setDriverClassName(config.getString("jdbc.driverClassName"));
			dataSource.setUrl(config.getString("jdbc.url"));
			dataSource.setUsername(config.getString("jdbc.username"));
			dataSource.setPassword(config.getString("jdbc.password"));
			dataSource.setJmxEnabled(false);

			dataSource.setTestWhileIdle(false);
			dataSource.setTestOnReturn(false);
			dataSource.setTestOnBorrow(config.getBoolean("dataSource.testOnBorrow"));
			dataSource.setValidationQuery(config.getString("dataSource.validationQuery"));
			dataSource.setValidationInterval(config.getInt("dataSource.validationInterval"));

			dataSource.setMaxActive(config.getInt("dataSource.maxActive"));
			dataSource.setInitialSize(config.getInt("dataSource.initialSize"));
			dataSource.setMaxWait(config.getInt("dataSource.maxWait"));
			dataSource.setMaxIdle(config.getInt("dataSource.maxIdle"));
			dataSource.setRemoveAbandonedTimeout(config.getInt("dataSource.removeAbandonedTimeOut"));
			dataSource.setMinEvictableIdleTimeMillis(config.getInt("dataSource.minEvictableIdleTimeMillis"));
			dataSource.setMinIdle(config.getInt("dataSource.minIdle"));

			dataSource.setLogAbandoned(config.getBoolean("dataSource.logAbandoned"));
			dataSource.setRemoveAbandoned(config.getBoolean("dataSource.removeAbandoned"));
		} catch (ConfigurationException e) {
			l.error("ConfigurationException when TomcatPool init");
			dataSource = null;
		} finally {
			config = null;
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		if (dataSource != null) {
			return dataSource.getConnection();
		}
		init();
		if (dataSource != null) {
			return dataSource.getConnection();
		}
		l.error("dataSource is null when getConnection");
		return null;
	}

	@Override
	public void close() throws SQLException {
		if (dataSource != null) {
			dataSource.close();
			dataSource = null;
		}
	}

	@Override
	public void debug() {
		if (dataSource != null) {
			StringBuilder sb = new StringBuilder();
			l.debug(">> Show TomcatPool debug info\n");
//			try {
				sb.append("getPoolSize()=").append(dataSource.getPoolSize()).append('\n')
				.append("getActive()=").append(dataSource.getActive()).append('\n')
				.append("getIdle()=").append(dataSource.getIdle()).append('\n')
				.append("getMaxActive()=").append(dataSource.getMaxActive()).append('\n')
				.append("getMaxIdle()=").append(dataSource.getMaxIdle()).append('\n')
				.append("getMaxWait()=").append(dataSource.getMaxWait()).append('\n')
				.append("getMaxAge()=").append(dataSource.getMaxAge()).append('\n')
				.append("getNumActive()=").append(dataSource.getNumActive()).append('\n')
				.append("getNumIdle()=").append(dataSource.getNumIdle()).append('\n')
				.append("getRemoveAbandonedTimeout()=").append(dataSource.getRemoveAbandonedTimeout()).append('\n')
				.append("getWaitCount()=").append(dataSource.getWaitCount()).append('\n');
				l.debug(sb);
//			} catch (SQLException e) {
//				l.debug("SQLException when debug");
//			}
		}
	}
}
