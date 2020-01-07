package com.warcgenerator.core.task.generateCorpus;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.Country;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.warc.WarcDS;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.LangFilterHelper;
import com.warcgenerator.core.helper.OutputHelper;
import com.warcgenerator.core.task.ITask;
import com.warcgenerator.core.task.Task;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusStates;

public class CheckActiveSitesConfigTask extends Task implements ITask {
	private AppConfig config;
	private Map<String, DataBean> urls;
	private Set<String> urlsInactives;
	private Map<String, DataSource> outputDS;
	private OutputCorpusConfig outputCorpusConfig;
	private IDataSource domainsLabeledDS;
	private GenerateCorpusState generateCorpusState;

	private static Logger logger = LogManager.getLogger(CheckActiveSitesConfigTask.class);

	public CheckActiveSitesConfigTask(AppConfig config, Map<String, DataBean> urls,
			Set<String> urlsInactives, Map<String, DataSource> outputDS,
			OutputCorpusConfig outputCorpusConfig,
			IDataSource domainsLabeledDS, GenerateCorpusState generateCorpusState) {
		this.config = config;
		this.urls = urls;
		this.urlsInactives = urlsInactives;
		this.outputDS = outputDS;
		this.outputCorpusConfig = outputCorpusConfig;
		this.domainsLabeledDS = domainsLabeledDS;
		this.generateCorpusState = generateCorpusState;
	}

	public void execute() {
		logger.info("Task start");

		if (!config.getOnlyActiveSites()) {
			//for (String url : urlsInactives) {
			for (String url:urls.keySet()) {
				if (urlsInactives.contains(url)) {
					DataBean data = urls.get(url);
					if (data.getData() != null) {
						try {
							if (LangFilterHelper.checkLanguageAllowed(data.getData(),
									data.getDsConfig().getCountryList())) {
	
								DataSource warcDS = outputDS.get(FileHelper
										.getDomainNameFromURL(url));
	
								if (warcDS == null) {
									StringBuilder warcFileName = new StringBuilder();
	
									StringBuilder outputWarcPath = new StringBuilder();
									if (data.isSpam()) {
										outputWarcPath.append(outputCorpusConfig.getSpamDir());
									} else {
										outputWarcPath.append(outputCorpusConfig.getHamDir());
									}
									outputWarcPath.append(File.separator);
									
	
									warcFileName.append(outputWarcPath.toString()).append(
											FileHelper.getOutputFileName(url));
	
									warcDS = new WarcDS(new OutputWarcConfig(true,
											warcFileName.toString()));
	
									outputDS.put(
											FileHelper.getDomainNameFromURL(data.getUrl()),
											warcDS);
								}
								warcDS.write(data);
								OutputHelper.writeLabeled(domainsLabeledDS, data.getUrl(),
										data.isSpam());
								generateCorpusState.incDomainsCorrectlyLabeled(data.isSpam());
							} else {
									// TODO Write in some output file instead of the log
									logger.info("URL Filtered. Available:");
									StringBuffer sb = new StringBuffer();
									for(Country country:data.getDsConfig().getCountryList()) {
										sb.append(country.getName()).append(" ");
									}
									logger.info(sb.toString());
								
							}
						} catch (Exception e) {
							logger.info("Could not check language text");
							e.printStackTrace();
						}
					}
				}
			}
		}

		logger.info("Task completed");
	}

	public void rollback() {
		logger.info("Rollback");
	}
}