package com.warcgenerator.core.datasource;

public class DataBean {
	private String data;
	private String url;
	private boolean spam;
	
	public DataBean() {}
	
	public DataBean(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSpam() {
		return spam;
	}

	public void setSpam(boolean spam) {
		this.spam = spam;
	}
}
