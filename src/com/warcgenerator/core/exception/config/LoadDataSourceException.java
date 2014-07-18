package com.warcgenerator.core.exception.config;

/**
 * Exception when the application is not able to load
 * the data source configuration
 * @author Miguel Callon
 *
 */
public class LoadDataSourceException extends ConfigException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadDataSourceException() {
		super();
	}
	
	public LoadDataSourceException(Throwable e) {
		super(e);
	}
}
