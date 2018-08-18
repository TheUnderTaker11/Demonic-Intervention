package com.theundertaker11.demonicintervention.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	
	public static void init() {
		
		GameRegistry.addShapedRecipe(ItemRegistry.essence.getRegistryName(), null, new ItemStack(ItemRegistry.essence), new Object[]{
				"xyx",
				"yzy",
				"xyx", 'z', "flint", 'x', "ingotIron", 'y', "glass"});
	}
}
