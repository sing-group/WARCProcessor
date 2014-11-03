package com.warcgenerator.gui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.warcgenerator.core.datasource.bean.Country;

public class CountryRenderer extends JLabel implements
		ListCellRenderer<Country> {

	public CountryRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Country> list, Country country, int index,
			boolean isSelected, boolean cellHasFocus) {

		String code = country.getCode();
		// ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/"
		// + code + ".png"));

		// setIcon(imageIcon);
		setText(country.getName());

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(new Color(255,255,255,0));
			setForeground(list.getForeground());
		}

		return this;
	}

}
