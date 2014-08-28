package com.warcgenerator.gui.actions.datasource;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantCreatePanel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep3Panel;

public class DSAsisstantCreateAction extends AbstractAction implements Observer {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel mainAssistantCreatePanel;
	private DSAssistantCreatePanel dsAssistantCreatePanel;
	private JPanel dsAssistantStep1Panel;
	private JPanel dsAssistantStep2Panel;
	private JPanel dsAssistantStep3Panel;
	private CustomCardLayout cardLayout;

	public DSAsisstantCreateAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.logic = logic;
		this.view = view;
		view.addObserver(this);

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
	public void actionPerformed(ActionEvent e) {
		init();
		DataSourceConfig dsConfig = new DataSourceConfig();
		Session.add(Constants.DATASOURCE_FORM_SESSION_KEY, dsConfig);

		System.out.println("create panel!!!!!!!!!!!!!!!");

		((CardLayout) mainAssistantCreatePanel.getLayout()).show(
				mainAssistantCreatePanel, dsAssistantCreatePanel.getName());

		view.loadMainPanel(mainAssistantCreatePanel);
	}

	@Override
	public void update(Observable obj, Object message) {
		// TODO Auto-generated method stub

		System.out.println("Detecta algo!!");

		if (obj == view) {
			if (mainAssistantCreatePanel.isVisible()
					&& ((Object[]) message)[0]
							.equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane.showConfirmDialog(
						view.getMainFrame(),
						"¿Esta seguro que desea salir del asistente?");

				if (userSelection == JOptionPane.OK_OPTION) {
					CustomJPanel panel = (CustomJPanel) cardLayout
							.getCurrentPanel(mainAssistantCreatePanel);
					panel.rollback();

					JPanel newPanel = (JPanel) ((Object[]) message)[1];
					view.loadMainPanel(newPanel);
				}
			}
		}
	}

}
