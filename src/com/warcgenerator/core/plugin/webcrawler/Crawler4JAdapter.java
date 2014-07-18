package com.warcgenerator.core.plugin.webcrawler;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.config.WebCrawlerConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.exception.plugin.PluginException;
import com.warcgenerator.core.helper.FileHelper;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler4JAdapter extends WebCrawler implements IWebCrawler {
	private CrawlController controller;
	private Map<String, IWebCrawlerHandler> handlers;
	private WebCrawlerBean webCrawlerBean;
	private int numberOfCrawlers;
	private Map<String, com.warcgenerator.core.plugin.webcrawler.HtmlParseData> parseDataMap;
	
	private static Logger logger = Logger.getLogger
            (Crawler4JAdapter.class);

    // Currently it doesn't use any filter
	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	public Crawler4JAdapter() {
		parseDataMap = new HashMap<String, 
				com.warcgenerator.core.plugin.webcrawler.HtmlParseData>();
		handlers = new HashMap<String, IWebCrawlerHandler>();
	}

	public Crawler4JAdapter(WebCrawlerConfig configWC,
			WebCrawlerBean webCrawlerBean
			) throws PluginException {
		super();
		this.handlers = new HashMap<String, IWebCrawlerHandler>();
		this.webCrawlerBean = webCrawlerBean;
		String crawlStorageFolder = configWC.getStorePath();
		numberOfCrawlers = configWC.getNumberOfCrawlers();

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		/*
		 * You can set the maximum crawl depth here. The default value is -1 for
		 * unlimited depth
		 */
		config.setMaxDepthOfCrawling(configWC.getMaxDepthOfCrawling());
		
		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);

		try {
			controller = new CrawlController(config, pageFetcher,
					robotstxtServer);
		} catch (Exception e) {
			throw new PluginException(e);
		}

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		for (String url:configWC.getUrls()) {
			controller.addSeed(url);
		}
	}

	@SuppressWarnings("unchecked")
	public void start() {
		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(Crawler4JAdapter.class, numberOfCrawlers);
		
		Map<String, DataSource> outputDS = 
				new HashMap<String, DataSource>();
		
		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		for (Object localData : crawlersLocalData) {
			if (localData instanceof Collection<?>) {
				for (com.warcgenerator.core.plugin.webcrawler.HtmlParseData parseData : 
						(Collection<com.warcgenerator.core.plugin.webcrawler.HtmlParseData>) localData) {
					IWebCrawlerHandler handler = handlers.get(
							FileHelper.getDomainNameFromURL(parseData.getUrl()));
					
					DataSource warcDS = 
							outputDS.get(FileHelper.getDomainNameFromURL(parseData.getUrl()));
					if (warcDS == null) {
						StringBuilder outputWarcPath = new StringBuilder();
						if (webCrawlerBean.isSpam()) {
							outputWarcPath.append(webCrawlerBean.
									getOutputCorpusConfig().getSpamDir());
						} else {
							outputWarcPath.append(webCrawlerBean.
									getOutputCorpusConfig().getHamDir());
						}
						outputWarcPath.append(File.separator);
						
						StringBuilder warcFileName = new StringBuilder();
						warcFileName.append(outputWarcPath.toString()).append(
								FileHelper.getFileNameFromURL(
										FileHelper.getDomainNameFromURL(parseData.getUrl())
										)).append(".warc");
						
						warcDS = new WarcDS(
								new OutputWarcConfig(webCrawlerBean.isSpam(),
										warcFileName.toString()));
						
						outputDS.put(FileHelper.getDomainNameFromURL(parseData.getUrl()),
								warcDS);
					}
					
					if (handler == null) {
						handler = new WebCrawlerHandler(
							webCrawlerBean.isSpam(),
							webCrawlerBean.getDomainsNotFoundDS(), 
							webCrawlerBean.getDomainsLabeledDS(),
							warcDS);
						
						handlers.put(FileHelper.getDomainNameFromURL(parseData.getUrl()),
									handler);
					}
					
					handler.handle(parseData);
				}
			}
		}
		
		// Close all output datasources
		for (DataSource aux : outputDS.values()) {
					aux.close();
		}
	}

	public Object getMyLocalData() {
		return parseDataMap.values();
	}

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		// return !FILTERS.matcher(href).matches()
		// && href.startsWith("http://www.ics.uci.edu/");
		return !FILTERS.matcher(href).matches();
	}

	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode,
			String statusDescription) {
		com.warcgenerator.core.plugin.webcrawler.HtmlParseData parseData =
			getParseData(webUrl.getURL());
		
		parseData.setUrl(webUrl.getURL());
		parseData.setHttpStatus(statusCode);
		parseData.setHttpStatusDescription(statusDescription);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		
		
		String url = page.getWebURL().getURL();
		logger.info("URL: " + url);
		com.warcgenerator.core.plugin.webcrawler.HtmlParseData parseData =
				getParseData(url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			List<WebURL> links = htmlParseData.getOutgoingUrls();

			parseData.setHtml(html);
			parseData.setText(text);

			logger.info("Text length: " + text.length());
			logger.info("Html length: " + html.length());
			logger.info("Number of outgoing links: " + links.size());
		}
	}

	private com.warcgenerator.core.plugin.webcrawler.HtmlParseData getParseData(String url) {
		com.warcgenerator.core.plugin.webcrawler.HtmlParseData parseData = 
				parseDataMap.get(url);
		if (parseData == null) {
			parseData =  new com.warcgenerator.core.plugin.webcrawler.HtmlParseData();
			parseDataMap.put(url, parseData);
		}
		return parseData;
	}
}
