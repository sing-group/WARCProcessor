package com.warcgenerator.datasources.warccsv.dao;

import java.sql.SQLException;
import java.util.List;

import com.warcgenerator.datasources.warccsv.bean.URLInfo;

public interface DAO {
	Integer put(URLInfo urlInfo) throws SQLException;

	List<URLInfo> get(String domain) throws SQLException;

	List<URLInfo> list() throws SQLException;
}
