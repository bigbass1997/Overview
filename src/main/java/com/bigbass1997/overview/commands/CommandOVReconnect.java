package com.bigbass1997.overview.commands;

import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandOVReconnect extends CommandBase {

	@Override
	public String getCommandName() {
		return "ovreconnect";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ovreconnect";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		sender.addChatMessage(Util.getChatComponent("Attempting MySQL Reconnect..."));
		
		MySQLControl.checkConnection();
		
		if(MySQLControl.isConnected){
			sender.addChatMessage(Util.getChatComponent("Overview is already connected to the MySQL Database."));
			return;
		} else {
			MySQLControl.reconnect();
		}
		sender.addChatMessage(Util.getChatComponent("New MySQL Status: " + MySQLControl.isConnected));
		sender.addChatMessage(Util.getChatComponent("For more detailed info type /ovstatus"));
	}

}
