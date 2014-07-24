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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep3BackAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep3CancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep3FinishAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantStep3Panel extends JPanel {
	private Action dsAssistantStep3BackAction;
	private Action dsAssistantStep3CancelAction;
	private Action dsAssistantStep3FinishAction;

	/**
	 * Create the panel.
	 */
	public DSAssistantStep3Panel(IAppLogic logic,
			WarcGeneratorGUI view) {
		dsAssistantStep3BackAction =
				new DSAsisstantStep3BackAction(logic, view);
		dsAssistantStep3CancelAction =
				new DSAsisstantStep3CancelAction(view);
		dsAssistantStep3FinishAction =
				new DSAsisstantStep3FinishAction(logic, view);
		
		setBackground(new Color(230, 230, 250));
		
		JLabel lblNewLabel = new JLabel("Origenes de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setBackground(new Color(230, 230, 250));
		txtpnunOrigenDe.setText("Asegure que la informaci\u00F3n es correcta.");
		
		JButton btnNuevoOrigen = new JButton("Finalizar");
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep3FinishAction.actionPerformed(e);
			}
		});
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep3CancelAction.actionPerformed(e);
			}
		});
		
		JLabel lblPasoDe = new JLabel("Paso 3 de 3");
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep3BackAction.actionPerformed(e);
			}
		});
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(23)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPasoDe)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnVolver)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNuevoOrigen))
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevoOrigen)
						.addComponent(lblPasoDe)
						.addComponent(btnNewButton)
						.addComponent(btnVolver))
					.addGap(21))
		);
		setLayout(groupLayout);

	}
}
