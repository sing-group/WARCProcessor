package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class DSourcesAction extends AbstractAction {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private Action dsCreateAction;
	
	public DSourcesAction(IAppLogic logic, 
			WarcGeneratorGUI view) {
		this.logic = logic;
		this.view = view;
		
		dsCreateAction = new DSAsisstantCreateAction(logic, view);
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (logic.getDataSourceConfigList().size() == 0) {
			dsCreateAction.actionPerformed(e);
		}
	}
}
