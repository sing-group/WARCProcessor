package com.warcgenerator.core.helper;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.common.net.InternetDomainName;
import com.warcgenerator.core.config.Constants;

import edu.uci.ics.crawler4j.url.URLCanonicalizer;

public class FileHelper {
	private static FileFilter generalFileFilter;

	private static Logger logger = Logger.getLogger(FileHelper.class);

	/**
	 * Check if a file is readable
	 * 
	 * @param path
	 *            Path to file
	 * @return True if it is readable
	 */
	public static boolean checkIfExists(String path) {
		return Files.isReadable(FileSystems.getDefault().getPath(path)
				.getParent());
	}

	/**
	 * Create directories from an input array
	 * 
	 * @param dirs
	 *            to create
	 */
	public static void createDirs(String[] dirs) {
		for (String dir : dirs) {
			if (dir != null) {
				File f = new File(dir);
				f.mkdirs();
			}
		}
	}

	/**
	 * Remove dirs from an input array
	 * 
	 * @param dirs
	 *            to remove
	 */
	public static void removeDirsIfExist(String[] dirs) {
		for (String dir : dirs) {
			File f = new File(dir);
			logger.info("Deleting dir: " + dir);
			try {
				FileUtils.deleteDirectory(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Parse url to get only its domain
	 * 
	 * @param url
	 *            URL
	 * @return url without params
	 */
	public static String getURLWithoutParams(String url) {
		String domain = url;
		URL myUrl = null;
		try {
			myUrl = new URL(url);
			// Remove http://domain.com/ <- this last '/'
			if (myUrl.getFile().length() > 1) {
				domain = domain.substring(0, domain.indexOf(myUrl.getFile()));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return domain;
	}

	/**
	 * Get the DomainName from a URL
	 * 
	 * @param url
	 *            URL
	 * @return domainName The domain name from the URL
	 */
	public static String getDomainNameFromURL(String url) {
		String privateDomain = "";
		try {
			privateDomain = InternetDomainName.from(url).topPrivateDomain()
					.name();
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
	 * Used to avoid problems like "http://domain.es", "http://domain.es/" and "domain.es"
	 * 
	 * @param urlStr URL
	 * @return urlStr normalized
	 */
	public static String normalizeURL(String urlStr) {
		sun.net.URLCanonicalizer urlCanonicalizer = new sun.net.URLCanonicalizer();

		// Normalize
		if (urlStr.endsWith("/"))
			urlStr = urlStr.substring(0, urlStr.length() - 1);

		// Given a possibly abbreviated URL (missing a protocol name, typically),
		// this method's job is to transform that URL into a canonical form,
		// by including a protocol name and additional syntax, if necessary.
		urlStr = urlCanonicalizer.canonicalize(urlStr);

		return urlStr;
	}

	/**
	 * Create fileName from URL
	 * 
	 * @param url
	 *            URL
	 * @return name of file
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
	 * 
	 * @param url
	 *            Domain
	 * @return filename with url domain and output file extension
	 */
	public static String getOutputFileName(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				FileHelper.getFileNameFromURL(FileHelper
						.getDomainNameFromURL(url))).append(".")
				.append(Constants.outputCorpusFileExtension);
		return sb.toString();
	}

	/**
	 * Get basic FileFilter
	 * 
	 * @return filter of FileFilter
	 */
	public static FileFilter getGeneralFileFilter() {
		if (generalFileFilter == null) {
			generalFileFilter = new FileFilter() {
				@Override
				public boolean accept(File file) {
					return !file.isHidden();
				}
			};
		}

		return generalFileFilter;
	}
}
