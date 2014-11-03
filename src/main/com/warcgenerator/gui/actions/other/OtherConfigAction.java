package com.warcgenerator.gui.actions.other;

import java.awt.event.ActionEvent;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.JOptionPane;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.other.OtherConfigPanel;

public class OtherConfigAction 
	extends CustomAction {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private OtherConfigPanel configPanel;
	
	public OtherConfigAction(IAppLogic logic, WarcGeneratorGUI view,
			OtherConfigPanel configPanel) {
		super(view, configPanel);
		this.logic = logic;
		this.view = view;
		this.configPanel = configPanel;
	}
		
	@Override
	public void action(ActionEvent e) {		
		AppConfig config = logic.getAppConfig();
		
		configPanel.getTempDirTField().setValue(
				config.getWebCrawlerTmpStorePath());
		configPanel.getDeepCrawlerTField().setValue(
				config.getMaxDepthOfCrawling());
		configPanel.getNumberOfCrawlersTField().setValue(
				config.getNumCrawlers());		
		configPanel.getChckbxFollowRedirect().setSelected(
				config.getFollowRedirect());

		view.loadMainPanel(configPanel);
	}

	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			if (this.isCurrentAction()
					&& ((Object[])message)[0].
						equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane
						.showConfirmDialog(view.getMainFrame(),
								"Existen cambios no guardados. ¿Desea guardar los cambios?");
				
				if (userSelection == JOptionPane.OK_OPTION) {
					configPanel.save();
					Action nextAction = (Action)((Object[])message)[1];
					nextAction.actionPerformed(null);
				} else if (userSelection == JOptionPane.NO_OPTION) {
					configPanel.rollback();
					Action nextAction = (Action)((Object[])message)[1];
					nextAction.actionPerformed(null);
				}
			}
		}
	}
	
}
