package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.util.FileUtil;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * 
 * @author amparop
 *
 */
@SuppressWarnings("serial")
public class OpenOutputFolderAction extends AbstractAction {
	private IAppLogic logic;
	private WarcGeneratorGUI view;

	public OpenOutputFolderAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.logic = logic;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig config = logic.getAppConfig();

		File outputFolder = new File(config.getOutputConfig().getOutputDir());
		
		if (outputFolder.exists()) {
			FileUtil.openInDefaultExplorer(outputFolder.getAbsolutePath());
		} else {
			JOptionPane.showMessageDialog(view.getMainFrame(),
					Messages.getString("OpenOutputFolder.message1.text")
						+ outputFolder.getAbsolutePath() +
					Messages.getString("OpenOutputFolder.message2.text"));
		}
	}
}
