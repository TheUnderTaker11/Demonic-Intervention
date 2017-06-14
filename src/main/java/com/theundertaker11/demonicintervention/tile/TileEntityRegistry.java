package com.theundertaker11.demonicintervention.tile;

import com.theundertaker11.demonicintervention.blocks.pedestal.TilePedestal;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {

	public static void init()
	{
		GameRegistry.registerTileEntity(TilePedestal.class, "TilePedestal");
	}
}
