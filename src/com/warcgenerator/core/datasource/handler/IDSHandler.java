package com.warcgenerator.core.datasource.handler;

import java.util.Map;
import java.util.Set;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.plugin.webcrawler.IWebCrawlerHandler;

public interface IDSHandler {
	public DataSourceConfig getDSConfig();
	public void toHandle(Set<String> urlsSpam,
			Set<String> urlsHam,
			Map<String, IWebCrawlerHandler> webCrawlerHandlersSpam,
			Map<String, IWebCrawlerHandler> webCrawlerHandlersHam,
			Map<String, DataSource> outputDS,
			IDataSource labeledDS,
			IDataSource notFoundDS);
}
