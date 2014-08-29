package com.warcgenerator.gui.components.listener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;

public class CustomPropertyChangeListener implements
	PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println("property : " + e.getPropertyName());
		
		JComponent component = (JComponent) e.getSource();

		if (component instanceof JFormattedTextField) {
			JFormattedTextField textField =
					(JFormattedTextField)component;
			
			if (e.getPropertyName().equals("value")) {
				System.out.println("oldValue: " + e.getOldValue());
				System.out.println("newValue: " + e.getNewValue());
				
				if (e.getOldValue() != null &&
						!e.getOldValue().equals(e.getNewValue())) {
					Session.add(Constants.FORM_MODIFIED_SESSION_KEY,
							new Boolean(true));	
				}
			}
		}
	}
}
