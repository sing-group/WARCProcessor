package com.warcgenerator.core.datasource;

import com.warcgenerator.core.config.DataSourceConfig;

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
	 * @param filePath
	 */
	public void setOutputFilePath(String filePath) {
		this.getDataSourceConfig().setFilePath(filePath);
	}
}
