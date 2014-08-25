package com.warcgenerator.gui.view.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.warcgenerator.gui.components.CustomJDialog;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

import javax.swing.JPanel;

public class AboutOfDialog extends CustomJDialog {
	private JLabel stateLbl;
	private WarcGeneratorGUI view;
	private JButton okBtn;

	public AboutOfDialog(WarcGeneratorGUI view) {
		super(view.getMainFrame(), true);
		//this.setUndecorated(true); 
		//this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		setTitle("Acerca de WarcGenerator");
		this.view = view;
		this.setBounds(0, 0, 360, 202);
		
		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/warc.png"));
		
		JPanel panelCenter = new JPanel();
		
		JPanel panelLeft = new JPanel();
		getContentPane().add(panelLeft, BorderLayout.WEST);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[] {0, 55, 0};
		gbl_panelLeft.rowHeights = new int[] {15, 58, 0};
		gbl_panelLeft.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelLeft.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelLeft.setLayout(gbl_panelLeft);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_4 = new GridBagConstraints();
		gbc_horizontalStrut_4.insets = new Insets(0, 0, 1, 0);
		gbc_horizontalStrut_4.gridx = 1;
		gbc_horizontalStrut_4.gridy = 0;
		panelLeft.add(horizontalStrut_4, gbc_horizontalStrut_4);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridheight = 2;
		gbc_verticalStrut_1.insets = new Insets(0, 20, 0, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 0;
		panelLeft.add(verticalStrut_1, gbc_verticalStrut_1);
		JLabel lblNewLabel_1 = new JLabel(icon);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelLeft.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		//panelCenter.setLayout(gridBagLayout);
		getContentPane().add(panelCenter, BorderLayout.CENTER);
		
		gridBagLayout.columnWidths = new int[] {0, 30};
		gridBagLayout.rowHeights = new int[] {10, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelCenter.setLayout(gridBagLayout);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_5 = new GridBagConstraints();
		gbc_horizontalStrut_5.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_5.gridx = 2;
		gbc_horizontalStrut_5.gridy = 3;
		panelCenter.add(horizontalStrut_5, gbc_horizontalStrut_5);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.gridwidth = 3;
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_2.gridx = 0;
		gbc_horizontalStrut_2.gridy = 4;
		panelCenter.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		JLabel lblNewLabel = new JLabel("WarcGenerator GUI");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 4;
		panelCenter.add(lblNewLabel, gbc_lblNewLabel);
		
		Box horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.gridwidth = 4;
		gbc_horizontalBox.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalBox.gridx = 0;
		gbc_horizontalBox.gridy = 5;
		panelCenter.add(horizontalBox, gbc_horizontalBox);
		
		JLabel lblGenerandoCorpusEn = new JLabel("Version: [Poner aqui version]");
		GridBagConstraints gbc_lblGenerandoCorpusEn = new GridBagConstraints();
		gbc_lblGenerandoCorpusEn.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenerandoCorpusEn.anchor = GridBagConstraints.WEST;
		gbc_lblGenerandoCorpusEn.gridx = 2;
		gbc_lblGenerandoCorpusEn.gridy = 6;
		panelCenter.add(lblGenerandoCorpusEn, gbc_lblGenerandoCorpusEn);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 10, 0);
		gbc_horizontalStrut.gridx = 3;
		gbc_horizontalStrut.gridy = 8;
		panelCenter.add(horizontalStrut, gbc_horizontalStrut);
		
		stateLbl = new JLabel("Trabajo de fin de grado de Miguel Callon Alvarez\r\n");
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.anchor = GridBagConstraints.WEST;
		gbc_stateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_stateLbl.gridx = 2;
		gbc_stateLbl.gridy = 7;
		panelCenter.add(stateLbl, gbc_stateLbl);
		
		JLabel lblcopyrightEsei = new JLabel("(c) Copyright ESEI 2014/2015");
		GridBagConstraints gbc_lblcopyrightEsei = new GridBagConstraints();
		gbc_lblcopyrightEsei.anchor = GridBagConstraints.WEST;
		gbc_lblcopyrightEsei.insets = new Insets(0, 0, 5, 5);
		gbc_lblcopyrightEsei.gridx = 2;
		gbc_lblcopyrightEsei.gridy = 8;
		panelCenter.add(lblcopyrightEsei, gbc_lblcopyrightEsei);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_3.gridx = 2;
		gbc_horizontalStrut_3.gridy = 11;
		panelCenter.add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.gridx = 3;
		gbc_horizontalStrut_1.gridy = 13;
		panelCenter.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutOfDialog.this.dispose();
			}
		});
		GridBagConstraints gbc_okBtn = new GridBagConstraints();
		gbc_okBtn.insets = new Insets(0, 0, 5, 5);
		gbc_okBtn.anchor = GridBagConstraints.EAST;
		gbc_okBtn.gridwidth = 2;
		gbc_okBtn.gridx = 1;
		gbc_okBtn.gridy = 10;
		panelCenter.add(okBtn, gbc_okBtn);
		
		this.getRootPane().setDefaultButton(okBtn);
	}

	public JButton getOkBtn() {
		return okBtn;
	}
	
}
