package com.bigbass1997.overview.commands;

import com.bigbass1997.overview.ConfigManager;
import com.bigbass1997.overview.util.MySQLControl;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandOVReloadConfig extends CommandBase {

	@Override
	public String getCommandName() {
		return "ovreload";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/ovreload";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		ConfigManager.reloadConfig();
		MySQLControl.init();
	}

}
