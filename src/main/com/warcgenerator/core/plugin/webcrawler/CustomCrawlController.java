package com.warcgenerator.core.plugin.webcrawler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import crawlercommons.filters.basic.BasicURLNormalizer;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.frontier.FrontierConfiguration;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CustomCrawlController extends CrawlController {

	private static final Logger logger = LogManager.getLogger(CrawlController.class.getName());

	
	public CustomCrawlController(CrawlConfig config, BasicURLNormalizer normalizer,
			PageFetcher pageFetcher, RobotstxtServer robotstxtServer,
			FrontierConfiguration frontierConfiguration) throws Exception {
		super(config, normalizer, pageFetcher, robotstxtServer, frontierConfiguration);
	}
	
	/*protected static void sleep(int segundos) {
		try {
			Thread.sleep(Math.round(segundos) * 10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
		// Send the shutdown request and then wait for finishing
	//public void shutdown() {
		//super.finished = true;
		//super.shutdown();
	    //super.waitUntilFinish();
	//}
	
}
