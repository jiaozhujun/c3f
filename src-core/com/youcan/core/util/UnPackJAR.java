package com.youcan.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class UnPackJAR {
	
	/**
	 * destFile为需要解压的jar包路径
	 * files为解压包后的,文件路径;使用数组保存其路径
	 * 当打包成功返回一个布尔值为true 
	 * 功能尚为完善,先只能解压第一个包内文件
	 * */
	public boolean unpackJar(String[] files,String destFile){
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
//		try {
//			// 打开 jar 文件
//			String inFilename = "rsa.jar";
//			JarInputStream in = new JarInputStream(new FileInputStream(
//					inFilename));
//			// 获取下一个 jar 实体
//			JarEntry entry = in.getNextJarEntry();
//			// 打开输出文件
//			String outFilename = "QQ.java";
//			OutputStream out = new FileOutputStream(outFilename);
//			// // 将jar转换成文件
//			byte[] buf = new byte[1024];
//			int len;
//			while ((len = in.read(buf)) > 0) {
//				out.write(buf, 0, len);
//			}
//			// 关闭流
//			out.close();
//			in.close();
//		} catch (IOException e) {
//			System.out.println(e.toString());
//		}
//	}
}
