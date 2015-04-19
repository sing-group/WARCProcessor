package com.warcgenerator.datasources.warccsv;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.warc.WarcDS;

@Ignore("This test will prove bug #123 is fixed, once someone fixes it")
public class WarcCSVDSTest {
	
	@Test
	public void testRead() {
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("WARC DS");
		dsConfig.setFilePath("src/test/resources/in/warccsv/test1");
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		
		CustomParamConfig customParam = new CustomParamConfig();
		customParam.setValue("WARC-Target-URI");
		customParams.put(WarcDS.URL_TAG, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("1");
		customParams.put(WarcCSVDS.SPAM_COL, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("0");
		customParams.put(WarcCSVDS.URL_COL, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue(",");
		customParams.put(WarcCSVDS.FIELD_SEPARATOR, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("1");
		customParams.put(WarcCSVDS.SPAM_COL_SPAM_VALUE, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("src/test/resources/in/warccsv/test1/index.csv");
		customParams.put(WarcCSVDS.FILE_CSV, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("true");
		customParams.put(WarcCSVDS.HEADER_ROW_PRESENT, customParam);
		
		customParam = new CustomParamConfig();
		customParam.setValue("(.*)");
		customParams.put(WarcCSVDS.REGEXP_URL_TAG, customParam);
		
		dsConfig.setCustomParams(customParams);
		WarcCSVDS arffDS = new WarcCSVDS(dsConfig);
		DataBean bean = arffDS.read();
		
		assertEquals("http://www.ucm.es", bean.getUrl());
		assertEquals(false, bean.isSpam());
		
		byte[] content = (byte[])bean.getData();
		assertEquals("Hola mundo esei.uvigo.es!!!", new String(content));
		
		while ((bean = arffDS.read()) != null &&
				!bean.getUrl().equals("http://www.yonkis.com/deadpool-no-sera-apta-para-menores/")) {
		}	
		
		assertEquals("http://www.yonkis.com/deadpool-no-sera-apta-para-menores/", bean.getUrl());
		assertEquals(true, bean.isSpam());
		
		content = (byte[])bean.getData();
		assertEquals("Hola mundo www.yonkis.com!!!", new String(content));
		
		arffDS.close();
	}
}
