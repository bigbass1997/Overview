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
	
	private static float lastTimeCheck = -1.0f;
	
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
			checkConnection();
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
		checkConnection();
		
		try {
			if(isConnected) connection.close();
		} catch (SQLException e) {
			if(ConfigManager.debug) e.printStackTrace();
		}
	}
	
	public static void logEvent(String eventName, String displayName, int worldID, int posX, int posY, int posZ, String description){
		checkConnection();
		
		if(isConnected){
			String convertedEventName = ConfigManager.eventNames.get(eventName);
			if(convertedEventName == null || convertedEventName == "") convertedEventName = eventName;
			
			new LogThread(dbTable, convertedEventName, displayName, worldID, posX, posY, posZ, description){
				
				@Override
				public void run(){
					Statement stmt;
					try {
						stmt = connection.createStatement();
						stmt.execute("INSERT INTO `" + dbTable + "` (eventName, displayName, worldID, posX, posY, posZ, description) VALUES('" + eventName + "','" + displayName + "','" + worldID + "','" + posX + "','" + posY + "','" + posZ + "','" + description + "')");
					} catch (SQLException e) {
						if(ConfigManager.debug) e.printStackTrace();
					}
				}
				
			}.start();
		} else {
			MySQLControl.autoReconnect();
		}
	}
	
	public static void reconnect(){
		checkConnection();
		
		try {
			if(!isConnected){
				connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
				checkConnection();
			}
		} catch (SQLException e) {
			Util.log.error("MySQLControl.reconnect() SQLException");
			if(ConfigManager.debug) e.printStackTrace();
		}
	}
	
	public static void checkConnection(){
		try {
			if(connection == null){
				isConnected = false;
			} else if(connection.isClosed()){
				isConnected = false;
			} else {
				isConnected = true;
			}
		} catch (SQLException e) {
			if(ConfigManager.debug) e.printStackTrace();
			isConnected = false;
		}
	}
	
	public static void autoReconnect(){
		if(lastTimeCheck == -1.0f){
			lastTimeCheck = System.nanoTime();
			return;
		} else {
			if((System.nanoTime() - lastTimeCheck) > 60000000000.0f){
				if(ConfigManager.debug) Util.log.info("Attempting auto-reconnect!");
				if(!MySQLControl.isConnected) reconnect();
				lastTimeCheck = System.nanoTime();
			}
		}
	}
}
