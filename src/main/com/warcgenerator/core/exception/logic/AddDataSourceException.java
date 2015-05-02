package com.warcgenerator.core.exception.logic;

/**
 * Exception when the application is not able to load
 * the data source configuration
 * @author Miguel Callon
 *
 */
public class AddDataSourceException extends LogicException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddDataSourceException() {
		super();
	}
	
	public AddDataSourceException(Throwable e) {
		super(e);
	}
}
