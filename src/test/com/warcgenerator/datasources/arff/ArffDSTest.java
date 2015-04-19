package com.warcgenerator.datasources.arff;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.exception.datasource.MissingParamsException;

@Ignore("This test will prove bug #123 is fixed, once someone fixes it")
public class ArffDSTest {
	
	@Test
	public void testRead() {
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("ARFF DS");
		dsConfig.setFilePath("src/test/resources/in/arff/test.arff");
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		
		CustomParamConfig customParam = new CustomParamConfig();
		customParam.setValue("LABEL");
		customParams.put(ArffDS.LABEL_TAG, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("URL");
		customParams.put(ArffDS.URL_TAG, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("1");
		customParams.put(ArffDS.SPAM_COL_SPAM_VALUE, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("URL_(.*)");
		customParams.put(ArffDS.REGEXP_URL_TAG, customParam);
		
		dsConfig.setCustomParams(customParams);
		ArffDS arffDS = new ArffDS(dsConfig);
		DataBean bean = arffDS.read();
		String url = bean.getUrl();
		
		assertEquals("http://esei.uvigo.es", url);
		
		arffDS.close();
	}
	
	@Test(expected = MissingParamsException.class)
	public void testMissingParameterSpamAttr() {
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("ARFF DS");
		dsConfig.setFilePath("src/test/resources/in/arff/test.arff");
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		
		CustomParamConfig customParam = new CustomParamConfig();
		customParam.setValue("URL");
		customParams.put(ArffDS.URL_TAG, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("1");
		customParams.put(ArffDS.SPAM_COL_SPAM_VALUE, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("URL_(.*)");
		customParams.put(ArffDS.REGEXP_URL_TAG, customParam);
		
		dsConfig.setCustomParams(customParams);
		ArffDS arffDS = new ArffDS(dsConfig);
		DataBean bean = arffDS.read();
		String url = bean.getUrl();
		
		assertEquals("http://esei.uvigo.es", url);
		
		arffDS.close();
	}
}
