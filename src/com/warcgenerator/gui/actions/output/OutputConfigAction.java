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
	
	public OutputConfigAction(IAppLogic logic, WarcGeneratorGUI view
			) {
		this.logic = logic;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		OutputConfigPanel configPanel = 
				new OutputConfigPanel(logic, view);

		AppConfig config = logic.getAppConfig();
		
		configPanel.getOutputDirTField().setText(
				config.getOutputConfig().getOutputDir());
		configPanel.getSpamDirTField().setText(
				config.getSpamDirName());
		configPanel.getHamDirTField().setText(
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
