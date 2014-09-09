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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.output.OutputSaveAction;
import com.warcgenerator.gui.actions.output.OutputSaveAndGenerateAction;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.validator.NotNullOREmptyValidator;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class OutputConfigPanel extends CustomJPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFormattedTextField outputDirTField;
	private JFormattedTextField spamDirTField;
	private JFormattedTextField hamDirTField;
	private JCheckBox chckbxEliminarLaSalida;
	
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	
	private OutputSaveAction outputSaveAction;
	private OutputSaveAndGenerateAction outputSaveAndGenerateAction;
	
	// Create a file chooser
	private JFileChooser fc = new JFileChooser();
	
	/**
	 * Create the panel.
	 */
	public OutputConfigPanel(final IAppLogic logic, final WarcGeneratorGUI view) {
		super();
		this.logic = logic;
		this.view = view;
		
		this.setName("Configuracion de salida");
		
		outputSaveAction = new OutputSaveAction(logic, view, this);
		outputSaveAndGenerateAction = new OutputSaveAndGenerateAction(
				logic, view, this, outputSaveAction);
		
		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/save.png"));
		
		JButton saveAndGenerateBtn = new JButton("Guardar y generar"); //$NON-NLS-1$
		saveAndGenerateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputSaveAndGenerateAction.actionPerformed(e);
			}
		});
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		
		JButton saveBtn = new JButton(Messages.getString("GeneralConfigPanel.btnNewButton.text")); //$NON-NLS-1$
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		//panel.setBackground(new Color(230, 230, 250));
		
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 363};
		gbl_panel.rowHeights = new int[] {0, 36, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.gridwidth = 2;
		gbc_horizontalStrut_1.insets = new Insets(5, 0, 5, 0);
		gbc_horizontalStrut_1.gridx = 1;
		gbc_horizontalStrut_1.gridy = 0;
		panel.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblNewLabel_3 = new JLabel(icon);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 1;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("Carpeta de salida"); //$NON-NLS-1$
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblConfigureLasCarpetas = new JLabel(Messages.getString("OutputConfigPanel.lblConfigureLasCarpetas.text")); //$NON-NLS-1$
		lblConfigureLasCarpetas.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblConfigureLasCarpetas = new GridBagConstraints();
		gbc_lblConfigureLasCarpetas.gridwidth = 2;
		gbc_lblConfigureLasCarpetas.anchor = GridBagConstraints.WEST;
		gbc_lblConfigureLasCarpetas.gridx = 1;
		gbc_lblConfigureLasCarpetas.gridy = 2;
		panel.add(lblConfigureLasCarpetas, gbc_lblConfigureLasCarpetas);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		panel_1.add(saveBtn);
		panel_1.add(saveAndGenerateBtn);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {0, 190, 82};
		gbl_panel_2.rowHeights = new int[] {0, 0, 33, 29, 35, 72};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0};
		panel_2.setLayout(gbl_panel_2);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 3;
		gbc_horizontalStrut.insets = new Insets(10, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 0;
		panel_2.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("OutputConfigPanel.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-1$
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		outputDirTField = new JFormattedTextField("");
		outputDirTField.setEditable(false);
		outputDirTField.setColumns(10);
		GridBagConstraints gbc_outputDirTField = new GridBagConstraints();
		gbc_outputDirTField.fill = GridBagConstraints.BOTH;
		gbc_outputDirTField.insets = new Insets(0, 0, 5, 5);
		gbc_outputDirTField.gridx = 1;
		gbc_outputDirTField.gridy = 1;
		panel_2.add(outputDirTField, gbc_outputDirTField);
		
		JButton examineBtn = new JButton("Examinar"); //$NON-NLS-1$
		examineBtn.setIcon(new ImageIcon(OutputConfigPanel.class.getResource("/com/warcgenerator/gui/resources/img/find.png")));
		examineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(OutputConfigPanel.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					outputDirTField.setValue(file.getAbsolutePath());
				} 
			}
		});
		GridBagConstraints gbc_examineBtn = new GridBagConstraints();
		gbc_examineBtn.insets = new Insets(0, 0, 5, 5);
		gbc_examineBtn.gridx = 2;
		gbc_examineBtn.gridy = 1;
		panel_2.add(examineBtn, gbc_examineBtn);
		
		JLabel lblNewLabel_2 = new JLabel(Messages.getString("OutputConfigPanel.lblNewLabel_2.text")); //$NON-NLS-1$
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		spamDirTField = new JFormattedTextField("");
		spamDirTField.setInputVerifier(new NotNullOREmptyValidator(view.getMainFrame(), spamDirTField, "Debe especificar un directorio de spam"));
		spamDirTField.setColumns(10);
		GridBagConstraints gbc_spamDirTField = new GridBagConstraints();
		gbc_spamDirTField.fill = GridBagConstraints.BOTH;
		gbc_spamDirTField.insets = new Insets(0, 0, 5, 5);
		gbc_spamDirTField.gridx = 1;
		gbc_spamDirTField.gridy = 2;
		panel_2.add(spamDirTField, gbc_spamDirTField);
		
		JLabel lblHam = new JLabel(Messages.getString("OutputConfigPanel.lblHam.text")); //$NON-NLS-1$
		lblHam.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblHam = new GridBagConstraints();
		gbc_lblHam.fill = GridBagConstraints.BOTH;
		gbc_lblHam.insets = new Insets(0, 0, 5, 5);
		gbc_lblHam.gridx = 0;
		gbc_lblHam.gridy = 3;
		panel_2.add(lblHam, gbc_lblHam);
		
		hamDirTField = new JFormattedTextField("");
		hamDirTField.setInputVerifier(new NotNullOREmptyValidator(view.getMainFrame(), hamDirTField, "Debe especificar un directorio de ham"));
		hamDirTField.setColumns(10);
		GridBagConstraints gbc_hamDirTField = new GridBagConstraints();
		gbc_hamDirTField.fill = GridBagConstraints.BOTH;
		gbc_hamDirTField.insets = new Insets(0, 0, 5, 5);
		gbc_hamDirTField.gridx = 1;
		gbc_hamDirTField.gridy = 3;
		panel_2.add(hamDirTField, gbc_hamDirTField);
		
		chckbxEliminarLaSalida = new JCheckBox(Messages.getString("OutputConfigPanel.chckbxEliminarLaSalida.text"));
		chckbxEliminarLaSalida.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_chckbxEliminarLaSalida = new GridBagConstraints();
		gbc_chckbxEliminarLaSalida.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxEliminarLaSalida.gridwidth = 3;
		gbc_chckbxEliminarLaSalida.fill = GridBagConstraints.BOTH;
		gbc_chckbxEliminarLaSalida.gridx = 0;
		gbc_chckbxEliminarLaSalida.gridy = 4;
		panel_2.add(chckbxEliminarLaSalida, gbc_chckbxEliminarLaSalida);

	}

	public JFormattedTextField getOutputDirTField() {
		return outputDirTField;
	}

	public JFormattedTextField getSpamDirTField() {
		return spamDirTField;
	}

	public JFormattedTextField getHamDirTField() {
		return hamDirTField;
	}
	
	public JCheckBox getChckbxEliminarLaSalida() {
		return chckbxEliminarLaSalida;
	}

	public void setChckbxEliminarLaSalida(JCheckBox chckbxEliminarLaSalida) {
		this.chckbxEliminarLaSalida = chckbxEliminarLaSalida;
	}

	@Override
	public void save() {
		outputSaveAction.actionPerformed(null);
	}
}
