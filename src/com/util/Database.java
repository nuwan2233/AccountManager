package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** 
 * @author Nuwan Rupsinghe
 * @version Account Manager 1.0
 * Database connection class
 */
public class Database {	
	
	private static final String DRIVER_NAME = "org.sqlite.JDBC";
	private static final String DB_URL = "jdbc:sqlite:resources/AccountManager.db";
	
	public static Connection getDBConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection connect = DriverManager.getConnection(DB_URL);
			System.out.println("DB Connection Successful");
			System.out.println(connect.toString());
			return connect;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
