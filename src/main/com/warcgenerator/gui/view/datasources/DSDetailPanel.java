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

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSEnableAction;
import com.warcgenerator.gui.actions.datasource.DSModifyAction;
import com.warcgenerator.gui.actions.datasource.DSRemoveAction;
import com.warcgenerator.gui.actions.datasource.OpenInputFolderAction;
import com.warcgenerator.gui.components.CustomButton;
import com.warcgenerator.gui.components.CustomLabel;
import com.warcgenerator.gui.components.CustomTextPane;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

@SuppressWarnings("serial")
public class DSDetailPanel extends JPanel {
	private JTextArea summaryConfigTField;
	private JScrollPane scrollPane;
	private JCheckBox chckbxEnableDS;
	private Action dsModifyAction;
	private Action dsRemoveAction;
	private Action dsEnableAction;
	private Action openInputFolderAction;

	/**
	 * Create the panel.
	 * @param logic IAppLogic
	 * @param view WarcGeneratorGUI
	 * @param config DataSourceConfig
	 * @param parentAssistant JPanel
	 */
	public DSDetailPanel(IAppLogic logic, WarcGeneratorGUI view,
			DataSourceConfig config, JPanel parentAssistant) {
		dsModifyAction = new DSModifyAction(logic, view, config,
				parentAssistant);
		dsRemoveAction = new DSRemoveAction(logic, view, config, this);
		dsEnableAction = new DSEnableAction(logic, view, config, this);
		openInputFolderAction = new OpenInputFolderAction(config, view);

		setName("DSDetailPanel" + config.getId());

		ImageIcon icon = new ImageIcon(
				WarcGeneratorGUI.class
						.getResource("/com/warcgenerator/gui/resources/img/database.png"));

		scrollPane = new JScrollPane();

		summaryConfigTField = new JTextArea();
		summaryConfigTField.setWrapStyleWord(true);
		scrollPane.setViewportView(summaryConfigTField);
		scrollPane.setBounds(23, 23, 404, 134);
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 363 };
		gbl_panel.rowHeights = new int[] { 0, 36, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };
		panel.setLayout(gbl_panel);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 2;
		gbc_horizontalStrut.insets = new Insets(5, 0, 5, 0);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 0;
		panel.add(horizontalStrut, gbc_horizontalStrut);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		panel.add(verticalStrut, gbc_verticalStrut);

		JLabel label = new JLabel((Icon) null);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);

		JLabel lblNewLabel_1 = new JLabel(icon);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		CustomLabel lblNewLabel = new CustomLabel();
		lblNewLabel.setName("DSDetailPanel.lblNewLabel.text"); //$NON-NLS-1$
		view.addLocaleChangeListener(lblNewLabel);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		CustomTextPane txtpnunOrigenDe = new CustomTextPane();
		txtpnunOrigenDe.setName("DSDetailPanel.txtpnunOrigenDe.text");
		view.addLocaleChangeListener(txtpnunOrigenDe);
		GridBagConstraints gbc_txtpnunOrigenDe = new GridBagConstraints();
		gbc_txtpnunOrigenDe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtpnunOrigenDe.anchor = GridBagConstraints.WEST;
		gbc_txtpnunOrigenDe.gridwidth = 2;
		gbc_txtpnunOrigenDe.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnunOrigenDe.gridx = 1;
		gbc_txtpnunOrigenDe.gridy = 2;
		panel.add(txtpnunOrigenDe, gbc_txtpnunOrigenDe);
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setOpaque(false);
		txtpnunOrigenDe.setBackground(new Color(255, 255, 255, 0));

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(10, 0, 0, 0);
		gbc_horizontalStrut_1.gridwidth = 2;
		gbc_horizontalStrut_1.gridx = 1;
		gbc_horizontalStrut_1.gridy = 3;
		panel.add(horizontalStrut_1, gbc_horizontalStrut_1);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setHgap(0);
		panel_3.setBorder(null);
		panel_1.add(panel_3, BorderLayout.WEST);

		CustomButton btnNewButton = new CustomButton();
		btnNewButton.setName("DSDetailPanel.btnNewButton.text");
		view.addLocaleChangeListener(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openInputFolderAction.actionPerformed(null);
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.LEADING);
		btnNewButton
				.setIcon(new ImageIcon(
						DSDetailPanel.class
								.getResource("/com/warcgenerator/gui/resources/img/folder16x16.png")));
		panel_3.add(btnNewButton);

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.EAST);

		chckbxEnableDS = new JCheckBox(
				Messages.getString("DSDetailPanel.chckbxEnableDS.text"));
		panel_4.add(chckbxEnableDS);

		CustomButton removeBtn = new CustomButton();
		removeBtn.setName("DSDetailPanel.removeBtn.text");
		view.addLocaleChangeListener(removeBtn);
		panel_4.add(removeBtn);

		CustomButton btnNuevoOrigen = new CustomButton();
		btnNuevoOrigen.setName("DSDetailPanel.btnNuevoOrigen.text");
		view.addLocaleChangeListener(btnNuevoOrigen);
		panel_4.add(btnNuevoOrigen);
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsModifyAction.actionPerformed(e);
			}
		});
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsRemoveAction.actionPerformed(e);
			}
		});
		chckbxEnableDS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsEnableAction.actionPerformed(e);
			}
		});

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_3.gridx = 1;
		gbc_horizontalStrut_3.gridy = 0;
		panel_2.add(horizontalStrut_3, gbc_horizontalStrut_3);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_2.gridx = 1;
		gbc_horizontalStrut_2.gridy = 1;
		panel_2.add(horizontalStrut_2, gbc_horizontalStrut_2);

		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 0;
		panel_2.add(scrollPane, gbc_scrollPane_1);
		// add(scrollPane);

	}

	public void setSummaryText(String text) {
		summaryConfigTField.setText(text);
		summaryConfigTField.updateUI();
		summaryConfigTField.repaint();
		scrollPane.updateUI();
		scrollPane.repaint();
	}

	public JCheckBox getChckbxEnableDS() {
		return chckbxEnableDS;
	}

}
