package com.warcgenerator.gui.components.listener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;

public class CustomPropertyChangeListener implements
	PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		JComponent component = (JComponent) e.getSource();

		if (component instanceof JFormattedTextField) {
			if (e.getPropertyName().equals("value")) {
				if (e.getOldValue() != null &&
						!e.getOldValue().equals(e.getNewValue())) {
					Session.add(Constants.FORM_MODIFIED_SESSION_KEY,
							new Boolean(true));	
				}
			}
		}
	}
}
