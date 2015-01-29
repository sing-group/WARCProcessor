package com.warcgenerator.gui.components;

import javax.swing.JTextPane;

import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.util.locale.LocaleChangeEvent;
import com.warcgenerator.gui.util.locale.LocaleChangeListener;

@SuppressWarnings("serial")
public class CustomTextPane extends JTextPane implements LocaleChangeListener {	
	@Override
	public void localeChanged(LocaleChangeEvent e) {
		this.setText(Messages.getString(this.getName()));
	}
}
