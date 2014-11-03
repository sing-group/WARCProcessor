package com.warcgenerator.gui.components;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class CustomComboBoxRenderer<E> extends JLabel implements ListCellRenderer<E> {
    public static final String SEPARATOR = "SEPARATOR";
	
	JSeparator separator;

    public CustomComboBoxRenderer() {
      setOpaque(true);
      setBorder(new EmptyBorder(1, 1, 1, 1));
      separator = new JSeparator(JSeparator.HORIZONTAL);
    }

	@Override
	public Component getListCellRendererComponent(JList<? extends E> list,
			E value, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		 String str = (value == null) ? "" : value.toString();
	      if (SEPARATOR.equals(str)) {
	        return separator;
	      }
	      if (isSelected) {
	        setBackground(list.getSelectionBackground());
	        setForeground(list.getSelectionForeground());
	      } else {
	        setBackground(list.getBackground());
	        setForeground(list.getForeground());
	      }
	      setFont(list.getFont());
	      setText(str);
	      return this;
	}
}
