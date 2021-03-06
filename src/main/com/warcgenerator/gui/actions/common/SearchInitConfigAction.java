package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.InitConfigDialog;

@SuppressWarnings("serial")
public class SearchInitConfigAction extends AbstractAction {
	private WarcGeneratorGUI view;
	@SuppressWarnings("unused")
	private IAppLogic logic;
	private InitConfigDialog initConfigDialog;
	
	public SearchInitConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			InitConfigDialog initConfigDialog) {
		this.view = view;
		this.logic = logic;
		this.initConfigDialog = initConfigDialog;
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
			File fileToSave = fileChooser.getSelectedFile();
			initConfigDialog.addFirstConfigFile(fileToSave);
		}
	}
}
