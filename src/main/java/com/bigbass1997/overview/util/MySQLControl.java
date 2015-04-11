package com.bigbass1997.overview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLControl {
	
	public static Connection connection;
	public static boolean isConnected;
	
	public static void init(String hostname, int port, String database, String username, String password){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
			isConnected = !connection.isClosed();
		} catch (ClassNotFoundException e) {
			if(Util.debug) e.printStackTrace();
		} catch (SQLException e) {
			if(Util.debug) e.printStackTrace();
		}
		
		if(!isConnected){
			Util.log.warn("MySQL Connection FAILED! Logging will not work for this session.");
			Util.log.warn("To retry connection, restart server. (Command will be added in the future)");
		}
	}
	
	public static void close(){
		try {
			if(connection != null && isConnected) connection.close();
		} catch (SQLException e) {
			if(Util.debug) e.printStackTrace();
		}
	}
	
	public static void logEvent(String eventName, String displayName, int posX, int posY, int posZ, String description){
		if(isConnected){
			Statement stmt;
			try {
				stmt = connection.createStatement();
				stmt.execute("INSERT INTO events (eventName, displayName, posX, posY, posZ, description) VALUES('" + eventName + "','" + displayName + "','" + posX + "','" + posY + "','" + posZ + "','" + description + "')");
			} catch (SQLException e) {
				if(Util.debug) e.printStackTrace();
			}
		}
	}
}
