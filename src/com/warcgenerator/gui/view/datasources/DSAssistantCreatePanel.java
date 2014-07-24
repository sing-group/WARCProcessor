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
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAssistantCreateNewDSAction;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantCreatePanel extends JPanel {
	Action dsAssistantCreateNewDSAction;

	/**
	 * Create the panel.
	 */
	public DSAssistantCreatePanel(IAppLogic logic, WarcGeneratorGUI view) {
		dsAssistantCreateNewDSAction = new DSAssistantCreateNewDSAction(logic,
				view);
		
		setBackground(new Color(230, 230, 250));
		
		JLabel lblNewLabel = new JLabel("Origenes de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setBackground(new Color(230, 230, 250));
		txtpnunOrigenDe.setText("Un origen de datos le permite obtener informaci\u00F3n de corpus provenientes de diferentes tipos de ficheros. \r\n\r\nPulse en \"Nuevo Origen\" para comenzar el asistente de configuraci\u00F3n de un nuevo origen de datos. ");
		
		JButton btnNuevoOrigen = new JButton("Nuevo Origen");
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantCreateNewDSAction.actionPerformed(e);
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(305)
							.addComponent(btnNuevoOrigen)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
					.addComponent(btnNuevoOrigen)
					.addGap(22))
		);
		setLayout(groupLayout);

	}
}
