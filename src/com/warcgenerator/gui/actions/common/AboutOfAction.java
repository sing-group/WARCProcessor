package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.AboutOfDialog;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class AboutOfAction extends AbstractAction {
	private WarcGeneratorGUI view;
	private AboutOfDialog aboutOfDialog;
	
	public AboutOfAction(WarcGeneratorGUI view) {
		this.view = view;
		
		// Start config dialog
		aboutOfDialog = new AboutOfDialog(view);
		aboutOfDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		aboutOfDialog.pack();
		aboutOfDialog.setVisible(true);
	}
}
