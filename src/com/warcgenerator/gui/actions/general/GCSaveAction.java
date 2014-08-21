package com.warcgenerator.gui.actions.general;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;

public class GCSaveAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private GeneralConfigPanel panel;
	private IAppLogic logic;
	
	public GCSaveAction(IAppLogic logic,
			WarcGeneratorGUI view,
			GeneralConfigPanel panel
			) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig appConfig = logic.getAppConfig();
		
		appConfig.setNumSites(
				Integer.parseInt(panel.getNumSitesTField().getText()));
		if (panel.getSpamHamRatioRBtn().isSelected()) {
			appConfig.setRatioIsPercentage(true);
			appConfig.setRatioSpam(Integer.parseInt(
					panel.getSpamHamRationValueTField().getText()));
		} else {
			appConfig.setRatioIsPercentage(false);
			appConfig.setRatioSpam(Integer.parseInt(
					panel.getSpamQuantityTField().getText()));
		}
		appConfig.setDownloadAgain(panel.getDownloadAgainEnabledCBox().
				isSelected());
		appConfig.setOnlyActiveSites(panel.getOnlyActiveSitesEnabledCBox().
				isSelected());
		
		if (validate(appConfig)) {
			AppConfig config = logic.getAppConfig();
			
			logic.updateAppConfig(config);
			
			System.out.println("Se ha guardado con exito");
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
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
