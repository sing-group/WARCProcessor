package com.warcgenerator.datasources.warccsv.manager;

import java.sql.SQLException;
import java.util.List;

import com.warcgenerator.datasources.warccsv.bean.URLInfo;
import com.warcgenerator.datasources.warccsv.dao.DAO;

public class DBManager {
	private DAO dao;

	public DBManager(DAO dao) {
		this.dao = dao;
	}

	public void put(URLInfo urlInfo) {
		try {
			dao.put(urlInfo);

			System.out.println("Dato insertado: " + urlInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<URLInfo> get(String domain) {
		try {
			return dao.get(domain);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<URLInfo> list() {
		try {
			return dao.list();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
