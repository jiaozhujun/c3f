package com.youcan.commons.media;

import com.youcan.core.l;
import com.youcan.core.util.Text;

public class MediaClient {
	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_UNKNOWN_ERROR = -1;

	private static MediaInfo mediaInfo;
	private static ImageMagick imageMagick;
	private static SvfEngine svfEngine;
	private static PDF2Images pdfImages;

	private static Object lock = new Object();

	public static MediaInfo getMediaInfo() {
		if (mediaInfo == null) {
			synchronized (lock) {
				if (mediaInfo == null) {
					mediaInfo = new MediaInfo();
					mediaInfo.setSmiEngine(MediaConfig.smiEngine.cmd);
				}
			}
		}
		return mediaInfo;
	}

	public static ImageMagick getImageMagick() {
		if (imageMagick == null) {
			synchronized (lock) {
				if (imageMagick == null) {
					imageMagick = new ImageMagick(MediaConfig.sifEngine.cmd, MediaConfig.sifEngine.defaultSize, MediaConfig.sifEngine.defaultOptions);
				}
			}
		}
		return imageMagick;
	}

	public static SvfEngine getSvfEngine() {
		if (svfEngine == null) {
			synchronized (lock) {
				if (svfEngine == null) {
					svfEngine = new SvfEngine(MediaConfig.svfEngine.cmd);
				}
			}
		}
		return svfEngine;
	}

	public static PDF2Images getPDF2Images() {
		if (pdfImages == null) {
			synchronized (lock) {
				if (pdfImages == null) {
					pdfImages = new PDF2Images(MediaConfig.PDF2Images.cmd);
				}
			}
		}
		return pdfImages;
	}

	/**
	 * 
	 * 获取文件信息
	 * 
	 * @param filePath
	 *            - 文件路径
	 * @return
	 */
	public static VideoInfo getVideoInfo(String filePath) {
		try {
			VideoInfo vi = new VideoInfo();
			int[] wh = getMediaInfo().getVideoWH(filePath);
			vi.setWidth(wh[0]);
			vi.setHeight(wh[1]);
			String[] tmp = getMediaInfo().getFileESD(filePath).split("\\|");
			vi.setExt(tmp[0]);
			vi.setFileSize(Text.toLong(tmp[1], 0L));
			vi.setDuration(Text.toInt(tmp[2], 0));
			return vi;
		} catch (MediaInfoException e) {
			l.error("获取媒体信息时出错", e);
			return null;
		}
	}

	public static VideoInfo getDestVideoInfo(VideoInfo sourceVideoInfo) {
		VideoInfo destVideoInfo = new VideoInfo();
		int defaultWidth = MediaConfig.VideoConvert.defaultWidth;
		int defaultHeight = MediaConfig.VideoConvert.defaultHeight;
		int destWidth = sourceVideoInfo.getWidth();
		int destHeight = sourceVideoInfo.getHeight();
		if (defaultWidth == 0 || defaultHeight == 0) {
			if (destWidth > 0 && destHeight > 0) {
				if (defaultWidth == 0 && defaultHeight == 0) {
					//
				} else if (defaultWidth == 0) {
					destWidth = (int) Math.round(1.0 * destWidth * defaultHeight / destHeight);
					if (destWidth % 2 == 1) {
						destWidth += 1;
					}
					destHeight = defaultHeight;
				} else if (defaultHeight == 0) {
					destHeight = (int) Math.round(1.0 * destHeight * defaultWidth / destWidth);
					if (destHeight % 2 == 1) {
						destHeight += 1;
					}
					destWidth = defaultWidth;
				} else {
					destWidth = defaultWidth;
					destHeight = defaultHeight;
				}
			} else {
				l.error(" 检测出源视频的宽高为0");
				if (defaultWidth == 0 && defaultHeight == 0) {
					destWidth = 400;
					destHeight = 300;
				} else if (defaultWidth == 0) {
					destWidth = destHeight = defaultHeight;
				} else {
					destWidth = destHeight = defaultWidth;
				}
			}
		}
		destVideoInfo.setWidth(destWidth);
		destVideoInfo.setHeight(destHeight);
		destVideoInfo.setDuration(sourceVideoInfo.getDuration());
		return destVideoInfo;
	}
}