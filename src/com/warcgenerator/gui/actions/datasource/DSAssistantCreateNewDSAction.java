package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;

public class DSAssistantCreateNewDSAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel parentAssistant;
	private DSAssistantStep1Panel panel;
	private DataSourceConfig dsConfig;
	
	public DSAssistantCreateNewDSAction(IAppLogic logic,
			WarcGeneratorGUI view,
			JPanel parentAssistant) {
		this.view = view;
		this.logic = logic;
		this.parentAssistant = parentAssistant;
		
		
		
		System.out.println("panel es " + panel);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CustomCardLayout cardLayout = 
				((CustomCardLayout)parentAssistant.getLayout());
		
		cardLayout.next(parentAssistant);
		this.panel = (DSAssistantStep1Panel)
				cardLayout.getCurrentPanel(parentAssistant);
		
		fill();
		cardLayout.show(parentAssistant, panel.getName());
		System.out.println("Mostrar!!" + panel.getName());
	}

	private void fill() {
		dsConfig = (DataSourceConfig)Session.get(
				Constants.DATASOURCE_FORM_SESSION_KEY);
		
		System.out.println("panel es " + panel);
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
