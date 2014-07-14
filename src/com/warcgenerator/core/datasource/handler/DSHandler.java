package com.warcgenerator.core.datasource.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.config.WebCrawlerConfig;
import com.warcgenerator.core.datasource.DataBean;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.GenericDS;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.plugin.webcrawler.Crawler4JAdapter;
import com.warcgenerator.core.plugin.webcrawler.IWebCrawler;
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
	
	public void toHandle() {
		DataBean data = null;
		// Config of ds

		IDataSource labeledDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsLabeledFilePath()));
		IDataSource notFoundDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsNotFoundFilePath()));
		
		// Save references to the datasource output files
		Map<String, DataSource> outputDS =
				new HashMap<String, DataSource>();
		
		// Read a block from datasource
		while ((data = ds.read()) != null) {
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

			System.out.println("Handle!!-->" + outputWarcConfig.getFileName() );
			
			// Read specific web
			WebCrawlerHandler webCrawlerHandler = new WebCrawlerHandler(
					data.isSpam(), notFoundDS, labeledDS,
					warcDS);

			startWebCrawling(data.getUrl(), webCrawlerHandler);
		}
		// Close all output datasources
		for(DataSource aux:outputDS.values()) {
			aux.close();
		}
		labeledDS.close();
		notFoundDS.close();
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
	
	private void startWebCrawling(String url,
			WebCrawlerHandler webCrawlerHandler) {
		config.getWebCrawlerCfgTemplate().setMaxDepthOfCrawling(0);

		// Initialize web crawler
		WebCrawlerConfig webCrawlerConfig = new WebCrawlerConfig(
				config.getWebCrawlerCfgTemplate());
		webCrawlerConfig.setUrl(url);
		IWebCrawler webCrawler = new Crawler4JAdapter(webCrawlerConfig,
				webCrawlerHandler);

		// Start crawler
		webCrawler.start();
	}
}
