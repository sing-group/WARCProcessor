package com.warcgenerator.core.datasource.handler;

import java.io.File;
import java.util.Map;
import java.util.Set;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.plugin.webcrawler.IWebCrawlerHandler;
import com.warcgenerator.core.plugin.webcrawler.WebCrawlerHandler;

public abstract class DSHandler implements IDSHandler {
	protected IDataSource ds;
	protected DataSourceConfig dsConfig;
	protected OutputWarcConfig outputWarcConfig;
	protected OutputCorpusConfig outputCorpusConfig;
	protected AppConfig config;

	public DSHandler(IDataSource ds, AppConfig config) {
		this.ds = ds;
		this.config = config;
		dsConfig = ds.getDataSourceConfig();

		// Create a output corpus with config
		if (config.getOutputConfig() instanceof OutputCorpusConfig) {
			outputCorpusConfig = (OutputCorpusConfig) config.getOutputConfig();
		} else {
			throw new OutCorpusCfgNotFoundException();
		}

	}

	public abstract void handle(DataBean data);
	
	public void toHandle(Set<String> urls,
			Map<String, IWebCrawlerHandler> webCrawlerHandlers,
			Map<String, DataSource> outputDS, 
			IDataSource labeledDS,
			IDataSource notFoundDS) {
		DataBean data = null;
		// Read a block from datasource
		while ((data = ds.read()) != null) {
			urls.add(data.getUrl());
			
			// Method to implement
			handle(data);
			
			// Common
			// Get output dir
			outputWarcConfig = getOutputWarcPath(data.isSpam());
			
			// Open an output datasource
			DataSource warcDS = outputDS.get(outputWarcConfig.getFileName());

			if (warcDS == null) {
				warcDS = new WarcDS(outputWarcConfig);
				outputDS.put(outputWarcConfig.getFileName(), warcDS);
			}
			
			// Read specific web
			IWebCrawlerHandler webCrawlerHandler = new WebCrawlerHandler(
					data.isSpam(), notFoundDS, labeledDS,
					warcDS);
			
			webCrawlerHandlers.put(FileHelper.getDomainNameFromURL(data.getUrl()), 
					webCrawlerHandler);
		}
	}

	protected OutputWarcConfig getOutputWarcPath(boolean isSpam) {
		StringBuilder outputWarcPath = new StringBuilder();
		if (isSpam) {
			outputWarcPath.append(outputCorpusConfig.getSpamDir());
		} else {
			outputWarcPath.append(outputCorpusConfig.getHamDir());
		}
		outputWarcPath.append(File.separator);

		// Warc file name
		// Obtenemos el nombre del fichero
		File f = new File(ds.getDataSourceConfig().getFilePath());
		
		StringBuilder warcFileName = new StringBuilder();
		warcFileName.append(outputWarcPath.toString()).append(f.getName());
		
		// Initialize output Warc config
		return new OutputWarcConfig(ds.getDataSourceConfig()
				.isSpam(), warcFileName.toString());
	}
}
