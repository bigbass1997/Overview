package com.bigbass1997.overview.proxy;

import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import com.bigbass1997.overview.ConfigManager;
import com.bigbass1997.overview.commands.CommandOVReconnect;
import com.bigbass1997.overview.commands.CommandOVReloadConfig;
import com.bigbass1997.overview.commands.CommandOVStatus;
import com.bigbass1997.overview.handlers.BlockEventHandler;
import com.bigbass1997.overview.handlers.ChatEventHandler;
import com.bigbass1997.overview.handlers.CommandEventHandler;
import com.bigbass1997.overview.handlers.ConnectionHandler;
import com.bigbass1997.overview.handlers.PlayerInteractEventHandler;
import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {
	
	public ServerProxy(){
		
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent e){
		ConfigManager.loadConfig(e);
	}
	
	@Override
	public void init(){
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
		MinecraftForge.EVENT_BUS.register(new ChatEventHandler());
		MinecraftForge.EVENT_BUS.register(new CommandEventHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerInteractEventHandler());
		
		FMLCommonHandler.instance().bus().register(new ConnectionHandler());

		CommandHandler ch = (CommandHandler) MinecraftServer.getServer().getCommandManager();
		ch.registerCommand(new CommandOVReconnect());
		ch.registerCommand(new CommandOVStatus());
		ch.registerCommand(new CommandOVReloadConfig());
		
		MySQLControl.init();
		
		Util.log.info("MySQL Status: " + MySQLControl.isConnected);
	}
	
	@Override
	public void stop(){
		MySQLControl.close();
	}
}
