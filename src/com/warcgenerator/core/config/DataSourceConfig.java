package com.warcgenerator.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warcgenerator.core.datasource.handler.IDSHandler;

/**
 * File with data source configuration
 * 
 * @author Miguel Callon
 * 
 */
public class DataSourceConfig {
	public static final boolean IS_SPAM = true;
	public static final boolean IS_HAM = false;
	
	private String name;
	private boolean spam;
	private String filePath;
	private String dsClassName;
	private String handlerClassName;
	// Max number of elements to get from datasource
	private Integer maxElements;
	private Map<String, String> customParams;
	
	// Parent datasource reference
	private DataSourceConfig parent;
	private List<DataSourceConfig> children;
	
	private IDSHandler handler;
	
	public DataSourceConfig() {
		this.customParams = new HashMap<String, String>();
		this.children = new ArrayList<DataSourceConfig>();
	}
	
	public DataSourceConfig(String filePath) {
		this.filePath = filePath;
		this.customParams = new HashMap<String, String>();
		this.children = new ArrayList<DataSourceConfig>();
	}
	
	public DataSourceConfig(boolean spamOrHam, String filePath) {
		this.spam = spamOrHam;
		this.filePath = filePath;
		this.customParams = new HashMap<String, String>();
		this.children = new ArrayList<DataSourceConfig>();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataSourceConfig getParent() {
		return parent;
	}

	public void setParent(DataSourceConfig parent) {
		this.parent = parent;
	}

	public IDSHandler getHandler() {
		return handler;
	}

	public void setHandler(IDSHandler handler) {
		this.handler = handler;
	}

	public List<DataSourceConfig> getChildren() {
		return children;
	}

	public void setChildren(List<DataSourceConfig> children) {
		this.children = children;
	}
}
