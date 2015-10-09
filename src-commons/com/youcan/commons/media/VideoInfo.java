package com.youcan.commons.media;

public class VideoInfo extends AudioInfo {
	private int width;
	private int height;
	private int videoBitRate;
	private int overallBitRate;
	private double frameRate;
	private String videoCodec;

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getVideoBitRate() {
		return videoBitRate;
	}
	public void setVideoBitRate(int videoBitRate) {
		this.videoBitRate = videoBitRate;
	}
	public int getOverallBitRate() {
		return overallBitRate;
	}
	public void setOverallBitRate(int overallBitRate) {
		this.overallBitRate = overallBitRate;
	}
	public double getFrameRate() {
		return frameRate;
	}
	public void setFrameRate(double frameRate) {
		this.frameRate = frameRate;
	}
	public String getVideoCodec() {
		return videoCodec;
	}
	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}
}