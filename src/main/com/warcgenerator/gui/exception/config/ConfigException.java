package com.warcgenerator.gui.exception.config;

import com.warcgenerator.gui.exception.WarcGUIException;

/**
 * Exception when there is something wrong in the configuration
 * @author Miguel Callon
 *
 */
public class ConfigException extends WarcGUIException {

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
	
	public ConfigException(String message) {
		super(message);
	}
	
}
