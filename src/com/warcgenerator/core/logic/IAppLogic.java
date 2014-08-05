package com.warcgenerator.core.logic;

import java.util.Collection;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;

/**
 * Business logic layer interface
 * 
 * @author Miguel Callon
 *
 */
public interface IAppLogic {
	AppConfig getAppConfig();
	Collection<DataSourceConfig> getDataSourceTypesList();
	DataSourceConfig getDataSourceType(String type);
	Collection<DataSourceConfig> getDataSourceConfigList();
	void updateDataSourceConfig(String name,
			DataSourceConfig dsConfig);
	void addDataSourceConfig(DataSourceConfig dsConfig);
	void removeDataSourceConfig(String name);
	void generateCorpus();
}
