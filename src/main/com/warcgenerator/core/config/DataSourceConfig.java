package com.warcgenerator.core.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.datasource.common.bean.Country;
import com.warcgenerator.core.datasource.common.handler.IDSHandler;
import com.warcgenerator.core.exception.config.DSConfigException;
import com.warcgenerator.core.exception.logic.LogicException;

/**
 * File with data source configuration
 * 
 * @author Miguel Callon
 * 
 */
public class DataSourceConfig implements Comparable<DataSourceConfig> {
	public static final boolean IS_SPAM = true;
	public static final boolean IS_HAM = false;
	public static final int ID_INIT_VALUE = 1;
	public static int nextId = ID_INIT_VALUE;

	private Integer id;
	private String name = "";
	private String type = "";
	private Boolean spam;
	private String filePath = "";
	private String dsClassName = "";
	private String handlerClassName = "";
	private List<Country> countryList;
	// Max number of elements to get from datasource
	private Integer maxElements;
	private boolean enabled = true;
	private Map<String, CustomParamConfig> customParams;
	private Boolean useRecursiveFolders = true;

	// Parent datasource reference
	private DataSourceConfig parent;
	private List<DataSourceConfig> children;

	private IDSHandler handler;

	public DataSourceConfig() {
		this.customParams = new LinkedHashMap<String, CustomParamConfig>();
		this.children = new ArrayList<DataSourceConfig>();
		this.setCountryList(new ArrayList<Country>());
	}

	public DataSourceConfig(String filePath) {
		this();
		this.filePath = filePath;
	}

	public DataSourceConfig(Boolean spamOrHam, String filePath) {
		this(filePath);
		this.spam = spamOrHam;
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

	public void setCustomParams(Map<String, CustomParamConfig> customParams) {
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
		
		sb.append("-- DataSourceConfig");
		if (!enabled) {
			sb.append(" (DISABLED)");
		}
		sb.append(" --\n");
		sb.append("Name: ").append(name).append("\n").
		   append("Type: ").append(type).append("\n").
		   append("Enabled: ").append(enabled).append("\n").
		   append("FilePath: ").append(filePath).append("\n");
		   // append("DSClassName: ").append(dsClassName).append("\n").
		   // append("HandlerClassName: ").append(handlerClassName).append("\n").
		if (spam != null) {
			sb.append("Spam: ").append(spam).append("\n");
		}
		if (maxElements != null) {
			sb.append("MaxElements: ").append(maxElements).append("\n");
		}

		if (!customParams.isEmpty()) {
			sb.append("Custom params: \n");
			for (String key : customParams.keySet()) {
				CustomParamConfig customParam = customParams.get(key);
				String value = customParam.getValue();
				if (value != null && !value.equals("")) {
					sb.append(" - ").append(key).append(": ").append(value).append("\n");
				}
			}
		}

		if (!countryList.isEmpty()) {
			sb.append("Countries: \n");
			for (Country country : countryList) {
				sb.append(country.getName()).append("\n");
			}
		}
		sb.append("-- END DataSourceConfig --\n");
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
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Request a new datasource Id
	 * 
	 * @return id @type of Integer
	 */
	public static int getNextId() {
		return nextId++;
	}
	
	public static void resetId() {
		nextId = ID_INIT_VALUE;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	@Override
	public int compareTo(DataSourceConfig obj) {
		int lastCmp = name.compareTo(obj.name);
		return lastCmp;
	}
	
	public Boolean getUseRecursiveFolders() {
		return useRecursiveFolders;
	}

	public void setUseRecursiveFolders(Boolean useRecursiveFolders) {
		this.useRecursiveFolders = useRecursiveFolders;
	}
	
	public boolean validate() throws DSConfigException {
		if (name == null || name.isEmpty()) {
			throw new DSConfigException("Name can not be empty");
		} 
		if (type == null || type.isEmpty()) {
			throw new DSConfigException("Type can not be empty");
		} 
		if (filePath == null ||
				filePath.isEmpty()) {
			throw new DSConfigException("File path can not be empty");
		}
		return true;
	}
	
	public static void copy(DataSourceConfig dest, DataSourceConfig src) {
		Map<String, CustomParamConfig> customParamsConfigCopy = 
				new LinkedHashMap<String, CustomParamConfig>();
		List<Country> countriesCopy = new ArrayList<Country>();

		try {
			BeanUtils.copyProperties(dest, src);
			// Copy Custom params
			for (String customParamConfig : src.getCustomParams().keySet()) {
				CustomParamConfig customParamConfigCopy = new CustomParamConfig();
				BeanUtils.copyProperties(customParamConfigCopy, src
						.getCustomParams().get(customParamConfig));

				customParamsConfigCopy.put(customParamConfig,
						customParamConfigCopy);
			}

			dest.setCustomParams(customParamsConfigCopy);

			for (Country country : src.getCountryList()) {
				Country countryCopy = new Country();
				BeanUtils.copyProperties(countryCopy, country);
				countriesCopy.add(countryCopy);
			}
			dest.setCountryList(countriesCopy);

		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
	}
}
