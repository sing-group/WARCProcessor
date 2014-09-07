package com.warcgenerator.gui.components;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.listener.CustomPropertyChangeListener;

public abstract class CustomJPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomJPanel() {
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				installChangeListeners(getComponents());
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				uninstallChangeListeners(getComponents());
			}
		});
	}

	public void commit() {
		Session.add(Constants.FORM_MODIFIED_SESSION_KEY, new Boolean(false));
	}

	public void rollback() {
		Session.add(Constants.FORM_MODIFIED_SESSION_KEY, new Boolean(false));
	}

	public void uninstallChangeListeners(Component[] components) {
		for (Component component : components) {
			if (component instanceof JPanel) {
				uninstallChangeListeners(((JPanel) component).getComponents());
			} else if (component instanceof JFormattedTextField) {
				JFormattedTextField text = (JFormattedTextField) component;
				for (PropertyChangeListener changeListener : text
						.getPropertyChangeListeners()) {
					text.removePropertyChangeListener(changeListener);
				}
			}
		}
	}

	public void installChangeListeners(Component[] components) {
		for (Component component : components) {
			if (component instanceof JPanel) {
				installChangeListeners(((JPanel) component).getComponents());
			} else if (component instanceof JFormattedTextField) {
				JFormattedTextField text = (JFormattedTextField) component;
				text.addPropertyChangeListener("value",
						new CustomPropertyChangeListener());
			} else if (component instanceof JCheckBox) {
				JCheckBox cb = (JCheckBox) component;
				cb.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						Session.add(Constants.FORM_MODIFIED_SESSION_KEY,
								new Boolean(true));
					}
				});
			}
		}

	}

	public abstract void save();
}
