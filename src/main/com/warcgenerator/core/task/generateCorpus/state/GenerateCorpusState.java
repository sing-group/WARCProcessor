package com.warcgenerator.core.task.generateCorpus.state;

import java.util.Observable;

public class GenerateCorpusState extends Observable {
	private int websVisited;
	private int websToVisitTotal;
	private GenerateCorpusStates currentState;
	private String currentUrlCrawled;
	private String message;
	private String currentUrlReadedFromDS;
	private int numUrlSpamReadedFromDS;
	private int numUrlHamReadedFromDS;

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
	
	public void incUrlSpamReadedFromDS() {
		setNumUrlSpamReadedFromDS(getNumUrlSpamReadedFromDS() + 1);
	}
	
	public void incUrlHamReadedFromDS() {
		setNumUrlHamReadedFromDS(getNumUrlHamReadedFromDS() + 1);
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
		return numUrlSpamReadedFromDS + numUrlHamReadedFromDS;
	}

	public int getNumUrlSpamReadedFromDS() {
		return numUrlSpamReadedFromDS;
	}

	public void setNumUrlSpamReadedFromDS(int numUrlSpamReadedFromDS) {
		this.numUrlSpamReadedFromDS = numUrlSpamReadedFromDS;
	}

	public int getNumUrlHamReadedFromDS() {
		return numUrlHamReadedFromDS;
	}

	public void setNumUrlHamReadedFromDS(int numUrlHamReadedFromDS) {
		this.numUrlHamReadedFromDS = numUrlHamReadedFromDS;
	}
}
