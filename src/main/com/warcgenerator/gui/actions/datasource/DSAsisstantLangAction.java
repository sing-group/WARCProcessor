package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.bean.Country;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.components.SortedListModel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantLangPanel;

@SuppressWarnings("serial")
public class DSAsisstantLangAction extends CustomAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel parentAssistant;
	private DSAssistantLangPanel panel;

	public DSAsisstantLangAction(IAppLogic logic, WarcGeneratorGUI view,
			JPanel parentAssistant) {
		super(view, parentAssistant);
		this.view = view;
		this.logic = logic;
		this.parentAssistant = parentAssistant;
	}

	@Override
	public void action(ActionEvent e) {
		DataSourceConfig dsConfig = (DataSourceConfig) Session
				.get(Constants.DATASOURCE_FORM_SESSION_KEY);
		CustomCardLayout cardLayout = ((CustomCardLayout) parentAssistant
				.getLayout());

		cardLayout.next(parentAssistant);
		panel = (DSAssistantLangPanel) cardLayout
				.getCurrentPanel(parentAssistant);

		//List<Country> availableCountries = logic.listAvailableLanguagesFilter();
		List<Country> availableCountries = logic.listNotSelectedLanguages(
				dsConfig.getCountryList());
		
		SortedListModel<Country> model = new SortedListModel<Country>();
		for (Country country : availableCountries) {
			model.add(country);
		}

		SortedListModel<Country> modelSelected = new SortedListModel<Country>();
		
		for (Country country:dsConfig.getCountryList()) {
			modelSelected.add(country);
		}
		
		panel.enableLangSelection(false);
		// If there is some country selected, not all languages option
		// is selected
		if (dsConfig.getCountryList().size() > 0) {
			panel.getRdbtnAllLang().setSelected(false);
			panel.getRdbtnNoAllLang().setSelected(true);
			panel.enableLangSelection(true);
		}
		panel.getListCandidates().setModel(model);
		panel.getListSelected().setModel(modelSelected);

		cardLayout.show(parentAssistant, panel.getName());
	}

	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			if (this.isCurrentAction()
					&& ((Object[]) message)[0]
							.equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane
						.showConfirmDialog(
								view.getMainFrame(),
								"Se perderan los cambios. "
										+ "¿Esta seguro que desea salir del asistente?",
								"Elija una opción",
								JOptionPane.OK_CANCEL_OPTION);

				if (userSelection == JOptionPane.OK_OPTION) {
					panel.rollback();
					Action nextAction = (Action) ((Object[]) message)[1];
					nextAction.actionPerformed(null);
				}
			}
		}

	}
}
