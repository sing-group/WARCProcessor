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
		this.setBounds(0, 0, 360, 202);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 315, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_2.gridx = 1;
		gbc_horizontalStrut_2.gridy = 0;
		getContentPane().add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		JLabel lblNewLabel = new JLabel("Generando corpus");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 2;
		getContentPane().add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblGenerandoCorpusEn = new JLabel("Este proceso puede durar unos minutos.");
		GridBagConstraints gbc_lblGenerandoCorpusEn = new GridBagConstraints();
		gbc_lblGenerandoCorpusEn.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenerandoCorpusEn.anchor = GridBagConstraints.WEST;
		gbc_lblGenerandoCorpusEn.gridx = 1;
		gbc_lblGenerandoCorpusEn.gridy = 2;
		getContentPane().add(lblGenerandoCorpusEn, gbc_lblGenerandoCorpusEn);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 10, 0);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 3;
		getContentPane().add(horizontalStrut, gbc_horizontalStrut);
		
		stateLbl = new JLabel("Cargando...");
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.anchor = GridBagConstraints.WEST;
		gbc_stateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_stateLbl.gridx = 1;
		gbc_stateLbl.gridy = 4;
		getContentPane().add(stateLbl, gbc_stateLbl);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMaximum(100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridwidth = 2;
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 5;
		getContentPane().add(progressBar, gbc_progressBar);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_3.gridx = 1;
		gbc_horizontalStrut_3.gridy = 6;
		getContentPane().add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		cancelBtn = new JButton("Cancelar");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelGenerateCorpusAction.actionPerformed(e);
			}
		});
		GridBagConstraints gbc_cancelBtn = new GridBagConstraints();
		gbc_cancelBtn.insets = new Insets(0, 0, 5, 0);
		gbc_cancelBtn.gridwidth = 2;
		gbc_cancelBtn.gridx = 1;
		gbc_cancelBtn.gridy = 7;
		getContentPane().add(cancelBtn, gbc_cancelBtn);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_1.gridx = 2;
		gbc_horizontalStrut_1.gridy = 8;
		getContentPane().add(horizontalStrut_1, gbc_horizontalStrut_1);
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
