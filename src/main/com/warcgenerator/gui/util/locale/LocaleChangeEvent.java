package com.warcgenerator.gui.util.locale;

import java.util.EventObject;
import java.util.Locale;

@SuppressWarnings("serial")
public class LocaleChangeEvent extends EventObject {
	public LocaleChangeEvent(Object source) {
		super(source);
	}

	public LocaleChangeEvent(Object source, Locale locale) {
		super(source);
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	private Locale locale;
}