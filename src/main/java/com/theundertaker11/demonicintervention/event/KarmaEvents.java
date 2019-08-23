package com.theundertaker11.demonicintervention.event;

import com.theundertaker11.demonicintervention.api.KarmaUtils;
import com.theundertaker11.demonicintervention.init.DIConfig;
import com.theundertaker11.demonicintervention.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.village.Village;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class KarmaEvents {

	@SubscribeEvent
	public static void onTame(AnimalTameEvent event) {
		if(!event.getTamer().isServerWorld()) return;
		KarmaUtils.addKarma(event.getTamer(), DIConfig.tamingKarma);
	}
	
	@SubscribeEvent
	public static void entityDeath(LivingDeathEvent event) {
		if(event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			EntityLivingBase entity = event.getEntityLiving();
			if(entity instanceof EntityVillager) {
				KarmaUtils.addKarma(player, DIConfig.killingVillagerKarma);
			}
			else if(entity instanceof EntityZombie) {
				Village village = entity.world.getVillageCollection().getNearestVillage(entity.getPosition(), DIConfig.villageKarmaRange);
				if(village != null) {
					if(village.getNumVillagers() > 3) {
						KarmaUtils.addKarma(player, DIConfig.killingZombieKarma);
					}
				}
			}
			else if(entity instanceof EntityWither) {
				KarmaUtils.addKarma(player, DIConfig.killingWitherKarma);
			}
			else if(entity instanceof EntityTameable) {
				EntityTameable tame = (EntityTameable) entity;
				if(tame.isOwner(player)) {
					KarmaUtils.addKarma(player, -DIConfig.tamingKarma);
				}else if(tame.isTamed()) {
					if(!tame.getAttackTarget().getPersistentID().equals(player.getPersistentID()))
						KarmaUtils.addKarma(player, DIConfig.killingAnimalKarma * 2);
				}else {
					KarmaUtils.addKarma(player, DIConfig.killingAnimalKarma);
				}
			}
			else if(event.getEntityLiving() instanceof EntityAnimal) {
				KarmaUtils.addKarma(player, DIConfig.killingAnimalKarma);
			}
		}
	}
	
	@SubscribeEvent
	public static void livingUpdate(LivingUpdateEvent event) {
		if(event.getEntityLiving().isServerWorld() && event.getEntityLiving() instanceof EntityZombieVillager) {
			EntityZombieVillager zombie = (EntityZombieVillager) event.getEntityLiving();
			NBTTagCompound tag= new NBTTagCompound();
			zombie.writeEntityToNBT(tag);
			if(tag.hasKey("ConversionTime", 99) && tag.getInteger("ConversionTime") > -1) {
				//System.out.println(tag.getUniqueId("ConversionPlayer"));
				if(tag.hasUniqueId("ConversionPlayer")) {
					EntityPlayer player = zombie.getEntityWorld().getMinecraftServer().getPlayerList().getPlayerByUsername(UsernameCache.getLastKnownUsername(tag.getUniqueId("ConversionPlayer")));
					if(player != null && !ModUtils.isOnCooldown(tag.getUniqueId("ConversionPlayer"), "HealingZombieVillager")) {
						KarmaUtils.addKarma(player, DIConfig.curingZombieKarma);
						ModUtils.addCooldown(tag.getUniqueId("ConversionPlayer"), "HealingZombieVillager", 12000);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void livingSpawn(LivingSpawnEvent event) {
		if(!event.getWorld().isRemote) {
		if(event.getEntityLiving() instanceof EntityIronGolem) {
			EntityIronGolem golem = (EntityIronGolem) event.getEntityLiving();
			if(golem.isPlayerCreated()) {
				EntityPlayer player = golem.getEntityWorld().getClosestPlayerToEntity(golem, 5D);
				if(player != null) {
					Village village = golem.getEntityWorld().getVillageCollection().getNearestVillage(golem.getPosition(), DIConfig.villageKarmaRange);
					if(village != null) {
						if(village.getNumVillagers() > 3) {
							KarmaUtils.addKarma(player, DIConfig.creatingIronGolemKarma);
						}
					}
				}
			}
		}
		else if(event.getEntityLiving() instanceof EntityWither) {
			EntityWither wither = (EntityWither) event.getEntityLiving();
			EntityPlayer player = wither.getEntityWorld().getClosestPlayerToEntity(wither, 5D);
			if(player != null) {
				KarmaUtils.addKarma(player, -DIConfig.killingWitherKarma);
			}
		}
		}
	}
	
	@SubscribeEvent
	public static void entityInteract(EntityInteract event) {
		if(event.getEntityLiving() instanceof EntityAnimal) {
			EntityAnimal animal = (EntityAnimal)event.getEntityLiving();
			ItemStack itemstack = event.getEntityPlayer().getHeldItem(event.getHand());
	        if (!itemstack.isEmpty())
	        {
	            if (animal.isBreedingItem(itemstack) && ((animal.getGrowingAge() == 0 && animal.isInLove()) || animal.isChild()) )
	            {
	            	KarmaUtils.addKarma(event.getEntityPlayer(), DIConfig.feedingAnimalKarma);
	            }
	        }
		}
	}
}
