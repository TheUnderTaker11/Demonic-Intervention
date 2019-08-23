package com.theundertaker11.demonicintervention.crafting.pedestal;

import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalRecipe;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeGarlicSword extends PedestalRecipe{

	public RecipeGarlicSword(String name, int tier, int cost, int time) {
		super(name, tier, cost, time, (short)1);
		this.setMiddleItem(new ItemStack(Items.WOODEN_SWORD));
		this.setResult(new ItemStack(ItemRegistry.garlicSword));
		
		this.addItem(new ItemStack(ItemRegistry.garlic));
		this.addItem(new ItemStack(ItemRegistry.garlic));
		this.addItem(new ItemStack(ItemRegistry.garlic));
		this.addItem(new ItemStack(ItemRegistry.garlic));
	}

}
