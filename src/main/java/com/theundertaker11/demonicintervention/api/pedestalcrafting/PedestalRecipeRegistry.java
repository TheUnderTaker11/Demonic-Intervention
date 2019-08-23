package com.theundertaker11.demonicintervention.api.pedestalcrafting;

import java.util.ArrayList;

public class PedestalRecipeRegistry {

	public static ArrayList<PedestalRecipe> pedestalRecipes = new ArrayList<>();
	public static ArrayList<PedestalRecipe> pedestalRituals = new ArrayList<>();
	
	/**
	 * MUST have unique middle item from any other pedestal recipes or there will be a conflict.
	 * @param recipe
	 */
	public static void addPedestalRecipe(PedestalRecipe recipe) {
		pedestalRecipes.add(recipe);
	}
	
	public static void addPedestalRitual(PedestalRecipe recipe) {
		pedestalRituals.add(recipe);
	}
}
