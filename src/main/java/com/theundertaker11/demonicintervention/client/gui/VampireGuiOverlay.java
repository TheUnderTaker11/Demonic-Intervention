package com.theundertaker11.demonicintervention.client.gui;

import com.theundertaker11.demonicintervention.Reference;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.vampiredata.IVampireData;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber
public class VampireGuiOverlay {

	@SubscribeEvent
	public static void RendererPost(RenderGameOverlayEvent.Post event) 
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (event.getType() == ElementType.AIR) 
		{
			if(InfusionUtils.hasInfusion(Infusions.vampirism, mc.player) && InfusionUtils.getVampireData(mc.player) != null)
			{
				ScaledResolution scaled = event.getResolution();
				IVampireData extraData = InfusionUtils.getVampireData(mc.player);
				ResourceLocation bloodbar  = new ResourceLocation(Reference.MODID, "textures/gui/blood_bar.png");
				mc.getTextureManager().bindTexture(bloodbar);
				int width = scaled.getScaledWidth()/2 + 91 - 11*8-2;
				int height = scaled.getScaledHeight()-39;
				//GL11.glEnable(GL11.GL_TEXTURE_2D);
				int textureWidth = 110;
				int textureHeight = 20;
				double bloodPercent = (double)extraData.getBloodLevel()/(double)extraData.getMaxBloodLevel();

				mc.ingameGUI.drawModalRectWithCustomSizedTexture(width+3+((int)(86*(1.0-bloodPercent))), height+1, 2, 9, (int)(88*bloodPercent), 6, textureWidth, textureHeight);
				mc.ingameGUI.drawModalRectWithCustomSizedTexture(width, height, 0, 0, 90, 9, textureWidth, textureHeight);
				
				if(extraData.getIsAlphaVampire()) {
					
					//if(mc.world.getWorldTime() > 23450 || (mc.world.getWorldTime() >= 0 && mc.world.getWorldTime() < 12550)) {
					if(mc.world.provider.isSurfaceWorld() && extraData.isDaytime()) {
						if(mc.world.isRaining())
							mc.ingameGUI.drawCenteredString(mc.fontRenderer, "Raining", width+110, height, Integer.parseInt("008000", 16));
						else 
							mc.ingameGUI.drawCenteredString(mc.fontRenderer, "Deadly", width+110, height, Integer.parseInt("FF0000", 16));
					}
					else{
						mc.ingameGUI.drawCenteredString(mc.fontRenderer, "Safe", width+110, height, Integer.parseInt("008000", 16));
					}
					
				}
			}
		}
		
	}
	
	@SubscribeEvent
	public static void RendererPre(RenderGameOverlayEvent.Pre event) 
	{
		if(InfusionUtils.hasInfusion(Infusions.vampirism, Minecraft.getMinecraft().player))
		{
			if(event.getType() == ElementType.FOOD) {
				event.setCanceled(true);
			}
		}
	}
		/*
		//Arrow
		if(CustomPlayerTick.hasArrow) {
		ResourceLocation arrow  = new ResourceLocation("requestyeet","textures/gui/arrow.png");
		mc.getTextureManager().bindTexture(arrow);
		mc.ingameGUI.drawModalRectWithCustomSizedTexture(width, height, 0, 0, 70, 15, textureWidth, textureHeight);
		mc.ingameGUI.drawCenteredString(mc.fontRendererObj, CustomPlayerTick.arrowTickCount/20/2 + "/" + RequestYeet.arrowTicks/20, width+40, height+3, Integer.parseInt("FFAA00", 16));
		}*/

}
