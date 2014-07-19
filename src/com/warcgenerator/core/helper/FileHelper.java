package com.warcgenerator.core.helper;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.google.common.net.InternetDomainName;
import com.warcgenerator.core.config.Constants;

public class FileHelper {
	private static FileFilter generalFileFilter; 
	
	/**
	 * Create directories from an input array
	 * @param dirs to create
	 */
	public static void createDirs(String[] dirs) {
		for (String dir:dirs) {
			File f = new File(dir);
			f.mkdirs();
		}
	}
	
	/**
	 * Remove dirs from an input array
	 * @param dirs to remove
	 */
	public static void removeDirsIfExist(String[] dirs) {
		for (String dir:dirs) {
			File f = new File(dir);
			System.out.println("Deleting dir: " + dir);
			try {
				FileUtils.deleteDirectory(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	/**
	 * Get a file output name from a url domain 
	 * @param url Domain
	 * @return filename with url domain and output file extension
	 */
	public static String getOutputFileName(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append(FileHelper.getFileNameFromURL(
				FileHelper.getDomainNameFromURL(url)))
		.append(".")
		.append(Constants.outputCorpusFileExtension);
		return sb.toString();
	}
	
	public static FileFilter getGeneralFileFilter() {
		if (generalFileFilter == null) {
			generalFileFilter = new FileFilter() {
			    @Override
			    public boolean accept(File file) {
			        return !file.isHidden();
			    }};
		}
		
		return generalFileFilter;
		
		/*return new FileFilter() {
		    @Override
		    public boolean accept(File file) {
		        return !file.isHidden();
		    }};*/
	}
}
