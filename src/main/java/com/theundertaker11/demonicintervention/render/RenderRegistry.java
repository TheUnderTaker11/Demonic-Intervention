package com.theundertaker11.demonicintervention.render;

import com.theundertaker11.demonicintervention.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;

public class RenderRegistry {
	
	//This is for if an item or block doesn't extend My item/block base, or need special renders for metadata
	public static void Render(){
		//If you register a new texture for the same item you have to make another thing in ClientProxy!

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
