package com.youcan.test;

public class DbTest {
	DbTest() {
		//do nothing
	}
	
	public static void main(String[] args) {
		String s1 = "aaa&amp;b&bb";
		String s2 = s1.replaceAll("&amp;", "&").replaceAll("&", "&amp;");
		System.out.println(s2);
		/*
		String sql = "select userName from user";
		Pdb db = null;
		try {
			db = new Pdb(sql);
			ResultSet rs = db.executeQuery();
			String name;
			while(rs.next()) {
				name = rs.getString(0);
				System.out.println("name:" + name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		*/
	}
}
