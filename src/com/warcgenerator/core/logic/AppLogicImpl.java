package com.warcgenerator.core.logic;

import java.util.List;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.exception.logic.LogicException;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.helper.FileHelper;

/**
 * Business logic layer
 * 
 * @author Miguel Callon
 * 
 */
public class AppLogicImpl extends AppLogic implements IAppLogic {
	private List<IDSHandler> dsHandlers;
	private AppConfig config;
	private OutputCorpusConfig outputCorpusConfig;

	public AppLogicImpl(AppConfig config) throws LogicException {
		this.config = config;
		dsHandlers = ConfigHelper.getDSHandlers(config);
		
		// Create a output corpus with config
		if (config.getOutputConfig() instanceof OutputCorpusConfig) {
			outputCorpusConfig = (OutputCorpusConfig) config.getOutputConfig();
		} else {
			throw new OutCorpusCfgNotFoundException();
		}
		
		// Corpus Path dirs
		String dirs[] = { outputCorpusConfig.getOutputDir(),
				outputCorpusConfig.getSpamDir(), outputCorpusConfig.getHamDir() };
		System.out.println("Directorios:");
		for(String dir:dirs) {
			System.out.println(dir);
		}
		
		FileHelper.createDirs(dirs);
	}

	public void generateCorpus() throws LogicException {		
		// Generate wars
		// Read sources
		for (IDSHandler dsHandler : dsHandlers) {
			dsHandler.toHandle();
		}
	}
}
