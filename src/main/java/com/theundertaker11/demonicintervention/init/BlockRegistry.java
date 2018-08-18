package com.theundertaker11.demonicintervention.init;

import com.theundertaker11.demonicintervention.blocks.generalblocks.BlockSoulCleaner;
import com.theundertaker11.demonicintervention.blocks.pedestal.BlockPedestal;
import com.theundertaker11.demonicintervention.blocks.ritualmainblock.BlockRitualMain;
import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockRegistry {

	public static Block soulCleaner;
	public static Block pedestal;
	public static Block ritualMain;
	/**
	 * MAKE SURE ALL STRING NAMES ARE LOWERCASE
	 */
	public static void init()
	{
		soulCleaner = register(new BlockSoulCleaner("soulcleaner"));
		pedestal = register(new BlockPedestal("pedestal"));
		ritualMain = register(new BlockRitualMain("ritualmain"));
	}
	
	private static <T extends Block> T register(T block, ItemBlock itemBlock) {
		ForgeRegistries.BLOCKS.register(block);
		if (itemBlock != null) {
			//GameRegistry.register(itemBlock);
			ForgeRegistries.ITEMS.register(itemBlock);
		}

		if (block instanceof IItemModelProvider) {
			((IItemModelProvider) block).registerItemModel(itemBlock);
		}

		return block;
	}

	private static <T extends Block> T register(T block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		return register(block, itemBlock);
	}
}
