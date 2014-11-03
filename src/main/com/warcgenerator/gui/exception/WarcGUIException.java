package com.warcgenerator.gui.exception;

public class WarcGUIException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WarcGUIException() {
		super();
	}
	
	public WarcGUIException(Throwable e) {
		super(e);
	}
	
	public WarcGUIException(String message) {
		super(message);
	}
}
