package com.warcgenerator.gui.actions.other;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.core.logic.LogicCallback;
import com.warcgenerator.gui.actions.generate.GenerateCorpusAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.other.OtherConfigPanel;

@SuppressWarnings("serial")
public class OtherSaveAndGenerateAction 
	extends AbstractAction implements Observer {	
	private WarcGeneratorGUI view;
	private OtherConfigPanel panel;
	private IAppLogic logic;
	private Action generateCorpusAction;
	private OtherSaveAction otherSaveAction;
	private boolean showGenerateCorpus;
	
	public OtherSaveAndGenerateAction (IAppLogic logic,
			WarcGeneratorGUI view,
			OtherConfigPanel panel,
			OtherSaveAction otherSaveAction) {
		this.view = view;
		this.logic = logic;
		this.panel = panel;
		this.otherSaveAction = otherSaveAction;
		
		// Add callback
		logic.addObserver(this);
		
		generateCorpusAction =
				new GenerateCorpusAction(logic, view);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		showGenerateCorpus = true;
		otherSaveAction.actionPerformed(e);
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
