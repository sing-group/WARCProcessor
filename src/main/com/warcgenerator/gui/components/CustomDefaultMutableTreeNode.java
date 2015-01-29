package com.warcgenerator.gui.components;

import javax.swing.tree.DefaultMutableTreeNode;

import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.util.locale.LocaleChangeEvent;
import com.warcgenerator.gui.util.locale.LocaleChangeListener;

@SuppressWarnings("serial")
public class CustomDefaultMutableTreeNode extends DefaultMutableTreeNode implements
		LocaleChangeListener {
	public CustomDefaultMutableTreeNode() {
		super();
	}

	@Override
	public void localeChanged(LocaleChangeEvent e) {
		//setUserObject(Messages.getString(this.get));
	}
}
