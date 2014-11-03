package com.warcgenerator.core.datasource.handler;

import java.util.Map;
import java.util.Set;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.bean.DataBean;

/**
 * Read the urls from the input datasources and
 * get the information about urls to crawl. 
 * 
 * @author Miguel Callon
 *
 */
public interface IDSHandler {
	public DataSourceConfig getDSConfig();
	public void toHandle(Map<String, DataBean> urlsSpam,
			Map<String, DataBean> urlsHam);
}
