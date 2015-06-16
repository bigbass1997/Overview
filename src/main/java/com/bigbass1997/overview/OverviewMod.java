package com.bigbass1997.overview;

import com.bigbass1997.overview.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = OverviewMod.MODID, version = OverviewMod.VERSION, acceptableRemoteVersions = "*")
public class OverviewMod {
	public static final String MODID = "Overview";
	public static final String VERSION = "0.8";
	
	@Mod.Instance(MODID)
	public static OverviewMod instance;
	
	@SidedProxy(clientSide = "com.bigbass1997.overview.proxy.ClientProxy", serverSide = "com.bigbass1997.overview.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){
		proxy.preInit(e);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e){
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e){
	}
	
	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent e){
		proxy.stop();
	}
}
