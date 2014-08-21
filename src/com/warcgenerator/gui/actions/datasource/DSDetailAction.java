package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantCreatePanel;
import com.warcgenerator.gui.view.datasources.DSDetailPanel;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class DSDetailAction extends AbstractAction implements Observer {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	
	public DSDetailAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DataSourceConfig config) {
		this.logic = logic;
		this.view = view;
		this.config = config;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DSDetailPanel nextPanel = new DSDetailPanel(logic, view, config);
		nextPanel.setSummaryText(config.toString());
		
		view.loadMainPanel(nextPanel);
	}

	@Override
	public void update(Observable aPublisher, Object aData) {
		// TODO Auto-generated method stub
		
	}
	
	 
}
