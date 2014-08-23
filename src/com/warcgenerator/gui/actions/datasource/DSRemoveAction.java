package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class DSRemoveAction extends AbstractAction implements Observer {
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private DataSourceConfig config;
	
	public DSRemoveAction(IAppLogic logic, 
			WarcGeneratorGUI view,
			DataSourceConfig config) {
		this.logic = logic;
		this.view = view;
		this.config = config;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int dialogResult = JOptionPane.showConfirmDialog (
				view.getMainFrame(),
				"Esta seguro que desea eliminar el DS " 
						+ config.getName());
		if(dialogResult == JOptionPane.YES_OPTION){
			logic.removeDataSourceConfig(config.getId());
			
			view.buildTree();
			
			JOptionPane.showMessageDialog(
					view.getMainFrame(),
					"DS Eliminado: " + config.getName());
		}
	}

	@Override
	public void update(Observable aPublisher, Object aData) {
		// TODO Auto-generated method stub
		
	}
	
	 
}
