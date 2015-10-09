/**
 * @Title ElementParse.java
 * @Package com.youcan.m1905.util
 * @Description 
 * @author Jiao Zhujun(jiaozhujun@youcan.com.cn)
 * @date 2011-1-3 下午10:04:47
 * @version V1.0
 */
package com.youcan.core.util;

import org.dom4j.Element;

/**
 * @ClassName ElementParse
 * @Description 处理Element元素的便捷方式
 * @author Jiao Zhujun(jiaozhujun@youcan.com.cn)
 * @date 2011-1-3 下午10:04:47
 *
 */
public class ElementUtil {
	public static String stringValue(final Element e, String defaultValue) {
		try {
			return e.getTextTrim();
		} catch (Exception err) {
			return defaultValue;
		}
	}

	public static long longValue(final Element e, long defaultValue) {
		try {
			return Long.parseLong(e.getTextTrim());
		} catch (NumberFormatException err) {
			return defaultValue;
		} catch (Exception err) {
			return defaultValue;
		}
	}

	public static int intValue(final Element e, int defaultValue) {
		try {
			return Integer.parseInt(e.getTextTrim());
		} catch (NumberFormatException err) {
			return defaultValue;
		} catch (Exception err) {
			return defaultValue;
		}
	}

	public static boolean boolValue(final Element e, boolean defaultValue) {
		try {
			return Boolean.parseBoolean(e.getTextTrim());
		} catch (NumberFormatException err) {
			return defaultValue;
		} catch (Exception err) {
			return defaultValue;
		}
	}

	public static String stringAttribute(final Element e, String attrName, String defaultValue) {
		try {
			String r = e.attributeValue(attrName);
			return (r == null) ? defaultValue : r;
		} catch (Exception err) {
			return defaultValue;
		}
	}

	public static long longAttribute(final Element e, String attrName, long defaultValue) {
		try {
			return Long.parseLong(e.attributeValue(attrName));
		} catch (NumberFormatException err) {
			return defaultValue;
		} catch (Exception err) {
			return defaultValue;
		}
	}

	public static int intAttribute(final Element e, String attrName, int defaultValue) {
		try {
			return Integer.parseInt(e.attributeValue(attrName));
		} catch (NumberFormatException err) {
			return defaultValue;
		} catch (Exception err) {
			return defaultValue;
		}
	}

	public static boolean boolAttribute(final Element e, String attrName, boolean defaultValue) {
		try {
			return Boolean.parseBoolean(e.attributeValue(attrName));
		} catch (NumberFormatException err) {
			return defaultValue;
		} catch (Exception err) {
			return defaultValue;
		}
	}
}
