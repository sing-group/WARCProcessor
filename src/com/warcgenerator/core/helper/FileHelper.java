package com.warcgenerator.core.helper;

import java.io.File;

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
		String urlTmp = url.replaceAll("http://", "");
		urlTmp = urlTmp.replaceAll("https://", "");
		urlTmp = urlTmp.replace("?", "/?");
		if (urlTmp.indexOf("/") != -1) {
			urlTmp = urlTmp.substring(0, urlTmp.indexOf("/"));
		}
		return urlTmp;
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
