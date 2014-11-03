package com.warcgenerator.gui.view.generate;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.generate.GCGenerateAction;
import com.warcgenerator.gui.components.CustomJDialog;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;

public class GenerateCorpusDialog extends CustomJDialog {
	private GCGenerateAction gcGenerateAction;
	
	private JTextArea summaryConfigTField;
	private JScrollPane scrollPane;
	
	public void setSummaryText(String text) {
		summaryConfigTField.setText(text);
		summaryConfigTField.updateUI();
		summaryConfigTField.repaint();
		scrollPane.updateUI();
		scrollPane.repaint();
	}

	/**
	 * Create the panel.
	 */
	public GenerateCorpusDialog(IAppLogic logic, 
			WarcGeneratorGUI view) {
		super(view.getMainFrame(), true);
		gcGenerateAction = new GCGenerateAction(logic, view, this);
		
		this.setBounds(0, 0, 429, 382);
		this.setTitle("Generar Corpus");
		
		setBackground(new Color(230, 230, 250));
		
		JPanel header = new JPanel();
		getContentPane().add(header, BorderLayout.NORTH);
		GridBagLayout gbl_header = new GridBagLayout();
		gbl_header.columnWidths = new int[]{0, 0, 0, 0};
		gbl_header.rowHeights = new int[] {0, 0, 0, 0};
		gbl_header.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_header.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		header.setLayout(gbl_header);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 0;
		header.add(horizontalStrut, gbc_horizontalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_1.gridx = 2;
		gbc_horizontalStrut_1.gridy = 1;
		header.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 2;
		header.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(GenerateCorpusDialog.class.getResource("/com/warcgenerator/gui/resources/img/load.png")));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		header.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setOpaque(true);
		textPane.setBorder(null);
		textPane.setBackground(new Color(255,255,255,0));
		textPane.setEditable(false);
		textPane.setText(Messages.getString("GenerateCorpusPanel.txtpnunOrigenDe.text")); //$NON-NLS-1$
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 2;
		gbc_textPane.gridy = 2;
		header.add(textPane, gbc_textPane);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(10, 0, 0, 0);
		gbc_horizontalStrut_2.gridx = 2;
		gbc_horizontalStrut_2.gridy = 3;
		header.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		JPanel body = new JPanel();
		getContentPane().add(body, BorderLayout.CENTER);
		
		summaryConfigTField = new JTextArea();
		summaryConfigTField.setColumns(34);
		summaryConfigTField.setRows(14);
		summaryConfigTField.setWrapStyleWord(true);
		body.add(summaryConfigTField);
		
		scrollPane = new JScrollPane(summaryConfigTField);
		body.add(scrollPane);
		
		JPanel foot = new JPanel();
		FlowLayout flowLayout = (FlowLayout) foot.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(foot, BorderLayout.SOUTH);
		
		JButton btnNuevoOrigen = new JButton(Messages.getString("GenerateCorpusPanel.btnNuevoOrigen.text")); //$NON-NLS-1$
		btnNuevoOrigen.setIcon(new ImageIcon(GenerateCorpusDialog.class.getResource("/com/warcgenerator/gui/resources/img/OK.png")));
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gcGenerateAction.actionPerformed(e);
			}
		});
		
		JButton cancelBtn = new JButton(Messages.getString("GenerateCorpusDialog.btnNewButton.text_1")); //$NON-NLS-1$
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		foot.add(cancelBtn);
		btnNuevoOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
		foot.add(btnNuevoOrigen);
		
		JLabel lblNewLabel = new JLabel(Messages.getString("GenerateCorpusPanel.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-1$
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JLabel txtpnunOrigenDe = new JLabel();
		txtpnunOrigenDe.setText(Messages.getString("GenerateCorpusPanel.txtpnunOrigenDe.text")); //$NON-NLS-1$ //$NON-NLS-1$
		
		this.getRootPane().setDefaultButton(btnNuevoOrigen);
		btnNuevoOrigen.requestFocusInWindow();
	}
}
