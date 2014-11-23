package com.warcgenerator.core.datasource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.CloseException;
import com.warcgenerator.core.exception.datasource.DSException;
import com.warcgenerator.core.exception.datasource.OpenException;
import com.warcgenerator.core.exception.datasource.ReadException;
import com.warcgenerator.core.exception.datasource.WriteException;

/**
 * DataSource to read and write in plain text format
 * @author Miguel Callon
 */
public class GenericDS extends DataSource implements IDataSource {
	private BufferedReader bufferReader = null;
	private BufferedWriter bufferWriter = null;
	
	public GenericDS(DataSourceConfig dsConfig) throws DSException {
		super(dsConfig);
		File file = new File(dsConfig.getFilePath());
		try {
			file.createNewFile();
			bufferReader = new BufferedReader(new FileReader(
					file));
			bufferWriter = new BufferedWriter(new FileWriter(
					file));
		} catch (Exception e) {
			throw new OpenException(e);
		}
	}

	public DataBean read() throws DSException {
		DataBean dataBean = null;
		try {
			String line = new String();
			line = bufferReader.readLine();
			if (line != null) {
				dataBean = new DataBean();
				dataBean.setUrl(line);
				//dataBean.setSpam(this.getDataSourceConfig().getSpam());
			} 
		} catch (IOException e) {
			throw new ReadException(e);
		}
		return dataBean;
	}

	public void write(DataBean data) throws DSException {
		try {
			bufferWriter.write((String)data.getData());
			bufferWriter.newLine();
			bufferWriter.flush();
		} catch (IOException e) {
			throw new WriteException(e);
		}
	}

	public void close() throws DSException {
		try {
			bufferReader.close();
			bufferWriter.close();
		} catch (IOException e) {
			throw new CloseException(e);
		}
	}
}
