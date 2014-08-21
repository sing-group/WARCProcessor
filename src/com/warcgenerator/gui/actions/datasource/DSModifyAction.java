package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class DSModifyAction extends AbstractAction implements Observer {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	
	public DSModifyAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DataSourceConfig config) {
		this.logic = logic;
		this.view = view;
		this.config = config;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Session.add(
				Constants.DATASOURCE_FORM_SESSION_KEY,
				config);
		
		DSAssistantCreateNewDSAction nextAction = new
				DSAssistantCreateNewDSAction(logic, view);
		nextAction.actionPerformed(e);
	}

	@Override
	public void update(Observable aPublisher, Object aData) {
		// TODO Auto-generated method stub
		
	}
	
	 
}
