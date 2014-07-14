package com.warcgenerator.core.config;

import java.util.ArrayList;
import java.util.List;

/**
 * File with the web crawler config
 * 
 * @author Miguel Callon
 *
 */
public class WebCrawlerConfig {
	private String storePath;
	private List<String> urls;
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

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
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
