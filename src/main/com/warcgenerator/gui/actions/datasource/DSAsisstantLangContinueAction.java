package com.warcgenerator.gui.actions.datasource;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.common.bean.Country;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.components.SortedListModel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.ValidationDialog;
import com.warcgenerator.gui.view.datasources.DSAssistantLangPanel;

@SuppressWarnings("serial")
public class DSAsisstantLangContinueAction extends AbstractAction implements
		Observer {
	private WarcGeneratorGUI view;
	private DSAssistantLangPanel panel;
	private IAppLogic logic;
	private Action dsAsisstantStep2Action;

	public DSAsisstantLangContinueAction(IAppLogic logic,
			WarcGeneratorGUI view, DSAssistantLangPanel panel,
			JPanel parentAssistant) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;

		dsAsisstantStep2Action = new DSAsisstantStep2Action(logic, view,
				parentAssistant);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DataSourceConfig dsConfigSrc  = 
				(DataSourceConfig)Session.get(
						Constants.DATASOURCE_FORM_SESSION_KEY);
		DataSourceConfig dsConfig = new DataSourceConfig();
		try {
			BeanUtils.copyProperties(dsConfig, dsConfigSrc);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Country> countryList = new ArrayList<Country>();
		if (panel.getRdbtnNoAllLang().isSelected()) {
			SortedListModel<Country> model = (SortedListModel<Country>)
					panel.getListSelected().getModel();
			Iterator<Country> it = model.iterator();
			
			while (it.hasNext()) {
				countryList.add(it.next());
			}
		}
		dsConfig.setCountryList(countryList);
		
		if (validate(dsConfig)) {
			// Escribiendo los datos en el nuevo panel
			Session.add(
					Constants.DATASOURCE_FORM_SESSION_KEY, dsConfig);
			
			((CustomJPanel)panel).commit();
			dsAsisstantStep2Action.actionPerformed(e);
		}
	}

	public boolean validate(DataSourceConfig dsConfig) {
		StringBuilder errors = new StringBuilder();

		if (panel.getRdbtnNoAllLang().isSelected() &&
				dsConfig.getCountryList().size() == 0) {
			errors.append("Seleccione idiomas<br>");
		}

		if (errors.length() != 0) {
			ValidationDialog dialog = ValidationDialog.getInstance(view);
			dialog.setErroresLabel("<html>" + errors.toString() + "</html>");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			return false;
		}
		return true;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
