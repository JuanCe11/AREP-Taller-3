package edu.eci.escuelaing.taller3.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates a connection to the DB
 */
public class DBConnection {

	final static String URL = "jdbc:postgresql://ec2-18-215-99-63.compute-1.amazonaws.com:5432/d2euu9s7dvfanp";
	final static String DRIVER = "org.postgresql.Driver";
	final String DB = "d2euu9s7dvfanp";
	final static String USER = "wgwxdbfexidjqr";
	final static String PSW = "e7a72ca617beb9cc8125942562d6c8a622c147bdbe2b059eb261cf1c0e8f444e";

	/**
	 * Creates a connection to the DB
	 * 
	 * @return A connection to the DB
	 * @throws ClassNotFoundException when the driver class doesn't found
	 * @throws SQLException           Connection exception
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		return DriverManager.getConnection(URL, USER, PSW);
	}
}
