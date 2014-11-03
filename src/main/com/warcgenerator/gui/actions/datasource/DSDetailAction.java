package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantLangPanel;
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
public class DSDetailAction extends CustomAction {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	private JPanel mainDetailPanel;
	private DSDetailPanel detailPanel;
	private JPanel dsAssistantStep1Panel;
	private JPanel dsAssistantLangPanel;
	private JPanel dsAssistantStep2Panel;
	private JPanel dsAssistantStep3Panel;
	private CustomCardLayout cardLayout;
	
	public DSDetailAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DataSourceConfig config) {
		super(view, view.getAssistantPanel());
		this.logic = logic;
		this.view = view;
		this.config = config;
		
		mainDetailPanel = view.getAssistantPanel();
		
		detailPanel = 
				new DSDetailPanel(logic, view, config, mainDetailPanel);
		dsAssistantStep1Panel = new DSAssistantStep1Panel(logic, view,
				mainDetailPanel);
		dsAssistantLangPanel = new DSAssistantLangPanel(logic, view,
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
		mainDetailPanel.add(dsAssistantLangPanel,
				dsAssistantLangPanel.getName());
		mainDetailPanel.add(dsAssistantStep2Panel,
				dsAssistantStep2Panel.getName());
		mainDetailPanel.add(dsAssistantStep3Panel,
				dsAssistantStep3Panel.getName());
		
		mainDetailPanel.updateUI();
		mainDetailPanel.repaint();
	}

	@Override
	public void action(ActionEvent e) {
		init();
		detailPanel.setSummaryText(config.toString());
		((CustomCardLayout)mainDetailPanel.getLayout()).show(mainDetailPanel,
				detailPanel.getName());
		view.loadMainPanel(mainDetailPanel);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}	 
}
