package com.warcgenerator.gui.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	// //////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	// //////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}

	// //////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	// //////////////////////////////////////////////////////////////////////////
	private static final String BUNDLE_NAME = "com.warcgenerator.gui.resources.messages"; //$NON-NLS-1$

	private static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
	}

	// //////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	// //////////////////////////////////////////////////////////////////////////
	public static String getString(String key) {
		try {
			ResourceBundle bundle = loadBundle();
			if (key != null) {
				if (!bundle.containsKey(key)) return key;
				return bundle.getString(key);
			} else
				return "<Located text has not been found>";
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return "!" + key + "!";
		}
	}
}
