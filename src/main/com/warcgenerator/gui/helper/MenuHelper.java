package com.warcgenerator.gui.helper;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSDetailAction;
import com.warcgenerator.gui.components.CustomTreeNode;
import com.warcgenerator.gui.view.WarcGeneratorGUI;

public class MenuHelper {
	private static Logger logger = Logger.getLogger
            (MenuHelper.class);
	
	public static void loadDS(DefaultMutableTreeNode treeNode, 
			WarcGeneratorGUI view,
			IAppLogic logic) {
		for (DataSourceConfig config : logic.getDataSourceConfigList()) {
			CustomTreeNode treeNodeDS = new CustomTreeNode(config.getName());
			DSDetailAction newAction = new DSDetailAction(logic, view, config);
			treeNodeDS.setId(config.getId());
			treeNodeDS.setAction(newAction);
			treeNode.add(treeNodeDS);
		}
	}
	
	public static void addDS(JTree tree, DefaultMutableTreeNode node,
			DataSourceConfig config, WarcGeneratorGUI view, IAppLogic logic) {
		
		if (node != null) {
			CustomTreeNode treeNodeDS = new CustomTreeNode(config.getName());
			treeNodeDS.setId(config.getId());
			treeNodeDS.setAction(new DSDetailAction(logic, view, config));

			// Insert at the end of datasources
			node.insert(treeNodeDS, node.getChildCount());
			tree.updateUI();
			tree.repaint();
		}
	}
	
	public static void updateDS(JTree tree, Integer id, DataSourceConfig config,
			WarcGeneratorGUI view, IAppLogic logic) {
		DefaultMutableTreeNode node = searchNodeById(tree, id);
		if (node != null) {
			if (node instanceof CustomTreeNode) {
				node.setUserObject(config.getName());
				((CustomTreeNode) node).setAction(new DSDetailAction(logic,
						view, config));
				tree.updateUI();
				tree.repaint();
			}
		}
	}
	
	public static void removeDS(JTree tree, Integer id) {
		DefaultMutableTreeNode node = searchNodeById(tree, id);
		DefaultMutableTreeNode previousNode = 
				node.getPreviousNode();
		DefaultMutableTreeNode nextNode = 
				node.getNextNode();
		DefaultMutableTreeNode parentNode = 
				(DefaultMutableTreeNode)node.getParent();
		
		if (node != null) {
			if (node instanceof CustomTreeNode) {
				if (((CustomTreeNode)node).getId() != null &&
						((CustomTreeNode)node).getId().equals(id)) {	
					((DefaultMutableTreeNode)node.getParent()).remove(node);
						
					tree.updateUI();
					tree.repaint();
					
					// Select a datasource
					if (parentNode.getChildCount() > 0) {
						if (previousNode == parentNode) {
							selectAndExecuteLeftMenu(
									tree, (String)nextNode.getUserObject());
						} else {
							selectAndExecuteLeftMenu(
									tree, (String)previousNode.getUserObject());
						}
					} else {
						selectAndExecuteLeftMenu(
								tree, (String)parentNode.getUserObject());
					}
				}
			}
		}
	}
	
	public static DefaultMutableTreeNode searchNodeById(
			JTree tree, Integer id) {
		DefaultMutableTreeNode m_rootNode =
				(DefaultMutableTreeNode)tree.getModel().getRoot();
		DefaultMutableTreeNode node = null;
		Enumeration e = m_rootNode.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = (DefaultMutableTreeNode) e.nextElement();

			if (node instanceof CustomTreeNode) {
				if (((CustomTreeNode) node).getId() != null
						&& ((CustomTreeNode) node).getId().equals(id))
					return node;
			}
		}
		return null;
	}
	
	public static DefaultMutableTreeNode searchNode(JTree tree, 
			String nodeStr) {
		DefaultMutableTreeNode m_rootNode =
				(DefaultMutableTreeNode)tree.getModel().getRoot();
		DefaultMutableTreeNode node = null;
		Enumeration e = m_rootNode.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = (DefaultMutableTreeNode) e.nextElement();
			if (nodeStr.equals(node.getUserObject().toString())) {
				return node;
			}
		}
		return null;
	}
	
	public static TreePath getLeftMenu(JTree tree, String search) {
		DefaultMutableTreeNode node = searchNode(tree, search);
		TreePath tp = null;
		if (node != null) {
			TreeNode[] nodes = ((DefaultTreeModel) tree.getModel())
					.getPathToRoot(node);
			tp = new TreePath(nodes);
		} else {
			logger.info("Node with string " + search + " not found");
		}
		return tp;
	}
	
	public static void selectLeftMenu(JTree tree, TreePath tp) {
		//TreePath tp = getLeftMenu(tree, search);
		if (tree != null && tp != null) {
			tree.scrollPathToVisible(tp);
			tree.setSelectionPath(tp);
			tree.getParent().repaint();
			tree.updateUI();
			tree.repaint();
		}
	}
	
	public static void selectAndExecuteLeftMenu(JTree tree, String search) {
		TreePath tp = getLeftMenu(tree,  search);
		if (tp != null) {
			Object obj = tp.getLastPathComponent();
			if (obj instanceof CustomTreeNode) {
				CustomTreeNode itemSelected = (CustomTreeNode) obj;
				if (itemSelected.getAction() != null)
					itemSelected.getAction().actionPerformed(null);
				selectLeftMenu(tree, tp);
			}
		}
	}
}
