package com.warcgenerator.core.logic;

import java.util.List;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.exception.logic.LogicException;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.helper.FileHelper;

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
		
		// Initialize datasources
		/*for (DataSourceConfig dsConfig : config.getDataSourceConfigs()) {
			DataSource ds = null;
			switch (dsConfig.getTypeDS()) {
			case DataSourceConfig.WARC_DS:
				ds = new WarcDS(dsConfig.getFilePath());
				break;
			case DataSourceConfig.TXT_DS:
				ds = new FileDS(dsConfig.getFilePath());
				break;
			case DataSourceConfig.ARFF_DS:
				ds = new ArffDS(dsConfig.getFilePath());
				break;
			default:
				throw new DataSourceNotRecognizedException();
			}
			ds.setDataSourceConfig(dsConfig);
			dataSources.add(ds);
		}*/
		
		// Create a output corpus with config
		if (config.getOutputConfig() instanceof OutputCorpusConfig) {
			outputCorpusConfig = (OutputCorpusConfig) config.getOutputConfig();
		} else {
			throw new OutCorpusCfgNotFoundException();
		}
		
		// Corpus Path dirs
		String dirs[] = { outputCorpusConfig.getOutputDir(),
				outputCorpusConfig.getSpamDir(), outputCorpusConfig.getHamDir() };
		System.out.println("Directorios:");
		for(String dir:dirs) {
			System.out.println(dir);
		}
		
		FileHelper.createDirs(dirs);
	}

	public void generateCorpus() throws LogicException {		
		// Generate wars
		// Read sources
		for (IDSHandler dsHandler : dsHandlers) {
			dsHandler.toHandle();
		}

		// Generate wars
		// Read sources
		/*
		 * for (IDataSource ds : dataSources) { // Build Warc output dir
		 * StringBuilder outputWarcPath = new StringBuilder(); if
		 * (ds.getDataSourceConfig().isSpam()) {
		 * outputWarcPath.append(pathSpam); } else {
		 * outputWarcPath.append(pathHam); }
		 * outputWarcPath.append(File.separator);
		 * 
		 * DataBean data = null;
		 * 
		 * System.out.println("Leyendo del datasource!!!");
		 * 
		 * // Read each line from input datasource to get urls while ((data =
		 * ds.read()) != null) { System.out.println("Entrando!!!");
		 * 
		 * UrlBean urlBean = null; if (data instanceof UrlBean) { urlBean =
		 * (UrlBean)data; } else { throw new DataSourceNotRecognizedException();
		 * }
		 * 
		 * System.out.println("urlBean es: " + urlBean.getUrl());
		 * 
		 * // Warc filename StringBuilder warcFileName = new StringBuilder();
		 * warcFileName.append(outputWarcPath.toString()).
		 * append(FileHelper.getFileNameFromURL(urlBean.getUrl()));
		 * 
		 * // Initialize output Warc config OutputWarcConfig outputWarcConfig =
		 * new OutputWarcConfig(ds .getDataSourceConfig().isSpam(),
		 * warcFileName.toString());
		 * 
		 * // Datasource fileOuput DataSource warcDS = new
		 * WarcDS(outputWarcConfig);
		 * 
		 * // Read specific web WebCrawlerHandler webCrawlerHandler = new
		 * WebCrawlerHandler( ds.getDataSourceConfig().isSpam(), notFoundDS,
		 * labeledDS, warcDS);
		 * 
		 * // Initialize web crawler WebCrawlerConfig webCrawlerConfig = new
		 * WebCrawlerConfig( config.getWebCrawlerCfgTemplate());
		 * webCrawlerConfig.setUrl(urlBean.getUrl()); IWebCrawler webCrawler =
		 * new Crawler4JAdapter(webCrawlerConfig, webCrawlerHandler);
		 * 
		 * // Start crawler webCrawler.start();
		 * 
		 * warcDS.close(); } ds.close(); }
		 */
		//labeledDS.close();
		//notFoundDS.close();
	}
}
