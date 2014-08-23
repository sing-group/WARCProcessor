package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;

public class DSAsisstantStep2Action extends AbstractAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;

	public DSAsisstantStep2Action(IAppLogic logic, WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfig = (DataSourceConfig) Session
				.get(Constants.DATASOURCE_FORM_SESSION_KEY);

		DSAssistantStep2Panel nextPanel = new DSAssistantStep2Panel(logic, view);
		nextPanel.setTableModel(dsConfig.getCustomParams());

		view.loadMainPanel(nextPanel);
	}

}
