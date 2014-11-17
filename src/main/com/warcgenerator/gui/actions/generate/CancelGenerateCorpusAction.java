package com.warcgenerator.gui.actions.generate;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.generate.GeneratingCorpusDialog;

@SuppressWarnings("serial")
public class CancelGenerateCorpusAction 
	extends AbstractAction {	
	private WarcGeneratorGUI view;
	private GCGenerateAction gcGenerateAction;
	private GeneratingCorpusDialog gcd;
	
	public CancelGenerateCorpusAction(WarcGeneratorGUI view,
			GeneratingCorpusDialog gcd,
			GCGenerateAction gcGenerateAction) {
		this.view = view;
		this.gcGenerateAction = gcGenerateAction;
		this.gcd = gcd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int userSelection = JOptionPane.showConfirmDialog(view.getMainFrame(),
				"Esta seguro que desea cancelar el proceso?");
		if (userSelection == JOptionPane.OK_OPTION) {
			gcGenerateAction.getGcTask().cancel(true);
			gcd.getCancelBtn().setEnabled(false);
		}
	}
}
