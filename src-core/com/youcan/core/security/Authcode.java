package com.youcan.core.security;

import java.util.Calendar;

import com.youcan.core.util.Text;

public class Authcode {
	public static String createAuthCode(String macAddress, long time, String pass, char[] postfix) {
		if (postfix.length != 4) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		String year = new Integer(calendar.get(Calendar.YEAR)).toString();
		year = year.substring(0, 2) + "y~Ar" + year.substring(2);
		String month = new Integer(calendar.get(Calendar.MONTH)).toString();
		String timeLock = year + month;
		String md5 = Text.MD5(Text.MD5(macAddress) + timeLock + pass);
		md5 = Text.MD5(md5 + postfix[0]) + Text.MD5(md5 + postfix[1]) + Text.MD5(md5 + postfix[2]) + Text.MD5(md5 + postfix[3]);
		return md5;
	}

	public static String createAuthCode_ver1(String macAddress, long time, String pass, char[] postfix) {
		if (postfix.length != 4) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		String year = new Integer(calendar.get(Calendar.YEAR)).toString();
		year = year.substring(0, 2) + "y~Ar" + year.substring(2);
		String md5 = Text.MD5(Text.MD5(macAddress) + year + pass);
		md5 = Text.MD5(md5 + postfix[0]) + Text.MD5(md5 + postfix[1]) + Text.MD5(md5 + postfix[2]) + Text.MD5(md5 + postfix[3]);
		return md5;
	}
	
	/**
	 * 用于生成
	 * 
	 * @param username
	 *            用户名
	 * @param time
	 *            登陆时间
	 * @param id
	 *            用户id
	 * @param postfix
	 *            密钥
	 * @return
	 */
	public static String createAuthCode(String username, long time, long userId, char[] postfix) {
		if (postfix.length != 4) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		String year = new Integer(calendar.get(Calendar.YEAR)).toString();
		year = year.substring(0, 2) + "y~Ar" + year.substring(2);
		String month = new Integer(calendar.get(Calendar.MONTH)).toString();
		String timeLock = year + month;
		String md5 = Text.MD5(Text.MD5(username) + timeLock + userId);
		md5 = Text.MD5(md5 + postfix[0]) + Text.MD5(md5 + postfix[1]) + Text.MD5(md5 + postfix[2]) + Text.MD5(md5 + postfix[3]);
		return md5;
	}
}
