package com.warcgenerator.gui.components;

import javax.swing.JMenuItem;

import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.util.locale.LocaleChangeEvent;
import com.warcgenerator.gui.util.locale.LocaleChangeListener;

@SuppressWarnings("serial")
public class CustomMenuItem extends JMenuItem implements LocaleChangeListener {
	private String localeToolTipText;
	
	public CustomMenuItem() {
		super();
	}
	
	@Override
	public void localeChanged(LocaleChangeEvent e) {
		if (this.getName() != null) this.setText(Messages.getString(this.getName()));
		if (this.getLocaleToolTipText() != null) this.setToolTipText(Messages.getString(this.getLocaleToolTipText()));
	}

	public String getLocaleToolTipText() {
		return localeToolTipText;
	}

	public void setLocaleToolTipText(String localeToolTipText) {
		this.localeToolTipText = localeToolTipText;
	}
}
