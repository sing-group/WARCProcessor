package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep3Panel;
import com.warcgenerator.gui.view.datasources.DSDetailPanel;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class DSDetailAction extends AbstractAction implements Observer {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	private JPanel mainDetailPanel;
	private DSDetailPanel detailPanel;
	private JPanel dsAssistantStep1Panel;
	private JPanel dsAssistantStep2Panel;
	private JPanel dsAssistantStep3Panel;
	private CustomCardLayout cardLayout;
	
	public DSDetailAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DataSourceConfig config) {
		this.logic = logic;
		this.view = view;
		this.config = config;
		view.addObserver(this);
		
		System.out.println("config!!!!! es " + config);
		
		mainDetailPanel = view.getAssistantPanel();
		
		detailPanel = 
				new DSDetailPanel(logic, view, config, mainDetailPanel);
		dsAssistantStep1Panel = new DSAssistantStep1Panel(logic, view,
				mainDetailPanel);
		dsAssistantStep2Panel = new DSAssistantStep2Panel(logic, view,
				mainDetailPanel);
		dsAssistantStep3Panel = new DSAssistantStep3Panel(logic, view,
				mainDetailPanel);
	}
	
	public void init()  {
		mainDetailPanel = view.getAssistantPanel();
		mainDetailPanel.removeAll();
		cardLayout = (CustomCardLayout)mainDetailPanel.getLayout();
		
		mainDetailPanel.add(detailPanel,
				detailPanel.getName());
		mainDetailPanel.add(dsAssistantStep1Panel,
				dsAssistantStep1Panel.getName());
		mainDetailPanel.add(dsAssistantStep2Panel,
				dsAssistantStep2Panel.getName());
		mainDetailPanel.add(dsAssistantStep3Panel,
				dsAssistantStep3Panel.getName());
		
		mainDetailPanel.updateUI();
		mainDetailPanel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		init();
		detailPanel.setSummaryText(config.toString());
		((CustomCardLayout)mainDetailPanel.getLayout()).show(mainDetailPanel,
				detailPanel.getName());
		view.loadMainPanel(mainDetailPanel);
	}	 
	
	
	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			if (mainDetailPanel.isVisible()
					&& ((Object[])message)[0].
						equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane
						.showConfirmDialog(view.getMainFrame(),
								"Se perderan los cambios. "
									+ "¿Esta seguro que desea salir del asistente?");
				
				if (userSelection == JOptionPane.OK_OPTION) {
					CustomJPanel panel = 
							(CustomJPanel)cardLayout.getCurrentPanel(
									mainDetailPanel);
					panel.rollback();
					
					JPanel newPanel = (JPanel)((Object[])message)[1];
					view.loadMainPanel(newPanel);
				}
			}
		}
	}
}
