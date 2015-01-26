package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * Start an empty general config.
 * 
 * @author Miguel Callon
 *
 */
public class CreateNewConfigAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WarcGeneratorGUI view;
	private IAppLogic logic;

	public CreateNewConfigAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (view.getMntmSaveCG().isEnabled()) {
			int userSelection = JOptionPane.showConfirmDialog(view.getMainFrame(), 
					"Existen cambios no guardados. ¿Está seguro que desea continuar?",
					"Elija una opción", JOptionPane.YES_NO_OPTION);
			if (userSelection == JOptionPane.OK_OPTION) {
				loadNewAppConfig();
			}
		} else {
			loadNewAppConfig();
		}
		
	}
	
	private void loadNewAppConfig() {
		logic.loadNewAppConfig();
		
		view.buildTree();
		view.loadRecentFiles();
		view.selectFirstSelectionableItem();
	}
}
