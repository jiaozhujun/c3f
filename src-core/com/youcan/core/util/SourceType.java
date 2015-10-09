package com.youcan.core.util;

public class SourceType {
	public static enum MediaType {IMAGE, AUDIO, VIDEO, UNKNOWN, MULTI};
	public String fileExt;
	public MediaType mediaType;
	
	public SourceType(String fileExt, MediaType mediaType) {
		this.fileExt = fileExt;
		this.mediaType = mediaType;
	}
}
