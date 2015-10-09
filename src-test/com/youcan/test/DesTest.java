package com.youcan.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DesTest {
	void DestTest() {
		//do nothing
	}

	public static void main(String[] args) {
		String fileName = "F:/youcanappexpress.db";
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] eImei = new byte[16];
			fis.skip(19);
			fis.read(eImei);
			byte[] pImei;
			byte[] b = {(byte)0xf1,(byte)0xad,(byte)0xc2,(byte)0xa1,(byte)0x91,(byte)0xfa,(byte)0xdf,(byte)0x4c,(byte)0x2b,(byte)0x20,(byte)0x87,(byte)0xfc,(byte)0x4c,(byte)0x51,(byte)0x35,(byte)0xe1};
			String key = "j iaozhu jun-zZ2";
			System.out.println(new String(eImei,"ISO-8859-1").length());
			pImei = StringUtils.decrypt(b, key.getBytes());
			System.out.println("pImei: " + new String(pImei));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
