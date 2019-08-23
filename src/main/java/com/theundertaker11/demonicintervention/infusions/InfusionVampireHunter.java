package com.theundertaker11.demonicintervention.infusions;

import java.util.Iterator;

import com.theundertaker11.demonicintervention.api.infusion.Infusion;
import com.theundertaker11.demonicintervention.api.infusion.InfusionUtils;
import com.theundertaker11.demonicintervention.capability.vampiredata.IVampireData;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber
public class InfusionVampireHunter extends Infusion{

	public InfusionVampireHunter(String nameForShow) {
		super(nameForShow, false);
	}

	
	/**
	 * Makes hunters take 50% less damage from vampires and alphas
	 * The 30% extra they do to Alphas is handled in InfusionVampirism.class
	 * @param event
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onDamageTaken(LivingHurtEvent event) {
		if(event.getSource().getImmediateSource() instanceof EntityPlayer) {
			EntityPlayer attacker = (EntityPlayer)event.getSource().getImmediateSource();
			if(InfusionUtils.hasInfusion(Infusions.vampirism, attacker)) {
				event.setAmount(event.getAmount()/2);
			}
		}
	}
	
	/**
	 * Warns a Hunter if there is a Vampire within 30 blocks of them
	 * @param event
	 */
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		//Warns of nearby vampires
		if(event.side == Side.SERVER && InfusionUtils.hasInfusion(Infusions.vampireHunter, player)) {
			Iterator iterator = ModUtils.getEntitiesInRange(EntityPlayer.class, player.world, player.posX, player.posY,
					player.posZ, 30.5).iterator();
			while (iterator.hasNext()) {
				EntityPlayer nearbyPlayer= (EntityPlayer)iterator.next();
				if(InfusionUtils.hasInfusion(Infusions.vampirism, nearbyPlayer) && !ModUtils.isOnCooldown(player.getPersistentID(), "vampireWarning")) {
					if(InfusionUtils.isAlphaVampire(nearbyPlayer))
							player.sendMessage(new TextComponentString("ALPHA WARNING: There is an Alpha Vampire nearby!"));
					else
						player.sendMessage(new TextComponentString("WARNING: There is a Vampire nearby!"));
					//240 is Ghast scream sound
					player.world.playSound(nearbyPlayer, player.getPosition(), SoundEvent.REGISTRY.getObjectById(240), SoundCategory.PLAYERS, 0.4F, 1.0F);
					ModUtils.addCooldown(player.getPersistentID(), "vampireWarning", 400);
				}
			}
		}
		//Makes Alphas visible
		if(event.side == Side.CLIENT && InfusionUtils.hasInfusion(Infusions.vampireHunter, player)) {
			Iterator iterator = ModUtils.getEntitiesInRange(EntityPlayer.class, player.world, player.posX, player.posY,
					player.posZ, 50.5).iterator();
			while (iterator.hasNext()) {
				EntityPlayer nearbyPlayer= (EntityPlayer)iterator.next();
				nearbyPlayer.setInvisible(false);
			}
		}
	}
}
