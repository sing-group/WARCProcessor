package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
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
		logic.loadAppConfig(path);
		view.buildTree();
		view.selectFirstSelectionableItem();
		JOptionPane.showMessageDialog(view.getMainFrame(), 
				"La configuracion se ha cargado correctamente");
	}
}
