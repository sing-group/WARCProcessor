package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.helper.GUIConfigHelper;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class LoadAppConfigAction extends AbstractAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;

	public LoadAppConfigAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to load");

		int userSelection = fileChooser.showOpenDialog(view.getMainFrame());
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			logic.loadAppConfig(fileToSave.getAbsolutePath());

			// Add config file path
			GUIConfig guiConfig = (GUIConfig) Session
					.get(Constants.GUI_CONFIG_SESSION_KEY);
			guiConfig.addRecentConfigFile(fileToSave.getAbsolutePath());
			GUIConfigHelper.persistConfig(Constants.DEFAULT_GUI_CONFIG_XML,
					guiConfig);

			// Reload tree
			view.buildTree();
			view.selectFirstSelectionableItem();
		}
	}
}
