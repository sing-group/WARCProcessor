package com.warcgenerator.datasources.warccsv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static Connection connection;

	public static Connection getConnection(String connectionString)
			throws SQLException {

		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(connectionString);
		}

		return connection;
	}
}
