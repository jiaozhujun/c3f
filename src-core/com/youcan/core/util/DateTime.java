package com.youcan.core.util;

import java.util.Calendar;
import java.util.Date;

public class DateTime {
	public static java.text.SimpleDateFormat dtFormatFull = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static java.text.SimpleDateFormat dtFormatDate = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");
	public static java.text.SimpleDateFormat dtFormatTime = new java.text.SimpleDateFormat(
			"HH:mm:ss");
	public static java.text.SimpleDateFormat dtFormatComp = new java.text.SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	public static java.text.SimpleDateFormat dtFormatMin = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static java.text.SimpleDateFormat dtFormatHour = new java.text.SimpleDateFormat("yyyy-MM-dd HH");
	public static java.text.SimpleDateFormat dtFormatHourCn = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH时");
	
	public static final int FormatFull = 0;
	public static final int FormatDate = 1;
	public static final int FormatTime = 2;
	public static final int FormatComp = 3;
	public static final int FormatMin = 4;
	public static final int FormatHour = 5;
	public static final int FormatHourCn = 6;

	public static final int Time_Full_CN = 0; // yyyy年MM月dd日 星期（一～日）;
	public static final int Time_Date_CN = 1;
	public static final String[] WEEK_CN = { "一", "二", "三", "四", "五", "六", "日" };

	public static String longDateToStr(long date, int mode) {
		switch (mode) {
		case FormatFull:
			return dtFormatFull.format(new Date(date));
		case FormatDate:
			return dtFormatDate.format(new Date(date));
		case FormatTime:
			return dtFormatTime.format(new Date(date));
		case FormatComp:
			return dtFormatComp.format(new Date(date));
		case FormatMin:
			return dtFormatMin.format(new Date(date));
		case FormatHour:
			return dtFormatHour.format(new Date(date));
		case FormatHourCn:
			return dtFormatHourCn.format(new Date(date));
		default:
			return dtFormatFull.format(new Date(date));
		}
	}

	public static String longDateToStr(long date) {
		return longDateToStr(date, FormatDate);
	}

	// 返回今天零点的时间的long毫秒值
	public static long todayZero() {
		long curTime = System.currentTimeMillis() + 28800000;
		return curTime - curTime % 86400000 - 28800000;
	}
	public static String durationToStr(int duration) {
		StringBuilder durationStr = new StringBuilder();
		int ms = duration % 1000;
		int s = duration / 1000;
		int m = 0;
		int h = 0;
		if (s > 60) {
			m = s / 60;
			s = s % 60;
		}
		if (m > 60) {
			h = m / 60;
			m = m % 60;
		}
		if (h > 0) {
			durationStr.append(h).append("小时");
		}
		if (m > 0) {
			durationStr.append(m).append("分");
		}
		durationStr.append(s).append("秒");
		if (ms > 0) {
			durationStr.append(ms);
		}
		return durationStr.toString();
	}

	public static String durationToStrMs(int duration) {
		int ms = duration % 1000;
		int s = duration / 1000;
		int m = 0;
		int h = 0;
		if (s > 60) {
			m = s / 60;
			s = s % 60;
		}
		if (m > 60) {
			h = m / 60;
			m = m % 60;
		}
		return String.format("%02d:%02d:%02d.%03d", h, m, s, ms);
	}

	public static String longDateToCN(long date, int mode) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date));
		switch (mode) {
		case Time_Full_CN:
			return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1)
					+ "月" + c.get(Calendar.DATE) + "日 星期"
					+ WEEK_CN[c.get(Calendar.DAY_OF_WEEK) - 1];
		case Time_Date_CN:
			return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1)
					+ "月" + c.get(Calendar.DATE) + "日";
		default:
			return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1)
					+ "月" + c.get(Calendar.DATE) + "日 星期"
					+ WEEK_CN[c.get(Calendar.DAY_OF_WEEK) - 1];
		}
	}

	public static String getWeek(long date) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(date));
		return "星期" + WEEK_CN[c.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 时间转化成长整型数值
	 */
	public static long strdatetolong(String str, int mode) {
		try {
			switch (mode) {
			case 0:
				return dtFormatFull.parse(str).getTime();
			case 1:
				return dtFormatDate.parse(str).getTime();
			case 2:
				return dtFormatTime.parse(str).getTime();
			case 3:
				return dtFormatComp.parse(str).getTime();
			case 4:
				return dtFormatMin.parse(str).getTime();
			default:
				return dtFormatFull.parse(str).getTime();
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取当前月的天数
	 * 
	 * @param month
	 *            - 月
	 * @param year
	 *            - 年
	 * @return
	 */
	public static int getDay(int month, int year) {
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 2:
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				day = 29;
			else
				day = 28;
		}
		return day;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static long getToday() {
		return System.currentTimeMillis();
	}
}
