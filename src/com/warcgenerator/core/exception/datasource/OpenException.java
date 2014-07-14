package com.warcgenerator.core.exception.datasource;

/**
 * Exception when the read pointer is not able to open the file
 * @author Miguel Callon
 */
public class OpenException extends DSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OpenException(Throwable e) {
		super(e);
	}
}
