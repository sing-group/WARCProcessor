package com.warcgenerator.datasources.handler;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.datasource.handler.DSHandler;
import com.warcgenerator.core.datasource.handler.IDSHandler;

/**
 * Handle the read about a specific Arff Datasource read operation
 * @author amparop
 *
 */
public class ArffDSHandler extends DSHandler implements IDSHandler {	
	public ArffDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	}

	@Override
	public void handle(DataBean dataBean) {
		// Not implemented 
	}
}
