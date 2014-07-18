package com.warcgenerator.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * General configuration.
 * 
 * This class must be builded from a XML properties file.
 * 
 * @author Miguel Callon
 */
public class AppConfig {
	// Default values
	private final String CORPUS_DIR_PATH_DEFAULT = ".\\out";
	private final String SPAM_DIR_NAME_DEFAULT = "_spam_";
	private final String HAM_DIR_NAME_DEFAULT = "_ham_";
	private final String DOMAINS_LABELED_FILE_NAME_DEFAULT = "domains.labelled";
	private final String DOMAINS_NOTFOUND_FILE_NAME_DEFAULT = "domains.notFound";
	private final String MAX_DEPTH_OF_CRAWLING_DEFAULT = "2";
	private final String WEB_CRAWLER_DIR_TMP_STORE_PATH_DEFAULT = ".";

	private OutputConfig outputConfig;
	private List<DataSourceConfig> dataSourceConfigs;
	private WebCrawlerConfig webCrawlerCfgTemplate;
	private String corpusDirPath;
	private String spamDirName;
	private String hamDirName;
	private String domainsLabeledFileName;
	private String domainsNotFoundFileName;
	private String maxDepthOfCrawling;
	private String webCrawlerTmpStorePath;

	public AppConfig() {
		dataSourceConfigs = new ArrayList<DataSourceConfig>();
	}

	public void init() {
		corpusDirPath = corpusDirPath.equals("")?CORPUS_DIR_PATH_DEFAULT:corpusDirPath;
		spamDirName = spamDirName.equals("")?SPAM_DIR_NAME_DEFAULT:spamDirName;
		hamDirName = hamDirName.equals("")?HAM_DIR_NAME_DEFAULT:hamDirName;
		domainsLabeledFileName = domainsLabeledFileName.equals("")?
				DOMAINS_LABELED_FILE_NAME_DEFAULT:domainsLabeledFileName;
		domainsNotFoundFileName = domainsNotFoundFileName.equals("")?
				DOMAINS_NOTFOUND_FILE_NAME_DEFAULT:domainsNotFoundFileName;
		maxDepthOfCrawling = maxDepthOfCrawling.equals("")?
				MAX_DEPTH_OF_CRAWLING_DEFAULT:maxDepthOfCrawling;
		webCrawlerTmpStorePath = webCrawlerTmpStorePath.equals("")?
				WEB_CRAWLER_DIR_TMP_STORE_PATH_DEFAULT:webCrawlerTmpStorePath;		
		
		// Configure filepaths
		String pathCorpus = corpusDirPath;
		String pathSpam = pathCorpus + File.separator + spamDirName;
		String pathHam = pathCorpus + File.separator + hamDirName;
		String pathDomainsLabelled = pathCorpus + File.separator
				+ domainsLabeledFileName;
		String pathDomainsNotFound = pathCorpus + File.separator
				+ domainsNotFoundFileName;
		outputConfig = new OutputCorpusConfig(pathCorpus, pathSpam, pathHam,
				pathDomainsLabelled, pathDomainsNotFound);

		// Web crawler template config
		webCrawlerCfgTemplate = new WebCrawlerConfig();
		// Set number of crawlers
		// At the moment it is a fixed value
		webCrawlerCfgTemplate.setNumberOfCrawlers(2);
		// Set max depth of crawling
		Integer maxDepthOfCrawlingToInt = null;
		try {
			maxDepthOfCrawlingToInt = Integer.parseInt(maxDepthOfCrawling);
		} catch (NumberFormatException ex) {
			maxDepthOfCrawlingToInt = Integer
					.parseInt(MAX_DEPTH_OF_CRAWLING_DEFAULT);
		}
		
		webCrawlerCfgTemplate.setMaxDepthOfCrawling(maxDepthOfCrawlingToInt);
		webCrawlerCfgTemplate.setStorePath(webCrawlerTmpStorePath);
	}

	public List<DataSourceConfig> getDataSourceConfigs() {
		return dataSourceConfigs;
	}

	public void setDataSourceConfigs(List<DataSourceConfig> dataSourceConfigs) {
		this.dataSourceConfigs = dataSourceConfigs;
	}

	public OutputConfig getOutputConfig() {
		return outputConfig;
	}

	public void setOutputConfig(OutputConfig outputConfig) {
		this.outputConfig = outputConfig;
	}

	public WebCrawlerConfig getWebCrawlerCfgTemplate() {
		return webCrawlerCfgTemplate;
	}

	public void setWebCrawlerCfgTemplate(WebCrawlerConfig webCrawlerCfgTemplate) {
		this.webCrawlerCfgTemplate = webCrawlerCfgTemplate;
	}
	
	public String getSpamDirName() {
		return spamDirName;
	}

	public void setSpamDirName(String spamDirName) {
		this.spamDirName = spamDirName;
	}

	public String getHamDirName() {
		return hamDirName;
	}

	public void setHamDirName(String hamDirName) {
		this.hamDirName = hamDirName;
	}

	public String getDomainsLabeledFileName() {
		return domainsLabeledFileName;
	}

	public void setDomainsLabeledFileName(String domainsLabeledFileName) {
		this.domainsLabeledFileName = domainsLabeledFileName;
	}

	public String getDomainsNotFoundFileName() {
		return domainsNotFoundFileName;
	}

	public void setDomainsNotFoundFileName(String domainsNotFoundFileName) {
		this.domainsNotFoundFileName = domainsNotFoundFileName;
	}

	public String getMaxDepthOfCrawling() {
		return maxDepthOfCrawling;
	}

	public void setMaxDepthOfCrawling(String maxDepthOfCrawling) {
		this.maxDepthOfCrawling = maxDepthOfCrawling;
	}

	public String getWebCrawlerTmpStorePath() {
		return webCrawlerTmpStorePath;
	}

	public void setWebCrawlerTmpStorePath(String webCrawlerTmpStorePath) {
		this.webCrawlerTmpStorePath = webCrawlerTmpStorePath;
	}
	
	public String getCorpusDirPath() {
		return corpusDirPath;
	}

	public void setCorpusDirPath(String corpusDirPath) {
		this.corpusDirPath = corpusDirPath;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("spamDirName:  ").append(spamDirName).append("\n");
		sb.append("hamDirName:  ").append(hamDirName).append("\n");
		sb.append("domainsLabeledFileName:  ").append(domainsLabeledFileName)
				.append("\n");
		sb.append("domainsNotFoundFileName:  ").append(domainsNotFoundFileName)
				.append("\n");
		sb.append("maxDepthOfCrawling:  ").append(maxDepthOfCrawling)
				.append("\n");
		sb.append("webCrawlerDirTmpStorePath:  ")
				.append(webCrawlerTmpStorePath).append("\n");
		return sb.toString();
	}
}
