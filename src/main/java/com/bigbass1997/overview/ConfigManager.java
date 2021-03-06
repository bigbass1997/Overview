package com.bigbass1997.overview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;

import com.bigbass1997.overview.util.Util;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigManager {
	
	public static String hostname, database, username, password, dbTable;
	public static int port;
	public static boolean debug;
	
	public static Hashtable<String, String> eventNames;

	public static String configJsonPath;
	public static JsonObject configJson;
	
	public static void loadConfig(FMLPreInitializationEvent e){
		configJsonPath = e.getModConfigurationDirectory() + "/Overview.json";
		
		String configString = readFile(configJsonPath);
		if(configString == null || configString == ""){
			createConfig(e);
		}
		
		configJson = new JsonParser().parse(readFile(configJsonPath)).getAsJsonObject();
		
		//--
		
		JsonObject mysqlConfig = configJson.getAsJsonObject("mysql");
		hostname = mysqlConfig.get("hostname").getAsString();
		database = mysqlConfig.get("database").getAsString();
		username = mysqlConfig.get("username").getAsString();
		password = mysqlConfig.get("password").getAsString();
		dbTable = mysqlConfig.get("dbTable").getAsString();
		port = mysqlConfig.get("port").getAsInt();
		
		//--
		
		debug = configJson.get("debug").getAsBoolean();
		
		//--
		
		eventNames = new Hashtable<String, String>();
		JsonObject eventsConfig = configJson.getAsJsonObject("locale").getAsJsonObject("events");
		eventNames.put("BlockBreakEvent", eventsConfig.get("BlockBreakEvent").getAsString());
		eventNames.put("BlockPlaceEvent", eventsConfig.get("BlockPlaceEvent").getAsString());
		eventNames.put("ServerChatEvent", eventsConfig.get("ServerChatEvent").getAsString());
		eventNames.put("CommandEvent", eventsConfig.get("CommandEvent").getAsString());
		eventNames.put("PlayerLoggedInEvent", eventsConfig.get("PlayerLoggedInEvent").getAsString());
		eventNames.put("InventoryOpenedEvent", eventsConfig.get("InventoryOpenedEvent").getAsString());
		
		//--
		
		Hashtable<Filters, ArrayList<String>> filters = new Hashtable<Filters, ArrayList<String>>();
		JsonObject filtersConfig = configJson.getAsJsonObject("filter");
		
		ArrayList<String> eventName = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("eventName")){
			eventName.add(el.getAsString());
		}
		filters.put(Filters.EVENT_NAME, eventName);

		ArrayList<String> displayName = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("displayName")){
			displayName.add(el.getAsString());
		}
		filters.put(Filters.DISPLAY_NAME, displayName);

		ArrayList<String> worldID = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("worldID")){
			worldID.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.WORLD_ID, worldID);

		ArrayList<String> posX = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("posX")){
			posX.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.POS_X, posX);

		ArrayList<String> posY = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("posY")){
			posY.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.POS_Y, posY);

		ArrayList<String> posZ = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("posZ")){
			posZ.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.POS_Z, posZ);
		
		ArrayList<String> description = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("description")){
			description.add(el.getAsString());
		}
		filters.put(Filters.DESCRIPTION, description);
		
		FilterManager.filters = filters;
	}
	
	public static void reloadConfig(){
		configJson = new JsonParser().parse(readFile(configJsonPath)).getAsJsonObject();
		
		//--
		
		JsonObject mysqlConfig = configJson.getAsJsonObject("mysql");
		hostname = mysqlConfig.get("hostname").getAsString();
		database = mysqlConfig.get("database").getAsString();
		username = mysqlConfig.get("username").getAsString();
		password = mysqlConfig.get("password").getAsString();
		dbTable = mysqlConfig.get("dbTable").getAsString();
		port = mysqlConfig.get("port").getAsInt();
		
		//--
		
		debug = configJson.get("debug").getAsBoolean();
		
		//--
		
		eventNames = new Hashtable<String, String>();
		JsonObject eventsConfig = configJson.getAsJsonObject("locale").getAsJsonObject("events");
		eventNames.put("BlockBreakEvent", eventsConfig.get("BlockBreakEvent").getAsString());
		eventNames.put("BlockPlaceEvent", eventsConfig.get("BlockPlaceEvent").getAsString());
		eventNames.put("ServerChatEvent", eventsConfig.get("ServerChatEvent").getAsString());
		eventNames.put("CommandEvent", eventsConfig.get("CommandEvent").getAsString());
		eventNames.put("PlayerLoggedInEvent", eventsConfig.get("PlayerLoggedInEvent").getAsString());
		eventNames.put("InventoryOpenedEvent", eventsConfig.get("InventoryOpenedEvent").getAsString());
		
		//--
		
		Hashtable<Filters, ArrayList<String>> filters = new Hashtable<Filters, ArrayList<String>>();
		JsonObject filtersConfig = configJson.getAsJsonObject("filter");
		
		ArrayList<String> eventName = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("eventName")){
			eventName.add(el.getAsString());
		}
		filters.put(Filters.EVENT_NAME, eventName);

		ArrayList<String> displayName = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("displayName")){
			displayName.add(el.getAsString());
		}
		filters.put(Filters.DISPLAY_NAME, displayName);

		ArrayList<String> worldID = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("worldID")){
			worldID.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.WORLD_ID, worldID);

		ArrayList<String> posX = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("posX")){
			posX.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.POS_X, posX);

		ArrayList<String> posY = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("posY")){
			posY.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.POS_Y, posY);

		ArrayList<String> posZ = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("posZ")){
			posZ.add(String.valueOf(el.getAsInt()));
		}
		filters.put(Filters.POS_Z, posZ);
		
		ArrayList<String> description = new ArrayList<String>();
		for(JsonElement el : filtersConfig.getAsJsonArray("description")){
			description.add(el.getAsString());
		}
		filters.put(Filters.DESCRIPTION, description);
		
		FilterManager.filters = filters;
	}
	
	private static void createConfig(FMLPreInitializationEvent e){
		try {
			File file = new File(configJsonPath);
			FileUtils.copyURLToFile(ConfigManager.class.getClassLoader().getResource("Overview-default.json"), file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @author http://stackoverflow.com/a/19459884/4816410
	 * @author bigbass1997
	 * 
	 * @param path
	 * @return textFromFile or null
	 */
	private static String readFile(String path){
		try {
			File myFile = new File(path);
	        FileInputStream fIn;
			fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
	        String aDataRow = "";
	        String aBuffer = "";
	        while ((aDataRow = myReader.readLine()) != null) 
	        {
	            aBuffer += aDataRow ;
	        }
	        myReader.close();

			return aBuffer;
		} catch (FileNotFoundException e) {
			Util.log.info("File '" + path + "' not found! Creating default file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
