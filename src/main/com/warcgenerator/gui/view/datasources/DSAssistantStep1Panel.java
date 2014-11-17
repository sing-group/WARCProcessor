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
import java.io.File;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantStep1ContinueAction;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.validator.NotNullOREmptyValidator;

@SuppressWarnings("serial")
public class DSAssistantStep1Panel extends CustomJPanel {
	private Action dsAssistantStep1ContinueAction;
	private Action dsAssistantCancelAction;

	private JFormattedTextField nameJTField;
	private JFormattedTextField folderJTField;
	private JComboBox<String> tipoDSCBox;

	// Create a file chooser
	private JFileChooser fileChooser;

	/**
	 * Create the panel.
	 */
	public DSAssistantStep1Panel(IAppLogic logic, WarcGeneratorGUI view,
			JPanel parentAssistant) {
		super();
		fileChooser = new JFileChooser();

		dsAssistantStep1ContinueAction = new DSAsisstantStep1ContinueAction(
				logic, view, this, parentAssistant);
		dsAssistantCancelAction = new DSAsisstantCancelAction(logic, view,
				parentAssistant);

		this.setName("DSAssistantStep1Panel");

		ImageIcon icon = new ImageIcon(
				WarcGeneratorGUI.class
						.getResource("/com/warcgenerator/gui/resources/img/database.png"));

		// setBackground(new Color(230, 230, 250));

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

		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantCancelAction.actionPerformed(e);
			}
		});
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		// panel.setBackground(new Color(230, 230, 250));
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 363 };
		gbl_panel.rowHeights = new int[] { 0, 36, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(5, 0, 5, 0);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 0;
		panel.add(horizontalStrut, gbc_horizontalStrut);

		JLabel lblNewLabel_4 = new JLabel(icon);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 1;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		panel.add(verticalStrut, gbc_verticalStrut);

		JLabel label = new JLabel((Icon) null);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);

		JLabel lblNewLabel = new JLabel("Origenes de datos");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JLabel label_2 = new JLabel(
				"Seleccione los par\u00E1metros del corpus a generar.");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.gridwidth = 2;
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 2;
		panel.add(label_2, gbc_label_2);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);

		JLabel lblPasoDe = new JLabel("Paso 1 de 4");
		lblPasoDe.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblPasoDe);
		panel_1.add(btnNewButton);
		panel_1.add(btnNuevoOrigen);
		panel_1.add(txtpnunOrigenDe);

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 190, 82 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.gridwidth = 3;
		gbc_horizontalStrut_2.insets = new Insets(10, 0, 5, 0);
		gbc_horizontalStrut_2.gridx = 0;
		gbc_horizontalStrut_2.gridy = 0;
		panel_2.add(horizontalStrut_2, gbc_horizontalStrut_2);

		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);

		nameJTField = new JFormattedTextField();
		GridBagConstraints gbc_nameJTField = new GridBagConstraints();
		gbc_nameJTField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameJTField.insets = new Insets(0, 0, 5, 5);
		gbc_nameJTField.gridx = 1;
		gbc_nameJTField.gridy = 1;
		panel_2.add(nameJTField, gbc_nameJTField);
		nameJTField.setInputVerifier(new NotNullOREmptyValidator(view
				.getMainFrame(), nameJTField, "Debe especificar un nombre"));
		nameJTField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Carpeta:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);

		folderJTField = new JFormattedTextField();
		GridBagConstraints gbc_folderJTField = new GridBagConstraints();
		gbc_folderJTField.fill = GridBagConstraints.HORIZONTAL;
		gbc_folderJTField.insets = new Insets(0, 0, 5, 5);
		gbc_folderJTField.gridx = 1;
		gbc_folderJTField.gridy = 2;
		panel_2.add(folderJTField, gbc_folderJTField);
		folderJTField.setEditable(false);
		folderJTField.setColumns(10);

		JButton btnBuscar = new JButton("Examinar");
		btnBuscar.setIcon(new ImageIcon(DSAssistantStep1Panel.class
				.getResource("/com/warcgenerator/gui/resources/img/find.png")));
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 0);
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 2;
		panel_2.add(btnBuscar, gbc_btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle(Messages
						.getString(Constants.FILTER_FILE_CFG_DESCRIPTION));
				fileChooser.setAcceptAllFileFilterUsed(false);

				int returnVal = fileChooser
						.showOpenDialog(DSAssistantStep1Panel.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					folderJTField.setText(file.getAbsolutePath());
					// This is where a real application would open the file.
					// log.append("Opening: " + file.getName() + ".");
				} else {
					// log.append("Open command cancelled by user.");
				}
			}
		});

		JLabel lblNewLabel_3 = new JLabel("Tipo:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panel_2.add(lblNewLabel_3, gbc_lblNewLabel_3);

		tipoDSCBox = new JComboBox<String>();
		GridBagConstraints gbc_tipoDSCBox = new GridBagConstraints();
		gbc_tipoDSCBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipoDSCBox.insets = new Insets(0, 0, 0, 5);
		gbc_tipoDSCBox.gridx = 1;
		gbc_tipoDSCBox.gridy = 3;
		panel_2.add(tipoDSCBox, gbc_tipoDSCBox);

	}

	public void setTipoDSCBoxValues(String[] values) {
		tipoDSCBox.setModel(new DefaultComboBoxModel<String>(values));
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

	public void setTipoDSCBox(JComboBox<String> tipoDSCBox) {
		this.tipoDSCBox = tipoDSCBox;
	}

	public JComboBox<String> getTipoDSCBox() {
		return tipoDSCBox;
	}
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}
}
