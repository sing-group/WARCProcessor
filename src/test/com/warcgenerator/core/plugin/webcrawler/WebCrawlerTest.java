package com.warcgenerator.core.plugin.webcrawler;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.warcgenerator.AbstractTestCase;
import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputCorpusConfig;
import com.warcgenerator.core.config.WebCrawlerConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.generic.GenericDS;
import com.warcgenerator.core.helper.ConfigHelper;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

public class WebCrawlerTest extends AbstractTestCase {
	private final String CONFIG_FILE1 = "src/test/resources/config/config1.wpg";

	private AppConfig config;
	private WebCrawlerConfig webCrawlerConfig;
	private GenerateCorpusState generateCorpusState;
	private OutputCorpusConfig outputCorpusConfig;
	private IDataSource labeledDS;
	private IDataSource notFoundDS;
	private WebCrawlerBean webCrawlerBean;
	private Map<String, DataSource> outputDS;
	private Map<String, DataBean> urlsWebCrawler;
	private Set<String> urlsActive;
	private Set<String> urlsNotActive;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8089);

	@Before
	public void before() {
		AppConfig config = new AppConfig();
		ConfigHelper.configure(CONFIG_FILE1, config);
		config.init();

		webCrawlerConfig = new WebCrawlerConfig(
				config.getWebCrawlerCfgTemplate());

		webCrawlerConfig.setMaxDepthOfCrawling(1);
		webCrawlerConfig.setStorePath("src/test/resources/tmp");
		webCrawlerConfig.setNumberOfCrawlers(1);
		webCrawlerConfig.setFollowRedirect(true);

		generateCorpusState = new GenerateCorpusState();

		outputCorpusConfig = (OutputCorpusConfig) config.getOutputConfig();
		outputCorpusConfig.setOutputDir("src/test/resources/tmp/out");
		outputCorpusConfig.setHamDir("src/test/resources/tmp/out/_ham_");
		outputCorpusConfig.setSpamDir("src/test/resources/tmp/out/_spam_");

		// Generate wars
		labeledDS = new GenericDS(new DataSourceConfig(
				"src/test/resources/tmp/out/domains.labelled"));
		notFoundDS = new GenericDS(new DataSourceConfig(
				"src/test/resources/tmp/out/domains.notFound"));

		webCrawlerBean = new WebCrawlerBean(labeledDS, notFoundDS, false,
				outputCorpusConfig);

		outputDS = new HashMap<String, DataSource>();
		urlsWebCrawler = new HashMap<String, DataBean>();

		urlsActive = new HashSet<String>();
		urlsNotActive = new HashSet<String>();

	}

	// @Test
	public void testStart() {
		stubFor(get(urlMatching("/test1"))
				.willReturn(
						aResponse().withBody(
								"<html><body>Hello world!!</body></html>")));

		IWebCrawler webCrawler = new Crawler4JAdapter(config,
				generateCorpusState, webCrawlerConfig, webCrawlerBean,
				outputDS, urlsWebCrawler, urlsActive, urlsNotActive);

		webCrawler.addSeed("http://localhost:8089/test1");
		webCrawler.start();
	}

	@Test
	public void testStartLoop() {
		int numTries = 37;
		int numURL = 0;
		int numMeasures = 3;
		int start = 0;

		try {
			FileUtils.forceDelete(new File("src/test/resources/tmp/data.csv"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try (PrintWriter writer = new PrintWriter(new File(
				"src/test/resources/tmp/data.csv"))) {

			for (int z = 0; z < numTries; z++) {
				double tMean = 0;

				if (z < 10) { numURL++; }
				if (z >= 10 && z < 19) { numURL+=10; }
				if (z >= 19 && z < 28) { numURL+=100; }
				if (z >= 28) { numURL+=1000; }
				
				for (int j = 0; j < numMeasures; j++) {
					long t1 = System.currentTimeMillis();

					for (int i = start; i < numURL + start; i++) {
						stubFor(get(urlMatching("/test" + i))
								.willReturn(
										aResponse()
												.withBody(
														"<html><body>Hello world!!</body></html>")));
					}

					IWebCrawler webCrawler = new Crawler4JAdapter(config,
							generateCorpusState, webCrawlerConfig,
							webCrawlerBean, outputDS, urlsWebCrawler,
							urlsActive, urlsNotActive);

					for (int i = start; i < numURL + start; i++) {
						webCrawler.addSeed("http://localhost:8089/test" + i);
					}
					webCrawler.start();

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
				

				// Write down results
				// File file = new File("times");

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public void addSeed(String url); public void start(); public void stop();
	 * public void close();
	 */
}
