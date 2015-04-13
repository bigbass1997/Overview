package com.bigbass1997.overview.handlers;

import java.util.ArrayList;

import com.bigbass1997.overview.util.MySQLControl;
import com.bigbass1997.overview.util.Util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PlayerInteractEventHandler {
	
	@SubscribeEvent
	public void onPlayerInteractEvent(PlayerInteractEvent e){
		TileEntity te = e.world.getTileEntity(e.x, e.y, e.z);
		
		if(te instanceof IInventory){
			MySQLControl.logEvent("PlayerInteract_ChestOpened", e.entityPlayer.getDisplayName(), e.x, e.y, e.z, getStacksAsString((IInventory) te));
		}
	}
	
	@SuppressWarnings("unused")
	private ArrayList<ItemStack> getStacks(IInventory te){
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for(int i = 0; i < te.getSizeInventory(); i++){
			ItemStack stack = te.getStackInSlot(i);
			
			if(stack != null){
				stacks.add(stack);
			}
		}
		
		return stacks;
	}
	
	private String getStacksAsString(IInventory te){
		String s = "";
		
		for(int i = 0; i < te.getSizeInventory(); i++){
			ItemStack stack = te.getStackInSlot(i);
			
			if(stack != null){
				if(stack.getDisplayName() != "" || stack.getDisplayName() != null){
					if(s.isEmpty()){
						s = s.concat("[" + stack.stackSize + "x] " + stack.getDisplayName());
					}else{
						s = s.concat(", [" + stack.stackSize + "x] " + stack.getDisplayName());
					}
				}else{
					try {
						if(s.isEmpty()){
							s = s.concat("[" + stack.stackSize + "x] " + stack.getUnlocalizedName());
						}else{
							s = s.concat(", [" + stack.stackSize + "x] " + stack.getUnlocalizedName());
						}
					} catch (NoSuchMethodError error){
						if(Util.debug) Util.log.error("PlayerInteractEventHandler.getStackAsString() ifelse try NoSuchMethodError");
					}
				}
			}
		}
		
		return s.replaceAll("'", "");
	}
}
