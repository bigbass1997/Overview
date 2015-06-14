package com.bigbass1997.overview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.bigbass1997.overview.ConfigManager;

public class MySQLControl {
	
	public static Connection connection;
	public static boolean isConnected;
	
	private static String hostname, database, username, password, dbTable;
	private static int port;
	
	public static void init(){
		hostname = ConfigManager.hostname;
		port = ConfigManager.port;
		database = ConfigManager.database;
		username = ConfigManager.username;
		password = ConfigManager.password;
		dbTable = ConfigManager.dbTable;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
			isClosed();
		} catch (ClassNotFoundException e) {
			Util.log.error("MySQLControl.init() ClassNotFoundException");
			if(ConfigManager.debug) e.printStackTrace();
		} catch (SQLException e) {
			Util.log.error("MySQLControl.init() SQLException");
			if(ConfigManager.debug) e.printStackTrace();
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
			if(ConfigManager.debug) e.printStackTrace();
		}
	}
	
	public static void logEvent(String eventName, String displayName, int worldID, int posX, int posY, int posZ, String description){
		
		String convertedEventName = ConfigManager.eventNames.get(eventName);
		if(convertedEventName == null || convertedEventName == "") convertedEventName = eventName;
		
		new LogThread(dbTable, convertedEventName, displayName, worldID, posX, posY, posZ, description){
			
			@Override
			public void run(){
				if(isConnected){
					Statement stmt;
					try {
						stmt = connection.createStatement();
						stmt.execute("INSERT INTO `" + dbTable + "` (eventName, displayName, worldID, posX, posY, posZ, description) VALUES('" + eventName + "','" + displayName + "','" + worldID + "','" + posX + "','" + posY + "','" + posZ + "','" + description + "')");
					} catch (SQLException e) {
						if(ConfigManager.debug) e.printStackTrace();
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
			Util.log.error("MySQLControl.reconnect() SQLException");
			if(ConfigManager.debug) e.printStackTrace();
		}
	}
	
	public static boolean isClosed(){
		if(connection == null){
			isConnected = false;
			return true;
		} else {
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
}
