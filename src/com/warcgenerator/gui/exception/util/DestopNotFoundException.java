package com.warcgenerator.gui.exception.util;


/**
 * Exception when there is not able to find a explorer to open a file
 * @author Miguel Callon
 *
 */
public class DestopNotFoundException extends UtilException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DestopNotFoundException() {
		super();
	}
	
	public DestopNotFoundException(Throwable e) {
		super(e);
	}
	
	public DestopNotFoundException(String message) {
		super(message);
	}
	
}
