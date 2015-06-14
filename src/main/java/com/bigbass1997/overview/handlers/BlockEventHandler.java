package com.bigbass1997.overview.handlers;

import com.bigbass1997.overview.ConfigManager;
import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class BlockEventHandler {
	
	@SubscribeEvent
	public void onBlockBreak(final BlockEvent.BreakEvent e){
		logBlockEvent(e, "BlockBreakEvent");
	}
	
	@SubscribeEvent
	public void onBlockPlace(final BlockEvent.PlaceEvent e){
		logBlockEvent(e, "BlockPlaceEvent");
	}
	
	private void logBlockEvent(BlockEvent e, String eventType){
		String playername = "null";
		
		try {
			playername = ((BreakEvent) e).getPlayer().getDisplayName();
		} catch (ClassCastException error){
			if(ConfigManager.debug) Util.log.error("BlockEventHandler.logBlockEvent() try ClassCastException");
			if(ConfigManager.debug) error.printStackTrace();
		}
		
		if(playername == "null"){
			try {
				playername = ((PlaceEvent) e).player.getDisplayName();
			} catch (ClassCastException error){
				if(ConfigManager.debug) Util.log.error("BlockEventHandler.logBlockEvent() if(playername == null) try ClassCastException");
				if(ConfigManager.debug) error.printStackTrace();
			}
		}
		MySQLControl.logEvent(eventType, playername, e.world.provider.dimensionId, e.x, e.y, e.z, Util.getBlockName(e.block));
	}
}
