package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAsisstantCancelAction 
	extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private Action dsAsisstantCreateAction;
	private Action dsAsisstantDetailAction;
	private DataSourceConfig config;
	
	
	public DSAsisstantCancelAction(IAppLogic logic,
			WarcGeneratorGUI view
			) {
		this.view = view;
		this.logic = logic;
		
		config = (DataSourceConfig)Session.get(
				Constants.DATASOURCE_FORM_SESSION_KEY);
		dsAsisstantDetailAction = new DSDetailAction(logic, view,
				config);
		dsAsisstantCreateAction = new DSAsisstantCreateAction(logic, view);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (config.getId() != null) {
			dsAsisstantDetailAction.actionPerformed(e);
		} else {
			dsAsisstantCreateAction.actionPerformed(e);
		}
	}
}
