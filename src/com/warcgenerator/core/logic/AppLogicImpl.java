package com.warcgenerator.core.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.Constants;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.GenericDS;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.config.LoadDataSourceException;
import com.warcgenerator.core.exception.logic.ConfigFilePathIsNullException;
import com.warcgenerator.core.exception.logic.LogicException;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.XMLConfigHelper;
import com.warcgenerator.core.task.ExecutionTaskBatch;
import com.warcgenerator.core.task.Task;
import com.warcgenerator.core.task.generateCorpus.CheckActiveSitesConfigTask;
import com.warcgenerator.core.task.generateCorpus.GetURLFromDSTask;
import com.warcgenerator.core.task.generateCorpus.ReadURLsTask;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusStates;

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
	private ExecutionTaskBatch executorTasks;

	public AppLogicImpl(AppConfig config) throws LogicException {
		this.config = config;

		dataSourcesTypes = new HashMap<String, DataSourceConfig>();
		XMLConfigHelper.getDataSources(Constants.dataSourcesTypesXML,
				dataSourcesTypes);

		// Create a output corpus with config
		if (config.getOutputConfig() instanceof OutputCorpusConfig) {
			outputCorpusConfig = (OutputCorpusConfig) config.getOutputConfig();
		} else {
			throw new OutCorpusCfgNotFoundException();
		}

		
		/*
		// Corpus Path dirs
		String dirs[] = { outputCorpusConfig.getOutputDir(),
				outputCorpusConfig.getSpamDir(), outputCorpusConfig.getHamDir() };

		// Delete directories
		if (config.getFlushOutputDir() != null && config.getFlushOutputDir()) {
			FileHelper.removeDirsIfExist(dirs);
		}

		FileHelper.createDirs(dirs);
		*/
	}

	/**
	 * Save configuration in a file
	 */
	public void saveAppConfig() {
		String configFilePath = ConfigHelper.getConfigFilePath();
		if (configFilePath != null) {
			ConfigHelper.persistConfig(configFilePath, config);
		
			// Notify observers
			setChanged();
			notifyObservers(new LogicCallback(APP_CONFIG_SAVED_CALLBACK));
		} else {
			throw new ConfigFilePathIsNullException();
		}
	}
	
	/**
	 * Save configuration in a file
	 */
	public void saveAsAppConfig(String path) {
		ConfigHelper.persistConfig(path, config);
		
		// Notify observers
		setChanged();
		notifyObservers(new LogicCallback(APP_CONFIG_SAVED_AS_CALLBACK));
	}

	/**
	 * Return Filepath of the current Config file.
	 */
	public String getConfigFilePath() {
		return ConfigHelper.getConfigFilePath();
	}
	
	public void loadAppConfig(String path) {
		ConfigHelper.configure(path, config);
		config.init();
		
		// Notify observers
		setChanged();
		notifyObservers(new LogicCallback(APP_CONFIG_LOADED_CALLBACK));
	}
	
	public void loadNewAppConfig() {
		config = new AppConfig();
		config.init();
		
		// The filePath of this configuracion is not set yet.
		ConfigHelper.setConfigFilePath(null);
	}

	public void updateAppConfig(AppConfig appConfig) throws LogicException {
		appConfig.validate();
		try {
			BeanUtils.copyProperties(config, appConfig);
		} catch (IllegalAccessException e) {
			throw new LogicException(e);
		} catch (InvocationTargetException e) {
			throw new LogicException(e);
		}

		// Notify observers
		setChanged();
		notifyObservers(new LogicCallback(APP_CONFIG_UPDATED_CALLBACK));
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

		Collections.sort(dataSourceTypesList);
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

		Collections.sort(dataSourceConfigsList);
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

	public DataSourceConfig getDataSourceById(Integer id) {
		// ConfigHelper.getDSHandler(dsConfig, config);
		return config.getDataSourceConfigs().get(id);
	}

	/**
	 * Add a new DataSource
	 * 
	 * @param dsConfig
	 * @throws LoadDataSourceException
	 */
	public void addDataSourceConfig(DataSourceConfig dsConfig)
			throws LoadDataSourceException {
		String callback_message = DATASOURCE_UPDATED_CALLBACK;
		if (dsConfig.getId() == null) {
			dsConfig.setId(DataSourceConfig.getNextId());
			callback_message = DATASOURCE_CREATED_CALLBACK;
		}
		// ConfigHelper.getDSHandler(dsConfig, config);
		config.getDataSourceConfigs().put(dsConfig.getId(), dsConfig);
		// Notify observers
		setChanged();
		notifyObservers(new LogicCallback(callback_message,
				new Object[] { dsConfig.getId() }));
	}

	/**
	 * Remove a DataSource
	 * 
	 * @param name
	 */
	public void removeDataSourceConfig(Integer id) {
		config.getDataSourceConfigs().remove(id);
		// Notify observers
		setChanged();
		notifyObservers(new LogicCallback(DATASOURCE_REMOVED_CALLBACK,
				new Object[] { id }));
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

		// Corpus Path dirs
		String dirs[] = { outputCorpusConfig.getOutputDir(),
				outputCorpusConfig.getSpamDir(), outputCorpusConfig.getHamDir() };
		// Delete directories
		if (config.getFlushOutputDir() != null && config.getFlushOutputDir()) {
			FileHelper.removeDirsIfExist(dirs);
		}
		FileHelper.createDirs(dirs);
		
		// Get dsHandlers
		ConfigHelper.getDSHandlers(config, dataSourcesTypes);
		
		// Generate wars
		IDataSource labeledDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsLabeledFilePath()));
		IDataSource notFoundDS = new GenericDS(new DataSourceConfig(
				outputCorpusConfig.getDomainsNotFoundFilePath()));

		// Init data structures
		Map<String, DataBean> urlsSpam = new HashMap<String, DataBean>();
		Map<String, DataBean> urlsHam = new HashMap<String, DataBean>();
		Map<String, DataSource> outputDS = new HashMap<String, DataSource>();
		Set<String> urlsActive = new HashSet<String>();
		Set<String> urlsNotActive = new HashSet<String>();
		
		/*
		 * Si solo activas (descarta estas urls)
		 * 	-> Si descargar de nuevo
		 * 		Coger de internet
		 *  -> No descargar de nuevo
		 * 		Coger del fichero
		 * Si no solo activas
		 *  -> Si descargar de nuevo
		 * 		Coger de internet
		 *  -> No descargar de nuevo
		 * 		Coger del fichero		
		 */
		
		// Init Task
		Task t1 = new GetURLFromDSTask(config, generateCorpusState, urlsSpam,
				urlsHam);
		
		// ////////// READING SPAM
		Task t2 = new ReadURLsTask(config, outputCorpusConfig,
				generateCorpusState, outputDS,
				labeledDS, notFoundDS, urlsSpam, true, urlsActive,
				urlsNotActive);

		// ////////// READING HAM
		Task t3 = new ReadURLsTask(config, outputCorpusConfig,
				generateCorpusState, outputDS,
				labeledDS, notFoundDS, urlsHam, false, urlsActive,
				urlsNotActive);

		// Read url that contains html
		Task t4 = new CheckActiveSitesConfigTask(config,
				urlsSpam, urlsNotActive,
				outputDS, outputCorpusConfig, labeledDS);
		
		Task t5 = new CheckActiveSitesConfigTask(config,
				urlsHam, urlsNotActive,
				outputDS, outputCorpusConfig, labeledDS);

		executorTasks.addTask(t1);
		executorTasks.addTask(t2);
		executorTasks.addTask(t3);
		executorTasks.addTask(t4);
		executorTasks.addTask(t5);
		
		executorTasks.execution();

		if (executorTasks.isTerminate()) {
			generateCorpusState
					.setState(GenerateCorpusStates.PROCESS_CANCELLED);
		}

		// Close all output datasources
		for (DataSource ds : outputDS.values()) {
			ds.close();
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
