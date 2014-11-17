package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

@SuppressWarnings("serial")
public class DSAsisstantStep3BackAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel parentAssistant;
	
	public DSAsisstantStep3BackAction(IAppLogic logic,
			WarcGeneratorGUI view,
			JPanel parentAssistant
			) {
		this.view = view;
		this.logic = logic;
		this.parentAssistant = parentAssistant;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		((CustomCardLayout)parentAssistant.getLayout()).
			previous(parentAssistant);
	}
}
