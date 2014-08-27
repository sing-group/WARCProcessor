package com.warcgenerator.gui.components;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.listener.CustomPropertyChangeListener;

public abstract class CustomJPanel extends JPanel {
	public CustomJPanel() {
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				System.out.println("mostrado!!!!!!!!!!!!");
				installChangeListeners();
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				System.out.println("no visibleeeeeeeeeeee!!!!!!!!!!!!");
				uninstallChangeListeners();
			}
		});
	}
	
	public void commit() {
		Session.add(Constants.FORM_MODIFIED_SESSION_KEY,
				new Boolean(false));	
	}
	public void rollback() {
		Session.add(Constants.FORM_MODIFIED_SESSION_KEY,
				new Boolean(false));	
	}
	
	private void uninstallChangeListeners() {
		for (Component component:this.getComponents()) {
			if (component instanceof JFormattedTextField) {
				JFormattedTextField text = (JFormattedTextField)component;
				for (PropertyChangeListener changeListener:
					text.getPropertyChangeListeners()) {
					text.removePropertyChangeListener(changeListener);
				}
			}
		}
	}
	public void installChangeListeners() {
		System.out.println("instalando change listeners");
		
		for (Component component:this.getComponents()) {
			if (component instanceof JFormattedTextField) {
				System.out.println("Encontrado jformattedtextfield");
				
				JFormattedTextField text = (JFormattedTextField)component;
				text.addPropertyChangeListener("value",
						new CustomPropertyChangeListener());
			}
		}
	}
	public abstract void save();
}
