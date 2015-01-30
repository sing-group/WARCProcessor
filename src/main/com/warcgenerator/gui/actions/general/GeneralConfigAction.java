package com.warcgenerator.gui.actions.general;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;

public class GeneralConfigAction extends CustomAction implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private GeneralConfigPanel configPanel;

	public GeneralConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			GeneralConfigPanel configPanel) {
		super(view, configPanel);
		this.logic = logic;
		this.view = view;
		this.configPanel = configPanel;
	}

	@Override
	public void action(ActionEvent e) {
		AppConfig config = logic.getAppConfig();

		configPanel.getNumSitesTField().setValue(
				Integer.toString(config.getNumSites()));
		if (config.getRatioIsPercentage()) {
			configPanel.getSpamHamRatioRBtn().setSelected(true);
			configPanel.getQuantityEnabledRBtn().setSelected(false);

			configPanel.getSpamQuantityTField().setEnabled(false);
			configPanel.getSlider().setEnabled(true);
			configPanel.getSpamHamRationValueTField().setEnabled(true);
		} else {
			configPanel.getSpamHamRatioRBtn().setSelected(false);
			configPanel.getQuantityEnabledRBtn().setSelected(true);

			configPanel.getSpamQuantityTField().setEnabled(true);
			configPanel.getSlider().setEnabled(false);
			configPanel.getSpamHamRationValueTField().setEnabled(false);
		}

		configPanel.getSpamHamRationValueTField().setValue(
				Integer.toString(config.getRatioPercentageSpam()));
		configPanel.getSlider().setValue(config.getRatioPercentageSpam());
		configPanel.getSpamQuantityTField().setValue(
				config.getRatioQuantitySpam());

		configPanel.getOnlyActiveSitesEnabledCBox().setSelected(
				config.getOnlyActiveSites());
		configPanel.getDownloadAgainEnabledCBox().setSelected(
				config.getDownloadAgain());

		configPanel.getSpamHamRationValueTField().addPropertyChangeListener(
				"value", new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						configPanel.getSlider().setValue(
								Integer.valueOf((String) configPanel
										.getSpamHamRationValueTField()
										.getValue()));
					}
				});

		configPanel.commit();
		view.loadMainPanel(configPanel);
	}

	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			if (this.isCurrentAction()
					&& ((Object[]) message)[0]
							.equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane.showConfirmDialog(
						view.getMainFrame(),
						Messages.getString("GeneralConfgAction.warning.text"));

				if (userSelection == JOptionPane.OK_OPTION) {
					configPanel.save();
					Action nextAction = (Action) ((Object[]) message)[1];
					nextAction.actionPerformed(null);
				} else if (userSelection == JOptionPane.NO_OPTION) {
					configPanel.rollback();
					Action nextAction = (Action) ((Object[]) message)[1];
					nextAction.actionPerformed(null);
				}
			}
		}
	}

}
