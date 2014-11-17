package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

@SuppressWarnings("serial")
public class DSAsisstantLangBackAction 
	extends AbstractAction implements Observer {	

	@SuppressWarnings("unused")
	private WarcGeneratorGUI view;
	@SuppressWarnings("unused")
	private IAppLogic logic;
	
	private JPanel parentAssistant;
	
	public DSAsisstantLangBackAction(IAppLogic logic,
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

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
