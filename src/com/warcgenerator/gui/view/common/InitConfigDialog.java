package com.warcgenerator.gui.view.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.file.LoadRecentConfigAction;
import com.warcgenerator.gui.components.CustomJDialog;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class InitConfigDialog extends CustomJDialog {
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> configFilesList;
	private IAppLogic logic;
	private WarcGeneratorGUI view;
	private Action loadRecentConfigAction;

	public void addConfigFile(String configFile) {
		configFilesList.addItem(configFile);
	}
	
	/**
	 * Create the dialog.
	 */
	public InitConfigDialog(final IAppLogic logic, 
			final WarcGeneratorGUI view) {
		super(view.getMainFrame(), true);
		
		this.logic = logic;
		this.view = view;
		
		setTitle("Cargar configuracion");
		setBounds(100, 100, 449, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 220, 80, 30, 0};
		gbl_contentPanel.rowHeights = new int[]{14, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 0;
			gbc_verticalStrut.gridy = 0;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		}
		{
			JLabel lblNewLabel = new JLabel("Selecciona una configuracion");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JLabel lblDetermineLaConfiguracin = new JLabel("Determine la configuraci\u00F3n a usar en la aplicaci\u00F3n.");
			GridBagConstraints gbc_lblDetermineLaConfiguracin = new GridBagConstraints();
			gbc_lblDetermineLaConfiguracin.anchor = GridBagConstraints.WEST;
			gbc_lblDetermineLaConfiguracin.insets = new Insets(0, 0, 5, 5);
			gbc_lblDetermineLaConfiguracin.gridx = 1;
			gbc_lblDetermineLaConfiguracin.gridy = 1;
			contentPanel.add(lblDetermineLaConfiguracin, gbc_lblDetermineLaConfiguracin);
		}
		{
			configFilesList = new JComboBox();
			GridBagConstraints gbc_configFilesList = new GridBagConstraints();
			gbc_configFilesList.gridwidth = 2;
			gbc_configFilesList.insets = new Insets(0, 0, 0, 5);
			gbc_configFilesList.fill = GridBagConstraints.HORIZONTAL;
			gbc_configFilesList.gridx = 1;
			gbc_configFilesList.gridy = 3;
			contentPanel.add(configFilesList, gbc_configFilesList);
		}
		{
			JButton examineBtn = new JButton("Examinar");
			examineBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			GridBagConstraints gbc_examineBtn = new GridBagConstraints();
			gbc_examineBtn.anchor = GridBagConstraints.WEST;
			gbc_examineBtn.gridx = 3;
			gbc_examineBtn.gridy = 3;
			contentPanel.add(examineBtn, gbc_examineBtn);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loadRecentConfigAction = new LoadRecentConfigAction(logic,
								view, configFilesList.getSelectedItem().toString(),
								false);
						loadRecentConfigAction.actionPerformed(e);
						InitConfigDialog.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						InitConfigDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}
}
