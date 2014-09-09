package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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
		logic.loadNewAppConfig();
		
		view.buildTree();
		view.loadRecentFiles();
		view.selectFirstSelectionableItem();
	}
}
