package com.warcgenerator.gui.actions.general;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.actions.generate.GenerateCorpusAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;

public class GCSaveAndGenerateAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private GeneralConfigPanel panel;
	private IAppLogic logic;
	private Action generateCorpusAction;
	private GCSaveAction gcSaveAction;
	private boolean showGenerateCorpus;
	
	public GCSaveAndGenerateAction() {	
	}
	
	public void init(IAppLogic logic,
			WarcGeneratorGUI view,
			GeneralConfigPanel panel,
			GCSaveAction gcSaveAction) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		this.gcSaveAction = gcSaveAction;
		
		generateCorpusAction =
				new GenerateCorpusAction(logic, view);
		
		// Add callback
		this.logic.addObserver(this);
		gcSaveAction.init(logic, view, panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		showGenerateCorpus = true;
		gcSaveAction.actionPerformed(e);
	}
	
	@Override
	public void update(Observable obj, Object logicCallback) {
		if (obj == logic) {
			String message = ((LogicCallback)logicCallback).getMessage();
			if (showGenerateCorpus == true) {
				if (message.equals(IAppLogic.APP_CONFIG_UPDATED_CALLBACK)) {
					generateCorpusAction.actionPerformed(null);
					showGenerateCorpus = false;
				}
			}
		}
	}
	
}
