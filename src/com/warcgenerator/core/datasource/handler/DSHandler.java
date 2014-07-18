package com.warcgenerator.core.datasource.handler;

import java.util.Set;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;

/**
 *  
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
	
	public void toHandle(Set<String> urlsSpam,
			Set<String> urlsHam) {
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
			
			// Method to implement
			handle(data);
			
			if (data.isSpam()) {				
				urlsSpam.add(data.getUrl());
			} else {
				urlsHam.add(data.getUrl());
			}
		}
	}
}
