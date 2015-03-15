package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.warcgenerator.core.logic.AppLogic;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * Load an exist general config.
 * 
 * @author Miguel Callon
 *
 */
@SuppressWarnings("serial")
public class LoadAppConfigAction extends AbstractAction implements Observer {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private File fileToSave;

	/**
	 * Default constructor
	 * 
	 * @param logic
	 *            @type of IAppLogic
	 * @param view
	 *            @type of WarcGeneratorGUI
	 */
	public LoadAppConfigAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;
		logic.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to load");

		fileChooser.setFileFilter(new FileNameExtensionFilter(Messages
				.getString(Constants.FILTER_FILE_CFG_DESCRIPTION)
				+ " (."
				+ Constants.FILE_CFG_EXTENSION + ")",
				Constants.FILE_CFG_EXTENSION));

		int userSelection = fileChooser.showOpenDialog(view.getMainFrame());
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
			logic.loadAppConfig(fileToSave.getAbsolutePath());
		}
	}

	@Override
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			String message = ((LogicCallback) logicCallback).getMessage();
			if (message.equals(AppLogic.APP_CONFIG_LOADED_CALLBACK)) {
				// Reload tree
				view.buildTree();
				view.loadRecentFiles();

				// Remove recent changes from the session
				Session.add(Constants.FORM_MODIFIED_SESSION_KEY, new Boolean(
						false));

				view.selectFirstSelectionableItem();
				JOptionPane
						.showMessageDialog(
								view.getMainFrame(),
								Messages.getString("LoadAppConfigAction.confirmLoadConfiguration"),
								Messages.getString("GeneralDialog.info.title.text"),
								JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
