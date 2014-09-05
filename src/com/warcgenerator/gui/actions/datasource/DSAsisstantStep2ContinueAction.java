package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep3Panel;

public class DSAsisstantStep2ContinueAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private DSAssistantStep2Panel panel;
	private JPanel parentAssistant;
	
	public DSAsisstantStep2ContinueAction(IAppLogic logic,
			WarcGeneratorGUI view,
			DSAssistantStep2Panel panel,
			JPanel parentAssistant) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		this.parentAssistant = parentAssistant;
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
		
		// Check isSpam
		if (panel.getSpamEnabledCBox().isSelected()) {
			dsConfig.setSpam(panel.getSpamRButtom().isSelected());
		} else {
			dsConfig.setSpam(null);
		}
		if (panel.getMaxElementsEnabledCBox().isSelected()) {
			Integer intValue = null;
			try {
				intValue = Integer.parseInt(
					panel.getQuantityMaxElemsTField().getText());
			} catch (NumberFormatException ex) {
				// TODO handle this
			}
			dsConfig.setMaxElements(intValue);
		} else {
			dsConfig.setMaxElements(null);
		}
		
		// Get params from the params tabled
		Map<String, CustomParamConfig> customParams = dsConfig.getCustomParams();
		for (int i = 0; i < panel.getParamsTable().getModel().getRowCount(); i++) {
			String paramName = (String)panel.getParamsTable().getModel().getValueAt(i, 0);
			String paramValue = (String)panel.getParamsTable().getModel().getValueAt(i, 1);
			
			customParams.get(paramName).setValue(paramValue);
		}

		if (validate(dsConfig)) {
			Session.add(
					Constants.DATASOURCE_FORM_SESSION_KEY, dsConfig);
			
			CustomCardLayout cardLayout = 
					((CustomCardLayout)parentAssistant.getLayout());
			cardLayout.next(parentAssistant);
			DSAssistantStep3Panel nextPanel = (DSAssistantStep3Panel)
					cardLayout.getCurrentPanel(parentAssistant);
			
			nextPanel.setSummaryText(dsConfig.toString());
			
			cardLayout.show(parentAssistant, nextPanel.getName());
		}
	}

	public boolean validate(DataSourceConfig config) {
		StringBuilder errors = new StringBuilder();
		
		if (panel.getMaxElementsEnabledCBox().isSelected()) { 
			if (config.getMaxElements() == null ||
					config.getMaxElements() < 1) {
				errors.append("-Cantidad");
			}
		}
		
		for (String key:config.getCustomParams().keySet()) {
			CustomParamConfig customParam = config.getCustomParams().get(key);
			try {
				Class<?> cArgs[] = { String.class };
				System.out.println("get Type es: " + customParam.getType());
				
				Class<?> clazz = Class.forName(customParam.getType());
				Constructor<?> ctor = clazz.getConstructor(cArgs);
				
				if (customParam.getType().equals(
						Integer.class.getName())) {
					ctor.newInstance(customParam.getValue());
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errors.append("-Parametro: " + customParam.getName());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errors.append("-Parametro: " + customParam.getName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errors.append("-Parametro: " + customParam.getName());
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errors.append("-Parametro: " + customParam.getName());
			}
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
