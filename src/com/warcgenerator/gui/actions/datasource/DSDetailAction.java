package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.components.CustomCardLayout;
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
public class DSDetailAction extends AbstractAction {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	private JPanel mainDetailPanel;
	private DSDetailPanel detailPanel;
	
	public DSDetailAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DataSourceConfig config) {
		this.logic = logic;
		this.view = view;
		this.config = config;
		
		mainDetailPanel =
				new JPanel(new CustomCardLayout());
		mainDetailPanel.setName("DSDetailPanel" + config.getId());
		
		detailPanel = 
				new DSDetailPanel(logic, view, config, mainDetailPanel);
		JPanel dsAssistantStep1Panel = new DSAssistantStep1Panel(logic, view,
				mainDetailPanel);
		JPanel dsAssistantStep2Panel = new DSAssistantStep2Panel(logic, view,
				mainDetailPanel);
		JPanel dsAssistantStep3Panel = new DSAssistantStep3Panel(logic, view,
				mainDetailPanel);
		
		System.out.println("anadiendo: " + detailPanel.getName());
		
		mainDetailPanel.add(detailPanel,
				detailPanel.getName());
		mainDetailPanel.add(dsAssistantStep1Panel,
				dsAssistantStep1Panel.getName());
		mainDetailPanel.add(dsAssistantStep2Panel,
				dsAssistantStep2Panel.getName());
		mainDetailPanel.add(dsAssistantStep3Panel,
				dsAssistantStep3Panel.getName());
		
		view.addMainPanel(mainDetailPanel); 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("MOstrando panel: " + detailPanel.getName());
		
		detailPanel.setSummaryText(config.toString());
		
		((CustomCardLayout)mainDetailPanel.getLayout()).show(mainDetailPanel,
				detailPanel.getName());
		
		view.loadMainPanel(mainDetailPanel);
	}	 
}
