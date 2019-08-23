package com.theundertaker11.demonicintervention.crafting.pedestal;

import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalRecipe;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeKarmaExtractor extends PedestalRecipe{

	public RecipeKarmaExtractor(String name, int tier, int cost, int time) {
		super(name, tier, cost, time, (short)0);
		this.setMiddleItem(new ItemStack(ItemRegistry.bloodCollector));
		this.setResult(new ItemStack(ItemRegistry.karmaExtractor));
		this.addItem(new ItemStack(Items.NETHER_STAR));
		this.addItem(new ItemStack(Items.NETHER_STAR));
		this.addItem(new ItemStack(Items.SPECTRAL_ARROW));
		this.addItem(new ItemStack(Items.GOLDEN_APPLE, 1, 1));
	}
}
