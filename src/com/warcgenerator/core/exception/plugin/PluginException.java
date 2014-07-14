package com.warcgenerator.core.exception.plugin;

import com.warcgenerator.core.exception.WarcException;

public class PluginException extends WarcException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PluginException() {
		super();
	}
	
	public PluginException(Throwable e) {
		super(e);
	}
}
