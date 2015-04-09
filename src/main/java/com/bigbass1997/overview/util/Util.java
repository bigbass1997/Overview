package com.bigbass1997.overview.util;

import net.minecraft.block.Block;
import net.minecraft.util.ChatComponentText;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
	
	public static final Logger log = LogManager.getLogger("Overview");
	
	public static final boolean debug = false;
	
	public static String getBlockName(Block block){
		String blockname = "null";
		
		try {
			blockname = block.getLocalizedName();
		} catch (NoSuchMethodError error){
			if(debug) error.printStackTrace();
		}
		
		if(blockname == "null"){
			try {
				blockname = block.getUnlocalizedName();
			} catch (NoSuchMethodError error){
				if(debug) error.printStackTrace();
			}
		}
		
		return blockname;
	}
	
	public static ChatComponentText getChatComponent(String s){
		return new ChatComponentText(s);
		//TODO Add chat formating (e.g. colors etc)
	}
}
