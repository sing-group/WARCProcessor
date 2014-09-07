package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.logic.AppLogic;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.helper.GUIConfigHelper;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class LoadRecentConfigAction extends AbstractAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private String path;
	private boolean showConfirm;

	public LoadRecentConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			String path, boolean showConfirm) {
		this.view = view;
		this.logic = logic;
		this.path = path;
		this.showConfirm = showConfirm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
}
