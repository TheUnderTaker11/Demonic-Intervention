package com.theundertaker11.demonicintervention.init;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.items.ItemVampireBook;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {
	
	public static void init() {
		
		GameRegistry.addShapedRecipe(ItemRegistry.essence.getRegistryName(), new ResourceLocation(Reference.MODID + "main"), new ItemStack(ItemRegistry.essence), new Object[]{
				"xyx",
				"yzy",
				"xyx", 'z', "flint", 'x', "ingotIron", 'y', "glass"});
		//Real book recipe. This one actually describes how to become a vampire
		ItemStack realBook = new ItemStack(Items.WRITTEN_BOOK);
		NBTTagCompound realTag = new NBTTagCompound();
		ItemVampireBook.setRealTag(realTag);
		realBook.setTagCompound(realTag);
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID + "Book1"), new ResourceLocation(Reference.MODID + "realBook"), realBook, new Object[]{
				"xyx",
				"yzy",
				"xyx", 'z', Items.BOOK, 'x', Blocks.NETHERRACK, 'y', Blocks.SOUL_SAND});
		//This book will just make you kill yourself.
		ItemStack fakeBook = new ItemStack(Items.WRITTEN_BOOK);
		NBTTagCompound fakeTag = new NBTTagCompound();
		ItemVampireBook.setFakeTag(fakeTag);
		fakeBook.setTagCompound(fakeTag);
		GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID + "Book3"), new ResourceLocation(Reference.MODID + "fakeBook"), fakeBook, new Object[]{
				"xyx",
				"yzy",
				"xyx", 'z', Items.BOOK, 'y', Blocks.NETHERRACK, 'x', Blocks.SOUL_SAND});
		
		GameRegistry.addShapedRecipe(ItemRegistry.chalice.getRegistryName(), new ResourceLocation(Reference.MODID + "main"), new ItemStack(ItemRegistry.chalice), new Object[]{
				"xxx",
				"xzx",
				" x ", 'z', "gemEmerald", 'x', "ingotGold"});
	}
}
