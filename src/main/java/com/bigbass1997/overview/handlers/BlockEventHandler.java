package com.bigbass1997.overview.handlers;

import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class BlockEventHandler {
	
	@SubscribeEvent
	public void onBlockBreak(final BlockEvent.BreakEvent e){
		logBlockEvent(e, "BlockBreak");
	}
	
	@SubscribeEvent
	public void onBlockPlace(final BlockEvent.PlaceEvent e){
		logBlockEvent(e, "BlockPlace");
	}
	
	private void logBlockEvent(BlockEvent e, String eventType){
		String playername = "null";
		
		try {
			playername = ((BreakEvent) e).getPlayer().getDisplayName();
		} catch (ClassCastException error){
			if(Util.debug) Util.log.error("BlockEventHandler.logBlockEvent() try ClassCastException");
			if(Util.debug) error.printStackTrace();
		}
		
		if(playername == "null"){
			try {
				playername = ((PlaceEvent) e).player.getDisplayName();
			} catch (ClassCastException error){
				if(Util.debug) Util.log.error("BlockEventHandler.logBlockEvent() if(playername == null) try ClassCastException");
				if(Util.debug) error.printStackTrace();
			}
		}
		
		MySQLControl.logEvent(eventType, playername, e.x, e.y, e.z, Util.getBlockName(e.block));
	}
}
