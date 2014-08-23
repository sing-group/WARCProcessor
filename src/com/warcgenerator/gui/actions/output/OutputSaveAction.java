package com.warcgenerator.gui.actions.output;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;
import com.warcgenerator.gui.view.output.OutputConfigPanel;

public class OutputSaveAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private OutputConfigPanel panel;
	private IAppLogic logic;
	
	public OutputSaveAction(IAppLogic logic,
			WarcGeneratorGUI view,
			OutputConfigPanel panel
			) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig appConfig = logic.getAppConfig();
		
		appConfig.getOutputConfig().setOutputDir(
				panel.getOutputDirTField().getText());
		appConfig.setSpamDirName(panel.getSpamDirTField().getText());
		appConfig.setHamDirName(panel.getHamDirTField().getText());
		
		if (validate(appConfig)) {
			AppConfig config = logic.getAppConfig();
			
			logic.updateAppConfig(config);
			
			System.out.println("Se ha guardado con exito");
		}
	}

	public boolean validate(AppConfig config) {
		StringBuilder errors = new StringBuilder();
		
		if (errors.length() != 0) {
			ValidationDialog dialog =
					ValidationDialog.getInstance();
			dialog.setErroresLabel(errors.toString());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			return false;
		}
		return true;
	}
}
