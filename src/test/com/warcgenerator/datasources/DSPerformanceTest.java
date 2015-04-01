package com.warcgenerator.datasources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.datasources.csv.CSVDS;

public class DSPerformanceTest {
	private CSVDS csvDS;
	
	@Before
	public void before() {
		initCSVDS();
	}
	
	@Test
	public void testPerformance() {
		int numTries = 37;
		int numURL = 0;
		int numMeasures = 3;
		int start = 0;
		
		String testCSVFile = "src/test/resources/tmp/test_file.csv";
		
		try (PrintWriter writer = new PrintWriter(new File(
				"src/test/resources/tmp/DS_performance_data.csv"));
			 PrintWriter testWriter = new PrintWriter(new File(
				"src/test/resources/tmp/test_file.csv"))) {
			for (int z = 0; z < numTries; z++) {
				double tMean = 0;
				
				if (z < 10) { numURL++; }
				if (z >= 10 && z < 19) { numURL+=10; }
				if (z >= 19 && z < 28) { numURL+=100; }
				if (z >= 28) { numURL+=1000; }
				
				for (int j = 0; j < numMeasures; j++) {
					for (int i = start; i < numURL + start; i++) {
						
					}
					
					long t1 = System.currentTimeMillis();

					
					csvDS.read();
					
					
					long t2 = System.currentTimeMillis();

					// Inc url number
					start = start + numURL;

					long tDiff = t2 - t1;
					System.out.println("tDiff" + j + ": " + tDiff);

					tMean += (double) (tDiff / 3.0);
				}
								
				System.out.println("tMean: " + tMean);

				// NumUrls | t0 | t1 | t2 | tmean
				StringBuilder sb = new StringBuilder();
				sb.append(numURL).append(",").append(tMean);
				
				//System.out.println(sb.toString());
				writer.write(sb.toString());
				writer.println();
				writer.flush();
				

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initCSVDS() {
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
		csvDS = new CSVDS(dsConfig);
	}
}
