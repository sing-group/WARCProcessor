package com.warcgenerator.core.exception.datasource;

/**
 * Exception when the read pointer of a datasource has arrived
 * to the end.
 * @author Miguel Callon
 */
public class EOFException extends DSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EOFException() {
		super();
	}
	
	public EOFException(Throwable e) {
		super(e);
	}
	
}
