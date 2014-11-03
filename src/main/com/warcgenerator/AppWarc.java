package com.warcgenerator;

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.exception.WarcException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.logic.AppLogicImpl;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

/**
 * AppWarc is an application used to generate Webspam Corpus.
 * 
 * @author Miguel Callon
 */
public class AppWarc {
	private static AppWarc singleton = null;
	private AppConfig config = null;
	private IAppLogic logic = null;

	private static Logger logger = Logger.getLogger(AppWarc.class);

	private AppWarc() {
	}

	/**
	 * Singleton
	 * 
	 * @return
	 */
	public static AppWarc getInstance() {
		if (singleton == null) {
			singleton = new AppWarc();
		}
		return singleton;
	}

	public void init() {
		// Configure Log4j.xml
		DOMConfigurator.configure(this.getClass()
				.getResource("/config/log4j.xml"));

		// Properties properties = ConfigHelper.loadParams(pathConfig);
		logger.info("Loading default configuration...");
		config = new AppConfig();

		// Using XML config instead of properties
		/*
		 * ConfigHelper.configure(Constants.defaultConfigXML, config);
		 */
		config.init();

		logger.info("-- AppConfig --\n" + config);
		logger.info("Configuration loaded successfully");
		logic = new AppLogicImpl(config);
	}

	/**
	 * Load configurations
	 * 
	 * @param pathConfig
	 *            path to Xml properties config
	 */
	public void init(String pathConfig) throws WarcException {
		// Configure Log4j.xml
		DOMConfigurator.configure("config" + File.separator + "log4j.xml");

		// Properties properties = ConfigHelper.loadParams(pathConfig);
		logger.info("Loading configuration...");
		config = new AppConfig();
		// Using XML config instead of properties
		ConfigHelper.configure(pathConfig, config);
		config.init();

		logger.info("-- AppConfig --\n" + config);
		logger.info("Configuration loaded successfully");
		logic = new AppLogicImpl(config);
	}

	/**
	 * Execute logic
	 * 
	 * @param pathConfig
	 *            path XML configuration file
	 */
	public void execute() throws WarcException {
		// Start
		logger.info("Generating corpus...");
		logic.generateCorpus(new GenerateCorpusState());
		logger.info("Corpus generated successfully ...");
	}

	/**
	 * 
	 * @return
	 */
	public IAppLogic getAppLogic() {
		return logic;
	}
}
