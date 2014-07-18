package com.warcgenerator.core.datasource.handler;

import java.util.Set;

import com.warcgenerator.core.config.DataSourceConfig;

/**
 * Read the urls from the input datasources and
 * get the information about urls to crawl. 
 * 
 * @author Miguel Callon
 *
 */
public interface IDSHandler {
	public DataSourceConfig getDSConfig();
	public void toHandle(Set<String> urlsSpam,
			Set<String> urlsHam);
}
