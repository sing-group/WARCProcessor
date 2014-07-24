package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;

public class DSAssistantCreateNewDSAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	
	public DSAssistantCreateNewDSAction(WarcGeneratorGUI view
			) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfig = new DataSourceConfig();
		Session.add(Constants.DATASOURCE_FORM_SESSION_KEY,
				dsConfig);
		view.loadMainPanel(new DSAssistantStep1Panel(view));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
