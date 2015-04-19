package com.warcgenerator.datasources.warc;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.warc.WarcDS;
import com.warcgenerator.datasources.arff.ArffDS;

@Ignore("This test will prove bug #123 is fixed, once someone fixes it")
public class WarcDSTest {
	
	@Test
	public void testRead() {
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("WARC DS");
		dsConfig.setFilePath("src/test/resources/in/warc/test.warc");
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		
		CustomParamConfig customParam = new CustomParamConfig();
		customParam.setValue("WARC-Target-URI");
		customParams.put(WarcDS.URL_TAG, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("(.*)");
		customParams.put(ArffDS.REGEXP_URL_TAG, customParam);
		
		dsConfig.setCustomParams(customParams);
		WarcDS warcDS = new WarcDS(dsConfig);
		DataBean bean = warcDS.read();
		String url = bean.getUrl();
		
		assertEquals("https://www.ugr.es/", url);
		
		warcDS.close();
	}
}
