package com.warcgenerator.datasources.csv;

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
public class CSVDSTest {
	
	@Test
	public void testRead() {
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("CSV DS");
		dsConfig.setFilePath("src/test/resources/in/csv/test.csv");
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		
		CustomParamConfig customParam = new CustomParamConfig();
		customParam.setValue(",");
		customParams.put(CSVDS.FIELD_SEPARATOR, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("true");
		customParams.put(CSVDS.HEADER_ROW_PRESENT, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("0");
		customParams.put(CSVDS.URL_COL, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("5");
		customParams.put(CSVDS.SPAM_COL, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("1");
		customParams.put(CSVDS.SPAM_COL_SPAM_VALUE, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("(.*)");
		customParams.put(CSVDS.REGEXP_URL_TAG, customParam);
		
		dsConfig.setCustomParams(customParams);
		CSVDS csvDS = new CSVDS(dsConfig);
		DataBean bean = csvDS.read();
		String url = bean.getUrl();
		
		assertEquals("http://sing.ei.uvigo.es/kunagi/login.html", url);
		
		csvDS.close();
	}
	
	@Test(expected = MissingParamsException.class)
	public void testMissingParameterSpamAttr() {
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("CSV DS");
		dsConfig.setFilePath("src/test/resources/in/csv/test.csv");
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		
		CustomParamConfig customParam = new CustomParamConfig();
		customParam.setValue(",");
		customParams.put(CSVDS.FIELD_SEPARATOR, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("true");
		customParams.put(CSVDS.HEADER_ROW_PRESENT, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("0");
		customParams.put(CSVDS.URL_COL, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("1");
		customParams.put(CSVDS.SPAM_COL_SPAM_VALUE, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("(.*)");
		customParams.put(CSVDS.REGEXP_URL_TAG, customParam);
		
		dsConfig.setCustomParams(customParams);
		CSVDS csvDS = new CSVDS(dsConfig);
	}
}
