package com.warcgenerator.core.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.common.GenerateCorpusState;
import com.warcgenerator.core.common.GenerateCorpusStates;
import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.Constants;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.WebCrawlerConfig;
import com.warcgenerator.core.datasource.GenericDS;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.config.LoadDataSourceException;
import com.warcgenerator.core.exception.logic.LogicException;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.XMLConfigHelper;
import com.warcgenerator.core.plugin.webcrawler.Crawler4JAdapter;
import com.warcgenerator.core.plugin.webcrawler.IWebCrawler;
import com.warcgenerator.core.plugin.webcrawler.WebCrawlerBean;

/**
 * Business logic layer
 * 
 * @author Miguel Callon
 * 
 */
public class AppLogicImpl extends AppLogic implements IAppLogic {
	private AppConfig config;
	private OutputCorpusConfig outputCorpusConfig;
	private Map<String, DataSourceConfig> dataSourcesTypes;
	
	public AppLogicImpl(AppConfig config) throws LogicException {
		this.config = config;
		
		// ConfigHelper.getDSHandlers(config);
		
		dataSourcesTypes = new HashMap<String, DataSourceConfig>();
		XMLConfigHelper.getDataSources(Constants.dataSourcesTypesXML,
				dataSourcesTypes);
		
		// Create a output corpus with config
		if (config.getOutputConfig() instanceof OutputCorpusConfig) {
			outputCorpusConfig = (OutputCorpusConfig) config.getOutputConfig();
		} else {
			throw new OutCorpusCfgNotFoundException();
		}

		// Corpus Path dirs
		String dirs[] = { outputCorpusConfig.getOutputDir(),
				outputCorpusConfig.getSpamDir(), outputCorpusConfig.getHamDir() };

		// Delete directories
		if (config.getFlushOutputDir()) {
			FileHelper.removeDirsIfExist(dirs);
		}
		
		FileHelper.createDirs(dirs);
	}
	
	public void updateAppConfig(AppConfig appConfig) throws LogicException {
		try {
			appConfig.validate();
		} catch (ConfigException e) {
			throw new LogicException(e);
		}
	
		try {
			BeanUtils.copyProperties(config, appConfig);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}	
	}
	
	public AppConfig getAppConfig() throws LogicException {
		AppConfig appConfigCopy = new AppConfig();
		try {
			BeanUtils.copyProperties(appConfigCopy, config);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
		return appConfigCopy;
	}
	
	public List<DataSourceConfig> getDataSourceTypesList() throws 
		LogicException {
		List<DataSourceConfig> dataSourceTypesList =
				new ArrayList<DataSourceConfig>();
		
		for (DataSourceConfig dsConfig:dataSourcesTypes.values()) {
			DataSourceConfig dsConfigCopy = new DataSourceConfig();
			try {
				BeanUtils.copyProperties(dsConfigCopy, dsConfig);
			} catch (IllegalAccessException e) {
				throw new LogicException(e);
			} catch (InvocationTargetException e) {
				throw new LogicException(e);
			}
			dataSourceTypesList.add(dsConfigCopy);
		}
		
		return dataSourceTypesList;
	}
	
	public DataSourceConfig getDataSourceType(String type) {
		DataSourceConfig dsConfigCopy = new DataSourceConfig();
		DataSourceConfig dsConfig = dataSourcesTypes.get(type);
		try {
			BeanUtils.copyProperties(dsConfigCopy, dsConfig);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
		return dsConfigCopy;
	}
	
	public List<DataSourceConfig> getDataSourceConfigList() {
		List<DataSourceConfig> dataSourceConfigsList =
				new ArrayList<DataSourceConfig>();
		
		for (DataSourceConfig dsConfig:config.getDataSourceConfigs().values()) {
			DataSourceConfig dsConfigCopy = new DataSourceConfig();
			try {
				BeanUtils.copyProperties(dsConfigCopy, dsConfig);
			} catch (IllegalAccessException e) {
				throw new LogicException(e);
			} catch (InvocationTargetException e) {
				throw new LogicException(e);
			}
			dataSourceConfigsList.add(dsConfigCopy);
		}
		
		return dataSourceConfigsList;
	}
	
	public DataSourceConfig getDataSourceConfig(String name) {
		DataSourceConfig dsConfigCopy = new DataSourceConfig();
		DataSourceConfig dsConfig = config.getDataSourceConfigs().
				get(name);
		try {
			BeanUtils.copyProperties(dsConfigCopy, dsConfig);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
		return dsConfigCopy;
	}
	
	/**
	 * Add a new DataSource
	 * @param dsConfig
	 * @throws LoadDataSourceException
	 */
	public void addDataSourceConfig(DataSourceConfig dsConfig) 
			throws LoadDataSourceException{
		if (dsConfig.getId() == null) {
			dsConfig.setId(DataSourceConfig.nextId);		
		}
		//ConfigHelper.getDSHandler(dsConfig, config);
		config.getDataSourceConfigs().put(
				dsConfig.getId(), dsConfig);
	}
	
	/**
	 * Remove a DataSource
	 * @param name
	 */
	public void removeDataSourceConfig(Integer name) {
		config.getDataSourceConfigs().remove(name);
	}

	public void generateCorpus(GenerateCorpusState generateCorpusState)
			throws LogicException {
		// Get dsHandlers
		ConfigHelper.getDSHandlers(config);
		
		// Generate wars
		IDataSource labeledDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsLabeledFilePath()));
		IDataSource notFoundDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsNotFoundFilePath()));		
		
