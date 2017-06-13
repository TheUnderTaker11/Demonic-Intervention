package com.theundertaker11.demonicintervention.event;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod.EventBusSubscriber
public class PlayerTick {

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		if(InfusionUtils.getIInfusions(event.player)==null) return;
		
		EntityPlayer player = event.player;
		IInfusions iInfusions = InfusionUtils.getIInfusions(player);
		
		for(Infusion infusion : iInfusions.getInfusionList())
		{
			if(infusion.shouldBeTicked())
			{
				//Calls it on both server and client, just in case both is needed
				infusion.tickInfusion(player, iInfusions);
			}
		}
	}
}
