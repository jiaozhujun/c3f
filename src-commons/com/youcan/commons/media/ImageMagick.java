package com.youcan.commons.media;

import java.io.IOException;

import com.youcan.core.CliStream;
import com.youcan.core.l;

public class ImageMagick {
	private CharSequence cmd = null;
	private CharSequence defaultSize = null;
	private CharSequence defaultOptions = null;

	public ImageMagick(String cmdPath, String defaultSize, String defaultOptions) {
		this.cmd = cmdPath;
		this.defaultSize = "".equals(defaultSize) ? null : defaultSize;
		this.defaultOptions = "".equals(defaultOptions) ? null : defaultOptions;
	}

	/**
	 * 改变图片大小尺寸
	 * 
	 * @param src
	 *            源图片文件
	 * @param dest
	 *            目标图片文件
	 * @param size
	 *            更改后的尺寸，如果为null则使用配置文件中的默认尺寸<br/>
		关于size的说明： <br/>
		width:转换后的宽度等于width，高度自动，保持比例<br/>
		xheight:转换后的高度等于height，宽度自动，保持比例<br/>
		widthxheight：转换后的宽高最大不超过width和height，保持比例<br/>
		widthxheight^：转换后的宽高最小的与width或height相等，保持比例<br/>
		widthxheight!：转化后的宽高与width、height相等，可能会变形<br/>
		widthxheight>：如果源图片的宽高大于width、height，则缩小，保持比例<br/>
		widthxheight<：如果源图片的宽高小于width、height，则放大，保持比例<br/>
	 * @param options
	 *            选项参数，可以为null
	 * @return
	 */
	public int resize(String src, String dest, String size, String options) {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(
					new StringBuffer().append(cmd).append(' ').append(src).append(" -resize ").append(size == null ? defaultSize : size).append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(' ').append(dest).toString());
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

	/**
	 * 裁剪图片
	 * 
	 * @param src
	 *            原图片文件
	 * @param dest
	 *            目标图片文件
	 * @param size
	 *            裁剪值：100x100+50+50
	 * @param options
	 *            选项参数，可以为null
	 * @return
	 */
	public int cutImage(String src, String dest, String size, String options) {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(
					new StringBuffer().append(cmd).append(' ').append(src).append(" -crop ").append(size == null ? defaultSize : size).append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(' ').append(dest).toString());
			new Thread(new CliStream(process.getInputStream())).start();
			new Thread(new CliStream(process.getErrorStream())).start();
			exitValue = process.waitFor();
		} catch (IOException e) {
			exitValue = -1;
		} catch (InterruptedException e) {
			exitValue = -1;
		}
		// System.out.println(new
		// StringBuffer().append(cmd).append(' ').append(src).append(" -crop ").append(size
		// == null ? defaultSize : size).append(' ').append(options == null ?
		// (defaultOptions == null ? "" : defaultOptions) :
		// options).append(' ').append(dest).toString());
		return exitValue;
	}

	/**
	 * 裁剪并重设文件尺寸
	 * 
	 * @return
	 */
	public int cutAndConvertImage(String src, String dest, String size, String reSize, String options) {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(
					new StringBuffer().append(cmd).append(' ').append(src).append(" -crop ").append(size == null ? defaultSize : size).append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(" -resize ").append(reSize == null ? defaultSize : reSize).append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(' ').append(dest).toString());
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

	public int roateCutAndResizeImage(String src, String dest, String angle, String size, String reSize, String options) {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(
					new StringBuffer().append(cmd).append(" -rotate").append(' ').append(angle).append(' ').append(src)
							.append(" -alpha set -channel RGBA -fuzz 1% -fill none -floodfill +0+0 white -shave 1x1 -crop ").append(size == null ? defaultSize : size).append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(" -resize ").append(reSize == null ? defaultSize : reSize).append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(' ').append(dest).toString());
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

	public int roateAndResizeImage(String src, String dest, String angle, String reSize, String options) {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(
					new StringBuffer().append(cmd).append(" -rotate").append(' ').append(angle).append(' ').append(src)
//							.append(" -alpha set -channel RGBA -fuzz 1% -fill none -floodfill +0+0 white -shave 1x1 -resize ").append(reSize == null ? defaultSize : reSize).append(' ')
							.append(" -resize ").append(reSize == null ? defaultSize : reSize).append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(' ').append(dest).toString());
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

	/**
	 * 去掉png图片的背景
	 * 
	 * @param src
	 * @param dest
	 * @param options
	 * @return
	 */
	public int removeBackgroundColor(String src, String dest, String options) {
		Process process = null;
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(
					new StringBuffer().append(cmd).append(' ').append(src).append(" -alpha set -channel RGBA -fuzz 1% -fill none -floodfill +0+0 white -shave 1x1 ").append(' ')
							.append(options == null ? (defaultOptions == null ? "" : defaultOptions) : options).append(' ').append(dest).toString());
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

	public int gif2png(String srcFilePath, String destFilePath) {
		l.debug("ImageMagick.gif2png 原图 " + srcFilePath + "   " + "转换的 " + destFilePath);
		Process process = null;
		String options = "";
		int exitValue = 0;
		try {
			process = Runtime.getRuntime().exec(new StringBuffer().append(cmd).append(' ').append(options).append(' ').append(srcFilePath).append(' ').append(destFilePath).toString());
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
