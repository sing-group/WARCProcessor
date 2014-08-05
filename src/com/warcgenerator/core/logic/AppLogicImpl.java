package com.warcgenerator.core.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.Constants;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.WebCrawlerConfig;
import com.warcgenerator.core.datasource.GenericDS;
import com.warcgenerator.core.datasource.IDataSource;
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
		
		ConfigHelper.getDSHandlers(config);
		
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
		ConfigHelper.getDSHandler(dsConfig, config);
		config.getDataSourceConfigs().put(
				dsConfig.getName(), dsConfig);
	}
	
	/**
	 * Update an exist DataSource
	 * @param name
	 * @param dsConfig
	 */
	public void updateDataSourceConfig(String name,
			DataSourceConfig dsConfig) {
		removeDataSourceConfig(name);
		addDataSourceConfig(dsConfig);
	}
	
	/**
	 * Remove a DataSource
	 * @param name
	 */
	public void removeDataSourceConfig(String name) {
		config.getDataSourceConfigs().remove(name);
	}

	public void generateCorpus() throws LogicException {
		// Generate wars
		IDataSource labeledDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsLabeledFilePath()));
		IDataSource notFoundDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsNotFoundFilePath()));		
		
		// Init data structures
		Set<String> urlsSpam = new HashSet<String>();
		Set<String> urlsHam = new HashSet<String>();
		
		// Get all DSHandlers for each DS
		// First the ham
		for (DataSourceConfig dsConfig : config.getDataSourceConfigs().values()) {
			getUrls(dsConfig, urlsSpam, urlsHam);
		}
		
		WebCrawlerBean webCrawlerSpam = new WebCrawlerBean( 
				  labeledDS,
				  notFoundDS,
				  true,
				  outputCorpusConfig);
		// Start crawling urls in batch
		startWebCrawling(urlsSpam, webCrawlerSpam);
		
		WebCrawlerBean webCrawlerHam = new WebCrawlerBean( 
				  labeledDS,
				  notFoundDS,
				  false,
				  outputCorpusConfig);
		startWebCrawling(urlsHam, webCrawlerHam);

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
	
	private void startWebCrawling(Set<String> urls,
			WebCrawlerBean webCrawlerBean) {
		//config.getWebCrawlerCfgTemplate().setMaxDepthOfCrawling(0);

		// Initialize web crawler
		WebCrawlerConfig webCrawlerConfig = new WebCrawlerConfig(
				config.getWebCrawlerCfgTemplate());
		webCrawlerConfig.setUrls(urls);
		IWebCrawler webCrawler = new Crawler4JAdapter(webCrawlerConfig,
				webCrawlerBean);

		// Start crawler
		webCrawler.start();
	}

}
