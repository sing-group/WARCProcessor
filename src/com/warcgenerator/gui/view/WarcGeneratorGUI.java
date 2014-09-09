package com.warcgenerator.gui.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.actions.common.AboutOfAction;
import com.warcgenerator.gui.actions.common.ExitAction;
import com.warcgenerator.gui.actions.common.RecentFileCBItem;
import com.warcgenerator.gui.actions.datasource.DSAsisstantCreateAction;
import com.warcgenerator.gui.actions.datasource.DSourcesAction;
import com.warcgenerator.gui.actions.file.CreateNewConfigAction;
import com.warcgenerator.gui.actions.file.LoadAppConfigAction;
import com.warcgenerator.gui.actions.file.LoadRecentConfigAction;
import com.warcgenerator.gui.actions.file.SaveAppConfigAction;
import com.warcgenerator.gui.actions.file.SaveAsAppConfigAction;
import com.warcgenerator.gui.actions.general.GeneralConfigAction;
import com.warcgenerator.gui.actions.generate.GenerateCorpusAction;
import com.warcgenerator.gui.actions.other.OtherConfigAction;
import com.warcgenerator.gui.actions.output.OutputConfigAction;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.common.Session;
import com.warcgenerator.gui.components.CustomCardLayout;
import com.warcgenerator.gui.components.CustomTreeNode;
import com.warcgenerator.gui.config.GUIConfig;
import com.warcgenerator.gui.helper.GUIConfigHelper;
import com.warcgenerator.gui.helper.MenuHelper;
import com.warcgenerator.gui.view.common.InitPanel;
import com.warcgenerator.gui.view.general.GeneralConfigPanel;
import com.warcgenerator.gui.view.other.OtherConfigPanel;
import com.warcgenerator.gui.view.output.OutputConfigPanel;

public class WarcGeneratorGUI extends Observable {
	private GUIConfig guiConfig;
	private CardLayout cardLayout;

	public static final String TRYING_CHANGE_MAIN_PANEL = "TRYING_CHANGE_MAIN_PANEL";

	private Action assistantCreateDSAction;
	private Action generateCorpusAction;
	private OutputConfigAction outputConfigAction;
	private OtherConfigAction otherConfigAction;
	private Action saveAsAppConfigAction;
	private Action saveAppConfigAction;
	private Action loadAppConfigAction;
	private Action createNewConfigAction;
	private Action dsourcesAction;
	private Action exitAction;
	private Action aboutOfAction;

	private JFrame frmWarcgenerator;
	private JPanel mainPanel;
	private JPanel cleanPanel;
	private JPanel assistantPanel;
	private JSplitPane splitPane;
	private DefaultMutableTreeNode m_rootNode;
	private JTree tree;
	private GeneralConfigAction generalConfigAction;
	private JMenu recentFilesMI;

	private IAppLogic logic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarcGeneratorGUI window = new WarcGeneratorGUI();
					window.frmWarcgenerator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WarcGeneratorGUI() {
		super();
		initialize();
	}

	/**
	 * Create the application.
	 */
	public WarcGeneratorGUI(IAppLogic logic) {
		this.logic = logic;
		initialize();
	}

