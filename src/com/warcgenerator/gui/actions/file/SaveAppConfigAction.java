package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.exception.logic.ConfigFilePathIsNullException;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.helper.GUIConfigHelper;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class SaveAppConfigAction
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private SaveAsAppConfigAction saveAsAppConfigAction;
	
	public SaveAppConfigAction(IAppLogic logic,
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
		
		saveAsAppConfigAction =
				new SaveAsAppConfigAction(logic, view);
		// Delete this observer to avoid its update method will be launched
		logic.deleteObserver(saveAsAppConfigAction);
		
		logic.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			logic.saveAppConfig();
		} catch (ConfigFilePathIsNullException ex) {
			saveAsAppConfigAction.actionPerformed(e);
		}
	}

	@Override
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			String message = ((LogicCallback)logicCallback).getMessage();
			if (message.equals(IAppLogic.APP_CONFIG_SAVED_CALLBACK)) {
				JOptionPane.showMessageDialog(view.getMainFrame(), 
						"La configuracion se ha guardado con exito.");
				view.loadRecentFiles();
				view.getMntmSaveCG().setEnabled(false);
			} else if (message.equals(IAppLogic.APP_CONFIG_LOADED_CALLBACK)) {
				view.getMntmSaveCG().setEnabled(false);
			} else if (message.equals(IAppLogic.APP_CONFIG_UPDATED_CALLBACK)) {
				view.getMntmSaveCG().setEnabled(true);
			} else if (message.equals(IAppLogic.APP_CONFIG_SAVED_AS_CALLBACK)) {
				view.getMntmSaveCG().setEnabled(false);
			} else if (message.equals(IAppLogic.DATASOURCE_CREATED_CALLBACK)) {
				view.getMntmSaveCG().setEnabled(true);
			} else if (message.equals(IAppLogic.DATASOURCE_UPDATED_CALLBACK)) {
				view.getMntmSaveCG().setEnabled(true);
			} else if (message.equals(IAppLogic.DATASOURCE_REMOVED_CALLBACK)) {
				view.getMntmSaveCG().setEnabled(true);
			}
		}
	}
}
