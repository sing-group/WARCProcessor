package com.warcgenerator.gui.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.gui.common.Constants;
import com.warcgenerator.gui.config.GUIConfig;

/**
 * 
 * @author Miguel Callon
 */

public class GUIConfigHelper {
	public static final String CUSTOM_GUI_CONFIG_XML_FULLPATH =
			Constants.DEFAULT_DIR_CUSTOM_GUI_CONFIG_XML +
			Constants.CUSTOM_GUI_CONFIG_XML;
	
	public static void configure(GUIConfig config) {
		InputStream is = null;
		File f = new File(CUSTOM_GUI_CONFIG_XML_FULLPATH);
		if (!f.exists()) {
			is = f.getClass().getResourceAsStream(Constants.DEFAULT_GUI_CONFIG_XML);
		} else {
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException ex) {
				throw new ConfigException(ex);
			}
		}
		configure(is, config);
	}
	
	private static void configure(InputStream is, GUIConfig config) {
		GUIConfig newConfig = new GUIConfig();
		try (InputStream isTmp = is) {
			// Store old config
			XMLGUIConfigHelper.getGUIConfigFromXml(isTmp, newConfig);
			newConfig.validate();
			BeanUtils.copyProperties(config, newConfig);
		} catch (IllegalAccessException e) {
			throw new ConfigException(e);
		} catch (InvocationTargetException e) {
			throw new ConfigException(e);
		} catch (IOException e) {
			throw new ConfigException(e);
		}
	}

	public static void persistConfig(GUIConfig config) {
		File f = new File(Constants.DEFAULT_DIR_CUSTOM_GUI_CONFIG_XML);
		if (!f.exists()) {
			f.mkdirs();
		}		
		persistConfig(CUSTOM_GUI_CONFIG_XML_FULLPATH, config);
	}

	private static void persistConfig(String path, GUIConfig config) {
		XMLGUIConfigHelper.saveXMLFromGUIConfig(path, config);
	}
}
