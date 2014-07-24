package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class DSCreateAction extends AbstractAction implements Observer {
	private IAppLogic appLogic;
	private WarcGeneratorGUI view;
	
	public DSCreateAction(IAppLogic appLogic, 
			WarcGeneratorGUI view) {
		this.appLogic = appLogic;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfig = new DataSourceConfig();
		appLogic.addDataSourceConfig(dsConfig);
	}

	@Override
	public void update(Observable aPublisher, Object aData) {
		// TODO Auto-generated method stub
		
	}
	
	 
}
