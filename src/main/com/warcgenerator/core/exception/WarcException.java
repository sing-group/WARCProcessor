package com.warcgenerator.core.exception;

public class WarcException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WarcException() {
		super();
	}
	
	public WarcException(Throwable e) {
		super(e);
	}
	
	public WarcException(String message) {
		super(message);
	}
}
