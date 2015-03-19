package com.warcgenerator.core.exception.config;

/**
 * Exception when the application is not able to load
 * the loj4j configuration
 * @author Miguel Callon
 *
 */
public class Log4JConfigNotFoundException extends ConfigException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Log4JConfigNotFoundException() {
		super();
	}
	
	public Log4JConfigNotFoundException(Throwable e) {
		super(e);
	}
	
	public Log4JConfigNotFoundException(String message) {
		super(message);
	}
}
