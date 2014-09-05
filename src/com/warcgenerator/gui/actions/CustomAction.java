package com.warcgenerator.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public abstract class CustomAction extends AbstractAction implements Observer {
	private JPanel panel;
	private WarcGeneratorGUI view;
	private static DefaultMutableTreeNode currentMenuPath;
	private static DefaultMutableTreeNode nextMenuPath;
	private static CustomAction currentAction;
	
	public CustomAction(WarcGeneratorGUI view) {
		this.view = view;
	}
	
	public CustomAction(WarcGeneratorGUI view, JPanel panel) {
		this(view);
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = Session.get(Constants.FORM_MODIFIED_SESSION_KEY);
		Boolean formModified = false;
		if (obj instanceof Boolean) {
			formModified = (Boolean) obj;
		}

		nextMenuPath = view.getSelectedNode();
		
		if (!formModified) {
			// Check menu 
			currentMenuPath = nextMenuPath;

			if (panel != null) {
				panel.setVisible(false);
			}
			view.deleteObserver(currentAction);
			currentAction = this;
			currentAction.action(e);
			view.addObserver(currentAction);
			if (panel instanceof CustomJPanel) {
				((CustomJPanel)panel).commit();
			}
			
			if (panel != null) {
				panel.setVisible(true);
			}
		} else {
			nextMenuPath = currentMenuPath;
			view.tryChangeMainPanel(this);
		}
		
		if (nextMenuPath != null) {
			view.selectLeftMenu(view.getSelectedMenu(nextMenuPath));
		}
	}
	
	public abstract void action(ActionEvent e);

	public boolean isCurrentAction() {
		return currentAction == this;
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
