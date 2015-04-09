package com.bigbass1997.overview.handlers;

import java.util.Arrays;
import java.util.List;

import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class ChestEventHandler {
	
	private final List<String> names = Arrays.asList(
			"tile.IronChest.name"
	);
	
	@SubscribeEvent
	public void playerInteractEvent(PlayerInteractEvent e){
		if(e.action == Action.RIGHT_CLICK_BLOCK){
			Block clickedBlock = e.world.getBlock(e.x, e.y, e.z);
			if(clickedBlock instanceof BlockChest || checkList(clickedBlock)){
				MySQLControl.logEvent("PlayerInteractEvent", e.entityPlayer.getDisplayName(), e.x, e.y, e.z, Util.getBlockName(clickedBlock));
			}
		}
	}
	
	private boolean checkList(Block block){
		if(names.contains(Util.getBlockName(block))){
			return true;
		} else {
			return false;
		}
	}
}