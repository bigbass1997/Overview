package com.bigbass1997.overview.handlers;

import com.bigbass1997.overview.util.MySQLControl;

import net.minecraftforge.event.ServerChatEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChatEventHandler {
	
	@SubscribeEvent
	public void onServerChat(ServerChatEvent e){
		MySQLControl.logEvent("ServerChatEvent", e.username, (int) e.player.posX, (int) e.player.posY, (int) e.player.posZ, e.message);
	}
}
