package com.warcgenerator.core.task.generateCorpus.state;

public enum GenerateCorpusStates {
	INIT,
	GETTING_URLS_FROM_DS,
	READING_URLS,
	CRAWLING_URLS,
	END,
	CANCELLING_PROCESS,
	PROCESS_CANCELLED
}
