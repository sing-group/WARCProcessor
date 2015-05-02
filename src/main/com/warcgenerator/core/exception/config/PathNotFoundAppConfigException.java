package com.warcgenerator.core.exception.config;


/**
 * Exception when the application is not able to save
 * the app configuration
 * @author Miguel Callon
 *
 */
public class PathNotFoundAppConfigException extends ConfigException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PathNotFoundAppConfigException() {
		super();
	}
	
	public PathNotFoundAppConfigException(Throwable e) {
		super(e);
	}
	
	public PathNotFoundAppConfigException(String message) {
		super(message);
	}
}
