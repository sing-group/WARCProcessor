package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * 
 * @author Miguel Callon
 *
 */
@SuppressWarnings("serial")
public class ExitAction extends AbstractAction implements Observer {
	private WarcGeneratorGUI view;
	@SuppressWarnings("unused")
	private IAppLogic logic;

	public ExitAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.logic = logic;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int userSelection = JOptionPane.showConfirmDialog(
				view.getMainFrame(),
				Messages.getString("ExitAction.message.text"),
				Messages.getString("ExitAction.title.text"),
				JOptionPane.OK_CANCEL_OPTION);

		if (userSelection == JOptionPane.OK_OPTION) {
			// Exit from the application
			System.exit(0);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
