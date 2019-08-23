package com.theundertaker11.demonicintervention.blocks.pedestal;

import com.theundertaker11.demonicintervention.tile.ItemStoringTileBase;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TilePedestal extends ItemStoringTileBase{
	
	public TilePedestal()
	{
		super();
	}
	
	public ItemStack getStoredStack()
	{
		return itemStackHandler.getStackInSlot(0);
	}
	
	public void setStoredStack(ItemStack stack) {
		itemStackHandler.setStackInSlot(0, stack);
		this.markDirty();
	}
}
