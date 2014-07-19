package com.warcgenerator.core.helper;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
	public static List<IDSHandler> getDSHandlers(AppConfig config) {
		List<IDSHandler> dSHandlerList = new ArrayList<IDSHandler>();

		try {
			for (DataSourceConfig ds : config.getDataSourceConfigs()) {
				File dirSrc = new File(ds.getFilePath());
				if (dirSrc.exists()) {
					for (File f : dirSrc.listFiles(
							FileHelper.getGeneralFileFilter())) {
						DataSourceConfig specificDsConfig = new
								 DataSourceConfig(f.getPath());
						specificDsConfig.setSpamOrHam(ds.isSpam());
						specificDsConfig.setMaxElements(ds.getMaxElements());
						specificDsConfig.setCustomParams(ds.getCustomParams());
						
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
				} else {
					throw new LoadDataSourceException("Path not found: " + dirSrc);
				}
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
		
		return dSHandlerList;
	}

}
