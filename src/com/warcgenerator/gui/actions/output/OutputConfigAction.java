package com.warcgenerator.gui.actions.output;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.output.OutputConfigPanel;

public class OutputConfigAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private OutputConfigPanel configPanel;
	
	public OutputConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			OutputConfigPanel configPanel) {
		this.logic = logic;
		this.view = view;
		this.configPanel = configPanel;
		view.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		AppConfig config = logic.getAppConfig();
		
		configPanel.getOutputDirTField().setValue(
				config.getOutputConfig().getOutputDir());
		configPanel.getSpamDirTField().setValue(
				config.getSpamDirName());
		configPanel.getHamDirTField().setValue(
				config.getHamDirName());
		
		view.loadMainPanel(configPanel);
	}
	
	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			Object isModified = Session.get(Constants.FORM_MODIFIED_SESSION_KEY);
			Boolean formModified = false;
			if (isModified instanceof Boolean) {
				formModified = (Boolean) isModified;
			}
			
			System.out.println("OutputConfig Es visible: ----> " + configPanel.isVisible());
			
			if (formModified && configPanel.isVisible()
					&& ((Object[])message)[0].
						equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane
						.showConfirmDialog(view.getMainFrame(),
								"Existen cambios no guardados. ¿Desea guardar los cambios?");
				
				if (userSelection == JOptionPane.OK_OPTION) {
					configPanel.save();
					JPanel newPanel = (JPanel)((Object[])message)[1];
					view.loadMainPanel(newPanel);
				} else if (userSelection == JOptionPane.NO_OPTION) {
					configPanel.rollback();
					JPanel newPanel = (JPanel)((Object[])message)[1];
					view.loadMainPanel(newPanel);
				}
			}
		}
	}
}
