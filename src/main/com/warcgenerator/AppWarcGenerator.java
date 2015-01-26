package com.warcgenerator;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import com.warcgenerator.core.exception.WarcException;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.exception.datasource.ReadException;
import com.warcgenerator.core.exception.datasource.WriteException;
import com.warcgenerator.gui.actions.common.StartGUIAction;

/**
 * AppWarcGenerator
 * 
 * @author Miguel Callon
 */
public class AppWarcGenerator {
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		List<LongOpt> options = new ArrayList<LongOpt>();
		options.add(new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'));
		options.add(new LongOpt("nogui", LongOpt.NO_ARGUMENT, null, 'n'));
		options.add(new LongOpt("config", LongOpt.REQUIRED_ARGUMENT, null, 'c'));

		CommandConfig config = handleOpt(args, options);
		if (!config.isShowHelp()) {
			final AppWarc app = AppWarc.getInstance();

			try {
				if (config.getConfigIni() != null
						&& !config.getConfigIni().isEmpty()) {
					app.init(config.getConfigIni());
				} else {
					app.init();
				}

				if (config.isUseGUI()) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								StartGUIAction mainAction = new StartGUIAction(
										app);
								mainAction.actionPerformed(null);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					app.execute();
				}
			} catch (OpenException e) {
				System.out
						.println("Is not posible open data source. Check config.xml");
			} catch (ReadException e) {
				System.out
						.println("Is not posible read from data source. Check permission.");
			} catch (WriteException e) {
				System.out
						.println("Is not posible write in data source. Check permission.");
			} catch (ConfigException e) {
				System.out
						.println("Is not posible read configuration. Check config.xml :"
								+ e);
			} catch (WarcException e) {
				System.out.println(e);
			}
		}
	}

	private static CommandConfig handleOpt(String[] args, List<LongOpt> options) {
		CommandConfig config = new CommandConfig();
		int c;
		LongOpt[] longopts = options.toArray(new LongOpt[options.size()]);
		StringBuffer usageOptions = new StringBuffer(" ");
		for (LongOpt longopt : longopts) {
			usageOptions.append("[--").append(longopt.getName());
			if (longopt.getHasArg() == LongOpt.REQUIRED_ARGUMENT) {
				usageOptions.append(" <path>");
			}
			usageOptions.append("] ");
		}

		StringBuffer usage = new StringBuffer("Usage: ");
		usage.append(usageOptions.toString());

		Getopt g = new Getopt("", args, "c:nh", longopts);
		while ((c = g.getopt()) != -1) {
			switch (c) {
			case 'c':
				config.setConfigIni(g.getOptarg());
				break;
			case 'h':
				config.setShowHelp(true);
				break;
			case 'n':
				config.setUseGUI(false);
				break;
			case ':':
				System.out.println("Falta algumento para la opción: "
						+ (char) g.getOptopt());
				config.setShowHelp(true);
				break;
			case '?':
				config.setShowHelp(true);
				break;
			default:
				config.setShowHelp(true);
				break;
			}
		}
		if (config.isShowHelp()) {
			System.out.println(usage);
		}
		
		return config;
	}
}

class CommandConfig {
	private boolean useGUI = true;
	private String configIni = "";
	private boolean showHelp = false;

	public boolean isUseGUI() {
		return useGUI;
	}

	public void setUseGUI(boolean useGUI) {
		this.useGUI = useGUI;
	}

	public String getConfigIni() {
		return configIni;
	}

	public void setConfigIni(String configIni) {
		this.configIni = configIni;
	}

	public boolean isShowHelp() {
		return showHelp;
	}

	public void setShowHelp(boolean showHelp) {
		this.showHelp = showHelp;
	}
}
