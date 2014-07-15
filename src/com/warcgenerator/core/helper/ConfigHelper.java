package com.warcgenerator.core.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.exception.config.ConfigException;
import com.warcgenerator.core.exception.config.LoadParamsException;

/**
 * 
 * @author Miguel Callon
 */

public class ConfigHelper {
	public static PrefixedProperty loadParams(String pathConfigs)
			throws ConfigException {
		PrefixedProperty props = new PrefixedProperty();
		InputStream is = null;

		// First try loading from the current directory
		try {
			File f = new File(pathConfigs);
			is = new FileInputStream(f);
			props.loadFromXML(is);
			is.close();
		} catch (InvalidPropertiesFormatException e) {
			throw new LoadParamsException(e);
		} catch (IOException e) {
			throw new LoadParamsException(e);
		}

		return props;
	}

	public static List<IDSHandler> getDSHandlers(AppConfig config) {
		List<IDSHandler> dSHandlerList = new ArrayList<IDSHandler>();

		try {
			for (DataSourceConfig ds : config.getDataSourceConfigs()) {
				File dirSrc = new File(ds.getFilePath());
				for (File f : dirSrc.listFiles()) {
					DataSourceConfig specificDsConfig = new
							 DataSourceConfig(f.getPath());
					specificDsConfig.setSpamOrHam(ds.isSpam());
					
					Class<?> cArgs[] = { DataSourceConfig.class };
					Class<?> clazz = Class.forName(ds.getDsClassName());
					Constructor<?> ctor = clazz.getConstructor(cArgs);
					IDataSource dsSource = (DataSource) ctor.newInstance(
							specificDsConfig);

					Class<?> cArgs2[] = { IDataSource.class, AppConfig.class };
					Class<?> clazz2 = Class.forName(ds.getHandlerClassName());
					Constructor<?> ctor2 = clazz2.getConstructor(cArgs2);
					dSHandlerList.add((IDSHandler) ctor2.newInstance(dsSource,
							config));
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dSHandlerList;
	}

}
