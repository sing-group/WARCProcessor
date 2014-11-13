package com.warcgenerator.datasources.warccsv.bean;

public class URLInfo {
	private Integer id;
	private String domain;
	private String url;
	private String filePath;
	private Boolean spam;

	public URLInfo() {
		spam = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getSpam() {
		return spam;
	}

	public void setSpam(Boolean spam) {
		this.spam = spam;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id: ").append(id).append(", domain: ").append(domain)
				.append(", url: ").append(url).append(", filePath: ")
				.append(filePath).append(", spam:").append(spam);
		return sb.toString();
	}
}

