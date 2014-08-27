package com.warcgenerator.core.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.common.GenerateCorpusState;
import com.warcgenerator.core.common.GenerateCorpusStates;
import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.Constants;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.datasource.GenericDS;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.config.LoadDataSourceException;
import com.warcgenerator.core.exception.logic.LogicException;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.XMLConfigHelper;
import com.warcgenerator.core.plugin.webcrawler.IWebCrawler;
import com.warcgenerator.core.task.ExecutionTaskBatch;
import com.warcgenerator.core.task.Task;
import com.warcgenerator.core.task.generateCorpus.GetURLFromDSTask;
import com.warcgenerator.core.task.generateCorpus.ReadHamTask;
import com.warcgenerator.core.task.generateCorpus.ReadSpamTask;

/**
 * Business logic layer
 * 
 * @author Miguel Callon
 * 
 */
public class AppLogicImpl extends AppLogic implements IAppLogic {
	private AppConfig config;
	private OutputCorpusConfig outputCorpusConfig;
	private Map<String, DataSourceConfig> dataSourcesTypes;
	private IWebCrawler webCrawler;
	private ExecutionTaskBatch executorTasks;

	public AppLogicImpl(AppConfig config) throws LogicException {
		this.config = config;

		// ConfigHelper.getDSHandlers(config);

		dataSourcesTypes = new HashMap<String, DataSourceConfig>();
		XMLConfigHelper.getDataSources(Constants.dataSourcesTypesXML,
				dataSourcesTypes);

		System.out.println("config.getOutputConfig() es "
				+ config.getOutputConfig());

		// Create a output corpus with config
		if (config.getOutputConfig() instanceof OutputCorpusConfig) {
			outputCorpusConfig = (OutputCorpusConfig) config.getOutputConfig();
		} else {
			throw new OutCorpusCfgNotFoundException();
		}

		// Corpus Path dirs
		String dirs[] = { outputCorpusConfig.getOutputDir(),
				outputCorpusConfig.getSpamDir(), outputCorpusConfig.getHamDir() };

		// Delete directories
		if (config.getFlushOutputDir() != null && config.getFlushOutputDir()) {
			FileHelper.removeDirsIfExist(dirs);
		}

		FileHelper.createDirs(dirs);
	}

	/**
	 * Save configuration in a file
	 */
	public void saveAppConfig(String path) {
		ConfigHelper.persistConfig(path, config);
	}

	public void loadAppConfig(String path) {
		ConfigHelper.configure(path, config);
		config.init();
	}

