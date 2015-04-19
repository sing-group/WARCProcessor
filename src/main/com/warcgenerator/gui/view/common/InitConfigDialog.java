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
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.RecentFileCBItem;
import com.warcgenerator.gui.actions.common.SearchInitConfigAction;
import com.warcgenerator.gui.actions.file.LoadRecentConfigAction;
import com.warcgenerator.gui.components.CustomButton;
import com.warcgenerator.gui.components.CustomComboBoxRenderer;
import com.warcgenerator.gui.components.CustomJDialog;
import com.warcgenerator.gui.components.CustomLabel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

@SuppressWarnings("serial")
public class InitConfigDialog extends CustomJDialog {
	private final JPanel contentPanel = new JPanel();
	private Vector<String> configFilesList;
	private DefaultComboBoxModel<String> comboBoxModel;
	private JComboBox<String> configFilesCBox;
	@SuppressWarnings("unused")
	private IAppLogic logic;
	@SuppressWarnings("unused")
	private WarcGeneratorGUI view;
	private Action loadRecentConfigAction;
	private Action searchInitConfigAction;
	private Map<String, RecentFileCBItem> recentFiles;

	private CustomButton okButton;

	public void addFirstConfigFile(File configFile) {
		if (comboBoxModel.getSize() > 0
				&& comboBoxModel.getElementAt(1).equals(
						CustomComboBoxRenderer.SEPARATOR)) {
			configFilesList.remove(0);
			configFilesList.remove(0);
		}

		RecentFileCBItem recentFile = new RecentFileCBItem(0,
				configFile.getName(), configFile.getAbsolutePath());
		recentFiles.put(recentFile.getPath(), recentFile);

		comboBoxModel.insertElementAt(configFile.getAbsolutePath(), 0);
		comboBoxModel.insertElementAt(CustomComboBoxRenderer.SEPARATOR, 1);
		configFilesCBox.setSelectedIndex(0);
		okButton.setEnabled(true);
	}

	public void addConfigFile(RecentFileCBItem configFile) {
		recentFiles.put(configFile.toString(), configFile);
		configFilesCBox.addItem(configFile.toString());
		okButton.setEnabled(true);
	}

	/**
	 * Create the dialog.
	 * @param logic IAppLogic
	 * @param view WarcGeneratorGUI
	 */
	public InitConfigDialog(final IAppLogic logic, final WarcGeneratorGUI view) {
		super(view.getMainFrame(), true);
		
		searchInitConfigAction = new SearchInitConfigAction(logic, view, this);
		recentFiles = new LinkedHashMap<String, RecentFileCBItem>();

		this.logic = logic;
		this.view = view;

		setName("InitConfigDialog.title.text");
		view.addLocaleChangeListener(this);
		setBounds(100, 100, 449, 174);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 220, 80, 30, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 14, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.gridwidth = 4;
			gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_horizontalStrut.gridx = 1;
			gbc_horizontalStrut.gridy = 0;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			Component verticalStrut = Box.createVerticalStrut(20);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 0;
			gbc_verticalStrut.gridy = 1;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1
					.setIcon(new ImageIcon(
							InitConfigDialog.class
									.getResource("/com/warcgenerator/gui/resources/img/script.png")));
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			CustomLabel lblNewLabel = new CustomLabel();
			lblNewLabel.setName("InitConfigDialog.lblNewLabel.text");
			view.addLocaleChangeListener(lblNewLabel);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 2;
			gbc_lblNewLabel.gridy = 1;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			CustomLabel lblDetermineLaConfiguracin = new CustomLabel();
			lblDetermineLaConfiguracin
					.setName("InitConfigDialog.lblDetermineLaConfiguracin.text");
			view.addLocaleChangeListener(lblDetermineLaConfiguracin);
			GridBagConstraints gbc_lblDetermineLaConfiguracin = new GridBagConstraints();
			gbc_lblDetermineLaConfiguracin.gridwidth = 4;
			gbc_lblDetermineLaConfiguracin.anchor = GridBagConstraints.WEST;
			gbc_lblDetermineLaConfiguracin.insets = new Insets(0, 0, 5, 0);
			gbc_lblDetermineLaConfiguracin.gridx = 1;
			gbc_lblDetermineLaConfiguracin.gridy = 2;
			contentPanel.add(lblDetermineLaConfiguracin,
					gbc_lblDetermineLaConfiguracin);
		}
		{
			configFilesList = new Vector<String>();
			comboBoxModel = new DefaultComboBoxModel<>(configFilesList);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.gridwidth = 3;
			gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
			gbc_horizontalStrut.gridx = 2;
			gbc_horizontalStrut.gridy = 3;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		}
		{
			configFilesCBox = new JComboBox<String>(comboBoxModel);
			configFilesCBox.setRenderer(new CustomComboBoxRenderer<String>());
			GridBagConstraints gbc_configFilesList = new GridBagConstraints();
			gbc_configFilesList.gridwidth = 3;
			gbc_configFilesList.insets = new Insets(0, 0, 0, 5);
			gbc_configFilesList.fill = GridBagConstraints.HORIZONTAL;
			gbc_configFilesList.gridx = 1;
			gbc_configFilesList.gridy = 4;
			contentPanel.add(configFilesCBox, gbc_configFilesList);
		}
		CustomButton examineBtn = new CustomButton();
		examineBtn.setName("InitConfigDialog.examineBtn.text");
		view.addLocaleChangeListener(examineBtn);
		examineBtn.setIcon(new ImageIcon(InitConfigDialog.class
				.getResource("/com/warcgenerator/gui/resources/img/find.png")));
		examineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchInitConfigAction.actionPerformed(e);
			}
		});
		GridBagConstraints gbc_examineBtn = new GridBagConstraints();
		gbc_examineBtn.anchor = GridBagConstraints.WEST;
		gbc_examineBtn.gridx = 4;
		gbc_examineBtn.gridy = 4;
		contentPanel.add(examineBtn, gbc_examineBtn);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new CustomButton();
				okButton.setName("InitConfigDialog.okButton.text");
				view.addLocaleChangeListener(okButton);
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String configFilePath = recentFiles.get(
								configFilesCBox.getSelectedItem().toString())
								.getPath();
						loadRecentConfigAction = new LoadRecentConfigAction(
								logic, view, configFilePath, false);
						loadRecentConfigAction.actionPerformed(e);
						InitConfigDialog.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				CustomButton cancelButton = new CustomButton();
				cancelButton.setName("InitConfigDialog.cancelButton.text");
				view.addLocaleChangeListener(cancelButton);
				
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
