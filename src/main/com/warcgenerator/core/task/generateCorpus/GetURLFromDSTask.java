package com.warcgenerator.core.task.generateCorpus;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.ReadURLHelper;
import com.warcgenerator.core.task.ITask;
import com.warcgenerator.core.task.Task;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusStates;

public class GetURLFromDSTask extends Task implements ITask {
	private GenerateCorpusState generateCorpusState;
	private AppConfig config;
	private Map<String, DataBean> urlsSpam, urlsHam;

	private static Logger logger = LogManager.getLogger(CheckActiveSitesConfigTask.class);

	public GetURLFromDSTask(AppConfig config,
			GenerateCorpusState generateCorpusState,
			Map<String, DataBean> urlsSpam, Map<String, DataBean> urlsHam) {
		this.generateCorpusState = generateCorpusState;
		this.config = config;
		this.urlsSpam = urlsSpam;
		this.urlsHam = urlsHam;
	}

	public void execute() {
		logger.info("Task start");
		Map<String, DataBean> urlsSpamTmp = new LinkedHashMap<String, DataBean>();
		Map<String, DataBean> urlsHamTmp = new LinkedHashMap<String, DataBean>();

		generateCorpusState.setState(GenerateCorpusStates.GETTING_URLS_FROM_DS);

		int numSitesSpam = ReadURLHelper.getMaxSitesSpam(config);

		Set<String> domainsSpam = new HashSet<String>();
		Set<String> domainsHam = new HashSet<String>();

		// Get all DSHandlers for each DS
		// First the ham
		for (DataSourceConfig dsConfig : config.getDataSourceConfigs().values()) {
			// Only get enabled DS
			if (dsConfig.getEnabled()) {
				getUrls(dsConfig, urlsSpamTmp, urlsHamTmp, numSitesSpam);
			}
		}
		
		// Get sites from urlsSpam, urlsHam
		for (String url:urlsSpamTmp.keySet()) {
			domainsSpam.add(FileHelper.getURLWithoutParams(url));
		}

		for (String url:urlsHamTmp.keySet()) {
			domainsHam.add(FileHelper.getURLWithoutParams(url));
		}

		for (String url : urlsSpamTmp.keySet()) {
			String domain = FileHelper.getURLWithoutParams(url);
			if (domainsSpam.contains(domain)) {
				urlsSpam.put(url, urlsSpamTmp.get(url));
			}
		}

		for (String url : urlsHamTmp.keySet()) {
			String domain = FileHelper.getURLWithoutParams(url);
			if (domainsHam.contains(domain)) {
				urlsHam.put(url, urlsHamTmp.get(url));
			}
		}

		logger.info("Task completed");
	}

	public void rollback() {
		logger.info("Rollback");
	}

	private void getUrls(DataSourceConfig dsConfig,
			Map<String, DataBean> urlsSpam, Map<String, DataBean> urlsHam, int numSitesSpam) {
		if (dsConfig.getHandler() != null) {
			dsConfig.getHandler().toHandle(urlsSpam, urlsHam, numSitesSpam,
					generateCorpusState);
		}

		for (DataSourceConfig dsChild : dsConfig.getChildren()) {
			getUrls(dsChild, urlsSpam, urlsHam, numSitesSpam);
		}
	}
}
