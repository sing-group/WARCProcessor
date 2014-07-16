package com.warcgenerator.core.helper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.common.net.InternetDomainName;

public class FileHelper {
	/**
	 * Create directories from an input array
	 */
	public static void createDirs(String[] dirs) {
		for (String dir:dirs) {
			File f = new File(dir);
			f.mkdirs();
		}
	}
	/**
	 * Get the DomainName from a URL
	 * @param url
	 * @return
	 */
	public static String getDomainNameFromURL(String url) {
		String domain = "";
		URL myUrl = null;
		try {
			myUrl = new URL(url);
			domain = myUrl.getHost();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String privateDomain = "";
		try {
			privateDomain =
				InternetDomainName.from(domain).topPrivateDomain().name();
		} catch (IllegalArgumentException ex) {
			String urlTmp = url.replaceAll("http://", "");
			urlTmp = urlTmp.replaceAll("https://", "");
			urlTmp = urlTmp.replace("?", "/?");
			if (urlTmp.indexOf("/") != -1) {
				urlTmp = urlTmp.substring(0, urlTmp.indexOf("/"));
			}
			
			privateDomain = urlTmp; 
		}
		return privateDomain;
	}
	
	/**
	 * Create fileName from URL
	 */
	public static String getFileNameFromURL(String url) {
		String fileName = url;
		// Replace slash by undercore
		fileName = fileName.replace("/", "_");
		fileName = fileName.replace(":", "_");
		fileName = fileName.replace(".", "_");
		return fileName;
	}
}
