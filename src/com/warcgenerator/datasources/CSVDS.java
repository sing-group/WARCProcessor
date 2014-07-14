package com.warcgenerator.datasources;

import java.io.BufferedReader;
import java.io.File;

import org.apache.log4j.Logger;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataBean;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;

public class CSVDS extends DataSource implements IDataSource {
	private CSVLoader loader;
	private Instances data;
	private BufferedReader reader;

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
		
			String[] options = weka.core.Utils.splitOptions("-F ;");
			loader.setOptions(options);
			
			loader.setSource(new File(this.getDataSourceConfig().getFilePath()));
			
			data = loader.getDataSet();
			
			// setting class attribute if the data format does not provide this
			// information
			// For example, the XRFF format saves the class attribute
			// information as well

		} catch (Exception e) {
			throw new OpenException(e);
		}
	}

	public DataBean read() throws DSException {
		System.out.println("Leyendo datasource!!");
		DataBean dataBean = null;

		Instance inst;
		inst = data.firstInstance();
		
		if (inst != null) {
			data.add(inst);

			boolean isSpam = false;
			// TODO Customize this, How can I know if this is spam or ham ?
			/*String label = inst.stringValue(inst.dataset().attribute("LABEL"));
			if (label.toLowerCase().equals("spam")) {
				isSpam = true;
			}*/

			String url = inst.stringValue(inst.dataset().attribute(0));

			dataBean = new DataBean();
			dataBean.setUrl(url);
			dataBean.setSpam(isSpam);
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
		// It's not necesary to implement it at the moment
	}

}
