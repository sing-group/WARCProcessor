package com.warcgenerator.gui.helper;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.config.LoadDataSourceException;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.core.helper.XMLConfigHelper;
import com.warcgenerator.gui.config.GUIConfig;

/**
 * 
 * @author Miguel Callon
 */

public class GUIConfigHelper {
	public static void configure(String path, GUIConfig config) {	
		GUIConfig newConfig = new GUIConfig();
		try {
			// Store old config
			XMLGUIConfigHelper.getGUIConfigFromXml(path, newConfig);
			newConfig.validate();
			BeanUtils.copyProperties(config, newConfig);
		} catch (IllegalAccessException e) {
			throw new ConfigException(e);
		} catch (InvocationTargetException e) {
			throw new ConfigException(e);
		} 
	}
	
	public static void persistConfig(String path, GUIConfig config) {
		XMLGUIConfigHelper.saveXMLFromGUIConfig(path, config);
	}
}
