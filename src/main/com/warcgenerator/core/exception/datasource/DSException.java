package com.warcgenerator.core.exception.datasource;

import com.warcgenerator.core.exception.WarcException;

public class DSException extends WarcException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DSException() {
		super();
	}
	
	public DSException(Throwable e) {
		super(e);
	}
	
	public DSException(String message) {
		super(message);
	}
}
