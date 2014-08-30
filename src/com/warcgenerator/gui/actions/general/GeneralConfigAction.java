package com.warcgenerator.gui.actions.general;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
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
		view.addObserver(this);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {		
		AppConfig config = logic.getAppConfig();
		
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
	public void update(Observable obj, Object message) {
		if (obj == view) {
			System.out.println("Es visible: ----> " + configPanel.isVisible());
			Object isModified = Session.get(Constants.FORM_MODIFIED_SESSION_KEY);
			Boolean formModified = false;
			if (isModified instanceof Boolean) {
				formModified = (Boolean) isModified;
			}
			
			if (formModified && configPanel.isShowing()
					&& ((Object[])message)[0].
						equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane
						.showConfirmDialog(view.getMainFrame(),
								"Existen cambios no guardados. ¿Desea guardar los cambios?");
				
				if (userSelection == JOptionPane.OK_OPTION) {
					configPanel.save();
					JPanel newPanel = (JPanel)((Object[])message)[1];
					view.loadMainPanel(newPanel);
				} else if (userSelection == JOptionPane.NO_OPTION) {
					configPanel.rollback();
					JPanel newPanel = (JPanel)((Object[])message)[1];
					view.loadMainPanel(newPanel);
				}
			}
		}
	}
	
}
