package com.theundertaker11.demonicintervention.render;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.blocks.pedestal.TilePedestal;
import com.theundertaker11.demonicintervention.blocks.pedestal.TilePedestalRender;
import com.theundertaker11.demonicintervention.blocks.ritualmainblock.TileRitualMain;
import com.theundertaker11.demonicintervention.blocks.ritualmainblock.TileRitualMainRender;
import com.theundertaker11.demonicintervention.init.ItemRegistry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderRegistry {
	
	//This is for if an item or block doesn't extend My item/block base, or need special renders for metadata
	@SideOnly(Side.CLIENT)
	public static void render()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new TilePedestalRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileRitualMain.class, new TileRitualMainRender());
		
		//If you register a new texture for the same item you have to make another thing in ClientProxy!
		regWithMetaAndName(ItemRegistry.essence, 1, "essence2");
	}

	public static void reg(Item item) {
		ModelResourceLocation res = new ModelResourceLocation(item.getRegistryName().toString(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, res);
	}

	public static void regWithMeta(Item item, int meta) {
		ModelResourceLocation res = new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, res);
	}

	public static void regWithMetaAndName(Item item, int meta, String name) {
		ModelResourceLocation res = new ModelResourceLocation(Reference.MODID + ":" + name, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, res);
	}
}
