package com.warcgenerator.core.datasource;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.exception.datasource.MissingParamsException;

public abstract class DataSource implements IDataSource {
	private DataSourceConfig dataSourceConfig;
	
	public DataSource() {}
	
	public DataSource(DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}

	public DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

	public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}
	
	/**
	 * Used to set the output file path
	 * @param filePath Path to output file
	 */
	public void setOutputFilePath(String filePath) {
		this.getDataSourceConfig().setFilePath(filePath);
	}
	
	/**
	 * Check if exist all mandatory params
	 * @param paramsList DataSource params
	 */
	public void validate(String[] paramsList)
			throws MissingParamsException {
		for (String param : paramsList) {
			if (!this.getDataSourceConfig().getCustomParams().containsKey(param)) {
				throw new MissingParamsException(param);
			}
		}
	}
}
