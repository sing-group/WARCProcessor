package com.warcgenerator.core.plugin.webcrawler;

import org.apache.http.HttpStatus;

import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;

public class WebCrawlerHandler implements IWebCrawlerHandler {
	private IDataSource warcDS;
	private IDataSource domainsLabeledDS;
	private IDataSource domainsNotFoundDS;
	private boolean isSpam;

	public WebCrawlerHandler(boolean isSpam, 
			IDataSource domainsNotFoundDS, IDataSource domainsLabeledDS,
			IDataSource warcDS) {
		this.isSpam = isSpam;
		this.warcDS = warcDS;
		this.domainsLabeledDS = domainsLabeledDS;
		this.domainsNotFoundDS = domainsNotFoundDS;
	}

	public void handle(HtmlParseData htmlParseData) {
		if (htmlParseData.getUrl() != null && 
				htmlParseData.getHttpStatus() == HttpStatus.SC_OK) {
			// Es un fichero de tipo warc
			// Busca el fichero warc al que pertenece la url
			
			
			DataBean bean = new DataBean();
			bean.setData(htmlParseData.getHtml());
			bean.setUrl(htmlParseData.getUrl());
			bean.setSpam(this.isSpam);
			
			warcDS.write(bean);
			writeLabeled(htmlParseData.getUrl(), this.isSpam);
		} else {
			writeNotFound(htmlParseData.getUrl(), htmlParseData.getHttpStatus(), 
					htmlParseData.getHttpStatusDescription());
		}
	}

	private void writeLabeled(String url, boolean isSpam) {
		StringBuffer sb = new StringBuffer();
		// TODO To param
		String type = isSpam ? "spam" : "ham";
		sb.append(url).append(" ").append(type);
		DataBean data = new DataBean(sb.toString());
		domainsLabeledDS.write(data);
	}
	
	private void writeNotFound(String url, int httpStatus, 
			String httpHttpStatusDescription) {
		StringBuilder sb = new StringBuilder();
		if (httpStatus == HttpStatus.SC_NOT_FOUND) {
             sb.append("Broken link: ")
             	.append(url);
	     } else {
	    	 sb.append("Non success status for link: ")
	    	 	.append(url)
	    	 	.append(", status code: ")
	    	 	.append(httpStatus)
	    	 	.append(", description: ")
	    	 	.append(httpHttpStatusDescription);
	     }
		DataBean data = new DataBean(sb.toString());
		domainsNotFoundDS.write(data);
	}
}
