package com.warcgenerator.core.plugin.webcrawler;

public interface IWebCrawler {
	public void addSeed(String url);
	public void start();
	public void stop();
	public void close();
}
