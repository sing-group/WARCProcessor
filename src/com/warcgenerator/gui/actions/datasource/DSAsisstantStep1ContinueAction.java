package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;

public class DSAsisstantStep1ContinueAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private DSAssistantStep1Panel panel;
	private IAppLogic logic;
	private Action dsAsisstantStep2Action;
	
	public DSAsisstantStep1ContinueAction(IAppLogic logic,
			WarcGeneratorGUI view,
			DSAssistantStep1Panel panel,
			JPanel parentAssistant
			) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		
		dsAsisstantStep2Action =
				new DSAsisstantStep2Action(logic, view, parentAssistant);
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
		
		dsConfig.setName(panel.getNameJTField().getText());
		dsConfig.setFilePath(panel.getFolderJTField().getText());	
		
		String dsType = (String)panel.getTipoDSCBox().getSelectedItem();
		DataSourceConfig dsConfigTmp = logic.getDataSourceType(dsType);
		dsConfig.setDsClassName(dsConfigTmp.getDsClassName());
		dsConfig.setHandlerClassName(dsConfigTmp.getHandlerClassName());
		
		System.out.println("rellenando param!!!");
		for (String key: dsConfigTmp.getCustomParams().keySet()) {
			System.out.println("parametro es " + key);
			dsConfig.getCustomParams().put(key, 
					dsConfigTmp.getCustomParams().get(key));
		}
		
		if (validate(dsConfig)) {
			// Escribiendo los datos en el nuevo panel
			Session.add(
					Constants.DATASOURCE_FORM_SESSION_KEY, dsConfig);
			
			dsAsisstantStep2Action.actionPerformed(e);
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
