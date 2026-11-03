package com.warcgenerator.core.plugin.webcrawler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.HttpEntityWrapper;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

import crawlercommons.filters.basic.BasicURLNormalizer;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.exceptions.PageBiggerThanMaxSizeException;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.url.WebURL;

public class CustomPageFetcher extends PageFetcher {
	private GenerateCorpusState generateCorpusState;
	private Map<String, DataBean> urls;
	private AppConfig appConfig;
	
	public CustomPageFetcher(GenerateCorpusState generateCorpusState, 
			CrawlConfig config, BasicURLNormalizer normalizer, AppConfig appConfig,
			Map<String, DataBean> urls) throws GeneralSecurityException {
		super(config, normalizer);
		this.setAppConfig(appConfig);
		this.generateCorpusState =
				generateCorpusState;
		this.urls = urls;
	}

	/**
	 * crawler4j-core discards responses without a Content-Type header, so
	 * treat them as HTML.
	 */
	@Override
	public PageFetchResult fetchPage(WebURL webUrl) throws InterruptedException, IOException,
			PageBiggerThanMaxSizeException, URISyntaxException {
		PageFetchResult result = super.fetchPage(webUrl);
		HttpEntity entity = result.getEntity();
		if (entity != null && (entity.getContentType() == null || entity.getContentType().isBlank())) {
			result.setEntity(new HttpEntityWrapper(entity) {
				@Override
				public String getContentType() {
					return "text/html";
				}
			});
		}
		return result;
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
