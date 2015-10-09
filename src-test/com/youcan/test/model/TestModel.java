package com.youcan.test.model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.youcan.core.db.Db;
import com.youcan.test.bean.TestBean;

public class TestModel {
	public TestModel() {
		//do nothing
	}

	/*
	 * 示例程序，功能做了简化
	 */
	public ArrayList<TestBean> getTestList() {
		Db db = null;
		ArrayList<TestBean> testList = null;
		try {
			db = new Db();
			ResultSet rs = db.executeQuery("SELECT uid,name FROM user");
			testList = new ArrayList<TestBean>(db.getNumRows());
			while(rs.next()) {
				TestBean test = null;
				test = new TestBean();
				test.setId(rs.getLong(1));
				test.setName(rs.getString(2));
				testList.add(test);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Db.close(db);
		}
		return testList;
	}
}
