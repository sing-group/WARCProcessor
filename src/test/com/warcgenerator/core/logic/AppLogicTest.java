package com.warcgenerator.core.logic;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.warcgenerator.AbstractTestCase;
import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.config.PathNotFoundAppConfigException;
import com.warcgenerator.core.exception.logic.AddDataSourceException;
import com.warcgenerator.core.exception.logic.ConfigFilePathIsNullException;
import com.warcgenerator.core.exception.logic.DataSourceNotFoundException;
import com.warcgenerator.core.exception.logic.OutCorpusCfgNotFoundException;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusStates;

//@Ignore("This test will prove bug #123 is fixed, once someone fixes it")
public class AppLogicTest extends AbstractTestCase {
	private IAppLogic logic;

	// Invalid config with numSites parameters removed
	private final String WRONG_CONFIG_FILE1 = "src/test/resources/config/wrong_config1.wpg";
	
	private final String CONFIG_FILE1 = "src/test/resources/config/config1.wpg";
	private final String CONFIG_FILE2 = "src/test/resources/config/config2.wpg";
	private final String CONFIG_FILE3 = "src/test/resources/config/config3.wpg";
	private final String CONFIG_FILE4 = "src/test/resources/config/config4.wpg";
	
	private final String DS_TEST_NAME = "Test DS";
	private final String DS_TEST_TYPE = "FileDS";
	private final String DS_TEST_CLASS_NAME = "com.warcgenerator.datasources.file.FileDS";
	private final String DS_TEST_HANDLER_NAME = "com.warcgenerator.datasources.file.handler.FileDSHandler";
	private final boolean DS_IS_ENABLED = true;
	private final String DS_FILE_PATH = "src/test/resources/in/file";

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8089);

	@Before
	public void setUp() throws Exception {
		// Initialize context configFilePath
		ConfigHelper.setConfigFilePath(null);
		
	}
	
	/**
	 * Test if an exception is raised when the initial configuration
	 * has not been initialized
	 */
	@Test(expected = OutCorpusCfgNotFoundException.class)
	public void testCheckCorpusCfgNotFound() {
		//exception.expectCause(is(OutCorpusCfgNotFoundException.class));
		
		// Check if there is an empty configuration in memory
		AppConfig config = new AppConfig();

		logic = new AppLogicImpl(config);
		logic.loadNewAppConfig();
	}

	/**
	 * Test if it is possible to create an empty configuration
	 */
	@Test
	public void testLoadNewAppConfig() {
		AppConfig config = new AppConfig();
		config.init();

		logic = new AppLogicImpl(config);
		logic.loadNewAppConfig();

		assertNull("Configuration file path not empty",
				logic.getConfigFilePath());
	}

	/**
	 *  Check if an exception is raised when the configuration file path
	 *  has not been set
	 */
	@Test(expected = ConfigFilePathIsNullException.class)
	public void testSaveIfConfigFilePathIsNull() {
		ConfigHelper.setConfigFilePath(null);
		AppConfig config = new AppConfig();
		config.init();

		logic = new AppLogicImpl(config);
		logic.saveAppConfig();
	}
	
	/**
	 *  Check if a the configuration is saved successfully when its
	 *  configuration file path has been set from a external 
	 *  configuration file
	 */
	@Test
	public void testSaveAppConfig() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();

		logic = new AppLogicImpl(config);
		logic.saveAppConfig();
	}
	
	@Test(expected = PathNotFoundAppConfigException.class)
	public void testSaveAppConfigWrongPath() throws IOException 
		{
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		String path = "target/test/resources/tmp/config";
		if ((new File(path)).exists()) {
			FileUtils.deleteDirectory(new File(path));
		}
		assertEquals(false, (new File(path)).exists());
		String filePath = path + "/config_tmp.wpg";
		
		ConfigHelper.setConfigFilePath(filePath);
		
		logic = new AppLogicImpl(config);
		// Using a wrong path
		logic.saveAppConfig();
	}
	
	/**
	 * Test if it is possible to save the configuration in 
	 * a new file
	 * @throws IOException If it is not possible to save the file
	 */
	@Test
	public void testSaveAsAppConfig() throws IOException {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		String path = "target/test/resources/tmp/config";
		if ((new File(path)).exists()) {
			FileUtils.deleteDirectory(new File(path));
		}
		FileUtils.deleteDirectory(new File(path));
		assertEquals(false, (new File(path)).exists());
		Files.createDirectories(FileSystems.getDefault().getPath(path));
		assertEquals(true, (new File(path)).exists());
		
		logic = new AppLogicImpl(config);
		logic.saveAsAppConfig(path + "/config_tmp.wpg");
		
		logic.loadAppConfig(path + "/config_tmp.wpg");
		assertEquals(logic.getDataSourceConfigList().size(), 9);
	}
	
	/**
	 * Test if a exception is raised when it is tried to save
	 * the configuration in a wrong path
	 */
	@Test(expected = PathNotFoundAppConfigException.class)
	public void testSaveAsAppConfigWrongPath() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		// Using a wrong path
		logic.saveAsAppConfig("src/test/resources/tmp/config/tmp/config_tmp.wpg");
	}
	
	/**
	 * Test if it an exception is raised when the config file to
	 * load has not been found
	 */
	@Test(expected = PathNotFoundAppConfigException.class)
	public void testLoadAppConfigWrongPath() throws IOException {
		AppConfig config = new AppConfig();
		config.init();
		
		String path = "target/test/resources/tmp/config";
		if ((new File(path)).exists()) {
			FileUtils.deleteDirectory(new File(path));
		}
		assertEquals(false, (new File(path)).exists());
		String filePath = path + "/config_tmp.wpg";
		
		
		logic = new AppLogicImpl(config);
		logic.loadAppConfig(filePath);
	}
	
	/**
	 * Test if it an exception is raised when the config file to
	 * load is not valid
	 */
	@Test(expected = ConfigException.class)
	public void testLoadAppConfigInvalidConfig() throws IOException {
		AppConfig config = new AppConfig();
		config.init();
		
		logic = new AppLogicImpl(config);
		logic.loadAppConfig(WRONG_CONFIG_FILE1);
	}
	
	/**
	 * Test if it obtains successfully the list of DataSources configured
	 * in the system.
	 */
	@Test
	public void testLoadAppConfig() {
		AppConfig config = new AppConfig();
		config.init();
		
		logic = new AppLogicImpl(config);
		logic.loadAppConfig(CONFIG_FILE1);
		
		assertEquals(logic.getDataSourceConfigList().size(), 9);
	}
	
	/**
	 * Test if it can update an already existing configuration
	 * file
	 */
	@Test
	public void testUpdateAppConfig() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE2, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		
		assertEquals(logic.getDataSourceConfigList().size(), 0);
		
		AppConfig newConfig = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, newConfig);
		logic.updateAppConfig(newConfig);
		
		assertEquals(logic.getDataSourceConfigList().size(), 9);
	}
	
	@Test
	public void testGetAppConfig() throws
		IllegalAccessException, InvocationTargetException,
		NoSuchMethodException {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		logic = new AppLogicImpl(config);		
		assertEquals(logic.getDataSourceConfigList().size(), 9);
		
		AppConfig currentAppConfig =  logic.getAppConfig();
		
		for(String property:BeanUtils.describe(config).keySet()) {
			BeanComparator<AppConfig> comparator =
					new BeanComparator<>(property);
			try {
				assertEquals(comparator.compare(
					config, currentAppConfig),0);
			} catch (ClassCastException ex) {
				System.out.println("Property: " + property +
						". Can not be checked");
			}
		}
	}
	
	/**
	 * Check if the config file path has been retrieved successfully
	 */
	@Test
	public void testGetConfigFilePath() {
		AppConfig config = new AppConfig();
		config.init();
		
		logic = new AppLogicImpl(config);
		logic.loadAppConfig(CONFIG_FILE1);
		
		assertEquals(CONFIG_FILE1, logic.getConfigFilePath());
	}
	
	/**
	 * Test if it can be retrieved an already existing
	 * DataSource by its Id
	 */
	@Test(expected = DataSourceNotFoundException.class)
	public void testDataSourceByIdDSNotFound() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE3, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		logic.getDataSourceById(10);
	}
	
	/**
	 * Test if it can be retrieved an already existing
	 * DataSource by its Id
	 */
	@Test
	public void testDataSourceById() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE3, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		DataSourceConfig dsConfig = logic.getDataSourceById(1);
		
		assertEquals(dsConfig.getName(), DS_TEST_NAME);
		//assertEquals(dsConfig.getDsClassName(), DS_TEST_CLASS_NAME);
		assertEquals(dsConfig.getType(), DS_TEST_TYPE);
		assertEquals(dsConfig.getEnabled(), DS_IS_ENABLED);
		assertEquals(dsConfig.getFilePath(), DS_FILE_PATH);
	}
	
	/**
	 * Test if it can be retrieved an already existing
	 * DataSourceType by its type name
	 */
	@Test
	public void testDataSourceType() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		DataSourceConfig dsConfig = logic.getDataSourceType("WarcDS");
		
		assertEquals(dsConfig.getName(), "WarcDS");
	}

	
	/**
	 * Check if an exception is raised when an uncompleted
	 * DataSourceConfig is expected to be added in 
	 * the system
	 */
	@Test(expected = AddDataSourceException.class)
	public void testAddDataSourceConfigWithoutName() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE2, config);
		config.init();

		logic = new AppLogicImpl(config);
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setType(DS_TEST_TYPE);
		dsConfig.setDsClassName(DS_TEST_CLASS_NAME);
		dsConfig.setHandlerClassName(DS_TEST_HANDLER_NAME);
		dsConfig.setEnabled(DS_IS_ENABLED);
		dsConfig.setFilePath(DS_FILE_PATH);
		logic.addDataSourceConfig(dsConfig);
		
		dsConfig = logic.getDataSourceById(1);
	}
	
	/**
	 * Test if it is possible to add a new DataSourceConfig
	 */
	@Test
	public void testAddDataSourceConfig() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE2, config);
		config.init();

		logic = new AppLogicImpl(config);
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName(DS_TEST_NAME);
		dsConfig.setType(DS_TEST_TYPE);
		dsConfig.setDsClassName(DS_TEST_CLASS_NAME);
		dsConfig.setHandlerClassName(DS_TEST_HANDLER_NAME);
		dsConfig.setEnabled(DS_IS_ENABLED);
		dsConfig.setFilePath(DS_FILE_PATH);
		logic.addDataSourceConfig(dsConfig);
		
		dsConfig = logic.getDataSourceById(1);
		
		assertEquals(dsConfig.getName(), DS_TEST_NAME);
		assertEquals(dsConfig.getType(), DS_TEST_TYPE);
		assertEquals(dsConfig.getDsClassName(), DS_TEST_CLASS_NAME);
		assertEquals(dsConfig.getHandlerClassName(), DS_TEST_HANDLER_NAME);
		assertEquals(dsConfig.getEnabled(), DS_IS_ENABLED);
		assertEquals(dsConfig.getFilePath(), DS_FILE_PATH);
	}

	/**
	 * Test if it is possible to get a list of all available
	 * DataSource types in the system.
	 */
	@Test
	public void testGetDataSourceTypesList() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		Collection<DataSourceConfig> dataSourceConfigTypeList = 
				logic.getDataSourceTypesList();
		
		assertEquals(6, dataSourceConfigTypeList.size());
	}
	
	/**
	 * Test if it is possible to get a list of all 
	 * DataSourceConfig set in the system.
	 */
	@Test
	public void testGetDataSourceConfigList() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		Collection<DataSourceConfig> dataSourceConfigList = 
				logic.getDataSourceConfigList();
		
		assertEquals(9, dataSourceConfigList.size());
	}
	
	/**
	 * Test if the DataSource to be removed does not exist
	 */
	@Test(expected = DataSourceNotFoundException.class)
	public void testRemoveDataSourceConfigNotExist() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		logic.removeDataSourceConfig(10);
		
		Collection<DataSourceConfig> dataSourceConfigList = 
				logic.getDataSourceConfigList();
		
		assertEquals(8, dataSourceConfigList.size());
	}
	
	/**
	 * Check if it is possible to remove an already existing
	 *  
	 */
	@Test
	public void testRemoveDataSourceConfig() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();
		
		logic = new AppLogicImpl(config);
		logic.removeDataSourceConfig(1);
		
		Collection<DataSourceConfig> dataSourceConfigList = 
				logic.getDataSourceConfigList();
		
		assertEquals(8, dataSourceConfigList.size());
	}
	
	// Generate corpus test
	@Test
	public void testGenerateCorpus() {
		int numURL = 5;
		int start = 0;
		
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE4, config);
		config.init();
		
		for (int i = start; i < numURL + start; i++) {
			stubFor(get(urlMatching("/test" + i))
					.willReturn(
							aResponse()
									.withBody(
											"<html><body>Hello world!!</body></html>")));
		}

		GenerateCorpusState generateCorpusState = new 
				GenerateCorpusState();
		logic = new AppLogicImpl(config);
		logic.generateCorpus(generateCorpusState);
		
		assertEquals(2, generateCorpusState.getNumUrlHamCorrectlyLabeled());
		assertEquals(2, generateCorpusState.getNumUrlSpamCorrectlyLabeled());
	}
	
	// Generate corpus test
	private void initServer() {
		int numURL = 5;
		int start = 0;
		
		for (int i = start; i < numURL + start; i++) {
			stubFor(get(urlMatching("/test" + i))
					.willReturn(
							aResponse()
									.withBody(
											"<html><body>Hello world!!</body></html>")));
		}
	}
	
	@Test
	public void testStopGenerateCorpus() {
		initServer();
		
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE4, config);
		config.init();
		
		for (final GenerateCorpusStates gs:
				new GenerateCorpusStates[] {
			GenerateCorpusStates.GETTING_URLS_FROM_DS, 
			GenerateCorpusStates.READING_URLS,
			GenerateCorpusStates.CRAWLING_URLS
		}) {
			final GenerateCorpusState generateCorpusState = new 
					GenerateCorpusState();
			class PruebaStopGenerationCorpus implements Observer {
				@Override
				public void update(Observable arg0, Object arg1) {
					if (generateCorpusState.getState().equals(
							gs)) {
						logic.stopGenerateCorpus();
					}
				}
			}
			
			logic = new AppLogicImpl(config);
			generateCorpusState.addObserver(new PruebaStopGenerationCorpus());
			
			ExecutorService es = Executors.newSingleThreadExecutor();		
			es.execute(new Runnable() {
			    @Override 
			    public void run() {
			    	logic.generateCorpus(generateCorpusState);
			    }
			});
			es.shutdown();
			
			try {
				es.awaitTermination(5, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			assertEquals(GenerateCorpusStates.PROCESS_CANCELLED,
					generateCorpusState.getState());
		}
	}	
	
	/*
	 * addObserver(Observer obs); void deleteObserver(Observer obs);
	 * List<Country> listAvailableLanguagesFilter(); List<Country>
	 * listNotSelectedLanguages( List<Country> listSelectedCountries);
	 */
}
