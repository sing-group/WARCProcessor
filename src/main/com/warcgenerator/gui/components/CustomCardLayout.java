package com.warcgenerator.gui.components;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class CustomCardLayout extends CardLayout {
	public CustomCardLayout() {
		super();
	}

	public JPanel getCurrentPanel(JPanel parent) {
		JPanel card = null; 
		for (Component comp : parent.getComponents()) {
			if (comp.isVisible() == true) {
				card = (JPanel) comp;
			}
		}
		return card;
	}
}
