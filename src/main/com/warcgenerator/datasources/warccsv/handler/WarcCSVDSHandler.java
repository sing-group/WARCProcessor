package com.warcgenerator.datasources.warccsv.handler;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.datasource.handler.DSHandler;
import com.warcgenerator.core.datasource.handler.IDSHandler;

public class WarcCSVDSHandler extends DSHandler implements IDSHandler {
	public WarcCSVDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	}

	@Override
	public void handle(DataBean dataBean) {
		// Not implemented 
	}
}
