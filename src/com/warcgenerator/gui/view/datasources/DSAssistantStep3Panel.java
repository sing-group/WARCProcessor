package com.warcgenerator.gui.view.datasources;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep3BackAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep3FinishAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantStep3Panel extends JPanel {
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
			WarcGeneratorGUI view) {
		dsAssistantStep3BackAction =
				new DSAsisstantStep3BackAction(logic, view);
		dsAssistantCancelAction =
				new DSAsisstantCancelAction(logic, view);
		dsAssistantStep3FinishAction =
				new DSAsisstantStep3FinishAction(logic, view);
		
		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/database.png"));
		
		setBackground(new Color(230, 230, 250));
		
		JLabel lblNewLabel = new JLabel("Origenes de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
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
		
		JLabel lblNewLabel_1 = new JLabel(icon);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblPasoDe)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnVolver)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNewButton)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNuevoOrigen))
							.addComponent(txtpnunOrigenDe, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
							.addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)))
					.addGap(23))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevoOrigen)
						.addComponent(lblPasoDe)
						.addComponent(btnNewButton)
						.addComponent(btnVolver))
					.addGap(21))
		);
		
		summaryConfigTField = new JTextArea();
		summaryConfigTField.setWrapStyleWord(true);
		scrollPane.setViewportView(summaryConfigTField);
		scrollPane.setBounds(23, 23, 404, 134);
		setLayout(groupLayout);

	}

	
}
