package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.Action;
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

public class LoadRecentConfigAction extends CustomAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private String path;
	private boolean showConfirm;

	public LoadRecentConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			String path, boolean showConfirm) {
		super(view);
		this.view = view;
		this.logic = logic;
		this.path = path;
		this.showConfirm = showConfirm;
	}

	@Override
	public void action(ActionEvent e) {
		if (showConfirm) {
			int userSelection = JOptionPane.showConfirmDialog(view.getMainFrame(),
					"Esta seguro que desea cargar la configuracion? \n" + path);
			if (userSelection == JOptionPane.YES_OPTION) {
				loadConfig();
			}
		} else {
			loadConfig();
		}
	}
	
	private void loadConfig() {
		// Add config file path
		GUIConfig guiConfig = (GUIConfig) Session
				.get(Constants.GUI_CONFIG_SESSION_KEY);
		guiConfig.addRecentConfigFile(path);
		GUIConfigHelper.persistConfig(Constants.DEFAULT_GUI_CONFIG_XML,
				guiConfig);

		logic.loadAppConfig(path);
		// Load App Config catchs the callback and show a info panel
	}

	@Override
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			String message = ((LogicCallback) logicCallback).getMessage();
			if (this.isCurrentAction() 
					&& message.equals(AppLogic.APP_CONFIG_LOADED_CALLBACK)) {
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
