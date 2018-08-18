package com.theundertaker11.demonicintervention.blocks.ritualmainblock;

import org.lwjgl.opengl.GL11;

import com.theundertaker11.demonicintervention.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TileRitualMainRender  extends TileEntitySpecialRenderer<TileRitualMain>{
	private final ResourceLocation ritualCircle = new ResourceLocation(Reference.MODID, "textures/blocks/ritualcircle.png");
	private final float base0 = 0-1.5F;
	private final float base3 = 3-1.5F;
	@Override
	public void render(TileRitualMain te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		ItemStack stack = te.getStoredStack();
		//Keeps seeing the itemstack is null. (That's all the method does is a null check)
		if(stack.isEmpty())
		{
			return;
		}
		//Render item above block
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(0.5, 2.26, 0.5);
		float angle = (((float)te.getWorld().getTotalWorldTime() + partialTicks) / 20.0F) * (180F / (float)Math.PI);
		GlStateManager.rotate(angle, 0, 1, 0);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.GROUND);
		GlStateManager.popMatrix();
		
		//Render Ritual Circle PNG
		float angle2 = (((float)te.getWorld().getTotalWorldTime() + partialTicks) / 20.0F) * (180F / (float)Math.PI);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(0.5, 3.56, 0.5);
		
		GlStateManager.rotate(270, 1, 0, 0);
		//GlStateManager.tr
		Minecraft.getMinecraft().getTextureManager().bindTexture(ritualCircle);
		
		//Start drawing png
		GlStateManager.enableTexture2D();
		Tessellator t = Tessellator.getInstance();
		BufferBuilder buf = t.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		if(te.getTierNoUpdate() != 0) {
			GlStateManager.rotate(-angle2, 0, 0, 1);
		}
		buf.pos(base0, base0, base0).tex(0, 0).endVertex();
		buf.pos(base3, base0, base0).tex(1,0).endVertex();
		buf.pos(base3,base3,base0).tex(1,1).endVertex();
		buf.pos(base0, base3,base0).tex(0,1).endVertex();
		t.draw();
		
		GlStateManager.popMatrix();
	}

}
