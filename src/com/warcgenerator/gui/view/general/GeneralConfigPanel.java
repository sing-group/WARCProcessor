package com.warcgenerator.gui.view.general;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.general.GCSaveAction;
import com.warcgenerator.gui.actions.general.GCSaveAndGenerateAction;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.components.listener.CustomPropertyChangeListener;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.validator.NaturalNumberAndZeroValidator;
import com.warcgenerator.gui.view.common.validator.NaturalNumberValidator;
import com.warcgenerator.gui.view.common.validator.PercentageValidator;

public class GeneralConfigPanel extends CustomJPanel {
	private JFormattedTextField numSitesTField;
	private JFormattedTextField spamHamRationValueTField;
	private JFormattedTextField spamQuantityTField;
	private JRadioButton spamHamRatioRBtn;
	private JRadioButton quantityEnabledRBtn;
	private JSlider slider;
	private JCheckBox onlyActiveSitesEnabledCBox;
	private JCheckBox downloadAgainEnabledCBox;
	private JLabel lblNewLabel_3;
	private JButton saveAndGenerateBtn;
	private JButton saveBtn;
	private GCSaveAndGenerateAction gcSaveAndGenerateAction;
	private GCSaveAction gcSaveAction;
	
	private IAppLogic logic;
	private WarcGeneratorGUI view;
		
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
		
		JLabel lblNewLabel = new JLabel(Messages.getString("GeneralConfigPanel.lblNewLabel.text")); //$NON-NLS-1$
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setBackground(new Color(255, 255, 255, 0));
		txtpnunOrigenDe.setText(Messages.getString("GeneralConfigPanel.txtpnunOrigenDe.text")); //$NON-NLS-1$
		
		saveAndGenerateBtn = new JButton(Messages.getString("GeneralConfigPanel.btnNuevoOrigen.text")); //$NON-NLS-1$
	
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
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("GeneralConfigPanel.lblNewLabel_1.text")); //$NON-NLS-1$
		
		numSitesTField = new JFormattedTextField("");
		numSitesTField.setToolTipText(Messages.getString("GeneralConfigPanel.numSitesTField.toolTipText")); //$NON-NLS-1$
		numSitesTField.setInputVerifier(new NaturalNumberValidator(view.getMainFrame(), numSitesTField, "Field cannot be null... "));
		numSitesTField.setColumns(10);
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		spamHamRatioRBtn = new JRadioButton(Messages.getString("GeneralConfigPanel.spamHamRationEnabledCBox.text")); //$NON-NLS-1$
		spamHamRatioRBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spamQuantityTField.setEnabled(false);
				slider.setEnabled(true);
				spamHamRationValueTField.setEnabled(true);
			}
		});
		
		quantityEnabledRBtn = new JRadioButton(Messages.getString("GeneralConfigPanel.rdbtnNewRadioButton.text")); //$NON-NLS-1$
		quantityEnabledRBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spamQuantityTField.setEnabled(true);
				slider.setEnabled(false);
				spamHamRationValueTField.setEnabled(false);	
			}
		});
		
		group.add(spamHamRatioRBtn);
		group.add(quantityEnabledRBtn);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        int value = (int)source.getValue();
			        spamHamRationValueTField.setValue(Integer.toString(value));
			    }
			}
		});
		
		spamHamRationValueTField = new JFormattedTextField("");
		spamHamRationValueTField.setInputVerifier(
				new PercentageValidator(view.getMainFrame(), spamHamRationValueTField, "Field cannot be null... "));
		spamHamRationValueTField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel(Messages.getString("GeneralConfigPanel.lblNewLabel_2.text")); //$NON-NLS-1$
		
		spamQuantityTField = new JFormattedTextField("");
		spamQuantityTField.setInputVerifier(
				new NaturalNumberAndZeroValidator(view.getMainFrame(), spamQuantityTField, "Field cannot be null... "));
		spamQuantityTField.setColumns(10);
		
		onlyActiveSitesEnabledCBox = new JCheckBox(Messages.getString("GeneralConfigPanel.chckbxNewCheckBox.text")); //$NON-NLS-1$
		downloadAgainEnabledCBox = new JCheckBox(Messages.getString("GeneralConfigPanel.chckbxNewCheckBox_1.text")); //$NON-NLS-1$
		
		saveBtn = new JButton(Messages.getString("GeneralConfigPanel.btnNewButton.text")); //$NON-NLS-1$
		
		gcSaveAction = new GCSaveAction();
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		lblNewLabel_3 = new JLabel(icon); //$NON-NLS-1$
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(numSitesTField, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(183)
							.addComponent(saveBtn)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(saveAndGenerateBtn))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(spamHamRatioRBtn)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spamHamRationValueTField, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(onlyActiveSitesEnabledCBox)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(quantityEnabledRBtn)
											.addGap(18)
											.addComponent(spamQuantityTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(30)))
									.addGap(18)
									.addComponent(downloadAgainEnabledCBox)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_2)))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addGap(18)
					.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(numSitesTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(spamHamRatioRBtn)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(spamHamRationValueTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_2)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(spamQuantityTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(quantityEnabledRBtn))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(onlyActiveSitesEnabledCBox)
						.addComponent(downloadAgainEnabledCBox))
					.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveAndGenerateBtn)
						.addComponent(saveBtn))
					.addGap(22))
		);
		setLayout(groupLayout);
	}
	
	public void save() {
		gcSaveAction.init(logic, view, 
				GeneralConfigPanel.this);
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

	public void setSpamHamRationValueTField(JFormattedTextField spamHamRationValueTField) {
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

	public void setOnlyActiveSitesEnabledCBox(JCheckBox onlyActiveSitesEnabledCBox) {
		this.onlyActiveSitesEnabledCBox = onlyActiveSitesEnabledCBox;
	}

	public JCheckBox getDownloadAgainEnabledCBox() {
		return downloadAgainEnabledCBox;
	}

	public void setDownloadAgainEnabledCBox(JCheckBox downloadAgainEnabledCBox) {
		this.downloadAgainEnabledCBox = downloadAgainEnabledCBox;
	}

	public JButton getSaveAndGenerateBtn() {
		return saveAndGenerateBtn;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}
}
