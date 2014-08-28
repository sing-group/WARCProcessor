package com.warcgenerator.gui.actions.general;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;

public class GCSaveAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private GeneralConfigPanel panel;
	private IAppLogic logic;
	
	public GCSaveAction() {
	}
	
	public void init(IAppLogic logic,
			WarcGeneratorGUI view,
			GeneralConfigPanel panel
			) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		
		// Add callback
		logic.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig appConfig = logic.getAppConfig();
		
		appConfig.setNumSites(
				Integer.parseInt((String)panel.getNumSitesTField().getValue()));
		if (panel.getSpamHamRatioRBtn().isSelected()) {
			appConfig.setRatioIsPercentage(true);
			appConfig.setRatioPercentageSpam(Integer.parseInt(
					panel.getSpamHamRationValueTField().getText()));
		} else {
			appConfig.setRatioIsPercentage(false);
			appConfig.setRatioQuantitySpam(Integer.parseInt(
					panel.getSpamQuantityTField().getText()));
		}
		appConfig.setDownloadAgain(panel.getDownloadAgainEnabledCBox().
				isSelected());
		appConfig.setOnlyActiveSites(panel.getOnlyActiveSitesEnabledCBox().
				isSelected());
		
		if (validate(appConfig)) {
			logic.updateAppConfig(appConfig);
		}	
	}

	public boolean validate(AppConfig config) {
		StringBuilder errors = new StringBuilder();
		
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
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			if (panel.isVisible()) {
				String message = ((LogicCallback)logicCallback).getMessage();
				if (message.equals(IAppLogic.APP_LOGIC_UPDATED_CALLBACK)) {
					JOptionPane.showMessageDialog(view.getMainFrame(), 
							"Los cambios se han guardado");
					panel.commit();
				}
			}
		}
	}
	
}
