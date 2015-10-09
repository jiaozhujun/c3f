package com.youcan.core.ha;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

public class Ha {
	private Random random = new Random(System.currentTimeMillis());
	private SingleServer[] serverPool = null;
	private boolean bypass = false;
	private SingleServer defaultServer = null;

	@SuppressWarnings("rawtypes")
	public boolean init(String configFile, String type) {
		boolean result = false;
		try {
			XMLConfiguration cnf = new XMLConfiguration(configFile);
			List fields = cnf.configurationsAt(type + ".server");
			if (fields.size() > 0) {
				serverPool = new SingleServer[fields.size()];
				int i = 0;
				for(Iterator it = fields.iterator(); it.hasNext();) {
				    HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
				    serverPool[i++] = new SingleServer(sub.getString("[@id]"), sub.getString("addr"), sub.getInt("port"));
				}
				result = true;
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public SingleServer randomServer() {
		if (!bypass && serverPool != null) {
			return serverPool[random.nextInt(serverPool.length)];
		} else {
			return defaultServer;
		}
	}
	
	public SingleServer getServer(String serverId) {
		if (!bypass) {
			for (SingleServer server : serverPool) {
				if (server.getId().equals(serverId)) {
					return server;
				}
			}
		}
		return defaultServer;
	}
	
	public SingleServer getServerByIp(String ip) {
		if (ip == null || serverPool == null) {
			return null;
		}
		for (SingleServer server : serverPool) {
			if (ip.equals(server.getAddr())) {
				return server;
			}
		}
		return null;
	}

	public SingleServer[] allServer() {
		return serverPool;
	}

	public void setDefaultServer(SingleServer defaultServer) {
		this.defaultServer = defaultServer;
	}

	public boolean isBypass() {
		return bypass;
	}

	public void setBypass(boolean bypass) {
		this.bypass = bypass;
	}
}
