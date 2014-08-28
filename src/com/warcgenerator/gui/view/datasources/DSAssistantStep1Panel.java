package com.warcgenerator.gui.view.datasources;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep1ContinueAction;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantStep1Panel extends CustomJPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Action dsAssistantStep1ContinueAction;
	private Action dsAssistantCancelAction;

	private JFormattedTextField nameJTField;
	private JFormattedTextField folderJTField;
	private JComboBox tipoDSCBox;

	// Create a file chooser
	private JFileChooser fc = new JFileChooser();

	/**
	 * Create the panel.
	 */
	public DSAssistantStep1Panel(IAppLogic logic, WarcGeneratorGUI view,
			JPanel parentAssistant) {
		super();
		dsAssistantStep1ContinueAction = new DSAsisstantStep1ContinueAction(
				logic, view, this, parentAssistant);
		dsAssistantCancelAction = new DSAsisstantCancelAction(logic,
				view, parentAssistant);
		
		this.setName("DSAssistantStep1Panel");

		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/database.png"));
		
		setBackground(new Color(230, 230, 250));

		JLabel lblNewLabel = new JLabel("Origenes de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setOpaque(false);
		txtpnunOrigenDe.setBackground(new Color(255, 255, 255, 0));
		txtpnunOrigenDe
				.setText("Un origen de permite definir una localizaci\u00F3n de ficheros de entrada y el tipo de corpus que contiene.");
		
		JButton btnNuevoOrigen = new JButton("Continuar");
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantStep1ContinueAction.actionPerformed(e);
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Nombre:");

		JLabel lblNewLabel_2 = new JLabel("Carpeta:");

		JLabel lblNewLabel_3 = new JLabel("Tipo:");

		nameJTField = new JFormattedTextField();
		nameJTField.setColumns(10);

		folderJTField = new JFormattedTextField();
		folderJTField.setEditable(false);
		folderJTField.setColumns(10);

		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantCancelAction.actionPerformed(e);
			}
		});

		JLabel lblPasoDe = new JLabel("Paso 1 de 3");

		tipoDSCBox = new JComboBox();

		JButton btnBuscar = new JButton("Examinar");
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(DSAssistantStep1Panel.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					folderJTField.setText(file.getAbsolutePath());
					// This is where a real application would open the file.
					//log.append("Opening: " + file.getName() + ".");
				} else {
					//log.append("Open command cancelled by user.");
				}
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel(icon);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel_2))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(nameJTField, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(folderJTField)
										.addGap(18)
										.addComponent(btnBuscar)
										.addPreferredGap(ComponentPlacement.RELATED))
									.addComponent(tipoDSCBox, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblPasoDe)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNewButton)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNuevoOrigen))
							.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(23, Short.MAX_VALUE))
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
						.addComponent(lblNewLabel_1)
						.addComponent(nameJTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(folderJTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2)))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBuscar)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(lblNewLabel_3))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tipoDSCBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevoOrigen)
						.addComponent(lblPasoDe)
						.addComponent(btnNewButton))
					.addGap(21))
		);
		setLayout(groupLayout);

	}

	public void setTipoDSCBoxValues(String[] values) {
		tipoDSCBox.setModel(new DefaultComboBoxModel(values));
	}

	public JFormattedTextField getNameJTField() {
		return nameJTField;
	}

	public void setNameJTField(JFormattedTextField nombreJTField) {
		this.nameJTField = nombreJTField;
	}

	public JFormattedTextField getFolderJTField() {
		return folderJTField;
	}

	public void setFolderJTField(JFormattedTextField carpetaJTField) {
		this.folderJTField = carpetaJTField;
	}

	public void setTipoDSCBox(JComboBox tipoDSCBox) {
		this.tipoDSCBox = tipoDSCBox;
	}

	public JComboBox getTipoDSCBox() {
		return tipoDSCBox;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}
}
