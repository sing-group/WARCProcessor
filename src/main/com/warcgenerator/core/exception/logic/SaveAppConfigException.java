package com.warcgenerator.core.exception.logic;

/**
 * Exception when the application is not able to load
 * the data source configuration
 * @author Miguel Callon
 *
 */
public class SaveAppConfigException extends LogicException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveAppConfigException() {
		super();
	}
	
	public SaveAppConfigException(Throwable e) {
		super(e);
	}
}
