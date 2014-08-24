package com.warcgenerator.core.task.generateCorpus;

import java.util.Set;

import com.warcgenerator.core.common.GenerateCorpusState;
import com.warcgenerator.core.common.GenerateCorpusStates;
import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.WebCrawlerConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.plugin.webcrawler.Crawler4JAdapter;
import com.warcgenerator.core.plugin.webcrawler.IWebCrawler;
import com.warcgenerator.core.plugin.webcrawler.WebCrawlerBean;
import com.warcgenerator.core.task.ITask;
import com.warcgenerator.core.task.Task;

public class ReadSpamTask extends Task implements ITask {
	private GenerateCorpusState generateCorpusState;
	private AppConfig config;
	private Set<String> urlsSpam;
	private IDataSource labeledDS, notFoundDS;
	private OutputCorpusConfig outputCorpusConfig;
	private IWebCrawler webCrawler;
	
	public ReadSpamTask(AppConfig config,
			OutputCorpusConfig outputCorpusConfig,
			GenerateCorpusState generateCorpusState,
			IDataSource labeledDS,
			IDataSource notFoundDS,
			Set<String> urlsSpam) {
		this.config = config;
		this.outputCorpusConfig = outputCorpusConfig;
		this.generateCorpusState = generateCorpusState;
		this.labeledDS = labeledDS;
		this.notFoundDS = notFoundDS;
		this.urlsSpam = urlsSpam;
	}
	
	public void execute() {
		generateCorpusState.setState(GenerateCorpusStates.READING_SPAM);
		generateCorpusState.setWebsToVisitTotal(urlsSpam.size());
		WebCrawlerBean webCrawlerSpam = new WebCrawlerBean(labeledDS,
				notFoundDS, true, outputCorpusConfig);
		
		WebCrawlerConfig webCrawlerConfig = new WebCrawlerConfig(
				config.getWebCrawlerCfgTemplate());
		webCrawlerConfig.setUrls(urlsSpam);
		IWebCrawler webCrawler = new Crawler4JAdapter(generateCorpusState,
				webCrawlerConfig, webCrawlerSpam);

		// Start crawler
		webCrawler.start();
	}
	
	public void rollback() {
		System.out.println("Rollback ReadSpamTask");
		webCrawler.stop();
	}
}