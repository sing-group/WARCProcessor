package com.warcgenerator.core.exception.config;


/**
 * Exception when the application is not able to save
 * the app configuration
 * @author Miguel Callon
 *
 */
public class PathNotFoundSaveAppConfigException extends ConfigException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PathNotFoundSaveAppConfigException() {
		super();
	}
	
	public PathNotFoundSaveAppConfigException(Throwable e) {
		super(e);
	}
	
	public PathNotFoundSaveAppConfigException(String message) {
		super(message);
	}
}
