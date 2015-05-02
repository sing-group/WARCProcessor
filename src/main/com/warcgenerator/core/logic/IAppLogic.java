package com.warcgenerator.core.logic;

import java.util.Collection;
import java.util.List;
import java.util.Observer;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.Country;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

/**
 * Business logic layer interface
 * 
 * @author Miguel Callon
 *
 */
public interface IAppLogic {
	// Callback messages
	String APP_CONFIG_LOADED_CALLBACK = "APP_CONFIG_LOADED_CALLBACK";
	String APP_CONFIG_SAVED_CALLBACK = "APP_CONFIG_SAVED_CALLBACK";
	String APP_CONFIG_SAVED_AS_CALLBACK = "APP_CONFIG_SAVED_AS_CALLBACK";
	String APP_CONFIG_UPDATED_CALLBACK = "APP_CONFIG_UPDATED_CALLBACK";
	String DATASOURCE_REMOVED_CALLBACK = "DATASOURCE_REMOVED_CALLBACK";
	String DATASOURCE_UPDATED_CALLBACK = "DATASOURCE_UPDATED_CALLBACK";
	String DATASOURCE_CREATED_CALLBACK = "DATASOURCE_CREATED_CALLBACK";
	
	void loadNewAppConfig();
	void saveAppConfig();
	void saveAsAppConfig(String path);
	String getConfigFilePath();
	void loadAppConfig(String path);
	void updateAppConfig(AppConfig appConfig);
	AppConfig getAppConfig();
	Collection<DataSourceConfig> getDataSourceTypesList();
	DataSourceConfig getDataSourceType(String type);
	DataSourceConfig getDataSourceById(int id);
	Collection<DataSourceConfig> getDataSourceConfigList();
	void addDataSourceConfig(DataSourceConfig dsConfig);
	void removeDataSourceConfig(int id);
	void stopGenerateCorpus();
	void generateCorpus(GenerateCorpusState generateCorpusState);
	void addObserver(Observer obs);
	void deleteObserver(Observer obs);
	List<Country> listAvailableLanguagesFilter();
	List<Country> listNotSelectedLanguages(
			List<Country> listSelectedCountries);
}
