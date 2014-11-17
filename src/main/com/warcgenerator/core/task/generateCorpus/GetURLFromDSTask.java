package com.warcgenerator.core.task.generateCorpus;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.task.ITask;
import com.warcgenerator.core.task.Task;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusStates;

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
		Map<String, DataBean> urlsSpamTmp = new LinkedHashMap<String, DataBean>();
		Map<String, DataBean> urlsHamTmp = new LinkedHashMap<String, DataBean>();
		
		generateCorpusState
				.setState(GenerateCorpusStates.GETTING_URLS_FROM_DS);
		// Get all DSHandlers for each DS
		// First the ham
		for (DataSourceConfig dsConfig : config.getDataSourceConfigs()
				.values()) {
			getUrls(dsConfig, urlsSpamTmp, urlsHamTmp);
		}
		
		int numSitesSpam = 0;
		int numSitesHam = 0;
		if (config.getRatioIsPercentage()) {
			numSitesSpam = (int)Math.round(config.getNumSites() *
					(config.getRatioPercentageSpam()/(double)100));
		} else {
			numSitesSpam = config.getRatioQuantitySpam();
			
		}
		numSitesHam = config.getNumSites() - numSitesSpam;
		
		Set<String> domainsSpam = new HashSet<String>();
		Set<String> domainsHam = new HashSet<String>();
		
		// Get sites from urlsSpam, urlsHam
		int numSites = 0;
		for (Iterator<String> it = urlsSpamTmp.keySet().iterator();
			 it.hasNext() && numSites < numSitesSpam;) {
			String url = it.next();
			numSites++;
			domainsSpam.add(FileHelper.getURLWithoutParams(url));
		}
		
		numSites = 0;
		for (Iterator<String> it = urlsHamTmp.keySet().iterator();
			it.hasNext() && numSites < numSitesHam;) {
			String url = it.next();
			numSites++;
			domainsHam.add(FileHelper.getURLWithoutParams(url));
		}
		
		for (String url:urlsSpamTmp.keySet()) {
			String domain = FileHelper.getURLWithoutParams(url);
			if (domainsSpam.contains(domain)) {
				urlsSpam.put(url, urlsSpamTmp.get(url));
			}
		}
		
		for (String url:urlsHamTmp.keySet()) {
			String domain = FileHelper.getURLWithoutParams(url);
			if (domainsHam.contains(domain)) {
				urlsHam.put(url, urlsHamTmp.get(url));
			}
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
