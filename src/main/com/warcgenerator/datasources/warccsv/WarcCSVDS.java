package com.warcgenerator.datasources.warccsv;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.archive.io.warc.WARCReaderFactory;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.datasources.csv.CSVDS;
import com.warcgenerator.datasources.warccsv.bean.URLInfo;
import com.warcgenerator.datasources.warccsv.dao.DBDAO;
import com.warcgenerator.datasources.warccsv.manager.DBManager;
import com.warcgenerator.datasources.warccsv.util.ConnectionUtil;

public class WarcCSVDS extends DataSource implements IDataSource {
	public static final String DS_TYPE = "WarcCSVDS";

	private static final String URL_TAG = "WarcURLTag";

	private static final String SPAM_COL = "SpamAttribute";
	private static final String URL_COL = "URLAttribute";
	private static final String FIELD_SEPARATOR = "FieldSeparator";
	private static final String SPAM_COL_SPAM_VALUE = "SpamValue";
	private static final String FILE_CSV = "FileCSV";
	private static final String REGEXP_URL_TAG = "RegExpURLAttribute";

	private static Logger logger = Logger.getLogger(WarcCSVDS.class);

	private Map<String, Boolean> domainSpam;

	private Iterator<URLInfo> iterator;

	/**
	 * Open a Arff datasource in read mode
	 * 
	 * @param path
	 * @throws DSException
	 */
	public WarcCSVDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);

		domainSpam = new LinkedHashMap<String, Boolean>();
		initCSV(domainSpam);

		for (String url : domainSpam.keySet()) {
			logger.info("CSV index: " + url + ", isSpam: "
					+ domainSpam.get(url));
		}

		String connectionString = "jdbc:derby:derbyDB;create=true";

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

			Connection connection = ConnectionUtil
					.getConnection(connectionString);

			DBDAO dbDAO = new DBDAO(connection);
			DBManager manager = new DBManager(dbDAO);

			buildIndex(manager, dsConfig.getFilePath());

			List<URLInfo> list = manager.list();
			iterator = list.iterator();

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buildIndex(DBManager manager, String dir) {
		fileDir(manager, new File(dir));
	}

	public void fileDir(DBManager manager, File file) {
		if (file.isDirectory()) {
			for (File fileTmp : file.listFiles()) {
				fileDir(manager, fileTmp);
			}
		} else {
			fileIndex(manager, file);
		}
	}

	public void fileIndex(DBManager manager, File warcFile) {
		WarcDS warc = null;
		if (WARCReaderFactory.isWARCSuffix(warcFile.getName())) {
			try {
				logger.info("Idexing file: " + warcFile.getAbsolutePath());
				DataSourceConfig dsConfig = new DataSourceConfig();
				dsConfig.setFilePath(warcFile.getAbsolutePath());
				// spam field is not used here
				dsConfig.setSpam(DataSourceConfig.IS_HAM);
				dsConfig.setCustomParams(this.getDataSourceConfig()
						.getCustomParams());
				warc = new WarcDS(dsConfig);

				buildDomains(manager, warc, warcFile);
			} catch (IOException ex) {
				throw new OpenException(ex);
			} finally {
				if (warc != null) {
					warc.close();
				}
			}
		}
	}

	private void buildDomains(DBManager manager, WarcDS warcDS, File warcFile)
			throws IOException {
		DataBean dataBean = null;
		while ((dataBean = warcDS.read()) != null) {
			URLInfo urlInfo = new URLInfo();
			urlInfo.setDomain(FileHelper.getDomainNameFromURL(dataBean.getUrl()));
			urlInfo.setUrl(dataBean.getUrl());
			urlInfo.setFilePath(warcFile.getAbsolutePath());
			// Check if spam
			Boolean spam = domainSpam.get(urlInfo.getDomain());
			if (spam != null) {
				urlInfo.setSpam(spam);
			} else {
				// TODO Parametrize
				logger.info("Can not determine if the domain: "
						+ urlInfo.getDomain() + ", is spam. Set spam false.");
			}
			write(manager, urlInfo);
		}
	}

	private void write(DBManager manager, URLInfo urlInfo) throws IOException {
		manager.put(urlInfo);
	}

	/**
	 * Init CSV file
	 * 
	 * @throws OpenException
	 */
	private void initCSV(Map<String, Boolean> domainSpam) throws OpenException {
		CSVDS csvDS = null;
		try {
			DataSourceConfig dsConfig = new DataSourceConfig();
			dsConfig.setFilePath(this
					.getDataSourceConfig().getCustomParams()
					.get(FILE_CSV).getValue());
			// spam field is not used here
			dsConfig.setCustomParams(this.getDataSourceConfig()
					.getCustomParams());
			csvDS = new CSVDS(dsConfig);
			
			DataBean dataBean;
			while ((dataBean = csvDS.read()) != null) {
				domainSpam.put(dataBean.getUrl(), dataBean.isSpam());
			}
		} finally {
			if (csvDS != null) {
				csvDS.close();
			}
		}
	}

	public DataBean read() throws DSException {
		DataBean dataBean = null;
		if (iterator.hasNext()) {
			URLInfo urlInfo = iterator.next();
			String warcFilePath = urlInfo.getFilePath();
		
			DataSourceConfig dsConfig = new DataSourceConfig();
			dsConfig.setFilePath(warcFilePath);
			// spam field is not used here
			dsConfig.setSpam(DataSourceConfig.IS_HAM);
			dsConfig.setCustomParams(this.getDataSourceConfig()
					.getCustomParams());
			
			WarcDS warcDS = null;
			try {
				warcDS = new WarcDS(dsConfig);
				while ((dataBean = warcDS.read()) != null &&
					!dataBean.getUrl().equals(urlInfo.getUrl()));
				dataBean.setSpam(urlInfo.getSpam());
			} finally {
				if (warcDS != null)
					warcDS.close();
			}
		}
		return dataBean;
	}

	public void write(DataBean bean) throws DSException {
		// It's not necesary to implement it at the moment
	}

	public void close() throws DSException {
		// It's not necesary to implement it at the moment
	}

	public static void main(String args[]) {
		// String connectionString = "jdbc:derby:derbyDB;create=true";
		String connectionString = "jdbc:derby:derbyDB;create=true";

		/*
		 * try {
		 * Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		 * 
		 * Connection connection =
		 * ConnectionUtil.getConnection(connectionString);
		 * 
		 * DBDAO dbDAO = new DBDAO(connection); DBManager manager = new
		 * DBManager(dbDAO);
		 */

		/*
		 * URLInfo urlInfo = new URLInfo(); urlInfo.setDomain("www.url.com");
		 * urlInfo.setUrl("http://www.info.com?p=52");
		 * urlInfo.setFilePath("c:/cosa");
		 * 
		 * manager.put(urlInfo);
		 */

		/*
		 * List<URLInfo> urls = manager.get("www.url.com"); for (URLInfo url:
		 * urls) { System.out.println(url); }
		 */

		DataSourceConfig dsConfig = new DataSourceConfig();

		CustomParamConfig customParam = new CustomParamConfig();

		customParam.setType(URL_TAG);
		customParam.setName(URL_TAG);
		customParam.setValue("WARC-Target-URI");
		dsConfig.getCustomParams().put(URL_TAG, customParam);

		customParam = new CustomParamConfig();

		customParam.setType(FIELD_SEPARATOR);
		customParam.setName(FIELD_SEPARATOR);
		customParam.setValue(",");
		customParam.setDefaultValue(",");

		dsConfig.getCustomParams().put(FIELD_SEPARATOR, customParam);

		customParam = new CustomParamConfig();
		customParam.setType(URL_COL);
		customParam.setName(URL_COL);
		customParam.setValue("0");
		customParam.setDefaultValue("0");

		dsConfig.getCustomParams().put(URL_COL, customParam);

		customParam = new CustomParamConfig();
		customParam.setType(SPAM_COL);
		customParam.setName(SPAM_COL);
		customParam.setValue("1");
		customParam.setDefaultValue("1");

		dsConfig.getCustomParams().put(SPAM_COL, customParam);

		customParam = new CustomParamConfig();
		customParam.setType(SPAM_COL_SPAM_VALUE);
		customParam.setName(SPAM_COL_SPAM_VALUE);
		customParam.setValue("1");
		customParam.setDefaultValue("1");

		dsConfig.getCustomParams().put(SPAM_COL_SPAM_VALUE, customParam);

		// dsConfig.setFilePath("C:/Users/Administrador/git/proyecto/in/0013wb-88.warc");
		// dsConfig.setFilePath("C:/Users/Administrador/Documents/ClueWeb09_English_1/en0000/00.warc/00.warc");
		// dsConfig.setFilePath("C:/Users/Administrador/Documents/ClueWeb09_English_1/en0000/00.warc.gz");
		// dsConfig.setFilePath("C:/Users/Administrador/Documents/prueba_corpus/en0000/0013wb-88.warc");
		dsConfig.setFilePath("C:/Users/Administrador/Documents/prueba_corpus/en0000");

		WarcCSVDS plugin = new WarcCSVDS(dsConfig);

		DataBean dataBean = null;
		do {
			dataBean = plugin.read();
		} while (dataBean != null);
	}
}