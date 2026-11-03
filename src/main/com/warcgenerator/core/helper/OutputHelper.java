package com.warcgenerator.core.helper;

import org.apache.hc.core5.http.HttpStatus;

import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;

public class OutputHelper {

	/**
	 * Write the Spam and Ham corpus
	 * @param domainsLabeledDS
	 * @param url URL to write in the corpus
	 */
	public static void writeSpamHam(IDataSource domainsLabeledDS,
									String url) {
		DataBean data = new DataBean(url);
		domainsLabeledDS.write(data);
	}

	public static void writeLabeled(IDataSource domainsLabeledDS,
			String url, boolean isSpam) {
		StringBuffer sb = new StringBuffer();
		// TODO To param
		String type = isSpam ? "spam" : "ham";
		sb.append(url).append(" ").append(type);
		DataBean data = new DataBean(sb.toString());
		domainsLabeledDS.write(data);
	}
	
	/**
	 * Key of the output WARC of a URL. There is one per domain and label, as
	 * spam and ham go to different folders.
	 * 
	 * @param url URL to write in the corpus
	 * @param isSpam Label of the URL
	 * @return Key of the output datasource
	 */
	public static String getOutputDSKey(String url, boolean isSpam) {
		return FileHelper.getDomainNameFromURL(url) + (isSpam ? "#spam" : "#ham");
	}

	public static void writeNotFound(IDataSource domainsNotFoundDS,
			String url, Integer httpStatus, 
			String httpHttpStatusDescription) {
		StringBuilder sb = new StringBuilder();
		if (httpStatus == null) {
			sb.append("Unable to connect to: ").append(url);
		} else  {
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
		}
		DataBean data = new DataBean(sb.toString());
		domainsNotFoundDS.write(data);
	}
}
