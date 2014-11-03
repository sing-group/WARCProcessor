package com.warcgenerator.core.config;

/**
 * File with output warc config
 * 
 * @author Miguel Callon
 *
 */
public class OutputWarcConfig {
	private boolean isSpam;
	private String fileName;
	public OutputWarcConfig(boolean isSpam, String fileName) {
		this.setSpam(isSpam);
		this.setFileName(fileName);
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isSpam() {
		return isSpam;
	}
	public void setSpam(boolean isSpam) {
		this.isSpam = isSpam;
	}
}
