package com.warcgenerator.gui.actions.other;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.exception.logic.LogicException;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.other.OtherConfigPanel;

@SuppressWarnings("serial")
public class OtherSaveAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private OtherConfigPanel panel;
	private IAppLogic logic;
	
	public OtherSaveAction(IAppLogic logic,
			WarcGeneratorGUI view,
			OtherConfigPanel panel
			) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		logic.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AppConfig appConfig = logic.getAppConfig();
		
		appConfig.setWebCrawlerTmpStorePath(
				panel.getTempDirTField().getText());
		appConfig.setNumCrawlers(Integer.parseInt(panel.
				getNumberOfCrawlersTField().getText()));
		appConfig.setMaxDepthOfCrawling(Integer.parseInt(
				panel.getDeepCrawlerTField().getText()));
		appConfig.setFollowRedirect(
				panel.getChckbxFollowRedirect().isSelected());
		
		if (validate(appConfig)) {
			try {
				logic.updateAppConfig(appConfig);
			} catch (LogicException ex) {
				ex.printStackTrace();
				
			}
		}
	}

	public boolean validate(AppConfig config) {
		StringBuilder errors = new StringBuilder();
		
		if (errors.length() != 0) {
			ValidationDialog dialog =
					ValidationDialog.getInstance(view);
			dialog.setErroresLabel(errors.toString());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			return false;
		}
		return true;
	}
	
	@Override
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			if (panel.isVisible()) {
				String message = ((LogicCallback)logicCallback).getMessage();
				if (message.equals(IAppLogic.APP_CONFIG_UPDATED_CALLBACK)) {
					JOptionPane.showMessageDialog(view.getMainFrame(), 
							"Los cambios se han guardado");
					panel.commit();
				}
			}
		}
	}
}
