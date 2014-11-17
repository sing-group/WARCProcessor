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
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReader;
import org.archive.io.warc.WARCReaderFactory;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import com.warcgenerator.core.config.CustomParamConfig;
import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.helper.FileHelper;
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
	
	
	private CSVLoader loader;
	private Instances data;

	private Iterator<Instance> readIterator;

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
			System.out.println("url es: " + url + ", valor: "
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
		WARCReader warcReader = null;

		if (WARCReaderFactory.isWARCSuffix(warcFile.getName())) {
			try {
				warcReader = openWarc(warcFile);
	
				buildDomains(manager, warcReader, warcFile);
			} catch (IOException ex) {
				throw new OpenException(ex);
			} finally {
				if (warcReader != null) {
					try {
						warcReader.close();
					} catch (IOException ex) {
						throw new OpenException(ex);
					}
				}
			}
		}
	}

	/**
	 * Open Warc
	 * 
	 * @param filePath
	 * @return WARCReader
	 * @throws OpenException
	 */
	private WARCReader openWarc(File file) throws OpenException {
		try {
			// logger.info("Openning file: " + dsConfig.getFilePath());
			// ArchiveReader ar = WARCReaderFactory.get(file.getAbsolutePath(),
			// new FileInputStream(file), true);
			
			return WARCReaderFactory.get(file);
		} catch (IOException e) {
			throw new OpenException(e);
		}
	}

	/**
	 * Get
	 * 
	 * @param reader
	 * @return
	 */
	private void buildDomains(DBManager manager, WARCReader reader,
			File warcFile) throws IOException {
		boolean skip = false;

		/*
		 * reader.setDigest(true); try { reader.dump(true); } catch
		 * (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		Iterator<ArchiveRecord> archIt = reader.iterator();
		while (archIt.hasNext()) {
			skip = false;
			try (ArchiveRecord ar = archIt.next()) {
				if (ar != null) {
					System.out.println("leyendo!!");

					// Get the filename
					String url = (String) ar
							.getHeader()
							.getHeaderFields()
							.get(((CustomParamConfig) this
									.getDataSourceConfig().getCustomParams()
									.get(URL_TAG)).getValue());
					if (url == null || url.equals("")) {
						System.out.println("Skip true");

						skip = true;
					}

					if (skip == false) {
						System.out.println("Skip false");
						URLInfo urlInfo = new URLInfo();
						urlInfo.setDomain(FileHelper.getDomainNameFromURL(url));
						urlInfo.setUrl(url);
						urlInfo.setFilePath(warcFile.getAbsolutePath());
						// Check if spam
						Boolean spam = domainSpam.get(urlInfo.getDomain());
						if (spam != null) {
							urlInfo.setSpam(spam);
						} else {
							// TODO Parametrize
							logger.info("Can not determine if the domain: "
									+ urlInfo.getDomain()
									+ ", is spam. Set spam false.");
						}
						write(manager, urlInfo);
					}

					System.out.println("leido!!");
				}
			}
		}
		;
	}

	private void write(DBManager manager, URLInfo urlInfo) throws IOException {
		// System.out.println(urlInfo);
		System.out.println(urlInfo);
		manager.put(urlInfo);
	}

	/**
	 * Init CSV file
	 * 
	 * @throws OpenException
	 */
	private void initCSV(Map<String, Boolean> domainSpam) throws OpenException {
		try {
			logger.info("Opening WarcCSV File: "
					+ this.getDataSourceConfig().getFilePath());

			// Load CSV
			loader = new CSVLoader();

			String[] options = weka.core.Utils.splitOptions("-F "
					+ this.getDataSourceConfig().getCustomParams()
							.get(FIELD_SEPARATOR).getValue());

			// loader.setStringAttributes(value);
			// loader.setNoHeaderRowPresent(true);
			loader.setOptions(options);

			// loader.setSource(new
			// File("C:\\Users\\Administrador\\git\\proyecto\\in\\csv\\de.testing.csv"));
			// loader.setSource(new
			// File("C:\\Users\\Administrador\\Documents\\ClueWeb09_English_1\\spam.eng_.labels_0.txt"));
			loader.setSource(new File(this.getDataSourceConfig().getCustomParams()
					.get(FILE_CSV).getValue()));

			data = loader.getDataSet();

			readIterator = data.iterator();

			while (readIterator.hasNext()) {
				Instance inst = readIterator.next();
				boolean isSpam = false;

				Object urlCol = this.getDataSourceConfig().getCustomParams()
						.get(URL_COL).getValue();

				String url = null;
				try {
					Integer urlColNum = Integer.valueOf((String) urlCol);

					url = inst.stringValue(inst.dataset().attribute(urlColNum));
				} catch (NumberFormatException ex) {
					String urlColName = (String) urlCol;
					url = inst
							.stringValue(inst.dataset().attribute(urlColName));
				}

				Object spamCol = this.getDataSourceConfig().getCustomParams()
						.get(SPAM_COL).getValue();
				String textoSpamLabel = "";
				try {
					Integer spamColNum = Integer.valueOf((String) spamCol);

					textoSpamLabel = inst.toString(inst.dataset().attribute(
							spamColNum));
				} catch (NumberFormatException ex) {
					String spamColName = (String) spamCol;
					Attribute attr = inst.dataset().attribute(spamColName);
					if (attr != null) {
						textoSpamLabel = inst.stringValue(attr);
					}
				}

				// Set the value that must be considerec to be a spam row
				String spamColSpamValue = this.getDataSourceConfig()
						.getCustomParams().get(SPAM_COL_SPAM_VALUE).getValue();
				if (textoSpamLabel.toLowerCase().equals(spamColSpamValue)) {
					isSpam = true;
				}

				domainSpam.put(url, isSpam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenException(e);
		}

	}
	
	public DataBean read() throws DSException {
		DataBean dataBean = null;

		if (iterator.hasNext()) {
			URLInfo urlInfo = iterator.next();
			String warcFilePath = urlInfo.getFilePath();
			ArchiveRecord ar = null;
			
			WARCReader reader = openWarc(new File(warcFilePath));
			Iterator<ArchiveRecord> archIt = reader.iterator();
			try {
				boolean skip = false;
				do {
					skip = false;
					if (archIt.hasNext()) {
						ar = archIt.next();
					}
					if (ar != null) {
						// Get the filename
						String url = (String) ar.getHeader()
								.getHeaderFields().get(((CustomParamConfig)this.getDataSourceConfig().getCustomParams()
										.get(URL_TAG)).getValue());
						if (url == null || url.equals("")) {
							skip = true;
						}
						
						if (skip == false) {
							dataBean = new DataBean();
							dataBean.setUrl(url);
							
							boolean isSpam = false;
							// If it's not specify either isSpam or not, set spam
							if (this.getDataSourceConfig().getSpam() != null) {
								isSpam = this.getDataSourceConfig().getSpam();
							}
							
							dataBean.setSpam(isSpam);
							dataBean.setData(ar);
							dataBean.setTypeDS(DS_TYPE);
							
							StringBuffer sb = new StringBuffer();
							try {
								for (byte[] buffer = new byte[1024];
										ar.read(buffer) != -1;) {
									sb.append(new String(buffer));
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dataBean.setData(sb.toString());
							
							// Turn the out file to the warc file name
							this.setOutputFilePath(
									FileHelper.getOutputFileName(dataBean.getUrl()));
							
							try {
								ar.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} while (skip != false && dataBean == null);
			} finally  {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
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
		//dsConfig.setFilePath("C:/Users/Administrador/Documents/prueba_corpus/en0000/0013wb-88.warc");
		dsConfig.setFilePath("C:/Users/Administrador/Documents/prueba_corpus/en0000");

		
		WarcCSVDS plugin = new WarcCSVDS(dsConfig);
		
		DataBean dataBean = null;
		do {
			dataBean = plugin.read();
			System.out.println("dataBean: " + dataBean);
		} while (dataBean != null);
		// plugin.fileIndex(manager,
		// "C:/Users/Administrador/Documents/ClueWeb09_English_1/en0000/00.warc.gz");

		// plugin.fileIndex(manager,
		// "C:/Users/Administrador/git/proyecto/in/0013wb-88.warc");

		/*
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (InstantiationException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalAccessException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (ClassNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// String = fileIndex(String indexFilePath, String warcFilePath);
	}
}