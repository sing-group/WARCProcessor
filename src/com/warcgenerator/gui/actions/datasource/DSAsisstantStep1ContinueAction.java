package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.helper.ValidatorHelper;
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
		dsConfig.setType(dsConfigTmp.getName());
		dsConfig.setDsClassName(dsConfigTmp.getDsClassName());
		dsConfig.setHandlerClassName(dsConfigTmp.getHandlerClassName());
		
		// If we are create a new datasource 
		if (dsConfig.getId() == null) {
			dsConfig.getCustomParams().clear();
			// Set default values to custom parameters
			for (String key: dsConfigTmp.getCustomParams().keySet()) {
				dsConfig.getCustomParams().put(key, 
						dsConfigTmp.getCustomParams().get(key));
			}
		}
		
		if (validate(dsConfig)) {
			// Escribiendo los datos en el nuevo panel
			Session.add(
					Constants.DATASOURCE_FORM_SESSION_KEY, dsConfig);
			
			((CustomJPanel)panel).commit();
			dsAsisstantStep2Action.actionPerformed(e);
		}
	}

	
	public boolean validate(DataSourceConfig dsConfig) {
		StringBuilder errors = new StringBuilder();
		
		// Check if already exist a DS with the same name
		boolean sameName = false;
		Collection<DataSourceConfig> dsList = 
			logic.getDataSourceConfigList();
		for (DataSourceConfig dsAux: dsList) {
			if (dsAux.getName().equals(dsConfig.getName())) {
				sameName = true;
			}
		}
		
		if (sameName && dsConfig.getId() == null) {
			errors.append("El nombre del datasource ya existe\n");
		}
		
		if (!ValidatorHelper.isNotNullOREmpty(
				dsConfig.getName())) {
			errors.append("Nombre<br>");
		}
		
		if (!ValidatorHelper.isNotNullOREmpty(
				dsConfig.getFilePath())) {
			errors.append("Carpeta<br>");
		}
	
		
		if (errors.length() != 0) {
			ValidationDialog dialog =
					ValidationDialog.getInstance(view.getMainFrame());
			dialog.setErroresLabel("<html>" + errors.toString() + "</html>");
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
