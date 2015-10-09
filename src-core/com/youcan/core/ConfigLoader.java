package com.youcan.core;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.youcan.core.ConfigLoader;
import com.youcan.core.l;
import com.youcan.core.util.ElementUtil;
import com.youcan.core.util.FileUtil;
import com.youcan.core.util.Text;

public class ConfigLoader {
	private static final String LAST_LOAD_TMP = "ext.config.tmp";
	private long lastModified = 0L;
	private String isFinal = "";
	private HashMap<String, String> stringValueStack;
	private Pattern pattern;

	public ConfigLoader() {
		lastModified = Text.toLong(FileUtil.readStringFromFile(LAST_LOAD_TMP), 0L);
		pattern = Pattern.compile("\\$\\{(.+?)\\}");
	}

	private String replace(String name, String value) {
		Matcher matcher = pattern.matcher(value);
		String replace;
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			if (matcher.groupCount() == 1 && stringValueStack.containsKey(replace = matcher.group(1))) {
				matcher.appendReplacement(sb, stringValueStack.get(replace));
			}
		}
		matcher.appendTail(sb);
		stringValueStack.put(name, value = sb.toString());
		return value;
	}

	private void writeClass(Element element, StringBuilder javaCode, int classLevel) {
		List<?> el = element.elements();
		int l = el.size();
		if (l == 0) {
			String type = element.attributeValue("type");
			String name = element.getName();
			String value = element.getTextTrim();
			if (type == null) {
				type = Text.checkType(value);
			}
			if ("String".equals(type)) {
				value = '"' + replace(name, value).replaceAll("\"", "\\\\\"") + '"';
			} else if ("long".equals(type)) {
				if (!(value.endsWith("l") || value.endsWith("L"))) {
					value = value + "L";
				}
			}
			javaCode.append("public static").append(isFinal).append(type)
					.append(' ').append(name).append("=").append(value)
					.append(";\n");
		} else {
			String name = element.getName();
			if ("others".equals(name)) {
				return;
			}
			if (classLevel > 0) {
				javaCode.append("public static final class ").append(name)
						.append(" {\n");
			} else {
				javaCode.append("public class ").append(name).append(" {\n");
			}
			Element e;
			classLevel++;
			for (int i = 0; i < l; i++) {
				e = (Element) el.get(i);
				writeClass(e, javaCode, classLevel);
			}
			javaCode.append("}\n");
		}
	}

	private void readClass(Element element, String javaClass, int classLevel) {
		List<?> el = element.elements();
		int l = el.size();
		if (l == 0) {
			String type = element.attributeValue("type");
			String name = element.getName();
			String value = element.getTextTrim();
			if (type == null) {
				type = Text.checkType(value);
			}
			try {
				Class<?> c = Class.forName(javaClass);
				Field field = c.getDeclaredField(name);
				if ("String".equals(type)) {
					field.set(null, replace(name, value));
				} else if ("boolean".equals(type)) {
					field.setBoolean(null, Text.toBoolean(value, false));
				} else if ("int".equals(type)) {
					field.setInt(null, Text.toInt(value, 0));
				} else if ("long".equals(type)) {
					field.setLong(null, Text.toLong(value, 0L));
				} else if ("float".equals(type)) {
					field.setFloat(null, Text.toFloat(value, 0F));
				} else if ("double".equals(type)) {
					field.setDouble(null, Text.toDouble(value, 0D));
				} else if ("byte[]".equals(type)) {
					try {
						field.set(null, value.getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					field.set(null, null);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			String name = element.getName();
			if ("others".equals(name)) {
				return;
			}
			if (classLevel > 0) {
				javaClass += '$' + name;
			} else {
				javaClass += '.' + name;
			}
			Element e;
			classLevel++;
			for (int i = 0; i < l; i++) {
				e = (Element) el.get(i);
				readClass(e, javaClass, classLevel);
			}
		}
	}

	private boolean build(File extConfigFile) {
		boolean result = false;
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(extConfigFile);
			Element root = document.getRootElement();
			String javaClassName = root.getName();
			String isBuild = root.attributeValue("build");
			if (isBuild != null && "false".equals(isBuild)) {
				return false;
			}
			stringValueStack = new HashMap<String, String>();
			stringValueStack.put("WEBROOT", g.rootPath == null ? "/" : g.rootPath);
			String javaPackageName = ElementUtil.stringAttribute(root,
					"package", "com.youcan.resources");
			isFinal = root.attributeValue("final");
			if (isFinal == null || "".equals(isFinal) || "true".equals(isFinal)) {
				isFinal = " final ";
			} else {
				isFinal = " ";
			}
			StringBuilder javaCode = new StringBuilder();
			javaCode.append("package ").append(javaPackageName).append(";\n");
			writeClass(root, javaCode, 0);
			result = FileUtil.writeFile(
					"gen/" + javaPackageName.replace('.', '/') + '/'
							+ javaClassName + ".java", javaCode, "UTF-8");
		} catch (DocumentException e) {
			l.error("文件" + extConfigFile.getPath() + "解析错误");
		} finally {
			stringValueStack.clear();
			stringValueStack = null;
		}
		return result;
	}

	public boolean load(File extConfigFile) {
		boolean result = false;
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(extConfigFile);
			Element root = document.getRootElement();
			String javaPackageName = ElementUtil.stringAttribute(root,
					"package", "com.youcan.resources");
			isFinal = root.attributeValue("final");
			if (isFinal == null || "".equals(isFinal) || "true".equals(isFinal)) {
				return true;
			}
			stringValueStack = new HashMap<String, String>();
			stringValueStack.put("WEBROOT", g.rootPath == null ? "/" : g.rootPath);
			readClass(root, javaPackageName, 0);
			result = true;
		} catch (DocumentException e) {
			l.error("文件" + extConfigFile.getPath() + "解析错误");
			result = false;
		} finally {
			stringValueStack.clear();
			stringValueStack = null;
		}
		return result;
	}

	private long lastLoad() {
		return lastModified;
	}

	@SuppressWarnings("static-method")
	private void writeLastLoad() {
		FileUtil.writeFile(LAST_LOAD_TMP,
				new StringBuilder().append(System.currentTimeMillis()));
	}

	@SuppressWarnings("static-method")
	File[] listConfigFiles(String path) {
		File topDir = new File(path);
		FileFilter ff = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile()
						&& file.getName().matches("^ext-[\\w-]+\\.xml$");
			}
		};
		return topDir.listFiles(ff);
	}

	/**
	 * @Title: main
	 * @Description: 
	 * @param @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
		String configFilePath = "config-file";
		if (args.length == 0) {
			System.out.println("invalid use of ConfigLoader.");
			return;
		} else if (args.length == 2) {
			configFilePath = args[1];
		}
		char command = args[0].charAt(0);
		final ConfigLoader cl = new ConfigLoader();
		boolean result = true;
		switch (command) {
		case 'l' : {
			// 加载编译cl文件
			File[] files = cl.listConfigFiles(configFilePath);
			if (files.length > 0) {
				for (File f : files) {
					if (f.lastModified() > cl.lastLoad()) {
						result = cl.build(f);
						System.out.println("build ext-config file " + f.getName()
								+ (result ? " successfull" : " failed"));
						cl.writeLastLoad();
					} else {
						System.out.println("build ext-config file " + f.getName()
								+ " not modified");
					}
				}
			} else {
				System.out.println("no ext-config file to build.");
			}
			break;
		}
		case 'f' : {
			// 刷新cl文件
			File[] files = cl.listConfigFiles(configFilePath);
			if (files.length > 0) {
				for (File f : files) {
					if (f.lastModified() > cl.lastLoad()) {
						result = cl.load(f);
						System.out.println("refresh ext-config file " + f.getName()
								+ (result ? " successfull." : " failed."));
					} else {
						System.out.println("refresh ext-config file " + f.getName()
								+ " not modified.");
					}
				}
			} else {
				System.out.println("no ext-config file to refresh.");
			}
			break;
		}
		case 'r' : {
			// 加载编译R资源文件
			File r = new File(configFilePath + "/resources.xml");
			if (r.exists()) {
				result = cl.build(r);
				System.out.println("resource file resources.xml reloaded.");
			} else {
				System.out.println("resource file resources.xml not exist.");
			}
			break;
		}
		default : {
			System.out.println("args[0] error.");
		}
		}
	}
}
