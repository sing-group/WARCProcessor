package com.warcgenerator.core.task.generateCorpus;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.OutputHelper;
import com.warcgenerator.core.task.ITask;
import com.warcgenerator.core.task.Task;

public class CheckActiveSitesConfigTask extends Task implements ITask {
	private AppConfig config;
	private Map<String, DataBean> urls;
	private Set<String> urlsInactives;
	private Map<String, DataSource> outputDS;
	private OutputCorpusConfig outputCorpusConfig;
	private IDataSource domainsLabeledDS;

	private static Logger logger = Logger
			.getLogger(CheckActiveSitesConfigTask.class);

	public CheckActiveSitesConfigTask(AppConfig config, Map<String, DataBean> urls,
			Set<String> urlsInactives, Map<String, DataSource> outputDS,
			OutputCorpusConfig outputCorpusConfig,
			IDataSource domainsLabeledDS) {
		this.config = config;
		this.urls = urls;
		this.urlsInactives = urlsInactives;
		this.outputDS = outputDS;
		this.outputCorpusConfig = outputCorpusConfig;
		this.domainsLabeledDS = domainsLabeledDS;
	}

	public void execute() {
		logger.info("Task start: CheckActiveSitesConfigTask");

		if (!config.getOnlyActiveSites()) {
			for (String url : urlsInactives) {
				DataBean data = urls.get(url);

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
			}
		}

		logger.info("Task completed: CheckActiveSitesConfigTask");
	}

	public void rollback() {
		System.out.println("ReadSpamWithContentTask");
	}
}