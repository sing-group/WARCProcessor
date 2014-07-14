package com.warcgenerator.datasources.handler;

import java.io.File;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.DataBean;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.handler.DSHandler;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.helper.FileHelper;

public class CSVDSHandler extends DSHandler implements IDSHandler {
	private DataBean dataBean;
	
	public CSVDSHandler(IDataSource ds, AppConfig config) {
		super(ds, config);
	}

	@Override
	public void handle(DataBean dataBean) {
		this.dataBean = dataBean; 
	}
	
	protected OutputWarcConfig getOutputWarcPath(boolean isSpam) {
		StringBuilder outputWarcPath = new StringBuilder();
		if (isSpam) {
			outputWarcPath.append(outputCorpusConfig.getSpamDir());
		} else {
			outputWarcPath.append(outputCorpusConfig.getHamDir());
		}
		outputWarcPath.append(File.separator);

		// Warc file name
		// Obtenemos el nombre del fichero
		File f = new File(ds.getDataSourceConfig().getFilePath());

		// TODO customize
		StringBuilder warcFileName = new StringBuilder();
		String url = FileHelper.getDomainNameFromURL(dataBean.getUrl());
		
		warcFileName.append(outputWarcPath.toString()).append(
				FileHelper.getFileNameFromURL(url))
		.append(".warc");

		// Initialize output Warc config
		return new OutputWarcConfig(ds.getDataSourceConfig()
				.isSpam(), warcFileName.toString());
	}
}
