package com.warcgenerator.core.task.generateCorpus.state;

public enum GenerateCorpusStates {
	GETTING_URLS_FROM_DS,
	READING_URLS,
	CRAWLING_URLS,
	ENDING,
	ERROR_PROCESSING_DS,
	CANCELlING_PROCESS,
	PROCESS_CANCELLED
}
