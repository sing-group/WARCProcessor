package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.warcgenerator.AppWarc;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.helper.GUIConfigHelper;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.InitConfigDialog;

/**
 * In java AbstractAction implements CommandPattern
 * http://www.javapractices.com/topic/TopicAction.do?Id=159
 * @author amparop
 *
 */
public class StartGUIAction extends AbstractAction {
	private AppWarc appWarc;
	private GUIConfig guiConfig;
	private WarcGeneratorGUI window;
	private InitConfigDialog initConfigDialog;
	
	public StartGUIAction(AppWarc appWarc) {
		this.appWarc = appWarc;
		
		// Get the guiConfig
		guiConfig = new GUIConfig();
		GUIConfigHelper.configure(
				Constants.DEFAULT_GUI_CONFIG_XML, guiConfig);
		Session.add(Constants.GUI_CONFIG_SESSION_KEY, guiConfig);
		
		window = new WarcGeneratorGUI(appWarc.getAppLogic());
		
		// Start config dialog
		initConfigDialog = new InitConfigDialog(appWarc.getAppLogic(),
				window);
		initConfigDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.setVisible(true);
		// Load recent config files
		for(String configFile:guiConfig.getRecentConfigFiles()) {
			initConfigDialog.addConfigFile(configFile);
		}
		initConfigDialog.setVisible(true);
	}
}
