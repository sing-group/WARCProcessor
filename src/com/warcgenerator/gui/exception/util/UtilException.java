package com.warcgenerator.gui.exception.util;

import com.warcgenerator.gui.exception.WarcGUIException;

/**
 * Exception when there is not able to find a explorer to open a file
 * @author Miguel Callon
 *
 */
public class UtilException extends WarcGUIException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UtilException() {
		super();
	}
	
	public UtilException(Throwable e) {
		super(e);
	}
	
	public UtilException(String message) {
		super(message);
	}
	
}
