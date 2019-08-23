package com.theundertaker11.demonicintervention.api.pedestalcrafting;

import net.minecraft.entity.EntityLivingBase;
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
	/**1 or more for good, -1 or less for evil, 0 for either */
	private short karmaType;
	private int time;
	
	public PedestalRecipe(String name, int tier, int cost, int time, short karmaType) {
		this.name = name;
		this.tier = tier;
		this.karmaType = karmaType;
		this.time = time;
		//TODO add cost mechanic
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
	protected void setResult(ItemStack stack) {
		result = stack;
	}
	protected void setMiddleItem(ItemStack stack) {
		middleItem = stack;
	}
	
	public ItemStack getResultingStack() {
		return result;
	}
	
	public short karmaType() {
		return this.karmaType;
	}
	public NonNullList<ItemStack> getItemList(){
		return itemList;
	}
	public int getTimeNeeded() {
		return time;
	}
	public ItemStack getMiddleStack() {
		return this.middleItem;
	}
	/**
	 * Override this to cause some after effects when the recipe is finished crafting, like lightning or curses or whatever
	 * @param world
	 * @param player
	 */
	public void onFinish(BlockPos pedestalPos, World world) {}
	
	/**
	 * A more broad version of onFinish, for rituals
	 * @param pos
	 * @param caster
	 * @param target
	 */
	public void onFinish(BlockPos pos, EntityPlayer caster, EntityLivingBase target) {}
}
