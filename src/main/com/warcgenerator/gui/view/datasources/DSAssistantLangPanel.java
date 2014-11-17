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
import java.util.List;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.warcgenerator.core.datasource.bean.Country;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCancelAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantLangBackAction;
import com.warcgenerator.gui.actions.datasource.DSAsisstantLangContinueAction;
import com.warcgenerator.gui.components.CountryRenderer;
import com.warcgenerator.gui.components.CustomJPanel;
import com.warcgenerator.gui.components.SortedListModel;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class DSAssistantLangPanel extends CustomJPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Action dsAssistantLangBackAction;
	private Action dsAssistantLangContinueAction;
	private Action dsAssistantCancelAction;

	private JList<Country> listCandidates;
	private JList<Country> listSelected;

	private JButton btnMoveOneRight;
	private JButton btnMoveOneLeft;
	private JButton btnMoveGrpRight;
	private JButton btnMoveGrpLeft;

	private JRadioButton rdbtnAllLang;
	private JRadioButton rdbtnNoAllLang;
	
	/**
	 * Create the panel.
	 */
	public DSAssistantLangPanel(IAppLogic logic, WarcGeneratorGUI view,
			JPanel parentAssistant) {
		super();
		dsAssistantLangBackAction = new DSAsisstantLangBackAction(logic, view,
				parentAssistant);
		dsAssistantLangContinueAction = new DSAsisstantLangContinueAction(
				logic, view, this, parentAssistant);
		dsAssistantCancelAction = new DSAsisstantCancelAction(logic, view,
				parentAssistant);

		this.setName("DSAssistantLangPanel");

		ImageIcon icon = new ImageIcon(
				WarcGeneratorGUI.class
						.getResource("/com/warcgenerator/gui/resources/img/database.png"));

		// setBackground(new Color(230, 230, 250));

		JTextPane txtpnunOrigenDe = new JTextPane();
		txtpnunOrigenDe.setEditable(false);
		txtpnunOrigenDe.setOpaque(false);
		txtpnunOrigenDe.setBackground(new Color(255, 255, 255, 0));
		txtpnunOrigenDe
				.setText("Un origen de permite definir una localizaci\u00F3n de ficheros de entrada y el tipo de corpus que contiene.");

		JButton btnNuevoOrigen = new JButton("Continuar");
		btnNuevoOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantLangContinueAction.actionPerformed(e);
			}
		});

		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dsAssistantCancelAction.actionPerformed(e);
			}
		});
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		// panel.setBackground(new Color(230, 230, 250));
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 363 };
		gbl_panel.rowHeights = new int[] { 0, 36, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(5, 0, 5, 0);
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 0;
		panel.add(horizontalStrut, gbc_horizontalStrut);

		JLabel lblNewLabel_4 = new JLabel(icon);
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 1;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		panel.add(verticalStrut, gbc_verticalStrut);

		JLabel label = new JLabel((Icon) null);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);

		JLabel lblNewLabel = new JLabel("Origenes de datos");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JLabel label_2 = new JLabel(
				"Seleccione los par\u00E1metros del corpus a generar.");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.gridwidth = 2;
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 2;
		panel.add(label_2, gbc_label_2);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel_1, BorderLayout.SOUTH);

		JLabel lblPasoDe = new JLabel("Paso 2 de 4");
		lblPasoDe.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblPasoDe);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dsAssistantLangBackAction.actionPerformed(arg0);
			}
		});
		panel_1.add(btnVolver);
		panel_1.add(btnNewButton);
		panel_1.add(btnNuevoOrigen);
		panel_1.add(txtpnunOrigenDe);

		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 150, 0, 0, 0, 150, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 13, 0, 0, 0, 1, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0 };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalStrut_2.gridwidth = 5;
		gbc_horizontalStrut_2.insets = new Insets(10, 0, 5, 5);
		gbc_horizontalStrut_2.gridx = 1;
		gbc_horizontalStrut_2.gridy = 0;
		panel_2.add(horizontalStrut_2, gbc_horizontalStrut_2);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 10, 5, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 1;
		panel_2.add(verticalStrut_1, gbc_verticalStrut_1);

		JLabel lblNewLabel_1 = new JLabel("Todos los idiomas:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		rdbtnAllLang = new JRadioButton("Sí");
		rdbtnAllLang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableLangSelection(false);
			}
		});
		rdbtnAllLang.setSelected(true);
		GridBagConstraints gbc_rdbtnAllLang = new GridBagConstraints();
		gbc_rdbtnAllLang.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnAllLang.gridx = 3;
		gbc_rdbtnAllLang.gridy = 1;
		panel_2.add(rdbtnAllLang, gbc_rdbtnAllLang);
		group.add(rdbtnAllLang);

		rdbtnNoAllLang = new JRadioButton("No");
		rdbtnNoAllLang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableLangSelection(true);
			}
		});
		GridBagConstraints gbc_rdbtnNoAllLang = new GridBagConstraints();
		gbc_rdbtnNoAllLang.anchor = GridBagConstraints.WEST;
		gbc_rdbtnNoAllLang.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNoAllLang.gridx = 5;
		gbc_rdbtnNoAllLang.gridy = 1;
		panel_2.add(rdbtnNoAllLang, gbc_rdbtnNoAllLang);
		group.add(rdbtnNoAllLang);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(5, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 1;
		gbc_horizontalStrut_1.gridy = 2;
		panel_2.add(horizontalStrut_1, gbc_horizontalStrut_1);

		JScrollPane scrollPane = new JScrollPane();
		listCandidates = new JList<Country>();
		listCandidates.setCellRenderer(new CountryRenderer());
		scrollPane.setViewportView(listCandidates);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 5;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 3;
		panel_2.add(scrollPane, gbc_list);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 5, 5, 10);
		gbc_verticalStrut_3.gridx = 2;
		gbc_verticalStrut_3.gridy = 3;
		panel_2.add(verticalStrut_3, gbc_verticalStrut_3);

		btnMoveOneRight = new JButton(">");
		btnMoveOneRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveSelection(listCandidates, listSelected);
			}
		});
		btnMoveOneRight.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gbc_btnMoveOneRight = new GridBagConstraints();
		gbc_btnMoveOneRight.fill = GridBagConstraints.VERTICAL;
		gbc_btnMoveOneRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveOneRight.gridx = 3;
		gbc_btnMoveOneRight.gridy = 3;
		panel_2.add(btnMoveOneRight, gbc_btnMoveOneRight);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.insets = new Insets(0, 10, 5, 5);
		gbc_verticalStrut_4.gridx = 4;
		gbc_verticalStrut_4.gridy = 3;
		panel_2.add(verticalStrut_4, gbc_verticalStrut_4);

		JScrollPane scrollPane2 = new JScrollPane();
		listSelected = new JList<Country>();
		listSelected.setCellRenderer(new CountryRenderer());
		scrollPane2.setViewportView(listSelected);
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.gridheight = 5;
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 5;
		gbc_list_1.gridy = 3;
		panel_2.add(scrollPane2, gbc_list_1);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.fill = GridBagConstraints.VERTICAL;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 10);
		gbc_verticalStrut_2.gridx = 6;
		gbc_verticalStrut_2.gridy = 3;
		panel_2.add(verticalStrut_2, gbc_verticalStrut_2);

		btnMoveOneLeft = new JButton("<");
		btnMoveOneLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveSelection(listSelected, listCandidates);
			}
		});
		btnMoveOneLeft.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gbc_btnMoveOneLeft = new GridBagConstraints();
		gbc_btnMoveOneLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveOneLeft.gridx = 3;
		gbc_btnMoveOneLeft.gridy = 4;
		panel_2.add(btnMoveOneLeft, gbc_btnMoveOneLeft);

		btnMoveGrpRight = new JButton("»");
		btnMoveGrpRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveAllSelection(listCandidates, listSelected);
			}
		});
		GridBagConstraints gbc_btnMoveGrpRight = new GridBagConstraints();
		gbc_btnMoveGrpRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveGrpRight.gridx = 3;
		gbc_btnMoveGrpRight.gridy = 5;
		panel_2.add(btnMoveGrpRight, gbc_btnMoveGrpRight);

		btnMoveGrpLeft = new JButton("«");
		btnMoveGrpLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveAllSelection(listSelected, listCandidates);
			}
		});
		GridBagConstraints gbc_btnMoveGrpLeft = new GridBagConstraints();
		gbc_btnMoveGrpLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveGrpLeft.gridx = 3;
		gbc_btnMoveGrpLeft.gridy = 6;
		panel_2.add(btnMoveGrpLeft, gbc_btnMoveGrpLeft);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_3.gridx = 1;
		gbc_horizontalStrut_3.gridy = 8;
		panel_2.add(horizontalStrut_3, gbc_horizontalStrut_3);

	}

	public void moveSelection(JList<Country> source, JList<Country> des) {
		List<Country> sourceList = source.getSelectedValuesList();

		SortedListModel<Country> modelSource = (SortedListModel<Country>) source
				.getModel();

		for (Country country : sourceList) {
			modelSource.removeElement(country);
		}

		SortedListModel<Country> modelDes = (SortedListModel<Country>) des
				.getModel();

		for (Country country : sourceList) {
			modelDes.add(country);
		}
	}

	public void moveAllSelection(JList<Country> source, JList<Country> des) {
		SortedListModel<Country> modelDes = (SortedListModel<Country>) des
				.getModel();

		for (int i = 0; i < source.getModel().getSize(); i++) {
			Country item = source.getModel().getElementAt(i);
			modelDes.add(item);
		}
		source.setModel(new SortedListModel<Country>());
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	public JList<Country> getListCandidates() {
		return listCandidates;
	}

	public JList<Country> getListSelected() {
		return listSelected;
	}
	
	public JRadioButton getRdbtnAllLang() {
		return rdbtnAllLang;
	}

	public JRadioButton getRdbtnNoAllLang() {
		return rdbtnNoAllLang;
	}

	public void enableLangSelection(boolean enable) {
		listCandidates.setEnabled(enable);
		listSelected.setEnabled(enable);
		listCandidates.clearSelection();
		listSelected.clearSelection();
		btnMoveOneRight.setEnabled(enable);
		btnMoveOneLeft.setEnabled(enable);
		btnMoveGrpRight.setEnabled(enable);
		btnMoveGrpLeft.setEnabled(enable);
		
	}
}
