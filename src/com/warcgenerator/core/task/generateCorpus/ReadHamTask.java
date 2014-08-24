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

public class ReadHamTask extends Task implements ITask {
	private GenerateCorpusState generateCorpusState;
	private AppConfig config;
	private Set<String> urlsHam;
	private IDataSource labeledDS, notFoundDS;
	private OutputCorpusConfig outputCorpusConfig;
	private IWebCrawler webCrawler;
	
	public ReadHamTask(AppConfig config,
			OutputCorpusConfig outputCorpusConfig,
			GenerateCorpusState generateCorpusState,
			IDataSource labeledDS,
			IDataSource notFoundDS,
			Set<String> urlsHam) {
		this.config = config;
		this.outputCorpusConfig = outputCorpusConfig;
		this.generateCorpusState = generateCorpusState;
		this.labeledDS = labeledDS;
		this.notFoundDS = notFoundDS;
		this.urlsHam = urlsHam;
	}
	
	public void execute() {
		generateCorpusState.setState(GenerateCorpusStates.READING_HAM);
		generateCorpusState.setWebsToVisitTotal(urlsHam.size());
		WebCrawlerBean webCrawlerHam = new WebCrawlerBean(labeledDS,
				notFoundDS, false, outputCorpusConfig);
		
		WebCrawlerConfig webCrawlerConfig = new WebCrawlerConfig(
				config.getWebCrawlerCfgTemplate());
		webCrawlerConfig.setUrls(urlsHam);
		webCrawler = new Crawler4JAdapter(generateCorpusState,
				webCrawlerConfig, webCrawlerHam);

		// Start crawler
		webCrawler.start();
	}
	
	public void rollback() {
		//System.out.println("Rollback ReadHamTask");
		//webCrawler.stop();
	}
}
