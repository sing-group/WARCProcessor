package com.warcgenerator.core.exception.config;

import com.warcgenerator.core.exception.WarcException;

/**
 * Exception when there is something wrong in the configuration
 * @author Miguel Callon
 *
 */
public class ConfigException extends WarcException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConfigException() {
		super();
	}
	
	public ConfigException(Throwable e) {
		super(e);
	}
	
}
