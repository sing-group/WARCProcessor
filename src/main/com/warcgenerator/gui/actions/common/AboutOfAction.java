package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.AboutOfDialog;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author Miguel Callon
 *
 */
@SuppressWarnings("serial")
public class AboutOfAction extends AbstractAction {
	@SuppressWarnings("unused")
	private WarcGeneratorGUI view;
	private AboutOfDialog aboutOfDialog;
	
	public AboutOfAction(WarcGeneratorGUI view) {
		this.view = view;
		
		// Start config dialog
		aboutOfDialog = new AboutOfDialog(view);
		aboutOfDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		view.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		aboutOfDialog.pack();
		aboutOfDialog.setVisible(true);
	}
}
