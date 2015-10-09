package com.youcan.commons.media;

public class AudioInfo extends GeneralInfo {
	private int duration;
	private int audioBitRate;
	private String audioCodec;

	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getAudioBitRate() {
		return audioBitRate;
	}
	public void setAudioBitRate(int audioBitRate) {
		this.audioBitRate = audioBitRate;
	}
	public String getAudioCodec() {
		return audioCodec;
	}
	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}
}
