package com.warcgenerator.gui.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCreateAction;
import com.warcgenerator.gui.actions.general.GeneralConfigAction;
import com.warcgenerator.gui.components.CustomTreeNode;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;

public class WarcGeneratorGUI {

	Action assistantCreateDSAction;
	
	private JFrame frmWarcgenerator;
	private JPanel mainPanel;
	private JSplitPane splitPane; 
	
	private IAppLogic logic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarcGeneratorGUI window = new WarcGeneratorGUI(null);
					window.frmWarcgenerator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WarcGeneratorGUI(IAppLogic logic) {
		this.logic = logic;
		assistantCreateDSAction = new DSAsisstantCreateAction(logic, 
				this);
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWarcgenerator = new JFrame();
		frmWarcgenerator.setTitle("WarcGenerator GUI");
		frmWarcgenerator.setBounds(100, 100, 630, 470);
		frmWarcgenerator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String label = "Nuevo origen";
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItemPopup = new JMenuItem(label);
		menuItemPopup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				assistantCreateDSAction.actionPerformed(null);
				System.out.println("Clicado");
			}
		});
		popup.add(menuItemPopup);
		
		JMenuBar menuBar = new JMenuBar();
		frmWarcgenerator.setJMenuBar(menuBar);
		
		JMenu mnInicio = new JMenu("Inicio");
		menuBar.add(mnInicio);
		
		JMenuItem mntmCargarConfiguracionGeneral = new JMenuItem("Cargar configuracion general");
		mnInicio.add(mntmCargarConfiguracionGeneral);
		
		JMenuItem mntmGuardarConfiguracionGeneral = new JMenuItem("Guardar configuracion general");
		mnInicio.add(mntmGuardarConfiguracionGeneral);
		
		JSeparator separator = new JSeparator();
		mnInicio.add(separator);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnInicio.add(mntmSalir);
		
		JMenu mnGenerarCorpus = new JMenu("Generar corpus");
		menuBar.add(mnGenerarCorpus);
		
		JMenu mnSalir = new JMenu("Acerca De ...");
		menuBar.add(mnSalir);
		
		splitPane = new JSplitPane();
		frmWarcgenerator.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		final JTree tree = new JTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//create a class which implements the MouseListener interface and
				//implement the following in your overridden mouseClicked method

			    if (SwingUtilities.isRightMouseButton(e)) {
			        int row = tree.getClosestRowForLocation(e.getX(), e.getY());
			        tree.setSelectionRow(row);
			        popup.show(e.getComponent(), e.getX(), e.getY());
			    } else {
			    	TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
			    	CustomTreeNode itemSelected = (CustomTreeNode)tp.getLastPathComponent();
			    	if (itemSelected != null) 
			    		System.out.println(itemSelected.toString());
			    	itemSelected.getAction().actionPerformed(null);
			    }
			}
		});
		
		final GeneralConfigPanel configPanel = new GeneralConfigPanel(logic, this);
		final GeneralConfigAction generalConfigAction = new
				GeneralConfigAction(logic, this);
		
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Configuracion") {
				{
					CustomTreeNode node_1;
					CustomTreeNode general = new CustomTreeNode("General");
					general.setAction(generalConfigAction);
					add(general);
					
					node_1 = new CustomTreeNode("Origenes");
					loadDS(node_1);
					add(node_1);
					add(new CustomTreeNode("Salida"));
				}
			}
		));
		panel.add(tree);
		
		mainPanel = new JPanel();
		splitPane.setRightComponent(mainPanel);
	}
	
	private void loadDS(DefaultMutableTreeNode treeNode) {
		for (DataSourceConfig config:logic.getDataSourceConfigList()) {
			CustomTreeNode treeNodeDS = new CustomTreeNode(
					config.getName());
			treeNode.add(treeNodeDS);
		}
	}
	
	public void loadMainPanel(JPanel newPanel) {
		System.out.println("Cambiando el panel a: " + newPanel);
		
		splitPane.setRightComponent(newPanel);
		splitPane.revalidate();
		splitPane.updateUI();
		splitPane.repaint();
	}
	
	public void setVisible(boolean visible) {
		frmWarcgenerator.setVisible(visible);
	}

}
