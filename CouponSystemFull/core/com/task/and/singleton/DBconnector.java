package com.task.and.singleton;

import java.sql.*;

import javax.xml.bind.annotation.XmlRootElement;

import com.exceptionerrors.ConnectorException;
/**
 * This is the updated Version 3 of DataBase Connector.
 * 
 * @author Raziel
 */

public class DBconnector {

	// Attributes
	private static Connection con = null;
	// private constructor
		private DBconnector(){}

		/**
		 * Starting Pool Connection (protected access) </br>
		 * The method is Protected because ONLY the Singleton starts the pool connection.
		 * 
		 * @throws ConnectorException
		 */
		
		protected static void startPool() throws ConnectorException{
						
				try {
					// This 'Class.forName' line fixed the exception of: "No suitable driver found for jdbc"
			           Class.forName(DatabaseInfo.getDriverclass());
					con = DriverManager.getConnection(
							DatabaseInfo.getUrl(), 
							DatabaseInfo.getUserdbname(), 
							DatabaseInfo.getPassowrddb());
					
				} catch (NullPointerException | SQLException | ClassNotFoundException e) {
					throw new ConnectorException("Error: Connection to the Database - FAILED (Check Your Connection To The Internet " 
							+ "OR Check User and Password of the Database)");
				} // catch
				try {
					
					Class.forName(DatabaseInfo.getDriverclass());
				} catch (ClassNotFoundException e) {
					throw new ConnectorException("Error: Connection to the Driver - FAILED (check the location of your driver)");
				} // catch
			
		} // startPool
		
		/**
		 * Get the current Connection (public access)
		 * @return a {@code Connection} Object of the current Connection.
		 */
		
		public static Connection getConnection() {
				return con;
		} // getConnection
		
} // Class
