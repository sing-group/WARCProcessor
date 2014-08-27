package com.warcgenerator.gui.actions.datasource;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantCreatePanel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep2Panel;
import com.warcgenerator.gui.view.datasources.DSAssistantStep3Panel;

public class DSAsisstantCreateAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel mainAssistantCreatePanel;
	private DSAssistantCreatePanel dsAssistantCreatePanel;
	
	public DSAsisstantCreateAction(IAppLogic logic, WarcGeneratorGUI view
			) {
		this.logic = logic;
		this.view = view;
		
		mainAssistantCreatePanel =
				new JPanel(new CustomCardLayout());
		mainAssistantCreatePanel.setName("AssistantCreatePanel");
		
		dsAssistantCreatePanel = 
			new DSAssistantCreatePanel(logic, view, mainAssistantCreatePanel);
		JPanel dsAssistantStep1Panel = new DSAssistantStep1Panel(logic, view,
				mainAssistantCreatePanel);
		JPanel dsAssistantStep2Panel = new DSAssistantStep2Panel(logic, view,
				mainAssistantCreatePanel);
		JPanel dsAssistantStep3Panel = new DSAssistantStep3Panel(logic, view,
				mainAssistantCreatePanel);
		
		mainAssistantCreatePanel.add(dsAssistantCreatePanel,
				dsAssistantCreatePanel.getName());
		mainAssistantCreatePanel.add(dsAssistantStep1Panel,
				dsAssistantStep1Panel.getName());
		mainAssistantCreatePanel.add(dsAssistantStep2Panel,
				dsAssistantStep2Panel.getName());
		mainAssistantCreatePanel.add(dsAssistantStep3Panel,
				dsAssistantStep3Panel.getName());
		
		view.addMainPanel(mainAssistantCreatePanel); 
		
		System.out.println("anadido panel " + dsAssistantCreatePanel.getName());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		DataSourceConfig dsConfig = new DataSourceConfig();
		Session.add(Constants.DATASOURCE_FORM_SESSION_KEY,
				dsConfig);

		System.out.println("");
		
		((CardLayout)mainAssistantCreatePanel.getLayout()).
			show(mainAssistantCreatePanel,
				dsAssistantCreatePanel.getName());
		
		view.loadMainPanel(mainAssistantCreatePanel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
