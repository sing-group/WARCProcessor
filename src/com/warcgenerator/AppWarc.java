package com.warcgenerator;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.exception.WarcException;
import com.warcgenerator.core.helper.XMLConfigHelper;
import com.warcgenerator.core.logic.AppLogicImpl;
import com.warcgenerator.core.logic.IAppLogic;

/**
 * AppWarc is an application used to generate Webspam Corpus.
 * 
 * @author Miguel Callon
 */
class AppWarc {
	private static AppWarc singleton = null; 
	private AppConfig config = null;
	private IAppLogic logic = null;
	
	private static Logger logger = Logger.getLogger
            (AppWarc.class);
	
	private AppWarc() {}

	/**
	 * Singleton
	 * @return
	 */
	public static AppWarc getInstance() {
		if (singleton == null) {
			singleton = new AppWarc();
		}
		return singleton;
	}

	/**
	 * Load configurations
	 * @param pathConfig path to Xml properties config
	 */
	private void init(String pathConfig) throws WarcException {
		// Configure Log4j.xml
		DOMConfigurator.configure("config" + File.separator + "log4j.xml");
		
		//Properties properties = ConfigHelper.loadParams(pathConfig);
		logger.info("Loading configuration...");
		config = new AppConfig();
		// Using XML config instead of properties
		XMLConfigHelper.configure(pathConfig, config);
		config.init();
		System.out.println("config es: " + config);
		
		// Get data sources
		/*config.setDataSourceConfigs(
				ConfigHelper.getDataSources(config));*/
		logger.info("Configuration loaded successfully");

		logic = new AppLogicImpl(config);
	}

	/**
	 * Execute logic
	 * @param pathConfig path XML configuration file
	 */
	public void execute(String pathConfig) throws WarcException {
		init(pathConfig);
		// Start
		logger.info("Generating corpus...");
		logic.generateCorpus();
		logger.info("Corpus generated successfully ...");
	}
}
