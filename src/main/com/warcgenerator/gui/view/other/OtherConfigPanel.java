package com.warcgenerator.gui.view.other;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import com.warcgenerator.gui.actions.other.OtherSaveAction;
import com.warcgenerator.gui.actions.other.OtherSaveAndGenerateAction;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.components.listener.CustomDocumentListener;
import com.warcgenerator.gui.util.Messages;
import com.warcgenerator.gui.view.WarcGeneratorGUI;
import com.warcgenerator.gui.view.common.validator.NaturalNumberAndZeroValidator;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class OtherConfigPanel extends CustomJPanel {
	private JFormattedTextField tempDirTField;
	private JFormattedTextField deepCrawlerTField;
	private JFormattedTextField numberOfCrawlersTField;
	private JCheckBox chckbxFollowRedirect;

	private IAppLogic logic;
	private WarcGeneratorGUI view;
	
	private OtherSaveAction otherSaveAction;
	private OtherSaveAndGenerateAction otherSaveAndGenerateAction;
	
	// Create a file chooser
	private JFileChooser fc = new JFileChooser();
	
	/**
	 * Create the panel.
	 */
	public OtherConfigPanel(final IAppLogic logic, final WarcGeneratorGUI view) {
		super();
		setMaximumSize(new Dimension(100, 100));
		this.logic = logic;
		this.view = view;
		
		this.setName("OtherConfigPanel");
		
		otherSaveAction = new OtherSaveAction(logic, view, this);
		otherSaveAndGenerateAction = new OtherSaveAndGenerateAction(
				logic, view, this, otherSaveAction);
		
		ImageIcon icon = new ImageIcon(WarcGeneratorGUI.class.getResource("/com/warcgenerator/gui/resources/img/save.png"));
		
		setBackground(new Color(230, 230, 250));
		
		JButton saveAndGenerateBtn = new JButton("Guardar y generar"); //$NON-NLS-1$
		saveAndGenerateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				otherSaveAndGenerateAction.actionPerformed(e);
			}
		});
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		
		JButton saveBtn = new JButton(Messages.getString("OtherConfigPanel.saveBtn.text")); //$NON-NLS-1$ //$NON-NLS-1$
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 363};
		gbl_panel.rowHeights = new int[] {0, 36, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		//panel.setBackground(new Color(230, 230, 250));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 2;
		gbc_horizontalStrut.insets = new Insets(5, 0, 5, 0);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 0;
		panel.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblNewLabel_3 = new JLabel(new ImageIcon(OtherConfigPanel.class.getResource("/com/warcgenerator/gui/resources/img/equipment.png")));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 1;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel(Messages.getString("OtherConfigPanel.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblOtrosParmetrosDe = new JLabel(Messages.getString("OtherConfigPanel.lblOtrosParmetrosDe.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblOtrosParmetrosDe = new GridBagConstraints();
		gbc_lblOtrosParmetrosDe.anchor = GridBagConstraints.WEST;
		gbc_lblOtrosParmetrosDe.gridwidth = 2;
		gbc_lblOtrosParmetrosDe.gridx = 1;
		gbc_lblOtrosParmetrosDe.gridy = 2;
		panel.add(lblOtrosParmetrosDe, gbc_lblOtrosParmetrosDe);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);
		panel_1.add(saveBtn);
		panel_1.add(saveAndGenerateBtn);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {0, 190, 78};
		gbl_panel_2.rowHeights = new int[] {0, 0, 0, 0, 5, 72};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_2.setLayout(gbl_panel_2);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.gridwidth = 3;
		gbc_horizontalStrut_2.insets = new Insets(10, 0, 5, 0);
		gbc_horizontalStrut_2.gridx = 0;
		gbc_horizontalStrut_2.gridy = 0;
		panel_2.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("OtherConfigPanel.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		tempDirTField = new JFormattedTextField("");
		GridBagConstraints gbc_tempDirTField = new GridBagConstraints();
		gbc_tempDirTField.fill = GridBagConstraints.HORIZONTAL;
		gbc_tempDirTField.insets = new Insets(0, 0, 5, 5);
		gbc_tempDirTField.gridx = 1;
		gbc_tempDirTField.gridy = 1;
		panel_2.add(tempDirTField, gbc_tempDirTField);
		tempDirTField.setEditable(false);
		tempDirTField.setColumns(10);
		
		JButton examineBtn = new JButton(Messages.getString("OtherConfigPanel.btnExaminar.text")); //$NON-NLS-1$
		examineBtn.setIcon(new ImageIcon(OtherConfigPanel.class.getResource("/com/warcgenerator/gui/resources/img/find.png")));
		GridBagConstraints gbc_examineBtn = new GridBagConstraints();
		gbc_examineBtn.insets = new Insets(0, 0, 5, 0);
		gbc_examineBtn.gridx = 2;
		gbc_examineBtn.gridy = 1;
		panel_2.add(examineBtn, gbc_examineBtn);
		examineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(OtherConfigPanel.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					tempDirTField.setValue(file.getAbsolutePath());
				} 
			}
		});
		
		JLabel lblHam = new JLabel(Messages.getString("OtherConfigPanel.lblHam.text_1")); //$NON-NLS-1$ //$NON-NLS-1$
		GridBagConstraints gbc_lblHam = new GridBagConstraints();
		gbc_lblHam.anchor = GridBagConstraints.EAST;
		gbc_lblHam.insets = new Insets(0, 0, 5, 5);
		gbc_lblHam.gridx = 0;
		gbc_lblHam.gridy = 2;
		panel_2.add(lblHam, gbc_lblHam);
		
		numberOfCrawlersTField = new JFormattedTextField("");
		GridBagConstraints gbc_numberOfCrawlersTField = new GridBagConstraints();
		gbc_numberOfCrawlersTField.fill = GridBagConstraints.HORIZONTAL;
		gbc_numberOfCrawlersTField.insets = new Insets(0, 0, 5, 5);
		gbc_numberOfCrawlersTField.gridx = 1;
		gbc_numberOfCrawlersTField.gridy = 2;
		panel_2.add(numberOfCrawlersTField, gbc_numberOfCrawlersTField);
		numberOfCrawlersTField.setInputVerifier(new NaturalNumberAndZeroValidator(view.getMainFrame(), numberOfCrawlersTField, "Numero mayor que 0"));
		numberOfCrawlersTField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel(Messages.getString("OtherConfigPanel.lblNewLabel_2.text")); //$NON-NLS-1$ //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		deepCrawlerTField = new JFormattedTextField("");
		GridBagConstraints gbc_deepCrawlerTField = new GridBagConstraints();
		gbc_deepCrawlerTField.fill = GridBagConstraints.HORIZONTAL;
		gbc_deepCrawlerTField.insets = new Insets(0, 0, 5, 5);
		gbc_deepCrawlerTField.gridx = 1;
		gbc_deepCrawlerTField.gridy = 3;
		panel_2.add(deepCrawlerTField, gbc_deepCrawlerTField);
		deepCrawlerTField.getDocument().addDocumentListener(
				new CustomDocumentListener(view, deepCrawlerTField));
		deepCrawlerTField.setInputVerifier(new NaturalNumberAndZeroValidator(view.getMainFrame(), deepCrawlerTField, "Numero entre 0 y 5"));
		deepCrawlerTField.setColumns(10);
		
		chckbxFollowRedirect = new JCheckBox(Messages.getString("OtherConfigPanel.chckbxSeguirEnlacesSi.text"));
		chckbxFollowRedirect.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_chckbxFollowRedirect = new GridBagConstraints();
		gbc_chckbxFollowRedirect.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxFollowRedirect.anchor = GridBagConstraints.EAST;
		gbc_chckbxFollowRedirect.gridwidth = 3;
		gbc_chckbxFollowRedirect.gridx = 0;
		gbc_chckbxFollowRedirect.gridy = 4;
		panel_2.add(chckbxFollowRedirect, gbc_chckbxFollowRedirect);
		
	}

	public JFormattedTextField getTempDirTField() {
		return tempDirTField;
	}

	public JFormattedTextField getDeepCrawlerTField() {
		return deepCrawlerTField;
	}

	public JFormattedTextField getNumberOfCrawlersTField() {
		return numberOfCrawlersTField;
	}
	
	public JCheckBox getChckbxFollowRedirect() {
		return chckbxFollowRedirect;
	}
	
	@Override
	public void save() {
		otherSaveAction.actionPerformed(null);
	}
}
