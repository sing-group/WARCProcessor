package com.warcgenerator.gui.view.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ValidationDialog extends JDialog {
	private static ValidationDialog dialog;
	private final JPanel contentPanel = new JPanel();
	private JLabel erroresLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ValidationDialog dialog = ValidationDialog.getInstance();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ValidationDialog getInstance() {
		if (dialog == null) {
			dialog = new ValidationDialog();
		}
		return dialog;
	}
	
	/**
	 * Create the dialog.
	 */
	private ValidationDialog() {
		setTitle("Existen errores");
		setBounds(100, 100, 449, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("Por favor, revise los siguientes campos:");
			contentPanel.add(lblNewLabel);
		}
		{
			erroresLabel = new JLabel("");
			contentPanel.add(erroresLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setErroresLabel(String messages) {
		this.erroresLabel.setText(messages);
	}

}
