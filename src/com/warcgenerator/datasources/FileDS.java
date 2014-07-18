package com.warcgenerator.datasources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.DataSource;
import com.warcgenerator.core.datasource.IDataSource;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.CloseException;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.exception.datasource.ReadException;
import com.warcgenerator.core.helper.FileHelper;

/**
 * DataSource to read blacklist and whitelist in plain text format
 * @author Miguel Callon
 */
public class FileDS extends DataSource implements IDataSource {
	private BufferedReader buffer = null;
	
	/**
	 * Main constructor to instance a FileDS class
	 * @param dsConfig @type of DataSourceConfig
	 * @throws DSException
	 */
	public FileDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);
		try {
			buffer = new BufferedReader(new FileReader(
					dsConfig.getFilePath()));
		} catch (FileNotFoundException e) {
			throw new OpenException(e);
		}
	}

	/**
	 * Read a DataBean from the datasource
	 * @return DataBean
	 */
	public DataBean read() throws DSException {
		DataBean dataBean = null;
		try {
			String line = new String();
			line = buffer.readLine();
			if (line != null) {
				dataBean = this.parseLine(line);
				
				// Turn the out file to the warc file name
				this.setOutputFilePath(
						FileHelper.getOutputFileName(dataBean.getUrl()));
			}
		} catch (IOException e) {
			throw new ReadException(e);
		}
		return dataBean;
	}

	/**
	 * Write a DataBean
	 */
	public void write(DataBean data) {
		// Not implemented
	}

	/**
	 * Close datasource
	 */
	public void close() throws DSException {
		try {
			buffer.close();
		} catch (IOException e) {
			throw new CloseException(e);
		}
	}

	/**
	 * Parse the content of a single line in datasource
	 * @param line String with the content of a single line
	 * @return DataBean
	 */
	protected DataBean parseLine(String line) {
		DataBean data = new DataBean();
		String[] array = line.split(" ");
		data.setUrl(array[0]);
		data.setSpam(this.getDataSourceConfig().isSpam());
		return data;
	}
}
