package com.bigbass1997.overview.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.bigbass1997.overview.handlers.BlockEventHandler;
import com.bigbass1997.overview.handlers.ChatEventHandler;
import com.bigbass1997.overview.handlers.ChestEventHandler;
import com.bigbass1997.overview.handlers.CommandEventHandler;
import com.bigbass1997.overview.handlers.ConnectionHandler;
import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {
	
	private String hostname, database, username, password;
	private int port;
	
	public ServerProxy(){
		
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent e){
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		
		hostname = config.get(Configuration.CATEGORY_GENERAL, "hostname", "yourhostname.com").getString();
		port = config.get(Configuration.CATEGORY_GENERAL, "port", 3306).getInt();
		database = config.get(Configuration.CATEGORY_GENERAL, "database", "databaseName").getString();
		username = config.get(Configuration.CATEGORY_GENERAL, "username", "root").getString();
		password = config.get(Configuration.CATEGORY_GENERAL, "password", "toor").getString();
		
		config.save();
	}
	
	@Override
	public void init(){
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
		MinecraftForge.EVENT_BUS.register(new ChestEventHandler());
		MinecraftForge.EVENT_BUS.register(new ChatEventHandler());
		MinecraftForge.EVENT_BUS.register(new CommandEventHandler());
		
		FMLCommonHandler.instance().bus().register(new ConnectionHandler());
		
		MySQLControl.init(hostname, port, database, username, password);
		
		Util.log.info("MySQL Status: " + MySQLControl.isConnected);
	}
	
	@Override
	public void stop(){
		MySQLControl.close();
	}
}
