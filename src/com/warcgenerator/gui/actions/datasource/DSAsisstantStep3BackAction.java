package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAsisstantStep3BackAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private Action dsAssistantStep2Action;
	
	public DSAsisstantStep3BackAction(IAppLogic logic,
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
		
		dsAssistantStep2Action = new 
				DSAsisstantStep2Action(logic, view);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dsAssistantStep2Action.actionPerformed(e);
	}
}
