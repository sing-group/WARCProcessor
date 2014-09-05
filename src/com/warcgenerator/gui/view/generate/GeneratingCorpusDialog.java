package com.warcgenerator.gui.view.generate;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.warcgenerator.gui.actions.generate.CancelGenerateCorpusAction;
import com.warcgenerator.gui.actions.generate.GCGenerateAction;
import com.warcgenerator.gui.components.CustomJDialog;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import javax.swing.ImageIcon;

public class GeneratingCorpusDialog extends CustomJDialog {
	private JProgressBar progressBar;
	private JLabel stateLbl;
	private WarcGeneratorGUI view;
	private Action cancelGenerateCorpusAction;
	private JButton cancelBtn;

	public GeneratingCorpusDialog(WarcGeneratorGUI view,
			GCGenerateAction GCGenerateAction) {
		super(view.getMainFrame(), true);
		//this.setUndecorated(true); 
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		cancelGenerateCorpusAction = new CancelGenerateCorpusAction(view, this,
				GCGenerateAction);
		
		setTitle("Por favor, espere...");
		this.view = view;
		this.setBounds(0, 0, 392, 173);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 55};
		gridBagLayout.rowHeights = new int[] {30, 0, 0, 0, 0, 0, 0, 0, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(GeneratingCorpusDialog.class.getResource("/com/warcgenerator/gui/resources/img/diagram.png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblGenerandoCorpusEn = new JLabel("Este proceso puede durar unos minutos.");
		GridBagConstraints gbc_lblGenerandoCorpusEn = new GridBagConstraints();
		gbc_lblGenerandoCorpusEn.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenerandoCorpusEn.anchor = GridBagConstraints.WEST;
		gbc_lblGenerandoCorpusEn.gridx = 2;
		gbc_lblGenerandoCorpusEn.gridy = 2;
		getContentPane().add(lblGenerandoCorpusEn, gbc_lblGenerandoCorpusEn);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 4;
		gbc_verticalStrut.gridy = 2;
		getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridheight = 4;
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 2;
		getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);
		
		stateLbl = new JLabel("Cargando...");
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.anchor = GridBagConstraints.WEST;
		gbc_stateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_stateLbl.gridx = 2;
		gbc_stateLbl.gridy = 4;
		getContentPane().add(stateLbl, gbc_stateLbl);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMaximum(100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridwidth = 4;
		gbc_progressBar.gridx = 2;
		gbc_progressBar.gridy = 5;
		getContentPane().add(progressBar, gbc_progressBar);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.gridwidth = 6;
		gbc_horizontalStrut_3.insets = new Insets(10, 0, 5, 0);
		gbc_horizontalStrut_3.gridx = 0;
		gbc_horizontalStrut_3.gridy = 6;
		getContentPane().add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		cancelBtn = new JButton("Cancelar");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelGenerateCorpusAction.actionPerformed(e);
			}
		});
		GridBagConstraints gbc_cancelBtn = new GridBagConstraints();
		gbc_cancelBtn.gridwidth = 6;
		gbc_cancelBtn.insets = new Insets(0, 0, 5, 0);
		gbc_cancelBtn.gridx = 0;
		gbc_cancelBtn.gridy = 7;
		getContentPane().add(cancelBtn, gbc_cancelBtn);
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public JLabel getStateLbl() {
		return stateLbl;
	}
	
	public JButton getCancelBtn() {
		return cancelBtn;
	}
}
