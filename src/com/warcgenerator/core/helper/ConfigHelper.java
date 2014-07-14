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
					// obtengo el constructor que recibe (en este caso) un
					// String
					DataSourceConfig specificDsConfig = new
							 DataSourceConfig(f.getPath());
					specificDsConfig.setSpamOrHam(ds.isSpam());
					
					Class<?> cArgs[] = { DataSourceConfig.class };
					Class<?> clazz = Class.forName(ds.getDsClassName());
					Constructor<?> ctor = clazz.getConstructor(cArgs);
					IDataSource dsSource = (DataSource) ctor.newInstance(
							specificDsConfig);

					System.out.println("reconstruyendo: " + ds.getHandlerClassName());
					
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

		// Read CSV dir
		/*
		 * File dirSrcCSV = new File(config.getSrcCSVDirPath()); for (File f:
		 * dirSrcCSV.listFiles()) { DataSourceConfig dsConfig = new
		 * DataSourceConfig(f.getPath()); IDataSource dataSource = new CSVDS(
		 * dsConfig); dSHandlerList.add(new CSVDSHandler( dataSource, config));
		 * }
		 */

		/*
		 * File dirSrcCSV = new File(config.getPropertyByGroup("ds")); for (File
		 * f: dirSrcCSV.listFiles()) { DataSourceConfig dsConfig = new
		 * DataSourceConfig(f.getPath()); IDataSource dataSource = new CSVDS(
		 * dsConfig); dSHandlerList.add(new CSVDSHandler( dataSource, config));
		 * }
		 */

		// Read Arff dir
		/*
		 * File dirSrcArff = new File(config.getSrcArffDirPath()); for (File f:
		 * dirSrcArff.listFiles()) { DataSourceConfig dsConfig = new
		 * DataSourceConfig(f.getPath()); IDataSource dataSource = new ArffDS(
		 * dsConfig); dSHandlerList.add(new ArffDSHandler( dataSource, config));
		 * }
		 * 
		 * // Read whitelist dir File dirWhiteList = new
		 * File(config.getWhiteListsDirPath()); for (File f :
		 * dirWhiteList.listFiles()) { DataSourceConfig dsConfig = new
		 * DataSourceConfig( DataSourceConfig.IS_HAM, f.getPath()); IDataSource
		 * dataSource = new FileDS(dsConfig); dSHandlerList.add(new
		 * FileDSHandler( dataSource, config)); } // Read blacklist dir File
		 * dirBlackList = new File(config.getBlackListsDirPath()); for (File f :
		 * dirBlackList.listFiles()) { DataSourceConfig dsConfig = new
		 * DataSourceConfig( DataSourceConfig.IS_SPAM, f.getPath()); IDataSource
		 * dataSource = new FileDS(dsConfig); dSHandlerList.add(new
		 * FileDSHandler( dataSource, config)); }
		 */
		// Read warc dir
		/*
		 * File dirSrcCorpus = new File(config.getSrcCorpusDirPath()); // Each
		 * directory is a corpus for (File corpus : dirSrcCorpus.listFiles()) {
		 * StringBuilder sb = new StringBuilder(dirSrcCorpus.getPath());
		 * sb.append(File.separator); sb.append(corpus.getName());
		 * sb.append(File.separator); // TODO Customize this StringBuilder path
		 * = new StringBuilder(sb.toString()); path.append("_ham_");
		 * 
		 * System.out.println("path es " + path.toString());
		 * 
		 * File dirHAM = new File(path.toString()); for (File f :
		 * dirHAM.listFiles()) { System.out.println("get path: " + f.getPath());
		 * DataSourceConfig dataSource = new DataSourceConfig(
		 * DataSourceConfig.IS_HAM, DataSourceConfig.WARC_DS, f.getPath());
		 * dataSourceList.add(dataSource); } path = new
		 * StringBuilder(sb.toString()); path.append("_spam_");
		 * 
		 * File dirSPAM = new File(path.toString()); for (File f :
		 * dirSPAM.listFiles()) { DataSourceConfig dataSource = new
		 * DataSourceConfig( DataSourceConfig.IS_SPAM, DataSourceConfig.WARC_DS,
		 * f.getPath()); dataSourceList.add(dataSource); } }
		 */

		return dSHandlerList;
	}

}
