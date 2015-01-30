package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;

@SuppressWarnings("serial")
public class DSAsisstantStep2Action extends CustomAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel parentAssistant;
	private DSAssistantStep2Panel panel;
	
	public DSAsisstantStep2Action(IAppLogic logic, WarcGeneratorGUI view,
			JPanel parentAssistant) {
		super(view, parentAssistant);
		this.view = view;
		this.logic = logic;
		this.parentAssistant = parentAssistant;
	}

	@Override
	public void action(ActionEvent e) {
		DataSourceConfig dsConfig = (DataSourceConfig) Session
				.get(Constants.DATASOURCE_FORM_SESSION_KEY);
		CustomCardLayout cardLayout = 
				((CustomCardLayout)parentAssistant.getLayout());
		
		cardLayout.next(parentAssistant);
		panel = (DSAssistantStep2Panel)
				cardLayout.getCurrentPanel(parentAssistant);
		
		if (dsConfig.getMaxElements() != null) {
			panel.getMaxElementsEnabledCBox().setSelected(true);
			panel.getQuantityMaxElemsTField().setEnabled(true);
            panel.getQuantityMaxElemsTField().setValue(
            		dsConfig.getMaxElements());
		}
		
		if (dsConfig.getSpam() != null) {			
			panel.getSpamEnabledCBox().setSelected(true);
			panel.getSpamRButtom().setEnabled(true);
			panel.getHamRButtom().setEnabled(true);
			if (dsConfig.getSpam()) {
				panel.getSpamRButtom().setSelected(true);
				panel.getHamRButtom().setSelected(false);
			} else {
				panel.getSpamRButtom().setSelected(false);
				panel.getHamRButtom().setSelected(true);
			}
		}
		
		panel.setTableModel(dsConfig.getCustomParams());
		
		cardLayout.show(parentAssistant, panel.getName());
	}

	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			if (this.isCurrentAction()
					&& ((Object[])message)[0].
						equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane
						.showConfirmDialog(view.getMainFrame(),
								Messages.getString("DSWizard.exit.message.text"),
								Messages.getString("DSWizard.exit.title.text"),
								JOptionPane.OK_CANCEL_OPTION);
				
				if (userSelection == JOptionPane.OK_OPTION) {
					panel.rollback();
					Action nextAction = (Action)((Object[])message)[1];
					nextAction.actionPerformed(null);
				}
			}
		}
		
	}
}
