package com.warcgenerator.core.exception.logic;


public class InvalidStateTransitionException extends LogicException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidStateTransitionException() {
		super();
	}
	
	public InvalidStateTransitionException(Throwable e) {
		super(e);
	}
	
	public InvalidStateTransitionException(String message) {
		super(message);
	}
}