		// Init data structures
		Set<String> urlsSpam = new HashSet<String>();
		Set<String> urlsHam = new HashSet<String>();
		
		generateCorpusState.setState(GenerateCorpusStates.GETTING_URLS_FROM_DS);
		
		// Get all DSHandlers for each DS
		// First the ham
		for (DataSourceConfig dsConfig : config.getDataSourceConfigs().values()) {
			getUrls(dsConfig, urlsSpam, urlsHam);
		}
		
		generateCorpusState.setState(GenerateCorpusStates.READING_SPAM);
		generateCorpusState.setWebsToVisitTotal(urlsSpam.size());
		WebCrawlerBean webCrawlerSpam = new WebCrawlerBean( 
				  labeledDS,
				  notFoundDS,
				  true,
				  outputCorpusConfig);
		// Start crawling urls in batch
		startWebCrawling(generateCorpusState,
				urlsSpam, webCrawlerSpam);
		
		generateCorpusState.setState(GenerateCorpusStates.READING_HAM);
		generateCorpusState.setWebsToVisitTotal(urlsHam.size());
		WebCrawlerBean webCrawlerHam = new WebCrawlerBean( 
				  labeledDS,
				  notFoundDS,
				  false,
				  outputCorpusConfig);
		startWebCrawling(generateCorpusState,
				urlsHam, webCrawlerHam);

		generateCorpusState.setState(GenerateCorpusStates.ENDING);
		labeledDS.close();
		notFoundDS.close();
	}
	
	private void getUrls(DataSourceConfig dsConfig, Set<String> urlsSpam,
			Set<String> urlsHam) {
		if (dsConfig.getHandler() != null) {
			dsConfig.getHandler().toHandle(urlsSpam, urlsHam);
		}
		for (DataSourceConfig dsChild: dsConfig.getChildren()) {
			getUrls(dsChild, urlsSpam, urlsHam);
		}
	}
	
	private void startWebCrawling(GenerateCorpusState generateCorpusState,
			Set<String> urls,
			WebCrawlerBean webCrawlerBean) {
		//config.getWebCrawlerCfgTemplate().setMaxDepthOfCrawling(0);

		// Initialize web crawler
		WebCrawlerConfig webCrawlerConfig = new WebCrawlerConfig(
				config.getWebCrawlerCfgTemplate());
		webCrawlerConfig.setUrls(urls);
		IWebCrawler webCrawler = new Crawler4JAdapter(generateCorpusState,
				webCrawlerConfig,
				webCrawlerBean);

		// Start crawler
		webCrawler.start();
	}

}
