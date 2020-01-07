package com.warcgenerator.datasources.warccsv;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.archive.io.warc.WARCReaderFactory;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.datasource.warc.WarcDS;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.helper.FileHelper;
import com.warcgenerator.datasources.csv.CSVDS;

public class WarcCSVDS extends DataSource implements IDataSource {
	public static final String DS_TYPE = "WarcCSVDS";

	public static final String URL_TAG = "WarcURLTag";
	public static final String SPAM_COL = "SpamAttribute";
	public static final String URL_COL = "URLAttribute";
	public static final String FIELD_SEPARATOR = "FieldSeparator";
	public static final String SPAM_COL_SPAM_VALUE = "SpamValue";
	public static final String FILE_CSV = "FileCSV";
	public static final String HEADER_ROW_PRESENT = "HeaderRowPresent";
	public static final String REGEXP_URL_TAG = "RegExpURLAttribute";

	private String paramList[] = { URL_TAG, SPAM_COL, URL_COL, FIELD_SEPARATOR,
			SPAM_COL_SPAM_VALUE, FILE_CSV, HEADER_ROW_PRESENT, REGEXP_URL_TAG };

	private static Logger logger = LogManager.getLogger(WarcCSVDS.class);

	private Map<String, Boolean> domainSpam;
	List<WarcDS> warcDSs;
	private Iterator<WarcDS> warcDSsIt;
	private WarcDS warcDSCurrent;

	/**
	 * Open a Arff datasource in read mode
	 * 
	 * @param dsConfig DataSourceConfig
	 * @throws DSException If error
	 */
	public WarcCSVDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);
		validate(paramList);

		domainSpam = new LinkedHashMap<String, Boolean>();
		warcDSs = new ArrayList<WarcDS>();
		initCSV(domainSpam);
		buildIndex(warcDSs, dsConfig.getFilePath());
		warcDSsIt = warcDSs.iterator();
		
		if (warcDSsIt.hasNext()) {
			warcDSCurrent = warcDSsIt.next();
		}

		for (String url : domainSpam.keySet()) {
			logger.info("CSV index: " + url + ", isSpam: "
					+ domainSpam.get(url));
		}
	}

	private void buildIndex(List<WarcDS> warcDSs, String dir) {
		fileDir(warcDSs, new File(dir));
	}

	public void fileDir(List<WarcDS> warcDSs, File file) {
		if (file.isDirectory()) {
			for (File fileTmp : file.listFiles()) {
				fileDir(warcDSs, fileTmp);
			}
		} else {
			fileIndex(warcDSs, file);
		}
	}

	public void fileIndex(List<WarcDS> warcDSs, File warcFile) {
		WarcDS warc = null;
		if (WARCReaderFactory.isWARCSuffix(warcFile.getName())) {
			logger.info("Idexing file: " + warcFile.getAbsolutePath());
			DataSourceConfig dsConfig = new DataSourceConfig();
			dsConfig.setFilePath(warcFile.getAbsolutePath());
			// spam field is not used here
			dsConfig.setSpam(DataSourceConfig.IS_HAM);
			dsConfig.setCustomParams(this.getDataSourceConfig()
					.getCustomParams());
			warcDSs.add(new WarcDS(dsConfig));
		}
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
			dsConfig.setFilePath(this.getDataSourceConfig().getCustomParams()
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

		// Read from a warcDS
		if (warcDSCurrent != null) {
			while ((dataBean = warcDSCurrent.read()) == null
					&& warcDSsIt.hasNext()) {
				warcDSCurrent.close();
				warcDSCurrent = warcDSsIt.next();
			}
		}

		if (dataBean != null) {
			// Check if spam
			Boolean spam = domainSpam.get(FileHelper.getDomainNameFromURL(dataBean
					.getUrl()));
			if (spam != null) {
				dataBean.setSpam(spam);
			} else {
				// TODO Parametrize
				logger.info("Can not determine if the domain: " + dataBean.getUrl()
						+ ", is spam. Set spam false.");
			}
		}
		return dataBean;
	}

	public void write(DataBean bean) throws DSException {
		// It's not necesary to implement it at the moment
	}

	public void close() throws DSException {
		for (WarcDS warcDS : warcDSs) {
			warcDS.close();
		}
	}
}