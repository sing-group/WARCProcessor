package com.warcgenerator.gui.actions.generate;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.task.GenerateCorpusTask;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.generate.GenerateCorpusDialog;
import com.warcgenerator.gui.view.generate.GeneratingCorpusDialog;

public class GCGenerateAction extends AbstractAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private GenerateCorpusTask gcTask;
	private GeneratingCorpusDialog gcd;
	private GenerateCorpusDialog generateCorpusDialog;

	public GCGenerateAction(IAppLogic logic, WarcGeneratorGUI view,
			GenerateCorpusDialog generateCorpusDialog) {
		this.view = view;
		this.logic = logic;
		this.generateCorpusDialog = generateCorpusDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Hide the parent dialog
		generateCorpusDialog.dispose();
		gcd = new GeneratingCorpusDialog(view, this);
		gcTask = new GenerateCorpusTask(logic, view, gcd);
		gcTask.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				// TODO Auto-generated method stub
				if ("progress" == e.getPropertyName()) {
					int progress = (Integer) e.getNewValue();
					gcd.getProgressBar().setValue(progress);
					
					if (gcTask.isDone()) {
						if (gcTask.isCancelled()) {
							JOptionPane.showMessageDialog(view.getMainFrame(),
									"El proceso ha sido cancelado.");
						}
						gcd.dispose();
					}
				}
			}
		});
		gcTask.execute();
		gcd.setVisible(true);
	}

	public GenerateCorpusTask getGcTask() {
		return gcTask;
	}
}
