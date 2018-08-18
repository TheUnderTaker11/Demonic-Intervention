package com.theundertaker11.demonicintervention.event;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.extradata.IExtraData;
import com.theundertaker11.demonicintervention.capability.infusions.IInfusions;
import com.theundertaker11.demonicintervention.capability.maxhealth.IMaxHealth;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class DeathEvents {

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		IInfusions oldInfusions = InfusionUtils.getIInfusions(event.getOriginal());
		IInfusions newInfusions = InfusionUtils.getIInfusions(event.getEntityPlayer());
		newInfusions.setInfusionList(oldInfusions.getInfusionList());
		IExtraData oldData = InfusionUtils.getExtraData(event.getOriginal());
		if(oldData != null) {
			IExtraData newData = InfusionUtils.getExtraData(event.getEntityPlayer());
			newData.setIsAlphaVampire(oldData.getIsAlphaVampire(), event.getEntityPlayer());
			newData.setBloodLevel(oldData.getBloodLevel(), event.getEntityPlayer());
		}
		if (ModUtils.getIMaxHealth(event.getEntityPlayer()) != null) {
			final IMaxHealth oldMaxHealth = ModUtils.getIMaxHealth(event.getOriginal());
			final IMaxHealth newMaxHealth = ModUtils.getIMaxHealth(event.getEntityPlayer());

			if (newMaxHealth != null && oldMaxHealth != null) {
				newMaxHealth.setBonusMaxHealth(oldMaxHealth.getBonusMaxHealth());
			}
		}
	}
}
