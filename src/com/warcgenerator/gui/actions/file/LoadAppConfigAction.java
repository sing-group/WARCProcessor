package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.warcgenerator.core.logic.AppLogic;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.helper.GUIConfigHelper;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class LoadAppConfigAction extends CustomAction implements Observer {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private File fileToSave;

	public LoadAppConfigAction(IAppLogic logic, WarcGeneratorGUI view) {
		super(view);
		this.view = view;
		this.logic = logic;
		logic.addObserver(this);
	}

	@Override
	public void action(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to load");

		int userSelection = fileChooser.showOpenDialog(view.getMainFrame());
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
			
			// Add config file path
			GUIConfig guiConfig = (GUIConfig) Session
					.get(Constants.GUI_CONFIG_SESSION_KEY);
			guiConfig.addRecentConfigFile(fileToSave.getAbsolutePath());
			GUIConfigHelper.persistConfig(Constants.DEFAULT_GUI_CONFIG_XML,
					guiConfig);
			
			logic.loadAppConfig(fileToSave.getAbsolutePath());
		}
	}

	@Override
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			String message = ((LogicCallback) logicCallback).getMessage();
			if (this.isCurrentAction() && 
					message.equals(AppLogic.APP_CONFIG_LOADED_CALLBACK)) {
				// Reload tree
				view.buildTree();
				view.loadRecentFiles();
				view.selectFirstSelectionableItem();
				JOptionPane.showMessageDialog(view.getMainFrame(), 
						"La configuracion se ha cargado correctamente");
			}
		}
	}

}
