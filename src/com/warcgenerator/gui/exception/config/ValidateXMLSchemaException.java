package com.warcgenerator.gui.exception.config;

import com.warcgenerator.gui.exception.WarcGUIException;

/**
 * Exception when there is something wrong in the validation of
 * the XML configuration against XSD
 * 
 * @author Miguel Callon
 *
 */
public class ValidateXMLSchemaException extends WarcGUIException {

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
