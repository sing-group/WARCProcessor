package com.warcgenerator.core.plugin.webcrawler;

import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.Country;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.warc.WarcDS;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.LangFilterHelper;
import com.warcgenerator.core.helper.OutputHelper;
import com.warcgenerator.core.task.generateCorpus.state.GenerateCorpusState;

public class WebCrawlerHandler implements IWebCrawlerHandler {
	private AppConfig config;
	private IDataSource warcDS;
	private IDataSource domainsLabeledDS;
	private IDataSource domainsNotFoundDS;
	private boolean isSpam;
	private Map<String, DataBean> urls;
	private Set<String> urlsActive;
	private Set<String> urlsNotActive;
	private GenerateCorpusState generateCorpusState;

	private static Logger logger = Logger.getLogger
            (WebCrawlerHandler.class);
	
	public WebCrawlerHandler(AppConfig config, boolean isSpam,
			IDataSource domainsNotFoundDS, IDataSource domainsLabeledDS,
			IDataSource warcDS, Map<String, DataBean> urls,
			Set<String> urlsActive, Set<String> urlsNotActive, GenerateCorpusState generateCorpusState) {
		this.config = config;
		this.isSpam = isSpam;
		this.warcDS = warcDS;
		this.domainsLabeledDS = domainsLabeledDS;
		this.domainsNotFoundDS = domainsNotFoundDS;
		this.urls = urls;
		this.urlsActive = urlsActive;
		this.urlsNotActive = urlsNotActive;
		this.generateCorpusState = generateCorpusState;
	}

	public void handle(HtmlParseData htmlParseData) {
		
		String url = FileHelper.normalizeURL(htmlParseData.getUrl());
		
		// Look for Warc Bean containing html of the urls
		DataBean previousWarcBean = urls.get(url);
		boolean existPreviousWarcBean = checkExistPreviousWarcBean(previousWarcBean);
		
		if (htmlParseData.getUrl() != null
				&& htmlParseData.getHttpStatus() == HttpStatus.SC_OK) {
			// Es un fichero de tipo warc
			// Busca el fichero warc al que pertenece la url
			DataBean bean = new DataBean();
			if (previousWarcBean != null) {
				bean.setDsConfig(previousWarcBean.getDsConfig());
			}
			bean.setData(htmlParseData.getHtml());
			bean.setUrl(htmlParseData.getUrl());
			bean.setSpam(this.isSpam);

			// If download again is not active then, if exists,
			// use the information available in warc datasources
			if (existPreviousWarcBean &&
				!config.getDownloadAgain()) {
					bean = previousWarcBean;
			}
			
			// Language filter
			try {
				logger.info("URL: " + bean.getUrl()); 
				if (bean.getDsConfig() == null ||
						LangFilterHelper.checkLanguageAllowed((String)bean.getData(),
						bean.getDsConfig().getCountryList())) {

					// Check if the url has data
					if (bean.getData() != null) {
						warcDS.write(bean);
						OutputHelper.writeLabeled(domainsLabeledDS, htmlParseData.getUrl(),
								this.isSpam);
						generateCorpusState.incDomainsCorrectlyLabeled(this.isSpam);
					} else {
						logger.info("URL: " + bean.getUrl() + " doesn't have data.");
					}
						
					urlsActive.add(htmlParseData.getUrl());
				} else {
					// TODO Write in some output file instead of the log
					logger.info("URL Filtered. Available:");
					StringBuffer sb = new StringBuffer();
					for(Country country:bean.getDsConfig().getCountryList()) {
						sb.append(country.getName()).append(" ");
					}
					logger.info(sb.toString());
				}
			} catch (Exception e) {
				throw new DSException(e);
			}
		} else if (htmlParseData.getUrl() != null &&
				//existPreviousWarcBean &&
				(htmlParseData.getHttpStatus() == HttpStatus.SC_MOVED_PERMANENTLY ||
				htmlParseData.getHttpStatus() == HttpStatus.SC_MOVED_TEMPORARILY)) {
		
				if (!config.getFollowRedirect()) {
					if (!existPreviousWarcBean) {
						OutputHelper.writeNotFound(domainsNotFoundDS,
						htmlParseData.getUrl(), htmlParseData.getHttpStatus(),
						htmlParseData.getHttpStatusDescription());
					} else {
						// Check if the url has data
						if (previousWarcBean.getData() != null) {
							warcDS.write(previousWarcBean);
							OutputHelper.writeLabeled(domainsLabeledDS, htmlParseData.getUrl(),
									this.isSpam);
						} else {
							logger.info("URL: " + previousWarcBean.getUrl() + " doesn't have data.");
						}
					}
				}
			
				// Opcion 2
				/*
				if (config.getFollowRedirect()) {
					OutputHelper.writeNotFound(domainsNotFoundDS,
					htmlParseData.getUrl(), htmlParseData.getHttpStatus(),
					htmlParseData.getHttpStatusDescription());
				} else {
					if (!existPreviousWarcBean || config.getOnlyActiveSites()) {
						OutputHelper.writeNotFound(domainsNotFoundDS,
						htmlParseData.getUrl(), htmlParseData.getHttpStatus(),
						htmlParseData.getHttpStatusDescription());
					}
					urlsNotActive.add(htmlParseData.getUrl());
				}*/
					
		} else {
			urlsNotActive.add(htmlParseData.getUrl());
			
			if (!existPreviousWarcBean) {
				OutputHelper.writeNotFound(domainsNotFoundDS,
						htmlParseData.getUrl(), htmlParseData.getHttpStatus(),
						htmlParseData.getHttpStatusDescription());
			}
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
