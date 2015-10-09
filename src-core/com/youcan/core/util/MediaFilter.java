package com.youcan.core.util;

import java.io.File;
import java.io.FilenameFilter;

public class MediaFilter implements FilenameFilter {
	private String[] fileExts = null;
	
	public MediaFilter() {
		
	}
	
	public void initFilter(String[] fileExts) {
		this.fileExts = fileExts;
	}
	
	public void initFilter(String fileExtStr) {
		initFilter(fileExtStr, ":");
	}

	public void initFilter(String fileExtStr, String pattern) {
		fileExts = fileExtStr.split(pattern);
		for (int i = 0; i < fileExts.length; i++) {
			fileExts[i] = fileExts[i].trim().toLowerCase();
		}
	}
	
	public boolean accept(File dir,String fname){
		for (int i = 0; i < fileExts.length; i++) {
			if (fname.toLowerCase().endsWith(fileExts[i])) {
				return true;
			}
		}
		return false;
	}
}
