package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantCreatePanel;

public class DSAsisstantCreateAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	
	public DSAsisstantCreateAction(IAppLogic logic, WarcGeneratorGUI view
			) {
		this.logic = logic;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		view.loadMainPanel(new DSAssistantCreatePanel(logic, view));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
