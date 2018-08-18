package com.theundertaker11.demonicintervention.blocks.pedestal;

import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TilePedestalRender extends TileEntitySpecialRenderer<TilePedestal>{
	
	@Override
	public void render(TilePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		ItemStack stack = te.getStoredStack();
		//Keeps seeing the itemstack is null. (That's all the method does is a null check)
		if(stack.isEmpty())
		{
			return;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(0.5, 1.06, 0.5);
		float angle = (((float)te.getWorld().getTotalWorldTime() + partialTicks) / 20.0F) * (180F / (float)Math.PI);
		GlStateManager.rotate(angle, 0, 1, 0);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.GROUND);
		GlStateManager.popMatrix();
	}
}
