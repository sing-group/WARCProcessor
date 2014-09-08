package com.warcgenerator.core.plugin.webcrawler;

import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

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

	private static Logger logger = Logger.getLogger
            (WebCrawlerHandler.class);
	
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
		// Look for Warc Bean containing html of the urls
		DataBean previousWarcBean = urls.get(htmlParseData.getUrl());
		boolean existPreviousWarcBean = checkExistPreviousWarcBean(previousWarcBean);
		
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
			if (existPreviousWarcBean &&
				!config.getDownloadAgain()) {
					bean = previousWarcBean;
			}

			// Check if the url has data
			if (bean.getData() != null) {
				warcDS.write(bean);
				OutputHelper.writeLabeled(domainsLabeledDS, htmlParseData.getUrl(),
						this.isSpam);
			} else {
				logger.info("URL: " + bean.getUrl() + " doesn't have data.");
			}
				
			urlsActive.add(htmlParseData.getUrl());
		} else if (htmlParseData.getUrl() != null &&
				existPreviousWarcBean &&
				!config.getFollowRedirect() &&
				(htmlParseData.getHttpStatus() == HttpStatus.SC_MOVED_PERMANENTLY ||
				htmlParseData.getHttpStatus() == HttpStatus.SC_MOVED_TEMPORARILY)) {
			
				if (!config.getDownloadAgain()) {
					warcDS.write(previousWarcBean);
					OutputHelper.writeLabeled(domainsLabeledDS, htmlParseData.getUrl(),
							this.isSpam);
				}
	
		} else {
			urlsNotActive.add(htmlParseData.getUrl());
			OutputHelper.writeNotFound(domainsNotFoundDS,
					htmlParseData.getUrl(), htmlParseData.getHttpStatus(),
					htmlParseData.getHttpStatusDescription());
		}
	}

	/**
	 * Check if exist in some warc a html related with the url in databean
	 * @param previousWarcBean
	 * @return
	 */
	private boolean checkExistPreviousWarcBean(DataBean previousWarcBean) {
		boolean existPreviousWarcBean = false;
		if (previousWarcBean != null && 
				previousWarcBean.getTypeDS() != null
					&& previousWarcBean.getTypeDS().equals(WarcDS.DS_TYPE)) {
				existPreviousWarcBean = true;
		}
		return existPreviousWarcBean;
	}
		
}
