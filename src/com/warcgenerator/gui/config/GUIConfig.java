package com.warcgenerator.gui.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.gui.common.Constants;

/**
 * General configuration.
 * 
 * This class must be builded from a XML properties file.
 * 
 * @author Miguel Callon
 */
public class GUIConfig {
	private List<String> recentConfigFiles;

	public GUIConfig() {
		recentConfigFiles = new ArrayList<String>();
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
	
	public List<String> getRecentConfigFiles() {
		return recentConfigFiles;
	}

	public void setRecentConfigFiles(List<String> recentConfigFiles) {
		this.recentConfigFiles = recentConfigFiles;
	}
	
	public void addRecentConfigFile(String configFilePath) {
		if (recentConfigFiles.contains(configFilePath)) {
			recentConfigFiles.remove(configFilePath);
			recentConfigFiles.add(configFilePath);
		} else {
			if (recentConfigFiles.size() >= 
					Constants.NUM_MAX_RECENT_CONFIG_FILES) {
				// Remove the oldest value
				recentConfigFiles.remove(recentConfigFiles.size());
				// Add new value
				recentConfigFiles.add(configFilePath);
			} else {
				recentConfigFiles.add(configFilePath);
			}
		}
	}
	
	public List<String> getRecentConfigFilesReversed() {
		List<String> reversedArray = new ArrayList<String>();
		for (String text:recentConfigFiles) {
			reversedArray.add(0, text);
		}
		return reversedArray;
	}
}