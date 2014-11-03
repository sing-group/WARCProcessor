package com.warcgenerator.core.exception.config;

/**
 * Exception when the application is not able to load
 * the configuration file
 * @author Miguel Callon
 *
 */
public class LoadParamsException extends ConfigException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadParamsException() {
		super();
	}
	
	public LoadParamsException(Throwable e) {
		super(e);
	}
}
