package com.warcgenerator.gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

@SuppressWarnings("serial")
public class SaveAsAppConfigAction extends AbstractAction implements Observer {
	private WarcGeneratorGUI view;
	private IAppLogic logic;

	public SaveAsAppConfigAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;

		logic.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		fileChooser.setFileFilter(new FileNameExtensionFilter(Messages
				.getString(Constants.FILTER_FILE_CFG_DESCRIPTION)
				+ " (."
				+ Constants.FILE_CFG_EXTENSION + ")",
				Constants.FILE_CFG_EXTENSION));

		int userSelection = fileChooser.showSaveDialog(view.getMainFrame());
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();

			if (!FilenameUtils.getExtension(fileToSave.getAbsolutePath())
					.equals(Constants.FILE_CFG_EXTENSION)) {
				fileToSave = new File(fileToSave.getAbsolutePath() + "."
						+ Constants.FILE_CFG_EXTENSION);
			}

			logic.saveAsAppConfig(fileToSave.getAbsolutePath());
		}
	}

	@Override
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			String message = ((LogicCallback) logicCallback).getMessage();
			if (message.equals(IAppLogic.APP_CONFIG_SAVED_AS_CALLBACK)) {
				JOptionPane.showMessageDialog(view.getMainFrame(),
						"La configuracion se ha guardado con exito.");
				view.loadRecentFiles();
			}
		}
	}
}
