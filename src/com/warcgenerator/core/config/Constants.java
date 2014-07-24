package com.warcgenerator.core.config;

public interface Constants {
	String outputCorpusFileExtension = "warc";
	String outputEnconding = "UTF-8";
	String outputContentType = "text/html";
	String configSchemaFilePath = ".//config//config.xsd";
	String defaultConfigXML= ".//config//config.xml";
	String dataSourcesTypesXML= ".//config//datasources.xml";
	
	public interface AppConfigConstants {
		// Default values
		String CORPUS_DIR_PATH_DEFAULT = ".\\out";
		String SPAM_DIR_NAME_DEFAULT = "_spam_";
		String HAM_DIR_NAME_DEFAULT = "_ham_";
		String DOMAINS_LABELED_FILE_NAME_DEFAULT = "domains.labelled";
		String DOMAINS_NOTFOUND_FILE_NAME_DEFAULT = "domains.notFound";
		Boolean FLASH_OUTPUT_DIR_DEFAULT = true;
		String MAX_DEPTH_OF_CRAWLING_DEFAULT = "2";
		String NUM_CRAWLERS_DEFAULT = "1";
		String WEB_CRAWLER_DIR_TMP_STORE_PATH_DEFAULT = ".";
	}
}
