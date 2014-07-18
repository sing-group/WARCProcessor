package com.warcgenerator.core.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.WebCrawlerConfig;
import com.warcgenerator.core.datasource.GenericDS;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.exception.logic.LogicException;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.helper.FileHelper;
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
	private List<IDSHandler> dsHandlers;
	private AppConfig config;
	private OutputCorpusConfig outputCorpusConfig;

	public AppLogicImpl(AppConfig config) throws LogicException {
		this.config = config;
		dsHandlers = ConfigHelper.getDSHandlers(config);

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
		for (IDSHandler dsHandler : dsHandlers) {
			dsHandler.toHandle(urlsSpam,
						urlsHam);
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
