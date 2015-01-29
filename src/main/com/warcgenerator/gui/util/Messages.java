package com.warcgenerator.gui.util;

import java.beans.Beans;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	////////////////////////////////////////////////////////////////////////////
	private static final String BUNDLE_NAME = "com.warcgenerator.gui.resources.messages"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = loadBundle();
	private static ResourceBundle loadBundle() {
		//return ResourceBundle.getBundle(BUNDLE_NAME);
		return ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	////////////////////////////////////////////////////////////////////////////
	public static String getString(String key) {
		try {
			//ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			ResourceBundle bundle = loadBundle();
			if (key != null)
				return bundle.getString(key);
			return "<Located text has not been found>";
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
}
