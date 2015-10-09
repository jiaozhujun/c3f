package com.youcan.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class PackJAR {
	/**
	 * files为需要打包的JAVA文件包,用数组保存起路径
	 * destFile为打包后的jar包路径
	 * 当打包成功返回一个布尔值为true 
	 * */
	public boolean packJar(String[] files,String destFile){
		boolean packIs=false;
//		 需要打包的JAVA文件
//		String[] filenames = files;
		// 创建字节流获取打包文件的数据
		byte[] buf = new byte[1024];
		try {
			// 创建包名
			String outFilename = destFile;
			JarOutputStream out = new JarOutputStream(new FileOutputStream(
					outFilename));
			// 压缩文件
			for (int i = 0; i < files.length; i++) {
				FileInputStream in = new FileInputStream(files[i]);
				// 添加 jar 实体 输出 stream.
				out.putNextEntry(new JarEntry(files[i]));
				// 将字节流转换成jar文件
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// 完成 jar 实体创建
				out.closeEntry();
				in.close();
			}
			// 完成 jar 文件创建
			out.close();
			packIs=true;
		} catch (IOException e) {
			packIs=false;
			System.out.println(e.toString());
		}
		return packIs; 
	}
	
//	public static void main(String[] argc) {
//		// 需要打包的JAVA文件
//		String[] filenames = new String[] { "a.java", "b.java" };
//		// 创建字节流获取打包文件的数据
//		byte[] buf = new byte[1024];
//		try {
//			// 创建包名
//			String outFilename = "rsa.jar";
//			JarOutputStream out = new JarOutputStream(new FileOutputStream(
//					outFilename));
//			// 压缩文件
//			for (int i = 0; i < filenames.length; i++) {
//				FileInputStream in = new FileInputStream(filenames[i]);
//				// 添加 jar 实体 输出 stream.
//				out.putNextEntry(new JarEntry(filenames[i]));
//				// 将字节流转换成jar文件
//				int len;
//				while ((len = in.read(buf)) > 0) {
//					out.write(buf, 0, len);
//				}
//				// 完成 jar 实体创建
//				out.closeEntry();
//				in.close();
//			}
//			// 完成 jar 文件创建
//			out.close();
//		} catch (IOException e) {
//			System.out.println(e.toString());
//		}
//	}
}
