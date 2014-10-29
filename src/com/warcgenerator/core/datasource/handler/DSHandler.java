package com.warcgenerator.core.datasource.handler;

import java.util.Map;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.FileHelper;

/**
 * Read the urls from the input datasources and
 * get the information about urls to crawl. 
 * 
 * @author Miguel Callon
 *
 */
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

	public DataSourceConfig getDSConfig() {
		return dsConfig;
	}
	
	public abstract void handle(DataBean data);
	
	public void toHandle(Map<String, DataBean> urlsSpam,
			Map<String, DataBean> urlsHam) {
		DataBean data = null;
		boolean stop = false;
		Integer maxElements = dsConfig.getMaxElements();
		
		// Read a block from datasource
		while ((data = ds.read()) != null
				&& !stop) {
			// Check if there is a limit
			if (maxElements != null) {
				stop = maxElements == 0?true:false;
				maxElements--;
			}
			
			// Add DataSourceConfig
			data.setDsConfig(dsConfig);
			
			// Method to implement
			handle(data);
			
			if (data.isSpam()) {
				addToUrls(urlsSpam, data);
			} else {
				addToUrls(urlsHam, data);
			}
		}
	}
	
	private void addToUrls(Map<String, DataBean> urls, 
			DataBean data) {
		// check if this url already exists in the list
		String url = FileHelper.normalizeURL(data.getUrl());
		DataBean aux = urls.get(url);
		if (aux == null) {
			// If is a new url only add it
			urls.put(url, data);
		} else {
			// If the url is not from warc type, add it
			if (!aux.getTypeDS().equals(WarcDS.DS_TYPE)) {
				urls.put(url, data);
			}
		}
	}
}
