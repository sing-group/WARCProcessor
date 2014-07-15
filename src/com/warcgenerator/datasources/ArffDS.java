package com.warcgenerator.datasources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.CloseException;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;

public class ArffDS extends DataSource implements IDataSource {
	private ArffReader arff;
	private Instances data;
	private BufferedReader reader;

	private static Logger logger = Logger.getLogger(ArffDS.class);

	/**
	 * Open a Arff datasource in read mode
	 * 
	 * @param path
	 * @throws DSException
	 */
	public ArffDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);

		try {
			logger.info("Openning Arff file: "
					+ this.getDataSourceConfig().getFilePath());

			reader = new BufferedReader(new FileReader(this
					.getDataSourceConfig().getFilePath()));

			arff = new ArffReader(reader, 1000);
			data = arff.getStructure();
			data.setClassIndex(data.numAttributes() - 1);

			// setting class attribute if the data format does not provide this
			// information
			// For example, the XRFF format saves the class attribute
			// information as well

		} catch (Exception e) {
			throw new OpenException(e);
		}
	}

	public DataBean read() throws DSException {
		logger.info("Reading datasource...");
		DataBean dataBean = null;

		Instance inst;
		try {
			inst = arff.readInstance(data);
			if (inst != null) {
				data.add(inst);

				boolean isSpam = false;
				String label = inst.stringValue(inst.dataset().attribute(
						"LABEL"));
				if (label.toLowerCase().equals("spam")) {
					isSpam = true;
				}

				String url = inst.stringValue(inst.dataset()
						.attribute("URL"));
				// Removing the string URL_ before the useful data
				if (url.matches("URL_(.*)")) {
					url = url.substring(4, url.length());
				}

				dataBean = new DataBean();
				dataBean.setUrl(url);
				dataBean.setSpam(isSpam);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * if (ar != null) { dataBean = new WarcBean(ar); //
		 * System.out.println("databean: " + dataBean.getData()); }
		 */

		return dataBean;
	}

	public void write(DataBean bean) throws DSException {
		// It's not necesary to implement it at the moment
	}

	public void close() throws DSException {
		try {
			reader.close();
		} catch (IOException e) {
			throw new CloseException(e);
		}
	}

}
