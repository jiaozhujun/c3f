package com.youcan.test;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.youcan.core.Crontab;

public class CrontabTest {

	public static void main(String[] args) {
		/*
		String period = "every 1 m";
		String delay = "from 17:00:00";
		if (period.matches("\\Aevery \\d+ [dhms]{1}\\z") && delay.matches("\\A(from \\d{2}\\:\\d{2}\\:\\d{2})|(\\d+)\\z")) {
		//if (period.matches("\\Aevery \\d+ [dhms]{1}\\z")) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
		*/
		try {
			XMLConfiguration config = new XMLConfiguration("E:/workspace/nms/WebContent/WEB-INF/classes/config.xml");
			Crontab crontab = new Crontab(config);
			crontab.start();
			System.out.println("crontab test end");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
