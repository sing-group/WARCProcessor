package com.warcgenerator.core.exception.datasource;


/**
 * Exception when the read pointer is not able to read the file
 * @author Miguel Callon
 */
public class MissingParamsException extends DSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MissingParamsException(Throwable e) {
		super(e);
	}
	
	public MissingParamsException(String param) {
		super(param);
	}
}
