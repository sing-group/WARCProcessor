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

public class OtherConfigPanel extends CustomJPanel {
	private JFormattedTextField tempDirTField;
	private JFormattedTextField deepCrawlerTField;
	private JFormattedTextField numberOfCrawlersTField;
	
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
		
		JLabel lblNewLabel = new JLabel(Messages.getString("OtherConfigPanel.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-1$
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setOpaque(false);
		txtpnunOrigenDe.setBackground(new Color(255, 255, 255, 0));
		txtpnunOrigenDe.setText(Messages.getString("OtherConfigPanel.txtpnunOrigenDe.text")); //$NON-NLS-1$ //$NON-NLS-1$
		
		JButton saveAndGenerateBtn = new JButton("Guardar y generar"); //$NON-NLS-1$
		saveAndGenerateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				otherSaveAndGenerateAction.actionPerformed(e);
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("OtherConfigPanel.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
		
		tempDirTField = new JFormattedTextField("");
		tempDirTField.setColumns(10);
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		
		JButton saveBtn = new JButton(Messages.getString("OtherConfigPanel.saveBtn.text")); //$NON-NLS-1$ //$NON-NLS-1$
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel(Messages.getString("OtherConfigPanel.lblNewLabel_2.text")); //$NON-NLS-1$ //$NON-NLS-1$
		
		JLabel lblHam = new JLabel(Messages.getString("OtherConfigPanel.lblHam.text_1")); //$NON-NLS-1$ //$NON-NLS-1$
		
		deepCrawlerTField = new JFormattedTextField("");
		deepCrawlerTField.getDocument().addDocumentListener(
				new CustomDocumentListener(view, deepCrawlerTField));
		deepCrawlerTField.setInputVerifier(new NaturalNumberAndZeroValidator(view.getMainFrame(), deepCrawlerTField, "Numero entre 0 y 5"));
		deepCrawlerTField.setColumns(10);
		
		numberOfCrawlersTField = new JFormattedTextField("");
		numberOfCrawlersTField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel(new ImageIcon(OtherConfigPanel.class.getResource("/com/warcgenerator/gui/resources/img/equipment.png")));
		
		JButton examineBtn = new JButton(Messages.getString("OtherConfigPanel.btnExaminar.text")); //$NON-NLS-1$
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
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(183)
							.addComponent(saveBtn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(saveAndGenerateBtn))
						.addComponent(txtpnunOrigenDe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_2)
								.addComponent(lblHam)
								.addComponent(lblNewLabel_1))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tempDirTField, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(numberOfCrawlersTField, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
									.addComponent(deepCrawlerTField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(examineBtn)))
					.addGap(49))
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
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tempDirTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(examineBtn)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(deepCrawlerTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHam)
						.addComponent(numberOfCrawlersTField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveBtn)
						.addComponent(saveAndGenerateBtn))
					.addGap(22))
		);
		setLayout(groupLayout);

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
	
	@Override
	public void save() {
		otherSaveAction.actionPerformed(null);
	}
}
