package com.warcgenerator.gui.view.datasources;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep2BackAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep2CancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep2ContinueAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantStep2Panel extends JPanel {
	private Action dsAssistantStep2BackAction;
	private Action dsAssistantStep2CancelAction;
	private Action dsAssistantStep2ContinueAction;
	
	private JTextField textField;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public DSAssistantStep2Panel(IAppLogic logic, 
			WarcGeneratorGUI view) {
		dsAssistantStep2BackAction =
				new DSAsisstantStep2BackAction(logic, view);
		dsAssistantStep2CancelAction =
				new DSAsisstantStep2CancelAction(view);
		dsAssistantStep2ContinueAction =
				new DSAsisstantStep2ContinueAction(logic, view);
		
		setBackground(new Color(230, 230, 250));
		
		JLabel lblNewLabel = new JLabel("Origenes de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setBackground(new Color(230, 230, 250));
		txtpnunOrigenDe.setText("Configure las opciones del origen de datos.");
		
		JButton btnNuevoOrigen = new JButton("Continuar");
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep2ContinueAction.actionPerformed(e);
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Spam:");
		
		JLabel lblNewLabel_2 = new JLabel("Max. elementos:");
		
		JLabel lblNewLabel_3 = new JLabel("Parametros:");
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep2CancelAction.actionPerformed(e);
			}
		});
		
		JLabel lblPasoDe = new JLabel("Paso 2 de 3");
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Habilitado");
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Habilitado");
		
		JRadioButton rdbtnSpam = new JRadioButton("S\u00ED");
		
		JRadioButton rdbtnNo = new JRadioButton("No");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep2BackAction.actionPerformed(e);
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel_2))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(chckbxNewCheckBox_1)
									.addGap(18)
									.addComponent(textField, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(chckbxNewCheckBox)
									.addGap(18)
									.addComponent(rdbtnSpam)
									.addGap(18)
									.addComponent(rdbtnNo))))
						.addComponent(lblNewLabel)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblPasoDe)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnVolver)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNewButton)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNuevoOrigen))
							.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)))
					.addGap(22))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxNewCheckBox)
						.addComponent(lblNewLabel_1)
						.addComponent(rdbtnSpam)
						.addComponent(rdbtnNo))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(chckbxNewCheckBox_1)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevoOrigen)
						.addComponent(lblPasoDe)
						.addComponent(btnNewButton)
						.addComponent(btnVolver))
					.addGap(21))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
			},
			new String[] {
				"Nombre", "Valor"
			}
		));
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
}
