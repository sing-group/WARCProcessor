package com.warcgenerator.datasources.handler;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.datasource.handler.DSHandler;
import com.warcgenerator.core.datasource.handler.IDSHandler;

public class FileDSHandler extends DSHandler implements IDSHandler {
	public FileDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	}

	public void handle(DataBean data) {
	
	}
}
