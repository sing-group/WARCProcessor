package com.warcgenerator.gui.view.general;

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

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.general.GCSaveAction;
import com.warcgenerator.gui.actions.general.GCSaveAndGenerateAction;
import com.warcgenerator.gui.components.CustomButton;
import com.warcgenerator.gui.components.CustomCheckBox;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.components.CustomLabel;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.validator.NaturalNumberAndZeroValidator;
import com.warcgenerator.gui.view.common.validator.NaturalNumberValidator;
import com.warcgenerator.gui.view.common.validator.PercentageValidator;

@SuppressWarnings("serial")
public class GeneralConfigPanel extends CustomJPanel {
	private JFormattedTextField numSitesTField;
	private JFormattedTextField spamHamRationValueTField;
	private JFormattedTextField spamQuantityTField;
	private JRadioButton spamHamRatioRBtn;
	private JRadioButton quantityEnabledRBtn;
	private JSlider slider;
	private CustomCheckBox onlyActiveSitesEnabledCBox;
	private CustomCheckBox downloadAgainEnabledCBox;
	private JLabel lblNewLabel_3;
	private CustomButton saveAndGenerateBtn;
	private CustomButton saveBtn;
	private GCSaveAndGenerateAction gcSaveAndGenerateAction;
	private GCSaveAction gcSaveAction;

	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private CustomLabel lblSeleccioneLosParmetros;
	private Component horizontalStrut;
	private Component verticalStrut;
	private Component horizontalStrut_2;

