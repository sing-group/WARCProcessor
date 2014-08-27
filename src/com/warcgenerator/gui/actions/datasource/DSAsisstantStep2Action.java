package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;

public class DSAsisstantStep2Action extends AbstractAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel parentAssistant;
	private DSAssistantStep2Panel panel;
	
	public DSAsisstantStep2Action(IAppLogic logic, WarcGeneratorGUI view,
			JPanel parentAssistant) {
		this.view = view;
		this.logic = logic;
		this.parentAssistant = parentAssistant;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfig = (DataSourceConfig) Session
				.get(Constants.DATASOURCE_FORM_SESSION_KEY);
		CustomCardLayout cardLayout = 
				((CustomCardLayout)parentAssistant.getLayout());
		
		cardLayout.next(parentAssistant);
		this.panel = (DSAssistantStep2Panel)
				cardLayout.getCurrentPanel(parentAssistant);
		cardLayout.show(parentAssistant, panel.getName());
		
		
		/* DSAssistantStep2Panel nextPanel = new DSAssistantStep2Panel(logic, view);
		nextPanel.setTableModel(dsConfig.getCustomParams());

		view.loadMainPanel(nextPanel);*/
	}

}
