package com.warcgenerator.core.task.generateCorpus.state;

import java.util.Observable;

public class GenerateCorpusState extends Observable {
	private int websVisited;
	private int websToVisitTotal;
	private GenerateCorpusStates currentState;
	private String currentUrlCrawled;
	
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
}
