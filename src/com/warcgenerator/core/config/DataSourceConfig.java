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
public class DataSourceConfig implements Comparable<DataSourceConfig> {
	public static final boolean IS_SPAM = true;
	public static final boolean IS_HAM = false;
	public static int nextId = 1;
	
	private Integer id;
	private String name = "";
	private String type = "";
	private Boolean spam;
	private String filePath = "";
	private String dsClassName = "";
	private String handlerClassName ="";
	// Max number of elements to get from datasource
	private Integer maxElements;
	private Map<String, CustomParamConfig> customParams;
	
	// Parent datasource reference
	private DataSourceConfig parent;
	private List<DataSourceConfig> children;
	
	private IDSHandler handler;
	
	public DataSourceConfig() {
		this.customParams = new HashMap<String, CustomParamConfig>();
		this.children = new ArrayList<DataSourceConfig>();
	}
	
	public DataSourceConfig(String filePath) {
		this.filePath = filePath;
		this.customParams = new HashMap<String, CustomParamConfig>();
		this.children = new ArrayList<DataSourceConfig>();
	}
	
	public DataSourceConfig(Boolean spamOrHam, String filePath) {
		this.spam = spamOrHam;
		this.filePath = filePath;
		this.customParams = new HashMap<String, CustomParamConfig>();
		this.children = new ArrayList<DataSourceConfig>();
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

	public Map<String, CustomParamConfig> getCustomParams() {
		return customParams;
	}

	public void setCustomParams(Map<String,
			CustomParamConfig> customParams) {
		this.customParams = customParams;
	}
	
	public Boolean getSpam() {
		return spam;
	}

	public void setSpam(Boolean spam) {
		this.spam = spam;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-- DataSourceConfig --\n").
		append("Name: ").append(name).append("\n").
		append("Type: ").append(type).append("\n").
		//append("DSClassName: ").append(dsClassName).append("\n").
		//append("HandlerClassName: ").append(handlerClassName).append("\n").
		append("FilePath: ").append(filePath).append("\n");
		if (spam != null) {
			sb.append("Spam: ").append(spam).append("\n");
		}
		if (maxElements != null) {
			sb.append("MaxElements: ").append(maxElements).append("\n");
		}
		
		if (!customParams.isEmpty()) {
			sb.append("Custom params: \n");
			for (String key: customParams.keySet()) {
				CustomParamConfig customParam = 
							customParams.get(key);
				String value = customParam.getValue();
				if (value != null && !value.equals("")) {
					sb.append(key).append(": ").append(value).append("\n");
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Request a new datasource Id
	 * @return id @type of Integer
	 */
	public static int getNextId() {
		return nextId++;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(DataSourceConfig obj) {
		int lastCmp = name.compareTo(obj.name);
        return lastCmp;
	}
}
