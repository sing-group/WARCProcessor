package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAsisstantStep2BackAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private Action dsAsisstantStep1Action;
	
	public DSAsisstantStep2BackAction(IAppLogic logic,
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
		
		dsAsisstantStep1Action = new DSAssistantCreateNewDSAction(
				logic, view);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dsAsisstantStep1Action.actionPerformed(e);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
