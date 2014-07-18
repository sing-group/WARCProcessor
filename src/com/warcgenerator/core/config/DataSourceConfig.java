package com.warcgenerator.core.config;

import java.util.HashMap;
import java.util.Map;

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
	private Map<String, String> customParams;
	
	public DataSourceConfig() {
		this.customParams = new HashMap<String, String>();
	}
	
	public DataSourceConfig(String filePath) {
		this.filePath = filePath;
		this.customParams = new HashMap<String, String>();
	}
	
	public DataSourceConfig(boolean spamOrHam, String filePath) {
		this.spam = spamOrHam;
		this.filePath = filePath;
		this.customParams = new HashMap<String, String>();
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

	public Map<String, String> getCustomParams() {
		return customParams;
	}

	public void setCustomParams(Map<String, String> customParams) {
		this.customParams = customParams;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-- DataSourceConfig --\n").
		append("DSClassName: ").append(dsClassName).append("\n").
		append("HandlerClassName: ").append(handlerClassName).append("\n").
		append("FilePath: ").append(filePath).append("\n").
		append("Spam: ").append(spam).append("\n");
		if (maxElements != null) {
			sb.append("MaxElements: ").append(maxElements).append("\n");
		}
		
		if (!customParams.isEmpty()) {
			sb.append("Custom params: ");
			for (String key: customParams.keySet()) {
				String value = customParams.get(key);
				if (value != null && !value.equals("")) {
					sb.append(key).append(": ").append(customParams.get(key)).append("\n");
				}
			}
		}
		return sb.toString();
	}
}
