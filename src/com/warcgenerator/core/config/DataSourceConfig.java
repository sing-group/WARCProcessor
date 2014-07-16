package com.warcgenerator.core.config;

/**
 * File with data source configuration
 * 
 * @author Miguel Callon
 * 
 */
public class DataSourceConfig {
	public static final boolean IS_SPAM = true;
	public static final boolean IS_HAM = false;
	
	private boolean spam;
	private String filePath;
	private String dsClassName;
	private String handlerClassName;
	// Max number of elements to get from datasource
	private Integer maxElements;
	
	public DataSourceConfig() {}
	
	public DataSourceConfig(String filePath) {
		this.filePath = filePath;
	}
	
	public DataSourceConfig(boolean spamOrHam, String filePath) {
		this.spam = spamOrHam;
		this.filePath = filePath;
	}
	
	public boolean isSpam() {
		return spam;
	}

	public void setSpamOrHam(boolean spam) {
		this.spam = spam;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDsClassName() {
		return dsClassName;
	}

	public void setDsClassName(String dsClassName) {
		this.dsClassName = dsClassName;
	}

	public String getHandlerClassName() {
		return handlerClassName;
	}

	public void setHandlerClassName(String handlerClassName) {
		this.handlerClassName = handlerClassName;
	}

	public Integer getMaxElements() {
		return maxElements;
	}

	public void setMaxElements(Integer maxElements) {
		this.maxElements = maxElements;
	}
}