	public void loadPanels() {
		cleanPanel = new InitPanel();
		mainPanel = new JPanel(new CustomCardLayout());
		assistantPanel = new JPanel(new CustomCardLayout());
		assistantPanel.setName("AssistantPanel");

		GeneralConfigPanel generalConfigPanel = new GeneralConfigPanel(logic,
				this);
		OutputConfigPanel outputconfigPanel = new OutputConfigPanel(logic, this);
		OtherConfigPanel otherConfigPanel = new OtherConfigPanel(logic, this);
		
		
		addMainPanel(cleanPanel);
		addMainPanel(generalConfigPanel);
		addMainPanel(outputconfigPanel);
		addMainPanel(otherConfigPanel);
		addMainPanel(assistantPanel);

		assistantCreateDSAction = new DSAsisstantCreateAction(logic, this);
		generateCorpusAction = new GenerateCorpusAction(logic, this);
		generalConfigAction = new GeneralConfigAction(logic, this,
				generalConfigPanel);
		outputConfigAction = new OutputConfigAction(logic, this,
				outputconfigPanel);
		otherConfigAction = new OtherConfigAction(logic, this, otherConfigPanel);
		saveAppConfigAction = new SaveAppConfigAction(logic, this);
		saveAsAppConfigAction = new SaveAsAppConfigAction(logic, this);
		loadAppConfigAction = new LoadAppConfigAction(logic, this);
		createNewConfigAction = new CreateNewConfigAction(logic, this);
		dsourcesAction = new DSourcesAction(logic, this,
				assistantCreateDSAction);
		exitAction = new ExitAction(logic, this);
		guiConfig = (GUIConfig) Session.get(Constants.GUI_CONFIG_SESSION_KEY);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			System.err
					.println("Nimbus L&F does not support. Default L&F will be used.");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		loadPanels();

		frmWarcgenerator = new JFrame();
		frmWarcgenerator.setResizable(false);
		frmWarcgenerator
				.setIconImage(Toolkit
						.getDefaultToolkit()
						.getImage(
								WarcGeneratorGUI.class
										.getResource("/com/warcgenerator/gui/resources/img/warc.png")));
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
			}
		});
		popup.add(menuItemPopup);

		JMenuBar menuBar = new JMenuBar();
		frmWarcgenerator.setJMenuBar(menuBar);

		JMenu mnInicio = new JMenu("Fichero");
		mnInicio.setMnemonic('F');
		menuBar.add(mnInicio);

		JMenuItem mntmCreateNewConfig = new JMenuItem("Nueva");
		mntmCreateNewConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewConfigAction.actionPerformed(e);
			}
		});
		mnInicio.add(mntmCreateNewConfig);

		JMenuItem mntmCargarConfiguracionGeneral = new JMenuItem(
				"Cargar");
		mntmCargarConfiguracionGeneral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadAppConfigAction.actionPerformed(e);
			}
		});
		mnInicio.add(mntmCargarConfiguracionGeneral);

		mnInicio.add(new JSeparator());
		
		JMenuItem mntmSaveCG = new JMenuItem(
				"Guardar");
		mntmSaveCG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		mntmSaveCG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAppConfigAction.actionPerformed(e);
			}
		});
		mnInicio.add(mntmSaveCG);
		
		JMenuItem mntmSaveAsCG = new JMenuItem(
				"Guardar como");
		mntmSaveAsCG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAsAppConfigAction.actionPerformed(e);
			}
		});
		mnInicio.add(mntmSaveAsCG);

		JSeparator separator = new JSeparator();
		mnInicio.add(separator);

		recentFilesMI = new JMenu("Configuraciones recientes");
		loadRecentFiles();
		mnInicio.add(recentFilesMI);

		mnInicio.add(new JSeparator());

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.CTRL_MASK));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		mnInicio.add(mntmSalir);

		final JMenu mnHelp = new JMenu("Ayuda");
		mnHelp.setMnemonic('A');
		menuBar.add(mnHelp);

		/*final JMenu mnLanguages = new JMenu("Idiomas");
		mnLanguages.setHorizontalAlignment(SwingConstants.LEFT);
		mnHelp.add(mnLanguages);

		final JMenuItem mnSpanish = new JMenuItem("Español");
		mnSpanish.setHorizontalAlignment(SwingConstants.LEFT);

		mnSpanish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mnLanguages.add(mnSpanish);		
		
		final JMenuItem mnEnglish = new JMenuItem("Ingles");
		mnEnglish.setHorizontalAlignment(SwingConstants.LEFT);

		mnEnglish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mnLanguages.add(mnEnglish);	*/
		
		final JMenuItem mnAboutOf = new JMenuItem("Acerca De ...");
		mnAboutOf.setHorizontalAlignment(SwingConstants.LEFT);

		mnAboutOf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutOfAction = new AboutOfAction(WarcGeneratorGUI.this);
				aboutOfAction.actionPerformed(null);
			}
		});
		mnHelp.add(mnAboutOf);

		menuBar.add(Box.createHorizontalGlue());

		JButton mnGenerarCorpus = new JButton("Generar corpus");
		mnGenerarCorpus.setMinimumSize(new Dimension(114, 26));
		mnGenerarCorpus.setIcon(new ImageIcon(WarcGeneratorGUI.class
				.getResource("/com/warcgenerator/gui/resources/img/load.png")));
		mnGenerarCorpus.setAlignmentX(Component.LEFT_ALIGNMENT);

		mnGenerarCorpus.setHorizontalAlignment(SwingConstants.LEFT);
		mnGenerarCorpus.setMnemonic('G');
		mnGenerarCorpus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateCorpusAction.actionPerformed(null);
			}
		});
		menuBar.add(mnGenerarCorpus);

		splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		frmWarcgenerator.getContentPane().add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(150, 2));
		splitPane.setLeftComponent(scrollPane);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setPreferredSize(new Dimension(140, 10));
		panel.setBackground(Color.WHITE);
		scrollPane.setViewportView(panel);

		tree = new JTree();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();

				/* if nothing is selected */
				if (node == null)
					return;

				/* retrieve the node that was selected */
				Object nodeInfo = node.getUserObject();

				/* React to the node selection. */
				
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// create a class which implements the MouseListener interface
				// and
				// implement the following in your overridden mouseClicked
				// method

				if (SwingUtilities.isRightMouseButton(e)) {
					int row = tree.getClosestRowForLocation(e.getX(), e.getY());
					tree.setSelectionRow(row);
					popup.show(e.getComponent(), e.getX(), e.getY());
				} else {
					TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
					if (tp != null) {
						Object obj = tp.getLastPathComponent();
						if (obj instanceof CustomTreeNode) {
							CustomTreeNode itemSelected = (CustomTreeNode) obj;
							if (itemSelected.getAction() != null)
								itemSelected.getAction().actionPerformed(null);
						}
					}
				}
			}
		});

		buildTree();
		panel.add(tree);

		splitPane.setRightComponent(mainPanel);
		frmWarcgenerator.setLocationRelativeTo(null);
	}

	public void buildTree() {
		final CustomTreeNode general = new CustomTreeNode("General");

		m_rootNode = new DefaultMutableTreeNode("Configuracion") {
			{
				CustomTreeNode node_1;

				general.setAction(generalConfigAction);
				add(general);
				CustomTreeNode output = new CustomTreeNode("Salida");
				output.setAction(outputConfigAction);
				add(output);

				CustomTreeNode other = new CustomTreeNode("Otros");
				other.setAction(otherConfigAction);
				add(other);
				
				node_1 = new CustomTreeNode("Origenes");
				node_1.setAction(dsourcesAction);
				add(node_1);
				loadDS(node_1);
			}
		};

		tree.setModel(new DefaultTreeModel(m_rootNode));
	}

	public void loadRecentFiles() {
		// Add config file path
		String configFilePath = logic.getConfigFilePath();
		if (configFilePath != null) {
			GUIConfig guiConfig = (GUIConfig) Session
					.get(Constants.GUI_CONFIG_SESSION_KEY);
			guiConfig.addRecentConfigFile(configFilePath);
			GUIConfigHelper.persistConfig(Constants.DEFAULT_GUI_CONFIG_XML,
					guiConfig);
		}
			
		recentFilesMI.removeAll();
		List<RecentFileCBItem> recentFiles = guiConfig.getRecentConfigFilesReversed();
		if (recentFiles.size() == 0) {
			JMenuItem recentConfig = new JMenuItem("[Ninguna]");
			recentConfig.setEnabled(false);
			recentFilesMI.add(recentConfig);
		} else {
			for (RecentFileCBItem configFile : recentFiles) {
				final String configFilePathRecent = configFile.getPath();
				JMenuItem recentConfig = new JMenuItem(configFile.toString());
				recentConfig.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Action loadRecentConfigAction = new LoadRecentConfigAction(
								logic, WarcGeneratorGUI.this, configFilePathRecent, true);
						loadRecentConfigAction.actionPerformed(e);
					}
				});
				recentFilesMI.add(recentConfig);
			}
		}
	}

	public void selectFirstSelectionableItem() {
		MenuHelper.selectAndExecuteLeftMenu(tree, "General");
	}

	public void updateDS(Integer id, DataSourceConfig config) {
		MenuHelper.updateDS(tree, id, config, this, logic);
	}

	public void removeDS(Integer id) {
		MenuHelper.removeDS(tree, id);
	}

	public void addDS(DataSourceConfig config) {
		DefaultMutableTreeNode node = MenuHelper.searchNode(tree, "Origenes");
		MenuHelper.addDS(tree, node, config, this, logic);
	}

	public void selectLeftMenu(TreePath tp) {
		MenuHelper.selectLeftMenu(tree, tp);
	}
	
	public DefaultMutableTreeNode getSelectedNode() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		return node;
	}
	
	public TreePath getSelectedMenu(DefaultMutableTreeNode node) {
		/*TreePath treePaths[] = tree.getSelectionPaths();
		TreePath treePath = null;
		if (treePaths != null) {
			treePath = new TreePath(treePaths);
		}*/
		TreePath treePath = null;
		
		if (node != null) {
			treePath = new TreePath(node.getPath());
		}
		
		return treePath;
	}
	
	public void selectAndExecuteLeftMenu(String search) {
		MenuHelper.selectAndExecuteLeftMenu(tree, search);
	}

	private void loadDS(DefaultMutableTreeNode treeNode) {
		MenuHelper.loadDS(treeNode, this, logic);
	}

	public void addMainPanel(JPanel panel) {
		mainPanel.add(panel, panel.getName());
	}
	
	public void tryChangeMainPanel(Action nextAction) {
		setChanged();
		notifyObservers(new Object[] { 
				WarcGeneratorGUI.TRYING_CHANGE_MAIN_PANEL,  nextAction} );
	}
	
	public void loadMainPanel(JPanel newPanel) {
				CustomCardLayout cardLayout = ((CustomCardLayout) mainPanel.getLayout());
		cardLayout.show(mainPanel, newPanel.getName());
	}
	
	public void setVisible(boolean visible) {
		frmWarcgenerator.setVisible(visible);
		frmWarcgenerator.pack();
	}

	public JFrame getMainFrame() {
		return frmWarcgenerator;
	}

	public JPanel getAssistantPanel() {
		return assistantPanel;
	}

	public void setAssistantPanel(JPanel assistantPanel) {
		this.assistantPanel = assistantPanel;
	}

}
