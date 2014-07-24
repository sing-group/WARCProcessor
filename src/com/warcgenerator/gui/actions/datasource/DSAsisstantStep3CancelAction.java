package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantCreatePanel;

public class DSAsisstantStep3CancelAction 
	extends AbstractAction implements Observer {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WarcGeneratorGUI view;
	
	public DSAsisstantStep3CancelAction(WarcGeneratorGUI view
			) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		view.loadMainPanel(new DSAssistantCreatePanel(view));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
