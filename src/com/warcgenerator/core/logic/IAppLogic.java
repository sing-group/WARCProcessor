package com.warcgenerator.core.logic;

import java.util.List;

import com.warcgenerator.core.config.DataSourceConfig;

/**
 * Business logic layer interface
 * 
 * @author Miguel Callon
 *
 */
public interface IAppLogic {
	public List<DataSourceConfig> getDataSourceTypesList();
	public void updateDataSourceConfig(String name,
			DataSourceConfig dsConfig);
	public void addDataSourceConfig(DataSourceConfig dsConfig);
	public void removeDataSourceConfig(String name);
	public void generateCorpus();
}
