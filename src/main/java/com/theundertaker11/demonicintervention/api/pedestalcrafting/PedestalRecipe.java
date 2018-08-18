package com.theundertaker11.demonicintervention.api.pedestalcrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
/**
 * Make sure you call {@link #setResult(ItemStack)} and {@link #setMiddleItem(ItemStack)} in constructor.
 * @author JoshC
 *
 */
public class PedestalRecipe {

	private NonNullList<ItemStack> itemList = NonNullList.create();
	private String name;
	private int tier;
	private ItemStack result;
	private ItemStack middleItem;
	
	public PedestalRecipe(String name, int tier) {
		this.name = name;
		this.tier = tier;
	}
	
	public String getName() {
		return name;
	}
	public int getTier() {
		return this.tier;
	}
	public void addItem(ItemStack stack) {
		itemList.add(stack);
	}
	protected void setResult(ItemStack stack) {}
	protected void setMiddleItem(ItemStack stack) {}
	
	public ItemStack getResultingStack() {
		return result;
	}
	
	public void onCraft(EntityPlayer player, World world, BlockPos pos) {
		
	}
}
