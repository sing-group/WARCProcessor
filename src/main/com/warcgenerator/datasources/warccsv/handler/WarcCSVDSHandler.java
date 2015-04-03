package com.warcgenerator.datasources.warccsv.handler;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.common.handler.DSHandler;
import com.warcgenerator.core.datasource.common.handler.IDSHandler;

public class WarcCSVDSHandler extends DSHandler implements IDSHandler {	
	public WarcCSVDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	}
	
	public void handle(DataBean data) {
		// Not implemented
	}
}
