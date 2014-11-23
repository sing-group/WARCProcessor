package com.warcgenerator.datasources.corpus.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.helper.FileHelper;

/**
 * Bean to store data about a Corpus file
 * @author Miguel Callon
 */
public class CorpusFileBean {
	private final String SPAM_DIR = "SpamDir";
	private final String HAM_DIR = "HamDir";
	
	private Iterator<File> spamFiles;
	private Iterator<File> hamFiles;
	private Spam spam;
	private Ham ham;

	public CorpusFileBean(String filePath, DataSourceConfig dsConfig) {
		StringBuilder spamPath = new StringBuilder(filePath).
				append(File.separator).
				append(dsConfig.getCustomParams().get(SPAM_DIR).getValue());
		StringBuilder hamPath = new StringBuilder(filePath).
				append(File.separator).
				append(dsConfig.getCustomParams().get(HAM_DIR).getValue());
		spam = new Spam();
		ham = new Ham();
		
		List<File> spamFilesList = new ArrayList<File>();
		List<File> hamFilesList = new ArrayList<File>();

		File f = new File(spamPath.toString());
		for (File faux : f.listFiles(
				FileHelper.getGeneralFileFilter())) {
			spamFilesList.add(faux);
		}
		f = new File(hamPath.toString());
		for (File faux : f.listFiles(
				FileHelper.getGeneralFileFilter())) {
			hamFilesList.add(faux);
		}
		spamFiles = spamFilesList.iterator();
		hamFiles = hamFilesList.iterator();
	}

	public Spam getSpam() {
		return spam;
	}

	public void setSpam(Spam spam) {
		this.spam = spam;
	}

	public Ham getHam() {
		return ham;
	}

	public void setHam(Ham ham) {
		this.ham = ham;
	}

	public class Spam implements Iterator<File> {
		@Override
		public boolean hasNext() {
			return spamFiles.hasNext();
		}

		@Override
		public File next() {
			// TODO Auto-generated method stub
			return spamFiles.next();
		}

		@Override
		public void remove() {
			spamFiles.remove();
		}
	}

	public class Ham implements Iterator<File> {
		@Override
		public boolean hasNext() {
			return hamFiles.hasNext();
		}

		@Override
		public File next() {
			// TODO Auto-generated method stub
			return hamFiles.next();
		}

		@Override
		public void remove() {
			hamFiles.remove();
		}
	}
}
