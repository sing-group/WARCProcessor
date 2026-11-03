package com.warcgenerator.datasources.file;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;

public class FileDSTest {
	
	@Test
	public void testRead() {
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("WARC DS");
		dsConfig.setFilePath("src/test/resources/in/file/test.txt");
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		dsConfig.setCustomParams(customParams);
		
		FileDS warcDS = new FileDS(dsConfig);
		DataBean bean = warcDS.read();
		String url = bean.getUrl();
		
		assertEquals("http://localhost:8089/test1", url);
		
		warcDS.close();
	}
}
