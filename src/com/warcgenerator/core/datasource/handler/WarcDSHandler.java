package com.warcgenerator.core.datasource.handler;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;

public class WarcDSHandler extends DSHandler implements IDSHandler {	
	public WarcDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	}
	
	public void handle(DataBean data) {
		// Not implemented
	}
}
