package com.youcan.commons.media;

import java.io.IOException;

import com.youcan.core.CallBack;
import com.youcan.core.CliStream;
import com.youcan.core.util.Text;

public class MediaInfo {
	private char[] smiEngine;
	private static final int CALLBACK_TIMEOUT = 5000;
	private static final String SPLITTER = "\\|";

	public MediaInfo() {
		smiEngine = "smiEngine".toCharArray();
	}

	/**
	 * 获得音频信息
	 * 
	 * @param filePath
	 * @return
	 * @throws MediaInfoException
	 */
	public AudioInfo getAudioInfo(String filePath) throws MediaInfoException {
		String audio = runMediaInfo("Audio;%Duration%|%BitRate%", filePath);
		String general = runMediaInfo("General;%FileExtension%|%FileSize%", filePath);

		String[] strAudio = audio.split(SPLITTER, 2);
		String[] strGe = general.split(SPLITTER, 2);

		AudioInfo audioInfo = new AudioInfo();
		audioInfo.setDuration(Text.toInt(strAudio[0], 0));
		audioInfo.setAudioBitRate(Text.toInt(strAudio[1], 0));
		audioInfo.setExt(strGe[0]);
		audioInfo.setFileSize(Text.toLong(strGe[1], 0L));
		return audioInfo;
	}

	// 获取音视频的播放时长，单位毫秒
	public int getDuration(String fileName) throws MediaInfoException {
		String strDuration = runMediaInfo("General;%Duration%", fileName);
		return Text.toInt(strDuration, -1);
	}

	// 获取媒体文件的后缀、字节数和时间长度，字符串形式
	public String getFileESD(String fileName) throws MediaInfoException {
		return runMediaInfo("General;%FileExtension%|%FileSize%|%Duration%", fileName);
	}

	// 获取媒体文件的字节数和时间长度
	public long[] getFilesizeDuration(String fileName) throws MediaInfoException {
		long[] result = { 0, 0 };
		String mediaInfo = runMediaInfo("General;%FileSize%|%Duration%", fileName);
		if (mediaInfo != null && !"".equals(mediaInfo)) {
			String[] mi = mediaInfo.split(SPLITTER, 2);
			if (mi != null && mi.length == 2) {
				result[0] = Text.toLong(mi[0], 0);
				result[1] = Text.toLong(mi[1], 0);
			}
		}
		return result;
	}

	// 获取通用信息
	public String getGeneralInfo(String fileName) throws MediaInfoException {
		return runMediaInfo("General;%Duration%|%FileExtension%|%FileSize%|%VideoCount%|%AudioCount%|%ImageCount%", fileName);
	}

	public int[] getImageWH(String fileName) throws MediaInfoException {
		int[] result = {0, 0};
		String mediaInfo = runMediaInfo("Image;%Width%|%Height%", fileName);
		if (mediaInfo != null && !"".equals(mediaInfo)) {
			String[] mi = mediaInfo.split(SPLITTER, 2);
			if (mi != null && mi.length == 2) {
				result[0] = Text.toInt(mi[0], 0);
				result[1] = Text.toInt(mi[1], 0);
			}
		}
		return result;
	}

	// 获取文件媒体类型信息
	public int[] getMediaInfo(String fileName) throws MediaInfoException {
		int[] result = {0, 0, 0};
		String mediaInfo = runMediaInfo("General;%VideoCount%|%AudioCount%|%ImageCount%", fileName);
		if (mediaInfo != null && !"".equals(mediaInfo)) {
			String[] mi = mediaInfo.split(SPLITTER, 3);
			if (mi != null && mi.length == 3) {
				result[0] = Text.toInt(mi[0], 0);
				result[1] = Text.toInt(mi[1], 0);
				result[2] = Text.toInt(mi[2], 0);
			}
		}
		return result;
	}

	/**
	 * 获得视频信息
	 * 
	 * @param filePath
	 * @return
	 * @throws MediaInfoException
	 */
	public VideoInfo getVideoInfo(String filePath) throws MediaInfoException {
		String audio = runMediaInfo("Audio;%Format%|%BitRate%", filePath);
		String video = runMediaInfo("Video;%Format%|%Duration%|%Width%|%Height%|%BitRate%|%FrameRate%", filePath);
		String general = runMediaInfo("General;%FileExtension%|%FileSize%|%OverallBitRate%", filePath);
		String[] strVi = video.split(SPLITTER, 6);
		String[] strAu = audio.split(SPLITTER, 2);
		String[] strGe = general.split(SPLITTER, 3);

		VideoInfo vi = new VideoInfo();
		vi.setAudioCodec(strAu[0]);
		vi.setAudioBitRate(Text.toInt(strAu[1], 0));// 音频码流
		vi.setVideoCodec(strVi[0]);
		vi.setDuration(Text.toInt(strVi[1], 0));
		vi.setWidth(Text.toInt(strVi[2], 0));
		vi.setHeight(Text.toInt(strVi[3], 0));
		vi.setVideoBitRate(Text.toInt(strVi[4], 0));// 视频码流
		vi.setFrameRate(Text.toDouble(strVi[5], 0.0));
		vi.setExt(strGe[0]);
		vi.setFileSize(Text.toLong(strGe[1], 0L));
		vi.setOverallBitRate(Text.toInt(strGe[2], 0));
		return vi;
	}

	public int[] getVideoWH(String fileName) throws MediaInfoException {
		int[] result = {0, 0};
		String mediaInfo = runMediaInfo("Video;%Width%|%Height%", fileName);
		if (mediaInfo != null && !"".equals(mediaInfo)) {
			String[] mi = mediaInfo.split(SPLITTER, 2);
			if (mi != null && mi.length == 2) {
				result[0] = Text.toInt(mi[0], 0);
				result[1] = Text.toInt(mi[1], 0);
			}
		}
		return result;
	}

	// 检查video的编码格式
	private String runMediaInfo(String options, String fileName) throws MediaInfoException {
		try {
			StringBuilder cmd = new StringBuilder().append(smiEngine).append(" --Output=").append(options).append(' ').append(fileName);
			Process process = Runtime.getRuntime().exec(cmd.toString());
			if (process == null) {
				throw (new MediaInfoException("Error when create process."));
			}
			CliStream cliStream = new CliStream(process.getInputStream());
			CallBack callBack = new CallBack(CALLBACK_TIMEOUT);
			cliStream.setCallBack(callBack);
			cliStream.setSpliter(" ");
			new Thread(cliStream).start();
			callBack.waitBack();
			return callBack.getContent().trim();
		} catch (IOException e) {
			throw (new MediaInfoException("IOException at runMediaInfo."));
		}
	}

	public void setSmiEngine(String smiEngine) {
		this.smiEngine = smiEngine.toCharArray();
	}

	// 获取版本信息
	public String versionInfo() throws MediaInfoException {
		String versioninfo = runMediaInfo(" --Version", "");
		return versioninfo.trim().toLowerCase().substring(versioninfo.lastIndexOf('v') + 1);
	}
}