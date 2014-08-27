package com.warcgenerator.gui.actions.output;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.output.OutputConfigPanel;

public class OutputConfigAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private OutputConfigPanel configPanel;
	
	public OutputConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			OutputConfigPanel configPanel) {
		this.logic = logic;
		this.view = view;
		this.configPanel = configPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		AppConfig config = logic.getAppConfig();
		
		configPanel.getOutputDirTField().setValue(
				config.getOutputConfig().getOutputDir());
		configPanel.getSpamDirTField().setValue(
				config.getSpamDirName());
		configPanel.getHamDirTField().setValue(
				config.getHamDirName());
		
		/*configPanel.getNumSitesTField().setText(Integer.toString(
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
				config.getDownloadAgain());*/
		
		view.loadMainPanel(configPanel);
	}
}
