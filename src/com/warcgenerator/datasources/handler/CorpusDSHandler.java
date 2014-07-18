package com.warcgenerator.datasources.handler;

import java.io.File;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.datasource.handler.DSHandler;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.helper.FileHelper;

public class CorpusDSHandler extends DSHandler implements IDSHandler {
	private DataBean data;
	
	public CorpusDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	}

	public void handle(DataBean data) {
		this.data = data;
	}
}
