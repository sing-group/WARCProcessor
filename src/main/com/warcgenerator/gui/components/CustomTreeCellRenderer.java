package com.warcgenerator.gui.components;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

/**
 * Renderer for tree cells
 * 
 * @author Miguel Callon
 *
 */
@SuppressWarnings("serial")
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
	private Icon loadIcon;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean isLeaf, int row,
			boolean focused) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				isLeaf, row, focused);
		setToolTipText(null);
		
		JComponent c = (JComponent)super.getTreeCellRendererComponent(
				tree, value, selected, expanded, isLeaf, row, focused);
		
		if (value instanceof CustomTreeNode) {
			CustomTreeNode customTreeNode = (CustomTreeNode) value;
			
			if (customTreeNode.getType().equals(
					CustomTreeNodeType.DATASOURCE_NODE)) {
				DataSourceConfig dsConfig = (DataSourceConfig) customTreeNode
						.getReferencedObject();

				if (!dsConfig.getEnabled()) {
					loadIcon = new ImageIcon(
							Toolkit.getDefaultToolkit()
									.getImage(
											WarcGeneratorGUI.class
													.getResource("/com/warcgenerator/gui/resources/img/database16x16_disabled.png")));
					setIcon(loadIcon);
					setToolTipText("Deshabilitado");
				} else {
					setIcon(new ImageIcon(WarcGeneratorGUI.class
							.getResource("/com/warcgenerator/gui/resources/img/database16x16.png")));
				}
			}
		}

		return c;
	}
}
