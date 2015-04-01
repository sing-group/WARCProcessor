package com.warcgenerator.core.plugin.webcrawler;

import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CustomCrawlController extends CrawlController {

	private static final Logger logger = Logger.getLogger(CrawlController.class.getName());

	
	public CustomCrawlController(CrawlConfig config, PageFetcher pageFetcher,
			RobotstxtServer robotstxtServer) throws Exception {
		super(config, pageFetcher, robotstxtServer);
	}
	
	/*protected static void sleep(int segundos) {
		try {
			Thread.sleep(Math.round(segundos) * 10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void shutdown() {
		//this.frontier.finish();		
		this.getPageFetcher().shutDown();
		super.shutdown();
	}
	
}
