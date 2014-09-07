package com.warcgenerator.core.task.generateCorpus;

import java.util.Map;

import org.apache.log4j.Logger;

import com.warcgenerator.core.common.GenerateCorpusState;
import com.warcgenerator.core.common.GenerateCorpusStates;
import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.task.ITask;
import com.warcgenerator.core.task.Task;

public class GetURLFromDSTask extends Task implements ITask {
	private GenerateCorpusState generateCorpusState;
	private AppConfig config;
	private Map<String, DataBean> urlsSpam, urlsHam;
	
	private static Logger logger = Logger
			.getLogger(CheckActiveSitesConfigTask.class);
	
	public GetURLFromDSTask(
			AppConfig config,
			GenerateCorpusState generateCorpusState,
			Map<String, DataBean> urlsSpam, Map<String, DataBean> urlsHam) {
		this.generateCorpusState = generateCorpusState;
		this.config = config;
		this.urlsSpam = urlsSpam;
		this.urlsHam = urlsHam;
	}
	
	public void execute() {
		logger.info("Task start");
		generateCorpusState
				.setState(GenerateCorpusStates.GETTING_URLS_FROM_DS);
		// Get all DSHandlers for each DS
		// First the ham
		for (DataSourceConfig dsConfig : config.getDataSourceConfigs()
				.values()) {
			getUrls(dsConfig, urlsSpam, urlsHam);
		}
		logger.info("Task completed");
	}
	
	public void rollback() {
		// Rollback
		logger.info("Rollback");
	}
	
	private void getUrls(DataSourceConfig dsConfig, Map<String, DataBean> urlsSpam,
			Map<String, DataBean> urlsHam) {
		if (dsConfig.getHandler() != null) {
			dsConfig.getHandler().toHandle(urlsSpam, urlsHam);
		}
		for (DataSourceConfig dsChild : dsConfig.getChildren()) {
			getUrls(dsChild, urlsSpam, urlsHam);
		}
	}
}
