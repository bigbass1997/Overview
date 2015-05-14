package com.bigbass1997.overview.util;

public class LogThread extends Thread {
	
	public String eventName, displayName, description;
	public int worldID, posX, posY, posZ;
	
	public LogThread(String eventName, String displayName, int worldID, int posX, int posY, int posZ, String description){
		this.eventName = eventName;
		this.displayName = displayName;
		this.worldID = worldID;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.description = description;
	}
}
