package com.warcgenerator.gui.components;

import javax.swing.JMenu;

import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.util.locale.LocaleChangeEvent;
import com.warcgenerator.gui.util.locale.LocaleChangeListener;

@SuppressWarnings("serial")
public class CustomMenu extends JMenu implements LocaleChangeListener {
	public CustomMenu() {
		super();
	}

	@Override
	public void localeChanged(LocaleChangeEvent e) {
		String text = Messages.getString(this.getName());
		this.setText(text);
		if (text.length() > 0)
			this.setMnemonic(text.toUpperCase().toCharArray()[0]);
	}
}
