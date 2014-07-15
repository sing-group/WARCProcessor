package com.warcgenerator.core.config;

import java.util.Set;

/**
 * File with the web crawler config
 * 
 * @author Miguel Callon
 *
 */
public class WebCrawlerConfig {
	private String storePath;
	private Set<String> urls;
	private int numberOfCrawlers;
	private int maxDepthOfCrawling;
	
	public WebCrawlerConfig() {
	}
	
	public WebCrawlerConfig(WebCrawlerConfig template) {
		this.numberOfCrawlers = template.getNumberOfCrawlers();
		this.urls = template.getUrls();
		this.maxDepthOfCrawling = template.getMaxDepthOfCrawling();
		this.storePath = template.getStorePath();
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public int getNumberOfCrawlers() {
		return numberOfCrawlers;
	}

	public void setNumberOfCrawlers(int numberOfCrawlers) {
		this.numberOfCrawlers = numberOfCrawlers;
	}

	public int getMaxDepthOfCrawling() {
		return maxDepthOfCrawling;
	}

	public void setMaxDepthOfCrawling(int maxDepthOfCrawling) {
		this.maxDepthOfCrawling = maxDepthOfCrawling;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	
	
}
