package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSDetailPanel;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * 
 * @author amparop
 *
 */
@SuppressWarnings("serial")
public class DSEnableAction extends AbstractAction {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig dsConfig;
	private DSDetailPanel detailPanel;

	public DSEnableAction(IAppLogic logic, WarcGeneratorGUI view,
			DataSourceConfig dsConfig, DSDetailPanel detailPanel) {
		this.logic = logic;
		this.view = view;
		this.dsConfig = dsConfig;
		this.detailPanel = detailPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!detailPanel.getChckbxEnableDS().isSelected()) {
			int userSelection = JOptionPane.showConfirmDialog(
					view.getMainFrame(),
					Messages.getString("DSEnableAction.disabled.text")
							+ dsConfig.getName() + "?",
					Messages.getString("DSEnableAction.disabled.title.text"),
					JOptionPane.OK_CANCEL_OPTION);

			if (userSelection == JOptionPane.OK_OPTION) {
				dsConfig.setEnabled(detailPanel.getChckbxEnableDS()
						.isSelected());
				JOptionPane
						.showMessageDialog(
								view.getMainFrame(),
								Messages.getString("DSEnableAction.datasource.text")
										+ ": "
										+ dsConfig.getName()
										+ Messages
												.getString("DSEnableAction.datasource.disabled.text"));
				logic.addDataSourceConfig(dsConfig);
			} else {
				detailPanel.getChckbxEnableDS().setSelected(true);
				JOptionPane.showMessageDialog(view.getMainFrame(), Messages
						.getString("DSEnableAction.datasource.cancelled.text"), 
						Messages.getString("GeneralDialog.info.title.text"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			dsConfig.setEnabled(detailPanel.getChckbxEnableDS().isSelected());
			JOptionPane
					.showMessageDialog(
							view.getMainFrame(),
							Messages.getString("DSEnableAction.datasource.text")
									+ ": "
									+ dsConfig.getName()
									+ Messages
											.getString("DSEnableAction.datasource.enabled.text"));
			logic.addDataSourceConfig(dsConfig);
		}

		DSDetailAction modifyAction = new DSDetailAction(logic, view, dsConfig);
		modifyAction.action(e);
		view.updateUI();
	}
}
