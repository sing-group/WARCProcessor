package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.warcgenerator.AppWarc;
import com.warcgenerator.gui.actions.file.SaveAppConfigAction;
import com.warcgenerator.gui.common.Constants;
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
	
	public StartGUIAction(final AppWarc appWarc) {
		this.appWarc = appWarc;
		
		// Get the guiConfig
		guiConfig = new GUIConfig();
		GUIConfigHelper.configure(
				Constants.DEFAULT_GUI_CONFIG_XML, guiConfig);
		Session.add(Constants.GUI_CONFIG_SESSION_KEY, guiConfig);
		
		window = new WarcGeneratorGUI(appWarc.getAppLogic());
		
		window.getMainFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.getMainFrame().addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ExitAction exitAction = new ExitAction(appWarc.getAppLogic(),
						window);
				exitAction.actionPerformed(null);
		    }
			
		});
		
		// Start config dialog if there is not any config file specified
		if (appWarc.getAppLogic().getConfigFilePath() == null ||
				appWarc.getAppLogic().getConfigFilePath().isEmpty()) {
			initConfigDialog = new InitConfigDialog(appWarc.getAppLogic(),
					window);
			initConfigDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.setVisible(true);
		
		if (initConfigDialog != null) {
			for(RecentFileCBItem configFile:guiConfig.getRecentConfigFilesReversed()) {
				initConfigDialog.addConfigFile(configFile);
			}
			initConfigDialog.setVisible(true);
		} else {
			window.selectFirstSelectionableItem();
		}
	}
}
