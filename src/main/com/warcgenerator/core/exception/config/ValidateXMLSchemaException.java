package com.warcgenerator.core.exception.config;

import com.warcgenerator.core.exception.WarcException;

/**
 * Exception when there is something wrong in the validation of
 * the XML configuration against XSD
 * 
 * @author Miguel Callon
 *
 */
public class ValidateXMLSchemaException extends WarcException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidateXMLSchemaException() {
		super();
	}
	
	public ValidateXMLSchemaException(Throwable e) {
		super(e);
	}
	
}
