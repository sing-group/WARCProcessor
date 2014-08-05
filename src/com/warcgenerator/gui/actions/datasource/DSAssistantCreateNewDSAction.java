package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;

public class DSAssistantCreateNewDSAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	
	public DSAssistantCreateNewDSAction(IAppLogic logic,
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfig = new DataSourceConfig();
		Session.add(Constants.DATASOURCE_FORM_SESSION_KEY,
				dsConfig);
		
		DSAssistantStep1Panel panel = new DSAssistantStep1Panel(logic, 
				view);
		
		System.out.println("logic es :" + logic);
		
		Collection<DataSourceConfig> dataSourceTypeList = 
				logic.getDataSourceTypesList();
		
		System.out.println("dataSourceTypeList es: " + 
				dataSourceTypeList);
		
		String[] dsTypeStringArray = 
				new String[dataSourceTypeList.size()];
		int i = 0;
		for (DataSourceConfig dsType:dataSourceTypeList) {
			dsTypeStringArray[i++] = dsType.getName();
		}
		
		panel.setTipoDSCBoxValues(dsTypeStringArray);
		
		view.loadMainPanel(panel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
