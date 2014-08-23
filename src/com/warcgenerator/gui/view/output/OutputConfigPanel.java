package com.warcgenerator.gui.view.output;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.output.OutputSaveAction;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.datasources.DSAssistantStep1Panel;

public class OutputConfigPanel extends JPanel {
	private JTextField outputDirTField;
	private JTextField spamDirTField;
	private JTextField hamDirTField;
	
	private OutputSaveAction outputSaveAction;
	
	// Create a file chooser
	private JFileChooser fc = new JFileChooser();
	
	/**
	 * Create the panel.
	 */
	public OutputConfigPanel(IAppLogic logic, WarcGeneratorGUI view) {
		outputSaveAction = new OutputSaveAction(logic, view, this);
		
		setBackground(new Color(230, 230, 250));
		
		JLabel lblNewLabel = new JLabel("Carpeta de salida"); //$NON-NLS-1$
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setBackground(new Color(230, 230, 250));
		txtpnunOrigenDe.setText("Configure las carpetas de salida del corpus a generar."); //$NON-NLS-1$
		
		JButton saveAndGenerateBtn = new JButton("Guardar y generar"); //$NON-NLS-1$
		saveAndGenerateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("OutputConfigPanel.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-1$
		
		outputDirTField = new JTextField();
		outputDirTField.setColumns(10);
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		
		JButton saveBtn = new JButton(Messages.getString("GeneralConfigPanel.btnNewButton.text")); //$NON-NLS-1$
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputSaveAction.actionPerformed(e);
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel(Messages.getString("OutputConfigPanel.lblNewLabel_2.text")); //$NON-NLS-1$
		
		JLabel lblHam = new JLabel(Messages.getString("OutputConfigPanel.lblHam.text")); //$NON-NLS-1$
		
		spamDirTField = new JTextField();
		spamDirTField.setColumns(10);
		
		hamDirTField = new JTextField();
		hamDirTField.setColumns(10);
		
		JButton examineBtn = new JButton("Examinar"); //$NON-NLS-1$
		examineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(OutputConfigPanel.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					outputDirTField.setText(file.getAbsolutePath());
					// This is where a real application would open the file.
					//log.append("Opening: " + file.getName() + ".");
				} 
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(183)
							.addComponent(saveBtn)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(saveAndGenerateBtn))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblHam)
									.addGap(18)
									.addComponent(hamDirTField))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_2)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(spamDirTField))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(outputDirTField, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(examineBtn)))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(outputDirTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(examineBtn))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(spamDirTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHam)
						.addComponent(hamDirTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(58)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveAndGenerateBtn)
						.addComponent(saveBtn))
					.addGap(22))
		);
		setLayout(groupLayout);

	}

	public JTextField getOutputDirTField() {
		return outputDirTField;
	}

	public JTextField getSpamDirTField() {
		return spamDirTField;
	}

	public JTextField getHamDirTField() {
		return hamDirTField;
	}	
}
