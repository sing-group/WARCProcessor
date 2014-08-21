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
	
	public GeneralConfigAction(IAppLogic logic, WarcGeneratorGUI view
			) {
		this.logic = logic;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		GeneralConfigPanel configPanel = 
				new GeneralConfigPanel(logic, view);

		AppConfig config = logic.getAppConfig();
		configPanel.getNumSitesTField().setText(Integer.toString(
				config.getNumSites()));
		if (config.getRatioIsPercentage()) {
			configPanel.getSpamHamRatioRBtn().setSelected(true);
			configPanel.getQuantityEnabledRBtn().setSelected(false);
			
			configPanel.getSpamHamRationValueTField().setText(
					Integer.toString(config.getRatioSpam()));
			configPanel.getSlider().setValue(
					config.getRatioSpam());
		} else {
			configPanel.getSpamHamRatioRBtn().setSelected(false);
			configPanel.getQuantityEnabledRBtn().setSelected(true);
		}
		
		configPanel.getOnlyActiveSitesEnabledCBox().setSelected(
				config.getOnlyActiveSites());
		configPanel.getDownloadAgainEnabledCBox().setSelected(
				config.getDownloadAgain());
		
		view.loadMainPanel(configPanel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