	/**
	 * Create the panel.
	 */
	public GeneralConfigPanel(final IAppLogic logic, final WarcGeneratorGUI view) {
		super();
		this.logic = logic;
		this.view = view;
		
		this.setName("Configuracion general");
		
		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/application.png"));
		
		setBackground(new Color(230, 230, 250));
		
		saveAndGenerateBtn = new CustomButton();
		saveAndGenerateBtn.setName("GeneralConfigPanel.btnNuevoOrigen.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(saveAndGenerateBtn);
		
		gcSaveAndGenerateAction =
				new GCSaveAndGenerateAction();
						
		
		saveAndGenerateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gcSaveAndGenerateAction.init(
						logic, view, GeneralConfigPanel.this,
						gcSaveAction);
				gcSaveAndGenerateAction.actionPerformed(e);
			}
		});
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		
		saveBtn = new CustomButton();
		saveBtn.setName("GeneralConfigPanel.btnNewButton.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(saveBtn);
		
		gcSaveAction = new GCSaveAction();
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		//panel.setBackground(new Color(230, 230, 250));
		
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 363};
		gbl_panel.rowHeights = new int[] {0, 36, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 2;
		gbc_horizontalStrut.insets = new Insets(5, 0, 5, 0);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 0;
		panel.add(horizontalStrut, gbc_horizontalStrut);
		
		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		lblNewLabel_3 = new JLabel(icon);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 1;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		CustomLabel lblNewLabel = new CustomLabel();
		lblNewLabel.setName("GeneralConfigPanel.lblNewLabel.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(lblNewLabel);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		lblSeleccioneLosParmetros = new CustomLabel();
		lblSeleccioneLosParmetros.setName("GeneralConfigPanel.lblSeleccioneLosParmetros.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(lblSeleccioneLosParmetros);
		GridBagConstraints gbc_lblSeleccioneLosParmetros = new GridBagConstraints();
		gbc_lblSeleccioneLosParmetros.anchor = GridBagConstraints.WEST;
		gbc_lblSeleccioneLosParmetros.gridwidth = 2;
		gbc_lblSeleccioneLosParmetros.gridx = 1;
		gbc_lblSeleccioneLosParmetros.gridy = 2;
		panel.add(lblSeleccioneLosParmetros, gbc_lblSeleccioneLosParmetros);
		
		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		panel_1.add(saveBtn);
		panel_1.add(saveAndGenerateBtn);
		
		panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {0, 190, 45};
		gbl_panel_2.rowHeights = new int[] {0, 0, 0, 0, 26, 84};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.gridwidth = 4;
		gbc_horizontalStrut_2.insets = new Insets(10, 0, 5, 0);
		gbc_horizontalStrut_2.gridx = 0;
		gbc_horizontalStrut_2.gridy = 0;
		panel_2.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		CustomLabel lblNewLabel_1 = new CustomLabel();
		lblNewLabel_1.setName("GeneralConfigPanel.lblNewLabel_1.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(lblNewLabel_1);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		numSitesTField = new JFormattedTextField("");
		GridBagConstraints gbc_numSitesTField = new GridBagConstraints();
		gbc_numSitesTField.fill = GridBagConstraints.HORIZONTAL;
		gbc_numSitesTField.insets = new Insets(0, 0, 5, 5);
		gbc_numSitesTField.gridx = 1;
		gbc_numSitesTField.gridy = 1;
		panel_2.add(numSitesTField, gbc_numSitesTField);
		//numSitesTField.setToolTipText(Messages.getString("GeneralConfigPanel.numSitesTField.toolTipText")); //$NON-NLS-1$
		numSitesTField.setInputVerifier(new NaturalNumberValidator(view.getMainFrame(), numSitesTField, "Numero mayor que 0"));
		numSitesTField.setColumns(10);
		
		spamHamRationValueTField = new JFormattedTextField("");
		GridBagConstraints gbc_spamHamRationValueTField = new GridBagConstraints();
		gbc_spamHamRationValueTField.insets = new Insets(0, 0, 5, 5);
		gbc_spamHamRationValueTField.gridx = 2;
		gbc_spamHamRationValueTField.gridy = 2;
		panel_2.add(spamHamRationValueTField, gbc_spamHamRationValueTField);
		spamHamRationValueTField.setInputVerifier(
				new PercentageValidator(view.getMainFrame(), spamHamRationValueTField, Messages.getString("GeneralConfigPanel.spamHamRationValueTField.error")));
		spamHamRationValueTField.setColumns(2);
		spamHamRatioRBtn = new JRadioButton(Messages.getString("GeneralConfigPanel.spamHamRationEnabledCBox.text")); //$NON-NLS-1$
		GridBagConstraints gbc_spamHamRatioRBtn = new GridBagConstraints();
		gbc_spamHamRatioRBtn.anchor = GridBagConstraints.EAST;
		gbc_spamHamRatioRBtn.insets = new Insets(0, 0, 5, 5);
		gbc_spamHamRatioRBtn.gridx = 0;
		gbc_spamHamRatioRBtn.gridy = 2;
		panel_2.add(spamHamRatioRBtn, gbc_spamHamRatioRBtn);
		spamHamRatioRBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spamQuantityTField.setEnabled(false);
				slider.setEnabled(true);
				spamHamRationValueTField.setEnabled(true);
			}
		});
		
		group.add(spamHamRatioRBtn);
		
		slider = new JSlider();
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 2;
		panel_2.add(slider, gbc_slider);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        int value = (int)source.getValue();
			        spamHamRationValueTField.setValue(Integer.toString(value));
			    }
			}
		});
		
		CustomLabel lblNewLabel_2 = new CustomLabel();
		lblNewLabel_2.setName("GeneralConfigPanel.lblNewLabel_2.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(lblNewLabel_2);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 3;
		gbc_lblNewLabel_2.gridy = 2;
		panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		quantityEnabledRBtn = new JRadioButton(Messages.getString("GeneralConfigPanel.rdbtnNewRadioButton.text")); //$NON-NLS-1$
		GridBagConstraints gbc_quantityEnabledRBtn = new GridBagConstraints();
		gbc_quantityEnabledRBtn.anchor = GridBagConstraints.WEST;
		gbc_quantityEnabledRBtn.insets = new Insets(0, 0, 5, 5);
		gbc_quantityEnabledRBtn.gridx = 0;
		gbc_quantityEnabledRBtn.gridy = 3;
		panel_2.add(quantityEnabledRBtn, gbc_quantityEnabledRBtn);
		quantityEnabledRBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spamQuantityTField.setEnabled(true);
				slider.setEnabled(false);
				spamHamRationValueTField.setEnabled(false);	
			}
		});
		group.add(quantityEnabledRBtn);
		
		spamQuantityTField = new JFormattedTextField("");
		GridBagConstraints gbc_spamQuantityTField = new GridBagConstraints();
		gbc_spamQuantityTField.fill = GridBagConstraints.HORIZONTAL;
		gbc_spamQuantityTField.insets = new Insets(0, 0, 5, 5);
		gbc_spamQuantityTField.gridx = 1;
		gbc_spamQuantityTField.gridy = 3;
		panel_2.add(spamQuantityTField, gbc_spamQuantityTField);
		spamQuantityTField.setInputVerifier(
				new NaturalNumberAndZeroValidator(view.getMainFrame(), spamQuantityTField, Messages.getString("GeneralConfigPanel.spamQuantityTField.error")));
		spamQuantityTField.setColumns(10);
		
		onlyActiveSitesEnabledCBox = new CustomCheckBox();
		onlyActiveSitesEnabledCBox.setName("GeneralConfigPanel.chckbxNewCheckBox.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(onlyActiveSitesEnabledCBox);
		GridBagConstraints gbc_onlyActiveSitesEnabledCBox = new GridBagConstraints();
		gbc_onlyActiveSitesEnabledCBox.gridwidth = 4;
		gbc_onlyActiveSitesEnabledCBox.anchor = GridBagConstraints.EAST;
		gbc_onlyActiveSitesEnabledCBox.insets = new Insets(0, 0, 5, 5);
		gbc_onlyActiveSitesEnabledCBox.gridx = 0;
		gbc_onlyActiveSitesEnabledCBox.gridy = 4;
		panel_2.add(onlyActiveSitesEnabledCBox, gbc_onlyActiveSitesEnabledCBox);
		downloadAgainEnabledCBox = new CustomCheckBox();
		downloadAgainEnabledCBox.setName(
				"GeneralConfigPanel.chckbxNewCheckBox_1.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(downloadAgainEnabledCBox);
		downloadAgainEnabledCBox.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_downloadAgainEnabledCBox = new GridBagConstraints();
		gbc_downloadAgainEnabledCBox.gridwidth = 3;
		gbc_downloadAgainEnabledCBox.anchor = GridBagConstraints.NORTHEAST;
		gbc_downloadAgainEnabledCBox.insets = new Insets(0, 0, 0, 5);
		gbc_downloadAgainEnabledCBox.gridx = 1;
		gbc_downloadAgainEnabledCBox.gridy = 5;
		panel_2.add(downloadAgainEnabledCBox, gbc_downloadAgainEnabledCBox);
		
	}

	public void save() {
		gcSaveAction.init(logic, view, GeneralConfigPanel.this);
		gcSaveAction.actionPerformed(null);
	}

	public JFormattedTextField getNumSitesTField() {
		return numSitesTField;
	}

	public void setNumSitesTField(JFormattedTextField numSitesTField) {
		this.numSitesTField = numSitesTField;
	}

	public JFormattedTextField getSpamHamRationValueTField() {
		return spamHamRationValueTField;
	}

	public void setSpamHamRationValueTField(
			JFormattedTextField spamHamRationValueTField) {
		this.spamHamRationValueTField = spamHamRationValueTField;
	}

	public JFormattedTextField getSpamQuantityTField() {
		return spamQuantityTField;
	}

	public void setSpamQuantityTField(JFormattedTextField spamQuantityTField) {
		this.spamQuantityTField = spamQuantityTField;
	}

	public JRadioButton getSpamHamRatioRBtn() {
		return spamHamRatioRBtn;
	}

	public void setSpamHamRatioRBtn(JRadioButton spamHamRatioRBtn) {
		this.spamHamRatioRBtn = spamHamRatioRBtn;
	}

	public JRadioButton getQuantityEnabledRBtn() {
		return quantityEnabledRBtn;
	}

	public void setQuantityEnabledRBtn(JRadioButton quantityEnabledRBtn) {
		this.quantityEnabledRBtn = quantityEnabledRBtn;
	}

	public JSlider getSlider() {
		return slider;
	}

	public void setSlider(JSlider slider) {
		this.slider = slider;
	}

	public JCheckBox getOnlyActiveSitesEnabledCBox() {
		return onlyActiveSitesEnabledCBox;
	}

	public void setOnlyActiveSitesEnabledCBox(
			CustomCheckBox onlyActiveSitesEnabledCBox) {
		this.onlyActiveSitesEnabledCBox = onlyActiveSitesEnabledCBox;
	}

	public JCheckBox getDownloadAgainEnabledCBox() {
		return downloadAgainEnabledCBox;
	}

	public void setDownloadAgainEnabledCBox(CustomCheckBox downloadAgainEnabledCBox) {
		this.downloadAgainEnabledCBox = downloadAgainEnabledCBox;
	}

	public JButton getSaveAndGenerateBtn() {
		return saveAndGenerateBtn;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}
}
