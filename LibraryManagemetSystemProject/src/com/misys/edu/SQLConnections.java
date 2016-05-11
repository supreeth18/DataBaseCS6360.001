package com.misys.edu;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnections {
	public Connection getSQLConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/supreethdb", "root", "root");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;

	}
}
