package com.theundertaker11.demonicintervention.event;

import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.extradata.IExtraData;
import com.theundertaker11.demonicintervention.infusions.Infusions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
/**
 * Will regen blood of non-vamp player and villagers, and take away blood from Vampire players
 * @author TheUnderTaker11
 *
 */
@EventBusSubscriber
public class RegenBloodLevels {

	@SubscribeEvent
	public static void regenAllBlood(WorldTickEvent event) {
		if(!event.world.isRemote && MainEventHandler.bloodCounter > 1198) {
			for(Entity ent: event.world.loadedEntityList)
			{
				if(ent!=null&&ent instanceof EntityLivingBase && !(ent instanceof EntityPlayer))
				{
					EntityLivingBase entity = (EntityLivingBase)ent;
					IExtraData data = InfusionUtils.getExtraData(entity);
					if(data != null) {
						if(data.getBloodLevel()<data.getMaxBloodLevel(entity)) {
							data.addBloodNormalEntity(126/2);
						}
					}
				}
			}
			
			for(EntityPlayer player: event.world.playerEntities)
			{
				if(!InfusionUtils.hasInfusion(Infusions.vampirism, player)) {
					IExtraData data = InfusionUtils.getExtraData(player);
					if(data != null) {
						if(data.getBloodLevel()<data.getMaxBloodLevel(player)) {
							data.addBloodNormalEntity(126/2);
						}
					}
				}
			}
		}
		
		if(!event.world.isRemote && MainEventHandler.twentyMinuteCounter > 2398) {
			for(EntityPlayerMP player : event.world.getMinecraftServer().getPlayerList().getPlayers()) {
				IExtraData extraData = InfusionUtils.getExtraData(player);
				if(InfusionUtils.hasInfusion(Infusions.vampirism, player) && extraData != null) {
					if(extraData.getBloodLevel() > 0) {
						extraData.removeBlood(23, player);
					}
				}
			}
		}
	}
}
