package com.warcgenerator.gui.view.generate;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JProgressBar;

public class GeneratingCorpusDialog extends JDialog {
	private JProgressBar progressBar;
	private JLabel stateLbl;

	public GeneratingCorpusDialog() {
		this.setBounds(0, 0, 300, 200);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Generar corpus ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 10, 0);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 2;
		getContentPane().add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblGenerandoCorpusEn = new JLabel("Generando corpus en la carpeta de salida");
		GridBagConstraints gbc_lblGenerandoCorpusEn = new GridBagConstraints();
		gbc_lblGenerandoCorpusEn.insets = new Insets(0, 0, 5, 0);
		gbc_lblGenerandoCorpusEn.anchor = GridBagConstraints.WEST;
		gbc_lblGenerandoCorpusEn.gridx = 1;
		gbc_lblGenerandoCorpusEn.gridy = 3;
		getContentPane().add(lblGenerandoCorpusEn, gbc_lblGenerandoCorpusEn);
		
		stateLbl = new JLabel("Cargando...");
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.anchor = GridBagConstraints.WEST;
		gbc_stateLbl.insets = new Insets(0, 0, 5, 0);
		gbc_stateLbl.gridx = 1;
		gbc_stateLbl.gridy = 5;
		getContentPane().add(stateLbl, gbc_stateLbl);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_1.gridx = 1;
		gbc_horizontalStrut_1.gridy = 6;
		getContentPane().add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		progressBar = new JProgressBar();
		progressBar.setMaximum(100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 7;
		getContentPane().add(progressBar, gbc_progressBar);
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public JLabel getStateLbl() {
		return stateLbl;
	}
}
