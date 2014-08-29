package com.warcgenerator.gui.config;

import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

/**
 * General configuration.
 * 
 * This class must be builded from a XML properties file.
 * 
 * @author Miguel Callon
 */
public class GUIConfig {
	private Vector<String> recentConfigFiles;

	public GUIConfig() {
		recentConfigFiles = new Vector<String>();
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
	
	public Vector<String> getRecentConfigFiles() {
		return recentConfigFiles;
	}

	public void setRecentConfigFiles(Vector<String> recentConfigFiles) {
		this.recentConfigFiles = recentConfigFiles;
	}
	
	public void addRecentConfigFile(String configFilePath) {
		recentConfigFiles.remove(configFilePath);
		recentConfigFiles.add(0, configFilePath);
	}
}