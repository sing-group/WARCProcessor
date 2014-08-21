package com.warcgenerator.core.helper;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.warcgenerator.core.config.AppConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.handler.IDSHandler;
import com.warcgenerator.core.exception.config.LoadDataSourceException;

/**
 * 
 * @author Miguel Callon
 */

public class ConfigHelper {
	public static void configure(String path, AppConfig config) {
		XMLConfigHelper.getAppConfigFromXml(path, config);
		config.validate();
	}
	
	// Add DSHandlers to each configuration data source
	public static void getDSHandlers(AppConfig config) 
		throws LoadDataSourceException {
		for (DataSourceConfig ds : config.getDataSourceConfigs().values()) {
			getDSHandler(ds, config);
		}
	}

	public static void getDSHandler(DataSourceConfig ds, AppConfig config) 
					throws LoadDataSourceException {
		try {
			File dirSrc = new File(ds.getFilePath());
			if (dirSrc.exists()) {
				for (File f : dirSrc.listFiles(FileHelper
						.getGeneralFileFilter())) {
					DataSourceConfig specificDsConfig = new DataSourceConfig(
							f.getPath());
					specificDsConfig.setSpamOrHam(ds.isSpam());
					specificDsConfig.setMaxElements(ds.getMaxElements());
					specificDsConfig.setCustomParams(ds.getCustomParams());
					specificDsConfig.setParent(ds);
					
					System.out.println("ds.getDsClassName() es " + 
							ds.getDsClassName());
					System.out.println("specificDsConfig es: " + 
							specificDsConfig);
					
					Class<?> cArgs[] = { DataSourceConfig.class };
					Class<?> clazz = Class.forName(ds.getDsClassName());
					System.out.println("Abriendo!!! 1");
					Constructor<?> ctor = clazz.getConstructor(cArgs);
					
					System.out.println("Abriendo!!! 2");
					IDataSource dsSource = (DataSource) ctor
							.newInstance(specificDsConfig);
					System.out.println("Abriendo!!! 3");
					
					
					System.out.println("HandlerclassName: " + 
							ds.getHandlerClassName());
					
					
					Class<?> cArgs2[] = { IDataSource.class, AppConfig.class };
					Class<?> clazz2 = Class.forName(ds.getHandlerClassName());
					Constructor<?> ctor2 = clazz2.getConstructor(cArgs2);
					
					specificDsConfig.setHandler((IDSHandler) ctor2.newInstance(dsSource,
							config));
					
					ds.getChildren().add(specificDsConfig);
				}
			} else {
				throw new LoadDataSourceException("Path not found: " + dirSrc);
			}

		} catch (ClassNotFoundException e) {
			throw new LoadDataSourceException(e);
		} catch (InstantiationException e) {
			throw new LoadDataSourceException(e);
		} catch (IllegalAccessException e) {
			throw new LoadDataSourceException(e);
		} catch (NoSuchMethodException e) {
			throw new LoadDataSourceException(e);
		} catch (SecurityException e) {
			throw new LoadDataSourceException(e);
		} catch (IllegalArgumentException e) {
			throw new LoadDataSourceException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new LoadDataSourceException(e);
		}
	}
}
