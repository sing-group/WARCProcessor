package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

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
public class DSModifyAction extends AbstractAction {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	private JPanel parentAssistant;
	
	public DSModifyAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DataSourceConfig config,
			JPanel parentAssistant) {
		this.logic = logic;
		this.view = view;
		this.config = config;
		this.parentAssistant = parentAssistant;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Session.add(
				Constants.DATASOURCE_FORM_SESSION_KEY,
				config);
		
		DSAssistantCreateNewDSAction nextAction = new
				DSAssistantCreateNewDSAction(logic, view, 
						parentAssistant);
		nextAction.actionPerformed(e);
	}
}
