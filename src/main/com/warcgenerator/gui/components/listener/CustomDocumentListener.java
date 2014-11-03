package com.warcgenerator.gui.components.listener;

import java.awt.Component;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class CustomDocumentListener implements
	DocumentListener {
	private WarcGeneratorGUI view;
	private Component component;
	
	public CustomDocumentListener(WarcGeneratorGUI view,
			Component component) {
		this.view = view;
		this.component = component;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
			
	}


}
