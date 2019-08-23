package com.theundertaker11.demonicintervention.crafting;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.api.pedestalcrafting.PedestalRecipeRegistry;
import com.theundertaker11.demonicintervention.crafting.pedestal.RecipeGarlicCharm;
import com.theundertaker11.demonicintervention.crafting.pedestal.RecipeGarlicSword;
import com.theundertaker11.demonicintervention.crafting.pedestal.RecipeKarmaExtractor;
import com.theundertaker11.demonicintervention.crafting.pedestal.essence.RitualVampireHunter;
import com.theundertaker11.demonicintervention.items.ItemVampireBook;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	
	public static void init() {
		
		pedestalRecipes();
		
		//Real book recipe. This one actually describes how to become a vampire
		ItemStack realBook = new ItemStack(Items.WRITTEN_BOOK);
		NBTTagCompound realTag = new NBTTagCompound();
		ItemVampireBook.setRealTag(realTag);
		realBook.setTagCompound(realTag);
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID + "Book1"), new ResourceLocation(Reference.MODID + "realBook"), realBook, new Object[]{
				"xyx",
				"yzy",
				"xyx", 'z', Items.BOOK, 'x', Blocks.NETHERRACK, 'y', Blocks.SOUL_SAND});
		//This book will just make you kill yourself. (Doesn't MAKE you, just tells you to)
		ItemStack fakeBook = new ItemStack(Items.WRITTEN_BOOK);
		NBTTagCompound fakeTag = new NBTTagCompound();
		ItemVampireBook.setFakeTag(fakeTag);
		fakeBook.setTagCompound(fakeTag);
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID + "Book3"), new ResourceLocation(Reference.MODID + "fakeBook"), fakeBook, new Object[]{
				"xyx",
				"yzy",
				"xyx", 'z', Items.BOOK, 'y', Blocks.NETHERRACK, 'x', Blocks.SOUL_SAND});
		
		/*GameRegistry.addShapedRecipe(ItemRegistry.chalice.getRegistryName(), new ResourceLocation(Reference.MODID + "main"), new ItemStack(ItemRegistry.chalice), new Object[]{
				"xxx",
				"xzx",
				" x ", 'z', "gemEmerald", 'x', "ingotGold"});*/
		/*GameRegistry.addShapedRecipe(ItemRegistry.essence.getRegistryName(), new ResourceLocation(Reference.MODID + "main"), new ItemStack(ItemRegistry.essence), new Object[]{
		"xyx",
		"yzy",
		"xyx", 'z', "flint", 'x', "ingotIron", 'y', "glass"});*/
	}
	
	public static void pedestalRecipes() {
		PedestalRecipeRegistry.addPedestalRecipe(new RecipeGarlicSword("garlic_sword_recipe", 1, 0, 200));
		PedestalRecipeRegistry.addPedestalRecipe(new RecipeKarmaExtractor("karma_extractor_recipe", 1, 0, 400));
		PedestalRecipeRegistry.addPedestalRecipe(new RecipeGarlicCharm("garlic_charm_recipe", 1, 0, 400));

		//Rituals
		PedestalRecipeRegistry.addPedestalRitual(new RitualVampireHunter("vampire_hunter_ritual", 1, 0, 800));
	}
}
