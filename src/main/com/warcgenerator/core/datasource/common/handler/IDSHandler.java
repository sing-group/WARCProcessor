package com.warcgenerator.core.datasource.common.handler;

import java.util.Map;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

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
			Map<String, DataBean> urlsHam, GenerateCorpusState auditor);
}
