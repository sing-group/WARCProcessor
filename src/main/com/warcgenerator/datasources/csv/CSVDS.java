package com.warcgenerator.datasources.csv;

import java.io.File;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.common.bean.DataBean;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;

/**
 * CSV DataSource
 * 
 * @author Miguel Callon
 */
public class CSVDS extends DataSource implements IDataSource {
	public static final String SPAM_COL = "SpamAttribute";
	public static final String URL_COL = "URLAttribute";
	public static final String FIELD_SEPARATOR = "FieldSeparator";
	public static final String SPAM_COL_SPAM_VALUE = "SpamValue";
	public static final String HEADER_ROW_PRESENT = "HeaderRowPresent";
	public static final String REGEXP_URL_TAG = "RegExpURLAttribute";

	private String paramList[] = {
			SPAM_COL, URL_COL, FIELD_SEPARATOR, SPAM_COL_SPAM_VALUE,
			HEADER_ROW_PRESENT, REGEXP_URL_TAG
	};
	
	private CSVLoader loader;
	private Instances data;

	private Iterator<Instance> readIterator;

	private static Logger logger = Logger.getLogger(CSVDS.class);

	/**
	 * Open a Arff datasource in read mode
	 * 
	 * @param dsConfig DataSourceConfig
	 * @throws DSException If error
	 */
	public CSVDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);
		validate(paramList);

		try {
			logger.info("Opening CSV File: "
					+ this.getDataSourceConfig().getFilePath());

			// load CSV
			loader = new CSVLoader();

			String[] options = weka.core.Utils.splitOptions("-F "
					+ this.getDataSourceConfig().getCustomParams()
							.get(FIELD_SEPARATOR).getValue());
			loader.setOptions(options);

			// Set if datasource has header
			loader.setNoHeaderRowPresent(!Boolean.parseBoolean(this
					.getDataSourceConfig().getCustomParams()
					.get(HEADER_ROW_PRESENT).getValue()));

			loader.setSource(new File(this.getDataSourceConfig().getFilePath()));
			data = loader.getDataSet();
			readIterator = data.iterator();
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenException(e);
		}
	}

	public DataBean read() throws DSException {
		DataBean dataBean = null;

		if (readIterator.hasNext()) {
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
				url = inst.stringValue(inst.dataset().attribute(urlColName));
			}

			Object spamCol = this.getDataSourceConfig().getCustomParams()
					.get(SPAM_COL).getValue();
			
			String textoSpamLabel = "";
			Attribute attribute = null;
			try {
				Integer spamColNum = Integer.valueOf((String) spamCol);
				attribute = inst.dataset().attribute(spamColNum);
			} catch (NumberFormatException ex) {
				String spamColName = (String) spamCol;
				attribute = inst.dataset().attribute(spamColName);
			}
			
			if (attribute != null) {
				textoSpamLabel = String.valueOf(inst.toString(attribute));

				// Set the value that must be considerec to be a spam row
				String spamColSpamValue = this.getDataSourceConfig()
						.getCustomParams().get(SPAM_COL_SPAM_VALUE).getValue();
				if (textoSpamLabel.toLowerCase().equals(spamColSpamValue)) {
					isSpam = true;
				}
			}

			Pattern pattern = Pattern.compile(this.getDataSourceConfig()
					.getCustomParams().get(REGEXP_URL_TAG).getValue());
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				url = matcher.group(1);
			}
			
			dataBean = new DataBean();
			dataBean.setUrl(url);
			dataBean.setSpam(isSpam);
		}

		return dataBean;
	}

	public void write(DataBean bean) throws DSException {
		// It's not necessary to implement it at the moment
	}

	public void close() throws DSException {
		// It's not necessary to implement it at the moment
	}
}
