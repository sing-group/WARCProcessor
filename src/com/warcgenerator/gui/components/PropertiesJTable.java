package com.warcgenerator.gui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class PropertiesJTable extends JTable {
	public PropertiesJTable() {
		this.setModel(new DefaultTableModel(new Object[0][0], new String[] {
				"Nombre", "Valor" }) {
			boolean[] columnEditables = new boolean[] { false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			/*
			 * public void setValueAt(Object value, int row, int col) {
			 * super.setValueAt(value, row, col); fireTableCellUpdated(row,
			 * col); }
			 */
		});

		setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				final Component c = super.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);
				// c.setBackground(getRowColour(row));
				return c;

			}
		});
	}

	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		Component c = null;

		Object obj = getModel().getValueAt(row, column);
		if (obj instanceof Boolean) {
			CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
			checkBoxRenderer.setSelected((Boolean) obj);
			c = checkBoxRenderer;
		} else {
			c = super.prepareRenderer(renderer, row, column);
		}

		return c;
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		if (column == 1) {
			Object obj = getValueAt(row, column);

			if (obj instanceof Boolean) {
				CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
				checkBoxRenderer.setSelected((Boolean) obj);
				return new DefaultCellEditor(checkBoxRenderer);
			} else {
				if (obj != null)
					return getDefaultEditor(obj.getClass());
			}
		}
		return super.getCellEditor(row, column);
	}
}

class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	CheckBoxRenderer() {
		setHorizontalAlignment(JLabel.LEFT);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			// super.setBackground(table.getSelectionBackground());
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}

		setSelected((value != null && ((Boolean) value).booleanValue()));
		return this;
	}

	public Color getRowColour(int row) {
		return row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE;
	}
}