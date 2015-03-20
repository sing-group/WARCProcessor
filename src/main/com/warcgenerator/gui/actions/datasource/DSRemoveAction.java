package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.AppLogic;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
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
public class DSRemoveAction extends AbstractAction implements Observer {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	private DSDetailPanel panel;

	public DSRemoveAction(IAppLogic logic, WarcGeneratorGUI view,
			DataSourceConfig config, DSDetailPanel panel) {
		this.logic = logic;
		this.view = view;
		this.config = config;
		this.panel = panel;

		logic.addObserver(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int dialogResult = JOptionPane.showConfirmDialog(
				view.getMainFrame(),
				Messages.getString("DSRemoveAction.remove.text")
						+ config.getName(),
				Messages.getString("DSRemoveAction.remove.title.text"),
				JOptionPane.OK_CANCEL_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			logic.removeDataSourceConfig(config.getId());
		}
	}

	@Override
	public void update(Observable aPublisher, Object logicCallback) {
		// TODO Auto-generated method stub
		if (panel.isShowing()) {
			String message = ((LogicCallback) logicCallback).getMessage();
			if (message.equals(AppLogic.DATASOURCE_REMOVED_CALLBACK)
					&& ((LogicCallback) logicCallback).getParams()[0]
							.equals(config.getId())) {
				JOptionPane.showMessageDialog(view.getMainFrame(),
						Messages.getString("DSRemoveAction.remove.text")
								+ config.getName(),
						Messages.getString("GeneralDialog.info.title.text"),
						JOptionPane.INFORMATION_MESSAGE);
				view.removeDS(config.getId());
			}
		}
	}

}
