package com.warcgenerator.core.exception.datasource;


/**
 * Exception when the read pointer is not able to write the file
 * @author Miguel Callon
 */
public class WriteException extends DSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WriteException(Throwable e) {
		super(e);
	}
}
