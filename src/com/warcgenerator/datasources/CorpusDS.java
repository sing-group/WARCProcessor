package com.warcgenerator.datasources;

import java.io.File;

import org.apache.log4j.Logger;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.config.OutputWarcConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.WarcDS;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.datasources.bean.CorpusFileBean;

/**
 * DataSource used to read Corpus files
 * @author Miguel Callon
 *
 */
public class CorpusDS extends DataSource implements IDataSource {
	@SuppressWarnings("unused")
	private OutputWarcConfig config;
	private CorpusFileBean corpus;
	private IDataSource currentDS;

	private static Logger logger = Logger.getLogger(CorpusDS.class);

	/**
	 * Open a Warc datasource in read mode
	 * 
	 * @param path
	 * @throws DSException
	 */
	public CorpusDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);
		logger.info("Opening Corpus file: " + dsConfig.getFilePath());
		corpus = new CorpusFileBean(dsConfig.getFilePath());
	}

	public DataBean read() throws DSException {
		logger.info("Reading datasource...");
		DataBean dataBean = null;
		DataSourceConfig dsConfig = null;

		if (currentDS == null) {
			if (corpus.getSpam().hasNext()) {
				dsConfig = new DataSourceConfig();
				File f = corpus.getSpam().next();
				dsConfig.setFilePath(f.getPath());
				dsConfig.setSpamOrHam(DataSourceConfig.IS_SPAM);
				currentDS = new WarcDS(dsConfig);
				
				// Turn the outfile to the warc file name
				this.getDataSourceConfig().setFilePath(f.getPath());
			} else if (corpus.getHam().hasNext()) {
				dsConfig = new DataSourceConfig();
				File f = corpus.getHam().next();
				dsConfig.setFilePath(f.getPath());
				dsConfig.setSpamOrHam(DataSourceConfig.IS_HAM);
				currentDS = new WarcDS(dsConfig);
				
				// Turn the outfile to the warc file name
				this.getDataSourceConfig().setFilePath(f.getPath());
			}
		}

		if (currentDS != null) {
			if ((dataBean = currentDS.read()) == null) {
				currentDS.close();
				currentDS = null;
				dataBean = read();
			}
		}
		
		return dataBean;
	}
	
	public void write(DataBean bean) throws DSException {
		// Not implemented
	}
	
	public void close() throws DSException {
		// Not implemented
	}
}
