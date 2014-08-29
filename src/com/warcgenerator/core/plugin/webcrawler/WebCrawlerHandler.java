package com.warcgenerator.core.plugin.webcrawler;

import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.helper.OutputHelper;

public class WebCrawlerHandler implements IWebCrawlerHandler {
	private AppConfig config;
	private IDataSource warcDS;
	private IDataSource domainsLabeledDS;
	private IDataSource domainsNotFoundDS;
	private boolean isSpam;
	private Map<String, DataBean> urls;
	private Set<String> urlsActive;
	private Set<String> urlsNotActive;

	public WebCrawlerHandler(AppConfig config, boolean isSpam,
			IDataSource domainsNotFoundDS, IDataSource domainsLabeledDS,
			IDataSource warcDS, Map<String, DataBean> urls,
			Set<String> urlsActive, Set<String> urlsNotActive) {
		this.config = config;
		this.isSpam = isSpam;
		this.warcDS = warcDS;
		this.domainsLabeledDS = domainsLabeledDS;
		this.domainsNotFoundDS = domainsNotFoundDS;
		this.urls = urls;
		this.urlsActive = urlsActive;
		this.urlsNotActive = urlsNotActive;
	}

	public void handle(HtmlParseData htmlParseData) {
		if (htmlParseData.getUrl() != null
				&& htmlParseData.getHttpStatus() == HttpStatus.SC_OK) {
			// Es un fichero de tipo warc
			// Busca el fichero warc al que pertenece la url
			DataBean bean = new DataBean();
			bean.setData(htmlParseData.getHtml());
			bean.setUrl(htmlParseData.getUrl());
			bean.setSpam(this.isSpam);

			// If download again is not active then, if exists,
			// use the information available in warc datasources
			DataBean tmpBean = urls.get(htmlParseData.getUrl());
			if (tmpBean != null) {
				if (tmpBean.getTypeDS() != null
						&& tmpBean.getTypeDS().equals(WarcDS.DS_TYPE)
						&& !config.getDownloadAgain()) {
					bean = tmpBean;
				}
			}

			warcDS.write(bean);
			OutputHelper.writeLabeled(domainsLabeledDS, htmlParseData.getUrl(),
					this.isSpam);

			urlsActive.add(htmlParseData.getUrl());
		} else {
			urlsNotActive.add(htmlParseData.getUrl());
			OutputHelper.writeNotFound(domainsNotFoundDS,
					htmlParseData.getUrl(), htmlParseData.getHttpStatus(),
					htmlParseData.getHttpStatusDescription());
		}
	}
}
