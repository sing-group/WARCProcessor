package com.warcgenerator.core.plugin.webcrawler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CustomCrawlController extends CrawlController {

	private static final Logger logger = LogManager.getLogger(CrawlController.class.getName());

	
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
	
		// Send the shutdown request and then wait for finishing
	//public void shutdown() {
		//super.finished = true;
		//super.shutdown();
	    //super.waitUntilFinish();
	//}
	
}
