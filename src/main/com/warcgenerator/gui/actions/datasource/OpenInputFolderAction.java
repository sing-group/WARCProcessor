package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.gui.util.FileUtil;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * 
 * @author Miguel Callon
 *
 */
@SuppressWarnings("serial")
public class OpenInputFolderAction extends AbstractAction {
	private DataSourceConfig dsConfig;
	private WarcGeneratorGUI view;

	public OpenInputFolderAction(DataSourceConfig dsConfig,
			WarcGeneratorGUI view) {
		this.dsConfig = dsConfig;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File inputFolder = new File(dsConfig.getFilePath());

		if (inputFolder.exists()) {
			FileUtil.openInDefaultExplorer(inputFolder.getAbsolutePath());
		} else {
			JOptionPane
					.showMessageDialog(
							view.getMainFrame(),
							Messages.getString("OpenInputFolderAction.dirNotFound1.text")
									+ inputFolder.getAbsolutePath()
									+ Messages
											.getString("OpenInputFolderAction.dirNotFound2.text"),
							Messages.getString("GeneralDialog.info.title.text"),
							JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
