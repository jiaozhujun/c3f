package com.youcan.commons.media;

import java.io.IOException;

import com.youcan.core.CliStream;

/**
 * 收取pdf中的图片
 * 
 */
public class PDF2Images {
	private CharSequence cmd = "D:/xpdf/pdfimages.exe";

	public PDF2Images(String cmd) {
		this.cmd = cmd;
	}

	public int extractJEPG(String options, String pdfPath, String outputPath) {
		Process process = null;
		int exitValue = 0;
		try {
			StringBuilder sb = new StringBuilder(cmd);
			sb.append(" ").append(options).append(" ").append(pdfPath).append(" ").append(outputPath);
			process = Runtime.getRuntime().exec(sb.toString());
			new Thread(new CliStream(process.getInputStream())).start();
			new Thread(new CliStream(process.getErrorStream())).start();
			exitValue = process.waitFor();
		} catch (IOException e) {
			exitValue = -1;
		} catch (InterruptedException e) {
			exitValue = -1;
		}
		return exitValue;
	}

}
