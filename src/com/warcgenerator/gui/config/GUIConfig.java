package com.warcgenerator.gui.config;

import java.util.HashSet;
import java.util.Set;

/**
 * General configuration.
 * 
 * This class must be builded from a XML properties file.
 * 
 * @author Miguel Callon
 */
public class GUIConfig {
	private Set<String> recentConfigFiles;

	public GUIConfig() {
		recentConfigFiles = new HashSet<String>();
	}
		
	public String toString() {
		StringBuffer sb = new StringBuffer("-- GUI-Config --");
		
		System.out.println("Recent config files:");
		for (String configFile: recentConfigFiles) {
			sb.append("-> Config File Path:  ").append(configFile).append("\n");
		}
			
		return sb.toString();
	}
	
	public boolean validate() {
		// Check if ratioQuantity is bigger than numSites
		/*if (!ratioIsPercentage && numSites < ratioSpam) {
			throw new RatioQuantityUnexpectedValueException();
		}*/
		
		return true;
	}
	
	public Set<String> getRecentConfigFiles() {
		return recentConfigFiles;
	}

	public void setRecentConfigFiles(Set<String> recentConfigFiles) {
		this.recentConfigFiles = recentConfigFiles;
	}
	
	public void addRecentConfigFile(String configFilePath) {
		recentConfigFiles.add(configFilePath);
	}
}