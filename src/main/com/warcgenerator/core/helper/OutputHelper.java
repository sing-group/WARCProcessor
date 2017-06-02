package com.warcgenerator.core.helper;

import org.apache.http.HttpStatus;

import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

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

	/**
	 * Write the SPAM and HAM corpus files
	 * @param urlSpamList List of spam URLs
	 * @param urlHamList List of ham URLs
	 */
	public static void writeCorpusFiles(List<String> urlSpamList, List<String> urlHamList) {
		try {
			PrintStream spamWriter = new PrintStream(new File("_spam_.txt"));
			PrintStream hamWriter = new PrintStream(new File("_ham_.txt"));

			for (String s: urlSpamList) {
				spamWriter.println(s);
			}
			for (String h: urlHamList) {
				hamWriter.println(h);
			}

			spamWriter.close();
			hamWriter.close();
		} catch (IOException e) {
			System.err.print("Error writing corpus files: " + e.getMessage());
		}
	}

}
