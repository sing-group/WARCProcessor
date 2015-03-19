package com.warcgenerator.gui.actions.common;

import java.awt.event.ActionEvent;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomMenuItem;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.helper.GUIConfigHelper;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

@SuppressWarnings("serial")
public class ChangeLanguageAction extends AbstractAction {
	@SuppressWarnings("unused")
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private Locale newLocale;
	private CustomMenuItem menuItem;

	public ChangeLanguageAction(Locale newLocale, CustomMenuItem menuItem,
			IAppLogic logic, WarcGeneratorGUI view) {
		this.logic = logic;
		this.view = view;
		this.newLocale = newLocale;
		this.menuItem = menuItem;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		CustomMenuItem oldMenuItem = (CustomMenuItem) Session
				.get(Constants.LANGUAGE_MENUITEM_SELECTED_SESSION_KEY);
		if (oldMenuItem != null) {
			oldMenuItem.setIcon(null);
		}

		menuItem.setIcon(new ImageIcon(WarcGeneratorGUI.class
				.getResource("/com/warcgenerator/gui/resources/img/OK.png")));
		Locale.setDefault(newLocale);
		Session.add(Constants.LANGUAGE_MENUITEM_SELECTED_SESSION_KEY, menuItem);

		// Persist language data in gui config file
		GUIConfig guiConfig = (GUIConfig) Session
				.get(Constants.GUI_CONFIG_SESSION_KEY);

		StringBuilder sb = new StringBuilder();
		sb.append(newLocale.getLanguage()).append("-")
				.append(newLocale.getCountry());
		guiConfig.setLanguage(sb.toString());
		GUIConfigHelper.persistConfig(guiConfig);

		view.updateUI();
	}

}
