package com.theundertaker11.demonicintervention.init;

import com.theundertaker11.demonicintervention.blocks.pedestal.TilePedestal;
import com.theundertaker11.demonicintervention.blocks.ritualmainblock.TileRitualMain;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {

	public static void init()
	{
		GameRegistry.registerTileEntity(TilePedestal.class, "tilepedestal");
		GameRegistry.registerTileEntity(TileRitualMain.class, "tileritualmain");
	}
}
