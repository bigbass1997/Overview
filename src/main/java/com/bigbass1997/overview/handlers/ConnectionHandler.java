package com.bigbass1997.overview.handlers;

import com.bigbass1997.overview.util.MySQLControl;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.entity.player.EntityPlayer;

public class ConnectionHandler {
	
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent e){
		EntityPlayer player = e.player;
		
		MySQLControl.logEvent(
				"PlayerLoggedIn",
				player.getDisplayName(),
				e.player.dimension,
				(int) player.posX,
				(int) player.posY,
				(int) player.posZ,
				""
		);
	}
}
