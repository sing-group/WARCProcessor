package com.warcgenerator.core.datasource.handler;

import java.util.Set;

import com.warcgenerator.core.config.DataSourceConfig;

public interface IDSHandler {
	public DataSourceConfig getDSConfig();
	public void toHandle(Set<String> urlsSpam,
			Set<String> urlsHam);
}
