package com.warcgenerator.core.config;

/**
 * File with the web crawler config
 * 
 * @author Miguel Callon
 *
 */
public class WebCrawlerConfig {
	private String storePath;
	private String url;
	private int numberOfCrawlers;
	private int maxDepthOfCrawling;
	
	public WebCrawlerConfig() {
	}
	
	public WebCrawlerConfig(WebCrawlerConfig template) {
		this.numberOfCrawlers = template.getNumberOfCrawlers();
		this.url = template.getUrl();
		this.maxDepthOfCrawling = template.getMaxDepthOfCrawling();
		this.storePath = template.getStorePath();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
