package com.youcan.core;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.log4j.FileAppender;
import org.apache.log4j.xml.DOMConfigurator;

import com.youcan.core.db.Db;
import com.youcan.core.db.PoolInterface;
import com.youcan.core.db.PoolProvider;
import com.youcan.core.network.NetworkInfo;
import com.youcan.core.security.Authcode;

public class Context {
	private static final boolean AUTHCODE_ENABLE = false;
	private Crontab crontab;

	// 释放连接池的资源
	public void descroyed() {
		l.info("contextDestroyed");
		if (crontab != null) {
			crontab.stop();
		}
		PoolProvider.close();
	}

	// 初始化连接池
	public void init(String containerContextPath, String configPath) {
		// 1. 加载服务环境
		g.loadContext(containerContextPath, configPath);

		// 2. 初始化日志对象
		initLog();

		// 3. 设置GlobalObj，用来保存全局变量
		String configFilePath = null;//configFilePath为空，默认读取WEB-INF/classes/config.xml
		initGlobal(configFilePath);

		// 4. 检查系统依赖环境
		checkDepends();

		// 5. 初始化数据库连接池
		initDb();

		// 6. 加载扩展配置文件信息，ext-xxx.xml
		loadExtConfig();

		// 7. 加载startup对象
		loadStartup();

		// 8. 加载定时任务
		crontab = new Crontab(g.getConfig());
		crontab.start();

		// 9. 垃圾清理，并显示系统信息
		System.gc();
		logSystemInfo();
		l.info("**** 启动完成 ****");
	}

	private void initLog() {
		System.setProperty("webapp.root", g.rootPath);
		DOMConfigurator.configure(g.configPath + "log4j.xml");
		if (l.init("rootLog")) {
			l.info("**** 系统启动 ****");
			l.info("日志对象初始化完成，日志文件保存在：" + ((FileAppender) l.getLog().getAppender("R")).getFile());
		} else {
			System.out.println("日志不存在");
			g.shutdown(g.ERROR_LOG);
		}
	}

	private void initGlobal(String configFilePath) {
		if (!g.loadConfig(configFilePath)) {
			l.fatal("解析配置文件config.xml出错");
			g.shutdown(g.ERROR_CONFIG);
		}
		l.info("内核系统版本 : " + g.coreInfo[0]);
		l.info("最近更新日期 : " + g.coreInfo[1]);
		l.info("      开发者 : " + g.coreInfo[2]);
		l.info("        网址 : " + g.coreInfo[3]);
		l.info("站点名称：" + g.siteName);
		l.info("站点地址：" + g.siteUrl);
		l.info("会话失效时间：" + g.sessionExpiredTime + "毫秒");
		l.info("加载全局变量参数...");
		if (g.var() > 0) {
			for (int i = 0; i < g.var(); i++) {
				String[] v = g.var(i);
				l.info("    " + v[0] + "=" + v[1]);
			}
		} else {
			l.info("没有设置任何全局变量参数globalVars");
		}
	}

	private void checkDepends() {
		l.info("检查系统依赖的目录环境...");
		List<HierarchicalConfiguration> fields = g.getConfig().configurationsAt("depend.dir-needs.dir");
		if (fields != null && fields.size() > 0) {
			String dir = null;
			boolean relative = true;
			String level = "create";
			boolean nfs = false;
			String info = "";
			File file = null;

			HierarchicalConfiguration sub;
			for (int i = 0; i < fields.size(); i++) {
				sub = fields.get(i);
				dir = sub.getString(".");
				relative = sub.getBoolean("[@relative]");
				level = sub.getString("[@level]");
				nfs = sub.getBoolean("[@nfs]");
				info = sub.getString("[@info]");
				if (nfs) {
					// TODO:网络路径
				} else {
					file = new File(relative ? g.rootPath + dir : dir);
					if (!file.isDirectory()) {
						if (level.equals("create") || level.equals("clean")) {
							if (file.mkdirs()) {
								l.info("env 目录不存在：" + file.getAbsolutePath() + ", 创建成功！");
							} else {
								l.fatal("env 目录不存在：" + file.getAbsolutePath() + ", 创建失败！");
								g.shutdown(g.ERROR_STARTUP);
							}
						} else if (level.equals("info")) {
							l.info("env 目录不存在：" + file.getAbsolutePath());
						} else if (level.equals("error")) {
							l.error("env 目录不存在：" + file.getAbsolutePath());
						} else if (level.equals("fatal")) {
							l.fatal("env 目录不存在：" + file.getAbsolutePath());
							g.shutdown(g.ERROR_STARTUP);
						} else {
							l.fatal("env 目录不存在：" + file.getAbsolutePath());
							g.shutdown(g.ERROR_STARTUP);
						}
					} else {
						if (level.equals("clean")) {
							if(file.delete()){
								l.info("清空目录成功：" + file.getAbsolutePath());
							}else{
								l.info("清空目录失败：" + file.getAbsolutePath());
							}
						}
						l.info("env 检查目录：" + file.getAbsolutePath() + ", OK!");
					}
				}
				l.info(dir);
				l.info(info);
			}
			sub = null;
		}

		// authcode
		if (AUTHCODE_ENABLE) {
			String authcodeCalc = null;
			try {
				String macAddress = NetworkInfo.getMacAddress().toString();
				l.info("mac: " + macAddress);
				authcodeCalc = Authcode.createAuthCode(macAddress, System.currentTimeMillis(), "youcancan", new char[] { 'y', 'o', 'u', 'c' });
			} catch (IOException e) {
				l.info("无法获取mac地址");
				e.printStackTrace();
			}
			String authcodeXml = g.var("authcode");
			if (!authcodeCalc.equals(authcodeXml)) {// authCode Error
				l.info("authCode Error");
				g.shutdown(g.ERROR_CONFIG);// authCode timeout
			}
		}
	}

