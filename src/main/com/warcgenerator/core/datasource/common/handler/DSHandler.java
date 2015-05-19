package com.warcgenerator.core.datasource.common.handler;

import java.util.Map;

import org.apache.log4j.Logger;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ReadURLHelper;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusStates;

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
	
	private static Logger logger = Logger.getLogger(DSHandler.class);

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
	
	@Override
	public void toHandle(Map<String, DataBean> urlsSpam,
			Map<String, DataBean> urlsHam, int maxURLsSpam, GenerateCorpusState auditor) {
		DataBean data = null;
		boolean stop = false;
		Integer maxElements = dsConfig.getMaxElements();
		int maxURLsHam = ReadURLHelper.getMaxSitesHam(config);
		
		// Read a block from datasource
		while ((data = ds.read()) != null
				&& auditor.getNumUrlReadedFromDS() != config.getNumSites()
				&& !stop) {
			auditor.setState(GenerateCorpusStates.GETTING_URLS_FROM_DS);
			auditor.setCurrentUrlReadedFromDS(data.getUrl());
			
			// Check if there is a limit
			if (maxElements != null) {
				stop = maxElements == 0?true:false;
				maxElements--;
			}
			
			// Add DataSourceConfig
			data.setDsConfig(dsConfig);
			
			// Force to be spam/ham
			if (dsConfig.getSpam() != null) {
				data.setSpam(dsConfig.getSpam());
			}
			
			// Method to implement
			handle(data);
			
			if (data.isSpam()) {
				ReadURLHelper.addUrl(urlsSpam, data, auditor, maxURLsSpam);
			} else {
				ReadURLHelper.addUrl(urlsHam, data, auditor, maxURLsHam);
			}
		}
	}
}
