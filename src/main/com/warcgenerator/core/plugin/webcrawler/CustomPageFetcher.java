package com.warcgenerator.core.plugin.webcrawler;

import java.util.Map;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;

public class CustomPageFetcher extends PageFetcher {
	private GenerateCorpusState generateCorpusState;
	private Map<String, DataBean> urls;
	private AppConfig appConfig;
	
	public CustomPageFetcher(GenerateCorpusState generateCorpusState, 
			CrawlConfig config, AppConfig appConfig, Map<String, DataBean> urls) {
		super(config);
		this.setAppConfig(appConfig);
		this.generateCorpusState =
				generateCorpusState;
		this.urls = urls;
	}

	public GenerateCorpusState getGenerateCorpusState() {
		return generateCorpusState;
	}

	public void setGenerateCorpusState(GenerateCorpusState generateCorpusState) {
		this.generateCorpusState = generateCorpusState;
	}

	public Map<String, DataBean> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, DataBean> urls) {
		this.urls = urls;
	}

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
}
