package com.warcgenerator.core.exception.datasource;

/**
 * Exception when the read pointer is not able to close the file
 * @author Miguel Callon
 */
public class CloseException extends DSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CloseException(Throwable e) {
		super(e);
	}
}
