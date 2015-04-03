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
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.warc.WarcDS;
import com.warcgenerator.datasources.arff.ArffDS;
import com.warcgenerator.datasources.csv.CSVDS;
import com.warcgenerator.datasources.file.FileDS;

public class DSPerformanceTest {
	private ArffDS arffDS;
	private CSVDS csvDS;
	private FileDS fileDS;
	private WarcDS warcDS;
	
	private String TEST_CSV_FILE = "src/test/resources/tmp/in_test/test_file.csv";
	private String TEST_ARFF_FILE = "src/test/resources/tmp/in_test/test_file.arff";
	private String TEST_TXT_FILE = "src/test/resources/tmp/in_test/test_file.txt";
	private String TEST_WARC_FILE = "src/test/resources/tmp/in_test/test_file.warc";
	
	private final int NUM_URLS = 10000;
	
	Map<String, IDataSource> dataSources;
	
	@Before
	public void before() {
		initCSVDS();
		initArffDS();
		initFileDS();
		initWarcDS();
		
		dataSources = new HashMap<String, IDataSource>();
		dataSources.put("CSV", csvDS);
		dataSources.put("Arff", arffDS);
		dataSources.put("TXT", fileDS);
		dataSources.put("WARC", warcDS);
	}

	@Test
	public void testPerformance() {
		int numTries = 37;
		int numURL = 0;
		int numMeasures = 3;
		int start = 0;

		try (PrintWriter writer = new PrintWriter(new File(
				"src/test/resources/tmp/DS_performance_data.csv"));) {
			StringBuilder sb = new StringBuilder();
			sb.append("Num. Urls");
			for(String key:dataSources.keySet()) {
				sb.append(",").append(key);
			}
			writer.println(sb.toString());
			writer.flush();
			
			for (int z = 0; z < numTries; z++) {
				double tMean = 0;

				if (z < 10) {
					numURL++;
				}
				if (z >= 10 && z < 19) {
					numURL += 10;
				}
				if (z >= 19 && z < 28) {
					numURL += 100;
				}
				if (z >= 28) {
					numURL += 1000;
				}

				// NumUrls | t0 | t1 | t2 | tmean
				sb = new StringBuilder();
				sb.append(numURL);
				for(String key:dataSources.keySet()) {
					IDataSource ds = dataSources.get(key);
					for (int j = 0; j < numMeasures; j++) {
						long t1 = System.nanoTime();
						for (int i = start; i < numURL + start; i++) {
							ds.read();
						}
						long t2 = System.nanoTime();
						
						// Inc url number
						start = start + numURL;
	
						long tDiff = t2 - t1;
						System.out.println("tDiff" + j + ": " + tDiff);
	
						tMean += (double) (tDiff / 3.0);
					}

					System.out.println(key + " - tMean: " + tMean);
					sb.append(",").append(tMean);
				}
				
				// System.out.println(sb.toString());
				writer.write(sb.toString());
				writer.println();
				writer.flush();

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initWarcDS() {
		try (PrintWriter testWriter = new PrintWriter(new File(TEST_WARC_FILE))) {
			StringBuilder sb = new StringBuilder();
			sb.append("WARC/1.0\nWARC-Type: warcinfo\nWARC-Date: 2012-02-12T08:23:23Z\nWARC-Filename: 0013wb-88.warc.gz\nWARC-Number-of-Documents: 967\nWARC-File-Length: 29902030\nWARC-Data-Type: web crawl\nWARC-Record-ID: <urn:uuid:1b361052-a7d0-48e7-a1f9-b7d5c235b893>\nContent-Type: application/warc-fields\nContent-Length: 283\n\nsoftware: Heritrix/3.1.1-SNAPSHOT-20120210.102032 http://crawler.archive.org\nformat: WARC File Format 1.0\nconformsTo: http://bibnum.bnf.fr/WARC/WARC_ISO_28500_version1_latestdraft.pdf\nisPartOf: ClueWeb12\ndescription:  The Lemur Project's ClueWeb12 dataset (http://lemurproject.org/\n\n\n");
			testWriter.print(sb.toString());
			
			for (int i = 0; i < NUM_URLS; i++) {
				sb = new StringBuilder();
				sb.append("WARC/1.0\nWARC-Type: response\nWARC-Date: 2012-02-12T08:28:02Z\nWARC-TREC-ID: clueweb12-0013wb-88-00000\nWARC-IP-Address: 97.74.46.128\nWARC-Payload-Digest: sha1:2LXCCLVQ42HRZW3YNSU56XUQJ2UO4EDQ\nWARC-Target-URI: http://esei.uvigo.es\nWARC-Record-ID: <urn:uuid:ed8ee5f6-9455-48c9-8f93-a7726ae4b89a>\nContent-Type: application/http; msgtype=response\nContent-Length: 44706\n\n");
				sb.append("HTTP/1.1 200 OK\nDate: Sun, 12 Feb 2012 08:28:03 GMT\nServer: Apache\nAccept-Ranges: bytes\nContent-Length: 44545\nConnection: close\nContent-Type: text/html\n\n");
				sb.append("Hola mundo!!\n\n");
				
				testWriter.println(sb.toString());
			}
			testWriter.println();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("WARC DS");
		dsConfig.setFilePath(TEST_WARC_FILE);
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		
		CustomParamConfig customParam = new CustomParamConfig();
		customParam.setValue("WARC-Target-URI");
		customParams.put(WarcDS.URL_TAG, customParam);

		customParam = new CustomParamConfig();
		customParam.setValue("(.*)");
		customParams.put(ArffDS.REGEXP_URL_TAG, customParam);
		
		dsConfig.setCustomParams(customParams);
		warcDS = new WarcDS(dsConfig);
	}
	
	public void initFileDS() {
		try (PrintWriter testWriter = new PrintWriter(new File(TEST_TXT_FILE))) {
			for (int i = 0; i < NUM_URLS; i++) {
				testWriter.println("http://localhost:8089/");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("FILE DS");
		dsConfig.setFilePath(TEST_TXT_FILE);
		
		Map<String, CustomParamConfig> customParams =
				new HashMap<String, CustomParamConfig>();
		dsConfig.setCustomParams(customParams);
		
		fileDS = new FileDS(dsConfig);
	}
	
	public void initArffDS() {
		try (PrintWriter testWriter = new PrintWriter(new File(TEST_ARFF_FILE))) {
			testWriter.println("@relation weather");
			testWriter.println();
			testWriter.println("@attribute LABEL string");
			testWriter.println("@attribute URL string");
			testWriter.println();
			testWriter.println("@data");
			testWriter.println();
			for (int i = 0; i < NUM_URLS; i++) {
				testWriter.println("http://localhost:8089/,1");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("ARFF DS");
		dsConfig.setFilePath(TEST_ARFF_FILE);
		
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
		arffDS = new ArffDS(dsConfig);
	}
	
	public void initCSVDS() {
		try (PrintWriter testWriter = new PrintWriter(new File(TEST_CSV_FILE))) {
			for (int i = 0; i < NUM_URLS; i++) {
				testWriter.println("http://localhost:8089/,1");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataSourceConfig dsConfig = new DataSourceConfig();
		dsConfig.setName("CSV DS");
		dsConfig.setFilePath(TEST_CSV_FILE);

		Map<String, CustomParamConfig> customParams = new HashMap<String, CustomParamConfig>();

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
