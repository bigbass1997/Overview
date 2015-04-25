package com.bigbass1997.overview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLControl {
	
	public static Connection connection;
	public static boolean isConnected;
	
	private static String hostname, database, username, password;
	private static int port;
	
	public static void init(String newHostname, int newPort, String newDatabase, String newUsername, String newPassword){
		hostname = newHostname;
		port = newPort;
		database = newDatabase;
		username = newUsername;
		password = newPassword;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
			isClosed();
		} catch (ClassNotFoundException e) {
			Util.log.error("MySQLControl.init() ClassNotFoundException");
			if(Util.debug) e.printStackTrace();
		} catch (SQLException e) {
			Util.log.error("MySQLControl.init() SQLException");
			if(Util.debug) e.printStackTrace();
		}
		
		if(!isConnected){
			Util.log.warn("MySQL Connection FAILED! Logging will not work for this session.");
			Util.log.warn("To retry connection, type /ovreconnect");
		}
	}
	
	public static void close(){
		try {
			if(!isClosed()) connection.close();
			isClosed();
		} catch (SQLException e) {
			if(Util.debug) e.printStackTrace();
		}
	}
	
	public static void logEvent(String eventName, String displayName, int posX, int posY, int posZ, String description){
		new LogThread(eventName, displayName, posX, posY, posZ, description){
			
			@Override
			public void run(){
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
			
		}.start();
	}
	
	public static void reconnect(){
		try {
			if(isClosed()){
				connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
			}
		} catch (SQLException e) {
			Util.log.error("MySQLControl.init() SQLException");
			if(Util.debug) e.printStackTrace();
		}
	}
	
	public static boolean isClosed(){
		if(connection == null){
			isConnected = false;
			return true;
		} else
			try {
				if(connection.isClosed()){
					isConnected = false;
					return true;
				} else {
					isConnected = true;
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				isConnected = false;
				return true;
			}
	}
}
