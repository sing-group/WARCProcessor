package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;

public class DSAssistantCreateNewDSAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private DSAssistantStep1Panel panel;
	private DataSourceConfig dsConfig;
	
	public DSAssistantCreateNewDSAction(IAppLogic logic,
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
		panel = new DSAssistantStep1Panel(logic, view);
		dsConfig = (DataSourceConfig)Session.get(
				Constants.DATASOURCE_FORM_SESSION_KEY);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		fill();
		view.loadMainPanel(panel);
	}

	private void fill() {
		panel.getNameJTField().setText(dsConfig.getName());
		panel.getFolderJTField().setText(dsConfig.getFilePath());
		
		Collection<DataSourceConfig> dataSourceTypeList = 
				logic.getDataSourceTypesList();
		String[] dsTypeStringArray = 
				new String[dataSourceTypeList.size()];
		int i = 0;
		for (DataSourceConfig dsType:dataSourceTypeList) {
			dsTypeStringArray[i++] = dsType.getName();
		}
		panel.setTipoDSCBoxValues(dsTypeStringArray);
	}
}
