package com.theundertaker11.demonicintervention.proxy;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.init.ItemRegistry;
import com.theundertaker11.demonicintervention.render.RenderRegistry;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemRenderer(Item item, int meta, String id) 
	{
		 ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MODID + ":" + id, "inventory"));
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ResourceLocation essenceTextures = new ResourceLocation(Reference.MODID + ":" + "essence2");

		ModelBakery.registerItemVariants(ItemRegistry.essence, essenceTextures);

		RenderRegistry.render();
	}

}
