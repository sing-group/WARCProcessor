package com.warcgenerator.gui.view.generate;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.warcgenerator.gui.actions.generate.CancelGenerateCorpusAction;
import com.warcgenerator.gui.actions.generate.GCGenerateAction;
import com.warcgenerator.gui.components.CustomJDialog;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class GeneratingCorpusDialog extends CustomJDialog {
	private JProgressBar progressBar;
	private JLabel stateLbl;
	private WarcGeneratorGUI view;
	private Action cancelGenerateCorpusAction;
	private JButton cancelBtn;
	private JCheckBox openOutputFolderCBox;

	public GeneratingCorpusDialog(WarcGeneratorGUI view,
			GCGenerateAction GCGenerateAction) {
		super(view.getMainFrame(), true);
		//this.setUndecorated(true); 
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		cancelGenerateCorpusAction = new CancelGenerateCorpusAction(view, this,
				GCGenerateAction);
		
		setTitle("Por favor, espere...");
		this.view = view;
		this.setBounds(0, 0, 359, 197);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 55};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.gridwidth = 6;
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 0;
		getContentPane().add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JLabel lblGenerandoCorpusEn = new JLabel("Este proceso puede durar unos minutos.");
		GridBagConstraints gbc_lblGenerandoCorpusEn = new GridBagConstraints();
		gbc_lblGenerandoCorpusEn.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenerandoCorpusEn.anchor = GridBagConstraints.WEST;
		gbc_lblGenerandoCorpusEn.gridx = 2;
		gbc_lblGenerandoCorpusEn.gridy = 1;
		getContentPane().add(lblGenerandoCorpusEn, gbc_lblGenerandoCorpusEn);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(GeneratingCorpusDialog.class.getResource("/com/warcgenerator/gui/resources/img/diagram.png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		stateLbl = new JLabel("Cargando...");
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.anchor = GridBagConstraints.WEST;
		gbc_stateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_stateLbl.gridx = 2;
		gbc_stateLbl.gridy = 2;
		getContentPane().add(stateLbl, gbc_stateLbl);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMaximum(100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridwidth = 3;
		gbc_progressBar.gridx = 2;
		gbc_progressBar.gridy = 3;
		getContentPane().add(progressBar, gbc_progressBar);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 6;
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 4;
		getContentPane().add(horizontalStrut, gbc_horizontalStrut);
		
		openOutputFolderCBox = new JCheckBox("Abrir carpeta de salida al finalizar.");
		openOutputFolderCBox.setSelected(true);
		GridBagConstraints gbc_openOutputFolderCBox = new GridBagConstraints();
		gbc_openOutputFolderCBox.anchor = GridBagConstraints.WEST;
		gbc_openOutputFolderCBox.gridwidth = 3;
		gbc_openOutputFolderCBox.insets = new Insets(0, 0, 5, 5);
		gbc_openOutputFolderCBox.gridx = 2;
		gbc_openOutputFolderCBox.gridy = 5;
		getContentPane().add(openOutputFolderCBox, gbc_openOutputFolderCBox);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.gridwidth = 6;
		gbc_horizontalStrut_3.insets = new Insets(10, 0, 5, 0);
		gbc_horizontalStrut_3.gridx = 0;
		gbc_horizontalStrut_3.gridy = 6;
		getContentPane().add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		cancelBtn = new JButton("Cancelar");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelGenerateCorpusAction.actionPerformed(e);
			}
		});
		GridBagConstraints gbc_cancelBtn = new GridBagConstraints();
		gbc_cancelBtn.gridwidth = 6;
		gbc_cancelBtn.gridx = 0;
		gbc_cancelBtn.gridy = 7;
		getContentPane().add(cancelBtn, gbc_cancelBtn);
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public JLabel getStateLbl() {
		return stateLbl;
	}
	
	public JButton getCancelBtn() {
		return cancelBtn;
	}
	
	public JCheckBox getOpenOutputFolderCBox() {
		return openOutputFolderCBox;
	}

	public void setOpenOutputFolderCBox(JCheckBox openOutputFolderCBox) {
		this.openOutputFolderCBox = openOutputFolderCBox;
	}
}
