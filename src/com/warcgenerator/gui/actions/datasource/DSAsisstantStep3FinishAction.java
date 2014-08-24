package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAsisstantStep3FinishAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	
	public DSAsisstantStep3FinishAction(IAppLogic logic, 
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfig  = 
				(DataSourceConfig)Session.get(
						Constants.DATASOURCE_FORM_SESSION_KEY);
		
		logic.addDataSourceConfig(dsConfig);
		
		Collection<DataSourceConfig> dsConfigList =
				logic.getDataSourceConfigList();
		for(DataSourceConfig key: dsConfigList) {
			System.out.println(key);
		}
		
		view.buildTree();
		
		Action dsDetailAction = new DSDetailAction(logic,
				view, dsConfig); 
		dsDetailAction.actionPerformed(e);
		
		// Load modify panel
		//view.loadMainPanel(new DSCreateStep3Panel(logic, 
		//		view));	
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
