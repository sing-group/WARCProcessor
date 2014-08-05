package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;

public class DSAsisstantStep1ContinueAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private DSAssistantStep1Panel panel;
	private IAppLogic logic;
	
	public DSAsisstantStep1ContinueAction(IAppLogic logic,
			WarcGeneratorGUI view,
			DSAssistantStep1Panel panel
			) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfigSrc  = 
				(DataSourceConfig)Session.get(
						Constants.DATASOURCE_FORM_SESSION_KEY);
		DataSourceConfig dsConfig = new DataSourceConfig();
		try {
			BeanUtils.copyProperties(dsConfig, dsConfigSrc);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		dsConfig.setName(panel.getNombreJTField().getText());
		dsConfig.setFilePath(panel.getCarpetaJTField().getText());	
		
		String dsType = (String)panel.getTipoDSCBox().getSelectedItem();
		DataSourceConfig dsConfigTmp = logic.getDataSourceType(dsType);
		dsConfig.setDsClassName(dsConfigTmp.getDsClassName());
		dsConfig.setHandlerClassName(dsConfigTmp.getHandlerClassName());
		
		for (String key: dsConfigTmp.getCustomParams().keySet()) {
			dsConfig.getCustomParams().put(key, 
					dsConfigTmp.getCustomParams().get(key));
		}
		
		System.out.println("Guardando en session: " + dsConfig);
		
		if (validate(dsConfig)) {
			// Escribiendo los datos en el nuevo panel
			Session.add(
					Constants.DATASOURCE_FORM_SESSION_KEY, dsConfig);
			
			DSAssistantStep2Panel nextPanel = new DSAssistantStep2Panel(logic,
					view);
			nextPanel.setTableModel(dsConfig.getCustomParams());
			
			
			view.loadMainPanel(nextPanel);
		}
	}

	
	public boolean validate(DataSourceConfig config) {
		StringBuilder errors = new StringBuilder();
		
		if (config.getName().length() == 0) {
			errors.append("-Name\n");
		}
		if (config.getFilePath().length() == 0) {
			errors.append("-Folder\n");
		}
		
		if (errors.length() != 0) {
			ValidationDialog dialog =
					ValidationDialog.getInstance();
			dialog.setErroresLabel(errors.toString());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			return false;
		}
		return true;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
