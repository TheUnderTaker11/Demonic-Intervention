package com.theundertaker11.demonicintervention.crafting.pedestal;

import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalRecipe;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeGarlicCharm extends PedestalRecipe{

	public RecipeGarlicCharm(String name, int tier, int cost, int time) {
		super(name, tier, cost, time, (short)1);
		this.setMiddleItem(new ItemStack(ItemRegistry.garlic));
		this.setResult(new ItemStack(ItemRegistry.garlicCharm));
		
		this.addItem(new ItemStack(Items.NETHER_STAR));
		this.addItem(new ItemStack(Blocks.GOLD_BLOCK));
		this.addItem(new ItemStack(Blocks.GOLD_BLOCK));
		this.addItem(new ItemStack(Blocks.GOLD_BLOCK));
	}

}
