package com.warcgenerator.core.datasource.handler;

import java.util.Map;
import java.util.Set;

import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.plugin.webcrawler.IWebCrawlerHandler;

public interface IDSHandler {
	public void toHandle(Set<String> urls, Map<String, 
			IWebCrawlerHandler> webCrawlerHandlers,
			Map<String, DataSource> outputDS,
			IDataSource labeledDS,
			IDataSource notFoundDS);
}
