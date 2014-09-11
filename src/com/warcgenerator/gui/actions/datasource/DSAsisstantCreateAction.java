package com.warcgenerator.gui.actions.datasource;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantCreatePanel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep3Panel;

public class DSAsisstantCreateAction extends CustomAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel mainAssistantCreatePanel;
	private DSAssistantCreatePanel dsAssistantCreatePanel;
	private JPanel dsAssistantStep1Panel;
	private JPanel dsAssistantStep2Panel;
	private JPanel dsAssistantStep3Panel;
	private CustomCardLayout cardLayout;

	public DSAsisstantCreateAction(IAppLogic logic, WarcGeneratorGUI view) {
		super(view, view.getAssistantPanel());
		this.logic = logic;
		this.view = view;

		mainAssistantCreatePanel = view.getAssistantPanel();
		
		dsAssistantCreatePanel = new DSAssistantCreatePanel(logic, view,
				mainAssistantCreatePanel);
		dsAssistantStep1Panel = new DSAssistantStep1Panel(logic, view,
				mainAssistantCreatePanel);
		dsAssistantStep2Panel = new DSAssistantStep2Panel(logic, view,
				mainAssistantCreatePanel);
		dsAssistantStep3Panel = new DSAssistantStep3Panel(logic, view,
				mainAssistantCreatePanel);
	}

	private void init() {
		mainAssistantCreatePanel = view.getAssistantPanel();
		mainAssistantCreatePanel.removeAll();

		cardLayout = (CustomCardLayout) mainAssistantCreatePanel.getLayout();

		mainAssistantCreatePanel.add(dsAssistantCreatePanel,
				dsAssistantCreatePanel.getName());
		mainAssistantCreatePanel.add(dsAssistantStep1Panel,
				dsAssistantStep1Panel.getName());
		mainAssistantCreatePanel.add(dsAssistantStep2Panel,
				dsAssistantStep2Panel.getName());
		mainAssistantCreatePanel.add(dsAssistantStep3Panel,
				dsAssistantStep3Panel.getName());
		
		mainAssistantCreatePanel.updateUI();
		mainAssistantCreatePanel.repaint();
	}

	@Override
	public void action(ActionEvent e) {
		init();
		/*DataSourceConfig dsConfig = new DataSourceConfig();
		Session.add(Constants.DATASOURCE_FORM_SESSION_KEY, dsConfig);*/

		((CardLayout) mainAssistantCreatePanel.getLayout()).show(
				mainAssistantCreatePanel, dsAssistantCreatePanel.getName());

		view.loadMainPanel(mainAssistantCreatePanel);
	}

	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			if (mainAssistantCreatePanel.isShowing()
					&& ((Object[]) message)[0]
							.equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane.showConfirmDialog(
						view.getMainFrame(),
						"¿Esta seguro que desea salir del asistente?");

				if (userSelection == JOptionPane.OK_OPTION) {
					CustomJPanel panel = (CustomJPanel) cardLayout
							.getCurrentPanel(mainAssistantCreatePanel);
					panel.rollback();

					Action nextAction = (Action)((Object[])message)[1];
					nextAction.actionPerformed(null);
				}
			}
		}
	}

}
