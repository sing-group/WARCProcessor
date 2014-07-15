package com.warcgenerator.datasources.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Bean to store data about a Corpus file
 * @author Miguel Callon
 */
public class CorpusFileBean {
	private Iterator<File> spamFiles;
	private Iterator<File> hamFiles;
	private Spam spam;
	private Ham ham;

	public CorpusFileBean(String filePath) {
		String spamPath = filePath + File.separator + "_spam_";
		String hamPath = filePath + File.separator + "_ham_";
		spam = new Spam();
		ham = new Ham();
		
		List<File> spamFilesList = new ArrayList<File>();
		List<File> hamFilesList = new ArrayList<File>();

		File f = new File(spamPath);
		for (File faux : f.listFiles()) {
			System.out.println("spam: " + faux.toString());
			spamFilesList.add(faux);
		}
		f = new File(hamPath);
		for (File faux : f.listFiles()) {
			hamFilesList.add(faux);
		}
		spamFiles = spamFilesList.iterator();
		hamFiles = hamFilesList.iterator();
		
		System.out.println("spam file: " + spamPath + "\n");
		System.out.println("ham file: " + hamPath + "\n");
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