	public void updateAppConfig(AppConfig appConfig) throws LogicException {
		try {
			appConfig.validate();
		} catch (ConfigException e) {
			throw new LogicException(e);
		}

		try {
			BeanUtils.copyProperties(config, appConfig);
			
			System.out.println("Nuevo numSites es" + config.getNumSites());
			
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
		
		// Notify observers
		setChanged();
		notifyObservers(APP_LOGIC_UPDATED_CALLBACK);
	}

	public AppConfig getAppConfig() throws LogicException {
		AppConfig appConfigCopy = new AppConfig();
		try {
			BeanUtils.copyProperties(appConfigCopy, config);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
		return appConfigCopy;
	}

	public List<DataSourceConfig> getDataSourceTypesList()
			throws LogicException {
		List<DataSourceConfig> dataSourceTypesList = new ArrayList<DataSourceConfig>();

		for (DataSourceConfig dsConfig : dataSourcesTypes.values()) {
			DataSourceConfig dsConfigCopy = new DataSourceConfig();
			try {
				BeanUtils.copyProperties(dsConfigCopy, dsConfig);
			} catch (IllegalAccessException e) {
				throw new LogicException(e);
			} catch (InvocationTargetException e) {
				throw new LogicException(e);
			}
			dataSourceTypesList.add(dsConfigCopy);
		}

		return dataSourceTypesList;
	}

	public DataSourceConfig getDataSourceType(String type) {
		DataSourceConfig dsConfigCopy = new DataSourceConfig();
		DataSourceConfig dsConfig = dataSourcesTypes.get(type);
		try {
			BeanUtils.copyProperties(dsConfigCopy, dsConfig);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
		return dsConfigCopy;
	}

	public List<DataSourceConfig> getDataSourceConfigList() {
		List<DataSourceConfig> dataSourceConfigsList = new ArrayList<DataSourceConfig>();

		for (DataSourceConfig dsConfig : config.getDataSourceConfigs().values()) {
			DataSourceConfig dsConfigCopy = new DataSourceConfig();
			try {
				BeanUtils.copyProperties(dsConfigCopy, dsConfig);
			} catch (IllegalAccessException e) {
				throw new LogicException(e);
			} catch (InvocationTargetException e) {
				throw new LogicException(e);
			}
			dataSourceConfigsList.add(dsConfigCopy);
		}

		return dataSourceConfigsList;
	}

	public DataSourceConfig getDataSourceConfig(String name) {
		DataSourceConfig dsConfigCopy = new DataSourceConfig();
		DataSourceConfig dsConfig = config.getDataSourceConfigs().get(name);
		try {
			BeanUtils.copyProperties(dsConfigCopy, dsConfig);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}
		return dsConfigCopy;
	}

	/**
	 * Add a new DataSource
	 * 
	 * @param dsConfig
	 * @throws LoadDataSourceException
	 */
	public void addDataSourceConfig(DataSourceConfig dsConfig)
			throws LoadDataSourceException {
		if (dsConfig.getId() == null) {
			dsConfig.setId(DataSourceConfig.nextId);
		}
		// ConfigHelper.getDSHandler(dsConfig, config);
		config.getDataSourceConfigs().put(dsConfig.getId(), dsConfig);
	}

	/**
	 * Remove a DataSource
	 * 
	 * @param name
	 */
	public void removeDataSourceConfig(Integer name) {
		config.getDataSourceConfigs().remove(name);
	}

	/**
	 * Stop the generation of corpus
	 */
	public void stopGenerateCorpus() {
		stopWebCrawling();
	}

	public void generateCorpus(GenerateCorpusState generateCorpusState)
			throws LogicException {
			executorTasks = new ExecutionTaskBatch();
		
			// Get dsHandlers
			ConfigHelper.getDSHandlers(config);

			// Generate wars
			IDataSource labeledDS = new GenericDS(new DataSourceConfig(
					outputCorpusConfig.getDomainsLabeledFilePath()));
			IDataSource notFoundDS = new GenericDS(new DataSourceConfig(
					outputCorpusConfig.getDomainsNotFoundFilePath()));

			// Init data structures
			Set<String> urlsSpam = new HashSet<String>();
			Set<String> urlsHam = new HashSet<String>();

			// Init Task
			Task t1 = new GetURLFromDSTask(config, 
					generateCorpusState, urlsSpam, urlsHam);
			Task t2 = new ReadSpamTask(config, outputCorpusConfig,
					generateCorpusState, labeledDS, notFoundDS, urlsSpam);
			Task t3 = new ReadHamTask(config, outputCorpusConfig,
					generateCorpusState, labeledDS, notFoundDS, urlsHam);
			
			executorTasks.addTask(t1);
			executorTasks.addTask(t2);
			executorTasks.addTask(t3);
			
			executorTasks.execution();
			
			if (executorTasks.isTerminate()) {
				generateCorpusState.setState(GenerateCorpusStates.
						PROCESS_CANCELLED);
			}
			
			generateCorpusState.setState(GenerateCorpusStates.ENDING);
			labeledDS.close();
			notFoundDS.close();
	}

	private void stopWebCrawling() {
		if (executorTasks != null)
			executorTasks.terminate();
	}
	
	public void addObserver(Observer obj) {
		deleteObserver(obj);
		super.addObserver(obj);
	}
}
