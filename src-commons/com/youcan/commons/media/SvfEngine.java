package com.youcan.commons.media;

import java.io.File;
import java.io.IOException;

import com.youcan.core.CliStream;
import com.youcan.core.l;

/**
 * 视频后续处理
 * 
 * @FilePath : C3Framework.com.youcan.commons.media.SvfEngine.java
 * @TypeName : SvfEngine
 * @Author : LiuXiYang
 * @Time : 2012-8-3 上午11:11:16
 * @Version : V
 */
public class SvfEngine {
	private CharSequence cmd = null;

	public SvfEngine(String cmdPath) {
		this.cmd = cmdPath;
	}

	/**
	 * 视频生成缩略图以及格式转换 Mp4
	 * 
	 * @param src
	 *            源文件地址
	 * @param iDest
	 *            截取出的图片源文件保存地址
	 * @param tDest
	 *            缩略图保存地址
	 * @param tWxH
	 *            缩略图宽x高
	 * @param vDest
	 *            转换后视频文件保存地址
	 * @return 处理结果 -1 发生错误
	 */
	public int createThumbAndConvert(String src, String iDest, String tDest, String tWxH, String vDest, boolean convert) {
		Process process = null;
		int exitValue = 0;
		try {
			// 截取指定位置截图 ${svfEnginePath} -i ${source} -y -f image2 -ss 5
			// -vframes 1 ${tempImg}
			process = Runtime.getRuntime().exec(new StringBuffer().append(cmd).append(" -i ").append(src).append(" -y -f image2 -ss 6 -vframes 1 ").append(iDest).toString());
			new Thread(new CliStream(process.getInputStream())).start();
			new Thread(new CliStream(process.getErrorStream())).start();
			exitValue = process.waitFor();

			// 图片缩略
			ImageMagick im = MediaClient.getImageMagick();
			im.resize(iDest, tDest, tWxH, null);

			// 视频格式转换 ${svfEnginePath} -i ${inputMedia} -f mp4 -y -b 350k -s
			// 400x300 -acodec libvo_aacenc -ar 48000 -ab 96k -ac 2 ${outputMp4}
			//在需要的时候进行转换：原视频不是MP4格式的
			if(convert){
				process = Runtime.getRuntime().exec(
						new StringBuffer().append(cmd).append(" -i ").append(src).append(" -f mp4 -y -b 350k -s ").append(tWxH).append(" -acodec libvo_aacenc -ar 48000 -ab 96k -ac 2 ").append(vDest).toString());
				new Thread(new CliStream(process.getInputStream())).start();
				new Thread(new CliStream(process.getErrorStream())).start();
				exitValue = process.waitFor();
			}
		} catch (IOException e) {
			exitValue = -1;
		} catch (InterruptedException e) {
			exitValue = -1;
		}
		return exitValue;
	}

	public int audioConvert(String srcFileName, String destFileName, String destFormat, String bitRate, int sampleRate, boolean convert) {
		Process process = null;
		int exitValue = 0;
		try {
			//只针对不是MP4格式的音频进行转码
			if(convert){
				process = Runtime.getRuntime().exec(
						new StringBuffer().append(cmd).append(" -i ").append(srcFileName).append(" -y -f ").append(destFormat.trim()).append(" -vn -acodec libvo_aacenc -ab ").append(bitRate.trim())
								.append(" -ar ").append(sampleRate).append(' ').append(destFileName).toString());
				new Thread(new CliStream(process.getInputStream())).start();
				new Thread(new CliStream(process.getErrorStream())).start();
				exitValue = process.waitFor();
			}else{
				File f = new File(srcFileName);
				f.renameTo(new File(destFileName));
			}
		} catch (IOException e) {
			exitValue = -1;
		} catch (InterruptedException e) {
			exitValue = -1;
		}
		return exitValue;
	}
}
