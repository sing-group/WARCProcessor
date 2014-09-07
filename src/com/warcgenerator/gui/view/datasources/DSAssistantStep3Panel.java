package com.warcgenerator.gui.view.datasources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep3BackAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep3FinishAction;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantStep3Panel extends CustomJPanel {
	private Action dsAssistantStep3BackAction;
	private Action dsAssistantCancelAction;
	private Action dsAssistantStep3FinishAction;
	
	private JTextArea summaryConfigTField;
	private JScrollPane scrollPane;
	
	public void setSummaryText(String text) {
		summaryConfigTField.setText(text);
		summaryConfigTField.updateUI();
		summaryConfigTField.repaint();
		scrollPane.updateUI();
		scrollPane.repaint();
	}

	/**
	 * Create the panel.
	 */
	public DSAssistantStep3Panel(IAppLogic logic, 
			WarcGeneratorGUI view,
			JPanel parentAssistant) {
		dsAssistantStep3BackAction =
				new DSAsisstantStep3BackAction(logic, view,
						parentAssistant);
		dsAssistantCancelAction =
				new DSAsisstantCancelAction(logic, view,
						parentAssistant);
		dsAssistantStep3FinishAction =
				new DSAsisstantStep3FinishAction(logic, view, this);
		
		setName("DSAssistantStep3Panel");
		
		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/database.png"));
		
		//setBackground(new Color(230, 230, 250));
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setOpaque(false);
		txtpnunOrigenDe.setBackground(new Color(255, 255, 255, 0));
		txtpnunOrigenDe.setText("Asegurese que la informaci\u00F3n es correcta y pulse en \"Finalizar\".");
		
		JButton btnNuevoOrigen = new JButton("Finalizar");
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep3FinishAction.actionPerformed(e);
			}
		});
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantCancelAction.actionPerformed(e);
			}
		});
		
		JLabel lblPasoDe = new JLabel("Paso 3 de 3");
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep3BackAction.actionPerformed(e);
			}
		});
		
		scrollPane = new JScrollPane();
		
		summaryConfigTField = new JTextArea();
		summaryConfigTField.setWrapStyleWord(true);
		scrollPane.setViewportView(summaryConfigTField);
		scrollPane.setBounds(23, 23, 404, 134);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		//panel.setBackground(new Color(230, 230, 250));
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 363};
		gbl_panel.rowHeights = new int[] {0, 36, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 2;
		gbc_horizontalStrut.insets = new Insets(5, 0, 5, 0);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 0;
		panel.add(horizontalStrut, gbc_horizontalStrut);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblNewLabel_1 = new JLabel(icon);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Origenes de datos");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JLabel label_3 = new JLabel("Seleccione los par\u00E1metros del corpus a generar.");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.gridwidth = 2;
		gbc_label_3.insets = new Insets(0, 0, 5, 0);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 2;
		panel.add(label_3, gbc_label_3);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(10, 0, 0, 0);
		gbc_horizontalStrut_1.gridwidth = 2;
		gbc_horizontalStrut_1.gridx = 1;
		gbc_horizontalStrut_1.gridy = 3;
		panel.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		panel_1.add(lblPasoDe);
		panel_1.add(btnVolver);
		panel_1.add(btnNewButton);
		panel_1.add(btnNuevoOrigen);
		panel_1.add(txtpnunOrigenDe);
		//panel_1.add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[] {0, 168, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, 0.0};
		panel_2.setLayout(gbl_panel_2);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_3.gridx = 1;
		gbc_horizontalStrut_3.gridy = 0;
		panel_2.add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_2.gridx = 1;
		gbc_horizontalStrut_2.gridy = 2;
		panel_2.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		panel_2.add(scrollPane, gbc_scrollPane_1);

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
}