	private void initDb() {
		l.info("初始化数据库连接池...");
		if (g.dbConfig.equals("")) {
			l.info("没有配置数据库");
			// g.shutdown(g.ERROR_DB);
		} else {
			l.info("数据库连接池的配置是 : " + g.dbConfig);
			PoolInterface pool;
			String poolClass = "com.youcan.core.db." + g.dbConfig.substring(0, 1).toUpperCase() + g.dbConfig.substring(1) + "Pool";
			try {
				pool = (PoolInterface) Class.forName(poolClass).newInstance();
				if (g.configPath != null) {
					pool.setConfig(g.configPath + "db-" + g.dbConfig + ".xml");
				} else {
					pool.setConfig(g.rootPath + "conf/db-" + g.dbConfig + ".xml");
				}
				pool.init();
				PoolProvider.setPool(pool);
				l.info("测试数据库连接");
				Db db = null;
				boolean testDb = true;
				try {
					db = new Db();
					db.execute("SELECT 1");
				} catch (SQLException e) {
					testDb = false;
					l.info("测试数据库连接时出现错误：" + e.getMessage());
				} finally {
					Db.close(db);
				}
				if (testDb) {
					l.info("测试数据库连接成功");
				} else {
					l.info("测试数据库连接失败");
					g.shutdown(g.ERROR_DB);
				}
				l.info("数据库连接池初始化操作完成(" + g.dbConfig + ")");
			} catch (InstantiationException e) {
				l.fatal("初始化数据库连接池错误，InstantiationException:" + poolClass);
				g.shutdown(g.ERROR_DB_CONFIG);
			} catch (IllegalAccessException e) {
				l.fatal("初始化数据库连接池错误，IllegalAccessException:" + poolClass);
				g.shutdown(g.ERROR_DB_CONFIG);
			} catch (ClassNotFoundException e) {
				l.fatal("初始化数据库连接池错误，ClassNotFoundException:" + poolClass);
				g.shutdown(g.ERROR_DB_CONFIG);
			} finally {
				poolClass = null;
			}
		}
	}

	private void loadStartup() {
		l.info("加载startup对象...");
		if (g.startup == null || g.startup.length == 0) {
			l.info("没有设置startup对象");
		} else {
			for (int i = 0; i < g.startup.length; i++) {
				if ("".equals(g.startup[i]))
					continue;
				l.info("    [" + i + "]" + g.startup[i]);
				try {
					((StartupInterface) Class.forName(g.startup[i]).newInstance()).init();
				} catch (InstantiationException e) {
					l.fatal("startup对象加载错误，InstantiationException:" + g.startup[i]);
					g.shutdown(g.ERROR_STARTUP);
				} catch (IllegalAccessException e) {
					l.fatal("startup对象加载错误，IllegalAccessException:" + g.startup[i]);
					g.shutdown(g.ERROR_STARTUP);
				} catch (ClassNotFoundException e) {
					l.fatal("startup对象加载错误，ClassNotFoundException:" + g.startup[i]);
					g.shutdown(g.ERROR_STARTUP);
				}
			}
		}
	}

	private void loadExtConfig() {
		// 刷新cl文件
		l.info("加载扩展配置信息...");
		final ConfigLoader cl = new ConfigLoader();
		File[] files =  cl.listConfigFiles(g.configPath);
		if (files != null && files.length > 0) {
			int i = 0;
			for (File f : files) {
				if (cl.load(f)) {
					l.info("    [" + i++ + "]" + f.getName() + ",成功");
				} else {
					l.info("    [" + i++ + "]" + f.getName() + ",失败");
				}
			}
		} else {
			l.info("没有要加载的扩展配置信息[cl-xxx.xml]");
		}
	}

	private void logSystemInfo() {
		l.info("系统信息");
		l.info("    操作系统:" + System.getProperty("os.name"));
		l.info("    体系结构:" + System.getProperty("os.arch"));
		l.info("    系统版本:" + System.getProperty("os.version"));
		l.info("    JAVA HOME:" + System.getProperty("java.home"));
		l.info("    CLASS PATH:" + System.getProperty("java.class.path"));
		l.info("    JAVA VERSION:" + System.getProperty("java.version"));
		l.info("    工作目录:" + System.getProperty("user.dir"));
		l.info("    用户帐号:" + System.getProperty("user.name"));
		l.info("    用户目录:" + System.getProperty("user.home"));
		l.info("      字符集:" + System.getProperty("file.encoding"));
		Runtime runtime = Runtime.getRuntime();
		l.info("    处理器数:" + String.format("%1$11s", new Integer(runtime.availableProcessors())));
		l.info("    最大内存:" + formatMemoryStr(runtime.maxMemory()));
		l.info("    全部内存:" + formatMemoryStr(runtime.totalMemory()));
		l.info("    空闲内存:" + formatMemoryStr(runtime.freeMemory()));
	}

	private String formatMemoryStr(long mem) {
		if (mem > 1024 * 1024 * 1024) {
			return String.format("%1$9sGB", String.format("%1$0#5.3f", new Double((double) mem / (1024 * 1024 * 1024))));
		} else if (mem > 1024 * 1024) {
			return String.format("%1$9sMB", String.format("%1$0#5.3f", new Double((double) mem / (1024 * 1024))));
		} else {
			return String.format("%1$9sKB", String.format("%1$0#5.3f", new Double((double) mem / (1024))));
		}
	}
}
