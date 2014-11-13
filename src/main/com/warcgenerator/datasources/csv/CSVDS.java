package com.warcgenerator.datasources.csv;

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;

public class CSVDS extends DataSource implements IDataSource {
	private static final String SPAM_COL = "spamCol";
	private static final String URL_COL = "urlCol";
	private static final String FIELD_SEPARATOR = "fieldSeparator";
	private static final String SPAM_COL_SPAM_VALUE = "spamColSpamValue";

	private CSVLoader loader;
	private Instances data;

	private Iterator<Instance> readIterator;

	private static Logger logger = Logger.getLogger(CSVDS.class);

	/**
	 * Open a Arff datasource in read mode
	 * 
	 * @param path
	 * @throws DSException
	 */
	public CSVDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);

		try {
			logger.info("Opening CSV File: "
					+ this.getDataSourceConfig().getFilePath());

			// load CSV
			loader = new CSVLoader();

			String[] options = weka.core.Utils.splitOptions("-F "
					+ this.getDataSourceConfig().getCustomParams()
							.get(FIELD_SEPARATOR).getValue());
			loader.setOptions(options);

			loader.setSource(new File(this.getDataSourceConfig().getFilePath()));
			data = loader.getDataSet();
			readIterator = data.iterator();

			// setting class attribute if the data format does not provide this
			// information
			// For example, the XRFF format saves the class attribute
			// information as well

		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenException(e);
		}
	}

	public DataBean read() throws DSException {
		DataBean dataBean = null;

		/*
		 * Instance inst = null; if (data.numInstances() > 0) { inst =
		 * data.firstInstance(); }
		 */

		// if (inst != null) {
		// data.add(inst);
		if (readIterator.hasNext()) {
			Instance inst = readIterator.next();
			boolean isSpam = false;
			// TODO Customize this, How can I know if this is spam or ham ?
			/*
			 * String label =
			 * inst.stringValue(inst.dataset().attribute("LABEL")); if
			 * (label.toLowerCase().equals("spam")) { isSpam = true; }
			 */
		
				
			Object urlCol = this.getDataSourceConfig()
					.getCustomParams().get(URL_COL).getValue();
			
			String url = null;
			try { 
				Integer urlColNum = Integer.valueOf((String)urlCol);
				
				url = inst.stringValue(inst.dataset().attribute(urlColNum));
			} catch (NumberFormatException ex) {
				String urlColName = (String)urlCol;
				url = inst.stringValue(inst.dataset().attribute(urlColName));
			}
			
			Object spamCol = this.getDataSourceConfig()
					.getCustomParams().get(SPAM_COL).getValue();
			String textoSpamLabel = "";
			try { 
				Integer spamColNum = Integer.valueOf((String)spamCol);
				
				textoSpamLabel = String.valueOf(
						inst.stringValue(inst.dataset().attribute(spamColNum)));
			} catch (NumberFormatException ex) {
				String spamColName = (String)spamCol;
				Attribute attr = inst.dataset().attribute(spamColName);
				if (attr != null) {
					textoSpamLabel = String.valueOf(inst.stringValue(attr));
				}
			}
			
			// Set the value that must be considerec to be a spam row
			String spamColSpamValue = this.getDataSourceConfig()
					.getCustomParams().get(SPAM_COL_SPAM_VALUE).getValue();
			if (textoSpamLabel.toLowerCase().equals(spamColSpamValue)) {
				isSpam = true;
			}

			dataBean = new DataBean();
			dataBean.setUrl(url);
			dataBean.setSpam(isSpam);
		}

		return dataBean;
	}

	public void write(DataBean bean) throws DSException {
		// It's not necesary to implement it at the moment
	}

	public void close() throws DSException {
		// It's not necesary to implement it at the moment
	}

}
