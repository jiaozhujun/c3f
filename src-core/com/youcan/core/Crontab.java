package com.youcan.core;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.youcan.core.g;
import com.youcan.core.l;
import com.youcan.core.util.DateTime;

public class Crontab {
	private XMLConfiguration config;
	Timer timer = new Timer("CrontabTimer");
	Timer daemonTimer = new Timer("DaemonCrontabTimer", true);

	public Crontab(XMLConfiguration config) {
		this.config = config;
	}

	public boolean start() {
		boolean result = false;
		List<HierarchicalConfiguration> fields = config.configurationsAt("crontab.task");
		int len = 0;
		if (fields != null && (len = fields.size()) > 0) {
			String errorMsg = null;
			String name = null;
			boolean stop = false;
//			boolean repeat = false;
			boolean daemon = false;
			boolean fixed = false;
			String delay = null;
			String period = null;
			String taskClass = null;
			HierarchicalConfiguration sub;
			String[] ps = null;
			long periodLong = 0L;
			int periodNum = 0;
			int gap = 0;
			
			for (int i = 0; i < len; i++) {
				sub = fields.get(i);
				name = sub.getString("[@name]", "task-" + i).trim();
				stop = sub.getBoolean("[@stop]", false);
//				repeat = sub.getBoolean("[@repeat]", true);
				daemon = sub.getBoolean("[@daemon]", false);
				fixed = sub.getBoolean("[@fixed]", true);
				delay = sub.getString("[@delay]", "0").trim().toLowerCase();
				period = sub.getString("[@period]", "every 1 d").trim().toLowerCase();
				taskClass = sub.getString(".", "").trim();
				errorMsg = null;
				try {
					if (taskClass != null && !taskClass.equals("")) {
						if (delay.matches("\\A(from \\d{2}\\:\\d{2}\\:\\d{2}( gap \\d+)?)|(\\d+)\\z") && period.matches("\\Aevery \\d+ [dhms]{1}\\z")) {
							TimerTask timerTask = (TimerTask) Class.forName(taskClass).newInstance();
							ps = period.split(" ");
							periodNum = Integer.parseInt(ps[1]);
							if (ps[2].equals("d")) {
								periodLong = periodNum * 86400000;
							} else if (ps[2].equals("h")) {
								periodLong = periodNum * 3600000;
							} else if (ps[2].equals("m")) {
								periodLong = periodNum * 60000;
							} else {
								periodLong = periodNum * 1000;
							}
							if (delay.startsWith("from")) {
								if (delay.length() > 18) {
									gap = Integer.parseInt(delay.substring(18));
								} else {
									gap = 0;
								}
								delay = delay.substring(5, 13);
								Calendar calendar = Calendar.getInstance();
								calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(delay.substring(0, 2)));
								calendar.set(Calendar.MINUTE, Integer.parseInt(delay.substring(3, 5)));
								calendar.set(Calendar.SECOND, Integer.parseInt(delay.substring(6, 8)));
								calendar.set(Calendar.MILLISECOND, 0);
								if (calendar.getTimeInMillis() - gap < System.currentTimeMillis()) {
									calendar.add(Calendar.DAY_OF_MONTH, 1);
								}
								if (fixed) {
									if (daemon) {
										daemonTimer.scheduleAtFixedRate(timerTask, calendar.getTime(), periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(calendar.getTimeInMillis(), DateTime.FormatFull) + ", Every " + periodLong/1000 + "s, Fixed, Daemon");
									} else {
										timer.scheduleAtFixedRate(timerTask, calendar.getTime(), periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(calendar.getTimeInMillis(), DateTime.FormatFull) + ", Every " + periodLong/1000 + "s, Fixed");
									}
								} else {
									if (daemon) {
										daemonTimer.schedule(timerTask, calendar.getTime(), periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(calendar.getTimeInMillis(), DateTime.FormatFull) + ", Every " + periodLong/1000 + "s, Daemon");
									} else {
										timer.schedule(timerTask, calendar.getTime(), periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(calendar.getTimeInMillis(), DateTime.FormatFull) + ", Every " + periodLong/1000 + "s");
									}
								}
							} else {
								if (fixed) {
									if (daemon) {
										daemonTimer.scheduleAtFixedRate(timerTask, Long.parseLong(delay) * 1000, periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(System.currentTimeMillis() + Long.parseLong(delay) * 1000, DateTime.FormatFull) + ", Every " + periodLong/1000 + "s, Fixed, Daemon");
									} else {
										timer.scheduleAtFixedRate(timerTask, Long.parseLong(delay) * 1000, periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(System.currentTimeMillis() + Long.parseLong(delay) * 1000, DateTime.FormatFull) + ", Every " + periodLong/1000 + "s, Fixed");
									}
								} else {
									if (daemon) {
										daemonTimer.schedule(timerTask, Long.parseLong(delay) * 1000, periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(System.currentTimeMillis() + Long.parseLong(delay) * 1000, DateTime.FormatFull) + ", Every " + periodLong/1000 + "s, Daemon");
									} else {
										timer.schedule(timerTask, Long.parseLong(delay) * 1000, periodLong);
										l.info("定时任务" + name + "成功启动: From " + DateTime.longDateToStr(System.currentTimeMillis() + Long.parseLong(delay) * 1000, DateTime.FormatFull) + ", Every " + periodLong/1000 + "s");
									}
								}
							}
							stop &= false;
						} else {
							//l.error("定时任务" + name + "的参数period或delay格式不正确");
							errorMsg = "定时任务" + name + "的参数period或delay格式不正确";
							stop &= true;
						}
					} else {
						//l.error("定时任务" + name + "没有指定要运行的TimerTask");
						errorMsg = "定时任务" + name + "没有指定要运行的TimerTask";
						stop &= true;
					}
				} catch (InstantiationException e) {
					stop &= true;
					errorMsg = "定时任务" + name + "初始化错误";
				} catch (IllegalAccessException e) {
					stop &= true;
					errorMsg = "定时任务" + name + "权限错误";
				} catch (ClassNotFoundException e) {
					stop &= true;
					errorMsg = "定时任务" + name + "指定的TimerTask类找不到";
				} finally {
					if (stop) {
						l.error(errorMsg + ", 程序终止");
						g.shutdown(0);
					} else {
						if (errorMsg != null) {
							l.warn(errorMsg);
						}
					}
				}
			}
			sub = null;
		} else {
			l.info("没有找到Crontab相关的配置文件");
			result = false;
		}

		return result;
	}
	
	public void stop() {
		timer.cancel();
//		timer.purge();
		daemonTimer.cancel();
//		daemonTimer.purge();
	}
}
