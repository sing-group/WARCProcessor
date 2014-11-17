package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.util.Validator;
import com.warcgenerator.gui.actions.CustomAction;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;

@SuppressWarnings("serial")
public class DSAssistantCreateNewDSAction 
	extends CustomAction {	
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private JPanel parentAssistant;
	private DSAssistantStep1Panel panel;
	private DataSourceConfig dsConfig;
	
	public DSAssistantCreateNewDSAction(IAppLogic logic,
			WarcGeneratorGUI view,
			JPanel parentAssistant) {
		super(view, parentAssistant);
		this.view = view;
		this.logic = logic;
		this.parentAssistant = parentAssistant;
	}
	
	@Override
	public void action(ActionEvent e) {
		CustomCardLayout cardLayout = 
				((CustomCardLayout)parentAssistant.getLayout());
		
		cardLayout.next(parentAssistant);
		this.panel = (DSAssistantStep1Panel)
				cardLayout.getCurrentPanel(parentAssistant);
		
		fill();
		cardLayout.show(parentAssistant, panel.getName());
	}

	private void fill() {
		dsConfig = (DataSourceConfig)Session.get(
				Constants.DATASOURCE_FORM_SESSION_KEY);

		panel.getNameJTField().setValue(dsConfig.getName());
		panel.getFolderJTField().setValue(dsConfig.getFilePath());
		
		Collection<DataSourceConfig> dataSourceTypeList = 
				logic.getDataSourceTypesList();
		String[] dsTypeStringArray = 
				new String[dataSourceTypeList.size()];
		int i = 0;
		for (DataSourceConfig dsType:dataSourceTypeList) {
			dsTypeStringArray[i++] = dsType.getName();
		}
		panel.setTipoDSCBoxValues(dsTypeStringArray);
		
		// Select item in combo box
		if (!Validator.isNullOrEmpty(dsConfig.getType())) {
			panel.getTipoDSCBox().getModel().
				setSelectedItem(dsConfig.getType());
		}
		
		if (dsConfig.getFilePath() != null &&
				!dsConfig.getFilePath().isEmpty()) {
			panel.getFileChooser().setCurrentDirectory(new File(
					dsConfig.getFilePath()));
		}
	}
	
	@Override
	public void update(Observable obj, Object message) {
		if (obj == view) {
			if (this.isCurrentAction()
					&& ((Object[])message)[0].
						equals(WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL)) {
				int userSelection = JOptionPane
						.showConfirmDialog(view.getMainFrame(),
								"Se perderan los cambios. "
									+ "¿Esta seguro que desea salir del asistente?",
									"Elija una opci�n",
									JOptionPane.OK_CANCEL_OPTION);
				
				if (userSelection == JOptionPane.OK_OPTION) {
					panel.rollback();
					Action nextAction = (Action)((Object[])message)[1];
					nextAction.actionPerformed(null);
				}
			}
		}
	}
	
}
