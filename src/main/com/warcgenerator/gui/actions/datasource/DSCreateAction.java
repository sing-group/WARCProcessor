package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
@SuppressWarnings("serial")
public class DSCreateAction extends AbstractAction implements Observer {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private JPanel parentAssistant;
	
	public DSCreateAction(IAppLogic logic, 
			WarcGeneratorGUI view, JPanel parentAssistant) {
		this.logic = logic;
		this.view = view;
		this.parentAssistant = parentAssistant;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfig = new DataSourceConfig();
		Session.add(
				Constants.DATASOURCE_FORM_SESSION_KEY,
				dsConfig);
		
		DSAssistantCreateNewDSAction nextAction = new
				DSAssistantCreateNewDSAction(logic, view, 
						parentAssistant);
		nextAction.actionPerformed(e);
	}

	@Override
	public void update(Observable aPublisher, Object aData) {
		// TODO Auto-generated method stub
	}
	
	 
}
