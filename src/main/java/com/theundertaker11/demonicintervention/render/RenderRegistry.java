package com.theundertaker11.demonicintervention.render;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.blocks.pedestal.TilePedestal;
import com.theundertaker11.demonicintervention.blocks.pedestal.TilePedestalRender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderRegistry {
	
	//This is for if an item or block doesn't extend My item/block base, or need special renders for metadata
	@SideOnly(Side.CLIENT)
	public static void render()
	{
		//If you register a new texture for the same item you have to make another thing in ClientProxy!
		ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new TilePedestalRender());
	}

	public static void reg(Item item) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

	public static void regWithMeta(Item item, int meta) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, meta, new ModelResourceLocation(Reference.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	public static void regWithMetaAndName(Item item, int meta, String name) {
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(item, meta, new ModelResourceLocation(Reference.MODID + ":" + name, "inventory"));
	}
}
