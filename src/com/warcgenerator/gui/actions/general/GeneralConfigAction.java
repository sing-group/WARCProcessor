package com.warcgenerator.gui.actions.general;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;

public class GeneralConfigAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private GeneralConfigPanel configPanel;
	
	public GeneralConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			GeneralConfigPanel configPanel) {
		this.logic = logic;
		this.view = view;
		this.configPanel = configPanel;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {		
		AppConfig config = logic.getAppConfig();
		
		System.out.println("config.getNumSites(): " + config.getNumSites());
		
		configPanel.getNumSitesTField().setValue(Integer.toString(
				config.getNumSites()));
		if (config.getRatioIsPercentage()) {
			configPanel.getSpamHamRatioRBtn().setSelected(true);
			configPanel.getQuantityEnabledRBtn().setSelected(false);
			
			configPanel.getSpamQuantityTField().setEnabled(false);
			configPanel.getSlider().setEnabled(true);
			configPanel.getSpamHamRationValueTField().setEnabled(true);
			
			configPanel.getSpamHamRationValueTField().setValue(
					Integer.toString(config.getRatioPercentageSpam()));
			configPanel.getSlider().setValue(
					config.getRatioPercentageSpam());
		} else {
			configPanel.getSpamHamRatioRBtn().setSelected(false);
			configPanel.getQuantityEnabledRBtn().setSelected(true);
			
			configPanel.getSpamQuantityTField().setEnabled(true);
			configPanel.getSlider().setEnabled(false);
			configPanel.getSpamHamRationValueTField().setEnabled(false);
			
			configPanel.getSpamHamRationValueTField().setValue(
					Integer.toString(config.getRatioPercentageSpam()));
		}
		
		configPanel.getOnlyActiveSitesEnabledCBox().setSelected(
				config.getOnlyActiveSites());
		configPanel.getDownloadAgainEnabledCBox().setSelected(
				config.getDownloadAgain());
		
		//view.loadMainPanel(configPanel);
		view.loadMainPanel(configPanel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
