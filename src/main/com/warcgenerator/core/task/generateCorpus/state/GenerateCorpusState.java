package com.warcgenerator.core.task.generateCorpus.state;

import java.util.Observable;

public class GenerateCorpusState extends Observable {
	private int websVisited;
	private int websToVisitTotal;
	private GenerateCorpusStates currentState;
	private String currentUrlCrawled;
	private String message;
	private String currentUrlReadedFromDS;
	private int numUrlReadedFromDS;

	public GenerateCorpusState() {
	}
	
	public void setState(GenerateCorpusStates currentState) {
		this.currentState = currentState;
		setChanged();
		notifyObservers();
	}
	
	public GenerateCorpusStates getState() {
		return currentState;
	}
	
	public void incWebsVisited() {
		setWebsVisited(getWebsVisited() + 1);
	}
	
	public void incUrlReadedFromDS() {
		setNumUrlReadedFromDS(getNumUrlReadedFromDS() + 1);
	}

	public int getWebsVisited() {
		return websVisited;
	}

	public void setWebsVisited(int websVisited) {
		this.websVisited = websVisited;
	}

	public String getCurrentUrlCrawled() {
		return currentUrlCrawled;
	}

	public void setCurrentUrlCrawled(String currentUrlCrawled) {
		this.currentUrlCrawled = currentUrlCrawled;
	}

	public int getWebsToVisitTotal() {
		return websToVisitTotal;
	}

	public void setWebsToVisitTotal(int websToVisitTotal) {
		this.websToVisitTotal = websToVisitTotal;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCurrentUrlReadedFromDS() {
		return currentUrlReadedFromDS;
	}

	public void setCurrentUrlReadedFromDS(String currentUrlReadedFromDS) {
		this.currentUrlReadedFromDS = currentUrlReadedFromDS;
	}
	
	public int getNumUrlReadedFromDS() {
		return numUrlReadedFromDS;
	}

	public void setNumUrlReadedFromDS(int numUrlReadedFromDS) {
		this.numUrlReadedFromDS = numUrlReadedFromDS;
	}
}
