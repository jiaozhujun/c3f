package com.youcan.core;

import java.io.File;
import java.util.List;

//import javax.servlet.ServletContext;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;

import com.youcan.core.g;
import com.youcan.core.util.FileUtil;

public class g {
	/*
	 * 版本信息
	 */
	protected static final String[] coreInfo = { "1.3.1", "2013-05-04",
			"Beijing Youcan Communications Co., Ltd.",
			"http://www.youcan.com.cn" };
	/*
	 * 错误代码
	 */
	protected static final int ERROR_CONFIG = 100;
	protected static final int ERROR_LOG = 101;
	protected static final int ERROR_DB = 102;
	protected static final int ERROR_DB_CONFIG = 103;
	protected static final int ERROR_STARTUP = 104;

	/*
	 * 服务器信息
	 */
	public static final int OS_WINDOWS = 0;
	public static final int OS_LINUX = 1;
	public static final int OS_OTHER = 2;
	public static final int ARCH_32 = 0;
	public static final int ARCH_64 = 1;

	/*
	 * 配置信息
	 */
//	private static ServletContext servletContext = null;
	private static XMLConfiguration config = null;
	public static String siteUrl;
	public static String siteName;
	public static String rootPath;
	public static String configPath;
	public static String uploadTemp;
	public static long uploadSizeMax;
	public static int sessionExpiredTime;
	public static String sessionIdName;
	public static int os = OS_WINDOWS;
	public static int arch = ARCH_64;
	private static String[][] vars;
	protected static String dbConfig;
	protected static String[] startup;
	public static long startupTime = System.currentTimeMillis();

	/**
	 * 加载运行环境
	 * 可以是ServletContext，也可以是String表示的运行根目录
	 * @param context
	 */
	public static void loadContext(String contextRootPath, String configPath) {
		rootPath = contextRootPath;
		if (rootPath != null && rootPath.length() > 0) {
			rootPath = rootPath.replace('\\', '/');
			if (rootPath.charAt(rootPath.length() - 1) != '/') {
				rootPath += '/';
			}
		}
		g.configPath = configPath;
		if (g.configPath == null ) {
			g.configPath = rootPath + "conf/";
		} else if (g.configPath.length() > 0) {
			g.configPath = g.configPath.replace('\\', '/');
			if (g.configPath.charAt(g.configPath.length() - 1) != '/') {
				g.configPath += '/';
			}
		}
	}

	public static void shutdown(int status) {
		System.exit(status);
	}

	public static boolean loadConfig(String configFilePath) {
		String tmp = System.getProperty("os.name").toLowerCase();
		if (tmp.startsWith("windows")) {
			g.os = g.OS_WINDOWS;
		} else if (tmp.startsWith("linux")) {
			g.os = g.OS_LINUX;
		} else {
			g.os = g.OS_OTHER;
		}
		tmp = System.getProperty("sun.arch.data.model");
		if (tmp.equals("64")) {
			g.arch = g.ARCH_64;
		} else {
			g.arch = g.ARCH_32;
		}
		try {
			if (configFilePath == null) {
				config = new XMLConfiguration(configPath + "config.xml");
			} else {
				config = new XMLConfiguration(configFilePath);
			}
			dbConfig = config.getString("db", "").toLowerCase();
			siteUrl = config.getString("serverInfo.siteUrl");
			siteName = config.getString("serverInfo.siteName");
			uploadTemp = rootPath + config.getString("serverInfo.uploadTemp");
			uploadSizeMax = config.getLong("serverInfo.uploadSizeMax", 0);
			sessionExpiredTime = config.getInt("serverInfo.sessionExpiredTime", 0);
			sessionIdName = config.getString("serverInfo.sessionIdName", "ycsid");
			String _s = g.getConfig().getString("startup");
			if ("".equals(_s)) {
				startup = null;
			} else {
				_s = _s.replaceAll("\\s", "");
				startup = _s.split(";");
			}
			_s = null;
			// 加载全局参数
			List<HierarchicalConfiguration> fields = config
					.configurationsAt("globalVars.var");
			if (fields != null && fields.size() > 0) {
				vars = new String[fields.size()][2];
				HierarchicalConfiguration sub;
				for (int i = 0; i < fields.size(); i++) {
					sub = fields.get(i);
					vars[i][0] = sub.getString("[@name]");
					vars[i][1] = sub.getString("[@value]");
				}
				sub = null;
			}
			return true;
		} catch (ConfigurationException e) {
			return false;
		}
	}

	public static XMLConfiguration getConfig() {
		return config;
	}

	public static int var() {
		if (vars != null) {
			return vars.length;
		}
		return 0;
	}

	public static String var(String name) {
		if (vars != null) {
			for (int i = 0; i < vars.length; i++) {
				if (vars[i][0].equals(name)) {
					return vars[i][1];
				}
			}
		}
		return null;
	}

	public static String[] var(int index) {
		if (vars != null && vars.length > index) {
			return vars[index];
		}
		return null;
	}

	public static boolean fileCache(String name, StringBuilder content) {
		return FileUtil.writeFile(rootPath + "_s/" + name, content, "UTF-8");
	}

	public static boolean unFileCache(String name) {
		return FileUtils.deleteQuietly(new File(rootPath + "_s/" + name));
	}

	public static boolean fileCache(String name, Document doc) {
		return FileUtil.writeXmlFile(rootPath + "_s/" + name, doc);
	}

	public static String readFileCache(String name) {
		return FileUtil.readStringFromFile(rootPath + "_s/" + name);
	}
}
