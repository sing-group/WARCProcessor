package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAsisstantCancelAction 
	extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private DataSourceConfig config;
	private JPanel parentAssistant;
	
	
	public DSAsisstantCancelAction(IAppLogic logic,
			WarcGeneratorGUI view,
			JPanel parentAssistant
			) {
		this.view = view;
		this.logic = logic;
		this.parentAssistant = parentAssistant;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		((CustomCardLayout)parentAssistant.getLayout()).
			first(parentAssistant);
	}
}
