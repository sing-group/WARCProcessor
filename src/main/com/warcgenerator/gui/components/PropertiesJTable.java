package com.warcgenerator.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class PropertiesJTable extends JTable {
	public PropertiesJTable() {
		// this.setRowHeight(21);

		this.setModel(new DefaultTableModel(new Object[0][0], new String[] {
				"Name", "Value" }) {
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

		this.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		this.setDefaultRenderer(CheckBoxRenderer.class, new CheckBoxRenderer());
		this.setDefaultRenderer(File.class, new FileChooserRenderer(null));
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		if (column == 1) {
			Object obj = getValueAt(row, column);

			if (obj instanceof Boolean) {
				CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
				checkBoxRenderer.setSelected((Boolean) obj);
				return new DefaultCellEditor(checkBoxRenderer);
			} else if (obj instanceof File) {
				JFileChooser fileChooser = new JFileChooser((File) obj);
				fileChooser.showDialog(this, "Seleccione CSV");
				File file = fileChooser.getSelectedFile();
				if (file == null) {
					// If have not selected a file, use default file
					file = (File) obj;
				}
				FileChooserRenderer renderer = new FileChooserRenderer(file);

				FileChooserCellEditor editor = new FileChooserCellEditor(
						renderer);
				return editor;
			} else {
				if (obj != null)
					return getDefaultEditor(obj.getClass());
			}
		}
		return super.getCellEditor(row, column);
	}
}

@SuppressWarnings("serial")
class FileChooserCellEditor extends AbstractCellEditor implements
		TableCellEditor {
	private FileChooserRenderer renderer;

	FileChooserCellEditor(FileChooserRenderer renderer) {
		this.renderer = renderer;
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return renderer.getFile();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return renderer.getTableCellRendererComponent(table,
				getCellEditorValue(), isSelected, true, row, column);
	}
}

@SuppressWarnings("serial")
class FileChooserRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
	private File file;

	FileChooserRenderer(File file) {
		super();
		this.file = file;
		setHorizontalAlignment(JLabel.LEFT);
		fill();
	}

	void fill() {
		if (file != null) {
			setText(file.getName());
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, true,
						true, row, column);
		file = (File) value;
		fill();
		return this;
	}

	File getFile() {
		return this.file;
	}

	void setFile(File file) {
		this.file = file;
	}
}

@SuppressWarnings("serial")
class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	CheckBoxRenderer() {
		setHorizontalAlignment(JLabel.LEFT);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
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