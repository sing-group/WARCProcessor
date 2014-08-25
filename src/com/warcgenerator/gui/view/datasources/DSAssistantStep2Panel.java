package com.warcgenerator.gui.view.datasources;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
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

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep2BackAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep2ContinueAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantStep2Panel extends JPanel {
	private Action dsAssistantStep2BackAction;
	private Action dsAssistantCancelAction;
	private Action dsAssistantStep2ContinueAction;
	
	private JTable paramsTable;
	public JTable getParamsTable() {
		return paramsTable;
	}

	private JTextField quantityMaxElemsTField;
	private JCheckBox spamEnabledCBox;
	private JCheckBox maxElementsEnabledCBox;
	private JRadioButton spamRButtom;
	private JRadioButton hamRButtom;
	
	/**
	 * Create the panel.
	 */
	public DSAssistantStep2Panel(IAppLogic logic, 
			WarcGeneratorGUI view) {
		dsAssistantStep2BackAction =
				new DSAsisstantStep2BackAction(logic, view);
		dsAssistantCancelAction =
				new DSAsisstantCancelAction(logic, view);
		dsAssistantStep2ContinueAction =
				new DSAsisstantStep2ContinueAction(logic, view, this);
		
		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/database.png"));
		
		setBackground(new Color(230, 230, 250));
		
		JLabel lblNewLabel = new JLabel("Origenes de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setOpaque(false);
		txtpnunOrigenDe.setBackground(new Color(255, 255, 255, 0));
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
				dsAssistantCancelAction.actionPerformed(e);
			}
		});
		
		JLabel lblPasoDe = new JLabel("Paso 2 de 3");
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    spamRButtom = new JRadioButton("S\u00ED");
	    spamRButtom.setSelected(true);
		hamRButtom = new JRadioButton("No");
		
		spamEnabledCBox = new JCheckBox("Habilitado");
		spamEnabledCBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (spamEnabledCBox.isSelected()) {
					spamRButtom.setEnabled(true);
					hamRButtom.setEnabled(true);
				} else {
					spamRButtom.setEnabled(false);
					hamRButtom.setEnabled(false);
				}
			}
		});
		maxElementsEnabledCBox = new JCheckBox("Habilitado");
		maxElementsEnabledCBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (maxElementsEnabledCBox.isSelected()) {
					quantityMaxElemsTField.setEnabled(true);
				} else {
					quantityMaxElemsTField.setEnabled(false);
				}
			}
		});
		
		// Disabled buttoms
		spamRButtom.setEnabled(false);
		hamRButtom.setEnabled(false);
		
	    group.add(spamRButtom);
	    group.add(hamRButtom);
		
		quantityMaxElemsTField = new JTextField();
		quantityMaxElemsTField.setColumns(10);
		
		// Disabled quantity
		quantityMaxElemsTField.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep2BackAction.actionPerformed(e);
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel(icon);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
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
									.addComponent(maxElementsEnabledCBox)
									.addGap(18)
									.addComponent(quantityMaxElemsTField, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(spamEnabledCBox)
									.addGap(18)
									.addComponent(spamRButtom)
									.addGap(18)
									.addComponent(hamRButtom))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_4)
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
							.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)))
					.addGap(22))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4))
					.addGap(18)
					.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(spamEnabledCBox)
						.addComponent(lblNewLabel_1)
						.addComponent(spamRButtom)
						.addComponent(hamRButtom))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(maxElementsEnabledCBox)
						.addComponent(quantityMaxElemsTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevoOrigen)
						.addComponent(lblPasoDe)
						.addComponent(btnNewButton)
						.addComponent(btnVolver))
					.addGap(21))
		);
		
		paramsTable = new JTable();
		paramsTable.setModel(new DefaultTableModel(
				new Object[0][0],
				new String[] {
					"Nombre", "Valor"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
				/*public void setValueAt(Object value, int row, int col) {
					 super.setValueAt(value, row, col);
					 fireTableCellUpdated(row, col);
				}*/

			}
		);
		
		scrollPane.setViewportView(paramsTable);
		setLayout(groupLayout);

	}

	public void setTableModel(Map<String, CustomParamConfig> map) {
		DefaultTableModel model = (DefaultTableModel)paramsTable.getModel();

		for(String key:map.keySet()) {
			model.addRow(new Object[]{key, map.get(key).getValue()});
		}	
	}
	
	public JTextField getQuantityMaxElemsTField() {
		return quantityMaxElemsTField;
	}

	public void setQuantityMaxElemsTField(JTextField quantityMaxElemsTField) {
		this.quantityMaxElemsTField = quantityMaxElemsTField;
	}

	public JCheckBox getSpamEnabledCBox() {
		return spamEnabledCBox;
	}

	public void setSpamEnabledCBox(JCheckBox spamEnabledCBox) {
		this.spamEnabledCBox = spamEnabledCBox;
	}

	public JCheckBox getMaxElementsEnabledCBox() {
		return maxElementsEnabledCBox;
	}

	public void setMaxElementsEnabledCBox(JCheckBox maxElementsEnabledCBox) {
		this.maxElementsEnabledCBox = maxElementsEnabledCBox;
	}

	public JRadioButton getSpamRButtom() {
		return spamRButtom;
	}

	public void setSpamRButtom(JRadioButton spamRButtom) {
		this.spamRButtom = spamRButtom;
	}

	public JRadioButton getHamRButtom() {
		return hamRButtom;
	}

	public void setHamRButtom(JRadioButton hamRButtom) {
		this.hamRButtom = hamRButtom;
	}
}
