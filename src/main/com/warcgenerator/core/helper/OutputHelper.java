package com.warcgenerator.core.helper;

import org.apache.http.HttpStatus;

import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;

public class OutputHelper {
	public static void writeLabeled(IDataSource domainsLabeledDS,
			String url, boolean isSpam) {
		StringBuffer sb = new StringBuffer();
		// TODO To param
		String type = isSpam ? "spam" : "ham";
		sb.append(url).append(" ").append(type);
		DataBean data = new DataBean(sb.toString());
		domainsLabeledDS.write(data);
	}
	
	public static void writeNotFound(IDataSource domainsNotFoundDS,
			String url, int httpStatus, 
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
