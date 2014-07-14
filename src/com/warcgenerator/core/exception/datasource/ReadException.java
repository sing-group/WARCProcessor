package com.warcgenerator.core.exception.datasource;


/**
 * Exception when the read pointer is not able to read the file
 * @author Miguel Callon
 */
public class ReadException extends DSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReadException(Throwable e) {
		super(e);
	}
}
