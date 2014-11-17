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

import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.components.CustomJDialog;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AboutOfDialog extends CustomJDialog {
	private JLabel stateLbl;
	@SuppressWarnings("unused")
	private WarcGeneratorGUI view;
	private JButton okBtn;

	public AboutOfDialog(WarcGeneratorGUI view) {
		super(view.getMainFrame(), true);
		//this.setUndecorated(true); 
		//this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		setTitle("Acerca de " + Constants.APP_NAME);
		this.view = view;
		this.setBounds(0, 0, 387, 246);
		
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
		
		gridBagLayout.columnWidths = new int[] {0, 30, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {10, 0, -8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelCenter.setLayout(gridBagLayout);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_5 = new GridBagConstraints();
		gbc_horizontalStrut_5.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_5.gridx = 2;
		gbc_horizontalStrut_5.gridy = 2;
		panelCenter.add(horizontalStrut_5, gbc_horizontalStrut_5);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.gridwidth = 3;
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_2.gridx = 0;
		gbc_horizontalStrut_2.gridy = 3;
		panelCenter.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		JLabel lblNewLabel = new JLabel(Constants.APP_NAME);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		panelCenter.add(lblNewLabel, gbc_lblNewLabel);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 15);
		gbc_verticalStrut.gridx = 4;
		gbc_verticalStrut.gridy = 3;
		panelCenter.add(verticalStrut, gbc_verticalStrut);
		
		Box horizontalBox = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox = new GridBagConstraints();
		gbc_horizontalBox.gridwidth = 5;
		gbc_horizontalBox.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalBox.gridx = 0;
		gbc_horizontalBox.gridy = 4;
		panelCenter.add(horizontalBox, gbc_horizontalBox);
		
		JLabel lblGenerandoCorpusEn = new JLabel("Versi\u00F3n: " + Constants.APP_VERSION);
		GridBagConstraints gbc_lblGenerandoCorpusEn = new GridBagConstraints();
		gbc_lblGenerandoCorpusEn.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenerandoCorpusEn.anchor = GridBagConstraints.WEST;
		gbc_lblGenerandoCorpusEn.gridx = 2;
		gbc_lblGenerandoCorpusEn.gridy = 5;
		panelCenter.add(lblGenerandoCorpusEn, gbc_lblGenerandoCorpusEn);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 2;
		gbc_horizontalStrut_1.gridy = 6;
		panelCenter.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JLabel lblDirectorDeProyecto = new JLabel("Tutor de TFG:");
		GridBagConstraints gbc_lblDirectorDeProyecto = new GridBagConstraints();
		gbc_lblDirectorDeProyecto.anchor = GridBagConstraints.WEST;
		gbc_lblDirectorDeProyecto.insets = new Insets(0, 0, 5, 5);
		gbc_lblDirectorDeProyecto.gridx = 2;
		gbc_lblDirectorDeProyecto.gridy = 7;
		panelCenter.add(lblDirectorDeProyecto, gbc_lblDirectorDeProyecto);
		
		JLabel lblJosRamnMndez = new JLabel("Jos\u00E9 Ram\u00F3n M\u00E9ndez Reboredo");
		GridBagConstraints gbc_lblJosRamnMndez = new GridBagConstraints();
		gbc_lblJosRamnMndez.anchor = GridBagConstraints.WEST;
		gbc_lblJosRamnMndez.insets = new Insets(0, 0, 5, 5);
		gbc_lblJosRamnMndez.gridx = 3;
		gbc_lblJosRamnMndez.gridy = 7;
		panelCenter.add(lblJosRamnMndez, gbc_lblJosRamnMndez);
		
		JLabel lblSubdirectorDeProyecto = new JLabel("Cotutor del TFG:");
		GridBagConstraints gbc_lblSubdirectorDeProyecto = new GridBagConstraints();
		gbc_lblSubdirectorDeProyecto.anchor = GridBagConstraints.WEST;
		gbc_lblSubdirectorDeProyecto.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubdirectorDeProyecto.gridx = 2;
		gbc_lblSubdirectorDeProyecto.gridy = 8;
		panelCenter.add(lblSubdirectorDeProyecto, gbc_lblSubdirectorDeProyecto);
		
		JLabel lblDavidRuanoOrds = new JLabel("David Ruano Ord\u00E1s");
		GridBagConstraints gbc_lblDavidRuanoOrds = new GridBagConstraints();
		gbc_lblDavidRuanoOrds.anchor = GridBagConstraints.WEST;
		gbc_lblDavidRuanoOrds.insets = new Insets(0, 0, 5, 5);
		gbc_lblDavidRuanoOrds.gridx = 3;
		gbc_lblDavidRuanoOrds.gridy = 8;
		panelCenter.add(lblDavidRuanoOrds, gbc_lblDavidRuanoOrds);
		
		stateLbl = new JLabel("Trabajo de fin de grado de:\r\n");
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.anchor = GridBagConstraints.WEST;
		gbc_stateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_stateLbl.gridx = 2;
		gbc_stateLbl.gridy = 9;
		panelCenter.add(stateLbl, gbc_stateLbl);
		
		JLabel lblMiguelCallonAlvarez = new JLabel("Miguel Call\u00F3n \u00C1lvarez");
		GridBagConstraints gbc_lblMiguelCallonAlvarez = new GridBagConstraints();
		gbc_lblMiguelCallonAlvarez.anchor = GridBagConstraints.WEST;
		gbc_lblMiguelCallonAlvarez.insets = new Insets(0, 0, 5, 5);
		gbc_lblMiguelCallonAlvarez.gridx = 3;
		gbc_lblMiguelCallonAlvarez.gridy = 9;
		panelCenter.add(lblMiguelCallonAlvarez, gbc_lblMiguelCallonAlvarez);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 2;
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 10;
		panelCenter.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblcopyrightEsei = new JLabel("(c) Copyright ESEI 2014/2015");
		GridBagConstraints gbc_lblcopyrightEsei = new GridBagConstraints();
		gbc_lblcopyrightEsei.anchor = GridBagConstraints.WEST;
		gbc_lblcopyrightEsei.insets = new Insets(0, 0, 5, 5);
		gbc_lblcopyrightEsei.gridx = 2;
		gbc_lblcopyrightEsei.gridy = 11;
		panelCenter.add(lblcopyrightEsei, gbc_lblcopyrightEsei);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_6 = new GridBagConstraints();
		gbc_horizontalStrut_6.insets = new Insets(5, 0, 5, 5);
		gbc_horizontalStrut_6.gridx = 2;
		gbc_horizontalStrut_6.gridy = 12;
		panelCenter.add(horizontalStrut_6, gbc_horizontalStrut_6);
		
		JLabel lblIconosWwwahasoftcom = new JLabel("Iconos: www.aha-soft.com");
		GridBagConstraints gbc_lblIconosWwwahasoftcom = new GridBagConstraints();
		gbc_lblIconosWwwahasoftcom.gridwidth = 2;
		gbc_lblIconosWwwahasoftcom.anchor = GridBagConstraints.WEST;
		gbc_lblIconosWwwahasoftcom.insets = new Insets(0, 0, 5, 5);
		gbc_lblIconosWwwahasoftcom.gridx = 2;
		gbc_lblIconosWwwahasoftcom.gridy = 13;
		panelCenter.add(lblIconosWwwahasoftcom, gbc_lblIconosWwwahasoftcom);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.insets = new Insets(15, 0, 0, 5);
		gbc_horizontalStrut_3.gridx = 2;
		gbc_horizontalStrut_3.gridy = 15;
		panelCenter.add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutOfDialog.this.dispose();
			}
		});
		GridBagConstraints gbc_okBtn = new GridBagConstraints();
		gbc_okBtn.insets = new Insets(0, 0, 5, 5);
		gbc_okBtn.anchor = GridBagConstraints.EAST;
		gbc_okBtn.gridwidth = 3;
		gbc_okBtn.gridx = 1;
		gbc_okBtn.gridy = 14;
		panelCenter.add(okBtn, gbc_okBtn);
		
		this.getRootPane().setDefaultButton(okBtn);
	}

	public JButton getOkBtn() {
		return okBtn;
	}
	
}
