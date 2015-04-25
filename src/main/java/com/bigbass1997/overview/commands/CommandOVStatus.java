package com.bigbass1997.overview.commands;

import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandOVStatus extends CommandBase {

	@Override
	public String getCommandName() {
		return "ovstatus";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ovstatus";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		boolean isConnected = !MySQLControl.isClosed();
		
		sender.addChatMessage(Util.getChatComponent("MySQL Connection Status: " + isConnected));
		
		if(!isConnected) sender.addChatMessage(Util.getChatComponent("Connection Object: null"));
		else sender.addChatMessage(Util.getChatComponent("Connection Object:" + MySQLControl.connection.toString()));
	}

}
