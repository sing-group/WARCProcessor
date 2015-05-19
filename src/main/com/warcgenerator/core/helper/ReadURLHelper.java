package com.warcgenerator.core.helper;

import java.util.Map;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.warc.WarcDS;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

public class ReadURLHelper {
	public static int getMaxSitesSpam(AppConfig config) {
		int numSitesSpam = 0;
		if (config.getRatioIsPercentage()) {
			numSitesSpam = (int) Math.round(config.getNumSites()
					* (config.getRatioPercentageSpam() / (double) 100));
		} else {
			numSitesSpam = config.getRatioQuantitySpam();
	
		}
		return numSitesSpam;
	}
	
	public static int getMaxSitesHam(AppConfig config) {
		return config.getNumSites() - getMaxSitesSpam(config);
	}
	
	public static void addUrl(Map<String, DataBean> urls, 
			DataBean data, GenerateCorpusState auditor,
			int maxURLs) {
			
		if (data.isSpam() && auditor.getNumUrlSpamReadedFromDS() != maxURLs) {
			addToUrls(urls, data);
			auditor.incUrlSpamReadedFromDS();
		} else if (!data.isSpam() && auditor.getNumUrlHamReadedFromDS() != maxURLs) {
			addToUrls(urls, data);
			auditor.incUrlHamReadedFromDS();
		}
	}
	private static void addToUrls(Map<String, DataBean> urls, 
			DataBean data) {
		// check if this url already exists in the list
		String url = FileHelper.normalizeURL(data.getUrl());
		DataBean aux = urls.get(url);
		if (aux == null) {
			// If is a new url only add it
			urls.put(url, data);
		} else {
			// If the url is not from warc type, add it
			if (!aux.getTypeDS().equals(WarcDS.DS_TYPE)) {
				urls.put(url, data);
			}
		}
	}
}
