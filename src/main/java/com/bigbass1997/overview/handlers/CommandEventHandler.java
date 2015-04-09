package com.bigbass1997.overview.handlers;

import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import net.minecraftforge.event.CommandEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommandEventHandler {
	
	@SubscribeEvent
	public void onCommandEvent(CommandEvent e){
		String s = "";
		for(String param : e.parameters){
			s = s.concat(param + " ");
		}
		
		Util.log.info("CommandEvent by " + e.sender.getCommandSenderName() + ": " + e.command.getCommandName() + " " + s);
		
		MySQLControl.logEvent("CommandEvent", e.sender.getCommandSenderName(), e.sender.getPlayerCoordinates().posX, e.sender.getPlayerCoordinates().posY, e.sender.getPlayerCoordinates().posZ, e.command.getCommandName() + " " + s);
	}
}
