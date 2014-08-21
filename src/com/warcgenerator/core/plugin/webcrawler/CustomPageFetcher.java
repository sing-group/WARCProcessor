package com.warcgenerator.core.plugin.webcrawler;

import com.warcgenerator.core.common.GenerateCorpusState;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;

public class CustomPageFetcher extends PageFetcher {
	private GenerateCorpusState generateCorpusState;
	
	public CustomPageFetcher(GenerateCorpusState generateCorpusState, 
			CrawlConfig config) {
		super(config);
		this.generateCorpusState =
				generateCorpusState;
	}

	public GenerateCorpusState getGenerateCorpusState() {
		return generateCorpusState;
	}

	public void setGenerateCorpusState(GenerateCorpusState generateCorpusState) {
		this.generateCorpusState = generateCorpusState;
	}
}
