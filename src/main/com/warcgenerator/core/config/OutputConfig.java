package com.warcgenerator.core.config;

/**
 * File with the output configuration
 * 
 * @author Miguel Callon
 * 
 */
public class OutputConfig {
	private String outputDir;
	
	/**
	 * Default constructor
	 */
	public OutputConfig() {
		
	}
	
	/**
	 * Create a OutputConfig with an outputDir given
	 * @param outputDir
	 */
	public OutputConfig(String outputDir) {
		this.outputDir = outputDir;
	}
	
	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
}
