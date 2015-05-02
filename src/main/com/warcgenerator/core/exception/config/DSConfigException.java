package com.warcgenerator.core.exception.config;

/**
 * Exception when the application is not able to load
 * the data source configuration
 * @author Miguel Callon
 *
 */
public class DSConfigException extends ConfigException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DSConfigException() {
		super();
	}
	
	public DSConfigException(Throwable e) {
		super(e);
	}
	
	public DSConfigException(String message) {
		super(message);
	}
}
