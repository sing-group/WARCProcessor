package com.warcgenerator.core.datasource;

import com.warcgenerator.core.config.DataSourceConfig;
import com.warcgenerator.core.datasource.bean.DataBean;
import com.warcgenerator.core.exception.datasource.DSException;

public interface IDataSource {	
	public DataBean read() throws DSException;
	public void write(DataBean data) throws DSException;
	public void close() throws DSException;
	public DataSourceConfig getDataSourceConfig();
	public void setDataSourceConfig(DataSourceConfig dataSourceConfig);
}
