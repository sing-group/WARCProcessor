package com.warcgenerator.datasources.warccsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.warcgenerator.datasources.warccsv.bean.URLInfo;

public class DBDAO implements DAO {
	private Connection connection;
	private final String URL_INFO_TABLE = "url_info";
	private final String DROP_TABLE = "DROP TABLE " + URL_INFO_TABLE;
	private final String GET_DATA_BY_DOMAIN = "SELECT id, domain, url, filePath, spam FROM "
			+ URL_INFO_TABLE + " WHERE domain = ?";
	private final String INSERT_URL_INFO = "INSERT INTO " + URL_INFO_TABLE
			+ "(domain, url, filePath, spam) VALUES (?, ?, ?, ?)";
	private final String LIST_DATA = "SELECT id, domain, url, filePath, spam FROM "
			+ URL_INFO_TABLE;

	public DBDAO(Connection connection) throws SQLException {
		this.connection = connection;
		createMetadata();
	}

	public void createMetadata() throws SQLException {
		// DROP TABLE if it exists
		try (PreparedStatement statement = connection
				.prepareStatement(DROP_TABLE)) {
			int result = statement.executeUpdate();

			if (result != 0) {
				// TODO control this
				// Table doen't exist
				// throw new SQLException("Error creating table");
			}
		} catch (SQLException ex) {
			// TODO Control this
			// Table doen't exist
		}

		// If tables don't exist we create them
		String CREATE_TABLE = "CREATE TABLE "
				+ URL_INFO_TABLE
				+ " (id INT GENERATED ALWAYS AS IDENTITY,"
				+ " domain VARCHAR(255), url VARCHAR(255) , filePath VARCHAR(255), spam BOOLEAN)";

		try (PreparedStatement statement = connection
				.prepareStatement(CREATE_TABLE)) {
			int result = statement.executeUpdate();

			if (result != 0) {
				throw new SQLException("Error creating table");
			}
		}
	}

	public Integer put(URLInfo urlInfo) throws SQLException {
		Integer id = null;

		try (PreparedStatement statement = connection.prepareStatement(
				INSERT_URL_INFO, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, urlInfo.getDomain());
			statement.setString(2, urlInfo.getUrl());
			statement.setString(3, urlInfo.getFilePath());
			statement.setBoolean(4, urlInfo.getSpam());

			int result = statement.executeUpdate();

			if (result == 1) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						id = generatedKeys.getInt(1);
					}
				}
			} else {
				throw new SQLException("Unexpected result");
			}
		}
		return id;
	}

	public List<URLInfo> get(String domain) throws SQLException {
		List<URLInfo> urlInfoList = new ArrayList<URLInfo>();

		try (PreparedStatement statement = connection
				.prepareStatement(GET_DATA_BY_DOMAIN)) {
			statement.setString(1, domain);

			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					URLInfo urlInfo = new URLInfo();

					urlInfo.setId(result.getInt("id"));
					urlInfo.setDomain(result.getString("domain"));
					urlInfo.setUrl(result.getString("url"));
					urlInfo.setFilePath(result.getString("filePath"));
					urlInfo.setSpam(result.getBoolean("spam"));

					urlInfoList.add(urlInfo);
				}
			}
		}

		return urlInfoList;
	}

	public List<URLInfo> list() throws SQLException {
		List<URLInfo> urlInfoList = new ArrayList<URLInfo>();

		try (PreparedStatement statement = connection
				.prepareStatement(LIST_DATA)) {

			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					URLInfo urlInfo = new URLInfo();

					urlInfo.setId(result.getInt("id"));
					urlInfo.setDomain(result.getString("domain"));
					urlInfo.setUrl(result.getString("url"));
					urlInfo.setFilePath(result.getString("filePath"));
					urlInfo.setSpam(result.getBoolean("spam"));

					urlInfoList.add(urlInfo);
				}
			}
		}

		return urlInfoList;
	}
}
