package com.warcgenerator.gui.actions.generate;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.generate.GenerateCorpusPanel;

public class GenerateCorpusAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	
	public GenerateCorpusAction(IAppLogic logic,
			WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig appConfig = 
				logic.getAppConfig();
		
		GenerateCorpusPanel nextPanel =
				new GenerateCorpusPanel(logic, view);
		nextPanel.setSummaryText(appConfig.toString());
		
		view.loadMainPanel(nextPanel);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
