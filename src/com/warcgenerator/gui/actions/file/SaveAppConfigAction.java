package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class SaveAppConfigAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	
	public SaveAppConfigAction(IAppLogic logic,
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig appConfig = logic.getAppConfig();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		int userSelection = fileChooser.showSaveDialog(view.getMainFrame());
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();	
			logic.saveAppConfig(fileToSave.getAbsolutePath());
		}
	}
}
