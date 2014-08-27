package com.warcgenerator.gui.components;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class CustomCardLayout extends CardLayout {
	private JPanel parent;

	public CustomCardLayout() {
		super();
	}

	public JPanel getCurrentPanel(JPanel parent) {
		JPanel card = null; 
		
		System.out.println("parent es " + parent);
		for (Component comp : parent.getComponents()) {
			if (comp.isVisible() == true) {
				card = (JPanel) comp;
				System.out.println(card.getName());
			}
		}
		return card;
	}
}
