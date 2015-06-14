package com.bigbass1997.overview.util;

public class LogThread extends Thread {
	
	public String eventName, displayName, description, dbTable;
	public int worldID, posX, posY, posZ;
	
	public LogThread(String dbTable, String eventName, String displayName, int worldID, int posX, int posY, int posZ, String description){
		this.dbTable = dbTable;
		this.eventName = eventName;
		this.displayName = displayName;
		this.worldID = worldID;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.description = description;
	}
}
