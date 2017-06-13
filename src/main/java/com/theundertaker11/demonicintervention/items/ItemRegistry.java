package com.theundertaker11.demonicintervention.items;

import com.theundertaker11.demonicintervention.render.IItemModelProvider;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

	public static Item debuggingTool;
	
	public static void init()
	{
		debuggingTool = register(new ItemDebuggingTool("debuggingtool"));
		
	}
	
	private static <T extends Item> T register(T item) 
	{
		GameRegistry.register(item);
		
		if(item instanceof IItemModelProvider){
			((IItemModelProvider)item).registerItemModel(item);
		}
		return item;
	}
}
