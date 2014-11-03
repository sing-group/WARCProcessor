package com.warcgenerator.gui.actions.output;

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
import com.warcgenerator.gui.view.output.OutputConfigPanel;

public class OutputSaveAndGenerateAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private OutputConfigPanel panel;
	private IAppLogic logic;
	private Action generateCorpusAction;
	private OutputSaveAction outputSaveAction;
	private boolean showGenerateCorpus;
	
	public OutputSaveAndGenerateAction (IAppLogic logic,
			WarcGeneratorGUI view,
			OutputConfigPanel panel,
			OutputSaveAction outputSaveAction) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		this.outputSaveAction = outputSaveAction;
		
		// Add callback
		logic.addObserver(this);
		
		generateCorpusAction =
				new GenerateCorpusAction(logic, view);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		showGenerateCorpus = true;
		outputSaveAction.actionPerformed(e);
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
