package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep3Panel;

public class DSAsisstantStep3FinishAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private DSAssistantStep3Panel panel;
	private Action dsDetailAction;
	private DataSourceConfig dsConfig;
	
	public DSAsisstantStep3FinishAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DSAssistantStep3Panel panel
			) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		
		logic.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dsConfig  = 
				(DataSourceConfig)Session.get(
						Constants.DATASOURCE_FORM_SESSION_KEY);
		System.out.println("Insertando!!! " + dsConfig);
		
		logic.addDataSourceConfig(dsConfig);
		
		Collection<DataSourceConfig> dsConfigList =
				logic.getDataSourceConfigList();
		for(DataSourceConfig key: dsConfigList) {
			System.out.println(key);
		}	
	}

	@Override
	public void update(Observable obj, Object logicCallack) {
		if (obj == logic) {
			if (panel.isShowing()) {
				String message = ((LogicCallback)logicCallack).getMessage();
				if (message.equals(IAppLogic.DATASOURCE_UPDATED_CALLBACK)) {
					JOptionPane.showMessageDialog(view.getMainFrame(), 
							"Los cambios se han actualizado");
					panel.commit();
					
					view.updateDS(dsConfig.getId(), dsConfig);
					view.selectLeftMenu(dsConfig.getName());
				} else if (message.equals(
						IAppLogic.DATASOURCE_CREATED_CALLBACK)) {
					JOptionPane.showMessageDialog(view.getMainFrame(), 
							"Se ha creado el datasource");
					panel.commit();
					
					Integer id = (Integer)
							((LogicCallback)logicCallack).getParams()[0];
					DataSourceConfig newDSConfig = logic.getDataSourceById(id);
					
					//view.buildTree();
					view.addDS(newDSConfig);
					view.selectLeftMenu(dsConfig.getName());
				}
			}
		}
	}
	
}
