package com.warcgenerator.gui.actions.generate;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.generate.GenerateCorpusDialog;

@SuppressWarnings("serial")
public class GenerateCorpusAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private GenerateCorpusDialog generateCorpusDialog;
	
	public GenerateCorpusAction(IAppLogic logic,
			WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig appConfig = 
				logic.getAppConfig();
		
		generateCorpusDialog = 
				new GenerateCorpusDialog(logic, view);
		generateCorpusDialog.setSummaryText(appConfig.toString());
		generateCorpusDialog.setVisible(true);
	}
}
