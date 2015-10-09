package com.youcan.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public ZipUtil() {
		//do nothing
	}

	/*
	 * 文件压缩和解压
	 */
	public static File zip(File zipFile, List<File> files)
			throws FileNotFoundException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		zip(out, files);
		return zipFile;
	}

	public static void zip(ZipOutputStream out, List<File> srcFiles) {
		try {
			for (File srcFile : srcFiles) {
				zip(out, srcFile);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void zip(ZipOutputStream out, File srcFile)
			throws IOException {
		out.putNextEntry(new ZipEntry(srcFile.getName()));
		FileInputStream fis = new FileInputStream(srcFile);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = fis.read(buffer, 0, buffer.length)) != -1) {
			out.write(buffer, 0, length);
		}
		out.closeEntry();
		fis.close();
	}

	public static void unZip(File zipFile, String desdir) throws IOException {
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<?>en = zip.entries();
		ZipEntry entry = null;
		byte[] buffer = new byte[8192];
		int length = -1;
		InputStream input = null;
		BufferedOutputStream bos = null;
		File file = null;

		while (en.hasMoreElements()) {
			entry = (ZipEntry) en.nextElement();
			if (entry.isDirectory()) {
				continue;
			}

			input = zip.getInputStream(entry);
			file = new File(desdir, entry.getName());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			bos = new BufferedOutputStream(new FileOutputStream(file));

			while (true) {
				length = input.read(buffer);
				if (length == -1)
					break;
				bos.write(buffer, 0, length);
			}
			bos.close();
			input.close();
		}
		zip.close();
	}
}
