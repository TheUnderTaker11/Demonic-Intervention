package com.theundertaker11.demonicintervention.api.pedestalcrafting;

import javax.annotation.Nullable;

import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class PedestalUtils {

	@Nullable
	public static PedestalRecipe getRecipe(ItemStack stack) {
		for(PedestalRecipe recipe : PedestalRecipeRegistry.pedestalRecipes) {
			if(ModUtils.compareItems(stack, recipe.getMiddleStack())) {
				return recipe;
			}
		}
		return null;
	}
	
	@Nullable
	public static PedestalRecipe getRitual(NonNullList<ItemStack> list) {
		for(PedestalRecipe recipe : PedestalRecipeRegistry.pedestalRituals) {
			if(itemListMatch(recipe.getItemList(), list)) {
				return recipe;
			}
		}
		return null;
	}
	
	@Nullable
	public static PedestalEssenceRecipe getEssenceRitual(NonNullList<ItemStack> list) {
		PedestalRecipe recipe = getRitual(list);
		if(recipe instanceof PedestalEssenceRecipe) {
			return (PedestalEssenceRecipe) recipe;
		}else return null;
	}
	/**
	 * Make list1 be GUARENTEED to have item entries orginally, or it will fuck up probably
	 * @param list1
	 * @param list2
	 * @return
	 */
	private static boolean itemListMatch(NonNullList<ItemStack> list1, NonNullList<ItemStack> list2) {
		NonNullList<ItemStack> copy = ModUtils.createCopyItemList(list1);
		for(ItemStack stack2 : list2) {
			for(int i=0; i < copy.size() ; i++) {
				ItemStack copyStack = copy.get(i);
				if(ModUtils.compareItems(copyStack, stack2)) {
					copy.set(i, ItemStack.EMPTY);
					break;
				}
			}
		}
		for(ItemStack stack : copy) {
			if(!stack.isEmpty())
				return false;
		}
		return true;
	}
}
