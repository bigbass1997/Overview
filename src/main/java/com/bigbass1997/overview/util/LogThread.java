package com.bigbass1997.overview.util;

public class LogThread extends Thread {
	
	public String eventName, displayName, description;
	public int posX, posY, posZ;
	
	public LogThread(String eventName, String displayName, int posX, int posY, int posZ, String description){
		this.eventName = eventName;
		this.displayName = displayName;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.description = description;
	}
}
