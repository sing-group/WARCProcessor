package com.warcgenerator.datasources.warc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Pattern;

import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.warc.WarcDS;
import com.warcgenerator.datasources.arff.ArffDS;

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
		
		assertEquals("http://localhost:8089/test1", url);
		
		warcDS.close();
	}
	
	@Test
	public void testWriteUsesIso8601Date() throws Exception {
		File warc = File.createTempFile("warcds", ".warc");
		warc.deleteOnExit();
		
		WarcDS warcDS = new WarcDS(new OutputWarcConfig(false, warc.getPath()));
		DataBean bean = new DataBean();
		bean.setUrl("http://localhost:8089/test1");
		bean.setData("<html></html>");
		warcDS.write(bean);
		warcDS.close();
		
		String iso8601 = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z";
		String content = new String(Files.readAllBytes(warc.toPath()), StandardCharsets.UTF_8);
		assertTrue(content, Pattern.compile("^WARC-Date: " + iso8601 + "$", Pattern.MULTILINE)
				.matcher(content).find());
		
		// The record has to be readable by an independent WARC parser
		try (ArchiveReader reader = WARCReaderFactory.get(warc)) {
			ArchiveRecord record = reader.iterator().next();
			assertEquals("http://localhost:8089/test1", record.getHeader().getUrl());
			assertTrue(record.getHeader().getDate().matches(iso8601));
		}
	}
}
