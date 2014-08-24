package com.warcgenerator.gui.actions.generate;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.task.GenerateCorpusTask;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.generate.GeneratingCorpusDialog;

public class GCGenerateAction extends AbstractAction {
	private WarcGeneratorGUI view;
	private IAppLogic logic;
	private GenerateCorpusTask gcTask;
	private GeneratingCorpusDialog gcd;

	public GCGenerateAction(IAppLogic logic, WarcGeneratorGUI view) {
		this.view = view;
		this.logic = logic;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
						if (!gcTask.isCancelled()) {
							JOptionPane.showMessageDialog(view.getMainFrame(),
									"El corpus se ha generado con exito.");
						} else {
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
