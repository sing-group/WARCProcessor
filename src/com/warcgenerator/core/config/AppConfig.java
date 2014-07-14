package com.warcgenerator.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.warcgenerator.core.helper.PrefixedProperty;

/**
 * General configuration.
 * 
 * This class must be builded from a XML properties file.
 * 
 * @author Miguel Callon
 */
public class AppConfig {
	// Properties keys
	private final String CORPUS_DIR_PATH = "CORPUS_DIR_PATH";
	private final String SPAM_DIR_NAME = "SPAM_DIR_NAME";
	private final String HAM_DIR_NAME = "HAM_DIR_NAME";
	private final String DOMAINS_LABELED_FILE_NAME = "DOMAINS_LABELED_FILE_NAME";
	private final String DOMAINS_NOTFOUND_FILE_NAME = "DOMAINS_NOTFOUND_FILE_NAME";
	private final String WHITELISTS_DIR_PATH = "WHITELISTS_DIR_PATH";
	private final String BLACKLISTS_DIR_PATH = "BLACKLISTS_DIR_PATH";
	private final String SRC_CORPUS_DIR_PATH = "SRC_CORPUS_DIR_PATH";
	private final String SRC_ARFF_DIR_PATH = "SRC_ARFF_DIR_PATH";
	/* private final String SRC_CSV_DIR_PATH = "SRC_CSV_DIR_PATH"; */
	private final String MAX_DEPTH_OF_CRAWLING = "MAX_DEPTH_OF_CRAWLING";
	private final String WEB_CRAWLER_DIR_TMP_STORE_PATH = "WEB_CRAWLER_DIR_TMP_STORE_PATH";

	// Default values
	private final String CORPUS_DIR_PATH_DEFAULT = ".\\out";
	private final String SPAM_DIR_NAME_DEFAULT = "_spam_";
	private final String HAM_DIR_NAME_DEFAULT = "_ham_";
	private final String DOMAINS_LABELED_FILE_NAME_DEFAULT = "domains.labelled";
	private final String DOMAINS_NOTFOUND_FILE_NAME_DEFAULT = "domains.labelled";
	private final String WHITELISTS_DIR_PATH_DEFAULT = ".\\in\\plain\\whitelists";
	private final String BLACKLISTS_DIR_PATH_DEFAULT = ".\\in\\plain\\blacklists";
	private final String SRC_CORPUS_DIR_PATH_DEFAULT = ".\\in\\corpus";
	private final String SRC_ARFF_DIR_PATH_DEFAULT = ".\\in\\arff";
	private final String SRC_CSV_DIR_PATH_DEFAULT = ".\\in\\csv";
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

	private PrefixedProperty properties;

	public AppConfig() {
		dataSourceConfigs = new ArrayList<DataSourceConfig>();		
	}

	public void init() {
		corpusDirPath = corpusDirPath.equals("")?corpusDirPath:CORPUS_DIR_PATH_DEFAULT;
		spamDirName = spamDirName.equals("")?spamDirName:SPAM_DIR_NAME_DEFAULT;
		hamDirName = hamDirName.equals("")?hamDirName:HAM_DIR_NAME_DEFAULT;
		domainsLabeledFileName = domainsLabeledFileName.equals("")?
				domainsLabeledFileName:DOMAINS_LABELED_FILE_NAME_DEFAULT;
		domainsNotFoundFileName = domainsNotFoundFileName.equals("")?
				domainsNotFoundFileName:DOMAINS_NOTFOUND_FILE_NAME_DEFAULT;
		maxDepthOfCrawling = maxDepthOfCrawling.equals("")?
				maxDepthOfCrawling:MAX_DEPTH_OF_CRAWLING_DEFAULT;
		webCrawlerTmpStorePath = webCrawlerTmpStorePath.equals("")?
				webCrawlerTmpStorePath:WEB_CRAWLER_DIR_TMP_STORE_PATH_DEFAULT;		
		
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
		webCrawlerCfgTemplate.setNumberOfCrawlers(1);
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

	/*public AppConfig(PrefixedProperty properties) {
		this.properties = properties;

		String corpusFilePath = properties.getProperty(CORPUS_DIR_PATH,
				CORPUS_DIR_PATH_DEFAULT);
		String spamDirName = properties.getProperty(SPAM_DIR_NAME,
				SPAM_DIR_NAME_DEFAULT);
		String hamDirName = properties.getProperty(HAM_DIR_NAME,
				HAM_DIR_NAME_DEFAULT);
		String domainsLabeledFileName = properties.getProperty(
				DOMAINS_LABELED_FILE_NAME, DOMAINS_LABELED_FILE_NAME_DEFAULT);
		String domainsNotFoundFileName = properties.getProperty(
				DOMAINS_NOTFOUND_FILE_NAME, DOMAINS_NOTFOUND_FILE_NAME_DEFAULT);
		String maxDepthOfCrawling = properties.getProperty(
				MAX_DEPTH_OF_CRAWLING, MAX_DEPTH_OF_CRAWLING_DEFAULT);
		String webCrawlerTmpStorePath = properties.getProperty(
				WEB_CRAWLER_DIR_TMP_STORE_PATH,
				WEB_CRAWLER_DIR_TMP_STORE_PATH_DEFAULT);

		// Data sources paths
		// - type white list
		// - type black list
		// - type corpus
		this.setWhiteListsDirPath(properties.getProperty(WHITELISTS_DIR_PATH,
				WHITELISTS_DIR_PATH_DEFAULT));
		this.blackListsDirPath = properties.getProperty(BLACKLISTS_DIR_PATH,
				BLACKLISTS_DIR_PATH_DEFAULT);
		this.srcArffDirPath = properties.getProperty(SRC_ARFF_DIR_PATH,
				SRC_ARFF_DIR_PATH_DEFAULT);
		this.srcCorpusDirPath = properties.getProperty(SRC_CORPUS_DIR_PATH,
				SRC_CORPUS_DIR_PATH_DEFAULT);
		
		// Configure filepaths
		String pathCorpus = corpusFilePath;
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
		webCrawlerCfgTemplate.setNumberOfCrawlers(1);
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
	}*/

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
