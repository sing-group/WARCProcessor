package com.warcgenerator.core.exception.config.validation;

import com.warcgenerator.core.exception.config.ConfigException;

/**
 * Exception when the application is not able to load
 * the data source configuration
 * @author Miguel Callon
 *
 */
public class RatioQuantityUnexpectedValueException extends ConfigException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RatioQuantityUnexpectedValueException() {
		super();
	}
	
	public RatioQuantityUnexpectedValueException(Throwable e) {
		super(e);
	}
	
	public RatioQuantityUnexpectedValueException(String message) {
		super(message);
	}
}
